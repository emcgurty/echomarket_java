package echomarket.hibernate;

import java.math.BigDecimal;
import java.util.Date;

public class LenderTransfer implements java.io.Serializable {

    private String lenderTransferId;
    private String lenderId;
    private String itemId;
    private String userId;
    private String participantId;
    private Integer borrowerComesToWhichAddress;
    private Integer meetBorrowerAtAgreedL2b;
    private Integer meetBorrowerAtAgreedB2l;
    private Integer willDeliverToBorrower;
    private Integer thirdPartyPresenceL2b;
    private Integer thirdPartyPresenceB2l;
    private Integer borrowerThirdPartyChoice;
    private Integer agreedThirdPartyChoiceL2b;
    private Integer agreedThirdPartyChoiceB2l;
    private Integer borrowerReturnsToWhichAddress;
    private Integer willPickUpPreferredLocationB2l;
    private Integer lenderThirdPartyChoiceB2l;
    private Integer lenderThirdPartyChoiceL2b;
    private Integer borrowerChoice;
    private String remoteIp;
    private String comment;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;

    public LenderTransfer() {
    }

    public LenderTransfer(String lenderTransferId, String lenderId, String itemId, Date dateCreated, Date dateUpdated, Date dateDeleted) {
        this.lenderTransferId = lenderTransferId;
        this.lenderId = lenderId;
        this.itemId = itemId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
    }

    public LenderTransfer(String lenderTransferId, String lenderId, String itemId, String userId, String participantId, Integer borrowerComesToWhichAddress, Integer meetBorrowerAtAgreedL2b, Integer meetBorrowerAtAgreedB2l, Integer willDeliverToBorrower, Integer thirdPartyPresenceL2b, Integer thirdPartyPresenceB2l, Integer borrowerThirdPartyChoice, Integer agreedThirdPartyChoiceL2b, Integer agreedThirdPartyChoiceB2l, Integer borrowerReturnsToWhichAddress, Integer willPickUpPreferredLocationB2l, Integer lenderThirdPartyChoiceB2l, Integer borrowerChoice, String remoteIp, String comment, Date dateCreated, Date dateUpdated, Date dateDeleted) {
        this.lenderTransferId = lenderTransferId;
        this.lenderId = lenderId;
        this.itemId = itemId;
        this.userId = userId;
        this.participantId = participantId;
        this.borrowerComesToWhichAddress = borrowerComesToWhichAddress;
        this.meetBorrowerAtAgreedL2b = meetBorrowerAtAgreedL2b;
        this.meetBorrowerAtAgreedB2l = meetBorrowerAtAgreedB2l;
        this.willDeliverToBorrower = willDeliverToBorrower;
        this.thirdPartyPresenceL2b = thirdPartyPresenceL2b;
        this.thirdPartyPresenceB2l = thirdPartyPresenceB2l;
        this.borrowerThirdPartyChoice = borrowerThirdPartyChoice;
        this.agreedThirdPartyChoiceL2b = agreedThirdPartyChoiceL2b;
        this.agreedThirdPartyChoiceB2l = agreedThirdPartyChoiceB2l;
        this.borrowerReturnsToWhichAddress = borrowerReturnsToWhichAddress;
        this.willPickUpPreferredLocationB2l = willPickUpPreferredLocationB2l;
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
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

    public String getLenderId() {
        return this.lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParticipantId() {
        return this.participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Integer getBorrowerComesToWhichAddress() {
        return this.borrowerComesToWhichAddress;
    }

    public void setBorrowerComesToWhichAddress(Integer borrowerComesToWhichAddress) {
        this.borrowerComesToWhichAddress = borrowerComesToWhichAddress;
    }

    public Integer getMeetBorrowerAtAgreedL2b() {
        return this.meetBorrowerAtAgreedL2b;
    }

    public void setMeetBorrowerAtAgreedL2b(Integer meetBorrowerAtAgreedL2b) {
        this.meetBorrowerAtAgreedL2b = meetBorrowerAtAgreedL2b;
    }

    public Integer getMeetBorrowerAtAgreedB2l() {
        return this.meetBorrowerAtAgreedB2l;
    }

    public void setMeetBorrowerAtAgreedB2l(Integer meetBorrowerAtAgreedB2l) {
        this.meetBorrowerAtAgreedB2l = meetBorrowerAtAgreedB2l;
    }

    public Integer getWillDeliverToBorrower() {
        return this.willDeliverToBorrower;
    }

    public void setWillDeliverToBorrower(Integer willDeliverToBorrower) {
        this.willDeliverToBorrower = willDeliverToBorrower;
    }

    public Integer getThirdPartyPresenceL2b() {
        return this.thirdPartyPresenceL2b;
    }

    public void setThirdPartyPresenceL2b(Integer thirdPartyPresenceL2b) {
        this.thirdPartyPresenceL2b = thirdPartyPresenceL2b;
    }

    public Integer getThirdPartyPresenceB2l() {
        return this.thirdPartyPresenceB2l;
    }

    public void setThirdPartyPresenceB2l(Integer thirdPartyPresenceB2l) {
        this.thirdPartyPresenceB2l = thirdPartyPresenceB2l;
    }

    public Integer getBorrowerThirdPartyChoice() {
        return this.borrowerThirdPartyChoice;
    }

    public void setBorrowerThirdPartyChoice(Integer borrowerThirdPartyChoice) {
        this.borrowerThirdPartyChoice = borrowerThirdPartyChoice;
    }

    public Integer getAgreedThirdPartyChoiceL2b() {
        return this.agreedThirdPartyChoiceL2b;
    }

    public void setAgreedThirdPartyChoiceL2b(Integer agreedThirdPartyChoiceL2b) {
        this.agreedThirdPartyChoiceL2b = agreedThirdPartyChoiceL2b;
    }

    public Integer getAgreedThirdPartyChoiceB2l() {
        return this.agreedThirdPartyChoiceB2l;
    }

    public void setAgreedThirdPartyChoiceB2l(Integer agreedThirdPartyChoiceB2l) {
        this.agreedThirdPartyChoiceB2l = agreedThirdPartyChoiceB2l;
    }

    public Integer getBorrowerReturnsToWhichAddress() {
        return this.borrowerReturnsToWhichAddress;
    }

    public void setBorrowerReturnsToWhichAddress(Integer borrowerReturnsToWhichAddress) {
        this.borrowerReturnsToWhichAddress = borrowerReturnsToWhichAddress;
    }

    public Integer getWillPickUpPreferredLocationB2l() {
        return this.willPickUpPreferredLocationB2l;
    }

    public void setWillPickUpPreferredLocationB2l(Integer willPickUpPreferredLocationB2l) {
        this.willPickUpPreferredLocationB2l = willPickUpPreferredLocationB2l;
    }

    public Integer getLenderThirdPartyChoiceB2l() {
        return this.lenderThirdPartyChoiceB2l;
    }

    public void setLenderThirdPartyChoiceB2l(Integer lenderThirdPartyChoiceB2l) {
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
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

}
