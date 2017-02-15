package echomarket.web.managedbeans;

import echomarket.hibernate.Map;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("app")
@SessionScoped
public class ApplicationParams extends AppAbstract implements Serializable {

  private static final long serialVersionUID = 1L;

  private String user_id;
  private String participant_id;
  private String itemId;
  private Boolean acceptID;
  private Boolean creatorDetailID;
  private Boolean comDetailID;
  private Boolean comMemberDetailID;
  private Boolean partID;
  private Boolean cpId;
  private Boolean LITid;
  private Boolean LICid;
  private String username;
  private String firstName;
  private String lastName;
  private String userAlias;
  private String userType;
  private String email;
  private String userAction;
  private String communityId;
  private String communityName;
  private Integer editable;
  private Integer roleId;
  private String action;
  private String pid;
  private String uid;    /// user_id for member registration url

// emm 1.8
  public ApplicationParams() {
    user_id = null;
    participant_id = null;
    itemId = null;
    acceptID = false;
    creatorDetailID = false;
    comDetailID = false;
    comMemberDetailID = false;
    partID = false;
    cpId = false;
    LITid = false;
    LICid = false;
    username = null;
    firstName = "";
    lastName = "";
    userAlias = "";
    userType = "";
    email = "";
    userAction = "";
    communityId = "";
    communityName = "";
    editable = -99;
    roleId = -9;
    action = "";
    pid = "";
    uid = "";    /// user_id for member registration url
  }

  public String Logout() {
    setUserToNull();
    return "index";
  }

  protected void setUserToNull() {
    this.user_id = null;
    this.username = null;
    this.userAlias = null;
    this.userType = null;
    this.email = null;
    this.roleId = null;
    this.communityId = null;
    this.communityName = null;
    this.pid = null;
    this.editable = 0;

  }

  protected void completeContactPreferences(String pid) {

    List results = null;
    Session hib = null;
    Transaction tx = null;
    String currentItem = "";
    if (getItemId() != null) {
      currentItem = getItemId();
    }
    this.cpId = false;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      if (currentItem.isEmpty() == true) {
        results = hib.createQuery("from ContactPreference WHERE participant_id = :pid GROUP BY participant_id ORDER BY participant_id ")
                .setParameter("pid", pid)
                .setMaxResults(1)
                .list();
      } else {
        results = hib.createQuery("from ContactPreference WHERE participant_id = :pid and itemId = :iid GROUP BY participant_id, itemId")
                .setParameter("pid", pid)
                .setParameter("iid", currentItem)
                .setMaxResults(1)
                .list();
      }

      tx.commit();

    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeContactPreferences");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

    } finally {
      tx = null;
      hib = null;
      currentItem = null;
    }
    if (results != null) {
      if (results.size() == 1) {
        this.cpId = true;
        results = null;
      }
    }

  }

  protected String userIsWhichType() {

    String returnType = null;
    if ((this.userType.contains("lend") == true) && (this.userType.contains("borrow") == false)) {
      returnType = "lend";
    } else if ((this.userType.contains("borrow") == true) && (this.userType.contains("lend") == false)) {
      returnType = "borrow";
    } else if ((this.userType.contains("borrow") == true) && (this.userType.contains("lend") == true)) {
      returnType = "both";
    }
    return returnType;
  }

  protected void completeLIC(String pid) {

    List results = null;
    Session hib = null;
    Transaction tx = null;
    String currentItem = "";
    if (getItemId() != null) {
      currentItem = getItemId();
    }
    this.LICid = false;
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      if (currentItem.isEmpty() == true) {
        results = hib.createQuery("from LenderItemConditions WHERE participant_id = :pid GROUP BY participant_id, dateCreated")
                .setParameter("pid", pid)
                .setMaxResults(1)
                .list();
      } else {
        results = hib.createQuery("from LenderItemConditions WHERE participant_id = :pid and itemId = :iid GROUP BY participant_id, itemId, dateCreated")
                .setParameter("pid", pid)
                .setParameter("iid", currentItem)
                .setMaxResults(1)
                .list();
      }
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeLIC");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

    } finally {
      tx = null;
      hib = null;
      currentItem = null;
    }
    if (results != null) {
      if (results.size() == 1) {
        this.LICid = true;
        results = null;
      }
    }

  }

  protected void completeLIT(String pid) {

    List results = null;
    Session hib = null;
    Transaction tx = null;
    String currentItem = "";
    if (getItemId() != null) {
      currentItem = getItemId();
    }
    this.LITid = false;
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      if (currentItem.isEmpty() == true) {
        results = hib.createQuery("from LenderTransfer WHERE participant_id = :pid GROUP BY participant_id, dateCreated")
                .setParameter("pid", pid)
                .setMaxResults(1)
                .list();
      } else {
        results = hib.createQuery("from LenderTransfer WHERE participant_id = :pid and itemId = :iid GROUP BY participant_id, itemId, dateCreated")
                .setParameter("pid", pid)
                .setParameter("iid", currentItem)
                .setMaxResults(1)
                .list();
      }

      tx.commit();

    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeLIT");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
      currentItem = null;
    }
    if (results != null) {
      if (results.size() == 1) {
        this.LITid = true;
        results = null;
      }
    }

  }

  protected List findUserName() {

    Session hib = null;
    Transaction tx = null;
    List results = null;
    String queryString = "from Users where username = :un";

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery(queryString).setParameter("un", this.username).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error at in findUserName");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      hib = null;
      tx = null;
    }

    return results;
  }

  protected String[] getApplicationEmail() {

    Session hib = null;
    Transaction tx = null;
    List results = null;
    String[] holdMap = new String[2];
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from Map WHERE key_text like '%gmail.com'").list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in getApplicationEmail");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      hib = null;
      tx = null;
    }
    if (results != null) {
      if (results.size() == 1) {
        Map map = (Map) results.get(0);
        holdMap[0] = map.getKeyText();
        holdMap[1] = map.getValueText();
      }
    }
    return holdMap;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserAlias() {
    return userAlias;
  }

  public void setUserAlias(String ua) {
    this.userAlias = ua;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String ut) {
    this.userType = ut;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the user_id
   */
  public String getUser_id() {
    return user_id;
  }

  /**
   * @param user_id the user_id to set
   */
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  /**
   * @return the userAction
   */
  public String getUserAction() {
    if (userAction == null) {
      this.userAction = "dashboard";
      return userAction;
    } else {
      return userAction;
    }
  }

  /**
   * @param userAction the userAction to set
   */
  public void setUserAction(String userAction) {
    this.userAction = userAction;
  }

  /**
   * @return the communityName
   */
  public String getCommunityName() {
    return communityName;
  }

  /**
   * @param communityName the communityName to set
   */
  public void setCommunityName(String communityName) {
    this.communityName = communityName;
  }

  public String load_login() {
    this.username = null;
    this.userAction = "login";
    return "index";
  }

  public String load_forgotUserPassword() {
    this.username = null;
    this.userAction = "forgotUserPassword";
    return "index?faces-redirect=true";
  }

  public String load_forgotUsername() {
    this.username = null;
    this.userAction = "forgotUsername";
    return "index?faces-redirect=true";
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

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
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
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * @param action the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * @return the community_id
   */
  public String getCommunityId() {
    return communityId;
  }

  /**
   * @param community_id the community_id to set
   */
  public void setCommunityId(String communityId) {
    this.communityId = communityId;
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
   * @return the pid
   */
  public String getPid() {
    return pid;
  }

  /**
   * @param pid the pid to set
   */
  public void setPid(String pid) {
    this.pid = pid;
  }

  /**
   * @return the acceptID
   */
  public Boolean getAcceptID() {
    return acceptID;
  }

  /**
   * @param acceptID the acceptID to set
   */
  public void setAcceptID(Boolean acceptID) {
    this.acceptID = acceptID;
  }

  public Boolean getCpId() {
    return cpId;
  }

  public void setCpId(Boolean cpId) {
    this.cpId = cpId;
  }

  /**
   * @return the LITid
   */
  public Boolean getLITid() {
    return LITid;
  }

  /**
   * @param LITid the LITid to set
   */
  public void setLITid(Boolean LITid) {
    this.LITid = LITid;
  }

  /**
   * @return the LICid
   */
  public Boolean getLICid() {
    return LICid;
  }

  /**
   * @param LICid the LICid to set
   */
  public void setLICid(Boolean LICid) {
    this.LICid = LICid;
  }

  /**
   * @return the partID
   */
  public Boolean getPartID() {
    return partID;
  }

  /**
   * @param partID the partID to set
   */
  public void setPartID(Boolean partID) {
    this.partID = partID;
  }

  /**
   * @return the creatorDetailID
   */
  public Boolean getCreatorDetailID() {
    return creatorDetailID;
  }

  /**
   * @param creatorDetailID the creatorDetailID to set
   */
  public void setCreatorDetailID(Boolean creatorDetailID) {
    this.creatorDetailID = creatorDetailID;
  }

  /**
   * @return the comDetailID
   */
  public Boolean getComDetailID() {
    return comDetailID;
  }

  /**
   * @param comDetailID the comDetailID to set
   */
  public void setComDetailID(Boolean comDetailID) {
    this.comDetailID = comDetailID;
  }

  /**
   * @return the itemId
   */
  public String getItemId() {
    return itemId;
  }

  /**
   * @param itemId the itemId to set
   */
  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  /**
   * @return the comMemberDetailID
   */
  public Boolean getComMemberDetailID() {
    return comMemberDetailID;
  }

  /**
   * @param comMemberDetailID the comMemberDetailID to set
   */
  public void setComMemberDetailID(Boolean comMemberDetailID) {
    this.comMemberDetailID = comMemberDetailID;
  }

}
