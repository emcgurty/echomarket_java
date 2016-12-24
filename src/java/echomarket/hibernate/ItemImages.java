package echomarket.hibernate;

import java.util.Date;

public class ItemImages  implements java.io.Serializable {

     private String itemImageId;
     private String itemId;
     private String imageContentType;
     private Integer imageHeight;
     private Integer imageWidth;
     private String imageFileName;
     private String itemImageCaption;
     private Date dateCreated;
     private Date dateDeleted;
     private Date dateUpdated;

    public ItemImages() {
    }

    public ItemImages(String itemImageId, String itemId, String imageFileName) {
        this.itemImageId = itemImageId;
        this.itemId = itemId;
        this.imageFileName = imageFileName;
        this.dateCreated = dateCreated;
        this.dateDeleted = dateDeleted;
        this.dateUpdated = dateUpdated;
    }
    public ItemImages(String itemImageId, String itemId, String imageContentType, Integer imageHeight, Integer imageWidth, String imageFileName, String itemImageCaption) {
       this.itemImageId = itemImageId;
       this.itemId = itemId;
       this.imageContentType = imageContentType;
       this.imageHeight = imageHeight;
       this.imageWidth = imageWidth;
       this.imageFileName = imageFileName;
       this.itemImageCaption = itemImageCaption;
       
    }
   
    public String getItemImageId() {
        return this.itemImageId;
    }
    
    public void setItemImageId(String itemImageId) {
        this.itemImageId = itemImageId;
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

    /**
     * @return the item_id
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @param item_id the item_id to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

   



}


