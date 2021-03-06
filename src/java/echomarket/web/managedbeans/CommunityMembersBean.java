package echomarket.web.managedbeans;

import echomarket.SendEmail.SendEmail;
import echomarket.hibernate.Participant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
//import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class CommunityMembersBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;

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
  private String errorMessage;
  private Integer currentRow;

  // emm 1.8
  public CommunityMembersBean() {

  }

//  public String skipMembers() {
//    return ubean.skipCommunityMembers();   // hopefully not cyclic
//
//  }
  public String actionSave() {
    this.errorMessage = null;
    Session hib = null;
    Transaction tx = null;
    Participant cm = null;
    List new_rows = getNew_comm_member_rows();
    Integer new_row_size = new_rows.size();
    SendEmail se = null;
    String[] getMap = null;
    Boolean savedRecord = false;
    String fullName = null;

    for (int i = 0; i < new_row_size; i++) {
      cm = (Participant) new_rows.get(i);
      if ((cm.getAlias().isEmpty() == false) && (cm.getFirstName().isEmpty() == false) && (cm.getLastName().isEmpty() == false)) {
        this.firstName = cm.getFirstName();
        this.lastName = cm.getLastName();
        this.alias = cm.getAlias();
        this.emailAlternative = cm.getEmailAlternative();
        this.participant_id = cm.getParticipant_id();
        fullName = this.firstName + " " + this.lastName + " with alias " + this.alias;
        if (checkForDuplicate() == false) {

          try {
            hib = hib_session();
            tx = hib.beginTransaction();
            hib.save(cm);
            tx.commit();
            savedRecord = true;
          } catch (Exception ex) {
            tx.rollback();
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, "COMMUNITY MEMBER NOT SAVED", ex);
          } finally {
            hib = null;
            tx = null;
          }
          if (savedRecord == true) {
            getMap = new String[2];
            getMap = app.getApplicationEmail();
            try {
              se = new SendEmail("member", this.firstName, this.lastName, this.alias, this.emailAlternative, getMap[0], getMap[1], app.getCommunityName(), this.participant_id, app.getUser_id());
              savedRecord = true;
            } catch (Exception ex) {
              System.out.println("Error in Send Member Notification");
              Logger.getLogger(CommunityMembersBean.class.getName())
                      .log(Level.SEVERE, "COMMUNITY MEMBER NOT updated", ex);

            } finally {
              se = null;
              getMap = null;
            }
            if (savedRecord == true) {
              app.setPartID(savedRecord);
              app.setComMemberDetailID(savedRecord);
              message(null, "NewMember", new Object[]{fullName});
            }
          }
        }
      }
    }
    return load_community_members();
  }

  private Boolean hasCreatorRights() {

    System.out.println("Called hasCreatorRights");
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    String cid = app.getCommunityId();
    String uid = app.getUser_id();
    List result = null;
    Boolean isCreatorRights = false;
    queryString = "FROM Participant where community_id = :cid AND is_creator = 1 AND user_id = :uid ";

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", cid)
              .setParameter("uid", uid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }

    if (result != null) {
      if (result.size() == 1) {
        isCreatorRights = true;
      }
    }
    result = null;
    return isCreatorRights;
  }

  public String load_community_members() {

    this.currentRow = -1;
    this.errorMessage = null;
    List result = null;
    Session hib = null;
    Transaction tx = null;
    if (hasCreatorRights() == true) {

      String queryString = null;
      queryString = "FROM Participant where community_id = :cid";
      try {
        hib = hib_session();
        tx = hib.beginTransaction();
        result = hib.createQuery(queryString)
                .setParameter("cid", app.getCommunityId())
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
      } finally {
        tx = null;
        hib = null;
        result = null;
      }

      this.editable = 0;
      /// applicationBean editable not used here
      
      return "community_members";
    } else {
      message(null, "MustBeCommunityCreatorAddMember", null);
      return "index";
    }
  }

  public void editAction(Participant cmid) {
    this.errorMessage = null;
    if (this.currentRow != null) {
      if (this.currentRow > -1) {
        cancelAction(comm_member_rows.get(this.currentRow));
        this.currentRow = -1;
      }
    }
    Session hib = null;
    Transaction tx = null;
    cmid.setEditable(1);
    this.currentRow = cmid.getRowIndex();
    this.firstName = cmid.getFirstName();
    this.lastName = cmid.getLastName();
    this.alias = cmid.getAlias();
    this.emailAlternative = cmid.getEmailAlternative();
    this.isActive = cmid.getIsActive();
    this.isCreator = cmid.getIsCreator();
    this.editable = 1;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.update(cmid);
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger
              .getLogger(CommunityMembersBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;

    }
  }

  public void actionReturn() {
    this.editable = 0;

  }

  public void addAction() {

    if (this.howManyRecords > 0 && this.howManyRecords < 26) {
      if (this.currentRow != null) {
        if (this.currentRow > -1) {
          cancelAction(comm_member_rows.get(this.currentRow));
          this.currentRow = -1;
        }
      }
      this.setErrorMessage("");
      this.editable = 3;

    } else {
      this.setErrorMessage("Please provide a value between 1 and 25");
    }

  }

  public void cancelAction(Participant cmid) {
    this.errorMessage = null;
    this.currentRow = cmid.getRowIndex();
    Session hib = null;
    Transaction tx = null;
    Boolean cancelSuccess = false;
    cmid.setEditable(0);
    this.firstName = cmid.getFirstName();
    this.lastName = cmid.getLastName();
    this.alias = cmid.getAlias();
    this.emailAlternative = cmid.getEmailAlternative();
    this.isCreator = cmid.getIsCreator();
    this.isActive = cmid.getIsActive();

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.update(cmid);
      tx.commit();
      cancelSuccess = true;
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunityMembersBean.class.getName())
              .log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }
    if (cancelSuccess == true) {
      //message(null, "MemberRecordDeleted", null);
    } else {
      //message(null, "MemberRecordNotDeleted", null);
    }
  }

  public void updateAction(Integer whichRow) {  /// For existing member: This works
    this.errorMessage = null;
    Session hib = null;
    Transaction tx = null;
    List new_rows = getComm_member_rows();
    Participant cm = (Participant) new_rows.get(whichRow);
    Boolean foundDuplicate = false;

    if ((this.firstName.isEmpty() == false) && (this.lastName.isEmpty() == false) && (this.alias.isEmpty() == false)) {

      for (int i = 0; i < new_rows.size(); i++) {
        if (i != whichRow) {
          Participant pp = (Participant) new_rows.get(i);
          if ((pp.getFirstName().equalsIgnoreCase(this.firstName)) && (pp.getLastName().equalsIgnoreCase(this.lastName)) && (pp.getAlias().equalsIgnoreCase(this.alias))) {
            foundDuplicate = true;
            break;
          }
        }
      }

      if (foundDuplicate == false) {
        cm.setEditable(0);
        cm.setFirstName(firstName);
        cm.setLastName(lastName);
        cm.setEmailAlternative(emailAlternative);
        cm.setAlias(alias);
        cm.setIsCreator(isCreator);
        cm.setIsActive(isActive);

        try {
          hib = hib_session();
          tx = hib.beginTransaction();
          hib.update(cm);
          tx.commit();
          this.editable = 0;
          System.out.println("COMMUNITY MEMBER UPDATED");
        } catch (Exception ex) {
          tx.rollback();
          Logger.getLogger(CommunityMembersBean.class.getName())
                  .log(Level.SEVERE, "COMMUNITY MEMBER NOT updates", ex);
        } finally {
          hib = null;
          tx = null;
        }
      } else {
        this.errorMessage = "Name: " + this.firstName + " " + this.lastName + " with alias: " + this.alias + " is a duplicate record.";
      }
    }
  }

  public ArrayList<Participant> getNew_comm_member_rows() {
    return new_comm_member_rows;
  }

  public void setNew_comm_member_rows(ArrayList<Participant> new_comm_member_rows) {
    this.new_comm_member_rows = new_comm_member_rows;
  }

  public List buildCommunityMemberCreators() {
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List result = null;
    queryString = "SELECT part.firstName, part.lastName, us.userAlias, us.email, part.isActive  "
            + " FROM Users us "
            + " INNER join us.participant part "
            + " WHERE part.communityId = :cid and part.isCreator = 1";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", app.getCommunityId())
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("ERROR IN build Community Member list");
      Logger.getLogger(CommunityMembersBean.class.getName())
              .log(Level.SEVERE, "ERROR IN build Community Member list", ex);

    } finally {
      tx = null;
      hib = null;
    }
    return result;
  }

  private List getExistingMemberList() {

    System.out.println("called getExistingMemberList");
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List result = null;

    queryString = "FROM Participant where community_id = :cid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", app.getCommunityId())
              .list();
      tx.commit();

    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunityMembersBean.class.getName())
              .log(Level.SEVERE, "ERROR IN build Community Member list", ex);
      System.out.println("ERROR IN build Community Member list");

    } finally {
      tx = null;
      hib = null;
    }
    ArrayList<Participant> comm_member = new ArrayList<Participant>();
    Participant existing_cm;
    for (int i = 0; i < result.size(); i++) {
      Participant pp = (Participant) result.get(i);
      existing_cm = new Participant(pp.getParticipant_id(), pp.getCommunityId(), pp.getUserId(), "NA", pp.getFirstName(), pp.getMi(), pp.getLastName(), pp.getAlias(), pp.getIsActive(), pp.getEditable(), pp.getDateCreated(), pp.getDateUpdated(), i, pp.getGoodwill(), pp.getAge18OrMore(), pp.getEmailAlternative(), pp.getIsCreator());
      comm_member.add(existing_cm);
    }
    this.comm_member_rows = comm_member;
    return comm_member_rows;

  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMi() {
    return mi;
  }

  public void setMi(String mi) {
    this.mi = mi;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getIsActive() {
    return isActive;
  }

  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
  }

  public String getRemoteIp() {
    return remoteIp;
  }

  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  public String getCommunity_id() {
    return community_id;
  }

  public void setCommunity_id(String community_id) {
    this.community_id = community_id;
  }

  public Integer getEditable() {
    return editable;
  }

  public void setEditable(Integer editable) {
    this.editable = editable;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public Integer getIsCreator() {
    return isCreator;
  }

  public void setIsCreator(Integer isCreator) {
    this.isCreator = isCreator;
  }

  private List getNewMemberList() {
    this.errorMessage = null;
    Integer howMany = -1;
    ArrayList<Participant> comm_member = new ArrayList<Participant>();
    
    if (getHowManyRecords() == null ) {
      howMany = 1;
    } else {
      howMany = getHowManyRecords();
    }

    System.out.println("HOW MANY RECORDS");
    System.out.println(howMany);
    Participant new_cm;
    for (Integer i = 0; i < howMany; i++) {
      new_cm = new Participant(UUID.randomUUID().toString(), app.getCommunityId(), UUID.randomUUID().toString(), null, null, null, null, null, 1, 1, new Date(), new Date(), i, 0, 0, null, 0);
      comm_member.add(new_cm);
    }
    this.new_comm_member_rows = comm_member;
    return comm_member;
  }

  public List getNew_member() {
    return getNewMemberList();
  }

  /**
   * @param new_member the new_member to set
   */
  public void setNew_member(List new_member) {
    this.new_member = new_member;
  }

//  public void setNew_member(Participant[] cm) {
//    this.setNew_member(cm);
//  }
  public List getExisting_member() {
    return getExistingMemberList();
  }

  /**
   * @param existing_member the existing_member to set
   */
  public void setExisting_member(List existing_member) {
    this.existing_member = existing_member;
  }

//  public void setExisting_member(Participant[] existing_member) {
//    this.existing_member = existing_member;
//  }
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

  private Boolean checkForDuplicate() {

    System.out.println("Called checkforDuplicate");
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List result = null;
    queryString = " FROM Participant where community_id = :cid AND firstName = :fn AND lastName = :ln AND alias = :al";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", app.getCommunityId())
              .setParameter("fn", this.firstName)
              .setParameter("ln", this.lastName)
              .setParameter("al", this.alias)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger
              .getLogger(CommunityMembersBean.class
                      .getName()).log(Level.SEVERE, null, ex);

    } finally {
      tx = null;
      hib = null;
    }
    if (result.size() > 0) {
      result = null;
      return true;
    } else {
      result = null;
      return false;
    }
  }

  /**
   * @return the errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @param errorMessage the errorMessage to set
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @return the currentRow
   */
  public Integer getCurrentRow() {
    return currentRow;
  }

  /**
   * @param currentRow the currentRow to set
   */
  public void setCurrentRow(Integer currentRow) {
    this.currentRow = currentRow;
  }

}
