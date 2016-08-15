package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Users;
import echomarket.hibernate.Borrowers;
import echomarket.hibernate.ItemImages;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "bb")
@RequestScoped
public class BorrowersBean extends AbstractBean implements Serializable {


    private String userId;
    private int contactDescribeId;
    private String organizationName;
    private int displayBorrowerOrganizationName;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private int displayBorrowerName;
    private int displayBorrowerAddress;
    private int displayBorrowerAlternativeAddress;
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
    
//  public Addresses(String id, String lenderId, String borrowerId, String addressLine1, String addressLine2, String postalCode, String city, String province, String usStateId, String region, String countryId, String addressType) {
    
    private static ArrayList<Addresses> primary
            = new ArrayList<Addresses>(Arrays.asList(
                    
                    new Addresses(UUID.randomUUID().toString(), null, null, null, null, null,null, null, null, null, null, "primary")
            
            
            ));

    private static ArrayList<Addresses> alternative
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(),  null, null, null, null, null,null, null, null, null, null, "alternative")));

    public ArrayList<Addresses> getPrimary() {
        return primary;
    }

    public ArrayList<Addresses> getAlternative() {
        return alternative;
    }

    /**
     * @param aPrimary the primary to set
     */
    public static void setPrimary(ArrayList<Addresses> aPrimary) {
        primary = aPrimary;
    }

    /**
     * @param aAlternative the alternative to set
     */
    public static void setAlternative(ArrayList<Addresses> aAlternative) {
        alternative = aAlternative;
    }



    public String saveBorrowerRegistration() {
        
        //  For the moment I do not want to set up HIbernate for table associations.  Instead used ArrayList, which is working fine.  Get return of properties
        // Test here
        String asdssd = getItemModel();
        // Test here 
        List padrs = getPrimary();
        /// Begin Hiernate transaction... first to save Borroweer detail, then with borrower_id, save addresses
        //Session sb = hib_session();
        //Transaction tx = sb.beginTransaction();
        
        //   Need to learn if I can pass ArrayList in creating new.  new Addresses adr = new Addresses(padrs);
        
        message(
                null,
                "BorrowerRegistionRecordSaved",
                null);

        return "index";
    }

    
// Need to eliminate this and perform same as done for addresses
    public ItemImages[] buildImageAccess() {

        List results = null;
        ItemImages[] address_array = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String queryString = "from ItemImages where borrower_id = :bid";
        results = session.createQuery(queryString).setParameter("bid", getId()).list();
        tx.commit();
        int result_size = results.size();
        if (result_size > 0) {
            address_array = new ItemImages[result_size];
            for (int i = 0; i < result_size; i++) {
                ItemImages a_array = (ItemImages) results.get(i);
                address_array[i] = new ItemImages(a_array.getId(), a_array.getBorrowerId(), a_array.getLenderId(), a_array.getImageContentType(),
                        a_array.getImageHeight(), a_array.getImageWidth(), a_array.getIsActive(), a_array.getDateCreated(), a_array.getDateDeleted(),
                        a_array.getDateUpdated(), a_array.getImageFileName(), a_array.getItemImageCaption(), a_array.getAdvertiserId());
            }
        } else {
//           Addresses create_record;    return the record with max id
//           Session hib = hib_session();
//           tx = hib.beginTransaction();
//            Criteria c = hib.createCriteria(Addresses.class);
//            c.addOrder(Order.desc("id"));
//            c.setMaxResults(1);
//            create_record = (Addresses) c.uniqueResult();
            String tmp_id = UUID.randomUUID().toString();
            //// Need to check not dup..
            address_array = new ItemImages[1];
            address_array[0] = new ItemImages(tmp_id);

        }
        return address_array;

    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the contactDescribeId
     */
    public int getContactDescribeId() {
        return contactDescribeId;
    }

    /**
     * @param contactDescribeId the contactDescribeId to set
     */
    public void setContactDescribeId(int contactDescribeId) {
        this.contactDescribeId = contactDescribeId;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the displayBorrowerOrganizationName
     */
    public int getDisplayBorrowerOrganizationName() {
        return displayBorrowerOrganizationName;
    }

    /**
     * @param displayBorrowerOrganizationName the
     * displayBorrowerOrganizationName to set
     */
    public void setDisplayBorrowerOrganizationName(int displayBorrowerOrganizationName) {
        this.displayBorrowerOrganizationName = displayBorrowerOrganizationName;
    }

    /**
     * @return the otherDescribeYourself
     */
    public String getOtherDescribeYourself() {
        return otherDescribeYourself;
    }

    /**
     * @param otherDescribeYourself the otherDescribeYourself to set
     */
    public void setOtherDescribeYourself(String otherDescribeYourself) {
        this.otherDescribeYourself = otherDescribeYourself;
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
     * @return the mi
     */
    public String getMi() {
        return mi;
    }

    /**
     * @param mi the mi to set
     */
    public void setMi(String mi) {
        this.mi = mi;
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
     * @return the displayBorrowerName
     */
    public int getDisplayBorrowerName() {
        return displayBorrowerName;
    }

    /**
     * @param displayBorrowerName the displayBorrowerName to set
     */
    public void setDisplayBorrowerName(int displayBorrowerName) {
        this.displayBorrowerName = displayBorrowerName;
    }

    /**
     * @return the displayBorrowerAddress
     */
    public int getDisplayBorrowerAddress() {
        return displayBorrowerAddress;
    }

    /**
     * @param displayBorrowerAddress the displayBorrowerAddress to set
     */
    public void setDisplayBorrowerAddress(int displayBorrowerAddress) {
        this.displayBorrowerAddress = displayBorrowerAddress;
    }

    /**
     * @return the homePhone
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * @param homePhone the homePhone to set
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * @return the cellPhone
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * @param cellPhone the cellPhone to set
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * @return the alternativePhone
     */
    public String getAlternativePhone() {
        return alternativePhone;
    }

    /**
     * @param alternativePhone the alternativePhone to set
     */
    public void setAlternativePhone(String alternativePhone) {
        this.alternativePhone = alternativePhone;
    }

    /**
     * @return the publicDisplayHomePhone
     */
    public Integer getPublicDisplayHomePhone() {
        return publicDisplayHomePhone;
    }

    /**
     * @param publicDisplayHomePhone the publicDisplayHomePhone to set
     */
    public void setPublicDisplayHomePhone(Integer publicDisplayHomePhone) {
        this.publicDisplayHomePhone = publicDisplayHomePhone;
    }

    /**
     * @return the publicDisplayCellPhone
     */
    public Integer getPublicDisplayCellPhone() {
        return publicDisplayCellPhone;
    }

    /**
     * @param publicDisplayCellPhone the publicDisplayCellPhone to set
     */
    public void setPublicDisplayCellPhone(Integer publicDisplayCellPhone) {
        this.publicDisplayCellPhone = publicDisplayCellPhone;
    }

    /**
     * @return the publicDisplayAlternativePhone
     */
    public Integer getPublicDisplayAlternativePhone() {
        return publicDisplayAlternativePhone;
    }

    /**
     * @param publicDisplayAlternativePhone the publicDisplayAlternativePhone to
     * set
     */
    public void setPublicDisplayAlternativePhone(Integer publicDisplayAlternativePhone) {
        this.publicDisplayAlternativePhone = publicDisplayAlternativePhone;
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
     * @return the emailAlternative
     */
    public String getEmailAlternative() {
        return emailAlternative;
    }

    /**
     * @param emailAlternative the emailAlternative to set
     */
    public void setEmailAlternative(String emailAlternative) {
        this.emailAlternative = emailAlternative;
    }

    /**
     * @return the borrowerContactByEmail
     */
    public String getBorrowerContactByEmail() {
        return borrowerContactByEmail;
    }

    /**
     * @param borrowerContactByEmail the borrowerContactByEmail to set
     */
    public void setBorrowerContactByEmail(String borrowerContactByEmail) {
        this.borrowerContactByEmail = borrowerContactByEmail;
    }

    /**
     * @return the borrowerContactByHomePhone
     */
    public Integer getBorrowerContactByHomePhone() {
        return borrowerContactByHomePhone;
    }

    /**
     * @param borrowerContactByHomePhone the borrowerContactByHomePhone to set
     */
    public void setBorrowerContactByHomePhone(Integer borrowerContactByHomePhone) {
        this.borrowerContactByHomePhone = borrowerContactByHomePhone;
    }

    /**
     * @return the borrowerContactByCellPhone
     */
    public Integer getBorrowerContactByCellPhone() {
        return borrowerContactByCellPhone;
    }

    /**
     * @param borrowerContactByCellPhone the borrowerContactByCellPhone to set
     */
    public void setBorrowerContactByCellPhone(Integer borrowerContactByCellPhone) {
        this.borrowerContactByCellPhone = borrowerContactByCellPhone;
    }

    /**
     * @return the borrowerContactByAlternativePhone
     */
    public Integer getBorrowerContactByAlternativePhone() {
        return borrowerContactByAlternativePhone;
    }

    /**
     * @param borrowerContactByAlternativePhone the
     * borrowerContactByAlternativePhone to set
     */
    public void setBorrowerContactByAlternativePhone(Integer borrowerContactByAlternativePhone) {
        this.borrowerContactByAlternativePhone = borrowerContactByAlternativePhone;
    }

    /**
     * @return the borrowerContactByFacebook
     */
    public String getBorrowerContactByFacebook() {
        return borrowerContactByFacebook;
    }

    /**
     * @param borrowerContactByFacebook the borrowerContactByFacebook to set
     */
    public void setBorrowerContactByFacebook(String borrowerContactByFacebook) {
        this.borrowerContactByFacebook = borrowerContactByFacebook;
    }

    /**
     * @return the borrowerContactByTwitter
     */
    public String getBorrowerContactByTwitter() {
        return borrowerContactByTwitter;
    }

    /**
     * @param borrowerContactByTwitter the borrowerContactByTwitter to set
     */
    public void setBorrowerContactByTwitter(String borrowerContactByTwitter) {
        this.borrowerContactByTwitter = borrowerContactByTwitter;
    }

    /**
     * @return the borrowerContactByInstagram
     */
    public String getBorrowerContactByInstagram() {
        return borrowerContactByInstagram;
    }

    /**
     * @param borrowerContactByInstagram the borrowerContactByInstagram to set
     */
    public void setBorrowerContactByInstagram(String borrowerContactByInstagram) {
        this.borrowerContactByInstagram = borrowerContactByInstagram;
    }

    /**
     * @return the borrowerContactByLinkedIn
     */
    public String getBorrowerContactByLinkedIn() {
        return borrowerContactByLinkedIn;
    }

    /**
     * @param borrowerContactByLinkedIn the borrowerContactByLinkedIn to set
     */
    public void setBorrowerContactByLinkedIn(String borrowerContactByLinkedIn) {
        this.borrowerContactByLinkedIn = borrowerContactByLinkedIn;
    }

    /**
     * @return the borrowerContactByOtherSocialMedia
     */
    public String getBorrowerContactByOtherSocialMedia() {
        return borrowerContactByOtherSocialMedia;
    }

    /**
     * @param borrowerContactByOtherSocialMedia the
     * borrowerContactByOtherSocialMedia to set
     */
    public void setBorrowerContactByOtherSocialMedia(String borrowerContactByOtherSocialMedia) {
        this.borrowerContactByOtherSocialMedia = borrowerContactByOtherSocialMedia;
    }

    /**
     * @return the borrowerContactByOtherSocialMediaAccess
     */
    public String getBorrowerContactByOtherSocialMediaAccess() {
        return borrowerContactByOtherSocialMediaAccess;
    }

    /**
     * @param borrowerContactByOtherSocialMediaAccess the
     * borrowerContactByOtherSocialMediaAccess to set
     */
    public void setBorrowerContactByOtherSocialMediaAccess(String borrowerContactByOtherSocialMediaAccess) {
        this.borrowerContactByOtherSocialMediaAccess = borrowerContactByOtherSocialMediaAccess;
    }

    /**
     * @return the categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the otherItemCategory
     */
    public String getOtherItemCategory() {
        return otherItemCategory;
    }

    /**
     * @param otherItemCategory the otherItemCategory to set
     */
    public void setOtherItemCategory(String otherItemCategory) {
        this.otherItemCategory = otherItemCategory;
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
     * @return the itemConditionId
     */
    public Integer getItemConditionId() {
        return itemConditionId;
    }

    /**
     * @param itemConditionId the itemConditionId to set
     */
    public void setItemConditionId(Integer itemConditionId) {
        this.itemConditionId = itemConditionId;
    }

    /**
     * @return the itemCount
     */
    public Integer getItemCount() {
        return itemCount;
    }

    /**
     * @param itemCount the itemCount to set
     */
    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * @return the goodwill
     */
    public Integer getGoodwill() {
        return goodwill;
    }

    /**
     * @param goodwill the goodwill to set
     */
    public void setGoodwill(Integer goodwill) {
        this.goodwill = goodwill;
    }

    /**
     * @return the age18OrMore
     */
    public Integer getAge18OrMore() {
        return age18OrMore;
    }

    /**
     * @param age18OrMore the age18OrMore to set
     */
    public void setAge18OrMore(Integer age18OrMore) {
        this.age18OrMore = age18OrMore;
    }

    /**
     * @return the isActive
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the dateUpdated
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * @param dateUpdated the dateUpdated to set
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * @return the dateDeleted
     */
    public Date getDateDeleted() {
        return dateDeleted;
    }

    /**
     * @param dateDeleted the dateDeleted to set
     */
    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    /**
     * @return the approved
     */
    public int getApproved() {
        return approved;
    }

    /**
     * @param approved the approved to set
     */
    public void setApproved(int approved) {
        this.approved = approved;
    }

    /**
     * @return the notifyLenders
     */
    public int getNotifyLenders() {
        return notifyLenders;
    }

    /**
     * @param notifyLenders the notifyLenders to set
     */
    public void setNotifyLenders(int notifyLenders) {
        this.notifyLenders = notifyLenders;
    }

    /**
     * @return the receiveLenderNotification
     */
    public Integer getReceiveLenderNotification() {
        return receiveLenderNotification;
    }

    /**
     * @param receiveLenderNotification the receiveLenderNotification to set
     */
    public void setReceiveLenderNotification(Integer receiveLenderNotification) {
        this.receiveLenderNotification = receiveLenderNotification;
    }

    /**
     * @return the isCommunity
     */
    public Integer getIsCommunity() {
        return isCommunity;
    }

    /**
     * @param isCommunity the isCommunity to set
     */
    public void setIsCommunity(Integer isCommunity) {
        this.isCommunity = isCommunity;
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

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the advertiserId
     */
    public String getAdvertiserId() {
        return advertiserId;
    }

    /**
     * @param advertiserId the advertiserId to set
     */
    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    /**
     * @return the displayBorrowerAlternativeAddress
     */
    public int getDisplayBorrowerAlternativeAddress() {
        return displayBorrowerAlternativeAddress;
    }

    /**
     * @param displayBorrowerAlternativeAddress the
     * displayBorrowerAlternativeAddress to set
     */
    public void setDisplayBorrowerAlternativeAddress(int displayBorrowerAlternativeAddress) {
        this.displayBorrowerAlternativeAddress = displayBorrowerAlternativeAddress;
    }

}
