package echomarket.web.managedbeans;

import echomarket.hibernate.Purpose;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@RequestScoped
public class PurposeBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String purposeType;

  public PurposeBean() {
  }

  private String getPurposeType() {
    return purposeType;
  }

  private void setPurposeType(String purposeType) {
    this.purposeType = purposeType;
  }

  public Purpose[] buildPurposeArray() {
    System.out.println("Here at line 29");
    Purpose[] purposeArray = null;
    List purpose_list = null;
    purpose_list = purpose_list();
    int size_of_list = purpose_list.size();

    purposeArray = new Purpose[size_of_list];
    for (int i = 0; i < size_of_list; i++) {
      Purpose to_Array = (Purpose) purpose_list.get(i);
      purposeArray[i] = new Purpose(to_Array.getId(), to_Array.getPurposeType(), to_Array.getPurposeShort());
    }
    return purposeArray;
  }

  private List purpose_list() {
    System.out.println("Here at line 44");
    List result = null;
    Session session = null;
    Transaction tx = null;

    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("from Purpose ORDER BY purpose_order ").list();
      tx.commit();
      System.out.println("tx.commit worked");
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error at line 57 in PurposeBeans");
      ex.printStackTrace();

    } finally {
      session = null;
      tx = null;
    }
    System.out.println("result size");
    System.out.println(result.size());
    return result;
  }

}
