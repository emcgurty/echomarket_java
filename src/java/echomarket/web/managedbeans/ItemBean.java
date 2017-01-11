package echomarket.web.managedbeans;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import echomarket.SendEmail.SendEmail;
import echomarket.hibernate.ContactPreference;
import echomarket.hibernate.Items;
import echomarket.hibernate.ItemImages;
import echomarket.hibernate.LenderItemConditions;
import echomarket.hibernate.LenderTransfer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Id;
import javax.servlet.http.Part;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "itemBean")
@SessionScoped
public class ItemBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  private String itemId;
  private Integer categoryId;
  private String participant_id;
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
  private String whichType;
  private String history_id;
  private Integer history_which;
  private Part imageFileNamePart;
  private String remoteIp;
  private String itemImageCaption;
  private ArrayList<ItemImages> picture
          = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)
          ));

  public ArrayList<ItemImages> getPicture() {
    return picture;

  }

  public void setPicture(ArrayList<ItemImages> aPicture) {
    picture = aPicture;
  }

  public String getLenderHistory(String pid, Integer whichHistory) {
    this.history_id = pid;  /// history_id will be either a community_id or a particpant_id
    this.setHistory_which(whichHistory);
    return "lender_history";
  }

  public String getBorrowerHistory(String pid, Integer whichHistory) {
    this.history_id = pid;
    this.setHistory_which(whichHistory);
    return "borrower_history";
  }

  private String doesImageExist(String iid) {

    Session sb = null;
    Transaction tx = null;
    List result = null;
    String existingImageFileId = null;
    ItemImages existingImageobj = null;
    String queryString = "from ItemImages where item_id = :iid ";
    try {

      sb = hib_session();
      tx = sb.beginTransaction();
      result = sb.createQuery(queryString)
              .setParameter("iid", iid)
              .list();

      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println("Error on Update Item");
    } finally {

      tx = null;
      sb = null;

    }
    if (result.size() > 0) {
      existingImageobj = (ItemImages) result.get(0);
      existingImageFileId = existingImageobj.getImageFileName();
    }
    existingImageobj = null;
    result = null;
    return existingImageFileId;
  }

  private Boolean sendNotification(String notification_type) {
    return true;
  }

  private Boolean deleteExistingUserImage(String iid) {

    Session sb = null;
    Transaction tx = null;
    List result = null;
    Boolean retResult = false;

    /// Delete existing image file name record becuase may be using same name but had editted it
    String queryString = "from ItemImages where item_image_id = :iid ";
    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      result = sb.createQuery(queryString)
              .setParameter("iid", iid)
              .list();
      tx.commit();
      if (result.size() > 0) {
        sb.delete((ItemImages) result.get(0));
      }
      retResult = true;
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in deleteExistingImage");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      sb = null;
      result = null;
    }

    return retResult;

  }

  private Boolean processNewFileImage(String iid, String doesFileExist) {

    Boolean b_return = false;
    List ii = this.getPicture();
    Session sb = null;
    Transaction tx = null;

    try {
      b_return = SaveUserItemImage(getImageFileNamePart(), iid);
    } catch (Exception ex) {
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println("Error in processNewFileImage");;
    }

    if (b_return == true) {
      ItemImages iii = (ItemImages) ii.get(0);
      this.itemImageCaption = iii.getItemImageCaption();
      iii.setItemImageId(getId());
      iii.setItemId(iid);
      iii.setImageFileName(iid + "_" + getFileName(getImageFileNamePart()));
      iii.setImageContentType(getImageFileNamePart().getContentType());

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        if (doesFileExist == null) {
          sb.save(iii);
        } else {
          sb.update(iii);
        }
        tx.commit();
        b_return = true;
      } catch (Exception e) {
        tx.rollback();
        System.out.println("Error on Retreiving Image by Id");
      } finally {
        tx = null;
        sb = null;
      }
    }
    return b_return;

  }

  public String load_ud(String which, String iid) {

    List result = null;

    if (iid.isEmpty() == true) {
      ubean.setEditable(1);
    } else {
      ubean.setEditable(0);
    }

    Map<String, String> params = null;
    String action = null;

    try {
      params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
      action = params.get("action");
      if (action != null) {
        if ("item".equals(action)) {
          ubean.setEditable(1);
        }
      }
    } catch (Exception ex) {
    }

    if (iid.isEmpty() == false) {
      result = getCurrentItem(iid, which);
      if (result != null) {
        if (result.size() == 1) {
          Items ir = (Items) result.get(0);
          setItemId(ir.getItemId());
          this.participant_id = ir.getParticipant_id();
          this.categoryId = ir.getCategoryId();
          this.otherItemCategory = ir.getOtherItemCategory();
          this.itemModel = ir.getItemModel();
          this.itemDescription = ir.getItemDescription();
          this.itemConditionId = ir.getItemConditionId();
          this.itemCount = ir.getItemCount();
          this.itemType = ir.getItemType();
          //    this.comment = ir.getComment();  Later, not in gui yet
          this.notify = ir.getNotify();
          getCurrentPicture(iid);
        }
      }
    } else {
      // itemType was set above...
      itemType = which;
      itemId = null;
      categoryId = -9;
      participant_id = null;
      otherItemCategory = null;
      itemModel = null;
      itemDescription = null;
      itemConditionId = null;
      itemCount = null;
      comment = null;
      dateCreated = null;
      dateUpdated = null;
      dateDeleted = null;
      approved = -9;
      notify = -9;
      whichType = null;
      history_id = null;
      history_which = null;
      ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)));
      setPicture(tmp_picture);

    }

    return "user_item";
  }

  public String saveItem() {

    Session sb = null;
    Transaction tx = null;
    Boolean bret = false;
    String strRetId = null;
    String new_iid = getId();  // As ItemBean is session, this fails in 
    List result = null;
    this.remoteIp = getClientIpAddr();

    if ("both".equals(itemType)) {
      if (whichType.isEmpty() == false) {
        this.itemType = whichType;
      }
    }
    if (itemId.isEmpty() == true) {

      Items ii = new Items(new_iid, ubean.getParticipant_id(), categoryId, otherItemCategory,
              itemModel, itemDescription, itemConditionId, itemCount, comment, new Date(), null, null, 0, notify, itemType, this.remoteIp);

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        sb.save(ii);
        tx.commit();
        bret = true;
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Error in Save Item");
        Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
        bret = false;
      } finally {
        sb = null;
        tx = null;
      }
      if (bret == true) {
        bret = removeNullInLIC(ubean.getParticipant_id(), new_iid);
      }

      if (bret == true) {
        bret = removeNullInLIT(ubean.getParticipant_id(), new_iid);
      }

      if (bret == true) {
        bret = removeNullInCP(ubean.getParticipant_id(), new_iid);
      }

    } else {

      result = getCurrentItem(itemId, itemType);
      if (result.size() == 1) {
        Items uitem = (Items) result.get(0);
        new_iid = uitem.getItemId();
        uitem.setCategoryId(categoryId);
        uitem.setOtherItemCategory(otherItemCategory);
        uitem.setItemModel(itemModel);
        uitem.setItemDescription(itemDescription);
        uitem.setItemConditionId(itemConditionId);
        uitem.setItemCount(itemCount);
        uitem.setItemType(itemType);
        uitem.setApproved(0);

        try {
          sb = hib_session();
          tx = sb.beginTransaction();
          sb.update(uitem);
          tx.commit();
          bret = true;
        } catch (Exception ex) {
          bret = false;
          tx.rollback();
          System.out.println("Error in updating item");
          ex.printStackTrace();
        } finally {
          sb = null;
          tx = null;
        }

      } else {
        bret = false;  // Need to manage?
      }
    }
    /// After save or update is completed...
    if (bret == true) {
      strRetId = doesImageExist(new_iid);
      if (strRetId != null) {
        bret = deleteExistingUserImage(strRetId);
      }
      if ((bret == true) && (this.imageFileNamePart != null)) {
        bret = processNewFileImage(new_iid, strRetId);
      }

      if ((bret == true) && (notify == 1)) {
        bret = sendNotification(itemType);  // This needs to be written...
      }
    }

    if (bret == true) {
      if (itemId.isEmpty() == true) {
        this.itemId = new_iid;
      }
      SendEmail se = null;
      String[] getMap = null;
      getMap = new String[2];
      getMap = ubean.getApplicationEmail();
      if (getImageFileNamePart() != null) {
        se = new SendEmail(this.itemType, ubean.getEmail(), this.itemDescription, this.itemModel, this.itemCount, getFileName(getImageFileNamePart()), this.itemImageCaption, getMap[0], getMap[1], this.remoteIp);
      } else {
        List currentPicture = this.getPicture();
        if (currentPicture != null) {
          if (currentPicture.size() == 1) {
            ItemImages currentImage = (ItemImages) currentPicture.get(0);
            String currentImageFileName = currentImage.getImageFileName();
            String currentImageFileCaption = currentImage.getItemImageCaption();
            se = new SendEmail(this.itemType, ubean.getEmail(), this.itemDescription, this.itemModel, this.itemCount, currentImageFileName, currentImageFileCaption, getMap[0], getMap[1], this.remoteIp);
          }
        } else {
          se = new SendEmail(this.itemType, ubean.getEmail(), this.itemDescription, this.itemModel, this.itemCount, "", "", getMap[0], getMap[1], this.remoteIp);
        }
      }
      
      se = null;
      getMap = null;
      
      message(null, "ItemRecordUpdated", new Object[]{itemType, itemDescription, ubean.getEmail()});
      ubean.setEditable(0);
    } else {
      message(null, "ItemRecordNotUpdated", new Object[]{itemType, itemDescription});
      ubean.setEditable(1);

    }
    return load_ud(itemType, itemId);
  }

  /**
   * @return the categoryId
   */
  public Integer getCategoryId() {
    return categoryId;
  }

  /**
   * @param categoryId the categoryId to set
   */
  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * @return the otherItemCategory
   */
  public String getOtherItemCategory() {
    return otherItemCategory;
  }

  /**
   * @param otherItemCategory the otherItemCategory to set
   */
  public void setOtherItemCategory(String otherItemCategory) {
    this.otherItemCategory = otherItemCategory;
  }

  /**
   * @return the itemModel
   */
  public String getItemModel() {
    return itemModel;
  }

  /**
   * @param itemModel the itemModel to set
   */
  public void setItemModel(String itemModel) {
    this.itemModel = itemModel;
  }

  /**
   * @return the itemDescription
   */
  public String getItemDescription() {
    return itemDescription;
  }

  /**
   * @param itemDescription the itemDescription to set
   */
  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  /**
   * @return the itemConditionId
   */
  public Integer getItemConditionId() {
    return itemConditionId;
  }

  /**
   * @param itemConditionId the itemConditionId to set
   */
  public void setItemConditionId(Integer itemConditionId) {
    this.itemConditionId = itemConditionId;
  }

  /**
   * @return the itemCount
   */
  public Integer getItemCount() {
    return itemCount;
  }

  /**
   * @param itemCount the itemCount to set
   */
  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }

  /**
   * @return the dateCreated
   */
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * @param dateCreated the dateCreated to set
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * @return the dateUpdated
   */
  public Date getDateUpdated() {
    return dateUpdated;
  }

  /**
   * @param dateUpdated the dateUpdated to set
   */
  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  /**
   * @return the dateDeleted
   */
  public Date getDateDeleted() {
    return dateDeleted;
  }

  /**
   * @param dateDeleted the dateDeleted to set
   */
  public void setDateDeleted(Date dateDeleted) {
    this.dateDeleted = dateDeleted;
  }

  /**
   * @return the approved
   */
  public Integer getApproved() {
    return approved;
  }

  /**
   * @param approved the approved to set
   */
  public void setApproved(Integer approved) {
    this.approved = approved;
  }

  /**
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * @param comment the comment to set
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  private static Date hold_date() {
    Date hold_date = new Date();
    return hold_date;

  }

  /**
   * @return the imageFileName
   */
  public Part getImageFileNamePart() {
    return imageFileNamePart;
  }

  /**
   * @param imageFileName the imageFileName to set
   */
  public void setImageFileNamePart(Part imageFileName) {
    this.imageFileNamePart = imageFileName;
  }

  private Boolean SaveUserItemImage(Part ui, String bid) throws IOException {
    Boolean fileCreate = false;
    OutputStream out = null;
    InputStream filecontent = null;
    String itemImagePath = null;
    //String sPath1 = new File(".").getCanonicalPath();  -- tested many 
    // Just for development purposes.... Need to make this into separate function
    String sPath1 = "C://Users//emm//Documents//NetBeansProjects//giving_taking//web//resources";

    String sPath2 = "//" + this.itemType + "_images//";
    String buildFileName = bid + "_" + getFileName(ui);
    String sPath3 = buildFileName;
    File files = new File(sPath1 + sPath2);
    //Boolean makeDirectory = files.mkdirs();
    itemImagePath = sPath1 + sPath2 + sPath3;
    try {
      files = new File(itemImagePath);
      fileCreate = true;
    } catch (Exception ex) {
      System.out.println("Error in Creating New File");
      Logger
              .getLogger(ItemBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    }

    if (fileCreate == true) {

      if (files.exists()) {
        /// User may be using same image file name but has been editted
        files.delete();
      }

      try {
        files = new File(itemImagePath);   /// not sure I have to run it again, esp if was deleted above
        out = new FileOutputStream(files);
        filecontent = ui.getInputStream();
        int read = 0;
        final byte[] bytes = new byte[1024];
        while ((read = filecontent.read(bytes)) != -1) {
          out.write(bytes, 0, read);
        }
        fileCreate = true;
      } catch (FileNotFoundException fne) {
        fileCreate = false;
        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                new Object[]{fne.getMessage()});
      } finally {
        if (out != null) {
          out.close();
        }
        if (filecontent != null) {
          filecontent.close();
        }
        files = null;
      }
    }
    return fileCreate;
  }

  private String getFileName(final Part part) {
    //final String partHeader = part.getHeader("content-disposition");

    for (String content : part.getHeader("content-disposition").split(";")) {
      if (content.trim().startsWith("filename")) {
        return content.substring(
                content.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }

  public List getCurrentItemImage(String iid) {
    return getExistingPicture(iid);

  }

  public List getExistingPicture(String iid) {

    List result = null;
    Session hib = null;
    Transaction tx = null;

    if (iid == null) {
      return getPicture();
    } else {
      String queryString = "Select images from Items itms INNER JOIN itms.itemImages images where itms.itemId = :iid";
      try {
        hib = hib_session();
        tx = hib.beginTransaction();
        result = hib.createQuery(queryString)
                .setParameter("iid", iid)
                .list();
//                .setParameter("itype", this.itemType)
//                .list();
        tx.commit();
      } catch (Exception e) {
        tx.rollback();
        System.out.println("Error in getExistingPicture");
        Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, e);
      } finally {
        tx = null;
        hib = null;
      }

      Integer size_of_list = result.size();
      if (size_of_list == 0) {
        return getPicture();
      } else {
        ItemImages a_array = (ItemImages) result.get(0);
        ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(
                new ItemImages(a_array.getItemImageId(), a_array.getItemId(), a_array.getImageContentType(),
                        a_array.getImageHeight(), a_array.getImageWidth(), a_array.getImageFileName().toString(), a_array.getItemImageCaption())
        ));
        setPicture(tmp_picture);

        a_array = null;
        tmp_picture = null;
        result = null;
        return getPicture();
      }
    }
  }

  public void getCurrentPicture(String iid) {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    ItemImages a_array = null;
    ArrayList<ItemImages> tmp_picture = null;
    String queryString = "Select images from Items itms INNER JOIN itms.itemImages images where itms.itemId = :iid AND itms.itemType = :itype";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .setParameter("itype", this.itemType)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentPicture");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      tx = null;
      hib = null;
    }

    Integer size_of_list = result.size();
    if (size_of_list == 0) {
      tmp_picture = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)
      ));
      setPicture(tmp_picture);
    } else {
      a_array = (ItemImages) result.get(0);
      tmp_picture = new ArrayList<ItemImages>(Arrays.asList(
              new ItemImages(a_array.getItemImageId(), a_array.getItemId(), a_array.getImageContentType(),
                      a_array.getImageHeight(), a_array.getImageWidth(), a_array.getImageFileName().toString(), a_array.getItemImageCaption())
      ));
      setPicture(tmp_picture);
    }
    a_array = null;
    tmp_picture = null;
    result = null;
  }

  private List getCurrentItem(String iid, String which) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();

      query = "FROM Items WHERE item_id = :iid and itemType = :which";
      result = session.createQuery(query)
              .setParameter("iid", iid)
              .setParameter("which", which)
              .list();

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentItem");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, e);

    } finally {
      tx = null;
      session = null;

    }

    return result;
  }

  public List getParticipantItems(String pid, String which) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    this.itemType = which;
    // which_history: 0 = individual, 1 = community
    try {
      session = hib_session();
      tx = session.beginTransaction();
      if (this.history_which == 0) {
        query = "SELECT itmImage.imageFileName, itm.itemId, itm.itemDescription, itm.itemModel, itm.participant_id, itm.approved  "
                + "FROM Items itm INNER JOIN itm.itemImages itmImage WHERE itm.participant_id = :pid AND itm.itemType = :itype ";
      } else if (this.history_which == 1) {
        query = "SELECT itmImage.imageFileName, "
                + "itm.itemId, itm.itemDescription, "
                + "itm.itemModel, part.participant_id, itm.approved FROM Participant part, Items itm "
                + "INNER join part.item itm INNER join itm.itemImages itmImage"
                + "WHERE part.communityId = :pid AND itm.itemType = :itype";
      } else {
        //  Later query = "FROM Items WHERE participant_id = :pid and itemType = :it";
      }

      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("itype", which)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in ParticipantItems");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, e);

    } finally {
      tx = null;
      session = null;

    }

    return result;
  }

  private Boolean DeleteImageFile(String fileName) {

    Boolean return_delete_true = false;
    String sPath1 = "C://Users//emm//Documents//NetBeansProjects//giving_taking//web//resources";
    String sPath2 = "//" + this.itemType + "_images//";
    String sPath3 = fileName;
    File files = new File(sPath1 + sPath2);
    //Boolean makeDirectory = files.mkdirs();
    String itemImagePath = sPath1 + sPath2 + sPath3;
    files = new File(itemImagePath);

    if (files.exists()) {
      /// User may be using same image file name but has been editted
      return_delete_true = files.delete();
    }
    return return_delete_true;
  }

  /**
   * @return the notify
   */
  public int getNotify() {
    return notify;
  }

  /**
   * @param notify the notify to set
   */
  public void setNotify(int notify) {
    this.notify = notify;
  }

  /**
   * @return the itemId
   */
  @Id
  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public String getWhichType() {
    return whichType;
  }

  public void setWhichType(String whichType) {
    this.whichType = whichType;
  }

  public String getParticipant_id() {
    return participant_id;
  }

  public void setParticipant_id(String participant_id) {
    this.participant_id = participant_id;
  }

  /**
   * @return the itemType
   */
  public String getItemType() {
    if (itemType == null && ubean.getEditable() == 1) {
      return ubean.userIsWhichType();
    } else {
      return itemType;
    }
  }

  /**
   * @param itemType the itemType to set
   */
  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  private Boolean removeNullInLIC(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    Boolean return_value = false;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = " from LenderItemConditions lic "
              + " WHERE lic.participant_id = :pid ";
//              + " AND lic.itemId = :iid ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              //              .setParameter("iid", "")
              .setMaxResults(1)
              .list();
      tx.commit();
      if (result != null) {
        if (result.size() == 1) {
          LenderItemConditions na_lic = (LenderItemConditions) result.get(0);
          LenderItemConditions new_lic = new LenderItemConditions(getId(), ubean.getParticipant_id(), iid,
                  na_lic.getForFree(), na_lic.getAvailableForPurchase(), na_lic.getAvailableForPurchaseAmount(), na_lic.getSmallFee(),
                  na_lic.getSmallFeeAmount(), na_lic.getAvailableForDonation(), na_lic.getDonateAnonymous(),
                  na_lic.getTrade(), na_lic.getTradeItem(), na_lic.getAgreedNumberOfDays(), na_lic.getAgreedNumberOfHours(), na_lic.getIndefiniteDuration(),
                  na_lic.getPresentDuringBorrowingPeriod(), na_lic.getEntirePeriod(), na_lic.getPartialPeriod(), na_lic.getProvideProperUseTraining(),
                  na_lic.getSpecificConditions(), na_lic.getSecurityDepositAmount(), na_lic.getSecurityDeposit(), "NA", na_lic.getComment(), new Date(), new Date());
          if (session.isOpen() == false) {
            session = hib_session();
          }
          if (tx.isActive() == false) {

            tx = session.beginTransaction();
          } else {
            tx.rollback();
          }
          session.save(new_lic);
          tx.commit();
          return_value = true;
        }
        return_value = true;
      }
    } catch (Exception ex) {
      System.out.println("Error in removeNullInLIC");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
      tx.rollback();
    } finally {
      tx = null;
      session = null;

    }
    return return_value;
  }

  private Boolean removeNullInLIT(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    Boolean return_value = false;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = " from LenderTransfer lit "
              + " WHERE lit.participant_id = :pid "
              + " AND lit.itemId = :iid ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", "")
              .setMaxResults(1)
              .list();
      tx.commit();
      if (result != null) {
        if (result.size() == 1) {
          LenderTransfer na_lit = (LenderTransfer) result.get(0);
          LenderTransfer new_lt = new LenderTransfer(getId(), iid, ubean.getParticipant_id(),
                  na_lit.getBorrowerComesToWhichAddress(), na_lit.getMeetBorrowerAtAgreedL2b(), na_lit.getMeetBorrowerAtAgreedB2l(), na_lit.getWillDeliverToBorrower(),
                  na_lit.getThirdPartyPresenceL2b(), na_lit.getThirdPartyPresenceB2l(), na_lit.getBorrowerThirdPartyChoice(), na_lit.getAgreedThirdPartyChoiceL2b(),
                  na_lit.getAgreedThirdPartyChoiceB2l(), na_lit.getBorrowerReturnsToWhichAddress(), na_lit.getWillPickUpPreferredLocationB2l(), na_lit.getLenderThirdPartyChoiceB2l(),
                  na_lit.getBorrowerChoice(), "NA", na_lit.getComment(), new Date(), new Date(), null);
          if (session.isOpen() == false) {
            session = hib_session();
          }
          if (tx.isActive() == false) {
            tx = session.beginTransaction();
          } else {
            tx.rollback();
          }
          session.save(new_lt);
          /// should create new record
          tx.commit();
          return_value = true;
        }
        return_value = true;
      }
    } catch (Exception ex) {
      System.out.println("Error in removeNullInLIC");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);

      tx.rollback();

    } finally {
      tx = null;
      session = null;

    }
    return return_value;
  }

  private Boolean removeNullInCP(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    Boolean return_value = false;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = " from ContactPreference cp "
              + " WHERE cp.participant_id = :pid "
              + " AND cp.itemId = :iid ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", "")
              .setMaxResults(1)
              .list();
      tx.commit();
      if (result != null) {
        if (result.size() == 1) {
          ContactPreference na_cp = (ContactPreference) result.get(0);
          ContactPreference new_cp = new ContactPreference(getId(), ubean.getParticipant_id(), iid,
                  na_cp.getUseWhichContactAddress(), na_cp.getContactByChat(), na_cp.getContactByEmail(), na_cp.getContactByHomePhone(),
                  na_cp.getContactByCellPhone(), na_cp.getContactByAlternativePhone(), na_cp.getContactByFacebook(), na_cp.getContactByTwitter(),
                  na_cp.getContactByInstagram(), na_cp.getContactByLinkedIn(), na_cp.getContactByOtherSocialMedia(), na_cp.getContactByOtherSocialMediaAccess(), new Date());
          if (session.isOpen() == false) {
            session = hib_session();
          }
          if (tx.isActive() == false) {
            tx = session.beginTransaction();
          } else {
            tx.rollback();
          }
          session.save(new_cp);
          tx.commit();
          return_value = true;
        }
        return_value = true;
      }
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in removeNullInLIC");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);

    } finally {
      tx = null;
      session = null;

    }
    return return_value;
  }

  public void deleteCurrentRecord(String iid, String itemDescription) {
    // Need to set up hibernate to cascade for all affected tables...
    Session hib = null;
    Transaction tx = null;
    Integer retResult = 0;
    Query query = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      query = hib.createQuery("delete Items where itemId = :iid");
      query.setParameter("iid", iid);
      retResult = query.executeUpdate();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in deleteCurrentRecord");
      Logger
              .getLogger(ItemBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      hib = null;
      query = null;
    }
    if (retResult > 0) {
      message(null, "DeleteSelectedItemSuccess", new Object[]{itemDescription});
    } else {
      message(null, "DeleteSelectedItemFailed", new Object[]{itemDescription});
    }

  }

  /**
   * @return the history_id
   */
  public String getHistory_id() {
    return history_id;
  }

  /**
   * @param history_id the history_id to set
   */
  public void setHistory_id(String history_id) {
    this.history_id = history_id;
  }

  /**
   * @return the history_which
   */
  public Integer getHistory_which() {
    return history_which;
  }

  /**
   * @param history_which the history_which to set
   */
  public void setHistory_which(Integer history_which) {
    this.history_which = history_which;
  }

  /**
   * @return the remoteIp
   */
  public String getRemoteIp() {
    return remoteIp;
  }

  /**
   * @param remoteIp the remoteIp to set
   */
  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  /**
   * @return the itemImageCaption
   */
  public String getItemImageCaption() {
    return itemImageCaption;
  }

  /**
   * @param itemImageCaption the itemImageCaption to set
   */
  public void setItemImageCaption(String itemImageCaption) {
    this.itemImageCaption = itemImageCaption;
  }
}
