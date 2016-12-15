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
              + " left join part.item it "
              + " WHERE (cp.useWhichContactAddress = 1 OR cp.useWhichContactAddress = 3)"
              + " AND  (it.itemId like 'a%')";

      query = "SELECT part.emailAlternative, us.email, cp.contactByEmail  "
              + " from Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it "
              + " left join part.contactPreference cp "
              + " WHERE (cp.contactByEmail = 1)";

      query = "SELECT it.otherItemCategory, it.itemModel, it.itemDescription, it.itemType "
              + " FROM Users us, Participant part "
              + " left join us.participant part "
              + " left join part.item it ";

      query = "SELECT ltr FROM Users usr "
              + " left join usr.participant part "
              + " left join part.item itm "
              + " left join itm.lenderTransfer ltr ";
      query = "from LenderTransfer ltr "
              + "WHERE ltr.participant_id = :pid"
              + " AND ltr.itemId = :iid ";
      // 3b0eb3bb-a767-436e-9add-108804130808

      query = "SELECT u "
              + " FROM Users u "
              + " left join u.participant part "
              + " WHERE u.user_id = '75ec1225-50e8-4741-83ce-acc974b64883' AND part.goodwill = 1 AND part.age18OrMore = 1 AND u.activatedAt = null ";
//              + " WHERE  (part.participant_id = :pid)";
      
//       query = "SELECT addr "
//              + " FROM Participant part "
//              + " inner join part.addresses addr "
//              + " inner join part.item itm "
//              + " inner join itm.lenderTransfer ltrans "
//              + " WHERE (ltrans.borrowerComesToWhichAddress = 1 OR ltrans.borrowerComesToWhichAddress = 3)"
//              + " AND  (ltrans.itemId = '6991f7fb-0fe1-4635-9512-2b5a5155e5b2')"
//              + " AND  (part.displayAddress = 1)"
//              + " AND  (addr.addressType = 'primary')";

//            SELECT users.user_id
//FROM ((users INNER JOIN participant ON users.user_id = participant.user_id) 
//INNER JOIN addresses ON participant.participant_id = addresses.participant_id) 
//INNER JOIN contact_preference ON participant.participant_id = contact_preference.participant_id;
//      
//      This worked
//      Object obj_part = session.get(Participant.class, "c8b59121-7007-425a-ae3d-860598cb213b");
//      Participant part = (Participant) obj_part;
//      System.out.println(part.getCellPhone());
//      Set part_address = part.getAddresses();
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
