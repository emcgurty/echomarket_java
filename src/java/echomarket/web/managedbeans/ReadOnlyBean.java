package echomarket.web.managedbeans;

import echomarket.hibernate.Participant;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class ReadOnlyBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private String participant_id;
  private String itemId;
  private String which;
  private List itemFoundList;

  // emm 1.8
  public ReadOnlyBean() {

  }

  public String load_RO(String strwhich, String iid, String pid) {
    if ((strwhich.isEmpty() == false) && (iid.isEmpty() == false) && (pid.isEmpty() == false)) {
      // will be null if called from menu.... values assigned when user clicks Item Details from panels
      this.setWhich(strwhich);
      this.setItemId(iid);
      this.setParticipant_id(pid);
      setItemFoundList(null);
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
              + " AND  (lic.participant_id = :pid)  ORDER BY lic.dateCreated ";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getLIC in ReadOnlyBean");
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
              + " AND  (lt.participant_id = :pid) ORDER BY lt.dateCreated ";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("pid", pid)
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getLenderTransferData in ReadOnlyBean");
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
    if (this.getItemFoundList() == null) {
      try {
        hib = hib_session();
        tx = hib.beginTransaction();
        queryString = "FROM Items WHERE itemId = :iid AND participant_id = :pid  ORDER BY dateCreated ";
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
        this.setItemFoundList(result);
      }

    }
    return this.getItemFoundList();
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
              + " WHERE it.itemId = :iid ORDER BY it.dateCreated ";

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
              + " WHERE itemId = :iid AND participant_id = :pid ORDER BY dateCreated ";

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

    /*
 * //  For the moment this call is working, the returned object has two tiers the object and then the result. But I have seen the returned object has only one tier, which will cause the GUI to fail
     */
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
              + " WHERE cp.itemId = :iid  AND part.participant_id = :pid ORDER BY cp.dateCreated ";

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
      queryString = "SELECT part.emailAlternative as altemail, us.email as lemail, cp.contactByEmail, part.questionAltEmail  "
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
              + " WHERE (part.participant_id = :pid )"
              + " AND  (addr.addressType = 'primary')";

      result = hib.createQuery(queryString)
              .setParameter("pid", this.participant_id)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadOnlyBean");
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
              + " WHERE (part.participant_id = :pid )"
              + " AND  (addr.addressType = 'alternative')";
      result = hib.createQuery(queryString)
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
              + " AND  (addr.addressType = 'primary')"
              + " GROUP BY addr.addressType ";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
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

      queryString = "SELECT addr "
              + " FROM Participant part "
              + " left join part.addresses addr"
              + " left join part.contactPreference cp "
              + " left join part.item it "
              + " WHERE (cp.useWhichContactAddress = 2 OR cp.useWhichContactAddress = 1)"
              + " AND  (it.itemId = :iid)"
              + " AND  (part.displayAlternativeAddress = 1)"
              + " AND  (addr.addressType = 'alternative') "
              + " GROUP BY addr.addressType ";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getAddress in ReadOnlyBean");
      Logger.getLogger(ReadOnlyBean.class.getName()).log(Level.SEVERE, null, e);
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }
    //echomarket.hibernate.ParticipantAddress hold = (echomarket.hibernate.ParticipantAddress)result.get(0);
    //Participant pp = (Participant) hold.getPart();  /// Got my Participant record

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
    Session hib = null;
    Transaction tx = null;

    String queryString = "from Addresses where participant_id = :pid AND address_type = :which";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
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

  /**
   * @return the itemFoundList
   */
  public List getItemFoundList() {
    return itemFoundList;
  }

  /**
   * @param itemFoundList the itemFoundList to set
   */
  public void setItemFoundList(List itemFoundList) {
    this.itemFoundList = itemFoundList;
  }

}
