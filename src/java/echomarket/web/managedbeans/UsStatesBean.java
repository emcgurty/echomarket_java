package echomarket.web.managedbeans;

import echomarket.hibernate.UsStates;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named("uss")
@RequestScoped
public class UsStatesBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private String stateName;
  private Boolean us_built;

  public UsStatesBean() {
  }

  public String getOneState(String one_state) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String returnState = null;

    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("from UsStates WHERE id = :one_state")
              .setParameter("one_state", one_state)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error at line 36 in US Bean");
      Logger.getLogger(UsStatesBean.class.getName()).log(Level.SEVERE, null, e);

    } finally {
      session = null;
      tx = null;

    }

    if (result.size() > 0) {
      UsStates returnedStateName = (UsStates) result.get(0);
      returnState = returnedStateName.getStateName();
      returnedStateName = null;
    } else {
      returnState = "State not found";
    }

    result = null;
    return returnState;
  }

  public UsStates[] buildUsStates() {

    UsStates[] purposeArray = null;
    List uslist = null;
    uslist = us_list();
    int size_of_list = uslist.size();
    purposeArray = new UsStates[size_of_list];
    for (int i = 0; i < size_of_list; i++) {
      UsStates to_Array = (UsStates) uslist.get(i);
      purposeArray[i] = new UsStates(to_Array.getId(), to_Array.getStateName());
    }

    uslist = null;
    return purposeArray;
  }

  private List us_list() {

    System.out.println("US LIST CALL");
    List result = null;
    Session session = null;
    Transaction tx = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("FROM UsStates ORDER BY id").list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error at line 99 in US Bean");
      Logger.getLogger(UsStatesBean.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      tx = null;
      session = null;
    }

    return result;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the stateName
   */
  public String getStateName() {
    return stateName;
  }

  /**
   * @param stateName the stateName to set
   */
  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  /**
   * @return the us_built
   */
  public Boolean getUs_built() {
    return us_built;
  }

  /**
   * @param us_built the us_built to set
   */
  public void setUs_built(Boolean us_built) {
    this.us_built = us_built;
  }

}
