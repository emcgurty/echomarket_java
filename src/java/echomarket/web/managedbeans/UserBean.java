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
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {

    @Inject
    ContactPreferenceBean cpbean;
    @Inject
    ParticipantBean pbean;
    @Inject
    ItemBean ibean;
    private String user_id;
    private String username;
    private String userAlias;
    private String userType;
    private List<String> userTypeArray;
    private String password;
    private String email;
    private String resetCode;
    private String appEmail;
    private String userAction;
    private String registrationType;
    private String communityName;
    private Integer isCommunity;
    private Integer editable;
    private Integer roleId;

    public String Logout() {
        this.user_id = null;
        this.username = null;
        this.userAlias = null;
        this.userType = null;
        this.userTypeArray = null;
        this.password = null;
        this.email = null;
        this.resetCode = null;
        this.appEmail = null;
        this.isCommunity = null;
        this.roleId = null;

        return "index?faces-redirect=true";
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
            return userType.replace(";", " ").toUpperCase();
        }
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

    public String registerUser() {

        String test_mb = getAppEmail();
        Users create_record = null;
        Communities comm = null;
        Participant participant = null;
        String commName = null;
        String fullname = this.username;
        String ac = null;  // holds reset_code
        try {
            if (this.communityName != null) {
                commName = this.communityName;
            }
        } catch (Exception ex) {
        }

        Boolean savedRecord = false;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String current_user_id = null;
        try {
            current_user_id = getId();
            // Should do a look-up for id on 'creator'
            if (this.communityName != null) {
                create_record = new Users(current_user_id, this.username, this.email, this.password, this.resetCode, this.userAlias, parseUserTypeArray(), 2);
            } else {
                create_record = new Users(current_user_id, this.username, this.email, this.password, this.resetCode, this.userAlias, parseUserTypeArray(), -1);
            }
        } catch (Exception ex) {
        }

        try {
            hib.save(create_record);
            tx.commit();
            ac = create_record.getResetCode();
            savedRecord = true;
        } catch (Exception ex) {
            tx.rollback();
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on Update User");
            message(
                    null,
                    "LoginNotUpdated",
                    new Object[]{this.username, this.email});
        } finally {

            tx = null;
            hib = null;
            create_record = null;
        }

        if (savedRecord == true) {

            if (this.communityName != null) {
                hib = hib_session();
                tx = hib.beginTransaction();
                //participant = new Participant(getId(), current_user_id, -9, 0, "NA", "NA", "NA", 0, 0, 0, 1);
                //hib.save(participant);
                try {
                    comm = new Communities(current_user_id, this.communityName, 0, "NA", "?", "NA", "NA", "NA", "NA", "NA", "NA", "99", "99", "NA", "NA", this.email, 1, "NA", "NA");
                    hib.save(comm);
                    tx.commit();
                    Boolean txw = tx.wasCommitted();
                    savedRecord = true;
                } catch (Exception ex) {
                    tx.rollback();
                    Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error on Update User");

                } finally {
                    hib = null;
                    hib = null;
                }

            }

            if (savedRecord == true) {

                savedRecord = sendActivationEmail(ac);
            }
            if (savedRecord == true) {
                if (commName == null) {
                    message(
                            null,
                            "NewRegistration",
                            new Object[]{fullname, this.email});
                } else {

                    message(
                            null,
                            "NewCommunityRegistration",
                            new Object[]{fullname, commName, this.email});
                }
            } else {
                message(
                        null,
                        "NewRegistrationFailed",
                        new Object[]{fullname});

            }
        }

        return "index";
    }

    public void resetForm() {

        this.setUsername("");
        this.setEmail("");
        this.setPassword("");
    }

    public Boolean parseUserType(String whichType) {
        if (this.userType != null) {
            return this.userType.contains(whichType);
        } else {
            return false;
        }
    }

    private String[] buildTypeList() {

        List rl = null;
        Session hib;
        Transaction tx;
        hib = null;
        tx = null;

        try {
            hib = hib_session();
            tx = hib.beginTransaction();
        } catch (Exception ex) {
            System.out.println("Error at line 242 in UserBean");
            ex.printStackTrace();
        }

        String[] results = null;
        String queryString = "from Purpose order by purpose_order";
        try {
            rl = hib.createQuery(queryString).list();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error at line 250 in US Bean");
            ex.printStackTrace();
        } finally {
            try {
                hib.close();
            } catch (Exception ex) {
            }
            tx = null;
        }
        results = new String[rl.size()];

        for (int i = 0; i < rl.size(); i++) {
            Purpose p_a = (Purpose) rl.get(i);
            String tmp = p_a.getPurposeType();
            results[i] = tmp;
        }

        return results;
    }

    private String[] buildTypeListStored() {

        Session hib;
        Transaction tx;
        hib = null;
        tx = null;

        try {
            hib = hib_session();
            tx = hib.beginTransaction();
        } catch (Exception ex) {
        }

        String[] results = null;
        String queryString = "from Purpose order by purpose_order";
        List rl = hib.createQuery(queryString).list();
        tx.commit();
        results = new String[rl.size()];

        for (int i = 0; i < rl.size(); i++) {
            Purpose p_a = (Purpose) rl.get(i);
            String tmp = p_a.getPurposeShort();
            results[i] = tmp;
        }
        hib = null;
        tx = null;

        return results;
    }

    public String loginUser() {

        // debugging
        this.password = "Emcgurty123!";
        Boolean act_results = false;
        // getResetCode learned from url
        if (getResetCode() != null) {
            act_results = ActivateUser();
        }

        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        List results = null;
        String return_string = null;
        Boolean getp = false;
        String queryString = "from Users where username = :un  and activated_at != null";

        try {
            results = hib.createQuery(queryString).setParameter("un", this.username).list();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error at line 326 in UserLogin");
            ex.printStackTrace();
        } finally {
            hib = null;
            tx = null;
        }
        Users users_Array = new Users();

        //  Must return only one record
        if (results.size() == 1) {

            users_Array = (Users) results.get(0);
            results = null;
            byte[] salt = users_Array.getSalt();
            byte[] crypted_password = users_Array.getCryptedPassword();

            PasswordEncryptionService pes = new PasswordEncryptionService();
            try {
                getp = pes.authenticate(password, crypted_password, salt);

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            getp = false;
        }

        if (getp == true) {
            setUser_id(users_Array.getUser_id());
            setUserType(users_Array.getUserType());
            setUserAlias(users_Array.getUserAlias());
            setUsername(users_Array.getUsername());
            setEmail(users_Array.getEmail());
            if (users_Array.getRoleId() == 2) {
                setIsCommunity(1);
            }

            if (act_results == true) {
                this.editable = -1;
                return_string = "user_detail";
                message(null, "ActivateSuccessful", new Object[]{});
            } else {
                message(null, "LogInSuccessful", new Object[]{this.username});
            }

            act_results = false;
            List hasComplete = completeParticipantRecord(this.user_id);
            Integer hs = hasComplete.size();
            if (hs == 0) {
                hasComplete = null;
                this.editable = -1;
                return_string = "user_detail";
            } else if ((hs > 0)) {
                switch (userAction) {
                    case "agreement":
                        return pbean.load_ud(this.user_id);
                    case "dashboard":
                        Participant part = (Participant) hasComplete.get(0);
                        Integer gw = part.getGoodwill();
                        Integer i18 = part.getAge18OrMore();
                        String un = part.getFirstName();
                        if ((gw == 1) && (i18 == 1) && (un == null)) {
                            this.editable = 3;
                            return pbean.load_ud(this.user_id);
                        } else if ((gw == 1) && (i18 == 1) && (un != null)) {
                            List hasCompleteCP = completeContactPreferences(this.user_id);
                            hs = hasCompleteCP.size();
                            if (hs == 0) {
                                this.editable = 5;
                                return cpbean.load_ud(this.user_id);
                            } else {
                                this.editable = 13;
                                if (this.userType.contains("borrow")) {
                                    return ibean.load_ud("borrow", null);
                                } else if (this.userType.contains("lend")) {
                                    return ibean.load_ud("lend", null);
                                } else {
                                    return ibean.load_ud("both", null);
                                }
                            }
                        }

                    default:
                    // Statements
                }
                this.userAction = null;
            }

        } else if (getp
                == false) {
            this.username = null;
            this.userAlias = null;
            setUserType(null);
            this.password = null;
            this.email = null;
            setResetCode(null);

            message(
                    null,
                    "LogInFailed",
                    null);

            return_string = "login";
        }

        return return_string
                + "?faces-redirect=true";
//        return return_string;
    }

    private Boolean ActivateUser() {

        List results = null;
        Date ndate = new Date();
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String queryString = "from Users where reset_code = :rc";
        results = session.createQuery(queryString).setParameter("rc", getResetCode()).list();
        tx.commit();
        Users users_Array = (Users) results.get(0);
        String u_id = users_Array.getUser_id();
        users_Array = null;

        // Should return only one row
        if (results.size() == 1) {
            if (session.isOpen() == false) {
                session = hib_session();
            }
            if (tx.isActive() == false) {
                tx = session.beginTransaction();

            }

            Users uu = (Users) session.get(Users.class, u_id);
            uu.setActivatedAt(ndate);
            uu.setResetCode(null);
            tx.commit();
            session = null;
            tx = null;
            uu = null;
//            message(
//                    null,
//                    "ActivateSuccessful",
//                    new Object[]{});
            return true;
        } else {
            return false;
        }

    }

    public String managePasswordChange() {

        List results = ValidateUserNameResetCode();
        if (results != null) {
            Users userArray = (Users) results.get(0);
            Session hib = hib_session();
            Transaction tx = hib.beginTransaction();
            Users uu = (Users) hib.get(Users.class, userArray.getUser_id());
            uu.setResetCode(null);
            PasswordEncryptionService pes = new PasswordEncryptionService();
            try {
                uu.setCryptedPassword(pes.getEncryptedPassword(this.password, uu.getSalt()));

            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            tx.commit();
            tx = null;
        }
        //PasswordChangeSuccess
        message(
                null,
                "PasswordChangeSuccess",
                new Object[]{});
        return "index?faces-redirect=true";
    }

    public String forgotUserPassword() {
        String buildReset_Code = null;
        List results = ValidateEmail(email);
        if (results != null) {
            Users userArray = (Users) results.get(0);
            //// Update the user to generate reset_code
            Session hib = hib_session();
            Transaction tx = hib.beginTransaction();
            Users uu = (Users) hib.get(Users.class, userArray.getUser_id());
            buildReset_Code = uu.BuildRandomValue();
            uu.setResetCode(buildReset_Code);
            tx.commit();

            if (hib.isOpen() == false) {
                hib = hib_session();
            }

            if (tx.isActive() == false) {
                tx = hib.beginTransaction();
            }

            results = hib.createQuery("from Map WHERE key_text like '%gmail.com'").list();
            Map a_array = (Map) results.get(0);
            tx.commit();

            try {
                //  SendEmail .... You indicated that you forgot your user password, follow this link to change it
                SendEmail se = new SendEmail("forgotPassword", userArray.getUsername(), null, email, a_array.getKeyText(), a_array.getValueText(), userArray.getUser_id(), buildReset_Code);
                se = null;
            } catch (Exception e) {
                System.out.println("Send Mail Failed");
            }
            message(
                    null,
                    "ForgotUserPasswordSuccess",
                    new Object[]{email});
            this.username = null;
            this.userAction = "login";
            return "index";
        } else {
            message(
                    null,
                    "ForgotUserPasswordFailed",
                    new Object[]{email});
            this.username = null;
            this.userAction = "login";
            return "index";
        }
    }

    public String forgotUserName() {

        String results = ValidateEmailAndPassword(getEmail(), getPassword());
        if (results != null) {

            message(
                    null,
                    "FoundUsername",
                    new Object[]{results});
            results = null;
            this.username = null;
            this.userAction = "login";
            return "index";
        } else {
            message(
                    null,
                    "UserNameNotFound",
                    null);
            results = null;
            this.userAction = "login";
            return "index";
        }
    }

    private String ValidateEmailAndPassword(String em, String pw) {

        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        // Validate email and password
        String return_user_name = null;
        Boolean auth_pw = false;
        Boolean auth_em = false;
        String queryString = "from Users where email  = :em";
        List results = hib.createQuery(queryString).setParameter("em", em).list();
        tx.commit();
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
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (auth_pw == true && auth_em == true) {
            hib = null;
            tx = null;
            return return_user_name;
        } else {
            hib = null;
            tx = null;
            return null;
        }
    }

    private List ValidateEmail(String email) {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        // Validate email
        String queryString = "from Users where email  = :em";
        List results = hib.createQuery(queryString).setParameter("em", email).list();
        tx.commit();
        if (results.size() == 1) {
            return results;
        } else {
            return null;
        }
    }

    private List ValidateUserNameResetCode() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        // Validate email
        String queryString = "from Users where username = :un and reset_code = :rc";
        List results = hib.createQuery(queryString).setParameter("un", username).setParameter("rc", getResetCode()).list();
        tx.commit();
        tx = null;
        if (results.size() == 1) {
            return results;
        } else {
            return null;
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
            message(null,
                    "PasswordsDoNotMatch",
                    null);
        } else {
            PasswordValidator pv = new PasswordValidator();
            Boolean is_valid = pv.validate(password);
            if (!is_valid) {
                FacesMessage msg = new FacesMessage("Password does not have required values");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                context().addMessage(passwordId, msg);
                context().renderResponse();
                message(
                        null,
                        "PasswordMustContain",
                        null);

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

    private void setSessionVariables() {
//        /// Actually not necessary in @Inject success.... just retaining for now
//        FacesContext context = FacesContext.getCurrentInstance();
//        java.util.Map<String, Object> requestMap = context.getExternalContext().getSessionMap();
//
//        requestMap.put("user_id", getUser_id());
//        requestMap.put("user_alias", getUserAlias());
//        requestMap.put("user_type", getUserType());
//        requestMap.put("username", getUsername());
//        if (getIsCommunity() != 1) {
//            requestMap.put("is_community", getIsCommunity());
//
//        }

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

    public String userNameLabel(Integer IsCommunity) {

        if (IsCommunity == 1) {
            return "Community Username:";
        } else {
            return "Username:";

        }

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
        this.isCommunity = 0;
        this.communityName = null;
        return "user_registration?faces-redirect=true";
    }

    public String load_community_registration() {
        this.isCommunity = 1;
        return "community_registration?faces-redirect=true";

    }

    /**
     * @return the isCommunity
     */
    public Integer getIsCommunity() {
        return isCommunity;
    }

    /**
     * @param isCommunity the isCommunity to set
     */
    public void setIsCommunity(Integer isCommunity) {
        this.isCommunity = isCommunity;
    }

    private List completeParticipantRecord(String user_id) {

        List results = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        try {
            results = hib.createQuery("from Participant WHERE user_id = :uid")
                    .setParameter("uid", user_id)
                    .list();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            Logger
                    .getLogger(UserBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on completeParticipantRecord");
            return null;
        } finally {
            tx = null;
            hib = null;
        }
        return results;

    }

    private List completeContactPreferences(String user_id) {

        List results = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        try {
            results = hib.createQuery("from ContactPreference WHERE participant_id = :uid")
                    .setParameter("uid", user_id)
                    .list();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            Logger
                    .getLogger(UserBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on completeParticipantRecord");
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
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        Users uu = new Users(this.user_id, this.username, this.email, this.password, null, this.userAlias, parseUserTypeArray(), this.getRoleId());
        hib.update(uu);
        try {
            tx.commit();
            resetCodeString = uu.getResetCode();
            updateSuccess = true;
        } catch (Exception ex) {
            tx.rollback();
            Logger
                    .getLogger(UserBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on Update User");
            message(
                    null,
                    "LoginNotUpdated",
                    new Object[]{this.username, this.email});
        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            tx = null;
            hib = null;
        }
        if (updateSuccess == true) {

            sendActivationEmail(resetCodeString);
            message(
                    null,
                    "LoginUpdated",
                    new Object[]{this.username, this.email});
        } else {
            message(
                    null,
                    "LoginUpdateFailed",
                    new Object[]{this.username, this.email});

        }

        return "index";

    }

    public String load_ud(Integer which) {

        this.editable = which;
        if (user_id == null) {
            this.userAction = "agreement";
            message(null, "LoginInRequiredToReviseAgreement", null);
            return "index";
        } else {
            this.userAction = "dashboard";
//            return "user_detail.xhtml?faces-redirect=true";
            return "user_detail.xhtml";
        }
    }

    public void editUserLogin() {
        this.editable = 1;
    }

    public void updateNAE() {

        this.editable = 3;

    }

    public void editNAE() {

        this.editable = 2;
    }

    public void updateCP() {

        this.editable = 5;
    }

    public void editCP() {

        this.editable = 4;
    }

    private String parseUserTypeArray() {

        String hold_userTypeBuild = "";
        for (String userTypeArray1 : getUserTypeArray()) {
            hold_userTypeBuild = hold_userTypeBuild + userTypeArray1 + ";";
        }
        return hold_userTypeBuild;

    }

    /**
     * @return the roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    private Boolean sendActivationEmail(String resetCodeString) {

        Session hib = null;
        Transaction tx = null;
        List results = null;
        Map a_array = null;
        try {
            hib = hib_session();
            tx = hib.beginTransaction();
        } catch (Exception ex) {
            System.out.println("Error in SendActivationEmail, line 1003");
            ex.printStackTrace();
        }

        try {
            results = hib.createQuery("from Map WHERE key_text like '%gmail.com'").list();
            tx.commit();
            a_array = (Map) results.get(0);
        } catch (Exception ex) {
            tx.rollback();
            Logger
                    .getLogger(UserBean.class
                            .getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on SendEmail, line ");
        } finally {
            tx = null;
            hib = null;

        }

        try {
            if (this.communityName == null) {
                SendEmail se = new SendEmail("registration", username, userAlias, email, a_array.getKeyText(), a_array.getValueText(), password, resetCodeString);
                se = null;
            } else {
                SendEmail se = new SendEmail("Community: " + this.communityName, this.username, this.userAlias, this.email, a_array.getKeyText(), a_array.getValueText(), this.password, resetCodeString);
                se = null;
            }
            a_array = null;
            return true;
        } catch (Exception e) {
            System.out.println("Send Mail Failed");
            return false;
        }

    }

}
