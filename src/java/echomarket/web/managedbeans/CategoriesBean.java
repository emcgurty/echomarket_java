package echomarket.web.managedbeans;

import echomarket.hibernate.Categories;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("cats")
@RequestScoped
public class CategoriesBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 2L;
  private Integer id;
  private String categoryType;

  public CategoriesBean() {
  }

  public String getCategoryType() {
    return categoryType;
  }

  /**
   * @param categoryType the categoryType to set
   */
  public void setCategoryType(String categoryType) {
    this.categoryType = categoryType;
  }

  public String getCategoryName(Integer cid) {
    String returnString = null;
    List result = null;
    Session session;
    Transaction tx;
    session = null;
    tx = null;

    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("from Categories WHERE id = :cid")
              .setParameter("cid", cid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in getCategoryName");
      Logger.getLogger(CategoriesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      session = null;
      tx = null;
    }
    if (result != null) {
      if (result.size() == 1) {
        Categories retCat = (Categories) result.get((0));
        returnString = retCat.getCategoryType();
      }
    } else {
      returnString = "Not found";
    }

    result = null;
    return returnString;
  }

  public Categories[] buildCatArray() {
    Categories[] catArray = null;
    List cat_list = null;
    cat_list = cat_list();
    if (cat_list != null) {
      int size_of_list = cat_list.size();
      catArray = new Categories[size_of_list];
      for (int i = 0; i < size_of_list; i++) {
        Categories cArray = (Categories) cat_list.get(i);
        catArray[i] = new Categories(cArray.getId(), cArray.getCategoryType());
      }
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
      result = session.createQuery("from Categories Order By id").list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in cat_list");
      Logger.getLogger(CategoriesBean.class.getName()).log(Level.SEVERE, null, e);

    } finally {
      session = null;
      tx = null;
    }
    return result;
  }

}
