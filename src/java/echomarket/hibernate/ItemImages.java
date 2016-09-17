package echomarket.hibernate;
// Generated Aug 5, 2016 12:14:14 PM by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ItemImages generated by hbm2java
 */
@Entity
@Table(name = "item_images")
public class ItemImages implements java.io.Serializable {

    private String id;
    private Borrowers borrowers;
    private String borrower_id;
    private String lender_id;
    private String imageContentType;
    private Integer imageHeight;
    private Integer imageWidth;
    private Integer isActive;
    private Date dateCreated;
    private Date dateDeleted;
    private Date dateUpdated;
    private String imageFileName;
    private String itemImageCaption;
    private String advertiserId;

    public ItemImages() {
    }

    //new ItemImages(tmp_id);
    public ItemImages(String id) {
        this.id = id;
    }

    public ItemImages(String id, Date dateCreated, Date dateDeleted, Date dateUpdated, String imageFileName) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateDeleted = dateDeleted;
        this.dateUpdated = dateUpdated;
        this.imageFileName = imageFileName;
    }

    public ItemImages(String id, String borrower_id, String lender_id, String imageContentType, Integer imageHeight, Integer imageWidth, Integer isActive, Date dateCreated, Date dateDeleted, Date dateUpdated, String imageFileName, String itemImageCaption, String advertiserId) {
        this.id = id;
        //this.borrower_id = borrower_id;
        this.lender_id = lender_id;
        this.imageContentType = imageContentType;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
        this.isActive = isActive;
        this.dateCreated = dateCreated;
        this.dateDeleted = dateDeleted;
        this.dateUpdated = dateUpdated;
        this.imageFileName = imageFileName;
        this.itemImageCaption = itemImageCaption;
        this.advertiserId = advertiserId;
    }

//    @OneToMany(mappedBy = "item_images", cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
    @Id
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getLender_id() {
        return this.lender_id;
    }

    public void setLender_id(String lender_id) {
        this.lender_id = lender_id;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Integer getImageHeight() {
        return this.imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth() {
        return this.imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
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

    public Date getDateDeleted() {
        return this.dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Date getDateUpdated() {
        return this.dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getItemImageCaption() {
        return this.itemImageCaption;
    }

    public void setItemImageCaption(String itemImageCaption) {
        this.itemImageCaption = itemImageCaption;
    }

    public String getAdvertiserId() {
        return this.advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    
    public String getBorrower_id() {
        return borrower_id;
    }

    /**
     * @param borrower_id the borrower_id to set
     */
    public void setBorrower_id(String borrower_id) {
        this.borrower_id = borrower_id;
    }

    /**
     * @return the borrowers
     */
    public Borrowers getBorrowers() {
        return borrowers;
    }

    /**
     * @param borrowers the borrowers to set
     */
    public void setBorrowers(Borrowers borrowers) {
        this.borrowers = borrowers;
    }

    

}
