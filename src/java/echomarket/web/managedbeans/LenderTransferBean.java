package echomarket.web.managedbeans;

import echomarket.hibernate.LenderTransfer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean
@SessionScoped
public class LenderTransferBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
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
      System.out.println("Error in getCurrentLT");
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

        if (entry.getKey().contains("lenderTransferId")) {
          this.lenderTransferId = entry.getValue();
        }

        if (entry.getKey().contains("borrowerComesToWhichAddress")) {
          this.borrowerComesToWhichAddress = Integer.valueOf(entry.getValue());
        }
        if ("personal_information:meetBorrowerAtAgreed".equals(entry.getKey())) {
          this.setMeetBorrowerAtAgreed(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("meetBorrowerAtAgreedB")) {
          this.setMeetBorrowerAtAgreedBorrowerChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("meetBorrowerAtAgreedL")) {
          this.setMeetBorrowerAtAgreedLenderChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("meetBorrowerAtAgreedM")) {
          this.setMeetBorrowerAtAgreedMutual(Integer.valueOf(entry.getValue()));
        }

        if (entry.getKey().contains("willDeliverToBorrower")) {
          this.setWillDeliverToBorrower(Integer.valueOf(entry.getValue()));
        }
        if ("personal_information:thirdPartyPresence".equals(entry.getKey())) {
          this.setThirdPartyPresence(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("thirdPartyPresenceB")) {
          this.setThirdPartyPresenceBorrowerChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("thirdPartyPresenceL")) {
          this.setThirdPartyPresenceLenderChoice(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("thirdPartyPresenceM")) {
          this.setThirdPartyPresenceMutual(Integer.valueOf(entry.getValue()));
        }

        if (entry.getKey().contains("borrowerReturnsToWhichAddress")) {
          this.setBorrowerReturnsToWhichAddress(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("willPickUpPreferredLocation")) {
          this.setWillPickUpPreferredLocation(Integer.valueOf(entry.getValue()));
        }
        if (entry.getKey().contains("comment")) {
          String hold = entry.getValue();
          if (hold.length() > 254) {
            this.comment = hold.substring(0, 254);
          } else {
            this.comment = hold;
          }

        }

      }
    } catch (Exception ex) {
    }

    if (this.thirdPartyPresence != null) {
      if (this.thirdPartyPresence == 0) {
        this.thirdPartyPresenceBorrowerChoice = -9;
        this.thirdPartyPresenceLenderChoice = -9;
        this.thirdPartyPresenceMutual = -9;
      }
    }

    if (this.meetBorrowerAtAgreed != null) {
      if (this.meetBorrowerAtAgreed == 0) {
        this.meetBorrowerAtAgreedBorrowerChoice = -9;
        this.meetBorrowerAtAgreedLenderChoice = -9;
        this.meetBorrowerAtAgreedMutual = -9;
      }
    }

    if (this.lenderTransferId.isEmpty() == true) {

      LenderTransfer lt = new LenderTransfer(getId(), this.itemId, ubean.getParticipant_id(),
              this.borrowerComesToWhichAddress,
              this.meetBorrowerAtAgreed, this.getMeetBorrowerAtAgreedBorrowerChoice(), this.getMeetBorrowerAtAgreedLenderChoice(), this.getMeetBorrowerAtAgreedMutual(),
              this.willDeliverToBorrower,
              this.thirdPartyPresence, this.getThirdPartyPresenceBorrowerChoice(), this.getThirdPartyPresenceLenderChoice(), this.getThirdPartyPresenceMutual(),
              this.borrowerReturnsToWhichAddress,
              this.willPickUpPreferredLocation,
              getClientIpAddr(), this.comment, new Date(), new Date(), null);

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        sb.save(lt);
        tx.commit();
        // message(null, "LenderTransferSaved", null);
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

      if (this.itemId.isEmpty() == false) {
        cp_list = getCurrentLT_Iid(ubean.getParticipant_id(), this.itemId);
      } else {
        cp_list = getCurrentLT(ubean.getParticipant_id());
      }
      if (cp_list != null) {
        if (cp_list.size() == 1) {
          LenderTransfer lit = (LenderTransfer) cp_list.get(0);
          lit.setItemId(ubean.getItemId());
          lit.setBorrowerComesToWhichAddress(this.borrowerComesToWhichAddress);
          lit.setMeetBorrowerAtAgreed(this.meetBorrowerAtAgreed);
          lit.setMeetBorrowerAtAgreedBorrowerChoice(this.getMeetBorrowerAtAgreedBorrowerChoice());
          lit.setMeetBorrowerAtAgreedLenderChoice(this.getMeetBorrowerAtAgreedLenderChoice());
          lit.setMeetBorrowerAtAgreedMutual(this.getMeetBorrowerAtAgreedMutual());
          lit.setWillDeliverToBorrower(this.getWillDeliverToBorrower());
          lit.setThirdPartyPresence(this.getThirdPartyPresence());
          lit.setThirdPartyPresenceBorrowerChoice(this.getThirdPartyPresenceBorrowerChoice());
          lit.setThirdPartyPresenceLenderChoice(this.getThirdPartyPresenceLenderChoice());
          lit.setThirdPartyPresenceMutual(this.getThirdPartyPresenceMutual());
          lit.setBorrowerReturnsToWhichAddress(this.getBorrowerReturnsToWhichAddress());
          lit.setWillPickUpPreferredLocation(this.getWillPickUpPreferredLocation());
          lit.setComment(this.getComment());
          lit.setDateUpdated(new Date());

          sb = hib_session();
          tx = sb.beginTransaction();
          try {
            sb.update(lit);
            tx.commit();
            successTransaction = true;
            // message(null, "LenderTransferSaved", null);
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
          LenderTransfer lt = new LenderTransfer(getId(), this.itemId, ubean.getParticipant_id(),
                  this.borrowerComesToWhichAddress,
                  this.meetBorrowerAtAgreed, this.getMeetBorrowerAtAgreedBorrowerChoice(), this.getMeetBorrowerAtAgreedLenderChoice(), this.getMeetBorrowerAtAgreedMutual(),
                  this.willDeliverToBorrower,
                  this.thirdPartyPresence, this.getThirdPartyPresenceBorrowerChoice(), this.getThirdPartyPresenceLenderChoice(), this.getThirdPartyPresenceMutual(),
                  this.borrowerReturnsToWhichAddress,
                  this.willPickUpPreferredLocation,
                  "NA", this.comment, new Date(), new Date(), null);
          try {
            sb = hib_session();
            tx = sb.beginTransaction();
            sb.save(lt);
            tx.commit();

            //  message(null, "LenderTransferSaved", null);
            successTransaction = true;
          } catch (Exception ex) {
            tx.rollback();
            message(null, "UpdateOrSaveOfLITNotSuccessful", null);
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

    return load_ud(ubean.getParticipant_id());
//   return "lender_transfer.xhtml?faces-redirect=true";
//   return "lender_transfer";

  }

  public String load_ud(String pid) {

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
    // emm 1.8
    if (ubean != null) {
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
          this.meetBorrowerAtAgreed = (ltr.getMeetBorrowerAtAgreed());
          this.meetBorrowerAtAgreedBorrowerChoice = (ltr.getMeetBorrowerAtAgreedBorrowerChoice());
          this.meetBorrowerAtAgreedLenderChoice = (ltr.getMeetBorrowerAtAgreedLenderChoice());
          this.meetBorrowerAtAgreedMutual = (ltr.getMeetBorrowerAtAgreedMutual());

          this.willDeliverToBorrower = (ltr.getWillDeliverToBorrower());

          this.thirdPartyPresence = (ltr.getThirdPartyPresence());
          this.thirdPartyPresenceBorrowerChoice = (ltr.getThirdPartyPresenceBorrowerChoice());
          this.thirdPartyPresenceLenderChoice = (ltr.getThirdPartyPresenceLenderChoice());
          this.thirdPartyPresenceMutual = (ltr.getThirdPartyPresenceMutual());

          this.borrowerReturnsToWhichAddress = (ltr.getBorrowerReturnsToWhichAddress());
          this.willPickUpPreferredLocation = (ltr.getWillPickUpPreferredLocation());
          this.comment = (ltr.getComment());

          ltr = null;
        }
      } else {
        if (ubean != null) {
          this.participant_id = ubean.getParticipant_id();
        }
        this.borrowerComesToWhichAddress = -9;
        this.meetBorrowerAtAgreed = -9;
        this.meetBorrowerAtAgreedBorrowerChoice = -9;
        this.meetBorrowerAtAgreedLenderChoice = -9;
        this.meetBorrowerAtAgreedMutual = -9;

        this.willDeliverToBorrower = -9;
        this.thirdPartyPresence = -9;
        this.thirdPartyPresenceBorrowerChoice = -9;
        this.thirdPartyPresenceLenderChoice = -9;
        this.thirdPartyPresenceMutual = -9;
        this.willPickUpPreferredLocation = -9;
        this.borrowerReturnsToWhichAddress = -9;
        if (ubean != null) {
          ubean.setEditable(1);
        }
      }

    }

    return "lender_transfer";
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
