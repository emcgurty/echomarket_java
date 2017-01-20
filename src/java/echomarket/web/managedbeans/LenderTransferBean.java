package echomarket.web.managedbeans;

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

  public LenderTransferBean() {
  }

  private List getCurrentLT(String pid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "FROM LenderTransfer ltr "
              + " WHERE ltr.participant_id = :pid"
              + " ORDER BY ltr.participant_id, ltr.dateCreated";
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

  private List getCurrentLT_Iid(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "from LenderTransfer ltr "
              + "WHERE ltr.participant_id = :pid"
              + " AND ltr.itemId = :iid "
              + " ORDER BY ltr.participant_id, ltr.itemId, ltr.dateCreated";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", iid)
              .setMaxResults(1)
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

    Boolean successTransaction = false;
    List cp_list = null;
    Session sb;
    Transaction tx;
    sb = null;
    tx = null;
    String addNewCP = null;
    String currentIid = null;
    try {
      Map<String, String> params = null;
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      addNewCP = params.get("action");
      currentIid = params.get("iid");
      ubean.setItemId(currentIid);
      this.itemId = currentIid;
      for (Map.Entry<String, String> entry : params.entrySet()) {
//        if (entry.getKey().contains("itemId")) {
//          this.itemId = entry.getValue();
//          ubean.setItemId(this.itemId);
//        }
        if (entry.getKey().contains("lenderTransferId")) {
          this.lenderTransferId = entry.getValue();
        }
        if (entry.getKey().contains("borrowerComesToWhichAddress")) {
          this.borrowerComesToWhichAddress = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("meetBorrowerAtAgreed")) {
          this.setMeetBorrowerAtAgreed(Integer.valueOf(entry.getValue()));
        }
          if (entry.getKey().contains("willDeliverToBorrower")) {
            this.setWillDeliverToBorrower(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("thirdPartyPresence")) {
          this.setThirdPartyPresence(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("borrowerThirdPartyChoice")) {
          this.setBorrowerThirdPartyChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("agreedThirdPartyChoice")) {
          this.setAgreedThirdPartyChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("borrowerReturnsToWhichAddress")) {
          this.setBorrowerReturnsToWhichAddress(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("willPickUpPreferredLocation")) {
          this.setWillPickUpPreferredLocation(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("lenderThirdPartyChoice")) {
          this.setLenderThirdPartyChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("borrowerChoice")) {
          this.setBorrowerChoice(Integer.valueOf(entry.getValue()));
        }
        System.out.println(entry.getKey() + "/" + entry.getValue());
      }
    } catch (Exception ex) {
    }

    if (this.lenderTransferId.isEmpty() == true) {

      LenderTransfer lt = new LenderTransfer(getId(), this.itemId, ubean.getParticipant_id(), this.borrowerComesToWhichAddress, this.getMeetBorrowerAtAgreed(), this.getWillDeliverToBorrower(), this.getThirdPartyPresence(), this.getBorrowerThirdPartyChoice(), this.getAgreedThirdPartyChoice(), this.getBorrowerReturnsToWhichAddress(), this.getWillPickUpPreferredLocation(), this.getLenderThirdPartyChoice(), this.getBorrowerChoice(), "NA", this.getComment(), new Date(), new Date(), null);

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
        Logger.getLogger(LenderTransferBean.class.getName()).log(Level.SEVERE, null, ex);
        message(null, "LenderTransferNotSaved", null);
      } finally {
        sb = null;
        tx = null;
      }

    } else {

      if (ubean.getItemId().isEmpty() == false) {
        cp_list = getCurrentLT_Iid(ubean.getParticipant_id(), ubean.getItemId());
      } else {
        cp_list = getCurrentLT(ubean.getParticipant_id());
      }
      if (cp_list != null) {
        if (cp_list.size() == 1) {
          LenderTransfer lit = (LenderTransfer) cp_list.get(0);
          lit.setItemId(ubean.getItemId());
          lit.setBorrowerComesToWhichAddress(this.borrowerComesToWhichAddress);
          lit.setMeetBorrowerAtAgreed(this.getMeetBorrowerAtAgreed());
          
          lit.setWillDeliverToBorrower(this.getWillDeliverToBorrower());
          lit.setThirdPartyPresence(this.getThirdPartyPresence());

          lit.setBorrowerThirdPartyChoice(this.getBorrowerThirdPartyChoice());
          lit.setAgreedThirdPartyChoice(this.getAgreedThirdPartyChoice());

          lit.setBorrowerReturnsToWhichAddress(this.getBorrowerReturnsToWhichAddress());
          lit.setWillPickUpPreferredLocation(this.getWillPickUpPreferredLocation());
          lit.setLenderThirdPartyChoice(this.getLenderThirdPartyChoice());

          lit.setBorrowerChoice(this.getBorrowerChoice());
          lit.setDateUpdated(new Date());

          sb = hib_session();
          tx = sb.beginTransaction();
          try {
            sb.update(lit);
            tx.commit();
            successTransaction = true;
            message(null, "CPUpdated", null);
          } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in Update LIT");
            Logger.getLogger(LenderTransferBean.class.getName()).log(Level.SEVERE, null, ex);
            message(null, "UpdateOrSaveOfLITNotSuccessful", null);
          } finally {
            sb = null;
            tx = null;
          }
        } else {
          // Create new record
          LenderTransfer lt = new LenderTransfer(getId(), this.itemId, ubean.getParticipant_id(), this.borrowerComesToWhichAddress, this.getMeetBorrowerAtAgreed(), this.getWillDeliverToBorrower(), this.getThirdPartyPresence(), this.getBorrowerThirdPartyChoice(), this.getAgreedThirdPartyChoice(), this.getBorrowerReturnsToWhichAddress(), this.getWillPickUpPreferredLocation(), this.getLenderThirdPartyChoice(), this.getBorrowerChoice(), "NA", this.getComment(), new Date(), new Date(), null);
          try {
            sb = hib_session();
            tx = sb.beginTransaction();
            sb.save(lt);
            tx.commit();
            //ubean.setCpId(true);
            message(null, "CPSaved", null);
            successTransaction = true;
          } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in Save LIT");
            Logger.getLogger(LenderTransfer.class.getName()).log(Level.SEVERE, null, ex);
          } finally {
            sb = null;
            tx = null;
          }
        }
      }
    }

    if (successTransaction == true) {
      ubean.setEditable(1);
      if (ubean.getItemId() != null) {
        if (ubean.getItemId().isEmpty() == false) {
          ubean.completeLIT(ubean.getParticipant_id());
        } else {
          ubean.setLITid(true);
        }
      }
    } else {
      ubean.setEditable(0);
      ubean.setLITid(false);
    }

   // return load_ud(ubean.getParticipant_id()) + "?faces-redirect=true";
   return "lender_transfer.xhtml?faces-redirect=true";

  }

  public List load_ud(String pid) {

    List result = null;
    Map<String, String> params = null;
    params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String strIid = null;
    String action = null;
    Boolean isLITnull = false;

    try {
      strIid = params.get("iid");
      if (strIid != null) {
        if (strIid.isEmpty() == true) {
          strIid = null;
        }
      }
    } catch (Exception ex) {
    }

    if (ubean.getEditable() != null) {
      if (ubean.getEditable() == 0) {
        ubean.setEditable(1);
      } else {
        ubean.setEditable(0);
      }
    } else {
      ubean.setEditable(1);
    }
    try {
      action = params.get("action");
    } catch (Exception ex) {
    }

    if (action != null) {
      if ("edit".equals(action)) {
        ubean.setEditable(1);
      }
    }

    if (strIid != null) {
      result = getCurrentLT_Iid(pid, strIid);
    }
    if (result == null) {
      isLITnull = true;
    }

    if (result != null) {
      if (result.size() == 0) {
        isLITnull = true;
      }
    }
    
    if (isLITnull == true) {
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
          this.setMeetBorrowerAtAgreed(ltr.getMeetBorrowerAtAgreed());

          this.setWillDeliverToBorrower(ltr.getWillDeliverToBorrower());
          this.setThirdPartyPresence(ltr.getThirdPartyPresence());

          this.setBorrowerThirdPartyChoice(ltr.getBorrowerThirdPartyChoice());
          this.setAgreedThirdPartyChoice(ltr.getAgreedThirdPartyChoice());

          this.setBorrowerReturnsToWhichAddress(ltr.getBorrowerReturnsToWhichAddress());
          this.setWillPickUpPreferredLocation(ltr.getWillPickUpPreferredLocation());
          this.setLenderThirdPartyChoice(ltr.getLenderThirdPartyChoice());

          this.setBorrowerChoice(ltr.getBorrowerChoice());
          ltr = null;
        }
      }
    }
    //  result = null;
//  emm true
//    return "lender_transfer?faces-redirect=true";
//    return "lender_transfer";
    return result;
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
   * @return the meetBorrowerAtAgreed
   */
  public Integer getMeetBorrowerAtAgreed() {
    return meetBorrowerAtAgreed;
  }

  /**
   * @param meetBorrowerAtAgreed the meetBorrowerAtAgreed to set
   */
  public void setMeetBorrowerAtAgreed(Integer meetBorrowerAtAgreed) {
    this.meetBorrowerAtAgreed = meetBorrowerAtAgreed;
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
   * @return the thirdPartyPresence
   */
  public Integer getThirdPartyPresence() {
    return thirdPartyPresence;
  }

  /**
   * @param thirdPartyPresence the thirdPartyPresence to set
   */
  public void setThirdPartyPresence(Integer thirdPartyPresence) {
    this.thirdPartyPresence = thirdPartyPresence;
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
   * @return the agreedThirdPartyChoice
   */
  public Integer getAgreedThirdPartyChoice() {
    return agreedThirdPartyChoice;
  }

  /**
   * @param agreedThirdPartyChoice the agreedThirdPartyChoice to set
   */
  public void setAgreedThirdPartyChoice(Integer agreedThirdPartyChoice) {
    this.agreedThirdPartyChoice = agreedThirdPartyChoice;
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
   * @return the willPickUpPreferredLocation
   */
  public Integer getWillPickUpPreferredLocation() {
    return willPickUpPreferredLocation;
  }

  /**
   * @param willPickUpPreferredLocation the willPickUpPreferredLocation to set
   */
  public void setWillPickUpPreferredLocation(Integer willPickUpPreferredLocation) {
    this.willPickUpPreferredLocation = willPickUpPreferredLocation;
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
