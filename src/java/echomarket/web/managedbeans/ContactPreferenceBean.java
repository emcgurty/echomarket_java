package echomarket.web.managedbeans;

import echomarket.hibernate.Purpose;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named(value = "cpb")
@RequestScoped
public class ContactPreferenceBean extends AbstractBean implements Serializable {

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
     * @param contactByOtherSocialMediaAccess the contactByOtherSocialMediaAccess to set
     */
    public void setContactByOtherSocialMediaAccess(String contactByOtherSocialMediaAccess) {
        this.contactByOtherSocialMediaAccess = contactByOtherSocialMediaAccess;
    }
}
