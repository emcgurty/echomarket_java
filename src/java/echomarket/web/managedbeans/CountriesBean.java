package echomarket.web.managedbeans;


import echomarket.hibernate.Countries;
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
@ManagedBean(name = "country")
@RequestScoped
public class CountriesBean extends AbstractBean implements Serializable {

    /**
     * Creates a new instance of PurposeBean
     */
    
    private String countryId;
    private String countryName;


    public CountriesBean() {
    }

    
    public Countries[] buildCountries() {
        Countries[] purposeArray = null;
        List purpose_list = null;
        purpose_list = purpose_list();
        int size_of_list = purpose_list.size();
        purposeArray = new Countries[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            Countries to_Array = (Countries) purpose_list.get(i);
            purposeArray[i] = new Countries(to_Array.getCountryId(), to_Array.getCountryName());
        }
        return purposeArray;
    }

    private List purpose_list() {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();

        try {
            result = session.createQuery("from Countries ORDER BY id").list();
        } catch (Exception e) {
            System.out.println("Error at line 52 in Country Bean");
            e.printStackTrace();

        }
        tx.commit();

        return result;
    }

    /**
     * @return the id
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param id the id to set
     */
    public void setCountryId(String id) {
        this.countryId = id;
    }

    /**
     * @return the stateName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setCountryName(String cName) {
        this.countryName = cName;
    }

}
