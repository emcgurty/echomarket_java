package echomarket.hibernate;
// Generated Oct 29, 2016 11:54:05 AM by Hibernate Tools 4.3.1



/**
 * Countries generated by hbm2java
 */
public class Countries  implements java.io.Serializable {


     private String countryId;
     private String countryName;

    public Countries() {
    }

	
    public Countries(String countryId) {
        this.countryId = countryId;
    }
    public Countries(String countryId, String countryName) {
       this.countryId = countryId;
       this.countryName = countryName;
    }
   
    public String getCountryId() {
        return this.countryId;
    }
    
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    public String getCountryName() {
        return this.countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }




}


