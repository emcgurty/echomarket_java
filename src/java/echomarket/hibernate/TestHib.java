package echomarket.hibernate;

import echomarket.hibernate.HibernateUtil;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestHib {

  public static void main(String[] args) {
    Session session = null;
    try {
      session = HibernateUtil.getSessionFactory().getCurrentSession();
    } catch (Exception ex) {
      System.out.println("Error in TestHib");
      ex.printStackTrace();
      session = null;
      return;
    }

    List result = null;

    Transaction tx = session.beginTransaction();
    String query = null;
    try {

      query = "SELECT addr.addressLine1, addr.addressLine2, addr.postalCode, "
              + " addr.city, addr.province, addr.usStateId, addr.region, addr.countryId, addr.addressType "
              + " from Participant part "
              + " left join part.addresses addr"
              + " left join part.contactPreference cp "
              + " WHERE (cp.useWhichContactAddress = 1 OR cp.useWhichContactAddress = 3)";
//      
//      This worked
//      Object obj_part = session.get(Participant.class, "c8b59121-7007-425a-ae3d-860598cb213b");
//      Participant part = (Participant) obj_part;
//      System.out.println(part.getCellPhone());
//      Set part_address = part.getAddresses();
//       query = "select u from UserGroup ug inner join ug.user u";
//       query = "select addre from Participant part inner join part.Addresses addre";
      System.out.println(query);
      result = session.createQuery(query)
              .list();
      tx.commit();

    } catch (Exception e) {
      System.out.println("Error result/commit in TestHib");
      e.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      session = null;

    }

  }
}
