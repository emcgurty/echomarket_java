package echomarket.hibernate;
// Generated Oct 29, 2016 11:54:05 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Sessions generated by hbm2java
 */
public class Sessions  implements java.io.Serializable {


     private Integer id;
     private String sessionId;
     private String data;
     private Date createdAt;
     private Date updatedAt;

    public Sessions() {
    }

	
    public Sessions(String sessionId) {
        this.sessionId = sessionId;
    }
    public Sessions(String sessionId, String data, Date createdAt, Date updatedAt) {
       this.sessionId = sessionId;
       this.data = data;
       this.createdAt = createdAt;
       this.updatedAt = updatedAt;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getSessionId() {
        return this.sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getData() {
        return this.data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    public Date getCreatedAt() {
        return this.createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }




}


