/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "readOnly")
@RequestScoped
public class ReadOnlyBean extends AbstractBean implements Serializable {

  private String participant_id;
  private String itemId;
  private String which;

  public String load_RO(String strwhich, String iid) {
    this.setItemId(iid);
    this.setWhich(strwhich);
    return "read_only";
  }

  public List getLIC() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = " FROM LenderItemConditions lic "
              + " WHERE  (lic.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getLIC in ReadONlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getLenderTransferData() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = " FROM LenderTransfer lt "
              + " WHERE  (lt.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getLenderTransferData in ReadnNlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getItemData() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT it.otherItemCategory, it.itemModel, it.itemDescription,  it.itemConditionId, it.itemCount, it.itemType "
              + " FROM Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getItemData in ReadONlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByNAE() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT part.firstName, part.lastName, part.mi, part.displayName "
              + " part.contactDescribeId, part.organizationName, part.displayOrganization, part.otherDescribeYourself "
              + " it.itemType "
              + " FROM Participant part "
              + " left join part.item it "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error in getByNAE in ReadONlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getBySocialMedia() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT cp.contactBySocialMedia, cp.contactByOtherSocialMedia, "
              + " cp.contactByFacebook, cp.contactByTwitter, cp.contactByInstagram "
              + " FROM Participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getbySocialMedia in ReadOnlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByPhone() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT cp.contactByHomePhone, cp.contactByCellPhone, cp.contactByAlternativePhone, "
              + " part.homePhone, part.cellPhone, part.alternativePhone, "
              + " part.DisplayHomePhone, part.displayCellPhone, part.displayAlternativePhone "
              + " it.itemType "
              + " FROM Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getByPhone in ReadOnlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByEmail() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT part.emailAlternative altemail, us.email lemail, cp.contactByEmail  "
              + " FROM Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE (cp.contactByEmail = 1)"
              + " AND  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getByEmail in ReadOnlyBean");
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
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT addr.addressLine1, addr.addressLine2, addr.postalCode, "
              + " addr.city, addr.province, addr.usStateId, addr.region, addr.countryId "
              + " FROM Participipant part "
              + " left join part.item it "
              + " left join part.addresses addr "
              + " left join it.lenderTransfer ltrans "
              + " WHERE (ltrans.borrowerComesToWhichAddress = 1 OR ltrans.borrowerComesToWhichAddress = 3)"
              + " AND  (it.itemId = :iid)"
              + " AND  (part.displayAddress = = 1)"
              + " AND  (addr.addressType = 'primary')";
      result = hib.createQuery(queryString)
              .setParameter("iid", this.itemId)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadONlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getBorrowerComesToAlternativeAddress() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT addr.addressLine1, addr.addressLine2, addr.postalCode, "
              + " addr.city, addr.province, addr.usStateId, addr.region, addr.countryId "
              + " FROM Participipant part "
              + " left join part.item it "
              + " left join part.addresses addr "
              + " left join it.lenderTransfer ltrans "
              + " WHERE (ltrans.borrowerComesToWhichAddress = 1 OR ltrans.borrowerComesToWhichAddress = 2)"
              + " AND  (it.itemId = :iid)"
              + " AND  (addr.addressType = 'alternative')";
      result = hib.createQuery(queryString)
              .setParameter("iid", this.itemId)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadONlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getPrimaryAddress(String iid) {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT addr.addressLine1, addr.addressLine2, addr.postalCode, "
              + " addr.city, addr.province, addr.usStateId, addr.region, addr.countryId "
              + " FROM Participant part "
              + " left join part.addresses addr"
              + " left join part.contactPreference cp "
              + " left join part.item it "
              + " WHERE (cp.useWhichContactAddress = 1 OR cp.useWhichContactAddress = 3)"
              + " AND  (it.itemId = :iid)"
              + " AND  (part.displayAddress = = 1)"
              + " AND  (addr.addressType = 'primary')";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getPrimaryAdrress in ReadONlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getAlternativeAddress(String iid) {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT addr.addressLine1, addr.addressLine2, addr.postalCode, "
              + " addr.city, addr.province, addr.usStateId, addr.region, addr.countryId "
              + " FROM Participant part "
              + " left join part.addresses addr"
              + " left join part.contactPreference cp "
              + " left join part.item it "
              + " WHERE (cp.useWhichContactAddress = 1 OR cp.useWhichContactAddress = 3)"
              + " AND  (it.itemId = :iid)"
              + " AND  (part.displayAlternativeAddress = = 1)"
              + " AND  (addr.addressType = 'alternative')";
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getAlternativeAdrress in ReadOnlyBean");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      hib = null;
    }

    return result;
  }

  public List getByChat() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT cp.contactByChat "
              + " FROM contactPreference cp "
              + " WHERE (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();
      tx.commit();
    } catch (Exception e) {
      System.out.println("Error in getByChat in ReadOnlyBean");
      e.printStackTrace();
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

}
