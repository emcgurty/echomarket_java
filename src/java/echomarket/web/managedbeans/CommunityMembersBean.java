package echomarket.web.managedbeans;

import echomarket.hibernate.Communities;
import echomarket.hibernate.Participant;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "communityMembersBean")
@RequestScoped
public class CommunityMembersBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  private String participant_id;
  private String community_id;
  private String remoteIp;
  private String firstName;
  private String update_first_Name;
  private String mi;
  private String lastName;
  private String alias;
  private Integer isActive;
  private Integer isCreator;
  private String emailAlternative;
  private Integer editable;
  private String editWhichRecord;
  private Participant[] existing_member;
  private ArrayList<Participant> comm_member_rows;
  private String[] efirstName;
  private String[] elastName;
  private String[] ealias;
  private Integer[] eisActive;
  private String[] eemail;
  private Integer howManyRecords;

  private List getExistingMemberList() {
    // TO DO: Need to code for when getHowMany = 0... May be not
    ArrayList<Participant> comm_member = new ArrayList<Participant>();
    Integer howMany = getHowManyRecords();
    Participant new_cm;
    for (int i = 0; i < howMany; i++) {
      new_cm = new Participant(UUID.randomUUID().toString(), ubean.getCommunityId(), UUID.randomUUID().toString(), null, null, null, null, null, 1, 1, new Date(), new Date(), i, 0, 0);
      comm_member.add(new_cm);
    }
    this.comm_member_rows = comm_member;
    return comm_member;

  }

  public String load_community_members() {  // editable = 0

    this.firstName = "lis";
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List result = null;
    Boolean isCreatorRights = false;

    queryString = "FROM Participant where community_id = :cid AND is_creator = 1";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", ubean.getCommunityId())
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);

    } finally {

      if (result.size() > 0) {

        // Then person has right privs
        if (hib.isOpen() == false) {
          hib = hib_session();
        }
        if (tx.isActive() == false) {
          tx = hib.beginTransaction();
        }

        // In case some user didn't finish an update
        queryString = "FROM Participant where community_id = :cid";
        try {
          result = hib.createQuery(queryString)
                  .setParameter("cid", ubean.getUser_id())
                  .list();
          for (int i = 0; i < result.size(); i++) {
            Participant cm = (Participant) result.get(i);
            if (cm.getEditable() == 1) {
              cm.setEditable(0);
              hib.update(cm);
            }
          }
          tx.commit();
        } catch (Exception ex) {
          tx.rollback();
          Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

          if (hib.isOpen() == true) {
            hib.close();
          }
          if (tx.isActive() == true) {
            tx = null;
            try {
              hib = null;
            } catch (Exception ex) {
            }
          }
          isCreatorRights = true;
        }
      }

      if (isCreatorRights == true) {
        this.editable = 0;
        tx = null;
        hib = null;
        return "community_members.xhtml?faces-redirect=true";
      } else {
        message(null, "MustBeCommunityCreatorAddMember", null);
        return "index";

      }
    }
  }

  public void editAction(Participant cmid) {

    Session hib = null;
    Transaction tx = null;
    cmid.setEditable(1);
    this.firstName = cmid.getFirstName();
    this.lastName = cmid.getLastName();
    this.alias = cmid.getAlias();
    /// This is wrong
    // this.setEmailAlternative(cmid.getEmailAlternative());

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.update(cmid);
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;

    }
  }

  //http://stackoverflow.com/questions/8744162/difference-between-returning-null-and-from-a-jsf-action
  //return "";
  public void addAction() {
    this.editable = 3;
    //this.isActive_boolean = "true";
  }

  public void saveAction() {
    Session hib = hib_session();
    Transaction tx = hib.beginTransaction();
    List new_rows = getComm_member_rows();
    Participant comm;
    String new_uuid = null;

    for (int i = 0; i < new_rows.size(); i++) {
      Participant cm = (Participant) new_rows.get(i);
      //new_uuid = UUID.randomUUID().toString();
      /// Will eventually use Hibernate to check for duplicates.
      if (cm.getAlias() != null && cm.getFirstName() != null && cm.getLastName() != null) {
        cm.setEditable(0);
        this.editable = 0;
        //comm = new Participant();
        try {
          hib.save(cm);
          tx.commit();
          System.out.println("COMMUNITY MEMEBER SAVED" + new_uuid);
        } catch (Exception ex) {
          tx.rollback();
          Logger.getLogger(CommunitiesBean.class
                  .getName()).log(Level.SEVERE, "COMMUNITY MEMEBER NOT SAVED" + new_uuid, ex);

        } finally {
          cm = null;
          if (hib.isOpen() == false) {
            hib = hib_session();
          }
          if (tx.isActive() == false) {
            tx = hib.beginTransaction();
          }
        }
      }
    }

    if (hib.isOpen() == true) {
      hib.close();

    }
    if (tx.isActive() == true) {
      tx = null;
      try {
        hib = null;
      } catch (Exception ex) {
      }
    }

    this.editable = 0;

  }

  public void cancelAction(Participant cmid) {

    Session hib = null;
    Transaction tx = null;
    Boolean cancelSuccess = false;
    cmid.setEditable(0);
    this.firstName = cmid.getFirstName();
    this.lastName = cmid.getLastName();
    this.alias = cmid.getAlias();
    /// This is wrong
    // this.setEmailAlternative(cmid.getEmailAlternative());

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.update(cmid);
      tx.commit();
      cancelSuccess = true;
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }
    if (cancelSuccess == true) {
      message(null, "MemberRecordDeleted", null);
    } else {
      message(null, "MemberRecordNotDeleted", null);
    }
  }

  public void updateAction() {
    String nothing = null;
//    Session hib = null;
//    Transaction tx = null;
//    String newMemberName = cm.getFirstName() + " " + cm.getLastName();
//    cm.setEditable(0);
//
//    try {
//      hib = hib_session();
//      tx = hib.beginTransaction();
//      hib.update(cm);
//      tx.commit();
//      message(null, "MemberRecordUpdated", new Object[]{newMemberName});
//    } catch (Exception ex) {
//      tx.rollback();
//      Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, "COMMUNITY MEMEBER NOT SAVED", ex);
//      message(null, "MemberRecordNotUpdated", new Object[]{newMemberName});
//    } finally {
//      tx = null;
//      hib = null;
//    }
  }

  //return "community_members.xhtml?faces-redirect=true";
  public List buildCommunityMemberCreators() {
    Session hib = hib_session();
    Transaction tx = hib.beginTransaction();
    String queryString = null;
    List result = null;

    queryString = "FROM Participant where community_id = :cid and isCreator = 1";
    try {
      result = hib.createQuery(queryString)
              .setParameter("cid", ubean.getCommunityId())
              .list();
      tx.commit();

    } catch (Exception ex) {
      Logger.getLogger(CommunityMembersBean.class
              .getName()).log(Level.SEVERE, "ERROR IN build Community Member list", ex);
      System.out.println("ERROR IN build Community Member list");
      System.out.println(ex);

    } finally {
      if (hib.isOpen() == true) {
        hib.close();
      }
      if (tx.isActive() == true) {
        tx = null;
        try {
          hib = null;
        } catch (Exception ex) {
        }
      }
    }

    return result;

  }

  public List buildCommunityMembersList() {

    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List result = null;

    queryString = "FROM Participant where community_id = :cid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", ubean.getCommunityId())
              .list();
      tx.commit();

    } catch (Exception ex) {
      Logger.getLogger(CommunityMembersBean.class
              .getName()).log(Level.SEVERE, "ERROR IN build Community Member list", ex);
      System.out.println("ERROR IN build Community Member list");
      System.out.println(ex);

    } finally {
      if (hib.isOpen() == true) {
        hib.close();
      }
      if (tx.isActive() == true) {
        tx = null;
        try {
          hib = null;
        } catch (Exception ex) {
        }
      }
    }

    return result;

  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the mi
   */
  public String getMi() {
    return mi;
  }

  /**
   * @param mi the mi to set
   */
  public void setMi(String mi) {
    this.mi = mi;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the isActive
   */
  public Integer getIsActive() {
    return isActive;
  }

  /**
   * @param isActive the isActive to set
   */
  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
  }

  /**
   * @return the remoteIp
   */
  public String getRemoteIp() {
    return remoteIp;
  }

  /**
   * @param remoteIp the remoteIp to set
   */
  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  /**
   * @return the community_id
   */
  public String getCommunity_id() {
    return community_id;
  }

  /**
   * @param community_id the community_id to set
   */
  public void setCommunity_id(String community_id) {
    this.community_id = community_id;
  }

  /**
   * @return the editable
   */
  public Integer getEditable() {
    return editable;
  }

  /**
   * @param editable the editable to set
   */
  public void setEditable(Integer editable) {
    this.editable = editable;
  }

  /**
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  /**
   * @param alias the alias to set
   */
  public void setAlias(String alias) {
    this.alias = alias;
  }

  /**
   * @return the isCreator
   */
  public Integer getIsCreator() {
    return isCreator;
  }

  /**
   * @param isCreator the isCreator to set
   */
  public void setIsCreator(Integer isCreator) {
    this.isCreator = isCreator;
  }

  /**
   * @return the existing_alternative
   */
  public List getExisting_member() {
    return getExistingMemberList();
  }

  /**
   * @param existing_alternative the existing_alternative to set
   */
  public void setExisting_member(Participant[] cm) {
    this.setExisting_member(cm);
  }

  /**
   * @return the editWhichRecord
   */
  public String getEditWhichRecord() {
    return editWhichRecord;
  }

  /**
   * @param editWhichRecord the editWhichRecord to set
   */
  public void setEditWhichRecord(String editWhichRecord) {
    this.editWhichRecord = editWhichRecord;
  }

  /**
   * @return the howManyRecords
   */
  public Integer getHowManyRecords() {
    return howManyRecords;
  }

  /**
   * @param howManyRecords the howManyRecords to set
   */
  public void setHowManyRecords(Integer howManyRecords) {
    this.howManyRecords = howManyRecords;
  }

  /**
   * @return the comm_member_rows
   */
  public ArrayList<Participant> getComm_member_rows() {
    return comm_member_rows;
  }

  /**
   * @param comm_member_rows the comm_member_rows to set
   */
  public void setComm_member_rows(ArrayList<Participant> comm_member_rows) {
    this.comm_member_rows = comm_member_rows;
  }

  /**
   * @return the efirstName
   */
  public String[] getEfirstName() {
    return efirstName;
  }

  /**
   * @param efirstName the efirstName to set
   */
  public void setEfirstName(String[] efirstName) {
    this.efirstName = efirstName;
  }

  /**
   * @return the elastName
   */
  public String[] getElastName() {
    return elastName;
  }

  /**
   * @param elastName the elastName to set
   */
  public void setElastName(String[] elastName) {
    this.elastName = elastName;
  }

  /**
   * @return the ealias
   */
  public String[] getEalias() {
    return ealias;
  }

  /**
   * @param ealias the ealias to set
   */
  public void setEalias(String[] ealias) {
    this.ealias = ealias;
  }

  /**
   * @return the eisActive
   */
  public Integer[] getEisActive() {
    return eisActive;
  }

  /**
   * @param eisActive the eisActive to set
   */
  public void setEisActive(Integer[] eisActive) {
    this.eisActive = eisActive;
  }

  /**
   * @return the eemail
   */
  public String[] getEemail() {
    return eemail;
  }

  /**
   * @param eemail the eemail to set
   */
  public void setEemail(String[] eemail) {
    this.eemail = eemail;
  }

  /**
   * @return the participant_id
   */
  public String getParticipant_id() {
    return participant_id;
  }

  /**
   * @param participant_id the participant_id to set
   */
  public void setParticipant_id(String participant_id) {
    this.participant_id = participant_id;
  }

  /**
   * @return the emailAlternative
   */
  public String getEmailAlternative() {
    return emailAlternative;
  }

  /**
   * @param emailAlternative the emailAlternative to set
   */
  public void setEmailAlternative(String emailAlternative) {
    this.emailAlternative = emailAlternative;
  }

  /**
   * @return the update_first_Name
   */
  public String getUpdate_first_Name() {
    return update_first_Name;
  }

  /**
   * @param update_first_Name the update_first_Name to set
   */
  public void setUpdate_first_Name(String update_first_Name) {
    this.update_first_Name = update_first_Name;
  }

}
