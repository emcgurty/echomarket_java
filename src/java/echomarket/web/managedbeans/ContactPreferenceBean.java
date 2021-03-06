package echomarket.web.managedbeans;

import echomarket.hibernate.ContactPreference;
import echomarket.hibernate.Participant;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class ContactPreferenceBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 6L;

  private String contactPreferenceId;
  private String participant_id;
  private String itemId;
  private Integer useWhichContactAddress;
  private Integer contactByChat;
  private Integer contactByEmail;
  private Integer contactByHomePhone;
  private Integer contactByCellPhone;
  private Integer contactByAlternativePhone;
  private String contactByFacebook;
  private String contactByTwitter;
  private String contactByInstagram;
  private String contactByLinkedIn;
  private String contactByOtherSocialMedia;
  private String contactByOtherSocialMediaAccess;
  private Boolean questionAltAddress;
  private Boolean questionAltEmail;
//  private Boolean displayPrimary;
//  private Boolean displayAlternative;
  private Boolean displayHomePhone;
  private Boolean displayAlternativePhone;
  private Boolean displayCellPhone;
  private Boolean displayAlternativeEmail;
  private String userAlternativeEmail;

  public ContactPreferenceBean() {
  }

  @Id
  public String getContactPreferenceId() {
    return contactPreferenceId;
  }

  public void setContactPreferenceId(String contactPreferenceId) {
    this.contactPreferenceId = contactPreferenceId;
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
   * @return the useWhichContactAddress
   */
  public Integer getUseWhichContactAddress() {
    return useWhichContactAddress;
  }

  /**
   * @param useWhichContactAddress the useWhichContactAddress to set
   */
  public void setUseWhichContactAddress(Integer useWhichContactAddress) {
    this.useWhichContactAddress = useWhichContactAddress;
  }

  /**
   * @return the contactByChat
   */
  public Integer getContactByChat() {

    return contactByChat;

  }

  /**
   * @param contactByChat the contactByChat to set
   */
  public void setContactByChat(Integer contactByChat) {
    this.contactByChat = contactByChat;
  }

  /**
   * @return the contactByEmail
   */
  public Integer getContactByEmail() {
    return contactByEmail;
  }

  /**
   * @param contactByEmail the contactByEmail to set
   */
  public void setContactByEmail(Integer contactByEmail) {
    this.contactByEmail = contactByEmail;
  }

  /**
   * @return the contactByHomePhone
   */
  public Integer getContactByHomePhone() {
    return contactByHomePhone;
  }

  /**
   * @param contactByHomePhone the contactByHomePhone to set
   */
  public void setContactByHomePhone(Integer contactByHomePhone) {
    this.contactByHomePhone = contactByHomePhone;
  }

  /**
   * @return the contactByCellPhone
   */
  public Integer getContactByCellPhone() {
    return contactByCellPhone;
  }

  /**
   * @param contactByCellPhone the contactByCellPhone to set
   */
  public void setContactByCellPhone(Integer contactByCellPhone) {
    this.contactByCellPhone = contactByCellPhone;
  }

  /**
   * @return the contactByAlternativePhone
   */
  public Integer getContactByAlternativePhone() {
    return contactByAlternativePhone;
  }

  /**
   * @param contactByAlternativePhone the contactByAlternativePhone to set
   */
  public void setContactByAlternativePhone(Integer contactByAlternativePhone) {
    this.contactByAlternativePhone = contactByAlternativePhone;
  }

  /**
   * @return the contactByFacebook
   */
  public String getContactByFacebook() {
    return contactByFacebook;

  }

  /**
   * @param contactByFacebook the contactByFacebook to set
   */
  public void setContactByFacebook(String contactByFacebook) {
    this.contactByFacebook = contactByFacebook;
  }

  /**
   * @return the contactByTwitter
   */
  public String getContactByTwitter() {
    return contactByTwitter;
  }

  /**
   * @param contactByTwitter the contactByTwitter to set
   */
  public void setContactByTwitter(String contactByTwitter) {
    this.contactByTwitter = contactByTwitter;
  }

  /**
   * @return the contactByInstagram
   */
  public String getContactByInstagram() {
    return contactByInstagram;
  }

  /**
   * @param contactByInstagram the contactByInstagram to set
   */
  public void setContactByInstagram(String contactByInstagram) {
    this.contactByInstagram = contactByInstagram;
  }

  /**
   * @return the contactByLinkedIn
   */
  public String getContactByLinkedIn() {
    return contactByLinkedIn;

  }

  /**
   * @param contactByLinkedIn the contactByLinkedIn to set
   */
  public void setContactByLinkedIn(String contactByLinkedIn) {
    this.contactByLinkedIn = contactByLinkedIn;
  }

  /**
   * @return the contactByOtherSocialMedia
   */
  public String getContactByOtherSocialMedia() {
    return contactByOtherSocialMedia;
  }

  /**
   * @param contactByOtherSocialMedia the contactByOtherSocialMedia to set
   */
  public void setContactByOtherSocialMedia(String contactByOtherSocialMedia) {
    this.contactByOtherSocialMedia = contactByOtherSocialMedia;
  }

  /**
   * @return the contactByOtherSocialMediaAccess
   */
  public String getContactByOtherSocialMediaAccess() {
    return contactByOtherSocialMediaAccess;
  }

  /**
   * @param contactByOtherSocialMediaAccess the contactByOtherSocialMediaAccess
   * to set
   */
  public void setContactByOtherSocialMediaAccess(String contactByOtherSocialMediaAccess) {
    this.contactByOtherSocialMediaAccess = contactByOtherSocialMediaAccess;
  }

  public String updateCP() {

    Boolean successTransaction = false;
    List cp_list = null;
    Session sb;
    Transaction tx;
    sb = null;
    tx = null;
    String addNewCP = null;
    String currentIid = null;
    try {
      Map<String, String> params = null;
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      addNewCP = params.get("action");
      currentIid = params.get("iid");
      app.setItemId(currentIid);
      this.itemId = currentIid;
      for (Map.Entry<String, String> entry : params.entrySet()) {

        if (entry.getKey().contains("contactPreferenceId")) {
          this.contactPreferenceId = entry.getValue();
        }
        if (entry.getKey().contains("contactByFacebook")) {
          this.contactByFacebook = entry.getValue();
        }
        if (entry.getKey().contains("contactByChat")) {
          this.contactByChat = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("contactByTwitter")) {
          this.contactByTwitter = entry.getValue();
        }
        if (entry.getKey().contains("contactByLinkedIn")) {
          this.contactByLinkedIn = entry.getValue();
        }
        if (entry.getKey().contains("contactByInstagram")) {
          this.contactByInstagram = entry.getValue();
        }
        if ("personal_information:contactByOtherSocialMedia".equals(entry.getKey())) {
          this.contactByOtherSocialMedia = entry.getValue();
        }
        if (entry.getKey().contains("contactByOtherSocialMediaAccess")) {
          this.contactByOtherSocialMediaAccess = entry.getValue();
        }
        if (entry.getKey().contains("useWhichContactAddress")) {
          this.useWhichContactAddress = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("contactByEmail")) {
          this.contactByEmail = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("contactByHomePhone")) {
          this.contactByHomePhone = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("contactByCellPhone")) {
          this.contactByCellPhone = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("contactByAlternativePhone")) {
          this.contactByAlternativePhone = Integer.valueOf(entry.getValue());
        }
        System.out.println(entry.getKey() + "/" + entry.getValue());
      }
    } catch (Exception ex) {
    }

    if (this.contactPreferenceId.isEmpty() == true) {

      ContactPreference part = new ContactPreference(getId(),
              app.getParticipant_id(), this.itemId,
              this.useWhichContactAddress, this.contactByChat,
              this.contactByEmail, this.contactByHomePhone,
              this.contactByCellPhone, this.contactByAlternativePhone,
              this.contactByFacebook, this.contactByTwitter, this.contactByInstagram, this.contactByLinkedIn, this.contactByOtherSocialMedia, this.contactByOtherSocialMediaAccess, new Date());

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        sb.save(part);
        tx.commit();
        message(null, "CPSaved", null);
        successTransaction = true;
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Error in Save Contact Preferences");
        Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        sb = null;
        tx = null;
      }

    } else {

      if (this.itemId.isEmpty() == false) {
        cp_list = getCurrentCP_Id(app.getParticipant_id(), this.itemId);
      } else {
        cp_list = getCurrentCP(app.getParticipant_id());
      }
      if (cp_list != null) {
        if (cp_list.size() == 1) {
          ContactPreference part = (ContactPreference) cp_list.get(0);
          part.setItemId(this.itemId);
          part.setUseWhichContactAddress(this.useWhichContactAddress);
          part.setContactByChat(this.contactByChat);
          part.setContactByEmail(this.contactByEmail);
          part.setContactByHomePhone(this.contactByHomePhone);
          part.setContactByAlternativePhone(this.contactByAlternativePhone);
          part.setContactByCellPhone(this.contactByCellPhone);
          part.setContactByAlternativePhone(this.contactByAlternativePhone);
          part.setContactByFacebook(this.contactByFacebook);
          part.setContactByTwitter(this.contactByTwitter);
          part.setContactByInstagram(this.contactByInstagram);
          part.setContactByLinkedIn(this.contactByLinkedIn);
          part.setContactByOtherSocialMedia(this.contactByOtherSocialMedia);
          part.setContactByOtherSocialMediaAccess(this.contactByOtherSocialMediaAccess);
          part.setDateUpdated(new Date());

          sb = hib_session();
          tx = sb.beginTransaction();
          try {
            sb.update(part);
            tx.commit();
            successTransaction = true;
            message(null, "CPUpdated", null);
          } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in Update Contact Preferences");
            Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
            message(null, "UpdateOrSaveOfCPNotSuccessful", null);
          } finally {
            sb = null;
            tx = null;
          }
        } else {
          // Create new record
          ContactPreference part = new ContactPreference(getId(), app.getParticipant_id(),
                  this.itemId, this.useWhichContactAddress, this.contactByChat,
                  this.contactByEmail, this.contactByHomePhone, this.contactByCellPhone,
                  this.contactByAlternativePhone, this.contactByFacebook, this.contactByTwitter,
                  this.contactByInstagram, this.contactByLinkedIn,
                  this.contactByOtherSocialMedia, this.contactByOtherSocialMediaAccess, new Date());

          try {
            sb = hib_session();
            tx = sb.beginTransaction();
            sb.save(part);
            tx.commit();
            //app.setCpId(true);
            message(null, "CPSaved", null);
            successTransaction = true;
          } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in Save Contact Preferences");
            Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
          } finally {
            sb = null;
            tx = null;
          }
        }
      }
    }

    if (successTransaction == true) {
      app.setEditable(1);
      if (app.getItemId() != null) {
        if (app.getItemId().isEmpty() == false) {
          app.completeContactPreferences(app.getParticipant_id());
        } else {
          app.setCpId(true);
        }
      }
    } else {
      app.setEditable(0);
      app.setCpId(false);
    }
    return load_ud(app.getParticipant_id());
  }

  public String load_ud(String pid) {

    List partlist = null;
    Map<String, String> params = null;
    params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String strIid = null;
    String action = null;
    Boolean isCPnull = false;
    try {
      strIid = params.get("iid");
      if (strIid != null) {
        if (strIid.isEmpty() == true) {
          strIid = null;
        }
      }
    } catch (Exception ex) {
    }
    // emm 1.8
    if (app != null) {

      if (app.getEditable() != null) {
        if (app.getEditable() == 0) {
          app.setEditable(1);
        } else {
          app.setEditable(0);
        }
      } else {
        app.setEditable(1);
      }

      try {
        action = params.get("action");
      } catch (Exception ex) {
      }

      if (action != null) {
        if ("edit".equals(action)) {
          app.setEditable(1);
        }
      }
    }
    if (strIid != null) {
      partlist = getCurrentCP_Id(pid, strIid);
    }

    if (partlist == null) {
      isCPnull = true;
    }

    if (partlist != null) {
      if (partlist.size() == 0) {
        isCPnull = true;
      }
    }

    if (isCPnull == true) {
      partlist = getCurrentCP(pid);
    }

    if (partlist != null) {
      if (partlist.size() == 1) {
        ContactPreference pp = (ContactPreference) partlist.get(0);
        if (pp != null) {
          this.contactPreferenceId = pp.getContactPreferenceId();
          this.itemId = (pp.getItemId());
          this.participant_id = (pp.getParticipant_id());
          this.useWhichContactAddress = (pp.getUseWhichContactAddress());
          this.contactByChat = (pp.getContactByChat());
          this.contactByEmail = (pp.getContactByEmail());
          this.contactByHomePhone = (pp.getContactByHomePhone());
          this.contactByCellPhone = (pp.getContactByCellPhone());
          this.contactByAlternativePhone = (pp.getContactByAlternativePhone());
          this.contactByFacebook = pp.getContactByFacebook();
          this.contactByTwitter = (pp.getContactByTwitter());
          this.contactByInstagram = (pp.getContactByInstagram());
          this.contactByLinkedIn = (pp.getContactByLinkedIn());
          this.contactByOtherSocialMedia = (pp.getContactByOtherSocialMedia());
          this.contactByOtherSocialMediaAccess = (pp.getContactByOtherSocialMediaAccess());
          getParticipantAddreseEmailAlts(this.participant_id);
          pp = null;
        }
      } else if (app != null) {
        getParticipantAddreseEmailAlts(app.getParticipant_id());
      }
    }
    partlist = null;
    return "user_contact_preferences";
  }

  private List getCurrentCP(String pid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "FROM ContactPreference WHERE participant_id = :pid  ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setMaxResults(1)
              .list();

      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getCurrentCP");
      e.printStackTrace();
      tx.rollback();
      return null;
    } finally {
      tx = null;
      session = null;

    }
    return result;
  }

  private List getCurrentCP_Id(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "FROM ContactPreference WHERE participant_id = :pid  and itemId = :iid ORDER BY participant_id, itemId, dateCreated";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", iid)
              .setMaxResults(1)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getCurrentCP");
      e.printStackTrace();
      tx.rollback();
      return null;
    } finally {
      tx = null;
      session = null;

    }
    return result;
  }

  private void getParticipantAddreseEmailAlts(String pid) {

    System.out.println("Called getParticipantAddreseEmailAlts");
    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "FROM Participant WHERE participant_id = :pid  ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setMaxResults(1)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getParticipantAddreseEmailAlts");
      e.printStackTrace();
      tx.rollback();
    } finally {
      tx = null;
      session = null;
    }

    if (result != null) {
      if (result.size() == 1) {
        Participant part = (Participant) result.get(0);
        if (part.getQuestionAltAddress() != null) {
          if (part.getQuestionAltAddress() == 1) {
            this.setQuestionAltAddress(true);
          } else {
            this.setQuestionAltAddress(false);
          }
        } else {
          this.setQuestionAltAddress(false);
        }

        if (part.getEmailAlternative() != null) {
          this.displayAlternativeEmail = true;
          this.userAlternativeEmail = part.getEmailAlternative();
        } else {
          this.displayAlternativeEmail = false;
          this.userAlternativeEmail = null;
        }

/// Now being managed in applicationParams becuase information needed for both ContactPreferenceBean and LenderTransferBean        
        if (part.getDisplayAddress() == 1) {
          app.setDisplayPrimary(true);
        } else {
          app.setDisplayPrimary(false);
        }
        if (part.getDisplayAlternativeAddress() == 1) {
          app.setDisplayAlternative(true);
        } else {
          app.setDisplayAlternative(false);
        }
        if (part.getDisplayHomePhone() == 1) {
          this.displayHomePhone = true;
        } else {
          this.displayHomePhone = false;
        }
        if (part.getDisplayCellPhone() == 1) {
          this.displayCellPhone = true;
        } else {
          this.displayCellPhone = false;
        }
        if (part.getDisplayAlternativePhone() == 1) {
          this.displayAlternativePhone = true;
        } else {
          this.displayAlternativePhone = false;
        }

      }

    }
  }

  /**
   * @return the questionAltAddress
   */
  public Boolean getQuestionAltAddress() {
    return questionAltAddress;
  }

  /**
   * @param questionAltAddress the questionAltAddress to set
   */
  public void setQuestionAltAddress(Boolean questionAltAddress) {
    this.questionAltAddress = questionAltAddress;
  }

  /**
   * @return the questionAltEmail
   */
  public Boolean getQuestionAltEmail() {
    return questionAltEmail;
  }

  /**
   * @param questionAltEmail the questionAltEmail to set
   */
  public void setQuestionAltEmail(Boolean questionAltEmail) {
    this.questionAltEmail = questionAltEmail;
  }

//  /**
//   * @return the displayPrimary
//   */
//  public Boolean getDisplayPrimary() {
//    return displayPrimary;
//  }
//
//  /**
//   * @param displayPrimary the displayPrimary to set
//   */
//  public void setDisplayPrimary(Boolean displayPrimary) {
//    this.displayPrimary = displayPrimary;
//  }
//
//  /**
//   * @return the displayAlternative
//   */
//  public Boolean getDisplayAlternative() {
//    return displayAlternative;
//  }
//
//  /**
//   * @param displayAlternative the displayAlternative to set
//   */
//  public void setDisplayAlternative(Boolean displayAlternative) {
//    this.displayAlternative = displayAlternative;
//  }
  /**
   * @return the displayHomePhone
   */
  public Boolean getDisplayHomePhone() {
    return displayHomePhone;
  }

  /**
   * @param displayHomePhone the displayHomePhone to set
   */
  public void setDisplayHomePhone(Boolean displayHomePhone) {
    this.displayHomePhone = displayHomePhone;
  }

  /**
   * @return the displayAlternativePhone
   */
  public Boolean getDisplayAlternativePhone() {
    return displayAlternativePhone;
  }

  /**
   * @param displayAlternativePhone the displayAlternativePhone to set
   */
  public void setDisplayAlternativePhone(Boolean displayAlternativePhone) {
    this.displayAlternativePhone = displayAlternativePhone;
  }

  /**
   * @return the displayCellPhone
   */
  public Boolean getDisplayCellPhone() {
    return displayCellPhone;
  }

  /**
   * @param displayCellPhone the displayCellPhone to set
   */
  public void setDisplayCellPhone(Boolean displayCellPhone) {
    this.displayCellPhone = displayCellPhone;
  }

  /**
   * @return the displayAlternativeEmail
   */
  public Boolean getDisplayAlternativeEmail() {
    return displayAlternativeEmail;
  }

  /**
   * @param displayAlternativeEmail the displayAlternativeEmail to set
   */
  public void setDisplayAlternativeEmail(Boolean displayAlternativeEmail) {
    this.displayAlternativeEmail = displayAlternativeEmail;
  }

  /**
   * @return the userAlternativeEmail
   */
  public String getUserAlternativeEmail() {
    return userAlternativeEmail;
  }

  /**
   * @param userAlternativeEmail the userAlternativeEmail to set
   */
  public void setUserAlternativeEmail(String userAlternativeEmail) {
    this.userAlternativeEmail = userAlternativeEmail;
  }

}
