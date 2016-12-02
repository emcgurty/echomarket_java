package echomarket.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

public class Items implements java.io.Serializable {

  private String itemId;
  private String participant_id;
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
  private Integer approved;
  private Integer notify;
  private String itemType;
  private Set<LenderTransfer> lenderTransfer = new HashSet<LenderTransfer>();
  private Set<LenderItemConditions> lenderItemConditions = new HashSet<LenderItemConditions>();

  public Items() {
  }

  public Items(String itemId, Date dateCreated, Date dateUpdated, Date dateDeleted, Integer approved, Integer notify) {
    this.itemId = itemId;
    this.dateCreated = dateCreated;
    this.dateUpdated = dateUpdated;
    this.dateDeleted = dateDeleted;
    this.approved = approved;
    this.notify = notify;
  }

  public Items(String itemId, String participant_id, Integer categoryId, String otherItemCategory, String itemModel, String itemDescription, Integer itemConditionId, Integer itemCount, String comment, Date dateCreated, Date dateUpdated, Date dateDeleted, Integer approved, Integer notify, String itemType) {
    this.itemId = itemId;
    this.participant_id = participant_id;
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

  public String getParticipant_id() {
    return this.participant_id;
  }

  public void setParticipant_id(String participant_id) {
    this.participant_id = participant_id;
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

  public Integer getApproved() {
    return this.approved;
  }

  public void setApproved(Integer approved) {
    this.approved = approved;
  }

  public Integer getNotify() {
    return this.notify;
  }

  public void setNotify(Integer notify) {
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

  /**
   * @return the lenderTransfer
   */
  @OneToMany
  @JoinTable(name = "echomarket.hibernate.LenderTransfer")
  @JoinColumn(name = "itemId")
  public Set<LenderTransfer> getLenderTransfer() {
    return lenderTransfer;
  }

  /**
   * @param lenderTransfer the lenderTransfer to set
   */
  public void setLenderTransfer(Set<LenderTransfer> lenderTransfer) {
    this.lenderTransfer = lenderTransfer;
  }

  /**
   * @return the lenderItemConditions
   */
  @OneToMany
  @JoinTable(name = "echomarket.hibernate.LenderItemConditions")
  @JoinColumn(name = "itemId")
  public Set<LenderItemConditions> getLenderItemConditions() {
    return lenderItemConditions;
  }

  /**
   * @param lenderItemConditions the lenderItemConditions to set
   */
  public void setLenderItemConditions(Set<LenderItemConditions> lenderItemConditions) {
    this.lenderItemConditions = lenderItemConditions;
  }

}
