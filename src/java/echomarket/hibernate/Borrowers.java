package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
  regarding using set: http://stackoverflow.com/questions/7711703/displaying-objects-from-a-set-collection-in-datatable-jsf-does-not-work
  
 */
@Entity
@Table(name = "borrowers")
public class Borrowers implements java.io.Serializable {

    
    private String borrower_id;
    private Set<ItemImages> itemImages = new HashSet<ItemImages>();
    private String user_id;
    private int contactDescribeId;
    private String organizationName;
    private int displayBorrowerOrganizationName;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private int displayBorrowerName;
    private int displayBorrowerAddress;
    private String homePhone;
    private String cellPhone;
    private String alternativePhone;
    private Integer publicDisplayHomePhone;
    private Integer publicDisplayCellPhone;
    private Integer publicDisplayAlternativePhone;
    private int useWhichContactAddress;
    private String emailAlternative;
    private String borrowerContactByEmail;
    private Integer borrowerContactByHomePhone;
    private Integer borrowerContactByCellPhone;
    private Integer borrowerContactByAlternativePhone;
    private String borrowerContactByFacebook;
    private String borrowerContactByTwitter;
    private String borrowerContactByInstagram;
    private String borrowerContactByLinkedIn;
    private String borrowerContactByOtherSocialMedia;
    private String borrowerContactByOtherSocialMediaAccess;
    private Integer categoryId;
    private String otherItemCategory;
    private String itemModel;
    private String itemDescription;
    private Integer itemConditionId;
    private Integer itemCount;
    private Integer goodwill;
    private Integer age18OrMore;
    private Integer isActive;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;
    private int approved;
    private int notifyLenders;
    private Integer receiveLenderNotification;
    private Integer isCommunity;
    private String remoteIp;
    private String comment;
    private String advertiserId;
    private int displayBorrowerAlternativeAddress;

    public Borrowers() {
        
        
    }

    public Borrowers(String id, int contactDescribeId, int displayBorrowerOrganizationName, String firstName, String lastName, int displayBorrowerName, int displayBorrowerAddress, int useWhichContactAddress, Date dateCreated, Date dateUpdated, Date dateDeleted, int approved, int notifyLenders, int displayBorrowerAlternativeAddress) {
        this.borrower_id = id;
        this.contactDescribeId = contactDescribeId;
        this.displayBorrowerOrganizationName = displayBorrowerOrganizationName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayBorrowerName = displayBorrowerName;
        this.displayBorrowerAddress = displayBorrowerAddress;
        this.useWhichContactAddress = useWhichContactAddress;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
        this.approved = approved;
        this.notifyLenders = notifyLenders;
        this.displayBorrowerAlternativeAddress = displayBorrowerAlternativeAddress;
    }

    public Borrowers(String id, String user_id, int contactDescribeId, String organizationName, int displayBorrowerOrganizationName, String otherDescribeYourself, String firstName, String mi, String lastName, int displayBorrowerName, int displayBorrowerAddress, String homePhone, String cellPhone, String alternativePhone, Integer publicDisplayHomePhone, Integer publicDisplayCellPhone, Integer publicDisplayAlternativePhone, int useWhichContactAddress, String emailAlternative, String borrowerContactByEmail, Integer borrowerContactByHomePhone, Integer borrowerContactByCellPhone, Integer borrowerContactByAlternativePhone, String borrowerContactByFacebook, String borrowerContactByTwitter, String borrowerContactByInstagram, String borrowerContactByLinkedIn, String borrowerContactByOtherSocialMedia, String borrowerContactByOtherSocialMediaAccess, Integer categoryId, String otherItemCategory, String itemModel, String itemDescription, Integer itemConditionId, Integer itemCount, Integer goodwill, Integer age18OrMore, Integer isActive, Date dateCreated, Date dateUpdated, Date dateDeleted, int approved, int notifyLenders, Integer receiveLenderNotification, Integer isCommunity, String remoteIp, String comment, String advertiserId, int displayBorrowerAlternativeAddress) {
        this.borrower_id = id;
        this.user_id = user_id;
        this.contactDescribeId = contactDescribeId;
        this.organizationName = organizationName;
        this.displayBorrowerOrganizationName = displayBorrowerOrganizationName;
        this.otherDescribeYourself = otherDescribeYourself;
        this.firstName = firstName;
        this.mi = mi;
        this.lastName = lastName;
        this.displayBorrowerName = displayBorrowerName;
        this.displayBorrowerAddress = displayBorrowerAddress;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.alternativePhone = alternativePhone;
        this.publicDisplayHomePhone = publicDisplayHomePhone;
        this.publicDisplayCellPhone = publicDisplayCellPhone;
        this.publicDisplayAlternativePhone = publicDisplayAlternativePhone;
        this.useWhichContactAddress = useWhichContactAddress;
        this.emailAlternative = emailAlternative;
        this.borrowerContactByEmail = borrowerContactByEmail;
        this.borrowerContactByHomePhone = borrowerContactByHomePhone;
        this.borrowerContactByCellPhone = borrowerContactByCellPhone;
        this.borrowerContactByAlternativePhone = borrowerContactByAlternativePhone;
        this.borrowerContactByFacebook = borrowerContactByFacebook;
        this.borrowerContactByTwitter = borrowerContactByTwitter;
        this.borrowerContactByInstagram = borrowerContactByInstagram;
        this.borrowerContactByLinkedIn = borrowerContactByLinkedIn;
        this.borrowerContactByOtherSocialMedia = borrowerContactByOtherSocialMedia;
        this.borrowerContactByOtherSocialMediaAccess = borrowerContactByOtherSocialMediaAccess;
        this.categoryId = categoryId;
        this.otherItemCategory = otherItemCategory;
        this.itemModel = itemModel;
        this.itemDescription = itemDescription;
        this.itemConditionId = itemConditionId;
        this.itemCount = itemCount;
        this.goodwill = goodwill;
        this.age18OrMore = age18OrMore;
        this.isActive = isActive;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
        this.approved = approved;
        this.notifyLenders = notifyLenders;
        this.receiveLenderNotification = receiveLenderNotification;
        this.isCommunity = isCommunity;
        this.remoteIp = remoteIp;
        this.comment = comment;
        this.advertiserId = advertiserId;
        this.displayBorrowerAlternativeAddress = displayBorrowerAlternativeAddress;
    }

//    @OneToMany(mappedBy = "borrowers", cascade = CascadeType.ALL)
//    public Set<ItemImages> getItemImages() {
//        return ItemImages;
//    }
    

    public int getContactDescribeId() {
        return this.contactDescribeId;
    }

    public void setContactDescribeId(int contactDescribeId) {
        this.contactDescribeId = contactDescribeId;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getDisplayBorrowerOrganizationName() {
        return this.displayBorrowerOrganizationName;
    }

    public void setDisplayBorrowerOrganizationName(int displayBorrowerOrganizationName) {
        this.displayBorrowerOrganizationName = displayBorrowerOrganizationName;
    }

    public String getOtherDescribeYourself() {
        return this.otherDescribeYourself;
    }

    public void setOtherDescribeYourself(String otherDescribeYourself) {
        this.otherDescribeYourself = otherDescribeYourself;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMi() {
        return this.mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDisplayBorrowerName() {
        return this.displayBorrowerName;
    }

    public void setDisplayBorrowerName(int displayBorrowerName) {
        this.displayBorrowerName = displayBorrowerName;
    }

    public int getDisplayBorrowerAddress() {
        return this.displayBorrowerAddress;
    }

    public void setDisplayBorrowerAddress(int displayBorrowerAddress) {
        this.displayBorrowerAddress = displayBorrowerAddress;
    }

    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return this.cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getAlternativePhone() {
        return this.alternativePhone;
    }

    public void setAlternativePhone(String alternativePhone) {
        this.alternativePhone = alternativePhone;
    }

    public Integer getPublicDisplayHomePhone() {
        return this.publicDisplayHomePhone;
    }

    public void setPublicDisplayHomePhone(Integer publicDisplayHomePhone) {
        this.publicDisplayHomePhone = publicDisplayHomePhone;
    }

    public Integer getPublicDisplayCellPhone() {
        return this.publicDisplayCellPhone;
    }

    public void setPublicDisplayCellPhone(Integer publicDisplayCellPhone) {
        this.publicDisplayCellPhone = publicDisplayCellPhone;
    }

    public Integer getPublicDisplayAlternativePhone() {
        return this.publicDisplayAlternativePhone;
    }

    public void setPublicDisplayAlternativePhone(Integer publicDisplayAlternativePhone) {
        this.publicDisplayAlternativePhone = publicDisplayAlternativePhone;
    }

    public int getUseWhichContactAddress() {
        return this.useWhichContactAddress;
    }

    public void setUseWhichContactAddress(int useWhichContactAddress) {
        this.useWhichContactAddress = useWhichContactAddress;
    }

    public String getEmailAlternative() {
        return this.emailAlternative;
    }

    public void setEmailAlternative(String emailAlternative) {
        this.emailAlternative = emailAlternative;
    }

    public String getBorrowerContactByEmail() {
        return this.borrowerContactByEmail;
    }

    public void setBorrowerContactByEmail(String borrowerContactByEmail) {
        this.borrowerContactByEmail = borrowerContactByEmail;
    }

    public Integer getBorrowerContactByHomePhone() {
        return this.borrowerContactByHomePhone;
    }

    public void setBorrowerContactByHomePhone(Integer borrowerContactByHomePhone) {
        this.borrowerContactByHomePhone = borrowerContactByHomePhone;
    }

    public Integer getBorrowerContactByCellPhone() {
        return this.borrowerContactByCellPhone;
    }

    public void setBorrowerContactByCellPhone(Integer borrowerContactByCellPhone) {
        this.borrowerContactByCellPhone = borrowerContactByCellPhone;
    }

    public Integer getBorrowerContactByAlternativePhone() {
        return this.borrowerContactByAlternativePhone;
    }

    public void setBorrowerContactByAlternativePhone(Integer borrowerContactByAlternativePhone) {
        this.borrowerContactByAlternativePhone = borrowerContactByAlternativePhone;
    }

    public String getBorrowerContactByFacebook() {
        return this.borrowerContactByFacebook;
    }

    public void setBorrowerContactByFacebook(String borrowerContactByFacebook) {
        this.borrowerContactByFacebook = borrowerContactByFacebook;
    }

    public String getBorrowerContactByTwitter() {
        return this.borrowerContactByTwitter;
    }

    public void setBorrowerContactByTwitter(String borrowerContactByTwitter) {
        this.borrowerContactByTwitter = borrowerContactByTwitter;
    }

    public String getBorrowerContactByInstagram() {
        return this.borrowerContactByInstagram;
    }

    public void setBorrowerContactByInstagram(String borrowerContactByInstagram) {
        this.borrowerContactByInstagram = borrowerContactByInstagram;
    }

    public String getBorrowerContactByLinkedIn() {
        return this.borrowerContactByLinkedIn;
    }

    public void setBorrowerContactByLinkedIn(String borrowerContactByLinkedIn) {
        this.borrowerContactByLinkedIn = borrowerContactByLinkedIn;
    }

    public String getBorrowerContactByOtherSocialMedia() {
        return this.borrowerContactByOtherSocialMedia;
    }

    public void setBorrowerContactByOtherSocialMedia(String borrowerContactByOtherSocialMedia) {
        this.borrowerContactByOtherSocialMedia = borrowerContactByOtherSocialMedia;
    }

    public String getBorrowerContactByOtherSocialMediaAccess() {
        return this.borrowerContactByOtherSocialMediaAccess;
    }

    public void setBorrowerContactByOtherSocialMediaAccess(String borrowerContactByOtherSocialMediaAccess) {
        this.borrowerContactByOtherSocialMediaAccess = borrowerContactByOtherSocialMediaAccess;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getOtherItemCategory() {
        return this.otherItemCategory;
    }

    public void setOtherItemCategory(String otherItemCategory) {
        this.otherItemCategory = otherItemCategory;
    }

    public String getItemModel() {
        return this.itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getItemConditionId() {
        return this.itemConditionId;
    }

    public void setItemConditionId(Integer itemConditionId) {
        this.itemConditionId = itemConditionId;
    }

    public Integer getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getGoodwill() {
        return this.goodwill;
    }

    public void setGoodwill(Integer goodwill) {
        this.goodwill = goodwill;
    }

    public Integer getAge18OrMore() {
        return this.age18OrMore;
    }

    public void setAge18OrMore(Integer age18OrMore) {
        this.age18OrMore = age18OrMore;
    }

    public Integer getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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

    public int getApproved() {
        return this.approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getNotifyLenders() {
        return this.notifyLenders;
    }

    public void setNotifyLenders(int notifyLenders) {
        this.notifyLenders = notifyLenders;
    }

    public Integer getReceiveLenderNotification() {
        return this.receiveLenderNotification;
    }

    public void setReceiveLenderNotification(Integer receiveLenderNotification) {
        this.receiveLenderNotification = receiveLenderNotification;
    }

    public Integer getIsCommunity() {
        return this.isCommunity;
    }

    public void setIsCommunity(Integer isCommunity) {
        this.isCommunity = isCommunity;
    }

    public String getRemoteIp() {
        return this.remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAdvertiserId() {
        return this.advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public int getDisplayBorrowerAlternativeAddress() {
        return this.displayBorrowerAlternativeAddress;
    }

    public void setDisplayBorrowerAlternativeAddress(int displayBorrowerAlternativeAddress) {
        this.displayBorrowerAlternativeAddress = displayBorrowerAlternativeAddress;
    }

//    public void addToItemImages(ItemImages ii) {
//        this.getItemImages().add(ii);
//        ii.get().add(this);
//    }
//
//    public void removeFromEvent(ItemImages event) {
//        this.getEvents().remove(event);
//        event.getParticipants().remove(this);
//    }
    /**
     * @return the item_images
     */
    @ManyToMany
    @JoinColumn(name = "borrower_id")
    public Set<ItemImages> getItemImages() {
        return itemImages;
    }

    /**
     * @param itemImages the itemImages to set
     */
    public void setItemImages(Set<ItemImages> itemImages) {
        
        this.itemImages = itemImages;
    }

    public void addItemImage(ItemImages ii){
        ii.setBorrowers(this);
        itemImages.add(ii);
        
    }
    /**
     * @return the borrower_id
     */
    @Id
    public String getBorrower_id() {
        return borrower_id;
    }

    /**
     * @param borrower_id the borrower_id to set
     */
    public void setBorrower_id(String borrower_id) {
        this.borrower_id = borrower_id;
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

}
