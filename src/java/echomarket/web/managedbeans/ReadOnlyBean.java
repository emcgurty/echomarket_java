/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import echomarket.hibernate.Participant;
import echomarket.hibernate.Users;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean
@SessionScoped
public class ReadOnlyBean extends AbstractBean implements Serializable {

  private String participant_id;
  private String itemId;
  private String which;

  public String load_RO(String strwhich, String iid, String pid) {
    if ((strwhich.isEmpty() == false) && (iid.isEmpty() == false) && (pid.isEmpty() == false)) {
      // will be null if called from menu.... values assigned when user clicks Item Details from panels
      this.setWhich(strwhich);
      this.setItemId(iid);
      this.setParticipant_id(pid);
    }
    // Get action
    Map<String, String> params = null;
    String action = null;

    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      action = params.get("action");
    } catch (Exception ex) {
    }
    return action + ".xhtml?faces-redirect=true";
  }

  public List getLIC(String iid, String which, String pid) {

    setItemId(iid);
    setWhich(which);
    setParticipant_id(pid);
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM LenderItemConditions lic "
              + " WHERE  (lic.itemId = :iid) "
              + " AND  (lic.participant_id = :pid)";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getLIC in ReadONlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getLenderTransferData(String iid, String which, String pid) {

    setItemId(iid);
    setWhich(which);
    setParticipant_id(pid);

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM LenderTransfer lt "
              + " WHERE  (lt.itemId = :iid) "
              + " AND  (lt.participant_id = :pid)";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getLenderTransferData in ReadnNlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }
  
    return result;
  }

  public List getItemData(String iid, String which, String pid) {

    setItemId(iid);
    setWhich(which);
    setParticipant_id(pid);
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "FROM Items WHERE  (itemId = :iid) AND (participant_id = :pid)";
      System.out.println(queryString);
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getItemData in ReadOnlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByNAE(String iid, String which) {

    setItemId(iid);
    setWhich(which);
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT part FROM Participant part "
              + " left join part.item it "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .list();
      tx.commit();
      if (result != null) {
        if (result.size() == 1) {
          Participant part = (Participant) result.get(0);
          setParticipant_id(part.getParticipant_id());
        }
      }
    } catch (Exception e) {
      System.out.println("Error in getByNAE in ReadOnlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getBySocialMedia(String iid, String which, String pid) {

    setItemId(iid);
    setWhich(which);
    setParticipant_id(pid);
    
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "FROM ContactPreference "
              + " WHERE (itemId = :iid) AND (participant_id = :pid) ";

      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getbySocialMedia in ReadOnlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByPhone(String iid, String which, String pid) {

    setItemId(iid);
    setWhich(which);
    setParticipant_id(pid);
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT cp.contactByHomePhone, cp.contactByCellPhone, cp.contactByAlternativePhone, "
              + " part.homePhone, part.cellPhone, part.alternativePhone, "
              + " part.displayHomePhone, part.displayCellPhone, part.displayAlternativePhone "
              + " FROM Participant part "
              + " INNER join part.contactPreference cp "
              + " WHERE  (cp.itemId = :iid)  AND (part.participant_id = :pid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getByPhone in ReadOnlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByEmail(String iid, String which, String pid) {

    setItemId(iid);
    setWhich(which);
    setParticipant_id(pid);
    
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT part.emailAlternative as altemail, us.email as lemail, cp.contactByEmail  "
              + " FROM Users us "
              + " INNER join us.participant part "
              + " INNER join part.contactPreference cp "
              + " WHERE (cp.itemId = :iid) AND (part.participant_id = :pid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      
      tx.commit();
    } catch (Exception e) {
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getBorrowerComesToPrimaryAddress() {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT addr "
              + " FROM Participant part "
              + " inner join part.addresses addr "
              + " inner join part.item itm "
              + " inner join itm.lenderTransfer ltrans "
              + " WHERE (ltrans.borrowerComesToWhichAddress = 1 OR ltrans.borrowerComesToWhichAddress = 3)"
              + " AND  (ltrans.itemId = :iid) "
              + " AND  (part.participant_id = :pid) "
              + " AND  (part.displayAddress = 1 )"
              + " AND  (addr.addressType = 'primary')";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.itemId)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadONlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getBorrowerComesToAlternativeAddress() {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT addr "
              + " FROM Participant part "
              + " inner join part.addresses addr "
              + " inner join part.item itm "
              + " inner join itm.lenderTransfer ltrans "
              + " WHERE (ltrans.borrowerComesToWhichAddress = 1 OR ltrans.borrowerComesToWhichAddress = 2)"
              + " AND  (ltrans.itemId = :iid)"
              + " AND  (part.participant_id = :pid)"
              + " AND  (addr.addressType = 'alternative')";
      result = hib.createQuery(queryString)
              .setParameter("iid", this.itemId)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadONlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  //// Probably do not need arg String iid, should just be using item_id property
  public List getPrimaryAddress(String iid) {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT addr "
              + " FROM Participant part "
              + " inner join part.addresses addr "
              + " inner join part.contactPreference cp "
              + " WHERE (cp.useWhichContactAddress = 1 OR cp.useWhichContactAddress = 3)"
              + " AND  (cp.itemId = :iid)"
              + " AND  (part.displayAddress = 1) "
              + " AND  (part.participant_id = :pid) "
              + " AND  (addr.addressType = 'primary')";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadONlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getAlternativeAddress(String iid) {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = "SELECT addr.addressLine1, addr.addressLine2, addr.postalCode, "
              + " addr.city, addr.province, addr.usStateId, addr.region, addr.countryId "
              + " FROM Participant part "
              + " left join part.addresses addr"
              + " left join part.contactPreference cp "
              + " left join part.item it "
              + " WHERE (cp.useWhichContactAddress = 1 OR cp.useWhichContactAddress = 3)"
              + " AND  (it.itemId = :iid)"
              + " AND  (part.displayAlternativeAddress = 1)"
              + " AND  (part.participant_id = :pid)"
              + " AND  (addr.addressType = 'alternative')";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getAlternativeAdrress in ReadOnlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

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
   * @return the which
   */
  public String getWhich() {
    return which;
  }

  /**
   * @param which the which to set
   */
  public void setWhich(String which) {
    this.which = which;
  }

  public List getExistingAddress(String pid, String which) {

    List result = null;
    Session hib = hib_session();
    Transaction tx = hib.beginTransaction();

    String queryString = "from Addresses where participant_id = :pid AND address_type = :which";
    try {
      result = hib.createQuery(queryString)
              .setParameter("pid", pid)
              .setParameter("which", which)
              .list();
      tx.commit();

    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getExistingAddress in ROBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);

    } finally {
      tx = null;
      hib = null;
    }
    return result;
  }

}
