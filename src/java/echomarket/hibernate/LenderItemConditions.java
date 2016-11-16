package echomarket.hibernate;

import java.math.BigDecimal;
import java.util.Date;

public class LenderItemConditions implements java.io.Serializable {

    private String lender_item_condition_id;
    private String itemId;
    private String participant_id;
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
    private BigDecimal securityDepositAmount;
    private Integer securityDeposit;
    private String remoteIp;
    private String comment;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;

    public LenderItemConditions() {
    }

    public LenderItemConditions(String lender_item_condition_id, String participant_id, Integer forFree, Integer availableForPurchase, BigDecimal availableForPurchaseAmount, Integer smallFee, BigDecimal smallFeeAmount, Integer availableForDonation, Integer donateAnonymous, Integer trade, String tradeItem, Integer agreedNumberOfDays,             Integer agreedNumberOfHours, Integer indefiniteDuration,             Integer presentDuringBorrowingPeriod, Integer entirePeriod,             Integer partialPeriod, Integer provideProperUseTraining,             String specificConditions, BigDecimal securityDepositAmount,             Integer securityDeposit, String remoteIp,            String comment, Date dateCreated, Date dateUpdated) 
    {
        this.lender_item_condition_id = lender_item_condition_id;
        this.participant_id = participant_id;
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
        this.securityDepositAmount = securityDepositAmount;
        this.securityDeposit = securityDeposit;
        this.remoteIp = remoteIp;
        this.comment = comment;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;

    }

    public String getLender_item_condition_id() {
        return lender_item_condition_id;
    }

    /**
     * @param lender_item_condition_id the lender_item_condition_id to set
     */
    public void setLender_item_condition_id(String lender_item_condition_id) {
        this.lender_item_condition_id = lender_item_condition_id;
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
     * @param presentDuringBorrowingPeriod the presentDuringBorrowingPeriod to
     * set
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
