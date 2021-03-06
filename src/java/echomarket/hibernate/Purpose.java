package echomarket.hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="purpose")
public class Purpose  implements java.io.Serializable {

     @Id 
     private String id;
     private String purposeType;
     private Integer purposeOrder;
     private String purposeShort;

    public Purpose() {
    }

    public Purpose(String id, String purposeType) {
        this.id = id;
        this.purposeType = purposeType;
        
    }
	
    public Purpose(String id, String purposeType, String purposeShort) {
        this.id = id;
        this.purposeType = purposeType;
        this.purposeShort = purposeShort;
    }
    public Purpose(String purposeType, Integer purposeOrder, String purposeShort) {
       this.purposeType = purposeType;
       this.purposeOrder = purposeOrder;
       this.purposeShort = purposeShort;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getPurposeType() {
        return this.purposeType;
    }
    
    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }
    public Integer getPurposeOrder() {
        return this.purposeOrder;
    }
    
    public void setPurposeOrder(Integer purposeOrder) {
        this.purposeOrder = purposeOrder;
    }
    public String getPurposeShort() {
        return this.purposeShort;
    }
    
    public void setPurposeShort(String purposeShort) {
        this.purposeShort = purposeShort;
    }


}