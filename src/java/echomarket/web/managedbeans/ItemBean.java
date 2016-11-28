package echomarket.web.managedbeans;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Id;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "itemBean")
@RequestScoped
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
  private Part imageFileNamePart;
  private ArrayList<ItemImages> picture
          = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)
          ));

  public ArrayList<ItemImages> getPicture() {
//    if (this.itemId == null) {
////      if (ubean.getEditable() == 1) {
//        ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(new ItemImages(null, null, null, null, null, "echo_market.png", null)));
//        setPicture(tmp_picture);
////      }
//    }
    return picture;

  }

  public void setPicture(ArrayList<ItemImages> aPicture) {
    picture = aPicture;
  }

  private String doesImageExist(String iid) {

    Session sb = hib_session();
    Transaction tx = sb.beginTransaction();
    List result = null;
    String existingImageFileId = null;
    ItemImages existingImageobj = null;
    String queryString = "from ItemImages where item_id = :iid ";

    result = sb.createQuery(queryString)
            .setParameter("iid", iid)
            .list();
    try {
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

//        Session sb = hib_session();
//        Transaction tx = sb.beginTransaction();
//        Find all lenders that match a borrowers needs, and vice-versa.  Then send email
    return true;
  }

  private Boolean deleteExistingUserImage(String iid) {

    Session sb = hib_session();
    Transaction tx = sb.beginTransaction();
    List result = null;
    Boolean retResult = false;

    /// Delete existing image file name record becuase maybe be using same name but had editted
    String queryString = "from ItemImages where item_image_id = :iid ";

    result = sb.createQuery(queryString)
            .setParameter("iid", iid)
            .list();
    try {
      if (result.size() > 0) {
        sb.delete((ItemImages) result.get(0));
      }
      tx.commit();
      retResult = true;
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error on Retreiving Image by Id");
    } finally {
      tx = null;
      sb = null;
      result = null;
    }

    return retResult;

  }

  private Boolean processNewFileImage(String iid) {

    Boolean b_return = false;
    List ii = this.getPicture();
    Session sb = hib_session();
    Transaction tx = sb.beginTransaction();

    try {
      b_return = SaveUserItemImage(getImageFileNamePart(), iid);
    } catch (Exception ex) {
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
      System.out.println("Error in Saving Borrower File");;
    }

    if (b_return == true) {
      ItemImages iii = (ItemImages) ii.get(0);
      iii.setItemImageId(getId());
      iii.setItem_id(iid);
      iii.setImageFileName(iid + "_" + getFileName(getImageFileNamePart()));
      iii.setImageContentType(getImageFileNamePart().getContentType());

      try {
        sb.save(iii);
        tx.commit();
        b_return = true;
      } catch (Exception e) {
        b_return = false;
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
    if ((ubean.getEditable().toString().isEmpty() == true) && (iid == null)) {
      ubean.setEditable(1);
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

    if (iid != null) {
      result = getCurrentItem(iid);
      if (result.size() == 1) {
        Items ir = (Items) result.get(0);
        this.itemId = ir.getItemId();
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
        // Image detail will be retrieved from gui
      }
    } else {
    }
       if (ubean.getUserType() == "lend") {
        return "lender_user_item.xhtml?faces-redirect=true";
      } else {
        return "borrower_user_item.xhtml?faces-redirect=true";
      }
    

  }

  public String saveItem() {

    Session sb;
    Transaction tx;
    sb = null;
    tx = null;
    Boolean bret = false;
    String strRetId = null;
    String new_iid = getId();
    List result = null;
    String which_type = null;

    if (itemType.isEmpty() == true) {
      if (whichType.isEmpty() == false) {
        itemType = whichType;
      } else {
        itemType = "NA";   /// wrong but will help with bebugging...
      }
    }

    if (itemId.isEmpty() == true) {

      Items ii = new Items(new_iid, ubean.getParticipant_id(), categoryId, otherItemCategory,
              itemModel, itemDescription, itemConditionId, itemCount, comment, new Date(), null, null, 1, notify, itemType);

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        sb.save(ii);
        tx.commit();
        bret = true;
        ubean.setItemId(new_iid);
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
        bret = removeNAFromLIC(ubean.getParticipant_id(), new_iid);
      }

      if (bret == true) {
        bret = removeNAFromLIT(ubean.getParticipant_id(), new_iid);
      }

    } else {
      result = getCurrentItem(itemId);
      if (result.size() == 1) {
        Items uitem = (Items) result.get(0);
        uitem.setCategoryId(categoryId);
        uitem.setOtherItemCategory(otherItemCategory);
        uitem.setItemModel(itemModel);
        uitem.setItemDescription(itemDescription);
        uitem.setItemConditionId(itemConditionId);
        uitem.setItemCount(itemCount);
        uitem.setItemType(itemType);

        if (sb.isOpen() == false) {
          sb = hib_session();
        }
        if (tx.isActive() == false) {
          tx = sb.beginTransaction();
        }

        try {
          sb.save(uitem);
          tx.commit();
          bret = true;
          ubean.setItemId(itemId);
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
        bret = false;
      }
    }

    if (bret == true) {
      strRetId = doesImageExist(new_iid);
      if (strRetId != null) {
        bret = deleteExistingUserImage(strRetId);
      }
      if ((bret == true) && (this.imageFileNamePart != null)) {
        bret = processNewFileImage(new_iid);
      }

      if ((bret == true) && (notify == 1)) {
        bret = sendNotification(itemType);  // This needs to be written...
      }
    }

    if (bret == true) {

      message(null, "ItemRecordUpdated", new Object[]{itemType, itemDescription});
      ubean.setEditable(0);
      if (itemId.isEmpty() == true) {
        this.itemId = new_iid;
      }
      return this.load_ud(itemType, itemId);

    } else {
      message(null, "ItemRecordNotUpdated", null);

      if (itemType == "lend") {
        return "lender_user_item";
      } else {
        return "borower_user_item";
      }
    }
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
  public int getApproved() {
    return approved;
  }

  /**
   * @param approved the approved to set
   */
  public void setApproved(int approved) {
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
      ex.printStackTrace();
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
    Session hib = hib_session();
    Transaction tx = hib.beginTransaction();
    String[] results = null;
    if (iid == null) {
      return getPicture();
    } else {
      String queryString = "from ItemImages where item_id = :iid ";
      try {
        result = hib.createQuery(queryString)
                .setParameter("iid", iid)
                .list();
        tx.commit();
      } catch (Exception e) {
        tx.rollback();
        System.out.println("Error in getExistingPicture");
        e.printStackTrace();
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
                new ItemImages(a_array.getItemImageId(), a_array.getItem_id(), a_array.getImageContentType(),
                        a_array.getImageHeight(), a_array.getImageWidth(), a_array.getImageFileName().toString(), a_array.getItemImageCaption())
        ));
        setPicture(tmp_picture);

        a_array = null;
        tmp_picture = null;
        return getPicture();
      }
    }
  }

  private List getCurrentItem(String iid) {

    List result = null;
    Session session = hib_session();
    Transaction tx = session.beginTransaction();
    String query = null;
    try {
      query = "FROM Items WHERE item_id = '" + iid + "'";
      result = session.createQuery(query).list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentItem");
      e.printStackTrace();

    } finally {
      tx = null;
      session = null;

    }

    return result;
  }

  public String getCurrentItemDescription(String iid) {

    List result = null;
    Session session = hib_session();
    Transaction tx = session.beginTransaction();
    String local_string = null;

    try {
      local_string = "FROM Items WHERE item_id = '" + iid + "'";
      result = session.createQuery(local_string).list();
      tx.commit();
      Items item_d = (Items) result.get(0);
      local_string = item_d.getItemDescription();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentItem");
      e.printStackTrace();

    } finally {
      tx = null;
      session = null;

    }

    return local_string;
  }

  public List getAllSoughtItems(String which) {
    System.out.println("getAllSoughtItems Called");
    List result = null;
    Session session = hib_session();
    Transaction tx = session.beginTransaction();
    String query = null;
    try {
      query = "FROM Items WHERE itemType = :it ORDER BY dateCreated";
      result = session.createQuery(query)
              .setParameter("it", which)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getAllSoughtItems");
      e.printStackTrace();

    } finally {
      tx = null;
      session = null;

    }

    return result;
  }

  public List getParticipantItems(String pid, String which) {
    System.out.println("getParticipantItems Called");
    List result = null;
    Session session = hib_session();
    Transaction tx = session.beginTransaction();
    String query = null;
    try {
      query = "FROM Items WHERE participant_id = :pid and itemType = :it";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("it", which)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in ParticipantItems");
      e.printStackTrace();

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

  public void deleteItem(String iid, String itemDesc) {

    List result = null;
    Session hib = hib_session();
    Transaction tx = hib.beginTransaction();
    Boolean retResult = false;

    String queryString = "from Items where item_id = :iid";
    try {
      result = hib.createQuery(queryString)
              .setParameter("iid", iid)
              .list();
      tx.commit();
      retResult = true;
    } catch (Exception ex) {
      tx.rollback();
      retResult = false;
      System.out.println("Error in deleting borrower record");
      Logger
              .getLogger(ItemBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      result = null;
      tx = null;
      hib = null;
    }
    if (retResult == true) {
      message(null, "DeleteSelectedBorrowerSuccess", new Object[]{itemDesc});
    } else {
      message(null, "DeleteSelectedBorrowerFailed", new Object[]{itemDesc});
    }

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

  /**
   * @param itemId the itemId to set
   */
  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  /**
   * @return the itemType
   */
  public String getWhichType() {
    return whichType;
  }

  /**
   * @param itemType the itemType to set
   */
  public void setWhichType(String whichType) {
    this.whichType = whichType;
  }

  /**
   * @return the participant_id
   */
  public String getParticipant_id() {
    return participant_id;
  }

  /**
   * @param participant_id the participant_id to set
   */
  public void setParticipant_id(String participant_id) {
    this.participant_id = participant_id;
  }

  /**
   * @return the itemType
   */
  public String getItemType() {
    if (itemType == null && ubean.getEditable() == 1) {
      return ubean.useIsWhichType();
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

  private Boolean removeNAFromLIC(String pid, String iid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    Boolean return_value = false;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      query = " from LenderItemConditions lic "
              + " WHERE lic.participant_id = :pid "
              + " AND lic.itemId = :iid ";
      result = session.createQuery(query)
              .setParameter("pid", pid)
              .setParameter("iid", "NA")
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
          /// should create new record
          tx.commit();
          return_value = true;
        }
        return_value = true;
      }
    } catch (Exception ex) {
      System.out.println("Error in removeNAFromLIC");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
      ex.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      session = null;

    }
    return return_value;
  }

  private Boolean removeNAFromLIT(String pid, String iid) {

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
              .setParameter("iid", "NA")
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
      System.out.println("Error in removeNAFromLIC");
      Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
      ex.printStackTrace();
      tx.rollback();

    } finally {
      tx = null;
      session = null;

    }
    return return_value;
  }
}
