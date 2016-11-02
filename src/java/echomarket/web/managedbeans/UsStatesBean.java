package echomarket.web.managedbeans;

import echomarket.hibernate.UsStates;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author emm
 */
@ManagedBean(name = "uss")
@RequestScoped
public class UsStatesBean extends AbstractBean implements Serializable {

    /**
     * Creates a new instance of PurposeBean
     */
    private String id;
    private String stateName;

    public UsStatesBean() {
    }

    public String getOneState(String one_state) {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String returnState = null;

        try {
            result = session.createQuery("from UsStates WHERE id = :one_state")
                    .setParameter("one_state", one_state)
                    .list();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error at line 36 in US Bean");
            e.printStackTrace();

        }
        if (result.size() > 0) {
            UsStates returnedStateName = (UsStates) result.get(0);
            returnState = returnedStateName.getStateName();
            returnedStateName= null;
        } else {
            returnState = "State not found";
        }
        
        tx = null;
        session = null;
        result = null;
        return returnState;
    }

    public UsStates[] buildUsStates() {
    
        UsStates[] purposeArray = null;
        List us_list = null;
        us_list = us_list();
        int size_of_list = us_list.size();
        purposeArray = new UsStates[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            UsStates to_Array = (UsStates) us_list.get(i);
            purposeArray[i] = new UsStates(to_Array.getId(), to_Array.getStateName());
        }
        us_list = null;
        return purposeArray;
    }

    private List us_list() {

        List result = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = hib_session();
            tx = session.beginTransaction();
        } catch(Exception ex){}

        try {
            result = session.createQuery("from UsStates ORDER BY id").list();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error at line 52 in US Bean");
            e.printStackTrace();

        }
        session = null;
        tx = null;
        session = null;

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

}
