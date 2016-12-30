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
    query = "SELECT new echomarket.hibernate.ParticipantAddress(part, addr.addressType) "
              + " from Participant part "
              + " INNER JOIN part.addresses addr "
              + " WHERE addr.addressType = 'primary' AND part.participant_id = '603aec80-3e31-451d-9ada-bc5c9d75b569' GROUP BY part.participant_id, addr.addressType";
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
    /// typically check that result is not null, and in my case that result.size() == 1
    echomarket.hibernate.ParticipantAddress hold = (echomarket.hibernate.ParticipantAddress)result.get(0);
    Participant pp = (Participant) hold.getPart();  /// Got my Participant record
    System.out.println("wait");  /// put a break here so I could evaluate return on result, hold and pp
    
  }
}
