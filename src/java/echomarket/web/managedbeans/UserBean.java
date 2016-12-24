package echomarket.web.managedbeans;

import echomarket.hibernate.PasswordValidator;
import echomarket.hibernate.Purpose;
import echomarket.hibernate.Users;
import echomarket.hibernate.Map;
import echomarket.SendEmail.SendEmail;
import echomarket.hibernate.Communities;
import echomarket.hibernate.Participant;
import echomarket.hibernate.PasswordEncryptionService;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import static jdk.nashorn.internal.objects.NativeString.trim;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {

  @Inject
  ContactPreferenceBean cpbean;
  @Inject
  ParticipantBean pbean;
  @Inject
  ItemBean ibean;
  @Inject
  LenderItemConditionsBean licibean;
  @Inject
  LenderTransferBean ltribean;
  @Inject
  CommunitiesBean commbean;
  private String user_id;
  private String participant_id;
  //private String itemId;
  private Boolean acceptID;
  private Boolean partID;
  private Boolean cpId;
  private Boolean LITid;
  private Boolean LICid;
  private String username;
  private String firstName;
  private String lastName;
  private String userAlias;
  private String userType;
  private List<String> userTypeArray;
  private String password;
  private String email;
  private String resetCode;
  private String appEmail;
  private String userAction;
  private String registrationType;
  private String communityId;
  private String communityName;
  private Integer editable;

  private Integer roleId;
  private String action;
  private String pid;

  public String Logout() {
    setUserToNull();
    return "index?faces-redirect=true";
  }

  private void setUserToNull() {
    this.user_id = null;
    this.username = null;
    this.userAlias = null;
    this.userType = null;
    this.userTypeArray = null;
    this.password = null;
    this.email = null;
    this.resetCode = null;
    this.appEmail = null;
    this.roleId = null;
    this.communityId = null;
    this.communityName = null;
    this.pid = null;
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
    if (this.editable == 1) {
      return userType;
    } else {
      String hold_UT = userType;
      hold_UT = hold_UT.replace(";", " ");
      hold_UT = trim(hold_UT).replace(" ", "ing and ");
      return hold_UT;

    }
  }
  
  public String getPrettyUserType() {
  
      String hold_UT = userType;
      hold_UT = hold_UT.replace(";", " ");
      hold_UT = trim(hold_UT).replace(" ", "ing or ");
      return hold_UT.concat("ing");
 
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String registerCommunityMember() {
    Boolean updateEntity = false;
    String strGetUserThatCreatedMember = getUserThatCreatedMember();
    if (strGetUserThatCreatedMember != null) {
      updateEntity = getCreatorDetail(strGetUserThatCreatedMember);   /// sets values needed for registerUser
    }
    ///  Need to get the user_id the Community creator to learn whther borrower or lender
    String return_string = registerUser();
    if (this.user_id != null) {
      updateEntity = updateParticipantRecord();
    }
    return return_string;
  }

  protected Boolean getCreatorDetail(String uid) {
    List results = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Boolean return_result = false;
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Users "
              + " WHERE user_id  = :uid";
      results = hib.createQuery(queryString)
              .setParameter("uid", uid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on updateParticipantRecord in Hibernate session");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }

    if (results != null) {
      if (results.size() == 1) {
        Users user = (Users) results.get(0);
        this.userType = user.getUserType();
        this.communityName = user.getCommunityName();
        return_result = true;
      }
    }
    return return_result;
  }

  private String getUserThatCreatedMember() {
    List results = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    String return_result = null;
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Participant "
              + " WHERE participant_id  = :pid";
      results = hib.createQuery(queryString)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on updateParticipantRecord in Hibernate session");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }

    if (results != null) {
      if (results.size() == 1) {
        Participant part = (Participant) results.get(0);
        return_result = part.getUserId();
      }
    }
    return return_result;
  }

  private Boolean updateParticipantRecord() {
    List results = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Boolean return_result = false;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Participant "
              + " WHERE participant_id  = :pid";
      results = hib.createQuery(queryString)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on updateParticipantRecord in Hibernate session");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }

    if (results != null) {
      if (results.size() == 1) {
        Participant part = (Participant) results.get(0);
        part.setEmailAlternative(null);
        part.setFirstName(firstName);
        part.setLastName(lastName);
        part.setAlias(userAlias);
        part.setUserId(user_id);
        try {
          hib = hib_session();
          tx = hib.beginTransaction();
          hib.update(part);
          tx.commit();
          return_result = true;
        } catch (Exception ex) {
          tx.rollback();
          System.out.println("Error on updateParticipantRecord in updating");
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          tx = null;
          hib = null;
        }
      }
    }

    return return_result;
  }

  public String registerUser() {
    Boolean savedRecord = false;
    String returnString = null;
    savedRecord = checkForDuplicateEmail(this.email);
    if (savedRecord == false) {
      savedRecord = checkForDuplicateUserName();
    }

    if (savedRecord == true) {
      returnString = "user_registration";
      message(null, "EitherEmailOrUsernameExistOnDateBase", null);
    } else {
      returnString = registerValidUser();
    }

    return returnString;
  }

  private String registerValidUser() {

    Boolean savedRecord = false;
    Users create_record = null;
    String commName = null;
    Session hib = null;
    Transaction tx = null;
    String current_user_id = null;
    String fullname = this.username;
    String ac = null;  // holds reset_code
    commName = this.communityName;

    try {
      current_user_id = getId();
      this.user_id = current_user_id;
      // Role ID, indivdual = 0; community creator = 1; community member = 2
      if (commName != null) {
        create_record = new Users(current_user_id, this.username, commName, this.email, this.password, this.resetCode, this.userAlias, parseUserTypeArray(), 1);
      } else {
        create_record = new Users(current_user_id, this.username, null, this.email, this.password, this.resetCode, this.userAlias, parseUserTypeArray(), 0);
      }
    } catch (Exception ex) {
      // Need to learn error on creating new entity object
    }

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.save(create_record);
      tx.commit();
      ac = create_record.getResetCode();
      savedRecord = true;
    } catch (Exception ex) {
      tx.rollback();
      this.user_id = null;
      System.out.println("Error in registerUser");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      message(null, "LoginNotUpdated", new Object[]{this.username, this.email});
    } finally {
      tx = null;
      hib = null;
      create_record = null;
    }

    if (savedRecord == true) {
      savedRecord = sendActivationEmail(ac);
    }
    if (savedRecord == true) {
      if (commName == null) {
        message(null, "NewRegistration", new Object[]{fullname, this.email});
      } else {
        message(null, "NewCommunityRegistration", new Object[]{fullname, commName, this.email});
      }
    } else {
      message(null, "NewRegistrationFailed", new Object[]{fullname});
    }

    try {
      java.util.Map<String, String> params = null;
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      setAction(params.get("action"));
    } catch (Exception ex) {
    }

    setUserToNull();
    this.userAction = "login";
    return "index";
  }

  public String userIsWhichType() {

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

  public Boolean parseUserType(String whichType) {
    if (this.userType != null) {
      return this.userType.contains(whichType);
    } else {
      return false;
    }
  }

  private String[] buildTypeList() {

    List p_list = null;
    Session hib = null;
    Transaction tx = null;
    String[] results = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
    } catch (Exception ex) {
      System.out.println("Error at line 233 in UserBean");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    }

    String queryString = "from Purpose order by purpose_order";
    try {
      p_list = hib.createQuery(queryString).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error at line 250 in US Bean");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      hib = null;
      tx = null;
    }
    Integer p_list_size = p_list.size();
    results = new String[p_list_size];

    for (int i = 0; i < p_list_size; i++) {
      Purpose p_a = (Purpose) p_list.get(i);
      String tmp = p_a.getPurposeType();
      results[i] = tmp;
    }

    return results;
  }

  public String processActivation() {

    Boolean b_local_results = false;
    Boolean activation_success = true;
    List results = null;
    List accept_results = null;
    Integer memberCreator = -9;
    String return_string = null;
    String holdResetCode = getResetCode();  // because if process fails, I will null all user values
    results = findUserName();
    if (results != null) {
      if (results.size() == 0) {
        message(null, "UserNameNotFound", new Object[]{this.username});
        activation_success = false;
      } else if (results.size() == 1) {
        b_local_results = validateUserPassword(results);
        if (b_local_results == false) {
          message(null, "InvalidPassword", new Object[]{this.username});
          activation_success = false;
        } else if (b_local_results == true) {
          b_local_results = ActivateUser();    /// returns true if database was updated with reset_code
          if (b_local_results == false) {
            activation_success = false;
            message(null, "FailureToActivateUser", new Object[]{this.username});
          } else {
            // User is valid
            Users uu = (Users) results.get(0);  /// User result that has current user data
            memberCreator = uu.getRoleId();
//            if (memberCreator == 1) {
//              setCurrentUserCommunityId(uu.getUser_id());
//              setCommunityName(uu.getCommunityName());
//            }
            setRoleId(memberCreator);
            setEmail(uu.getEmail());
            setUserAlias(uu.getUserAlias());
            setUsername(this.username);
            setUserType(uu.getUserType());
            setUser_id(uu.getUser_id());
            results = null;
            message(null, "ActivateSuccessful", new Object[]{this.username});
          }
        }
      }
    } else {
      message(null, "UserNameNotFound", new Object[]{this.username});
      activation_success = false;
    }

    if (activation_success == true) {
      return_string = pbean.load_ud("-1");
    } else {
      setUserToNull();
      return_string = "activate_user.xhtml?reset_code=" + holdResetCode;
    }

    return return_string;
  }

  public String loginUser() {
    // debugging with password assignment
    this.password = "Emcgurty123!";

    Boolean b_local_results = false;
    Integer memberCreator = -9;
    List results = null;
    List accept_results = null;
    String return_string = null;
    results = findUserName();
    if (results != null) {

      if (results.size() == 0) {
        message(null, "UserNameNotFound", new Object[]{this.username});
        this.username = null;
        return_string = "index";
      } else if (results.size() == 1) {
        Boolean validPassword = validateUserPassword(results);
        if (validPassword == false) {
          message(null, "InvalidPassword", new Object[]{this.username});
          this.username = null;
          return_string = "index";
        } else if (validPassword == true) {
          results = verifyUserIsActivated();
          if (results != null) {
            if (results.size() == 0) {
              message(null, "UserHasNotActivated", new Object[]{this.username});
              this.username = null;
              return_string = "index";
            } else if (results.size() == 1) {
              Users uu1 = (Users) results.get(0);
              setUser_id(uu1.getUser_id());
              setUserType(uu1.getUserType());
              uu1 = null;
              accept_results = hasAcceptedAgreement();
              if (accept_results != null) {
                if (accept_results.size() == 0) {
                  accept_results = null;
                  message(null, "UserHasNotAccepted", new Object[]{this.username});
                  return_string = pbean.load_ud("-1");
                } else if (accept_results.size() == 1) {
                  accept_results = null;
                  this.acceptID = true;
                  message(null, "LogInSuccessful", new Object[]{this.username});
                  Users uu = (Users) results.get(0);  /// User result that has current user data
                  memberCreator = uu.getRoleId();
                  setRoleId(memberCreator);
                  if (memberCreator == 1) {
                    setCurrentUserCommunityId(uu.getUser_id());  // sets cid and pid
                    setCommunityName(uu.getCommunityName());
                  }

                  setEmail(uu.getEmail());
                  setUserAlias(uu.getUserAlias());
                  setUsername(this.username);
                  setUserType(uu.getUserType());
                  setUser_id(uu.getUser_id());
                  results = null;

                  switch (memberCreator) {
                    case 0:  // Individual not with a community
                      return_string = findWhatIsComplete();
                      break;
                    case 1:  // A community creator
                      return_string = findWhatIsCommunityComplete();
                      if (return_string.isEmpty() == true) {
                        return_string = findWhatIsComplete();  // hold needs to be written
                      }
                      break;
                    case 2:  // A member of a community
                      return_string = findWhatIsComplete(); // hold needs to be written
                      break;
                    default:
                      break;
                  }
                }
              } else {
                message(null, "UserHasNotAccepted", new Object[]{this.username});
                return_string = pbean.load_ud("-1");
              }
            }

          } else {
            message(null, "UserHasNotActivated", new Object[]{this.username});
            this.username = null;
            return_string = "index";
          }

        } else {  // Closes null findUserName
          message(null, "UserNameNotFound", new Object[]{this.username});
          this.username = null;
          return_string = "index";
        }
      }

    }
    return return_string;
  }

  private String findWhatIsComplete() {

    String return_string = "";
    String pid = null;
    List hasCompleteLIT = null;
    List hasCompleteLIC = null;
    String return_null = "";
    List partList = completeParticipantRecord();
    if (partList != null) {
      if (partList.size() == 1) {
        Participant part = (Participant) partList.get(0);
        pid = part.getParticipant_id();
        this.participant_id = pid;
        this.partID = true;
      } else {
        this.editable = 0;
        this.partID = true;
        return_string = pbean.load_ud(this.user_id);
      }

      if (return_string.isEmpty() == true) {
        List completCP = completeContactPreferences(this.participant_id);
        Integer hs = completCP.size();
        if (hs == 0) {
          setEditable(0);
          this.cpId = true;
          return_string = cpbean.load_ud(pid);
        } else {
          this.editable = 0;
          this.cpId = true;
          switch (this.userIsWhichType()) {
            case "borrow":
              setAction("current");
              return_string = ibean.load_ud("borrow", return_null);
              break;
            case "lend":
              hasCompleteLIT = completeLIT(this.participant_id);
              if (hasCompleteLIT.size() == 0) {
                this.LITid = true;
                return_string = ltribean.load_ud(this.participant_id);
              } else {
                this.LITid = true;
                hasCompleteLIC = completeLIC(this.participant_id);
                if (hasCompleteLIC.size() == 0) {
                  this.LICid = true;
                  return_string = licibean.load_ud(this.participant_id);
                } else {
                  setAction("current");
                  this.LICid = true;
                  return_string = ibean.load_ud("lend", return_null);
                }
              }
              break;
            case "both":
              hasCompleteLIT = completeLIT(this.participant_id);
              if (hasCompleteLIT.size() == 0) {
                this.LITid = true;
                return_string = ltribean.load_ud(this.participant_id);
              } else {
                this.LITid = true;
                hasCompleteLIC = completeLIC(this.participant_id);
                if (hasCompleteLIC.size() == 0) {
                  this.LICid = true;
                  return_string = licibean.load_ud(this.participant_id);
                } else {
                  setAction("current");
                  this.LICid = true;
                  return_string = ibean.load_ud("both", return_null);
                }
                break;
              }
          }
        }
      }
    } else {
      message(null, "ParticipantNotFound", null);
      return_string = "index";
    }

    return return_string;
  }

  private String findWhatIsCommunityComplete() {

    String return_string = "";
    String pid = null;
    Participant part = null;
    List partList = completeParticipantRecord();
    if (partList != null) {
      if (partList.size() == 1) {
        part = (Participant) partList.get(0);
        pid = part.getParticipant_id();
        setParticipant_id(pid);
        setPartID(true);
        setCommunityId(part.getCommunityId());
      } else {
        setEditable(0);
        return_string = pbean.load_ud(this.user_id);
      }

      if (return_string.isEmpty() == true) {
        List completCD = completeCommunityDetail();
        Integer hs = completCD.size();
        if (hs == 0) {
          return_string = commbean.load_community_detail();
        } else {
          ////  Check for Community members??
          ///   Or return to a Community Menu ???
        }
      }
    } else {
      message(null, "ParticpantNotFound", null);
      return_string = "index";
    }
    return return_string;
  }

  private void setCurrentUserCommunityId(String uid) {
    List results = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Participant "
              + " WHERE user_id = :uid";
      results = hib.createQuery(queryString)
              .setParameter("uid", uid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on getCurrentUserCommunityId");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

    } finally {
      tx = null;
      hib = null;
    }

    if (results != null) {
      if (results.size() == 1) {
        Participant getPID = (Participant) results.get(0);
        setParticipant_id(getPID.getParticipant_id());
        setCommunityId(getPID.getCommunityId());
        results = null;
        getPID = null;
      }
    }

  }

  private List completeCommunityDetail() {
    List results = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Communities "
              + " WHERE community_id = :cid";
      results = hib.createQuery(queryString)
              .setParameter("cid", this.communityId)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeCommunityDetail");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;
  }

  private List hasAcceptedAgreement() {
    List results = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Participant "
              + " WHERE user_id  = :uid AND goodwill = 1 AND age18OrMore = 1";
      results = hib.createQuery(queryString)
              .setParameter("uid", this.user_id)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on hasAcceptedAgreement");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;
  }

  private Boolean validateUserPassword(List auth_result) {

    Users users_Array = null;
    users_Array = new Users();
    Boolean getp = false;
    users_Array = (Users) auth_result.get(0);
    byte[] salt = users_Array.getSalt();
    byte[] crypted_password = users_Array.getCryptedPassword();
    PasswordEncryptionService pes = new PasswordEncryptionService();
    try {
      getp = pes.authenticate(this.password, crypted_password, salt);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeySpecException ex) {
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      salt = null;
      crypted_password = null;
      pes = null;
    }

    return getp;
  }

  private List findUserName() {

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

  private List verifyUserIsActivated() {

    Session hib = null;
    Transaction tx = null;
    List results = null;
    String queryString = "from Users where username = :un  and activated_at != null";

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery(queryString).setParameter("un", this.username).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error at in verifyUserIsActivated");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      hib = null;
      tx = null;
    }

    return results;
  }

  private Boolean ActivateUser() {

    List results = null;
    Date ndate = new Date();
    Session session = null;
    Transaction tx = null;
    String queryString = null;
    Boolean resultSuccess = false;
    Users users_Array = null;

    try {
      queryString = "from Users where reset_code = :rc";
      session = hib_session();
      tx = session.beginTransaction();
      results = session.createQuery(queryString).setParameter("rc", getResetCode()).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      session = null;
      tx = null;
    }

    if (results != null) {
      if (results.size() == 1) {
        try {
          session = hib_session();
          tx = session.beginTransaction();
          users_Array = (Users) results.get(0);
          users_Array.setActivatedAt(ndate);
          users_Array.setResetCode(null);
          session.update(users_Array);
          tx.commit();
          resultSuccess = true;
        } catch (Exception ex) {
          tx.rollback();
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          users_Array = null;
          session = null;
          tx = null;
        }
      }
    }

    return resultSuccess;
  }

  public String managePasswordChange() {
    Session hib = null;
    Transaction tx = null;
    Users userArray = null;
    List results = ValidateUserNameResetCode();
    if (results != null) {

      try {
        userArray = (Users) results.get(0);
        hib = hib_session();
        tx = hib.beginTransaction();
        Users uu = (Users) hib.get(Users.class, userArray.getUser_id());
        uu.setResetCode(null);
        PasswordEncryptionService pes = new PasswordEncryptionService();
        try {
          uu.setCryptedPassword(pes.getEncryptedPassword(this.password, uu.getSalt()));

        } catch (NoSuchAlgorithmException ex) {
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

        } catch (InvalidKeySpecException ex) {
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        tx.commit();
        message(null, "PasswordChangeSuccess", new Object[]{});
      } catch (Exception ex) {
        tx.rollback();
        Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        message(null, "PasswordChangeSuccessFailed", new Object[]{});
      } finally {
        userArray = null;
        tx = null;
        hib = null;
        results = null;
      }
    }
    setUserToNull();
    this.userAction = "login";
    return "index";
  }

  public String forgotUserPassword() {
    String buildReset_Code = null;
    Users userArray = null;
    Session hib = null;
    Transaction tx = null;

    List results = ValidateEmail(email);
    if (results != null) {
      try {
        if (results.size() == 1) {
          userArray = (Users) results.get(0);
          hib = hib_session();
          tx = hib.beginTransaction();
          Users uu = (Users) hib.get(Users.class, userArray.getUser_id());
          buildReset_Code = uu.BuildRandomValue();
          uu.setResetCode(buildReset_Code);
          tx.commit();
        }
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Tx commit failed..");
        Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        tx = null;
        hib = null;
      }
      String[] appEmail = getApplicationEmail();
      /// if String not empty, need to code
      try {
        SendEmail se = new SendEmail("forgotPassword", userArray.getUsername(), null, email, appEmail[0], appEmail[1], userArray.getUser_id(), buildReset_Code);
        se = null;
      } catch (Exception ex) {
        System.out.println("Send Mail Failed");
        Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      }
      message(null, "ForgotUserPasswordSuccess", new Object[]{email});
      setUserToNull();
      this.userAction = "login";
      return "index";
    } else {
      message(null, "ForgotUserPasswordFailed", new Object[]{email});
      setUserToNull();
      this.userAction = "login";
      return "index";
    }
  }

  public String forgotUserName() {

    String results = ValidateEmailAndPassword(getEmail(), getPassword());
    if (results != null) {
      message(null, "FoundUsername", new Object[]{results});
    } else {
      message(null, "UserNameNotFound", null);
    }

    results = null;
    setUserToNull();
    this.userAction = "login";

    return "index";

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

  private String ValidateEmailAndPassword(String em, String pw) {

    Session hib = null;
    Transaction tx = null;
    List results = null;
    // Validate email and password
    String return_user_name = null;
    Boolean auth_pw = false;
    Boolean auth_em = false;
    String queryString = "from Users where email  = :em";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery(queryString).setParameter("em", em).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    } finally {
      hib = null;
      tx = null;
    }
    if (results != null) {
      if (results.size() == 1) {
        auth_em = true;
        Users users_Array = (Users) results.get(0);
        return_user_name = users_Array.getUsername();
        byte[] salt = users_Array.getSalt();
        byte[] crypted_password = users_Array.getCryptedPassword();

        PasswordEncryptionService pes = new PasswordEncryptionService();
        try {
          auth_pw = pes.authenticate(pw, crypted_password, salt);

        } catch (NoSuchAlgorithmException ex) {
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);

        } catch (InvalidKeySpecException ex) {
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }

    if (auth_pw == true && auth_em == true) {
      return return_user_name;
    } else {
      return null;
    }
  }

  private List ValidateEmail(String email) {
    Session hib = null;
    Transaction tx = null;
    List results = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "from Users where email  = :em";
      results = hib.createQuery(queryString).setParameter("em", email).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    }

    if (results.size() == 1) {
      return results;
    } else {
      return null;
    }
  }

  private List ValidateUserNameResetCode() {
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List results = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "from Users where username = :un and reset_code = :rc";
      results = hib.createQuery(queryString).setParameter("un", this.username).setParameter("rc", getResetCode()).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    } finally {
      hib = null;
      tx = null;
    }
    if (results.size() == 1) {
      return results;
    } else {
      return null;
    }
  }

  public void getMemberAlias(ComponentSystemEvent event) {
    UIComponent components = event.getComponent();
    UIInput uiInputAlias = (UIInput) components.findComponent("userAlias");
    String reg_alias = uiInputAlias.getLocalValue() == null ? "" : uiInputAlias.getLocalValue().toString();
    String aliasId = uiInputAlias.getClientId();
    if (reg_alias != null) {
      if (checkForDuplicateAlias(reg_alias) == true) {
        FacesMessage msg = new FacesMessage("Alias already belongs to an EchoMarket participant");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        context().addMessage(aliasId, msg);
        context().renderResponse();
      } else {
        return;
      }
    } else {
      // Required true do its job 
      return;
    }
  }

  public void getMemberEmail(ComponentSystemEvent event) {

    UIComponent components = event.getComponent();
    UIInput uiInputEmail = (UIInput) components.findComponent("email");
    String reg_email = uiInputEmail.getLocalValue() == null ? "" : uiInputEmail.getLocalValue().toString();
    String emailId = uiInputEmail.getClientId();
    if (reg_email != null) {
      if (checkForDuplicateEmail(reg_email) == true) {
        FacesMessage msg = new FacesMessage("Email address already belongs to an EchoMarket participant");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        context().addMessage(emailId, msg);
        context().renderResponse();
      } else {
        return;
      }
    } else {
      // Required true do its job 
      return;
    }
  }

  public void validatePassword(ComponentSystemEvent event) {

    UIComponent components = event.getComponent();

    // get password
    UIInput uiInputPassword = (UIInput) components.findComponent("password");

    String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
    String passwordId = uiInputPassword.getClientId();

    // get confirm password
    UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
    String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
            : uiInputConfirmPassword.getLocalValue().toString();

    // Let required="true" do its job.
    if (password.isEmpty() || confirmPassword.isEmpty()) {
      return;
    }
    if (!password.equals(confirmPassword)) {
      ///send a message
      FacesMessage msg = new FacesMessage("Password must match confirm password");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      context().addMessage(passwordId, msg);
      context().renderResponse();
//      message(null, "PasswordsDoNotMatch", null);
    } else {
      PasswordValidator pv = new PasswordValidator();
      Boolean is_valid = pv.validate(password);
      if (!is_valid) {
        FacesMessage msg = new FacesMessage("Password does not have required values");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        context().addMessage(passwordId, msg);
        context().renderResponse();
//        message(null, "PasswordMustContain", null);

      } else {
        return;
      }
    }
  }

  /**
   * @return the userTypeArray
   */
  public List<String> getUserTypeArray() {
    if (this.userType != null) {
      ArrayList<String> uta = new ArrayList<String>(Arrays.asList(this.userType.split(";")));
      return uta;
    } else {
      return userTypeArray;
    }
  }

  /**
   * @param userTypeArray the userTypeArray to set
   */
  public void setUserTypeArray(List<String> userTypeArray) {
    this.userTypeArray = userTypeArray;
  }

  /**
   * @return the resetCode
   */
  public String getResetCode() {
    return resetCode;
  }

  /**
   * @param resetCode the resetCode to set
   */
  public void setResetCode(String resetCode) {
    this.resetCode = resetCode;
  }

  /**
   * @return the appEmail
   */
  public String getAppEmail() {
    return appEmail;
  }

  /**
   * @param appEmail the appEmail to set
   */
  public void setAppEmail(String appEmail) {
    this.appEmail = appEmail;
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
   * @return the registrationType
   */
  public String getRegistrationType() {
    return registrationType;
  }

  /**
   * @param registrationType the registrationType to set
   */
  public void setRegistrationType(String registrationType) {
    this.registrationType = registrationType;
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

  public String load_user_registration() {
    this.roleId = 0;
    return "user_registration.xhtml?faces-redirect=true";
  }

  public String load_community_registration() {
    this.roleId = 1;
    return "community_registration.xhtml?faces-redirect=true";
  }

  public List completeParticipantRecord() {

    List results = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from Participant WHERE user_id = :uid AND firstName != null")
              .setParameter("uid", this.user_id)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeParticipantRecord");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;

  }

  public List completeLIC_LIT(String PID) {
    List results = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("SELECT part from Participant part INNER JOIN part.lenderItemConditions INNER JOIN part.lenderTransfer WHERE part.participant_id = :pid")
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeLIC");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;

  }

  private List completeLIC(String pid) {

    List results = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from LenderItemConditions WHERE participant_id = :pid")
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeLIC");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;

  }

  private List completeLIT(String pid) {

    List results = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from LenderTransfer WHERE participant_id = :pid")
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeLIT");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;

  }

  public List completeContactPreferences(String pid) {

    List results = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from ContactPreference WHERE participant_id = :pid")
              .setParameter("pid", pid)
              .list();
      tx.commit();

    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on completeContactPreferences");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return results;

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

  public String updateUserLogin() {

    Boolean updateSuccess = false;
    String resetCodeString = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      Users uu = new Users(this.user_id, this.username, this.communityName, this.email, this.password, null, this.userAlias, parseUserTypeArray(), this.getRoleId());
      hib.update(uu);
      tx.commit();
      resetCodeString = uu.getResetCode();
      updateSuccess = true;
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println("Error on Update User");
      message(null, "LoginNotUpdated", new Object[]{this.username, this.email});
    } finally {
      tx = null;
      hib = null;
    }
    if (updateSuccess == true) {
      sendActivationEmail(resetCodeString);
      message(null, "LoginUpdated", new Object[]{this.username, this.email});
    } else {
      message(null, "LoginUpdateFailed", new Object[]{this.username, this.email});

    }

    return "index";

  }

  public String load_ud(Integer which) {

    this.editable = which;
    if (user_id == null) {
      message(null, "LoginInRequiredToReviseUserInformation", null);
      return "index";
    } else {
      return "user_login_update";
    }
  }

  private String parseUserTypeArray() {

    String hold_userTypeBuild = "";
    for (String userTypeArray1 : getUserTypeArray()) {
      hold_userTypeBuild = hold_userTypeBuild + userTypeArray1 + ";";
    }
    return hold_userTypeBuild;

  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  private Boolean sendActivationEmail(String resetCodeString) {

    String[] getMap = new String[2];
    getMap = getApplicationEmail();
    Boolean return_string = false;

    try {
      if (this.communityName == null) {
        SendEmail se = new SendEmail("registration", this.username, this.userAlias, this.email, getMap[0], getMap[1], this.password, resetCodeString);
        se = null;
      } else {
        SendEmail se = new SendEmail("Community: " + this.communityName, this.username, this.userAlias, this.email, getMap[0], getMap[1], this.password, resetCodeString);
        se = null;
      }
      return_string = true;
    } catch (Exception ex) {
      System.out.println("Send Mail Failed");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      getMap = null;
    }

    return return_string;
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

//  public String getItemId() {
//    return itemId;
//  }
//
//  public void setItemId(String itemId) {
//    this.itemId = itemId;
//  }

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

  private Boolean checkForDuplicateAlias(String alias) {

    Session hib = null;
    Transaction tx = null;
    List results = null;
    Boolean foundAlias = false;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from Users where userAlias = :alias").setParameter("alias", alias).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on checkForDuplicateEmail");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }
    if (results != null) {
      if (results.size() > 0) {
        foundAlias = true;
      }
    }
    results = null;
    return foundAlias;
  }

  private Boolean checkForDuplicateEmail(String em) {
    Session hib = null;
    Transaction tx = null;
    List results = null;
    Boolean foundEmail = false;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from Users where email = :email").setParameter("email", em).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on checkForDuplicateEmail");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }
    if (results != null) {
      if (results.size() > 0) {
        foundEmail = true;
      }
    }
    results = null;
    return foundEmail;
  }

  private Boolean checkForDuplicateUserName() {
    String currentUserName = this.username;
    Session hib = null;
    Transaction tx = null;
    List results = null;
    Boolean foundUserName = false;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from Users where username = :username").setParameter("username", currentUserName).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in checkForDuplicateUserName");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }
    if (results != null) {
      if (results.size() > 0) {
        foundUserName = true;
      }
    }
    results = null;
    return foundUserName;
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

  private String getMemberInformation(String pid) {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Participant pt = null;
    String returnCID = null;
    queryString = "FROM Participant where participant_id = :pid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    } finally {
      tx = null;
      hib = null;

    }
    if (result != null) {
      if (result.size() == 1) {
        pt = (Participant) result.get(0);
        returnCID = pt.getCommunityId();
        this.communityId = returnCID;
        this.firstName = pt.getFirstName();
        this.lastName = pt.getLastName();
        this.userAlias = pt.getAlias();
        this.email = pt.getEmailAlternative();
        this.participant_id = pid;
      }
    }
    return returnCID;
  }

  public String getCommunityName(String pid) {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Communities pt = null;
    String getCID = getMemberInformation(pid);
    queryString = "FROM Communities where community_id = :cid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", getCID)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    } finally {
      tx = null;
      hib = null;

    }

    if (result != null) {
      if (result.size() == 1) {
        pt = (Communities) result.get(0);
        queryString = pt.getCommunityName();
      }
    }
    result = null;
    pt = null;
    return queryString;

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

}
