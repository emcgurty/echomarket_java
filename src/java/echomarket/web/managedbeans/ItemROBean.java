package echomarket.web.managedbeans;

import echomarket.hibernate.ItemImages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@RequestScoped
public class ItemROBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private ArrayList<ItemImages> picture
          = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)
          ));

  // emm 1.8
  public ItemROBean() {

  }

  public ArrayList<ItemImages> getPicture() {
    return picture;

  }

  public void setPicture(ArrayList<ItemImages> aPicture) {
    picture = aPicture;
  }

  public List getCurrentItemImage(String iid) {
    return getExistingPicture(iid);

  }

  private List getExistingPicture(String iid) {

    List result = null;
    Session hib = hib_session();
    Transaction tx = hib.beginTransaction();
    String[] results = null;
    if (iid == null) {
      return null;
    } else {
      String queryString = "from ItemImages where item_id = :iid ";
      try {
        result = hib.createQuery(queryString)
                .setParameter("iid", iid)
                .list();
        tx.commit();
      } catch (Exception e) {
        tx.rollback();
        System.out.println("Error in getExistingPicture");
        e.printStackTrace();
      } finally {
        tx = null;
        hib = null;
      }

      Integer size_of_list = result.size();
      if (size_of_list == 0) {
        return null;
      } else {
        ItemImages a_array = (ItemImages) result.get(0);
        ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(
                new ItemImages(a_array.getItemImageId(), a_array.getItemId(), a_array.getImageContentType(),
                        a_array.getImageHeight(), a_array.getImageWidth(), a_array.getImageFileName(), a_array.getItemImageCaption())
        ));
        setPicture(tmp_picture);

        a_array = null;
        tmp_picture = null;
        return getPicture();
      }
    }
  }

  public List getCurrentItem(String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = "FROM Items WHERE item_id = '" + iid + "'";
      result = session.createQuery(query).list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentItem");
      e.printStackTrace();

    } finally {
      tx = null;
      session = null;

    }

    return result;
  }

  public List getAllSoughtItems(String which) {
    System.out.println("getAllSoughtItems Called");
    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {   // Don't display community items
      session = hib_session();
      tx = session.beginTransaction();
      query = "  SELECT itm FROM Participant part "
              + " left join part.item itm "
              + " WHERE itm.itemType = :it AND part.communityId = null ORDER BY itm.dateCreated";
      result = session.createQuery(query)
              .setParameter("it", which)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getAllSoughtItems");
      e.printStackTrace();

    } finally {
      tx = null;
      session = null;

    }

    return result;
  }

}
