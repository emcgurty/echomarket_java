package echomarket.web.managedbeans;

import echomarket.hibernate.ContactPreference;
import echomarket.hibernate.ItemConditions;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "itemc")
@SessionScoped
public class ItemConditionBean extends AbstractBean implements Serializable {

//    private Integer id;
  private String condition;

  public ItemConditionBean() {
  }

  public String getItemConditionName(Integer cid) {

    String returnString = null;
    List result = null;
    Session session = null;
    Transaction tx = null;

    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("from ItemConditions WHERE id = :cid")
              .setParameter("cid", cid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in getItemConditionName");
      Logger.getLogger(ItemConditionBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      session = null;
      tx = null;
    }
    if (result != null) {
      if (result.size() == 1) {
        ItemConditions retCat = (ItemConditions) result.get((0));
        returnString = retCat.getCondition();
        retCat = null;
      }
    } else {
      returnString = "Not found";
    }

    result = null;
    return returnString;
  }

  public ItemConditions[] buildItemConditionsArray() {
    ItemConditions[] catArray = null;
    List cat_list = null;
    cat_list = cat_list();
    int size_of_list = cat_list.size();
    catArray = new ItemConditions[size_of_list];
    for (int i = 0; i < size_of_list; i++) {
      ItemConditions cArray = (ItemConditions) cat_list.get(i);
      catArray[i] = new ItemConditions(cArray.getId(), cArray.getCondition());
    }
    return catArray;
  }

  private List cat_list() {

    List result = null;
    Session session = null;
    Transaction tx = null;

    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("from ItemConditions order by id").list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in cat_list");
        Logger.getLogger(ItemConditionBean.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      session = null;
      tx = null;
    }

    return result;
  }

  /**
   * @return the condition
   */
  public String getCondition() {
    return condition;
  }

  /**
   * @param condition the condition to set
   */
  public void setCondition(String condition) {
    this.condition = condition;
  }

}
