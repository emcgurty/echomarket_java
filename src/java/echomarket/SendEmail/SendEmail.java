package echomarket.SendEmail;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import org.hibernate.validator.internal.util.logging.Messages;
public class SendEmail implements java.io.Serializable {

  private Integer user_id;
  private String whichEmail;
  private String username;
  private String commmunityName;
  private String password;
  private String user_alias;
  private String user_email;
  private String application_email_address;
  private String application_email_password;
  private String reset_code;
  private String firstName;
  private String lastName;
  private String subject;
  private String comments;
  private String itemDescription;
  private String itemModel;
  private Integer numberOfItems;
  private String imageFileName;
  private String itemImageCaption;
  private String remoteIp;

  public SendEmail() {
  }

///contact Us
  public SendEmail(String whichEmail, String email, String subject, String comments, String app_email, String app_password, String remoteIp) {
    this.whichEmail = whichEmail;
    this.user_email = email;
    this.subject = subject;
    this.comments = comments;
    this.application_email_address = app_email;
    this.application_email_password = app_password;
    this.remoteIp = this.remoteIp;
    Session sess = establishSession();
    sendContactUs(sess);
  }

///new Item
  public SendEmail(String whichEmail, String email, String itemDescription, String itemModel, Integer numberOfItems, String imageFileName, String itemImageCaption, String app_email, String app_password, String remoteIp) {
    this.whichEmail = whichEmail;   /// lend or borrow
    this.user_email = email;
    this.itemDescription = itemDescription;
    this.itemModel = itemModel;
    this.numberOfItems = numberOfItems;
    this.imageFileName = imageFileName;
    this.itemImageCaption = itemImageCaption;
    this.application_email_address = app_email;
    this.application_email_password = app_password;
    this.remoteIp = remoteIp;
    Session sess = establishSession();
    sendNewItem(sess);
  }

// community member 
  public SendEmail(String whichEmail, String firstName, String lastName, String user_alias,
          String user_email, String app_email, String app_password, String random, String rc) {

    this.whichEmail = whichEmail;
    this.firstName = firstName;
    this.lastName = lastName;
    this.commmunityName = random;
    this.user_alias = user_alias;
    this.user_email = user_email;
    this.application_email_address = app_email;
    this.application_email_password = app_password;
    this.reset_code = rc;
    Session sess = establishSession();

    String threeChars = this.whichEmail.substring(0, 3);
    if ("registration".equals(this.whichEmail)) {
      /// then random argument is the password
      this.password = random;
      sendRegistrationEmail(sess);
    } else if ("Com".equals(threeChars)) {
      /// then random argument is the password
      this.password = random;
      sendCommunityRegistrationEmail(sess);
    } else if ("member".equals(this.whichEmail)) {
      /// then random argument is the password
      sendCommunityMemberEmail(sess);
    } else if ("forgotPassword".equals(this.whichEmail)) {
      this.reset_code = rc;
      sendForgotPasswordEmail(sess);
    } else if ("forgotUserName".equals(this.whichEmail)) {

    }
  }

  public SendEmail(String whichEmail, String username, String user_alias,
          String user_email, String app_email, String app_password, String random, String rc) {

    this.whichEmail = whichEmail;
    this.username = username;
    this.user_alias = user_alias;
    this.user_email = user_email;
    this.application_email_address = app_email;
    this.application_email_password = app_password;
    this.reset_code = rc;
    Session sess = establishSession();

    String threeChars = this.whichEmail.substring(0, 3);
    if ("registration".equals(this.whichEmail)) {
      /// then random argument is the password
      this.password = random;
      sendRegistrationEmail(sess);

    } else if ("Com".equals(threeChars)) {
      /// then random argument is the password
      this.password = random;
      sendCommunityRegistrationEmail(sess);
    } else if ("member" == this.whichEmail) {
      /// then random argument is the password
      this.password = random;
      sendCommunityMemberEmail(sess);

    } else if ("forgotPassword".equals(this.whichEmail)) {
      //SendEmail se = new SendEmail("forgotPassword", userArray.getUsername(), null, email, returnApplicationAddress(), returnApplicationPwd(), null, reset_code);
      // random is user_id
      this.reset_code = rc;
      sendForgotPasswordEmail(sess);
    } else if ("forgotUserName".equals(this.whichEmail)) {

    }
  }

  private Boolean sendNewItem(Session sess) {

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(sess);
      message.setContent(BuildNewItemMessage(), "text/html; charset=utf-8");
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(getApplication_email_address()));

      // Set To: header field of the header.
      String recipients = getApplication_email_address() + " , " + this.user_email;
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
      

      // Set Subject: header field
      message.setSubject("New EchoMarket Item.");

      // Send message
      Transport.send(message);

      System.out.println("Sent message successfully....");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private Boolean sendContactUs(Session sess) {

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(sess);
      message.setContent(BuildContactUsMessage(), "text/html; charset=utf-8");
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(getApplication_email_address()));

      // Set To: header field of the header.
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(getApplication_email_address()));

      // Set Subject: header field
      message.setSubject("EchoMarket Contact.");

      // Send message
      Transport.send(message);

      System.out.println("Sent message successfully....");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private String BuildContactUsMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String buildMessage = "<html><h1>EchoMarket Contact Us</h1>"
            + "<p>Subject: " + this.subject + "</p>"
            + "<p>Comments:  " + this.comments + "</p>"
            + "<p>User Email:  " + this.user_email + "</p>"
            + "<p>Date Sent:  " + new Date() + "</p>"
            + "<p>Contact message was sent from IP Address:  " + this.remoteIp + ".</p>"
            + "<p>PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private String BuildNewItemMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String buildMessage = "<html><h1>EchoMarket New Item</h1>"
            + "<p>EchoMarket awaiting approval:</p>"
            + "<p>Item to " + this.whichEmail + ".</p>"
            + "<p>Item Description: " + this.itemDescription + "</p>"
            + "<p>Item Model:  " + this.itemModel + "</p>"
            + "<p>Number of Items:  " + this.numberOfItems + "</p>";

    if (this.imageFileName.isEmpty() == false) {
      buildMessage = buildMessage + "<p>Item Image:  " + this.imageFileName + "</p>"
              + "<p>(Unaware of image content, EchoMarket will not actually transmit the image file.)</p>";
    }
    if (this.itemImageCaption.isEmpty() == false) {
      buildMessage = buildMessage + "<p>Item Image Caption:  " + this.itemImageCaption + "</p>";
    }

    buildMessage = buildMessage + "<p>Date Sent:  " + new Date() + "</p>"
            + "<p>Your item will be assessed for approval within one day.</p>"
            + "<p>Thanks for participating on EchoMarket!</p>"
            + "<p>Item created from this IP Address:  " + this.getRemoteIp() + ".</p>"
            + "<p>PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private String BuildRegistrationMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String url_string = bundle.getString("ActivationUrl");
    Object paramArray[] = new Object[2];
    paramArray[0] = getResetCode();
    url_string = MessageFormat.format(url_string, paramArray);
    String buildMessage = "<html><h1>Your EchoMarket account has been created.</h1>"
            + "<p> User Name: " + this.username + "</p>"
            + "<p> Password:  " + this.password + "</p>"
            + "<h2> Visit this url to activate your account: </h2>"
            + "<p><a href='url'>" + url_string + "</a></p>"
            + "<p>  Thank you,</p>"
            + "<p>  www.echomarket.org</p>"
            + "<p>  PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private String BuildCommunityRegistrationMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String url_string = bundle.getString("ActivationUrl");
    Object paramArray[] = new Object[2];
    paramArray[0] = getResetCode();
    url_string = MessageFormat.format(url_string, paramArray);
    String buildMessage = "<html><h2>Your EchoMarket Community account has been created.</h2>"
            + "<p>" + this.whichEmail + "</p>"
            + "<p> User Name: " + this.username + "</p>"
            + "<p> Password:  " + this.password + "</p>"
            + "<h2> Visit this url to activate your account: </h2>"
            + "<p><a href='url'>" + url_string + "</a></p>"
            + "<p>  Thank you,</p>"
            + "<p>  www.echomarket.org</p>"
            + "<p>  PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private String BuildCommunityMemberRegistrationMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String url_string = bundle.getString("ActivationUrl");
    Object paramArray[] = new Object[2];
    paramArray[0] = getResetCode();
    url_string = MessageFormat.format(url_string, paramArray);
    String buildMessage = "<html><h1>Your EchoMarket Community account has been created.</h1>"
            + "<p>" + this.whichEmail + "</p>"
            + "<p> User Name: " + this.username + "</p>"
            + "<p> Password:  " + this.password + "</p>"
            + "<h2> Visit this url to activate your account: </h2>"
            + "<p><a href='url'>" + url_string + "</a></p>"
            + "<p>  Thank you,</p>"
            + "<p>  www.echomarket.org</p>"
            + "<p>  PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private String BuildMemberCommunityMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String url_string = bundle.getString("NewMemberUrl");
    Object paramArray[] = new Object[2];
    paramArray[0] = getResetCode();
    url_string = MessageFormat.format(url_string, paramArray);
    String buildMessage = "<html><h2>"
            + this.firstName + " " + this.lastName
            + " with alias, " + this.user_alias
            + ", you have been added to the EchoMarket Community: "
            + this.commmunityName + ".</h2>"
            + " <p>If you wish to participate in this Community, you need to complete two quick steps."
            + " First, you need to click on the link below or copy it into your browser's web address area, and complete Registration form. Then you will receive another email to activate your account.</p>"
            + " <h2> Visit this url to Register: </h2>"
            + " <p><a href='url'>" + url_string + "</a></p>"
            + " <p>  Thank you,</p>"
            + " <p>  www.echomarket.org</p>"
            + " <p>  PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private String BuildForgotPasswordMessage() {

    ResourceBundle bundle = ResourceBundle.getBundle(
            "echomarket.web.messages.Messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());
    String url_string = bundle.getString("PasswordChangeUrl");
    Object paramArray[] = new Object[1];
    paramArray[0] = this.getResetCode();
    url_string = MessageFormat.format(url_string, paramArray);   //could just use Object[] {value(s)}
    String buildMessage = "<html><h1>Within Echomarket, you indicated that you can't remember or lost your password...</h1>"
            + "<p>If you found it or remembered it, please disregard this.</p>"
            + "<p>Your password has not been changed.</p>"
            + "<h2> Visit this url to change your password: </h2>"
            + "<p><a href='url'>" + url_string + "</a></p>"
            + "<p>  Thank you,</p>"
            + "<p>  www.echomarket.org</p>"
            + "<p>  PS: If you received this email in error, please disregard it.</p></html>";
    return buildMessage;
  }

  private Session establishSession() {
    String host = "smtp.gmail.com"; //relay.jangosmtp.net";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", "587");
    // Get the Session object.
    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(getApplication_email_address(), getApplication_email_password());
      }
    });
    return session;

  }
//sendCommunityMemberEmail

  private Boolean sendCommunityMemberEmail(Session sess) {

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(sess);
      message.setContent(BuildMemberCommunityMessage(), "text/html; charset=utf-8");
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(getApplication_email_address()));

      // Set To: header field of the header.
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(getUser_email()));

      // Set Subject: header field
      message.setSubject("You have been added to EchoMarket Community: " + this.commmunityName);

      // Send message
      Transport.send(message);

      System.out.println("Sent message successfully....");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private Boolean sendRegistrationEmail(Session sess) {

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(sess);
      message.setContent(BuildRegistrationMessage(), "text/html; charset=utf-8");
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(getApplication_email_address()));

      // Set To: header field of the header.
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(getUser_email()));

      // Set Subject: header field
      message.setSubject("Thank you for Registering at EchoMarket.");

      // Send message
      Transport.send(message);

      System.out.println("Sent message successfully....");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private Boolean sendCommunityRegistrationEmail(Session sess) {

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(sess);
      message.setContent(BuildCommunityRegistrationMessage(), "text/html; charset=utf-8");
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(getApplication_email_address()));

      // Set To: header field of the header.
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(getUser_email()));

      // Set Subject: header field
      message.setSubject("Thank you for Registering at EchoMarket.");

      // Send message
      Transport.send(message);

      System.out.println("Sent message successfully....");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  private Boolean sendForgotPasswordEmail(Session sess) {

    try {
      // Create a default MimeMessage object.
      Message message = new MimeMessage(sess);
      message.setContent(BuildForgotPasswordMessage(), "text/html; charset=utf-8");
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(getApplication_email_address()));

      // Set To: header field of the header.
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(getUser_email()));

      // Set Subject: header field
      message.setSubject("Echomarket reponse to change password.");

      // Send message
      Transport.send(message);

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    } finally {
      System.out.println("Sent message successfully....");
    }
    return true;
  }

  private Integer getuser_id() {
    return user_id;
  }

  private void setuser_id(Integer id) {
    this.user_id = id;
  }

  private String getUsername() {
    return username;
  }

  private void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the password
   */
  private String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  private void setPassword(String pw) {
    this.password = pw;
  }

  /**
   * @return the user_alias
   */
  private String getUser_alias() {
    return user_alias;
  }

  /**
   * @param user_alias the user_alias to set
   */
  private void setUser_alias(String user_alias) {
    this.user_alias = user_alias;
  }

  /**
   * @return the user_email
   */
  private String getUser_email() {
    return user_email;
  }

  /**
   * @param user_email the user_email to set
   */
  private void setUser_email(String user_email) {
    this.user_email = user_email;
  }

  /**
   * @return the application_email_address
   */
  private String getApplication_email_address() {
    return application_email_address;
  }

  /**
   * @param application_email_address the application_email_address to set
   */
  private void setApplication_email_address(String application_email_address) {
    this.application_email_address = application_email_address;
  }

  /**
   * @return the application_email_password
   */
  private String getApplication_email_password() {
    return application_email_password;
  }

  /**
   * @param application_email_password the application_email_password to set
   */
  private void setApplication_email_password(String application_email_password) {
    this.application_email_password = application_email_password;
  }

  /**
   * @return the activation_code
   */
  private String getResetCode() {
    return reset_code;
  }

  /**
   * @param activation_code the username to set
   */
  private void setResetCode(String rc) {
    this.reset_code = rc;
  }

  /**
   * @return the commmunityName
   */
  private String getCommmunityName() {
    return commmunityName;
  }

  /**
   * @param commmunityName the commmunityName to set
   */
  private void setCommmunityName(String commmunityName) {
    this.commmunityName = commmunityName;
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
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @return the comments
   */
  public String getComments() {
    return comments;
  }

  /**
   * @param comments the comments to set
   */
  public void setComments(String comments) {
    this.comments = comments;
  }

  /**
   * @return the itemDescription
   */
  public String getItemDescription() {
    return itemDescription;
  }

  /**
   * @param itemDescription the itemDescription to set
   */
  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  /**
   * @return the itemModel
   */
  public String getItemModel() {
    return itemModel;
  }

  /**
   * @param itemModel the itemModel to set
   */
  public void setItemModel(String itemModel) {
    this.itemModel = itemModel;
  }

  /**
   * @return the numberOfItems
   */
  public Integer getNumberOfItems() {
    return numberOfItems;
  }

  /**
   * @param numberOfItems the numberOfItems to set
   */
  public void setNumberOfItems(Integer numberOfItems) {
    this.numberOfItems = numberOfItems;
  }

  /**
   * @return the imageFileName
   */
  public String getImageFileName() {
    return imageFileName;
  }

  /**
   * @param imageFileName the imageFileName to set
   */
  public void setImageFileName(String imageFileName) {
    this.imageFileName = imageFileName;
  }

  /**
   * @return the itemImageCaption
   */
  public String getItemImageCaption() {
    return itemImageCaption;
  }

  /**
   * @param itemImageCaption the itemImageCaption to set
   */
  public void setItemImageCaption(String itemImageCaption) {
    this.itemImageCaption = itemImageCaption;
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

}
