package echomarket.hibernate;
// Generated Oct 29, 2016 11:54:05 AM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * ContactUs generated by hbm2java
 */
public class ContactUs  implements java.io.Serializable {


     private String contactUsId;
     private String subject;
     private String remoteIp;
     private String email;
     private String comments;
     private Date dateCreated;
     private String userId;

    public ContactUs() {
    }

	
    public ContactUs(String contactUsId, String subject, String email, String comments, Date dateCreated) {
        this.contactUsId = contactUsId;
        this.subject = subject;
        this.email = email;
        this.comments = comments;
        this.dateCreated = dateCreated;
    }
    public ContactUs(String contactUsId, String subject, String remoteIp, String email, String comments, Date dateCreated, String userId) {
       this.contactUsId = contactUsId;
       this.subject = subject;
       this.remoteIp = remoteIp;
       this.email = email;
       this.comments = comments;
       this.dateCreated = dateCreated;
       this.userId = userId;
    }
   
    public String getContactUsId() {
        return this.contactUsId;
    }
    
    public void setContactUsId(String contactUsId) {
        this.contactUsId = contactUsId;
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


