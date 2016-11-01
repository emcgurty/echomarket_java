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
    private static ArrayList<Addresses> alternative
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "NA", null, "NA", "NA", null, "99", null, "99", "alternative")));

    public ContactPreferenceBean() {
    }

    @Id
    public String getContactPreferenceId() {
        return contactPreferenceId;
    }

    /**
     * @param contactPreferenceId the contactPreferenceId to set
     */
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
     * @param contactByOtherSocialMediaAccess the
     * contactByOtherSocialMediaAccess to set
     */
    public void setContactByOtherSocialMediaAccess(String contactByOtherSocialMediaAccess) {
        this.contactByOtherSocialMediaAccess = contactByOtherSocialMediaAccess;
    }

    private List getAddress() {

        List result = null;
        Addresses[] a_array = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Addresses where participant_id = :bid AND address_type = :which";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", ubean.getUser_id())
                    .setParameter("which", "alternative")
                    .list();
            tx.commit();
        } catch (Exception e) {

        } finally {
            tx = null;
            hib = null;
        }

        Integer size_of_list = result.size();
        if (size_of_list == 0) {
            return getAlternative();
        } else {
            return result;
        }

    }
    
    public ArrayList<Addresses> getAlternative() {
        return getAlternative();
    }

    public static void setAlternative(ArrayList<Addresses> aAlternative) {
        alternative = aAlternative;
    }

}
