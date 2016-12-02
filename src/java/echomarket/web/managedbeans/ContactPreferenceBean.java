package echomarket.web.managedbeans;

import echomarket.hibernate.ContactPreference;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean
@RequestScoped
public class ContactPreferenceBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
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
    if ((contactByFacebook != null) || (ubean.getEditable() == 1)) {
      return contactByFacebook;
    } else {
      return "Not provided";
    }

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
    if ((contactByTwitter != null) || (ubean.getEditable() == 1)) {
      return contactByTwitter;
    } else {
      return "Not provided";
    }

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
    if ((contactByInstagram != null) || (ubean.getEditable() == 1)) {
      return contactByInstagram;
    } else {
      return "Not provided";
    }

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
    if ((contactByLinkedIn != null) || (ubean.getEditable() == 1)) {
      return contactByLinkedIn;
    } else {
      return "Not provided";
    }

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
    if ((contactByOtherSocialMedia != null) || (ubean.getEditable() == 1)) {
      return contactByOtherSocialMedia;
    } else {
      return "Not provided";
    }

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
    if ((contactByOtherSocialMediaAccess != null) || (ubean.getEditable() == 1)) {
      return contactByOtherSocialMediaAccess;
    } else {
      return "Not provided";
    }

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

    Map<String, String> params = null;
    String addNewCP = null;
    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      addNewCP = params.get("action");
    } catch (Exception ex) {
    }

    if (contactPreferenceId.isEmpty() == true) {

      ContactPreference part = new ContactPreference(getId(), ubean.getParticipant_id(), itemId, useWhichContactAddress, contactByChat, contactByEmail, contactByHomePhone, contactByCellPhone, contactByAlternativePhone, contactByFacebook, contactByTwitter, contactByInstagram, contactByLinkedIn, contactByOtherSocialMedia, contactByOtherSocialMediaAccess, new Date());

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

      if (itemId.isEmpty() == false) {
        cp_list = getCurrentCP_Iid(ubean.getParticipant_id(), itemId);
      } else {
        cp_list = getCurrentCP(ubean.getParticipant_id());
      }
      if (cp_list != null) {
        if (cp_list.size() == 1) {
          ContactPreference part = (ContactPreference) cp_list.get(0);
          part.setItemId(itemId);
          part.setUseWhichContactAddress(useWhichContactAddress);
          part.setContactByChat(contactByChat);
          part.setContactByEmail(contactByEmail);
          part.setContactByHomePhone(contactByHomePhone);
          part.setContactByAlternativePhone(contactByAlternativePhone);
          part.setContactByCellPhone(contactByCellPhone);
          part.setContactByAlternativePhone(contactByAlternativePhone);
          part.setContactByFacebook(contactByFacebook);
          part.setContactByTwitter(contactByTwitter);
          part.setContactByInstagram(contactByInstagram);
          part.setContactByLinkedIn(contactByLinkedIn);
          part.setContactByOtherSocialMedia(contactByOtherSocialMedia);
          part.setContactByOtherSocialMediaAccess(contactByOtherSocialMediaAccess);
          part.setDateUpdated(new Date());

          sb = hib_session();
          tx = sb.beginTransaction();
          try {
            sb.update(part);
            tx.commit();
            successTransaction = true;
            message(null, "CPUpdated", null);
            ubean.setEditable(1);
          } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in Update Contact Preferences");
            Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
            message(null, "UpdateOrSaveOfCPNotSuccessful", null);
            ubean.setEditable(0);
          } finally {
            sb = null;
            tx = null;
          }
        }
      }
    }
    return load_ud(ubean.getParticipant_id());
  }

  public String load_ud(String pid) {

    List partlist = null;
    Map<String, String> params = null;
    String strIid = null;
    String action = null;

    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      strIid = params.get("iid");
      /// This doesn't work, hence below try/catch on strIid
      if (strIid.isEmpty() == true) {
        strIid = null;
      }
    } catch (Exception ex) {
    }

    if (ubean.getEditable() == 0) {
      ubean.setEditable(1);
    } else {
      ubean.setEditable(0);
    }

    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      action = params.get("action");
    } catch (Exception ex) {
    }

    if (action != null) {
      if ("edit".equals(action)) {
        ubean.setEditable(1);
      }
    }

    try {
      if (strIid != null) {
        partlist = getCurrentCP_Iid(pid, strIid);
      } else if (itemId != null) {
        partlist = getCurrentCP_Iid(pid, itemId);
      }
    } catch (Exception ex) {
      if (strIid.isEmpty() == false) {
        partlist = getCurrentCP_Iid(pid, strIid);
      } else if (itemId != null) {
        partlist = getCurrentCP_Iid(pid, itemId);
      }
    }

    if (partlist == null) {
      partlist = getCurrentCP(pid);
    }
    if (partlist != null) {
      if (partlist.size() == 1) {
        ContactPreference pp = (ContactPreference) partlist.get(0);
        if (pp != null) {
          setContactPreferenceId(pp.getContactPreferenceId());
          setItemId(pp.getItemId());
          setParticipant_id(pp.getParticipant_id());
          useWhichContactAddress = pp.getUseWhichContactAddress();
          contactByChat = pp.getContactByChat();
          contactByEmail = pp.getContactByEmail();
          contactByHomePhone = pp.getContactByHomePhone();
          contactByCellPhone = pp.getContactByCellPhone();
          contactByAlternativePhone = pp.getContactByAlternativePhone();
          contactByFacebook = pp.getContactByFacebook();
          contactByTwitter = pp.getContactByTwitter();
          contactByInstagram = pp.getContactByInstagram();
          contactByLinkedIn = pp.getContactByLinkedIn();
          contactByOtherSocialMedia = pp.getContactByOtherSocialMedia();
          contactByOtherSocialMediaAccess = pp.getContactByOtherSocialMediaAccess();
        }
      }
    }
    partlist = null;

    return "user_contact_preferences";

  }

  private List getCurrentCP(String pid) {

    List result = null;
    Session session = hib_session();
    Transaction tx = session.beginTransaction();
    String query = null;
    try {
      query = "FROM ContactPreference WHERE participant_id = :pid  ORDER BY participant_id, dateCreated";
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

  private List getCurrentCP_Iid(String pid, String iid) {

    List result = null;
    Session session = hib_session();
    Transaction tx = session.beginTransaction();
    String query = null;
    try {
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

}
