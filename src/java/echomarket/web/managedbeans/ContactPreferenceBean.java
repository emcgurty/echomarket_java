package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.ContactPreference;
import echomarket.hibernate.Purpose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

//See for https://github.com/lovelle/jquery-chat
@Named
@ManagedBean(name = "cpb")
@SessionScoped
public class ContactPreferenceBean extends AbstractBean implements Serializable {

    public static ArrayList<Addresses> getParticipant_alternative() {
        return participant_alternative;
    }

    public static void setParticipant_alternative(ArrayList<Addresses> aParticipant_alternative) {
        participant_alternative = aParticipant_alternative;
    }

    @Inject
    UserBean ubean;
    private String contactPreferenceId;
    private String participantId;
    private String itemId;
    private int useWhichContactAddress;
    private String contactByChat;
    private String contactByEmail;
    private Integer contactByHomePhone;
    private Integer contactByCellPhone;
    private Integer contactByAlternativePhone;
    private String contactByFacebook;
    private String contactByTwitter;
    private String contactByInstagram;
    private String contactByLinkedIn;
    private String contactByOtherSocialMedia;
    private String contactByOtherSocialMediaAccess;
    private static ArrayList<Addresses> participant_alternative
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null, null, null, null, null, "99", null, "99", "alternative")));

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
     * @return the participantId
     */
    public String getParticipantId() {
        return participantId;
    }

    /**
     * @param participantId the participantId to set
     */
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
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
    public int getUseWhichContactAddress() {
        return useWhichContactAddress;
    }

    /**
     * @param useWhichContactAddress the useWhichContactAddress to set
     */
    public void setUseWhichContactAddress(int useWhichContactAddress) {
        this.useWhichContactAddress = useWhichContactAddress;
    }

    /**
     * @return the contactByChat
     */
    public String getContactByChat() {

        return contactByChat;

    }

    /**
     * @param contactByChat the contactByChat to set
     */
    public void setContactByChat(String contactByChat) {
        this.contactByChat = contactByChat;
    }

    /**
     * @return the contactByEmail
     */
    public String getContactByEmail() {
        return contactByEmail;
    }

    /**
     * @param contactByEmail the contactByEmail to set
     */
    public void setContactByEmail(String contactByEmail) {
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
        if ((contactByFacebook != null) || (ubean.getEditable() == 5)) {
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
        if ((contactByTwitter != null) || (ubean.getEditable() == 5)) {
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
        if ((contactByInstagram != null) || (ubean.getEditable() == 5)) {
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
        if ((contactByLinkedIn != null) || (ubean.getEditable() == 5)) {
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
        if ((contactByOtherSocialMedia != null) || (ubean.getEditable() == 5)) {
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
        if ((contactByOtherSocialMediaAccess != null) || (ubean.getEditable() == 5)) {
            return contactByOtherSocialMediaAccess;
        } else {
            return "Not provided";
        }

    }

    /**
     * @param contactByOtherSocialMediaAccess the
     * contactByOtherSocialMediaAccess to set
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

        if ((this.itemId == null)) {
            /// Create new record
            ContactPreference part = new ContactPreference(getId(), ubean.getUser_id(), itemId, useWhichContactAddress, contactByChat, contactByEmail, contactByHomePhone, contactByCellPhone, contactByAlternativePhone, contactByFacebook, contactByTwitter, contactByInstagram, contactByLinkedIn, contactByOtherSocialMedia, contactByOtherSocialMediaAccess, new Date());

            try {
                sb = hib_session();
                tx = sb.beginTransaction();
                sb.save(part);
                tx.commit();
                successTransaction = true;
            } catch (Exception ex) {
                tx.rollback();
                System.out.println("Error in Save Contact Preferences");
                Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // sb = null;
                //  tx = null;
            }

        } else {
            
            cp_list = getCurrentCP(ubean.getUser_id());
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
            } catch (Exception ex) {
                tx.rollback();
                System.out.println("Error in Update Contact Preferences");
                Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                //sb = null;
                //tx = null;
            }

        }

        if (successTransaction = true) {
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            try {
                Addresses altadd = (Addresses) participant_alternative.get(0);
                String uid = altadd.getParticipantId();
                if (uid.equals(ubean.getUser_id())) {
                    sb.update(altadd);
                } else {
                    altadd.setParticipantId(ubean.getUser_id());
                    sb.save(altadd);
                }
                tx.commit();
                successTransaction = true;
            } catch (Exception ex) {
                tx.rollback();
                System.out.println("Error in Update Contact Preferences Address");
                Logger.getLogger(ContactPreferenceBean.class.getName()).log(Level.SEVERE, null, ex);
                successTransaction = false;
            } finally {
                sb = null;
                tx = null;
                cp_list = null;
            }

        }
        if (successTransaction = true) {
            ubean.setEditable(4);
        } else {
            message(null, "UpdateOrSaveOfCPNotSuccessful", null);
            ubean.setEditable(5);
        }
//        return "user_detail?faces-redirect=true";
        return "user_detail";
    }

    public List getAddress() {

        Addresses[] a_array = null;
        List result = null;
        Session hib;
        Transaction tx;
        hib = null;
        tx = null;

        try {
            hib = hib_session();
            tx = hib.beginTransaction();
        } catch (Exception ex) {
            System.out.println("Error at line 335 in getAddress");
            ex.printStackTrace();
        }

        String queryString = "from Addresses where participant_id = :bid AND address_type = :which";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", ubean.getUser_id())
                    .setParameter("which", "alternative")
                    .list();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error at line 344 in CP Bean");
            e.printStackTrace();

        } finally {
//            hib.close();
            hib = null;
            tx = null;

        }

        Integer size_of_list = result.size();
        if (size_of_list == 0) {
            return getParticipant_alternative();
        } else {
            return result;
        }

    }

    public String load_ud(String pid) {

        List partlist = null;
        partlist = getCurrentCP(pid);
        if (partlist.size() == 1) {
            ContactPreference pp = (ContactPreference) partlist.get(0);
            this.itemId = pp.getItemId();
            this.participantId = pp.getParticipantId();
            this.useWhichContactAddress = pp.getUseWhichContactAddress();
            this.contactByChat = pp.getContactByChat();
            this.contactByEmail = pp.getContactByEmail();
            this.contactByHomePhone = pp.getContactByHomePhone();
            this.contactByCellPhone = pp.getContactByCellPhone();
            this.contactByAlternativePhone = pp.getContactByAlternativePhone();
            this.contactByFacebook = pp.getContactByFacebook();
            this.contactByTwitter = pp.getContactByTwitter();
            this.contactByInstagram = pp.getContactByInstagram();
            this.contactByLinkedIn = pp.getContactByLinkedIn();
            this.contactByOtherSocialMedia = pp.getContactByOtherSocialMedia();
            this.contactByOtherSocialMediaAccess = pp.getContactByOtherSocialMediaAccess();
            //this.setParticipant_alternative((List)getAddress().get(0));

        }
        ubean.setEditable(5);
        partlist = null;

        return "user_detail";
//        return "user_detail?faces-redirect=true";
    }

    public List getCurrentCP(String pid) {

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

}
