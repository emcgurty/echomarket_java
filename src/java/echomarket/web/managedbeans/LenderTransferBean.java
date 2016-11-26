package echomarket.web.managedbeans;

import echomarket.hibernate.LenderItemConditions;
import echomarket.hibernate.LenderTransfer;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean
@RequestScoped
public class LenderTransferBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  private String lenderTransferId;
  private String itemId;
  private String participant_id;
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

  public LenderTransferBean() {
  }

  public String load_ud(String pid) {

    List result = null;
    Map<String, String> params = null;
    String strIid = null;
    String action = null;

    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      strIid = params.get("iid");
    } catch (Exception ex) {
    }
    
    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      action = params.get("action");
    } catch (Exception ex) {
    }
    
    if (strIid == null) {
      strIid = "";
    }
    if (strIid.isEmpty() == false) {
      result = getCurrentLT_Iid(pid, strIid);
    } else {
      result = getCurrentLT(pid);
    }
    
    if (result != null) {
      if (result.size() == 1) {
        LenderTransfer ltr = null;
        ltr = (LenderTransfer) result.get(0);
        if (ltr != null) {
          this.lenderTransferId = ltr.getLenderTransferId();
          this.itemId = ltr.getItemId();
          this.participant_id = ltr.getParticipant_id();
          this.borrowerComesToWhichAddress = ltr.getBorrowerComesToWhichAddress();
          this.meetBorrowerAtAgreedL2b = ltr.getMeetBorrowerAtAgreedL2b();
          this.meetBorrowerAtAgreedB2l = ltr.getMeetBorrowerAtAgreedL2b();
          this.willDeliverToBorrower = ltr.getWillDeliverToBorrower();
          this.thirdPartyPresenceL2b = ltr.getThirdPartyPresenceL2b();
          this.thirdPartyPresenceB2l = ltr.getThirdPartyPresenceB2l();
          this.borrowerThirdPartyChoice = ltr.getBorrowerThirdPartyChoice();
          this.agreedThirdPartyChoiceL2b = ltr.getAgreedThirdPartyChoiceL2b();
          this.agreedThirdPartyChoiceB2l = ltr.getAgreedThirdPartyChoiceB2l();
          this.borrowerReturnsToWhichAddress = ltr.getBorrowerReturnsToWhichAddress();
          this.willPickUpPreferredLocationB2l = ltr.getWillPickUpPreferredLocationB2l();
          this.lenderThirdPartyChoiceB2l = ltr.getLenderThirdPartyChoiceB2l();
          this.lenderThirdPartyChoiceL2b = ltr.getLenderThirdPartyChoiceL2b();
          this.borrowerChoice = ltr.getBorrowerChoice();
          ltr = null;
          result = null;
        }
      } else {
        ubean.setEditable(1);
      }
    }
    
    if ("edit".equals(action)) {
        ubean.setEditable(1);
    }
    
    return "lender_transfer";
  }

  public List getCurrentLT(String pid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "FROM LenderTransfer ltr "
              + " WHERE ltr.participant_id = :pid"
              + " ORDER BY ltr.dateCreated";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setMaxResults(1)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getCurrentCP");
      e.printStackTrace();
      tx.rollback();
      return null;
    } finally {
      tx = null;
      session = null;

    }
    return result;
  }

  public List getCurrentLT_Iid(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "from LenderTransfer ltr "
              + "WHERE ltr.participant_id = :pid"
              + " AND ltr.itemId = :iid ";

      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", iid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getCurrentLt");
      e.printStackTrace();
      tx.rollback();
      return null;
    } finally {
      tx = null;
      session = null;

    }
    return result;
  }

  public String updateLIT() {

    Session sb;
    Transaction tx;
    sb = null;
    tx = null;
    List litList = null;
    Boolean successTransaction = false;

    if (this.lenderTransferId.isEmpty() == true) {

      LenderTransfer lt = new LenderTransfer(getId(), "NA", ubean.getParticipant_id(), this.borrowerComesToWhichAddress, this.meetBorrowerAtAgreedL2b, this.meetBorrowerAtAgreedB2l, this.willDeliverToBorrower, this.thirdPartyPresenceL2b, this.thirdPartyPresenceB2l, this.borrowerThirdPartyChoice, this.agreedThirdPartyChoiceL2b, this.agreedThirdPartyChoiceB2l, this.borrowerReturnsToWhichAddress, this.willPickUpPreferredLocationB2l, this.lenderThirdPartyChoiceB2l, this.borrowerChoice, "NA", this.comment, new Date(), new Date(), null);

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        sb.save(lt);
        tx.commit();
        message(null, "LenderTransferSaved", null);
        successTransaction = true;
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Error in saveLenderItemCon");
        Logger.getLogger(LenderItemConditionsBean.class.getName()).log(Level.SEVERE, null, ex);
        message(null, "LenderTransferNotSaved", null);
        ubean.setEditable(1);
      } finally {
        sb = null;
        tx = null;

      }
    } else {
      // Do update
      litList = getCurrentLT_Iid(ubean.getParticipant_id(), itemId);
      if (litList.size() == 1) {
        LenderTransfer lit = (LenderTransfer) litList.get(0);
        lit.setItemId(itemId);
        lit.setParticipant_id(ubean.getParticipant_id());
        lit.setBorrowerComesToWhichAddress(borrowerComesToWhichAddress);
        lit.setMeetBorrowerAtAgreedL2b(meetBorrowerAtAgreedL2b);
        lit.setMeetBorrowerAtAgreedB2l(meetBorrowerAtAgreedB2l);
        lit.setWillDeliverToBorrower(willDeliverToBorrower);
        lit.setThirdPartyPresenceL2b(thirdPartyPresenceL2b);
        lit.setThirdPartyPresenceB2l(thirdPartyPresenceB2l);
        lit.setBorrowerThirdPartyChoice(borrowerThirdPartyChoice);
        lit.setAgreedThirdPartyChoiceL2b(agreedThirdPartyChoiceL2b);
        lit.setAgreedThirdPartyChoiceB2l(agreedThirdPartyChoiceB2l);
        lit.setBorrowerReturnsToWhichAddress(borrowerReturnsToWhichAddress);
        lit.setWillPickUpPreferredLocationB2l(willPickUpPreferredLocationB2l);
        lit.setLenderThirdPartyChoiceB2l(lenderThirdPartyChoiceB2l);
        lit.setLenderThirdPartyChoiceL2b(lenderThirdPartyChoiceL2b);

        sb = hib_session();
        tx = sb.beginTransaction();

        try {
          sb.update(lit);
          tx.commit();
          successTransaction = true;
          message(null, "LenderTransferUpdated", null);
        } catch (Exception ex) {
          message(null, "LenderTransferUpdatedFailed", null);
          tx.rollback();
          System.out.println("Error in Update Lender Transfer Perferenes");
          Logger.getLogger(LenderItemConditionsBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          sb = null;
          tx = null;
        }
      }
    }
    if (successTransaction == true) {
      ubean.setEditable(0);
    } else {
      ubean.setEditable(1);
    }

    return load_ud(ubean.getParticipant_id());

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
   * @param borrowerReturnsToWhichAddress the borrowerReturnsToWhichAddress to
   * set
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
   * @param willPickUpPreferredLocationB2l the willPickUpPreferredLocationB2l to
   * set
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
