package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Advertisers generated by hbm2java
 */
public class Advertisers  implements java.io.Serializable {


     private String id;
     private String title;
     private int categoryId;
     private String categoryOther;
     private String description;
     private String advertiserUrl;
     private String advertiserEmail;
     private int approved;
     private Date dateCreated;
     private Date dateUpdated;
     private Date dateDeleted;
     private String isActive;
     private Integer isActivated;
     private String remoteIp;

    public Advertisers() {
    }

	
    public Advertisers(String id, String title, int categoryId, String advertiserUrl, String advertiserEmail, int approved, Date dateCreated, String isActive) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.advertiserUrl = advertiserUrl;
        this.advertiserEmail = advertiserEmail;
        this.approved = approved;
        this.dateCreated = dateCreated;
        this.isActive = isActive;
    }
    public Advertisers(String id, String title, int categoryId, String categoryOther, String description, String advertiserUrl, String advertiserEmail, int approved, Date dateCreated, Date dateUpdated, Date dateDeleted, String isActive, Integer isActivated, String remoteIp) {
       this.id = id;
       this.title = title;
       this.categoryId = categoryId;
       this.categoryOther = categoryOther;
       this.description = description;
       this.advertiserUrl = advertiserUrl;
       this.advertiserEmail = advertiserEmail;
       this.approved = approved;
       this.dateCreated = dateCreated;
       this.dateUpdated = dateUpdated;
       this.dateDeleted = dateDeleted;
       this.isActive = isActive;
       this.isActivated = isActivated;
       this.remoteIp = remoteIp;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public int getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryOther() {
        return this.categoryOther;
    }
    
    public void setCategoryOther(String categoryOther) {
        this.categoryOther = categoryOther;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAdvertiserUrl() {
        return this.advertiserUrl;
    }
    
    public void setAdvertiserUrl(String advertiserUrl) {
        this.advertiserUrl = advertiserUrl;
    }
    public String getAdvertiserEmail() {
        return this.advertiserEmail;
    }
    
    public void setAdvertiserEmail(String advertiserEmail) {
        this.advertiserEmail = advertiserEmail;
    }
    public int getApproved() {
        return this.approved;
    }
    
    public void setApproved(int approved) {
        this.approved = approved;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public Date getDateUpdated() {
        return this.dateUpdated;
    }
    
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
    public Date getDateDeleted() {
        return this.dateDeleted;
    }
    
    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }
    public String getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    public Integer getIsActivated() {
        return this.isActivated;
    }
    
    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }
    public String getRemoteIp() {
        return this.remoteIp;
    }
    
    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }




}


