package echomarket.web.managedbeans;

import echomarket.hibernate.PasswordEncryptionService;
import echomarket.hibernate.PasswordValidator;
import echomarket.hibernate.Purpose;
import echomarket.hibernate.Users;
import echomarket.hibernate.Map;
import echomarket.SendEmail.SendEmail;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean
@SessionScoped
public class UserBean extends AbstractBean implements Serializable {


    private String username;
    private String userAlias;
    private String userType;
    private List<String> userTypeArray;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String resetCode;
    private String appEmail;  
    
    public UserBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        List result = null;
        Users create_record = null;
        List tmp = null;
        String hold_userTypeBuild = "";
        String fullname = firstName + " " + lastName;
        String ac = null;
        Integer holdUserType = null;
        Boolean savedRecord = false;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        try {
            if (hib.isOpen() == false) {
                hib = hib_session();
            }
            if (tx.isActive() == false) {
                tx = hib.beginTransaction();
            }
            ///  I am pursuing this effort in getting from established arrays, rather than making Where Database calls
            for (String userTypeArray1 : getUserTypeArray()) {
//                holdUserType = getEchomarket_types().indexOf(userTypeArray1);
//                tmp = getEchomarket_types_stored();
//                Object get_tmp_value = tmp.get(holdUserType);
                hold_userTypeBuild = hold_userTypeBuild + userTypeArray1 + ";";

            }

            setUserType(hold_userTypeBuild);
            create_record = new Users(getId(), firstName, lastName, username, userAlias, password, email, getUserType());
            hib.save(create_record);
            ac = create_record.getResetCode();
            tx.commit();
            savedRecord = true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Create new User failed");
            savedRecord = false;
        } finally {
        }

        if (savedRecord == true) {

            if (hib.isOpen() == false ) {
                hib = hib_session();
            }

            if (tx.isActive() == false) {
                tx = hib.beginTransaction();
            }

            List results = hib.createQuery("from Map WHERE key_text like '%gmail.com'").list();
            Map a_array = (Map) results.get(0);

            try {
                SendEmail se = new SendEmail("registration", username, userAlias, email, a_array.getKey_text(), a_array.getValue_text(), password, ac);
                se = null;
            } catch (Exception e) {
                System.out.println("Send Mail Failed");
            }
        }


        message(
                null,
                "NewRegistration",
                new Object[]{fullname});

        return "index";

    }

    public void resetForm() {

        this.setUsername("");
        this.setFirstName("");
        this.setLastName("");
        this.setEmail("");
        this.setPassword("");
    }

    private String[] buildTypeList() {

        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String[] results = null;
        String queryString = "from Purpose order by purpose_order";
        List rl = hib.createQuery(queryString).list();
        tx.commit();
        results = new String[rl.size()];

        for (int i = 0; i < rl.size(); i++) {
            Purpose p_a = (Purpose) rl.get(i);
            String tmp = p_a.getPurposeType();
            results[i] = tmp;
        }

        return results;
    }

    private String[] buildTypeListStored() {

        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
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

        return results;
    }

    public String loginUser() {
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
        String queryString = "from Users where username = :un   and activated_at != null";
        results = hib.createQuery(queryString).setParameter("un", username).list();
        tx.commit();

        //  Must return only one record
        if (results.size() == 1) {

            Users users_Array = (Users) results.get(0);
            byte[] salt = users_Array.getSalt();
            byte[] crypted_password = users_Array.getCryptedPassword();

            PasswordEncryptionService pes = new PasswordEncryptionService();
            try {
                getp = pes.authenticate(password, crypted_password, salt);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            getp = false;
        }
        hib = null;
        tx = null;
        results = null;
        if (getp == true) {
            message(
                    null,
                    "LogInSuccessful",
                    null);

            
            
            return_string = "index";
        } else if (getp == false) {
            username = null;
            userAlias = null;
            setUserType(null);
            password = null;
            firstName = null;
            lastName = null;
            email = null;
            setResetCode(null);

            message(
                    null,
                    "LogInFailed",
                    null);

            return_string = "login";
        }

        return return_string;
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
        String user_id = users_Array.getId();
        users_Array = null;

        // Should return only one row
        if (results.size() == 1) {
            if (session.isOpen() == false) {
                session = hib_session();
            }
            if (tx.isActive() == false) {
                tx = session.beginTransaction();
            }

            Users uu = (Users) session.get(Users.class, user_id);
            uu.setActivatedAt(ndate);
            uu.setResetCode(null);
            tx.commit();
            session = null;
            tx = null;
            uu = null;
            message(
                    null,
                    "ActivateSuccessful",
                    new Object[]{});
            return true;
        } else {
            return false;
        }

    }

    public String managePasswordChange() {

        List results = ValidateUserNameResetCode();
        if (results != null) {
            Users userArray = (Users) results.get(0);
            //this.user_id = Integer.toString(userArray.getId());
            //// Update the user to generate reset_code
            Session hib = hib_session();
            Transaction tx = hib.beginTransaction();
            Users uu = (Users) hib.get(Users.class, userArray.getId());
            uu.setResetCode(null);
            PasswordEncryptionService pes = new PasswordEncryptionService();
            try {
                uu.setCryptedPassword(pes.getEncryptedPassword(password, uu.getSalt()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            tx.commit();

        }
        //PasswordChangeSuccess
        message(
                null,
                "PasswordChangeSuccess",
                new Object[]{});
        return "index";
    }

    public String forgotUserPassword() {
        String buildReset_Code = null;
        List results = ValidateEmail(email);
        if (results != null) {
            Users userArray = (Users) results.get(0);
            //// Update the user to generate reset_code
            Session hib = hib_session();
            Transaction tx = hib.beginTransaction();
            Users uu = (Users) hib.get(Users.class, userArray.getId());
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

            try {
                //  SendEmail .... You indicated that you forgot your user password, follow this link to change it
                SendEmail se = new SendEmail("forgotPassword", userArray.getUsername(), null, email, a_array.getKey_text(), a_array.getValue_text(), userArray.getId(), buildReset_Code);
                se = null;
            } catch (Exception e) {
                System.out.println("Send Mail Failed");
            }
            message(
                    null,
                    "ForgotUserPasswordSuccess",
                    new Object[]{email});
            return "index";
        } else {
            message(
                    null,
                    "ForgotUserPasswordFailed",
                    new Object[]{email});
            return "login";
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
            return "login";
        } else {
            message(
                    null,
                    "UserNameNotFound",
                    null);
            results = null;
            return "forgotUsername";
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
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
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
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
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
        return userTypeArray;
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

}
