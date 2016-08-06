package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Contacts generated by hbm2java
 */
public class Contacts  implements java.io.Serializable {


     private String contactId;
     private String subject;
     private String remoteIp;
     private String email;
     private String comments;
     private Date dateCreated;
     private String userId;

    public Contacts() {
    }

	
    public Contacts(String contactId, String subject, String email, String comments, Date dateCreated) {
        this.contactId = contactId;
        this.subject = subject;
        this.email = email;
        this.comments = comments;
        this.dateCreated = dateCreated;
    }
    public Contacts(String contactId, String subject, String remoteIp, String email, String comments, Date dateCreated, String userId) {
       this.contactId = contactId;
       this.subject = subject;
       this.remoteIp = remoteIp;
       this.email = email;
       this.comments = comments;
       this.dateCreated = dateCreated;
       this.userId = userId;
    }
   
    public String getContactId() {
        return this.contactId;
    }
    
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getRemoteIp() {
        return this.remoteIp;
    }
    
    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }




}


