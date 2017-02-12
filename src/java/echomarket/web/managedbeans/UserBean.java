package echomarket.web.managedbeans;

import echomarket.hibernate.PasswordValidator;
//import echomarket.hibernate.Purpose;
import echomarket.hibernate.Users;
//import echomarket.hibernate.Map;
import echomarket.SendEmail.SendEmail;
import echomarket.hibernate.Communities;
import echomarket.hibernate.Participant;
import echomarket.hibernate.PasswordEncryptionService;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;
  @Inject
  ParticipantBean pbean;
  @Inject
  ContactPreferenceBean cpbean;
  @Inject
  ItemBean ibean;
  @Inject
  LenderTransferBean ltribean;
  @Inject
  LenderItemConditionsBean lcbean;
  @Inject
  CommunitiesBean commbean;
  @Inject
  CommunityMembersBean cmbean;

  private String user_id;
  private String username;
  private String firstName;
  private String lastName;
  private String userAlias;
  private String userType;
  private List<String> userTypeArray;   // hold checkbox selections of uer type: borow or lend
  private String password;
  private String email;
  private String resetCode;
  private String appEmail;
  private String communityId;
  private String communityName;
  private String isCompleteString;

  private Integer roleId;
  private String action;
  private String pid;
  private String uid;    /// user_id for member registration url

// emm 1.8
  public UserBean() {

  }

  public String clearItemId() {
    ibean.setItemId("");
    app.setItemId("");
    return pbean.load_ud(app.getParticipant_id());

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

  public String getPrettyUserType() {

    String return_string = "";

    if (app.getUserType() != null) {

      switch (app.getUserType()) {
        case "both":
          return_string = "Borrowing and Lending";
          break;
        case "borrow":
          return_string = "Borrowing";
          break;
        case "lend":
          return_string = "Lending";
          break;
      }
    }
    return return_string;
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

  public String registerCommunityMember() {   /// Need to test this...
    Boolean updateEntity = false;
    String strGetUserThatCreatedMember = getUserThatCreatedMember();  // return user_id of participant
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

  protected Boolean getCreatorDetail(String uid) {   /// Not sure about this...
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
      System.out.println("Error on getCreatorDetail");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }

    if (results != null) {
      if (results.size() == 1) {
        Users user = (Users) results.get(0);
        //   this.userType = user.getUserType();
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
              .setParameter("pid", app.getParticipant_id())
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
              .setParameter("pid", app.getParticipant_id())
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

  private String registerValidUser() {    // been reviewed

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
      String hold_ut = "";
      for (String s : getUserTypeArray()) {
        hold_ut += s + ",";
      }
      hold_ut = hold_ut.substring(0, hold_ut.length() - 1);
      if (commName != null) {

        create_record = new Users(current_user_id, this.username, commName, this.email, this.password, this.resetCode, this.userAlias, hold_ut, 1);
      } else {
        create_record = new Users(current_user_id, this.username, null, this.email, this.password, this.resetCode, this.userAlias, hold_ut, 0);
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
      savedRecord = sendActivationEmail(ac, 0);
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

    app.setUserToNull();
    app.setUserAction("login");
    return "index";
  }

  public String processActivation() {

    Boolean b_local_results = false;
    Boolean activation_success = true;
    List results = null;
    List accept_results = null;
    Integer memberCreator = -9;
    String return_string = null;
    String holdResetCode = getResetCode();  // because if process fails, I will null all user values
    app.setUsername(this.username);
    results = app.findUserName();
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
            app.setRoleId(memberCreator);
            app.setEmail(uu.getEmail());
            app.setUserAlias(uu.getUserAlias());
            app.setUsername(this.username);
            app.setUserType(uu.getUserType());
            app.setUserType(app.userIsWhichType());
            app.setUser_id(uu.getUser_id());
            /// emm 125
            app.setPartID(false);
            app.setCpId(false);
            app.setLICid(false);
            app.setLITid(false);
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
      app.setUserToNull();
      return_string = "activate_user.xhtml?reset_code=" + holdResetCode;
    }

    return return_string;
  }

  public String processMemberActivation() {   // no app canges here becuase user has not logged in yet

    Boolean savedRecord = false;
    Users create_record = null;
    String commName = null;
    Session hib = null;
    Transaction tx = null;
    String current_user_id = null;
    String queryString = null;
    String returnString = null;
    List results = null;
    commName = this.communityName;

    savedRecord = checkForDuplicateEmail(this.email);
    if (savedRecord == false) {
      savedRecord = checkForDuplicateUserName();
    }

    if (savedRecord == false) {
      try {
        queryString = " FROM Users user WHERE user.user_id = :uid";
        hib = hib_session();
        tx = hib.beginTransaction();
        results = hib.createQuery(queryString)
                .setParameter("uid", this.uid)
                .setMaxResults(1)
                .list();
        tx.commit();
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Error in updatingNewMemberParticpant, get UserType");
        Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        hib = null;
        tx = null;
      }

      if (results != null) {
        if (results.size() == 1) {
          Users uu = (Users) results.get(0);
          this.userType = uu.getUserType();   // from database, not gui
          savedRecord = true;
          results = null;
        }
      }

      if (savedRecord == true) {
        try {
          queryString = " FROM Participant part WHERE part.participant_id = :pid";
          hib = hib_session();
          tx = hib.beginTransaction();
          results = hib.createQuery(queryString)
                  .setParameter("pid", this.pid)
                  .list();
          try {
            tx.commit();
          } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in updatingNewMemberParticpant, getting current participant record");
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            savedRecord = false;
          } finally {
            hib = null;
            tx = null;

          }

          if (results != null) {
            if (results.size() == 1) {
              hib = hib_session();
              tx = hib.beginTransaction();
              Participant part = (Participant) results.get(0);
              part.setFirstName(this.firstName);
              part.setLastName(this.lastName);
              part.setAlias(this.userAlias);
              part.setEmailAlternative(null);
              part.setIsCreator(0);
              hib.update(part);
              tx.commit();
              savedRecord = true;
            }

          }
        } catch (Exception ex) {
          tx.rollback();
          System.out.println("Error in updatingNewMemberParticpant");
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          hib = null;
          tx = null;
        }

      }
      if (savedRecord == true) {
        try {
          current_user_id = getId();
          this.user_id = current_user_id;
          create_record = new Users(current_user_id, this.username, commName, this.email, this.password, this.userAlias, this.userType);
          hib = hib_session();
          tx = hib.beginTransaction();
          hib.save(create_record);
          tx.commit();
          savedRecord = true;
        } catch (Exception ex) {
          tx.rollback();
          this.user_id = null;
          System.out.println("Error in processMemberActivation create new user");
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
          message(null, "MemberRegistrationNotUpdated", new Object[]{this.username, this.email});
        } finally {
          tx = null;
          hib = null;
          create_record = null;
        }
      }
    } else {
      message(null, "DuplicateUserNameOrEmail", new Object[]{this.username, this.email});
    }

    if (savedRecord == true) {
      returnString = loginUser();   /// will set values 
    } else {
      app.setUserToNull();
      returnString = "member_registration.xhtml?pid=" + this.pid;
    }

    return returnString;
  }

  public String acceptUpdatedUser() {  /// no changes here becuase user has to login

    List results = null;
    Session hib = null;
    Transaction tx = null;
    app.setUsername(this.username);
    results = app.findUserName();
    Users uu = (Users) results.get(0);
    uu.setActivatedAt(new Date());
    uu.setResetCode(null);
    uu.setDateUpdated(new Date());
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.update(uu);
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in acceptUpdatedUser");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      hib = null;
      tx = null;
      results = null;
    }

    return loginUser();

  }

  public String loginUser() {
    // debugging with password assignment
    this.password = "Emcgurty123!";

    Boolean b_local_results = false;
    Integer memberCreator = -9;
    List results = null;
    List accept_results = null;
    String return_string = null;
    app.setUsername(this.username);
    results = app.findUserName();
    if (results != null) {

      if (results.size() == 0) {
        message(null, "UserNameNotFound", new Object[]{this.username});
        this.username = null;
        app.setUsername(null);
        return_string = "index";
      } else if (results.size() == 1) {
        Boolean validPassword = validateUserPassword(results);
        if (validPassword == false) {
          message(null, "InvalidPassword", new Object[]{this.username});
          this.username = null;
          app.setUsername(null);
          return_string = "index";
        } else if (validPassword == true) {
          results = verifyUserIsActivated();
          if (results != null) {
            if (results.size() == 0) {
              message(null, "UserHasNotActivated", new Object[]{this.username});
              this.username = null;
              app.setUsername(null);
              return_string = "index";
            } else if (results.size() == 1) {
//              Users uu1 = (Users) results.get(0);
//              app.setUser_id(uu1.getUser_id());
//              app.setRoleId(uu1.getRoleId());
//              app.setUserType(uu1.getUserType());  //db
//              app.setUserAlias(uu1.getUserAlias());
//              app.setEmail(uu1.getEmail());
//              uu1 = null;
              accept_results = hasAcceptedAgreement();  /// returns a Participant record
              if (accept_results != null) {
                if (accept_results.size() == 0) {
                  accept_results = null;
                  message(null, "UserHasNotAccepted", new Object[]{this.username});
                  return_string = "user_agreement";  ////pbean.load_ud("-1");
                } else if (accept_results.size() == 1) {
                  accept_results = null;
                  app.setAcceptID(true);
                  message(null, "LogInSuccessful", new Object[]{this.username});
                  Users uu = (Users) results.get(0);  /// User result that has current user data
                  memberCreator = uu.getRoleId();
                  app.setRoleId(memberCreator);
                  if (memberCreator > 0) {
                    setCurrentUserCommunityId(uu.getUser_id());  // sets cid and pid
                    app.setCommunityName(uu.getCommunityName());
                  }

                  app.setEmail(uu.getEmail());
                  app.setUserAlias(uu.getUserAlias());
                  app.setUsername(this.username);
                  app.setUserType(uu.getUserType());
                  app.setUserType(app.userIsWhichType());  /// I should never have to call userIsWhichTYpe again.....
                  app.setUser_id(uu.getUser_id());
                  this.user_id = uu.getUser_id();  // needed for findWhatIsComplete
                  results = null;
                  app.setLICid(false);
                  app.setLITid(false);
                  app.setCpId(false);

                  switch (memberCreator) {
                    case 0:  // Individual not with a community
                      findWhatIsComplete();
                      return_string = this.isCompleteString;
                      break;
                    case 1:  // A community creator
                      findWhatIsCommunityComplete();
                      return_string = this.isCompleteString;
                      if (return_string.isEmpty() == true) {
                        findWhatIsComplete();  // hold needs to be written
                        return_string = this.isCompleteString;
                      }
                      break;
                    case 2:  // A member of a community
                      findWhatIsComplete(); // hold needs to be written
                      return_string = this.isCompleteString;
                      break;
                    default:
                      break;
                  }
                }
              } else {
                message(null, "UserHasNotAccepted", new Object[]{this.username});
                return_string = "user_agreement"; //pbean.load_ud("-1");
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
    app.setEditable(1);
    switch (return_string) {
      case "user_agreement":
        return_string = pbean.load_ud("-1");
        break;
      case "user_item":
        return_string = ibean.load_ud(this.userType, "");
        break;
      case "user_nae":
        return_string = pbean.load_ud(this.user_id);
        break;
      case "user_contact_preferences":
        return_string = cpbean.load_ud(app.getParticipant_id());
        break;
      case "lender_transfer":
        return_string = ltribean.load_ud(app.getParticipant_id());
        break;
      case "lender_conditions":
        return_string = lcbean.load_ud(app.getParticipant_id());
        break;
      case "community_detail":
        return_string = commbean.load_community_detail();
        break;
      case "community_members":
        return_string = cmbean.load_community_members();
        break;
      default:
        return_string = "index";

    }
    if ("community_detail".equals(return_string) || "user_agreement".equals(return_string) || "index".equals(return_string)) {
      return return_string;  // no redirect because lose data
    } else {
      return return_string + "?faces-redirect=true";   /// need to stop forwarding from index AND redirect should end Session
    }
//    return return_string;   /// need to stop forwarding from index
  }

  private String findWhatIsComplete() {

    String return_string = "";
    String pid = null;
    String return_null = "";
    List partList = completeParticipantRecord();
    if (partList != null) {
      if (partList.size() == 1) {
        Participant part = (Participant) partList.get(0);
        pid = part.getParticipant_id();
        app.setParticipant_id(pid);
        app.setPartID(true);

      } else {
        app.setEditable(0);
        app.setPartID(true);
        return_string = "user_nae"; //pbean.load_ud(this.user_id);
      }

      if (return_string.isEmpty() == true) {
        app.completeContactPreferences(app.getParticipant_id());

        if (app.getCpId() == false) {
          app.setEditable(0);
          return_string = "user_contact_preferences"; //cpbean.load_ud(pid);
        } else {
          //  emm 123:  No data default 
          app.setEditable(1);
          switch (app.getUserType()) {
            case "borrow":
              setAction("current");
              return_string = "user_item";
              break;
            case "lend":
              ///setAction("current");
              app.completeLIT(app.getParticipant_id());
              if (app.getLITid() == false) {
                return_string = "lender_transfer"; //ltribean.load_ud(this.participant_id);
              } else {
                app.completeLIC(app.getParticipant_id());
                if (app.getLICid() == false) {
                  return_string = "lender_conditions"; //licibean.load_ud(this.participant_id);
                } else {
                  setAction("current");
                  return_string = "user_item"; //ibean.load_ud("lend", return_null);
                }
              }
              break;
            case "both":
              //setAction("current");
              app.completeLIT(app.getParticipant_id());
              if (app.getLITid() == false) {
                return_string = "lender_transfer"; //ltribean.load_ud(this.participant_id);
              } else {
                app.completeLIC(app.getParticipant_id());
                if (app.getLICid() == false) {
                  return_string = "lender_conditions"; //licibean.load_ud(this.participant_id);
                } else {
                  app.setAction("current");
                  app.setLICid(true);
                  return_string = "user_item"; //ibean.load_ud("both", return_null);
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
    this.isCompleteString = return_string;
    return return_string;
  }

  private void findWhatIsCommunityComplete() {

    String return_string = "";
    String pid = null;
    Participant part = null;
    List partList = completeParticipantRecord();
    if (partList != null) {
      if (partList.size() == 1) {
        part = (Participant) partList.get(0);
        pid = part.getParticipant_id();
        app.setParticipant_id(pid);
        app.setCommunityId(part.getCommunityId());
        app.setCreatorDetailID(true);
      } else {
        app.setCommunityId(part.getCommunityId());
        app.setCreatorDetailID(true);
        app.setEditable(0);
        return_string = "user_nae"; //pbean.load_ud(this.user_id);
      }

      if (return_string.isEmpty() == true) {
        List completCD = completeCommunityDetail();
        Integer hs = completCD.size();
        if (hs == 0) {
          app.setComDetailID(false);
          app.setEditable(1);
          return_string = "community_detail"; //commbean.load_community_detail();
        } else {
          app.setComDetailID(true);
          app.setComMemberDetailID(true);
          app.setEditable(1);
          return_string = "community_members"; //cmbean.load_community_members();

        }
      }
    } else {
      message(null, "ParticpantNotFound", null);
      return_string = "index";
    }
    this.isCompleteString = return_string;
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
        app.setParticipant_id(getPID.getParticipant_id());
        app.setCommunityId(getPID.getCommunityId());
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
              + " WHERE community_id = :cid and communityName is not null";
      results = hib.createQuery(queryString)
              .setParameter("cid", app.getCommunityId())
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
              .setParameter("uid", app.getUser_id())
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
      System.out.println("Error in activateUser");
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
    List results = validateUserNameResetCode();   // return user with reset code
    if (results != null) {

      try {
        userArray = (Users) results.get(0);
        hib = hib_session();
        tx = hib.beginTransaction();
        Users uu = (Users) hib.get(Users.class, userArray.getUser_id());
        uu.setResetCode(null);
        uu.setActivatedAt(new Date());
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
    app.setUserToNull();
    app.setUserAction("login");
    return "index";
  }

  public String forgotUserPassword() {
    String buildReset_Code = null;
    Users userArray = null;
    Session hib = null;
    Transaction tx = null;

    List results = validateEmail(email);
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
      String[] appEmail = app.getApplicationEmail();
      /// if String not empty, need to code
      try {
        SendEmail se = new SendEmail("forgotPassword", userArray.getUsername(), null, email, appEmail[0], appEmail[1], userArray.getUser_id(), buildReset_Code);
        se = null;
      } catch (Exception ex) {
        System.out.println("Send Mail Failed");
        Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      }
      message(null, "ForgotUserPasswordSuccess", new Object[]{email});
      app.setUserToNull();
      app.setUserAction("login");
      return "index";
    } else {
      message(null, "ForgotUserPasswordFailed", new Object[]{email});
      app.setUserToNull();
      app.setUserAction("login");
      return "index";
    }
  }

  public String forgotUserName() {

    String results = validateEmailAndPassword(getEmail(), getPassword());
    if (results != null) {
      message(null, "FoundUsername", new Object[]{results});
    } else {
      message(null, "UserNameNotFound", null);
    }

    results = null;
    app.setUserToNull();
    app.setUserAction("login");

    return "index";

  }

  private String validateEmailAndPassword(String em, String pw) {

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
      System.out.println("Error in ValiateEmailandPassword");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
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

  private List validateEmail(String email) {
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
      System.out.println("Error in validateEmail");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (results.size() == 1) {
      return results;
    } else {
      return null;
    }
  }

  private List validateUserNameResetCode() {
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
      System.out.println("Error in validateUserNameResetCode");
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
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

  public void makeCDselection(ComponentSystemEvent event) {

    UIComponent components = event.getComponent();
    UIInput uiInputCD = (UIInput) components.findComponent("contact_describe_id");
    String reg_cd = uiInputCD.getLocalValue() == null ? "" : uiInputCD.getLocalValue().toString();
    String CDId = uiInputCD.getClientId();
    if (reg_cd != null) {
      if ("-2".equals(reg_cd) == true) {
        FacesMessage msg = new FacesMessage("Plese make a selection to describe yourself");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        context().addMessage(CDId, msg);
        context().renderResponse();
      } else {
        return;
      }
    } else {
      // Required true do its job 
      return;
    }
  }

  public void makeCountryselection(ComponentSystemEvent event) {

    UIComponent components = event.getComponent();
    UIInput uiInputCD = (UIInput) components.findComponent("countryId");
    String reg_cd = uiInputCD.getLocalValue() == null ? "" : uiInputCD.getLocalValue().toString();
    String CDId = uiInputCD.getClientId();
    if (reg_cd != null) {
      if ("-2".equals(reg_cd) == true) {
        FacesMessage msg = new FacesMessage("Plese make a Country selection");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        context().addMessage(CDId, msg);
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

      } else {
        return;
      }
    }
  }

  /**
   * @return the userTypeArray
   */
  public List<String> getUserTypeArray() {

    if (userTypeArray == null && this.userType != null) {
      ArrayList<String> uta = new ArrayList<String>(Arrays.asList(this.userType.split(",")));
      if ("both".equals(uta.get(0)) && app.getEditable() != null) {
        List<String> uta2 = Arrays.asList("borrow", "lend");
        return uta2;
      } else {
        return uta;
      }
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
    app.setUserAction("login");
    return "index";
  }

  public String load_forgotUserPassword() {
    this.username = null;
    app.setUserAction("forgotUserPassword");
    return "index?faces-redirect=true";
  }

  public String load_forgotUsername() {
    this.username = null;
    app.setUserAction("forgotUsername");
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

  private List completeParticipantRecord() {  

    List results = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      results = hib.createQuery("from Participant WHERE user_id = :uid AND firstName is not null")
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

  public String updateUserLogin() {   

    // called by user_login_update.xhtml, which will produce email if user changed password
    Boolean updateSuccess = false;
    Boolean wasPasswordChanged = false;
    String resetCodeString = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    List result = null;
    Users uu = null;
    byte[] new_salt = null;
    byte[] new_passwordEncrypt = null;

    try {
      queryString = " FROM Users "
              + " WHERE user_id  = :uid";
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("uid", this.user_id)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println("Error on Update User, retrieving current user information");

    } finally {
      hib = null;
      tx = null;
    }

    if (result != null) {
      if (result.size() == 1) {
        wasPasswordChanged = validateUserPassword(result);  // if false then user changed password;
        if (wasPasswordChanged == false) {
          try {
            //// Need to create new salt and pepper for new password. 
            uu = new Users(this.user_id, this.username, this.communityName, this.email, this.password, null, this.userAlias, "NA", -1);
            new_salt = uu.getSalt();
            new_passwordEncrypt = uu.getCryptedPassword();
            resetCodeString = UUID.randomUUID().toString();
            uu = null;
          } catch (Exception ex) {

          } finally {
            uu = null;
          }
        }
        try {
          hib = hib_session();
          tx = hib.beginTransaction();
          uu = (Users) result.get(0);
          uu.setUsername(username);
          uu.setUserAlias(userAlias);
          uu.setEmail(email);
          this.userType = null;
          String userTypeString = String.join(",", getUserTypeArray());
          uu.setUserType(userTypeString);
          if (this.communityName != null) {
            uu.setCommunityName(this.communityName);
          }
          if (wasPasswordChanged == false) {
            uu.setActivatedAt(null);
            uu.setSalt(new_salt);
            uu.setCryptedPassword(new_passwordEncrypt);
            uu.setResetCode(resetCodeString);
          }
          hib.update(uu);
          tx.commit();
          updateSuccess = true;
          app.setUsername(this.username);
          app.setUserAlias(this.userAlias);
          app.setEmail(this.email);
          app.setRoleId(uu.getRoleId());   /// really not necessary
          app.setUserType(userTypeString);
          app.setUserType(app.userIsWhichType());  /// I should never have to call userIsWhichTYpe again.....          
        } catch (Exception ex) {
          tx.rollback();
          Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
          System.out.println("Error on Update User, updating current user information");
        } finally {
          hib = null;
          tx = null;
          uu = null;
          result = null;
        }
      }
    }

    if (updateSuccess == true) {
      if (wasPasswordChanged == false) {
        sendActivationEmail(resetCodeString, 1);  /// email will direct user to update_user.xhtml
        message(null, "LoginUpdated", new Object[]{app.getUsername(), app.getEmail()});
      } else {
        message(null, "LoginUpdatedNoEmail", new Object[]{app.getUsername()});
      }
    } else {
      message(null, "LoginUpdateFailed", new Object[]{app.getUsername()});
    }

    app.setUserToNull();
    app.setUserAction("login");
    return "index";
  }

  public String load_ud(Integer which) {

    app.setEditable(which);
    List results = null;
    if (app.getUser_id() == null) {
      message(null, "LoginInRequiredToReviseUserInformation", null);
      return "index";
    } else {
      try {
        
        results = app.findUserName();
        if (results != null) {
          if (results.size() == 1) {
            Users uu = (Users) results.get(0);
            this.userType = uu.getUserType();
            this.user_id = uu.getUser_id();
            this.username = uu.getUsername();
            this.userAlias = uu.getUserAlias();
            this.email = uu.getEmail();
            
          }
        }
      } catch (Exception ex)  {
        System.out.println("Error in UserBean, load_ud");
      } finally {
        results = null;
      }

      return "user_login_update";
    }
  }

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  private Boolean sendActivationEmail(String resetCodeString, Integer which) {

    //// which 0 = new, 1 = update
    String[] getMap = new String[2];
    getMap = app.getApplicationEmail();
    Boolean return_string = false;

    try {

      switch (which) {
        case 0:
          if (this.communityName == null) {
            SendEmail se = new SendEmail("registration", this.username, this.userAlias, this.email, getMap[0], getMap[1], this.password, resetCodeString);
            se = null;
          } else {
            SendEmail se = new SendEmail("Community: " + this.communityName, this.username, this.userAlias, this.email, getMap[0], getMap[1], this.password, resetCodeString);
            se = null;
          }
          break;
        case 1:
          if (this.communityName == null) {
            this.communityName = "";   // becuase SendEmail tests for isEmpty
          }
          SendEmail se = new SendEmail("update", this.username, this.userAlias, this.email, this.communityName, getMap[0], getMap[1], this.password, resetCodeString);
          se = null;
          break;
      }

      return_string = true;
    } catch (Exception ex) {
      System.out.println("Send Mail Failed");
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      getMap = null;
    }

    return return_string;
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
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
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
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
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
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
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

  public List getMemberRegistrationInformation(String pid) {   /// called from a GUI

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
      System.out.println("Error on getMemberRegistrationInformation");
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
    }

    if (result != null) {
      if (result.size() == 1) {
        pt = (Participant) result.get(0);
        this.firstName = pt.getFirstName();
        this.lastName = pt.getLastName();
        this.userAlias = pt.getAlias();
        this.email = pt.getEmailAlternative();
        this.username = null;

      }
    }

    return result;
  }

  private String getMemberInformation(String pid) {   // called from GUI

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
      System.out.println("Error on getMemberInformation");
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
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
        app.setParticipant_id(pid);
      }
    }
    return returnCID;
  }

  public String getRegistrationCommunityName(String pid) {   /// called from member_registration.xhmlt, user not known

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    String returnString = null;
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
      System.out.println("Error on getRegistrationCommunityName");
      Logger
              .getLogger(UserBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;

    }

    if (result != null) {
      if (result.size() == 1) {
        pt = (Communities) result.get(0);
        returnString = pt.getCommunityName();
      }
    }
    result = null;
    pt = null;
    return returnString;

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
   * @return the isCompleteString
   */
  public String getIsCompleteString() {
    return isCompleteString;
  }

  /**
   * @param isCompleteString the isCompleteString to set
   */
  public void setIsCompleteString(String isCompleteString) {
    this.isCompleteString = isCompleteString;
  }

  /**
   * @return the uid
   */
  public String getUid() {
    return uid;
  }

  /**
   * @param uid the uid to set
   */
  public void setUid(String uid) {
    this.uid = uid;
  }

}
