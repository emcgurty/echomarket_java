package echomarket.web.managedbeans;

import echomarket.SendEmail.SendEmail;
import echomarket.hibernate.Items;
import echomarket.hibernate.ItemImages;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.Id;
import javax.servlet.http.Part;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class ItemBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 9L;

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
  private List itemFoundList;
  private List imageFoundList;
  private ArrayList<ItemImages> picture
          = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)
          ));

// emm 1.8
  public ItemBean() {

  }

  public ArrayList<ItemImages> getPicture() {
    return picture;

  }

  public void setPicture(ArrayList<ItemImages> aPicture) {
    picture = aPicture;
  }

  public String getLenderHistory(String pid, Integer whichHistory) {
    this.history_id = pid;  /// history_id will be either a community_id or a particpant_id
    this.setHistory_which(whichHistory);
    this.itemFoundList = null;
    return "lender_history";
  }

  public String getBorrowerHistory(String pid, Integer whichHistory) {
    this.history_id = pid;
    this.setHistory_which(whichHistory);
    this.itemFoundList = null;
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
      iii.setItemImageCaption(itemImageCaption);
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
    this.imageFoundList = null;
    // emm 1.8

    if (app != null) {

      if (iid.isEmpty() == true) {
        app.setEditable(1);
      } else {
        app.setEditable(0);
      }

      if (this.itemId != null) {
        if (this.itemId.isEmpty() == false) {
          iid = this.itemId;
          app.setEditable(0);
        }
      }

      if (app.getItemId() != null) {
        if (app.getItemId().isEmpty() == false) {
          iid = app.getItemId();
          app.setEditable(0);
        }
      }

      Map<String, String> params = null;
      String action = null;

      try {
        params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        action = params.get("action");
        if (action != null) {
          if ("item".equals(action)) {

            app.setEditable(1);
          }
        }
      } catch (Exception ex) {
      }
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
      this.itemType = which;
      this.itemId = null;
      this.categoryId = -9;
      this.participant_id = null;
      this.otherItemCategory = null;
      this.itemModel = null;
      this.itemDescription = null;
      this.itemConditionId = null;
      this.itemCount = null;
      this.comment = null;
      this.dateCreated = null;
      this.dateUpdated = null;
      this.dateDeleted = null;
      this.approved = -9;
      this.notify = -9;
      this.whichType = null;
      this.history_id = null;
      this.history_which = null;
      ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)));
      setPicture(tmp_picture);
      /// loading picture needs a delay.... Not hapy with this.  Must be a way to test without using sleep

    }

    return "user_item";
  }

  public String saveItem() {

    // Data gathering of image caption now seems to by working fine.  Realize I have to eliminate redunancy in code
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

      Items ii = new Items(new_iid, app.getParticipant_id(), categoryId, otherItemCategory,
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
    }

    ////  If newImagePart.. What about this.picture caption changed...
    if ((bret == true) && (this.imageFileNamePart != null)) {
      bret = processNewFileImage(new_iid, strRetId);
    }

    if ((bret == true) && (this.imageFileNamePart == null)) {
      List currentPicture = this.getPicture();
      if (currentPicture != null) {
        if (currentPicture.size() == 1) {
          ItemImages currentImage = (ItemImages) currentPicture.get(0);
          setItemImageCaption(itemImageCaption);
          try {
            sb = hib_session();
            tx = sb.beginTransaction();
            sb.update(currentImage);
            tx.commit();
          } catch (Exception ex) {
            tx.rollback();

          } finally {
            sb = null;
            tx = null;
          }
        } else {

        }

      } else {

      }
    }

    if ((bret == true) && (notify == 1)) {
      bret = sendNotification(itemType);  // This needs to be written...
    }

    if (bret == true) {
      if (itemId.isEmpty() == true) {
        this.itemId = new_iid;
      }
      SendEmail se = null;
      String[] getMap = null;
      getMap = new String[2];
      getMap = app.getApplicationEmail();
      if (getImageFileNamePart() != null) {
        se = new SendEmail(this.itemType, app.getEmail(), this.itemDescription, this.itemModel, this.itemCount, getFileName(getImageFileNamePart()), this.itemImageCaption, getMap[0], getMap[1], this.remoteIp);
      } else {
        List currentPicture = this.getPicture();
        if (currentPicture != null) {
          if (currentPicture.size() == 1) {
            ItemImages currentImage = (ItemImages) currentPicture.get(0);
            String currentImageFileName = currentImage.getImageFileName();
            String currentImageFileCaption = currentImage.getItemImageCaption();
            se = new SendEmail(this.itemType, app.getEmail(), this.itemDescription, this.itemModel, this.itemCount, currentImageFileName, currentImageFileCaption, getMap[0], getMap[1], this.remoteIp);
          } else {
            se = new SendEmail(this.itemType, app.getEmail(), this.itemDescription, this.itemModel, this.itemCount, "", "", getMap[0], getMap[1], this.remoteIp);
          }

        } else {
          se = new SendEmail(this.itemType, app.getEmail(), this.itemDescription, this.itemModel, this.itemCount, "", "", getMap[0], getMap[1], this.remoteIp);
        }
      }

      se = null;
      getMap = null;

      message(null, "ItemRecordUpdated", new Object[]{itemType, itemDescription, app.getEmail()});
      app.setItemId(new_iid);
      app.setEditable(0);
    } else {
      message(null, "ItemRecordNotUpdated", new Object[]{itemType, itemDescription});
      app.setEditable(1);

    }
    return load_ud(itemType, itemId) + "?faces-redirect=true";
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
    ExternalContext ctx = context().getExternalContext();
    String absoluteWebPath = ctx.getRealPath("/");
    String resource_path = absoluteWebPath + "\\resources\\";
    String image_path = resource_path + "\\" + this.itemType + "_images\\";
    String buildFileName = image_path + bid + "_" + getFileName(ui);
    File files = null;

    try {
      files = new File(buildFileName);
      fileCreate = true;
    } catch (Exception ex) {
      System.out.println("Error in Creating New File");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (fileCreate == true) {
      if (files.exists()) {
        /// User may be using same image file name but has been editted
        files.delete();
      }

      try {
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
        Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, "SaveUserItemImage", fne);
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

    List result_list = null;
    if (iid.isEmpty() == true) {
      result_list = getPicture();
    } else if (this.imageFoundList == null) {
      result_list = getExistingPicture(iid);
    } else {
      result_list = imageFoundList;
    }
    return result_list;
  }

  private List getExistingPicture(String iid) {

    System.out.println("IN getExistingPicture");
    List result = null;
    Session hib = null;
    Transaction tx = null;

    if (iid == null) {
      return getPicture();
    } else {

      String queryString = " Select images from Items itms INNER JOIN itms.itemImages images where itms.itemId = :iid";
      try {
        hib = hib_session();
        tx = hib.beginTransaction();
        result = hib.createQuery(queryString)
                .setParameter("iid", iid)
                .list();
        tx.commit();
        this.imageFoundList = result;
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

  private void getCurrentPicture(String iid) {

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
    try {
      Thread.sleep(2500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String getCurrentImageName(String iid) {

    System.out.println("IN getCurrentImageName");
    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    String currentimageFileName = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();

      query = " FROM ItemImages WHERE item_id = :iid ";
      result = session.createQuery(query)
              .setParameter("iid", iid)
              .list();

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentImageName");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, e);

    } finally {
      tx = null;
      session = null;
    }

    if (result != null) {
      if (result.size() == 1) {
        ItemImages itmImg = (ItemImages) result.get(0);
        currentimageFileName = itmImg.getImageFileName();
      }
    }
    result = null;
    return currentimageFileName;
  }

  private List getCurrentItem(String iid, String which) {

    System.out.println("IN getCurrentItem");
    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();

      query = " FROM Items WHERE item_id = :iid and itemType = :which";
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

    System.out.println("called getParticipantItems");
    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    this.itemType = which;

    if (this.itemFoundList == null) {
      // which_history: 0 = individual, 1 = community
      try {
        session = hib_session();
        tx = session.beginTransaction();
        if (this.history_which == 0) {
          query = " SELECT itm "
                  + " FROM Items itm "
                  + " WHERE itm.participant_id = :pid AND itm.itemType = :itype ORDER BY itm.dateCreated ";
        } else if (this.history_which == 1) {
          query = " SELECT itm "
                  + " FROM Participant part, Items itm "
                  + " INNER join part.item itm "
                  + " WHERE part.communityId = :pid AND itm.itemType = :itype ORDER BY itm.dateCreated ";
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
        System.out.println("Error in getParticipantItems");
        Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, e);

      } finally {
        tx = null;
        session = null;

      }
      this.itemFoundList = result;
    }

    if (this.itemFoundList != null) {
      return this.itemFoundList;
    } else {
      return result;
    }
  }

  private Boolean DeleteImageFile(String fileName) {

    Boolean return_delete_true = false;
    ExternalContext ctx = context().getExternalContext();
    String absoluteWebPath = ctx.getRealPath("/");
    String resource_path = absoluteWebPath + "\\resources\\";
    String image_path = resource_path + "\\" + this.itemType + "_images\\";
    String buildFileName = image_path + fileName;
    File files = new File(buildFileName);

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
    if (itemType == null && app.getEditable() == 1) {
      return app.userIsWhichType();
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

  /**
   * @return the imageFoundList
   */
  public List getImageFoundList() {
    return imageFoundList;
  }

  /**
   * @param imageFoundList the imageFoundList to set
   */
  public void setImageFoundList(List imageFoundList) {
    this.imageFoundList = imageFoundList;
  }

}
