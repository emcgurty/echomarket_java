package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Purpose;
import echomarket.hibernate.Users;
import echomarket.hibernate.Lenders;
import echomarket.hibernate.ItemImages;
import echomarket.SendEmail.SendEmail;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

@ManagedBean(name = "ll")
@RequestScoped
public class LenderBean extends AbstractBean implements Serializable {


    private int contactDescribeId;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private int displayLenderName;
    private int displayLenderAddress;
    private String homePhone;
    private String cellPhone;
    private String alternativePhone;
    private Integer publicDisplayHomePhone;
    private Integer publicDisplayCellPhone;
    private Integer publicDisplayAlternativePhone;
    private Integer displayLenderAlternativeAddress;
    private int useWhichContactAddress;
    private String emailAlternative;
    private Integer borrowerContactByEmail;
    private Integer borrowerContactByHomePhone;
    private Integer borrowerContactByCellPhone;
    private Integer borrowerContactByAlternativePhone;
    private String borrowerContactByFacebook;
    private String borrowerContactByTwitter;
    private String borrowerContactByInstagram;
    private String borrowerContactByLinkedIn;
    private String borrowerContactByOtherSocialMedia;
    private String borrowerContactByOtherSocialMediaAccess;
    private Integer BComesToWhichAddress;
    private Integer meetBorrowerAtAgreedL2b;
    private Integer willDeliverToBorrowerPreferredL2b;
    private Integer thirdPartyPresenceL2b;
    private Integer lenderThirdPartyChoiceL2b;
    private Integer agreedThirdPartyChoiceL2b;
    private Integer BReturnsToWhichAddress;
    private Integer meetBorrowerAtAgreedB2l;
    private Integer willPickUpPreferredLocationB2l;
    private Integer thirdPartyPresenceB2l;
    private int lenderThirdPartyChoiceB2l;
    private Integer agreedThirdPartyChoiceB2l;
    private Integer borrowerChoice;
    private Integer categoryId;
    private String otherItemCategory;
    private String itemModel;
    private String itemDescription;
    private Integer itemCount;
    private Integer forFree;
    private Integer availableForPurchase;
    private BigDecimal availableForPurchaseAmount;
    private Integer smallFee;
    private BigDecimal smallFeeAmount;
    private Integer availableForDonation;
    private Integer donateAnonymous;
    private Integer trade;
    private String tradeItem;
    private Integer agreedNumberOfDays;
    private Integer agreedNumberOfHours;
    private Integer indefiniteDuration;
    private Integer presentDuringBorrowingPeriod;
    private Integer entirePeriod;
    private Integer partialPeriod;
    private Integer provideProperUseTraining;
    private String specificConditions;
    private Integer goodwill;
    private Integer age18OrMore;
    private Integer isActive;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;
    private String organizationName;
    private Integer displayLenderOrganizationName;
    private int approved;
    private Integer notifyBorrowers;
    private Integer receiveBorrowerNotifications;
    private int itemConditionId;
    private BigDecimal securityDepositAmount;
    private Integer securityDeposit;
    private Integer isCommunity;
    private String userId;
    private String remoteIp;
    private String comment;
    private String advertiserId;

    public LenderBean() {

    }

    public Addresses[] buildAddressList(String whichAddress) {

        List results = null;
        Addresses[] address_array = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String queryString = "from Addresses where lender_id = :lid  and address_type = :wa";
        results = session.createQuery(queryString).setParameter("lid", getId()).setParameter("wa", whichAddress).list();
        tx.commit();
        int result_size = results.size();
        if (result_size > 0) {
            address_array = new Addresses[result_size];
            for (int i = 0; i < result_size; i++) {
                Addresses a_array = (Addresses) results.get(i);
                /// String id, String lenderId, String borrowerId, String addressLine1, String addressLine2, String postalCode, 
                //String city, String province, String usStateId, String region, String countryId, String addressType
                address_array[i] = new Addresses(a_array.getId(), a_array.getLenderId(), a_array.getBorrowerId(), a_array.getAddressLine1(), a_array.getAddressLine2(), a_array.getPostalCode(),
                        a_array.getCity(), a_array.getProvince(), a_array.getUsStateId(), a_array.getRegion(), a_array.getCountryId(), a_array.getAddressType());
            }
        } else {

            String tmp_id = getId();
            address_array = new Addresses[1];
            address_array[0] = new Addresses(tmp_id, whichAddress);

        }
        return address_array;
    }

    public ItemImages[] buildImageAccess() {

        List results = null;
        ItemImages[] address_array = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String queryString = "from ItemImages where lender_id = :lid";
        results = session.createQuery(queryString).setParameter("lid", getId()).list();
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

            String tmp_id = getId();
            address_array = new ItemImages[1];
            address_array[0] = new ItemImages(tmp_id);

        }
        return address_array;

    }

//    /**
//     * @return the id
//     */
//    public String getId() {
//        return id;
//    }
//
//    /**
//     * @param id the id to set
//     */
//    public void setId(String id) {
//        this.id = id;
//    }

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
     * @return the displayLenderName
     */
    public int getDisplayLenderName() {
        return displayLenderName;
    }

    /**
     * @param displayLenderName the displayLenderName to set
     */
    public void setDisplayLenderName(int displayLenderName) {
        this.displayLenderName = displayLenderName;
    }

    /**
     * @return the displayLenderAddress
     */
    public int getDisplayLenderAddress() {
        return displayLenderAddress;
    }

    /**
     * @param displayLenderAddress the displayLenderAddress to set
     */
    public void setDisplayLenderAddress(int displayLenderAddress) {
        this.displayLenderAddress = displayLenderAddress;
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
     * @param publicDisplayAlternativePhone the publicDisplayAlternativePhone to set
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
    public Integer getBorrowerContactByEmail() {
        return borrowerContactByEmail;
    }

    /**
     * @param borrowerContactByEmail the borrowerContactByEmail to set
     */
    public void setBorrowerContactByEmail(Integer borrowerContactByEmail) {
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
     * @param borrowerContactByAlternativePhone the borrowerContactByAlternativePhone to set
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
     * @param borrowerContactByOtherSocialMedia the borrowerContactByOtherSocialMedia to set
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
     * @param borrowerContactByOtherSocialMediaAccess the borrowerContactByOtherSocialMediaAccess to set
     */
    public void setBorrowerContactByOtherSocialMediaAccess(String borrowerContactByOtherSocialMediaAccess) {
        this.borrowerContactByOtherSocialMediaAccess = borrowerContactByOtherSocialMediaAccess;
    }

    /**
     * @return the BComesToWhichAddress
     */
    public Integer getBComesToWhichAddress() {
        return BComesToWhichAddress;
    }

    /**
     * @param BComesToWhichAddress the BComesToWhichAddress to set
     */
    public void setBComesToWhichAddress(Integer BComesToWhichAddress) {
        this.BComesToWhichAddress = BComesToWhichAddress;
    }

    /**
     * @return the meetBorrowerAtAgreedL2b
     */
    public Integer getMeetBorrowerAtAgreedL2b() {
        return meetBorrowerAtAgreedL2b;
    }

    /**
     * @param meetBorrowerAtAgreedL2b the meetBorrowerAtAgreedL2b to set
     */
    public void setMeetBorrowerAtAgreedL2b(Integer meetBorrowerAtAgreedL2b) {
        this.meetBorrowerAtAgreedL2b = meetBorrowerAtAgreedL2b;
    }

    /**
     * @return the willDeliverToBorrowerPreferredL2b
     */
    public Integer getWillDeliverToBorrowerPreferredL2b() {
        return willDeliverToBorrowerPreferredL2b;
    }

    /**
     * @param willDeliverToBorrowerPreferredL2b the willDeliverToBorrowerPreferredL2b to set
     */
    public void setWillDeliverToBorrowerPreferredL2b(Integer willDeliverToBorrowerPreferredL2b) {
        this.willDeliverToBorrowerPreferredL2b = willDeliverToBorrowerPreferredL2b;
    }

    /**
     * @return the thirdPartyPresenceL2b
     */
    public Integer getThirdPartyPresenceL2b() {
        return thirdPartyPresenceL2b;
    }

    /**
     * @param thirdPartyPresenceL2b the thirdPartyPresenceL2b to set
     */
    public void setThirdPartyPresenceL2b(Integer thirdPartyPresenceL2b) {
        this.thirdPartyPresenceL2b = thirdPartyPresenceL2b;
    }

    /**
     * @return the lenderThirdPartyChoiceL2b
     */
    public Integer getLenderThirdPartyChoiceL2b() {
        return lenderThirdPartyChoiceL2b;
    }

    /**
     * @param lenderThirdPartyChoiceL2b the lenderThirdPartyChoiceL2b to set
     */
    public void setLenderThirdPartyChoiceL2b(Integer lenderThirdPartyChoiceL2b) {
        this.lenderThirdPartyChoiceL2b = lenderThirdPartyChoiceL2b;
    }

    /**
     * @return the agreedThirdPartyChoiceL2b
     */
    public Integer getAgreedThirdPartyChoiceL2b() {
        return agreedThirdPartyChoiceL2b;
    }

    /**
     * @param agreedThirdPartyChoiceL2b the agreedThirdPartyChoiceL2b to set
     */
    public void setAgreedThirdPartyChoiceL2b(Integer agreedThirdPartyChoiceL2b) {
        this.agreedThirdPartyChoiceL2b = agreedThirdPartyChoiceL2b;
    }

    /**
     * @return the BReturnsToWhichAddress
     */
    public Integer getBReturnsToWhichAddress() {
        return BReturnsToWhichAddress;
    }

    /**
     * @param BReturnsToWhichAddress the BReturnsToWhichAddress to set
     */
    public void setBReturnsToWhichAddress(Integer BReturnsToWhichAddress) {
        this.BReturnsToWhichAddress = BReturnsToWhichAddress;
    }

    /**
     * @return the meetBorrowerAtAgreedB2l
     */
    public Integer getMeetBorrowerAtAgreedB2l() {
        return meetBorrowerAtAgreedB2l;
    }

    /**
     * @param meetBorrowerAtAgreedB2l the meetBorrowerAtAgreedB2l to set
     */
    public void setMeetBorrowerAtAgreedB2l(Integer meetBorrowerAtAgreedB2l) {
        this.meetBorrowerAtAgreedB2l = meetBorrowerAtAgreedB2l;
    }

    /**
     * @return the willPickUpPreferredLocationB2l
     */
    public Integer getWillPickUpPreferredLocationB2l() {
        return willPickUpPreferredLocationB2l;
    }

    /**
     * @param willPickUpPreferredLocationB2l the willPickUpPreferredLocationB2l to set
     */
    public void setWillPickUpPreferredLocationB2l(Integer willPickUpPreferredLocationB2l) {
        this.willPickUpPreferredLocationB2l = willPickUpPreferredLocationB2l;
    }

    /**
     * @return the thirdPartyPresenceB2l
     */
    public Integer getThirdPartyPresenceB2l() {
        return thirdPartyPresenceB2l;
    }

    /**
     * @param thirdPartyPresenceB2l the thirdPartyPresenceB2l to set
     */
    public void setThirdPartyPresenceB2l(Integer thirdPartyPresenceB2l) {
        this.thirdPartyPresenceB2l = thirdPartyPresenceB2l;
    }

    /**
     * @return the lenderThirdPartyChoiceB2l
     */
    public int getLenderThirdPartyChoiceB2l() {
        return lenderThirdPartyChoiceB2l;
    }

    /**
     * @param lenderThirdPartyChoiceB2l the lenderThirdPartyChoiceB2l to set
     */
    public void setLenderThirdPartyChoiceB2l(int lenderThirdPartyChoiceB2l) {
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
    }

    /**
     * @return the agreedThirdPartyChoiceB2l
     */
    public Integer getAgreedThirdPartyChoiceB2l() {
        return agreedThirdPartyChoiceB2l;
    }

    /**
     * @param agreedThirdPartyChoiceB2l the agreedThirdPartyChoiceB2l to set
     */
    public void setAgreedThirdPartyChoiceB2l(Integer agreedThirdPartyChoiceB2l) {
        this.agreedThirdPartyChoiceB2l = agreedThirdPartyChoiceB2l;
    }

    /**
     * @return the borrowerChoice
     */
    public Integer getBorrowerChoice() {
        return borrowerChoice;
    }

    /**
     * @param borrowerChoice the borrowerChoice to set
     */
    public void setBorrowerChoice(Integer borrowerChoice) {
        this.borrowerChoice = borrowerChoice;
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
     * @return the forFree
     */
    public Integer getForFree() {
        return forFree;
    }

    /**
     * @param forFree the forFree to set
     */
    public void setForFree(Integer forFree) {
        this.forFree = forFree;
    }

    /**
     * @return the availableForPurchase
     */
    public Integer getAvailableForPurchase() {
        return availableForPurchase;
    }

    /**
     * @param availableForPurchase the availableForPurchase to set
     */
    public void setAvailableForPurchase(Integer availableForPurchase) {
        this.availableForPurchase = availableForPurchase;
    }

    /**
     * @return the availableForPurchaseAmount
     */
    public BigDecimal getAvailableForPurchaseAmount() {
        return availableForPurchaseAmount;
    }

    /**
     * @param availableForPurchaseAmount the availableForPurchaseAmount to set
     */
    public void setAvailableForPurchaseAmount(BigDecimal availableForPurchaseAmount) {
        this.availableForPurchaseAmount = availableForPurchaseAmount;
    }

    /**
     * @return the smallFee
     */
    public Integer getSmallFee() {
        return smallFee;
    }

    /**
     * @param smallFee the smallFee to set
     */
    public void setSmallFee(Integer smallFee) {
        this.smallFee = smallFee;
    }

    /**
     * @return the smallFeeAmount
     */
    public BigDecimal getSmallFeeAmount() {
        return smallFeeAmount;
    }

    /**
     * @param smallFeeAmount the smallFeeAmount to set
     */
    public void setSmallFeeAmount(BigDecimal smallFeeAmount) {
        this.smallFeeAmount = smallFeeAmount;
    }

    /**
     * @return the availableForDonation
     */
    public Integer getAvailableForDonation() {
        return availableForDonation;
    }

    /**
     * @param availableForDonation the availableForDonation to set
     */
    public void setAvailableForDonation(Integer availableForDonation) {
        this.availableForDonation = availableForDonation;
    }

    /**
     * @return the donateAnonymous
     */
    public Integer getDonateAnonymous() {
        return donateAnonymous;
    }

    /**
     * @param donateAnonymous the donateAnonymous to set
     */
    public void setDonateAnonymous(Integer donateAnonymous) {
        this.donateAnonymous = donateAnonymous;
    }

    /**
     * @return the trade
     */
    public Integer getTrade() {
        return trade;
    }

    /**
     * @param trade the trade to set
     */
    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    /**
     * @return the tradeItem
     */
    public String getTradeItem() {
        return tradeItem;
    }

    /**
     * @param tradeItem the tradeItem to set
     */
    public void setTradeItem(String tradeItem) {
        this.tradeItem = tradeItem;
    }

    /**
     * @return the agreedNumberOfDays
     */
    public Integer getAgreedNumberOfDays() {
        return agreedNumberOfDays;
    }

    /**
     * @param agreedNumberOfDays the agreedNumberOfDays to set
     */
    public void setAgreedNumberOfDays(Integer agreedNumberOfDays) {
        this.agreedNumberOfDays = agreedNumberOfDays;
    }

    /**
     * @return the agreedNumberOfHours
     */
    public Integer getAgreedNumberOfHours() {
        return agreedNumberOfHours;
    }

    /**
     * @param agreedNumberOfHours the agreedNumberOfHours to set
     */
    public void setAgreedNumberOfHours(Integer agreedNumberOfHours) {
        this.agreedNumberOfHours = agreedNumberOfHours;
    }

    /**
     * @return the indefiniteDuration
     */
    public Integer getIndefiniteDuration() {
        return indefiniteDuration;
    }

    /**
     * @param indefiniteDuration the indefiniteDuration to set
     */
    public void setIndefiniteDuration(Integer indefiniteDuration) {
        this.indefiniteDuration = indefiniteDuration;
    }

    /**
     * @return the presentDuringBorrowingPeriod
     */
    public Integer getPresentDuringBorrowingPeriod() {
        return presentDuringBorrowingPeriod;
    }

    /**
     * @param presentDuringBorrowingPeriod the presentDuringBorrowingPeriod to set
     */
    public void setPresentDuringBorrowingPeriod(Integer presentDuringBorrowingPeriod) {
        this.presentDuringBorrowingPeriod = presentDuringBorrowingPeriod;
    }

    /**
     * @return the entirePeriod
     */
    public Integer getEntirePeriod() {
        return entirePeriod;
    }

    /**
     * @param entirePeriod the entirePeriod to set
     */
    public void setEntirePeriod(Integer entirePeriod) {
        this.entirePeriod = entirePeriod;
    }

    /**
     * @return the partialPeriod
     */
    public Integer getPartialPeriod() {
        return partialPeriod;
    }

    /**
     * @param partialPeriod the partialPeriod to set
     */
    public void setPartialPeriod(Integer partialPeriod) {
        this.partialPeriod = partialPeriod;
    }

    /**
     * @return the provideProperUseTraining
     */
    public Integer getProvideProperUseTraining() {
        return provideProperUseTraining;
    }

    /**
     * @param provideProperUseTraining the provideProperUseTraining to set
     */
    public void setProvideProperUseTraining(Integer provideProperUseTraining) {
        this.provideProperUseTraining = provideProperUseTraining;
    }

    /**
     * @return the specificConditions
     */
    public String getSpecificConditions() {
        return specificConditions;
    }

    /**
     * @param specificConditions the specificConditions to set
     */
    public void setSpecificConditions(String specificConditions) {
        this.specificConditions = specificConditions;
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
     * @return the displayLenderOrganizationName
     */
    public Integer getDisplayLenderOrganizationName() {
        return displayLenderOrganizationName;
    }

    /**
     * @param displayLenderOrganizationName the displayLenderOrganizationName to set
     */
    public void setDisplayLenderOrganizationName(Integer displayLenderOrganizationName) {
        this.displayLenderOrganizationName = displayLenderOrganizationName;
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
     * @return the notifyBorrowers
     */
    public Integer getNotifyBorrowers() {
        return notifyBorrowers;
    }

    /**
     * @param notifyBorrowers the notifyBorrowers to set
     */
    public void setNotifyBorrowers(Integer notifyBorrowers) {
        this.notifyBorrowers = notifyBorrowers;
    }

    /**
     * @return the receiveBorrowerNotifications
     */
    public Integer getReceiveBorrowerNotifications() {
        return receiveBorrowerNotifications;
    }

    /**
     * @param receiveBorrowerNotifications the receiveBorrowerNotifications to set
     */
    public void setReceiveBorrowerNotifications(Integer receiveBorrowerNotifications) {
        this.receiveBorrowerNotifications = receiveBorrowerNotifications;
    }

    /**
     * @return the itemConditionId
     */
    public int getItemConditionId() {
        return itemConditionId;
    }

    /**
     * @param itemConditionId the itemConditionId to set
     */
    public void setItemConditionId(int itemConditionId) {
        this.itemConditionId = itemConditionId;
    }

    /**
     * @return the securityDepositAmount
     */
    public BigDecimal getSecurityDepositAmount() {
        return securityDepositAmount;
    }

    /**
     * @param securityDepositAmount the securityDepositAmount to set
     */
    public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
        this.securityDepositAmount = securityDepositAmount;
    }

    /**
     * @return the securityDeposit
     */
    public Integer getSecurityDeposit() {
        return securityDeposit;
    }

    /**
     * @param securityDeposit the securityDeposit to set
     */
    public void setSecurityDeposit(Integer securityDeposit) {
        this.securityDeposit = securityDeposit;
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
     * @return the displayLenderAlternativeAddress
     */
    public Integer getDisplayLenderAlternativeAddress() {
        return displayLenderAlternativeAddress;
    }

    /**
     * @param displayLenderAlternativeAddress the displayLenderAlternativeAddress to set
     */
    public void setDisplayLenderAlternativeAddress(Integer displayLenderAlternativeAddress) {
        this.displayLenderAlternativeAddress = displayLenderAlternativeAddress;
    }
    
    public String saveLenderRegistration() {
        String savedNewLenderRecord = "";
        System.out.println("Lets see return varbale");
        return savedNewLenderRecord;
    }
    
}
