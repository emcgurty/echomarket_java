package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * CommunityMembers generated by hbm2java
 */
public class CommunityMembers  implements java.io.Serializable {


     private String id;
     private String communityId;
     private String remoteIp;
     private String firstName;
     private String mi;
     private String lastName;
     private String alias;
     private Integer isActive;
     private Date dateCreated;
     private Date dateUpdated;
     private Date dateDeleted;
     private Integer isCreator;

    public CommunityMembers() {
    }

	
    public CommunityMembers(String id, String communityId, Date dateCreated, Date dateUpdated, Date dateDeleted) {
        this.id = id;
        this.communityId = communityId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
    }
    public CommunityMembers(String id, String communityId, String remoteIp, String firstName, String mi, String lastName, String alias, Integer isActive, Date dateCreated, Date dateUpdated, Date dateDeleted, Integer isCreator) {
       this.id = id;
       this.communityId = communityId;
       this.remoteIp = remoteIp;
       this.firstName = firstName;
       this.mi = mi;
       this.lastName = lastName;
       this.alias = alias;
       this.isActive = isActive;
       this.dateCreated = dateCreated;
       this.dateUpdated = dateUpdated;
       this.dateDeleted = dateDeleted;
       this.isCreator = isCreator;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getCommunityId() {
        return this.communityId;
    }
    
    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
    public String getRemoteIp() {
        return this.remoteIp;
    }
    
    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMi() {
        return this.mi;
    }
    
    public void setMi(String mi) {
        this.mi = mi;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAlias() {
        return this.alias;
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public Integer getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
    public Integer getIsCreator() {
        return this.isCreator;
    }
    
    public void setIsCreator(Integer isCreator) {
        this.isCreator = isCreator;
    }




}


