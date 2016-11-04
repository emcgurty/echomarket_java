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
    private Boolean us_built;

    public UsStatesBean() {
    }

    public String getOneState(String one_state) {

        List result = null;
        Session session;
        Transaction tx;
        session = null;
        tx = null;

        try {
            session = hib_session();
            tx = session.beginTransaction();
        } catch (Exception ex) {
            // This error is always called and I do not know why..
            message(
                    null,
                    "ApplicationError",
                    new Object[]{ex});
            return "index";
        }

        String returnState = null;

        try {
            result = session.createQuery("from UsStates WHERE id = :one_state")
                    .setParameter("one_state", one_state)
                    .list();

        } catch (Exception e) {
            System.out.println("Error at line 36 in US Bean");
            e.printStackTrace();

        } finally {
            tx = null;
            session = null;
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
        Transaction tx;
        tx = null;

        try {
            session = hib_session();
            tx = session.beginTransaction();
        } catch (Exception ex) {
            System.out.println("Error at line 100 in US Bean");
            ex.printStackTrace();
        }

        try {
            result = session.createQuery("FROM UsStates ORDER BY id").list();
        } catch (Exception e) {
            System.out.println("Error at line 52 in US Bean");
            e.printStackTrace();

        }
        session = null;
        tx = null;

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
