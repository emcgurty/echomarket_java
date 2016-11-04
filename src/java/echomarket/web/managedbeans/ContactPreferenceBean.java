package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Purpose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "cpb")
@RequestScoped
public class ContactPreferenceBean extends AbstractBean implements Serializable {

    public static ArrayList<Addresses> getParticpant_alternative() {
        return particpant_alternative;
    }

    public static void setParticpant_alternative(ArrayList<Addresses> aParticpant_alternative) {
        particpant_alternative = aParticpant_alternative;
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
    private static ArrayList<Addresses> particpant_alternative
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "NA", null, "NA", "NA", null, "99", null, "99", "alternative")));

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
        }

        String queryString = "from Addresses where participant_id = :bid AND address_type = :which";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", ubean.getUser_id())
                    .setParameter("which", "alternative")
                    .list();

        } catch (Exception e) {

        } finally {
            tx = null;
            hib = null;
        }

        Integer size_of_list = result.size();
        if (size_of_list == 0) {
            return getParticpant_alternative();
        } else {
            return result;
        }

    }

}
