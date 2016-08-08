package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




/**
 * Addresses generated by hbm2java
 */
@Entity
@Table(name="addresses")
public class Addresses  implements java.io.Serializable {

     @Id
     private String id;
     private String lenderId;
     private String borrowerId;
     private String addressLine1;
     private String addressLine2;
     private String postalCode;
     private String city;
     private String province;
     private String usStateId;
     private String region;
     private String countryId;
     private String addressType;

    public Addresses() {
    }

	
    public Addresses(String id, String addressType) {
        this.id = id;
        this.addressType = addressType;
    }
    public Addresses(String id, String lenderId, String borrowerId, String addressLine1, String addressLine2, String postalCode, String city, String province, String usStateId, String region, String countryId, String addressType) {
       this.id = id;
       this.lenderId = lenderId;
       this.borrowerId = borrowerId;
       this.addressLine1 = addressLine1;
       this.addressLine2 = addressLine2;
       this.postalCode = postalCode;
       this.city = city;
       this.province = province;
       this.usStateId = usStateId;
       this.region = region;
       this.countryId = countryId;
       this.addressType = addressType;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getLenderId() {
        return this.lenderId;
    }
    
    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }
    public String getBorrowerId() {
        return this.borrowerId;
    }
    
    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }
    public String getAddressLine1() {
        return this.addressLine1;
    }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return this.addressLine2;
    }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getPostalCode() {
        return this.postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return this.province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    public String getUsStateId() {
        return this.usStateId;
    }
    
    public void setUsStateId(String usStateId) {
        this.usStateId = usStateId;
    }
    public String getRegion() {
        return this.region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCountryId() {
        return this.countryId;
    }
    
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
    public String getAddressType() {
        return this.addressType;
    }
    
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }




}


