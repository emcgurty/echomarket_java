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

    
    public UsStates[] buildUsStates() {
        UsStates[] purposeArray = null;
        List purpose_list = null;
        purpose_list = purpose_list();
        int size_of_list = purpose_list.size();
        purposeArray = new UsStates[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            UsStates to_Array = (UsStates) purpose_list.get(i);
            purposeArray[i] = new UsStates(to_Array.getId(), to_Array.getStateName());
        }
        return purposeArray;
    }

    private List purpose_list() {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();

        try {
            result = session.createQuery("from UsStates ORDER BY id").list();
        } catch (Exception e) {
            System.out.println("Error at line 52 in US Bean");
            e.printStackTrace();

        }
        tx.commit();

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
