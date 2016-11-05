package echomarket.web.managedbeans;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

//See for https://github.com/lovelle/jquery-chat

@Named
@ManagedBean
@RequestScoped
public class LenderTransferBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
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
     private Integer lenderThirdPartyChoice;
     private Integer borrowerThirdPartyChoice;
     private Integer agreedThirdPartyChoiceL2b;
     private Integer agreedThirdPartyChoiceB2l;
     private Integer borrowerReturnsToWhichAddress;
     private Integer willPickUpPreferredLocationB2l;
     private Integer lenderThirdPartyChoiceB2l;
     private Integer borrowerChoice;
     private String remoteIp;
     private String comment;


    public LenderTransferBean() {
    }

    public String saveLenderTransfer() {
        return "index";
        
    }
    /**
     * @return the lenderTransferId
     */
    @Id
    public String getLenderTransferId() {
        return lenderTransferId;
    }

    /**
     * @param lenderTransferId the lenderTransferId to set
     */
    public void setLenderTransferId(String lenderTransferId) {
        this.lenderTransferId = lenderTransferId;
    }

    /**
     * @return the lenderId
     */
    public String getLenderId() {
        return lenderId;
    }

    /**
     * @param lenderId the lenderId to set
     */
    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
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
     * @return the borrowerComesToWhichAddress
     */
    public Integer getBorrowerComesToWhichAddress() {
        return borrowerComesToWhichAddress;
    }

    /**
     * @param borrowerComesToWhichAddress the borrowerComesToWhichAddress to set
     */
    public void setBorrowerComesToWhichAddress(Integer borrowerComesToWhichAddress) {
        this.borrowerComesToWhichAddress = borrowerComesToWhichAddress;
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
     * @return the willDeliverToBorrower
     */
    public Integer getWillDeliverToBorrower() {
        return willDeliverToBorrower;
    }

    /**
     * @param willDeliverToBorrower the willDeliverToBorrower to set
     */
    public void setWillDeliverToBorrower(Integer willDeliverToBorrower) {
        this.willDeliverToBorrower = willDeliverToBorrower;
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
     * @return the lenderThirdPartyChoice
     */
    public Integer getLenderThirdPartyChoice() {
        return lenderThirdPartyChoice;
    }

    /**
     * @param lenderThirdPartyChoice the lenderThirdPartyChoice to set
     */
    public void setLenderThirdPartyChoice(Integer lenderThirdPartyChoice) {
        this.lenderThirdPartyChoice = lenderThirdPartyChoice;
    }

    /**
     * @return the borrowerThirdPartyChoice
     */
    public Integer getBorrowerThirdPartyChoice() {
        return borrowerThirdPartyChoice;
    }

    /**
     * @param borrowerThirdPartyChoice the borrowerThirdPartyChoice to set
     */
    public void setBorrowerThirdPartyChoice(Integer borrowerThirdPartyChoice) {
        this.borrowerThirdPartyChoice = borrowerThirdPartyChoice;
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
     * @return the borrowerReturnsToWhichAddress
     */
    public Integer getBorrowerReturnsToWhichAddress() {
        return borrowerReturnsToWhichAddress;
    }

    /**
     * @param borrowerReturnsToWhichAddress the borrowerReturnsToWhichAddress to set
     */
    public void setBorrowerReturnsToWhichAddress(Integer borrowerReturnsToWhichAddress) {
        this.borrowerReturnsToWhichAddress = borrowerReturnsToWhichAddress;
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
     * @return the lenderThirdPartyChoiceB2l
     */
    public Integer getLenderThirdPartyChoiceB2l() {
        return lenderThirdPartyChoiceB2l;
    }

    /**
     * @param lenderThirdPartyChoiceB2l the lenderThirdPartyChoiceB2l to set
     */
    public void setLenderThirdPartyChoiceB2l(Integer lenderThirdPartyChoiceB2l) {
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
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


    

}
