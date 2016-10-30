package echomarket.hibernate;
// Generated Oct 29, 2016 11:54:05 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Roles generated by hbm2java
 */
public class Roles  implements java.io.Serializable {


     private Integer id;
     private String roleName;
     private Date createdAt;
     private Date updatedAt;

    public Roles() {
    }

	
    public Roles(Date createdAt, Date updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Roles(String roleName, Date createdAt, Date updatedAt) {
       this.roleName = roleName;
       this.createdAt = createdAt;
       this.updatedAt = updatedAt;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRoleName() {
        return this.roleName;
    }
    
    public void setRoleName(String roleName) {
        this.roleName = roleName;
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


