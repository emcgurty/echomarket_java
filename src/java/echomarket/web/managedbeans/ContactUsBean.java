package echomarket.web.managedbeans;

import echomarket.SendEmail.SendEmail;
import echomarket.hibernate.ContactUs;
import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "contactUsBean")
@RequestScoped
public class ContactUsBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  private String user_id;
  private String email;
  private String subject;
  private String comments;
  
  public ContactUsBean() {
  }

  public String sendMessage() {
    
    String rip = getClientIpAddr();
    Session hib = null;
    Transaction tx = null;
    Boolean savedRecord = false;
    ContactUs cus = new ContactUs(UUID.randomUUID().toString(), this.subject, rip, this.email, this.comments, this.user_id);

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      hib.save(cus);
      tx.commit();
      savedRecord = true;
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(ContactUsBean.class.getName()).log(Level.SEVERE, "Error in save ContactUsBean", ex);
      
    } finally {
      hib = null;
      tx = null;
    }
    if (savedRecord == true) {
      SendEmail se = null;
      String[] getMap = null;
      getMap = new String[2];
      getMap = ubean.getApplicationEmail();
      try {
        se = new SendEmail("contactUs", this.email, this.subject, this.comments, getMap[0], getMap[1], getClientIpAddr());
        message(null, "MessageSuccessfullySent", new Object[]{this.email});
      } catch (Exception ex) {
        message(null, "MessageNotSuccessfullySent", new Object[]{this.email});
        System.out.println("Error in ContactUsBean in sending message");
        Logger.getLogger(ContactUsBean.class.getName())
                .log(Level.SEVERE, "COMMUNITY MEMBER NOT updates", ex);

      } finally {
        se = null;
        getMap = null;
      }
    } else {
      message(null, "MessageNotSuccessfullySent", new Object[]{this.email});
    }
    return "index";

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
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
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


}
