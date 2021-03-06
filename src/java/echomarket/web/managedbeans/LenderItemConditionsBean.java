package echomarket.web.managedbeans;

import echomarket.hibernate.LenderItemConditions;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("lenderItemConditionsBean")
@RequestScoped
public class LenderItemConditionsBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String lender_item_condition_id;
  private String participant_id;
  private String itemId;
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

  public LenderItemConditionsBean() {
  }

  private List getCurrentItemConditions(String pid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "from  LenderItemConditions lic "
              + " WHERE lic.participant_id = :pid"
              + " ORDER BY lic.participant_id, lic.dateCreated ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setMaxResults(1)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getCurrentLic");
      e.printStackTrace();
      tx.rollback();
      return null;
    } finally {
      tx = null;
      session = null;

    }
    return result;
  }

  private List getCurrentItemConditions_Iid(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = " from LenderItemConditions lic "
              + " WHERE lic.participant_id = :pid "
              + " AND lic.itemId = :iid "
              + " ORDER BY lic.participant_id, lic.itemId, lic.dateCreated ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", iid)
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

  public String updateLIC() {

    Session sb;
    Transaction tx;
    sb = null;
    tx = null;
    List icList = null;
    Boolean successTransaction = false;
    Map<String, String> params = null;
    String addNewCP = null;
    String strIid = null;

    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      addNewCP = params.get("action");
      strIid = params.get("iid");
      if (strIid == null) {
        strIid = "";
      }
      app.setItemId(strIid);
      this.itemId = strIid;
      for (Map.Entry<String, String> entry : params.entrySet()) {
        if (entry.getKey().contains("lender_item_condition_id")) {
          this.lender_item_condition_id = entry.getValue();
        }
        if (entry.getKey().contains("forFree")) {
          this.forFree = Integer.valueOf(entry.getValue());
        }
        if ("personal_information:availableForPurchase".equals(entry.getKey())) {
          this.availableForPurchase = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("availableForPurchaseAmount")) {
          this.availableForPurchaseAmount = new BigDecimal(entry.getValue());
        }
        if ("personal_information:smallFee".equals(entry.getKey())) {
          this.smallFee = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("smallFeeAmount")) {
          this.smallFeeAmount = new BigDecimal(entry.getValue());
        }
        if (entry.getKey().contains("availableForDonation")) {
          this.availableForDonation = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("donateAnonymous")) {
          this.donateAnonymous = Integer.valueOf(entry.getValue());
        }
        if ("personal_information:trade".equals(entry.getKey())) {
          this.trade = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("tradeItem")) {
          this.tradeItem = entry.getValue();
        }
        if (entry.getKey().contains("agreedNumberOfDays")) {
          this.agreedNumberOfDays = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("agreedNumberOfHours")) {
          this.agreedNumberOfHours = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("indefiniteDuration")) {
          this.indefiniteDuration = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("presentDuringBorrowingPeriod")) {
          this.presentDuringBorrowingPeriod = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("entirePeriod")) {
          this.entirePeriod = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("partialPeriod")) {
          this.partialPeriod = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("provideProperUseTraining")) {
          this.provideProperUseTraining = Integer.valueOf(entry.getValue());
        }
        if (entry.getKey().contains("specificConditions")) {
          String hold = entry.getValue();
          if (hold.length() > 254) {
            this.specificConditions = hold.substring(0, 254);
          } else {
            this.specificConditions = hold;
          }

        }
        if (entry.getKey().contains("securityDepositAmount")) {
          this.securityDepositAmount = new BigDecimal(entry.getValue());
        }
        if ("personal_information:securityDeposit".equals(entry.getKey())) {
          this.securityDeposit = Integer.valueOf(entry.getValue());
        }
      }

    } catch (Exception ex) {

    } finally {

      params = null;

    }

    if (lender_item_condition_id.isEmpty() == true) {

      LenderItemConditions lic = new LenderItemConditions(getId(), app.getParticipant_id(), this.itemId, this.forFree, this.availableForPurchase, this.availableForPurchaseAmount, this.smallFee, this.smallFeeAmount, this.availableForDonation, this.donateAnonymous, this.trade, this.tradeItem, this.agreedNumberOfDays, this.agreedNumberOfHours, this.indefiniteDuration, this.presentDuringBorrowingPeriod, this.entirePeriod, this.partialPeriod, this.provideProperUseTraining, this.specificConditions, this.securityDepositAmount, this.securityDeposit, getClientIpAddr(), this.comment, new Date(), new Date());

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        sb.save(lic);
        tx.commit();
        successTransaction = true;
        app.setEditable(1);
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Error in updateLIC");
        Logger.getLogger(LenderItemConditionsBean.class.getName()).log(Level.SEVERE, null, ex);
        message(null, "LenderItemConditionsNotSaved", null);
        app.setEditable(0);
      } finally {
        sb = null;
        tx = null;
      }
    } else {

      if (this.itemId.isEmpty() == false) {
        icList = getCurrentItemConditions_Iid(app.getParticipant_id(), this.itemId);
      } else {
        icList = getCurrentItemConditions(app.getParticipant_id());
      }

      if (icList != null) {
        if (icList.size() == 1) {
          LenderItemConditions ic = (LenderItemConditions) icList.get(0);
          ic.setItemId(this.itemId);
          ic.setAgreedNumberOfDays(this.agreedNumberOfDays);
          ic.setAgreedNumberOfHours(this.agreedNumberOfHours);
          ic.setAvailableForDonation(this.availableForDonation);
          ic.setAvailableForPurchase(this.availableForPurchase);
          ic.setAvailableForPurchaseAmount(this.availableForPurchaseAmount);
          ic.setDonateAnonymous(this.donateAnonymous);
          ic.setEntirePeriod(this.entirePeriod);
          ic.setForFree(this.forFree);
          ic.setIndefiniteDuration(this.indefiniteDuration);
          ic.setPartialPeriod(this.partialPeriod);
          ic.setPresentDuringBorrowingPeriod(this.presentDuringBorrowingPeriod);
          ic.setProvideProperUseTraining(this.provideProperUseTraining);
          ic.setSecurityDeposit(this.securityDeposit);
          ic.setSecurityDepositAmount(this.securityDepositAmount);
          ic.setSmallFee(this.smallFee);
          ic.setSmallFeeAmount(this.smallFeeAmount);
          ic.setSpecificConditions(this.specificConditions);
          ic.setTrade(this.trade);
          ic.setTradeItem(this.tradeItem);

          try {
            sb = hib_session();
            tx = sb.beginTransaction();
            sb.update(ic);
            tx.commit();
            successTransaction = true;
            app.setEditable(1);
            message(null, "LenderItemConditionsUpdated", null);
          } catch (Exception ex) {
            message(null, "LenderItemConditionsUpdatedFailed", null);
            tx.rollback();
            System.out.println("Error in Update Lender Item Conditions");
            Logger.getLogger(LenderItemConditionsBean.class.getName()).log(Level.SEVERE, null, ex);
            app.setEditable(0);
          } finally {
            sb = null;
            tx = null;
          }
        } else {
          // Create new record
          LenderItemConditions lic = new LenderItemConditions(getId(), app.getParticipant_id(), this.itemId, this.forFree, this.availableForPurchase, this.availableForPurchaseAmount, this.smallFee, this.smallFeeAmount, this.availableForDonation, this.donateAnonymous, this.trade, this.tradeItem, this.agreedNumberOfDays, this.agreedNumberOfHours, this.indefiniteDuration, this.presentDuringBorrowingPeriod, this.entirePeriod, this.partialPeriod, this.provideProperUseTraining, this.specificConditions, this.securityDepositAmount, this.securityDeposit, getClientIpAddr(), this.comment, new Date(), new Date());
          try {
            sb = hib_session();
            tx = sb.beginTransaction();
            sb.save(lic);
            tx.commit();
            message(null, "LenderItemConditionsUpdated", null);
            successTransaction = true;
          } catch (Exception ex) {
            tx.rollback();
            message(null, "LenderItemConditionsUpdatedFailed", null);
            System.out.println("Error in Save LIC");
            Logger.getLogger(LenderItemConditionsBean.class.getName()).log(Level.SEVERE, null, ex);
          } finally {
            sb = null;
            tx = null;
          }
        }
      }
    }

    if (successTransaction == true) {
      if (this.itemId.isEmpty() == true) {   /// User is setting default LIC
        app.setLICid(true);
      } else {
        app.completeLIC(app.getParticipant_id());
      }
    } else {
      app.setLICid(false);
    }

    return load_ud(app.getParticipant_id());
  }

  public String load_ud(String pid) {

    List result = null;
    Map<String, String> params = null;
    params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String strIid = null;
    String action = null;
    Boolean isLICnull = false;

    try {
      strIid = params.get("iid");
      if (strIid != null) {
        if (strIid.isEmpty() == true) {
          strIid = null;
        }
      }
    } catch (Exception ex) {
    }
    if (app != null) {
      if (app.getEditable() != null) {
        if (app.getEditable() == 0) {
          app.setEditable(1);
        } else {
          app.setEditable(0);
        }
      } else {
        app.setEditable(1);
      }
      try {
        action = params.get("action");
      } catch (Exception ex) {
      }

      if (action != null) {
        if ("edit".equals(action)) {
          app.setEditable(1);
        }
      }
    }
    if (strIid != null) {
      result = getCurrentItemConditions_Iid(pid, strIid);
    }
    if (result == null) {
      isLICnull = true;
    }

    if (result != null) {
      if (result.size() == 0) {
        isLICnull = true;
      }
    }

    if (isLICnull == true) {
      result = getCurrentItemConditions(pid);
    }
    if (result != null) {
      if (result.size() == 1) {
        LenderItemConditions pp = (LenderItemConditions) result.get(0);
        if (pp != null) {
          this.itemId = pp.getItemId();
          this.lender_item_condition_id = (pp.getLender_item_condition_id());
          this.participant_id = (pp.getParticipant_id());
          this.forFree = pp.getForFree();
          this.availableForPurchase = pp.getAvailableForPurchase();
          this.availableForPurchaseAmount = pp.getAvailableForPurchaseAmount();
          this.smallFee = pp.getSmallFee();
          this.smallFeeAmount = pp.getSmallFeeAmount();
          this.availableForDonation = pp.getAvailableForDonation();
          this.donateAnonymous = pp.getDonateAnonymous();
          this.trade = pp.getTrade();
          this.tradeItem = (pp.getTradeItem());
          this.agreedNumberOfDays = pp.getAgreedNumberOfDays();
          this.agreedNumberOfHours = pp.getAgreedNumberOfHours();
          this.indefiniteDuration = pp.getIndefiniteDuration();
          this.presentDuringBorrowingPeriod = pp.getPresentDuringBorrowingPeriod();
          this.entirePeriod = pp.getEntirePeriod();
          this.partialPeriod = pp.getPartialPeriod();
          this.provideProperUseTraining = pp.getProvideProperUseTraining();
          this.specificConditions = pp.getSpecificConditions();
          this.securityDepositAmount = pp.getSecurityDepositAmount();
          this.securityDeposit = pp.getSecurityDeposit();
//this.comment= pp.get(); // Later
          pp = null;
        }
      }
    }
    result = null;

    return "lender_conditions";
  }

  /**
   * @return the lender_item_condition_id
   */
  @Id
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
