package echomarket.hibernate;

import java.math.BigDecimal;
import java.util.Date;

public class LenderTransfer implements java.io.Serializable {

    private String lenderTransferId;
    private String itemId;
    private String participant_id;
    private Integer borrowerComesToWhichAddress;
    private Integer meetBorrowerAtAgreed;
    private Integer willDeliverToBorrower;
    private Integer thirdPartyPresence;
    private Integer borrowerThirdPartyChoice;
    private Integer agreedThirdPartyChoice;
    private Integer borrowerReturnsToWhichAddress;
    private Integer willPickUpPreferredLocation;
    private Integer lenderThirdPartyChoice;
    private Integer borrowerChoice;
    private String remoteIp;
    private String comment;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;

    public LenderTransfer() {
    }

       public LenderTransfer(String lenderTransferId, String participant_id, String itemId, Date dateCreated, Date dateUpdated, Date dateDeleted) {
        this.lenderTransferId = lenderTransferId;
        this.participant_id = participant_id;
        this.itemId = itemId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
    }
       
    public LenderTransfer(String lenderTransferId, String itemId, String participant_id, 
            Integer borrowerComesToWhichAddress, Integer meetBorrowerAtAgreed, 
            Integer willDeliverToBorrower,  
            Integer thirdPartyPresence, Integer borrowerThirdPartyChoice,  
            Integer agreedThirdPartyChoice, Integer borrowerReturnsToWhichAddress, 
            Integer willPickUpPreferredLocation, Integer lenderThirdPartyChoice, 
            Integer borrowerChoice, String remoteIp, String comment, Date dateCreated, Date dateUpdated, Date dateDeleted) {
        this.lenderTransferId = lenderTransferId;
        this.itemId = itemId;
        this.participant_id = participant_id;
        this.borrowerComesToWhichAddress = borrowerComesToWhichAddress;
        this.meetBorrowerAtAgreed = meetBorrowerAtAgreed;
        this.willDeliverToBorrower = willDeliverToBorrower;
        this.thirdPartyPresence = thirdPartyPresence;
        this.borrowerThirdPartyChoice = borrowerThirdPartyChoice;
        this.agreedThirdPartyChoice = agreedThirdPartyChoice;
        this.borrowerReturnsToWhichAddress = borrowerReturnsToWhichAddress;
        this.willPickUpPreferredLocation = willPickUpPreferredLocation;
        this.lenderThirdPartyChoice = lenderThirdPartyChoice;
        this.borrowerChoice = borrowerChoice;
        this.remoteIp = remoteIp;
        this.comment = comment;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
    }

    public String getLenderTransferId() {
        return this.lenderTransferId;
    }

    public void setLenderTransferId(String lenderTransferId) {
        this.lenderTransferId = lenderTransferId;
    }

   public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getParticipant_id() {
        return this.participant_id;
    }

    public void setParticipant_id(String participant_id) {
        this.participant_id = participant_id;
    }

    public Integer getBorrowerComesToWhichAddress() {
        return this.borrowerComesToWhichAddress;
    }

    public void setBorrowerComesToWhichAddress(Integer borrowerComesToWhichAddress) {
        this.borrowerComesToWhichAddress = borrowerComesToWhichAddress;
    }

    public Integer getMeetBorrowerAtAgreed() {
        return this.meetBorrowerAtAgreed;
    }

    public void setMeetBorrowerAtAgreed(Integer meetBorrowerAtAgreed) {
        this.meetBorrowerAtAgreed = meetBorrowerAtAgreed;
    }

    public Integer getWillDeliverToBorrower() {
        return this.willDeliverToBorrower;
    }

    public void setWillDeliverToBorrower(Integer willDeliverToBorrower) {
        this.willDeliverToBorrower = willDeliverToBorrower;
    }

    public Integer getThirdPartyPresence() {
        return this.thirdPartyPresence;
    }

    public void setThirdPartyPresence(Integer thirdPartyPresence) {
        this.thirdPartyPresence = thirdPartyPresence;
    }

    
    public Integer getBorrowerThirdPartyChoice() {
        return this.borrowerThirdPartyChoice;
    }

    public void setBorrowerThirdPartyChoice(Integer borrowerThirdPartyChoice) {
        this.borrowerThirdPartyChoice = borrowerThirdPartyChoice;
    }

    public Integer getAgreedThirdPartyChoice() {
        return this.agreedThirdPartyChoice;
    }

    public void setAgreedThirdPartyChoice(Integer agreedThirdPartyChoice) {
        this.agreedThirdPartyChoice = agreedThirdPartyChoice;
    }

    
    public Integer getBorrowerReturnsToWhichAddress() {
        return this.borrowerReturnsToWhichAddress;
    }

    public void setBorrowerReturnsToWhichAddress(Integer borrowerReturnsToWhichAddress) {
        this.borrowerReturnsToWhichAddress = borrowerReturnsToWhichAddress;
    }

    public Integer getWillPickUpPreferredLocation() {
        return this.willPickUpPreferredLocation;
    }

    public void setWillPickUpPreferredLocation(Integer willPickUpPreferredLocationB2l) {
        this.willPickUpPreferredLocation = willPickUpPreferredLocationB2l;
    }

    public Integer getLenderThirdPartyChoice() {
        return this.lenderThirdPartyChoice;
    }

    public void setLenderThirdPartyChoice(Integer lenderThirdPartyChoice) {
        this.lenderThirdPartyChoice = lenderThirdPartyChoice;
    }

    public Integer getBorrowerChoice() {
        return this.borrowerChoice;
    }

    public void setBorrowerChoice(Integer borrowerChoice) {
        this.borrowerChoice = borrowerChoice;
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

  

}
