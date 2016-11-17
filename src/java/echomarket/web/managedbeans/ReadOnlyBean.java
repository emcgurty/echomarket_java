/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import echomarket.hibernate.Users;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "readOnly")
@RequestScoped
public class ReadOnlyBean extends AbstractBean implements Serializable {

  private String participant_id;
  private String itemId;

  public String load_RO(String iid) {
    this.setItemId(iid);
    return "read_only";
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
              + " Participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();

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
  
  public List getByPhone() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT cp.contactByHomePhone, cp.contactByCellPhone, cp.contactByAlternativePhone, "
              + " part.homePhone, part.cellPhone, part.alternativePhone, "
              + " part.DisplayHomePhone, part.displayCellPhone, part.displayAlternativePhone, "
              + " it.itemType "
              + " from Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();

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

  public List getByEmail() {

    List result = null;
    Session hib = hib_session();
    Transaction tx = null;
    String queryString = null;

    try {
      tx = hib.beginTransaction();
      queryString = "SELECT part.emailAlternative altemail, us.email lemail, cp.contactByEmail  "
              + " from Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE (cp.contactByEmail = 1)"
              + " AND  (it.itemId = :iid)";

      result = hib.createQuery(queryString)
              .setParameter("iid", this.getItemId())
              .list();

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
              + " from Participant part "
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
              + " from Participant part "
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

}
