package echomarket.hibernate;

import java.math.BigDecimal;
import java.util.Date;

public class LenderTransfer implements java.io.Serializable {

  private String lenderTransferId;
  private String itemId;
  private String participant_id;
  private Integer borrowerComesToWhichAddress;
  private Integer meetBorrowerAtAgreed;
  private Integer meetBorrowerAtAgreedLenderChoice;
  private Integer meetBorrowerAtAgreedBorrowerChoice;
  private Integer meetBorrowerAtAgreedMutual;
  private Integer willDeliverToBorrower;
  private Integer thirdPartyPresence;
  private Integer thirdPartyPresenceLenderChoice;
  private Integer thirdPartyPresenceBorrowerChoice;
  private Integer thirdPartyPresenceMutual;
  private Integer borrowerReturnsToWhichAddress;
  private Integer willPickUpPreferredLocation;

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
          Integer borrowerComesToWhichAddress,
          Integer meetBorrowerAtAgreed,
          Integer meetBorrowerAtAgreedBorrowerChoice,
          Integer meetBorrowerAtAgreedLenderChoice,
          Integer meetBorrowerAtAgreedMutual,
          Integer willDeliverToBorrower,
          Integer thirdPartyPresence,
          Integer thirdPartyPresenceBorrowerChoice,
          Integer thirdPartyPresenceLenderChoice,
          Integer thirdPartyPresenceMutual,
          Integer borrowerReturnsToWhichAddress,
          Integer willPickUpPreferredLocation,
          String remoteIp, String comment, Date dateCreated, Date dateUpdated, Date dateDeleted) {
    this.lenderTransferId = lenderTransferId;
    this.itemId = itemId;
    this.participant_id = participant_id;
    this.borrowerComesToWhichAddress = borrowerComesToWhichAddress;
    this.meetBorrowerAtAgreed = meetBorrowerAtAgreed;
    this.meetBorrowerAtAgreedBorrowerChoice = meetBorrowerAtAgreedBorrowerChoice;
    this.meetBorrowerAtAgreedLenderChoice = meetBorrowerAtAgreedLenderChoice;
    this.meetBorrowerAtAgreedMutual = meetBorrowerAtAgreedMutual;

    this.willDeliverToBorrower = willDeliverToBorrower;

    this.thirdPartyPresence = thirdPartyPresence;
    this.thirdPartyPresenceBorrowerChoice = thirdPartyPresenceBorrowerChoice;
    this.thirdPartyPresenceLenderChoice = thirdPartyPresenceLenderChoice;
    this.thirdPartyPresenceMutual = thirdPartyPresenceMutual;

    this.borrowerReturnsToWhichAddress = borrowerReturnsToWhichAddress;
    this.willPickUpPreferredLocation = willPickUpPreferredLocation;

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
   * @return the meetBorrowerAtAgreedLenderChoice
   */
  public Integer getMeetBorrowerAtAgreedLenderChoice() {
    return meetBorrowerAtAgreedLenderChoice;
  }

  /**
   * @param meetBorrowerAtAgreedLenderChoice the
   * meetBorrowerAtAgreedLenderChoice to set
   */
  public void setMeetBorrowerAtAgreedLenderChoice(Integer meetBorrowerAtAgreedLenderChoice) {
    this.meetBorrowerAtAgreedLenderChoice = meetBorrowerAtAgreedLenderChoice;
  }

  /**
   * @return the meetBorrowerAtAgreedBorrowerChoice
   */
  public Integer getMeetBorrowerAtAgreedBorrowerChoice() {
    return meetBorrowerAtAgreedBorrowerChoice;
  }

  /**
   * @param meetBorrowerAtAgreedBorrowerChoice the
   * meetBorrowerAtAgreedBorrowerChoice to set
   */
  public void setMeetBorrowerAtAgreedBorrowerChoice(Integer meetBorrowerAtAgreedBorrowerChoice) {
    this.meetBorrowerAtAgreedBorrowerChoice = meetBorrowerAtAgreedBorrowerChoice;
  }

  /**
   * @return the meetBorrowerAtAgreedMutual
   */
  public Integer getMeetBorrowerAtAgreedMutual() {
    return meetBorrowerAtAgreedMutual;
  }

  /**
   * @param meetBorrowerAtAgreedMutual the meetBorrowerAtAgreedMutual to set
   */
  public void setMeetBorrowerAtAgreedMutual(Integer meetBorrowerAtAgreedMutual) {
    this.meetBorrowerAtAgreedMutual = meetBorrowerAtAgreedMutual;
  }

  /**
   * @return the thirdPartyPresenceLenderChoice
   */
  public Integer getThirdPartyPresenceLenderChoice() {
    return thirdPartyPresenceLenderChoice;
  }

  /**
   * @param thirdPartyPresenceLenderChoice the thirdPartyPresenceLenderChoice to
   * set
   */
  public void setThirdPartyPresenceLenderChoice(Integer thirdPartyPresenceLenderChoice) {
    this.thirdPartyPresenceLenderChoice = thirdPartyPresenceLenderChoice;
  }

  /**
   * @return the thirdPartyPresenceBorrowerChoice
   */
  public Integer getThirdPartyPresenceBorrowerChoice() {
    return thirdPartyPresenceBorrowerChoice;
  }

  /**
   * @param thirdPartyPresenceBorrowerChoice the
   * thirdPartyPresenceBorrowerChoice to set
   */
  public void setThirdPartyPresenceBorrowerChoice(Integer thirdPartyPresenceBorrowerChoice) {
    this.thirdPartyPresenceBorrowerChoice = thirdPartyPresenceBorrowerChoice;
  }

  /**
   * @return the thirdPartyPresenceMutual
   */
  public Integer getThirdPartyPresenceMutual() {
    return thirdPartyPresenceMutual;
  }

  /**
   * @param thirdPartyPresenceMutual the thirdPartyPresenceMutual to set
   */
  public void setThirdPartyPresenceMutual(Integer thirdPartyPresenceMutual) {
    this.thirdPartyPresenceMutual = thirdPartyPresenceMutual;
  }

}
