package echomarket.hibernate;

import java.util.Date;

public class ContactPreference implements java.io.Serializable {

    private String contactPreferenceId;
    private String participant_id;
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
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;

    public ContactPreference() {
    }

    public ContactPreference(String contactPreferenceId, int useWhichContactAddress, Date dateCreated, Date dateUpdated, Date dateDeleted) {
        this.contactPreferenceId = contactPreferenceId;
        this.useWhichContactAddress = useWhichContactAddress;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
    }

    //update
    public ContactPreference(String contactPreferenceId, String participant_id, String itemId, int useWhichContactAddress, String contactByChat, String contactByEmail, Integer contactByHomePhone, Integer contactByCellPhone, Integer contactByAlternativePhone, String contactByFacebook, String contactByTwitter, String contactByInstagram, String contactByLinkedIn, String contactByOtherSocialMedia, String contactByOtherSocialMediaAccess, Date dateUpdated) {
    
        this.contactPreferenceId = contactPreferenceId;
        this.participant_id = participant_id;
        this.itemId = itemId;
        this.useWhichContactAddress = useWhichContactAddress;
        this.contactByChat = contactByChat;
        this.contactByEmail = contactByEmail;
        this.contactByHomePhone = contactByHomePhone;
        this.contactByCellPhone = contactByCellPhone;
        this.contactByAlternativePhone = contactByAlternativePhone;
        this.contactByFacebook = contactByFacebook;
        this.contactByTwitter = contactByTwitter;
        this.contactByInstagram = contactByInstagram;
        this.contactByLinkedIn = contactByLinkedIn;
        this.contactByOtherSocialMedia = contactByOtherSocialMedia;
        this.contactByOtherSocialMediaAccess = contactByOtherSocialMediaAccess;
        this.dateUpdated = dateUpdated;

    }

    public String getContactPreferenceId() {
        return this.contactPreferenceId;
    }

    public void setContactPreferenceId(String contactPreferenceId) {
        this.contactPreferenceId = contactPreferenceId;
    }

    public String getParticipant_id() {
        return this.participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public int getUseWhichContactAddress() {
        return this.useWhichContactAddress;
    }

    public void setUseWhichContactAddress(int useWhichContactAddress) {
        this.useWhichContactAddress = useWhichContactAddress;
    }

    public String getContactByChat() {
        return this.contactByChat;
    }

    public void setContactByChat(String contactByChat) {
        this.contactByChat = contactByChat;
    }

    public String getContactByEmail() {
        return this.contactByEmail;
    }

    public void setContactByEmail(String contactByEmail) {
        this.contactByEmail = contactByEmail;
    }

    public Integer getContactByHomePhone() {
        return this.contactByHomePhone;
    }

    public void setContactByHomePhone(Integer contactByHomePhone) {
        this.contactByHomePhone = contactByHomePhone;
    }

    public Integer getContactByCellPhone() {
        return this.contactByCellPhone;
    }

    public void setContactByCellPhone(Integer contactByCellPhone) {
        this.contactByCellPhone = contactByCellPhone;
    }

    public Integer getContactByAlternativePhone() {
        return this.contactByAlternativePhone;
    }

    public void setContactByAlternativePhone(Integer contactByAlternativePhone) {
        this.contactByAlternativePhone = contactByAlternativePhone;
    }

    public String getContactByFacebook() {
        return this.contactByFacebook;
    }

    public void setContactByFacebook(String contactByFacebook) {
        this.contactByFacebook = contactByFacebook;
    }

    public String getContactByTwitter() {
        return this.contactByTwitter;
    }

    public void setContactByTwitter(String contactByTwitter) {
        this.contactByTwitter = contactByTwitter;
    }

    public String getContactByInstagram() {
        return this.contactByInstagram;
    }

    public void setContactByInstagram(String contactByInstagram) {
        this.contactByInstagram = contactByInstagram;
    }

    public String getContactByLinkedIn() {
        return this.contactByLinkedIn;
    }

    public void setContactByLinkedIn(String contactByLinkedIn) {
        this.contactByLinkedIn = contactByLinkedIn;
    }

    public String getContactByOtherSocialMedia() {
        return this.contactByOtherSocialMedia;
    }

    public void setContactByOtherSocialMedia(String contactByOtherSocialMedia) {
        this.contactByOtherSocialMedia = contactByOtherSocialMedia;
    }

    public String getContactByOtherSocialMediaAccess() {
        return this.contactByOtherSocialMediaAccess;
    }

    public void setContactByOtherSocialMediaAccess(String contactByOtherSocialMediaAccess) {
        this.contactByOtherSocialMediaAccess = contactByOtherSocialMediaAccess;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Date getDateDeleted() {
        return this.dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
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


}
