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
  private String mi;
  private String lastName;
  private String alias;
  private Integer isActive;
  private Integer isCreator;
  private String emailAlternative;
  private Integer editable;
  private String editWhichRecord;
  private List new_member;
  private List existing_member;
  private ArrayList<Participant> new_comm_member_rows;
  private ArrayList<Participant> comm_member_rows;
  private Integer howManyRecords;

  private List getNewMemberList() {
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
    this.emailAlternative = cmid.getEmailAlternative();
    
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
          Logger.getLogger(CommunityMembersBean.class
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
    //this.setEmailAlternative(cmid.getEmailAlternative());

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

  public void updateAction(Integer whichRow) {

    Session hib = null;
    Transaction tx = null;
    List new_rows = getNew_comm_member_rows();
    Participant cm = (Participant) new_rows.get(whichRow);
    //new_uuid = UUID.randomUUID().toString();
    /// Will eventually use Hibernate to check for duplicates.
    if (cm.getAlias() != null && cm.getFirstName() != null && cm.getLastName() != null) {
      cm.setEditable(0);
      cm.setFirstName(firstName);
      cm.setLastName(lastName);
      cm.setEmailAlternative(emailAlternative);
      cm.setAlias(alias);

      try {
        hib = hib_session();
        tx = hib.beginTransaction();
        hib.update(cm);
        tx.commit();
        this.editable = 0;
        System.out.println("COMMUNITY MEMBER UPDATED");
      } catch (Exception ex) {
        tx.rollback();
        Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, "COMMUNITY MEMBER NOT updates", ex);
      } finally {
        hib = null;
        tx = null;
      }
    }

  }

  public ArrayList<Participant> getNew_comm_member_rows() {
    return new_comm_member_rows;
  }

  public void setNew_comm_member_rows(ArrayList<Participant> new_comm_member_rows) {
    this.new_comm_member_rows = new_comm_member_rows;
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
      Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, "ERROR IN build Community Member list", ex);
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

  public List getExistingMemberList() {

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
      tx = null;
      hib = null;
    }
    ArrayList<Participant> comm_member = new ArrayList<Participant>();
    Participant existing_cm;
    for (int i = 0; i < result.size(); i++) {
      Participant pp = (Participant) result.get(i);
      existing_cm = new Participant(pp.getParticipant_id(), pp.getCommunityId(), pp.getUserId(), "NA", pp.getFirstName(), pp.getMi(), pp.getLastName(), pp.getAlias(), pp.getIsActive(), pp.getEditable(), pp.getDateCreated(), pp.getDateUpdated(), i, pp.getGoodwill(), pp.getAge18OrMore());
      comm_member.add(existing_cm);
    }
    this.new_comm_member_rows = comm_member;
    return new_comm_member_rows;

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

  public Integer getIsCreator() {
    return isCreator;
  }


  public void setIsCreator(Integer isCreator) {
    this.isCreator = isCreator;
  }

  public List getNew_member() {
    return getNewMemberList();
  }

  public void setNew_member(Participant[] cm) {
    this.setNew_member(cm);
  }

  public List getExisting_member() {
    return getExistingMemberList();
  }

  public void setExisting_member(Participant[] existing_member) {
    this.setExisting_member(existing_member);
  }

  public String getEditWhichRecord() {
    return editWhichRecord;
  }

  public void setEditWhichRecord(String editWhichRecord) {
    this.editWhichRecord = editWhichRecord;
  }

  public Integer getHowManyRecords() {
    return howManyRecords;
  }

  public void setHowManyRecords(Integer howManyRecords) {
    this.howManyRecords = howManyRecords;
  }

  public ArrayList<Participant> getComm_member_rows() {
    return comm_member_rows;
  }

  public void setComm_member_rows(ArrayList<Participant> comm_member_rows) {
    this.comm_member_rows = comm_member_rows;
  }

  public String getParticipant_id() {
    return participant_id;
  }

  public void setParticipant_id(String participant_id) {
    this.participant_id = participant_id;
  }

  public String getEmailAlternative() {
    return emailAlternative;
  }

  public void setEmailAlternative(String emailAlternative) {
    this.emailAlternative = emailAlternative;
  }

}
