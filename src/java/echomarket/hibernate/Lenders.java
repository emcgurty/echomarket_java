package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="lenders")
public class Lenders implements java.io.Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String lender_id;
    private int contactDescribeId;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private int displayLenderName;
    private int displayLenderAddress;
    private int displayLenderAlternativeAddress;
    private String homePhone;
    private String cellPhone;
    private String alternativePhone;
    private Integer publicDisplayHomePhone;
    private Integer publicDisplayCellPhone;
    private Integer publicDisplayAlternativePhone;
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
    private Integer meetBorrowerAtAgreedL2B;
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
    private String user_id;
    private String remoteIp;
    private String comment;
    private String advertiserId;

    public Lenders() {
    }

    public Lenders(String id, int contactDescribeId, String firstName, String lastName, int displayLenderName, int displayLenderAddress, int useWhichContactAddress, int lenderThirdPartyChoiceB2l, Date dateCreated, Date dateUpdated, Date dateDeleted, int approved, int itemConditionId) {
        this.lender_id = id;
        this.contactDescribeId = contactDescribeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayLenderName = displayLenderName;
        this.displayLenderAddress = displayLenderAddress;
        this.useWhichContactAddress = useWhichContactAddress;
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
        this.approved = approved;
        this.itemConditionId = itemConditionId;
    }

    public Lenders(String id, String user_id, int contactDescribeId, String otherDescribeYourself, String firstName, String mi, String lastName, int displayLenderName, int displayLenderAddress, int displayLenderAlternativeAddress, String homePhone, String cellPhone, String alternativePhone, Integer publicDisplayHomePhone, Integer publicDisplayCellPhone, Integer publicDisplayAlternativePhone, int useWhichContactAddress, String emailAlternative, Integer borrowerContactByEmail, Integer borrowerContactByHomePhone, Integer borrowerContactByCellPhone, Integer borrowerContactByAlternativePhone, String borrowerContactByFacebook, String borrowerContactByTwitter, String borrowerContactByInstagram, String borrowerContactByLinkedIn, String borrowerContactByOtherSocialMedia, String borrowerContactByOtherSocialMediaAccess, Integer BComesToWhichAddress, Integer meetBorrowerAtAgreedL2b, Integer willDeliverToBorrowerPreferredL2b, Integer thirdPartyPresenceL2b, Integer lenderThirdPartyChoiceL2b, Integer agreedThirdPartyChoiceL2b, Integer BReturnsToWhichAddress, Integer meetBorrowerAtAgreedB2l, Integer willPickUpPreferredLocationB2l, Integer thirdPartyPresenceB2l, int lenderThirdPartyChoiceB2l, Integer agreedThirdPartyChoiceB2l, Integer borrowerChoice, Integer categoryId, String otherItemCategory, String itemModel, String itemDescription, Integer itemCount, Integer forFree, Integer availableForPurchase, BigDecimal availableForPurchaseAmount, Integer smallFee, BigDecimal smallFeeAmount, Integer availableForDonation, Integer donateAnonymous, Integer trade, String tradeItem, Integer agreedNumberOfDays, Integer agreedNumberOfHours, Integer indefiniteDuration, Integer presentDuringBorrowingPeriod, Integer entirePeriod, Integer partialPeriod, Integer provideProperUseTraining, String specificConditions, Integer goodwill, Integer age18OrMore, Integer isActive, Date dateCreated, Date dateUpdated, Date dateDeleted, String organizationName, Integer displayLenderOrganizationName, int approved, Integer notifyBorrowers, Integer receiveBorrowerNotifications, int itemConditionId, BigDecimal securityDepositAmount, Integer securityDeposit, Integer isCommunity, String remoteIp, String comment, String advertiserId) {
        this.lender_id = id;
        this.user_id = user_id;
        this.contactDescribeId = contactDescribeId;
        this.otherDescribeYourself = otherDescribeYourself;
        this.firstName = firstName;
        this.mi = mi;
        this.lastName = lastName;
        this.displayLenderName = displayLenderName;
        this.displayLenderAddress = displayLenderAddress;
        this.displayLenderAlternativeAddress = displayLenderAlternativeAddress;
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
        this.BComesToWhichAddress = BComesToWhichAddress;
        this.meetBorrowerAtAgreedL2B = meetBorrowerAtAgreedL2b;
        this.willDeliverToBorrowerPreferredL2b = willDeliverToBorrowerPreferredL2b;
        this.thirdPartyPresenceL2b = thirdPartyPresenceL2b;
        this.lenderThirdPartyChoiceL2b = lenderThirdPartyChoiceL2b;
        this.agreedThirdPartyChoiceL2b = agreedThirdPartyChoiceL2b;
        this.BReturnsToWhichAddress = BReturnsToWhichAddress;
        this.meetBorrowerAtAgreedB2l = meetBorrowerAtAgreedB2l;
        this.willPickUpPreferredLocationB2l = willPickUpPreferredLocationB2l;
        this.thirdPartyPresenceB2l = thirdPartyPresenceB2l;
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
        this.agreedThirdPartyChoiceB2l = agreedThirdPartyChoiceB2l;
        this.borrowerChoice = borrowerChoice;
        this.categoryId = categoryId;
        this.otherItemCategory = otherItemCategory;
        this.itemModel = itemModel;
        this.itemDescription = itemDescription;
        this.itemCount = itemCount;
        this.forFree = forFree;
        this.availableForPurchase = availableForPurchase;
        this.availableForPurchaseAmount = availableForPurchaseAmount;
        this.smallFee = smallFee;
        this.smallFeeAmount = smallFeeAmount;
        this.availableForDonation = availableForDonation;
        this.donateAnonymous = donateAnonymous;
        this.trade = trade;
        this.tradeItem = tradeItem;
        this.agreedNumberOfDays = agreedNumberOfDays;
        this.agreedNumberOfHours = agreedNumberOfHours;
        this.indefiniteDuration = indefiniteDuration;
        this.presentDuringBorrowingPeriod = presentDuringBorrowingPeriod;
        this.entirePeriod = entirePeriod;
        this.partialPeriod = partialPeriod;
        this.provideProperUseTraining = provideProperUseTraining;
        this.specificConditions = specificConditions;
        this.goodwill = goodwill;
        this.age18OrMore = age18OrMore;
        this.isActive = isActive;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
        this.organizationName = organizationName;
        this.displayLenderOrganizationName = displayLenderOrganizationName;
        this.approved = approved;
        this.notifyBorrowers = notifyBorrowers;
        this.receiveBorrowerNotifications = receiveBorrowerNotifications;
        this.itemConditionId = itemConditionId;
        this.securityDepositAmount = securityDepositAmount;
        this.securityDeposit = securityDeposit;
        this.isCommunity = isCommunity;
        this.user_id = user_id;
        this.remoteIp = remoteIp;
        this.comment = comment;
        this.advertiserId = advertiserId;
    }

/**
     * @return the lender_id
     */
    public String getLender_id() {
        return lender_id;
    }

    /**
     * @param lender_id the lender_id to set
     */
    public void setLender_id(String lender_id) {
        this.lender_id = lender_id;
    }
    
    public int getContactDescribeId() {
        return this.contactDescribeId;
    }

    public void setContactDescribeId(int contactDescribeId) {
        this.contactDescribeId = contactDescribeId;
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

    public int getDisplayLenderName() {
        return this.displayLenderName;
    }

    public void setDisplayLenderName(int displayLenderName) {
        this.displayLenderName = displayLenderName;
    }

    public int getDisplayLenderAddress() {
        return this.displayLenderAddress;
    }

    public void setDisplayLenderAddress(int displayLenderAddress) {
        this.displayLenderAddress = displayLenderAddress;
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

    public Integer getBorrowerContactByEmail() {
        return this.borrowerContactByEmail;
    }

    public void setBorrowerContactByEmail(Integer borrowerContactByEmail) {
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

    public Integer getBComesToWhichAddress() {
        return this.BComesToWhichAddress;
    }

    public void setBComesToWhichAddress(Integer BComesToWhichAddress) {
        this.BComesToWhichAddress = BComesToWhichAddress;
    }

    public Integer getMeetBorrowerAtAgreedL2b() {
        return this.meetBorrowerAtAgreedL2B;
    }

    public void setMeetBorrowerAtAgreedL2b(Integer meetBorrowerAtAgreedL2B) {
        this.meetBorrowerAtAgreedL2B = meetBorrowerAtAgreedL2B;
    }

    public Integer getWillDeliverToBorrowerPreferredL2b() {
        return this.willDeliverToBorrowerPreferredL2b;
    }

    public void setWillDeliverToBorrowerPreferredL2b(Integer willDeliverToBorrowerPreferredL2b) {
        this.willDeliverToBorrowerPreferredL2b = willDeliverToBorrowerPreferredL2b;
    }

    public Integer getThirdPartyPresenceL2b() {
        return this.thirdPartyPresenceL2b;
    }

    public void setThirdPartyPresenceL2b(Integer thirdPartyPresenceL2b) {
        this.thirdPartyPresenceL2b = thirdPartyPresenceL2b;
    }

    public Integer getLenderThirdPartyChoiceL2b() {
        return this.lenderThirdPartyChoiceL2b;
    }

    public void setLenderThirdPartyChoiceL2b(Integer lenderThirdPartyChoiceL2b) {
        this.lenderThirdPartyChoiceL2b = lenderThirdPartyChoiceL2b;
    }

    public Integer getAgreedThirdPartyChoiceL2b() {
        return this.agreedThirdPartyChoiceL2b;
    }

    public void setAgreedThirdPartyChoiceL2b(Integer agreedThirdPartyChoiceL2b) {
        this.agreedThirdPartyChoiceL2b = agreedThirdPartyChoiceL2b;
    }

    public Integer getBReturnsToWhichAddress() {
        return this.BReturnsToWhichAddress;
    }

    public void setBReturnsToWhichAddress(Integer BReturnsToWhichAddress) {
        this.BReturnsToWhichAddress = BReturnsToWhichAddress;
    }

    public Integer getMeetBorrowerAtAgreedB2l() {
        return this.meetBorrowerAtAgreedB2l;
    }

    public void setMeetBorrowerAtAgreedB2l(Integer meetBorrowerAtAgreedB2l) {
        this.meetBorrowerAtAgreedB2l = meetBorrowerAtAgreedB2l;
    }

    public Integer getWillPickUpPreferredLocationB2l() {
        return this.willPickUpPreferredLocationB2l;
    }

    public void setWillPickUpPreferredLocationB2l(Integer willPickUpPreferredLocationB2l) {
        this.willPickUpPreferredLocationB2l = willPickUpPreferredLocationB2l;
    }

    public Integer getThirdPartyPresenceB2l() {
        return this.thirdPartyPresenceB2l;
    }

    public void setThirdPartyPresenceB2l(Integer thirdPartyPresenceB2l) {
        this.thirdPartyPresenceB2l = thirdPartyPresenceB2l;
    }

    public int getLenderThirdPartyChoiceB2l() {
        return this.lenderThirdPartyChoiceB2l;
    }

    public void setLenderThirdPartyChoiceB2l(int lenderThirdPartyChoiceB2l) {
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
    }

    public Integer getAgreedThirdPartyChoiceB2l() {
        return this.agreedThirdPartyChoiceB2l;
    }

    public void setAgreedThirdPartyChoiceB2l(Integer agreedThirdPartyChoiceB2l) {
        this.agreedThirdPartyChoiceB2l = agreedThirdPartyChoiceB2l;
    }

    public Integer getBorrowerChoice() {
        return this.borrowerChoice;
    }

    public void setBorrowerChoice(Integer borrowerChoice) {
        this.borrowerChoice = borrowerChoice;
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

    public Integer getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getForFree() {
        return this.forFree;
    }

    public void setForFree(Integer forFree) {
        this.forFree = forFree;
    }

    public Integer getAvailableForPurchase() {
        return this.availableForPurchase;
    }

    public void setAvailableForPurchase(Integer availableForPurchase) {
        this.availableForPurchase = availableForPurchase;
    }

    public BigDecimal getAvailableForPurchaseAmount() {
        return this.availableForPurchaseAmount;
    }

    public void setAvailableForPurchaseAmount(BigDecimal availableForPurchaseAmount) {
        this.availableForPurchaseAmount = availableForPurchaseAmount;
    }

    public Integer getSmallFee() {
        return this.smallFee;
    }

    public void setSmallFee(Integer smallFee) {
        this.smallFee = smallFee;
    }

    public BigDecimal getSmallFeeAmount() {
        return this.smallFeeAmount;
    }

    public void setSmallFeeAmount(BigDecimal smallFeeAmount) {
        this.smallFeeAmount = smallFeeAmount;
    }

    public Integer getAvailableForDonation() {
        return this.availableForDonation;
    }

    public void setAvailableForDonation(Integer availableForDonation) {
        this.availableForDonation = availableForDonation;
    }

    public Integer getDonateAnonymous() {
        return this.donateAnonymous;
    }

    public void setDonateAnonymous(Integer donateAnonymous) {
        this.donateAnonymous = donateAnonymous;
    }

    public Integer getTrade() {
        return this.trade;
    }

    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    public String getTradeItem() {
        return this.tradeItem;
    }

    public void setTradeItem(String tradeItem) {
        this.tradeItem = tradeItem;
    }

    public Integer getAgreedNumberOfDays() {
        return this.agreedNumberOfDays;
    }

    public void setAgreedNumberOfDays(Integer agreedNumberOfDays) {
        this.agreedNumberOfDays = agreedNumberOfDays;
    }

    public Integer getAgreedNumberOfHours() {
        return this.agreedNumberOfHours;
    }

    public void setAgreedNumberOfHours(Integer agreedNumberOfHours) {
        this.agreedNumberOfHours = agreedNumberOfHours;
    }

    public Integer getIndefiniteDuration() {
        return this.indefiniteDuration;
    }

    public void setIndefiniteDuration(Integer indefiniteDuration) {
        this.indefiniteDuration = indefiniteDuration;
    }

    public Integer getPresentDuringBorrowingPeriod() {
        return this.presentDuringBorrowingPeriod;
    }

    public void setPresentDuringBorrowingPeriod(Integer presentDuringBorrowingPeriod) {
        this.presentDuringBorrowingPeriod = presentDuringBorrowingPeriod;
    }

    public Integer getEntirePeriod() {
        return this.entirePeriod;
    }

    public void setEntirePeriod(Integer entirePeriod) {
        this.entirePeriod = entirePeriod;
    }

    public Integer getPartialPeriod() {
        return this.partialPeriod;
    }

    public void setPartialPeriod(Integer partialPeriod) {
        this.partialPeriod = partialPeriod;
    }

    public Integer getProvideProperUseTraining() {
        return this.provideProperUseTraining;
    }

    public void setProvideProperUseTraining(Integer provideProperUseTraining) {
        this.provideProperUseTraining = provideProperUseTraining;
    }

    public String getSpecificConditions() {
        return this.specificConditions;
    }

    public void setSpecificConditions(String specificConditions) {
        this.specificConditions = specificConditions;
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

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getDisplayLenderOrganizationName() {
        return this.displayLenderOrganizationName;
    }

    public void setDisplayLenderOrganizationName(Integer displayLenderOrganizationName) {
        this.displayLenderOrganizationName = displayLenderOrganizationName;
    }

    public int getApproved() {
        return this.approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public Integer getNotifyBorrowers() {
        return this.notifyBorrowers;
    }

    public void setNotifyBorrowers(Integer notifyBorrowers) {
        this.notifyBorrowers = notifyBorrowers;
    }

    public Integer getReceiveBorrowerNotifications() {
        return this.receiveBorrowerNotifications;
    }

    public void setReceiveBorrowerNotifications(Integer receiveBorrowerNotifications) {
        this.receiveBorrowerNotifications = receiveBorrowerNotifications;
    }

    public int getItemConditionId() {
        return this.itemConditionId;
    }

    public void setItemConditionId(int itemConditionId) {
        this.itemConditionId = itemConditionId;
    }

    public BigDecimal getSecurityDepositAmount() {
        return this.securityDepositAmount;
    }

    public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
        this.securityDepositAmount = securityDepositAmount;
    }

    public Integer getSecurityDeposit() {
        return this.securityDeposit;
    }

    public void setSecurityDeposit(Integer securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public Integer getIsCommunity() {
        return this.isCommunity;
    }

    public void setIsCommunity(Integer isCommunity) {
        this.isCommunity = isCommunity;
    }

    public String getuser_id() {
        return this.user_id;
    }

    public void setuser_id(String user_id) {
        this.user_id = user_id;
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

    /**
     * @return the displayLenderAlternativeAddress
     */
    public int getDisplayLenderAlternativeAddress() {
        return displayLenderAlternativeAddress;
    }

    /**
     * @param displayLenderAlternativeAddress the
     * displayLenderAlternativeAddress to set
     */
    public void setDisplayLenderAlternativeAddress(int displayLenderAlternativeAddress) {
        this.displayLenderAlternativeAddress = displayLenderAlternativeAddress;
    }

    

}
