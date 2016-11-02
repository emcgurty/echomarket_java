package echomarket.web.managedbeans;

import echomarket.hibernate.Countries;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean(name = "country")
@RequestScoped
public class CountriesBean extends AbstractBean implements Serializable {
    
    private String countryId;
    private String countryName;

    public CountriesBean() {
    }

 public String getOneCountry(String one_country) {

        List result = null;
        Session session = hib_session();
        String returnCountry = null;

        try {
            result = session.createQuery("from Countries WHERE counry_id = :one_country")
                    .setParameter("one_country", one_country)
                    .list();
        } catch (Exception e) {
            System.out.println("Error at line 28 in Country Bean");
            e.printStackTrace();

        }
        if (result.size() > 0) {
            Countries returnedCountryName = (Countries) result.get(0);
            returnCountry = returnedCountryName.getCountryName();
            returnedCountryName= null;
        } else {
            returnCountry = "Country Not Found";
        }
        
        session = null;
        result = null;
        return returnCountry;
    }    
 
    public Countries[] buildCountries() {
        Countries[] purposeArray = null;
        List country_list = null;
        country_list = country_list();
        int size_of_list = country_list.size();
        purposeArray = new Countries[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            Countries to_Array = (Countries) country_list.get(i);
            purposeArray[i] = new Countries(to_Array.getCountryId(), to_Array.getCountryName());
        }
        return purposeArray;
    }

    private List country_list() {

        List result = null;
        Session session = hib_session();
        
        try {
            result = session.createQuery("from Countries ORDER BY id").list();
        } catch (Exception e) {
            System.out.println("Error at line 42 in Country Bean");
            e.printStackTrace();
        }
        
        session = null;
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
