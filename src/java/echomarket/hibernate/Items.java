package echomarket.hibernate;

import java.util.Date;

public class Items  implements java.io.Serializable {


     private String itemId;
     private String participantId;
     private Integer categoryId;
     private String otherItemCategory;
     private String itemModel;
     private String itemDescription;
     private Integer itemConditionId;
     private Integer itemCount;
     private String comment;
     private Date dateCreated;
     private Date dateUpdated;
     private Date dateDeleted;
     private int approved;
     private int notify;
     private String itemType;

     public Items() {
    }

	
    public Items(String itemId, Date dateCreated, Date dateUpdated, Date dateDeleted, int approved, int notify) {
        this.itemId = itemId;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.dateDeleted = dateDeleted;
        this.approved = approved;
        this.notify = notify;
    }
    public Items(String itemId, String participantId, Integer categoryId, String otherItemCategory, String itemModel, String itemDescription, Integer itemConditionId, Integer itemCount, String comment, Date dateCreated, Date dateUpdated, Date dateDeleted, int approved, int notify, String itemType) {
       this.itemId = itemId;
       this.participantId = participantId;
       this.categoryId = categoryId;
       this.otherItemCategory = otherItemCategory;
       this.itemModel = itemModel;
       this.itemDescription = itemDescription;
       this.itemConditionId = itemConditionId;
       this.itemCount = itemCount;
       this.comment = comment;
       this.dateCreated = dateCreated;
       this.dateUpdated = dateUpdated;
       this.dateDeleted = dateDeleted;
       this.approved = approved;
       this.notify = notify;
       this.itemType = itemType;
    }
   
    public String getItemId() {
        return this.itemId;
    }
    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getParticipantId() {
        return this.participantId;
    }
    
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }
    public Integer getCategoryId() {
        return this.categoryId;
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getOtherItemCategory() {
        return this.otherItemCategory;
    }
    
    public void setOtherItemCategory(String otherItemCategory) {
        this.otherItemCategory = otherItemCategory;
    }
    public String getItemModel() {
        return this.itemModel;
    }
    
    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }
    public String getItemDescription() {
        return this.itemDescription;
    }
    
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public Integer getItemConditionId() {
        return this.itemConditionId;
    }
    
    public void setItemConditionId(Integer itemConditionId) {
        this.itemConditionId = itemConditionId;
    }
    public Integer getItemCount() {
        return this.itemCount;
    }
    
    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
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
    public int getApproved() {
        return this.approved;
    }
    
    public void setApproved(int approved) {
        this.approved = approved;
    }
    public int getNotify() {
        return this.notify;
    }
    
    public void setNotify(int notify) {
        this.notify = notify;
    }

    /**
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * @param itemType the itemType to set
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

}


