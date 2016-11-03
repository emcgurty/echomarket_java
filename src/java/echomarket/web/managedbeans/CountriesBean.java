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

        String returnCountry = null;
        List result = null;
        Session session = null;
        Transaction tx = null;

        try {
            session = hib_session();
            tx = session.beginTransaction();
        } catch (Exception ex) {
            message(
                    null,
                    "ApplicationError",
                    new Object[]{ex});
            return "index";
        }
        try {
            result = session.createQuery("from Countries WHERE country_id = :one_country")
                    .setParameter("one_country", one_country)
                    .list();
        } catch (Exception e) {
            System.out.println("Error at line 28 in Country Bean");
            e.printStackTrace();

        }
        if (result.size() > 0) {
            Countries returnedCountryName = (Countries) result.get(0);
            returnCountry = returnedCountryName.getCountryName();
            returnedCountryName = null;
        } else {
            returnCountry = "Country Not Found";
        }

        session = null;
        result = null;
        return returnCountry;
    }

    public Countries[] buildCountries() {
        Countries[] purposeArray = null;
        List countrylist = null;
        countrylist = country_list();
        int size_of_list = countrylist.size();
        purposeArray = new Countries[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            Countries to_Array = (Countries) countrylist.get(i);
            purposeArray[i] = new Countries(to_Array.getCountryId(), to_Array.getCountryName());
        }
        countrylist = null;
        return purposeArray;
    }

    private List country_list() {

        List result = null;
        Session session = null;
        Transaction tx =  null;
        
        try {
            session = hib_session();
            tx = session.beginTransaction();
        } catch(Exception ex){
             message(
                    null,
                    "ApplicationError",
                    new Object[] {ex}); 
             
        }

        try {
            result = session.createQuery("from Countries ORDER BY country_id").list();
        } catch (Exception e) {
            System.out.println("Error at line 68 in Country Bean");
            e.printStackTrace();
        }

        session = null;
        tx = null;
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
