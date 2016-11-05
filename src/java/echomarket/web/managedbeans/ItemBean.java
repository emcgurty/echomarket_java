package echomarket.web.managedbeans;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import echomarket.hibernate.Addresses;
import echomarket.hibernate.Items;
import echomarket.hibernate.ItemImages;
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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "item")
@RequestScoped
public class ItemBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    //
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
    private Part imageFileNamePart;
    private Integer editable;

    private static ArrayList<ItemImages> picture
            = new ArrayList<ItemImages>(Arrays.asList(
                    new ItemImages(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null, null, null, "temp", null)
            ));
    
    public ArrayList<ItemImages> getPicture() {
        return picture;
    }

    public static void setPicture(ArrayList<ItemImages> aPicture) {
        picture = aPicture;
    }

    private String doesImageExist() {

        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        List result = null;
        String existingImageFileId = null;

        String queryString = "from ItemImages where borrower_id = :bid ";

        result = sb.createQuery(queryString)
                .setParameter("bid", ubean.getUserAction())
                .list();
        try {
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on Update Item");
        } finally {
            if (sb != null) {
                sb.close();
            }
            tx = null;
            sb = null;

        }
        if (result.size() > 0) {
            ItemImages existingImageobj = (ItemImages) result.get(0);
            existingImageFileId = existingImageobj.getImageFileName();
        }

        result = null;
        return existingImageFileId;
    }

    private Boolean sendNotification(String notification_type) {

//        Session sb = hib_session();
//        Transaction tx = sb.beginTransaction();

        return true;
    }

    private Boolean deleteExistingUserImage(String iid) {

        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        List result = null;
        Boolean retResult = false;

        /// Delete existing image file name record becuase maybe be using same name but had editted
        String queryString = "from ItemImages where image_id = :iid ";

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
            if (sb != null) {
                sb.close();
            }
            tx = null;
            sb = null;

        }

        return retResult;

    }

    private Boolean processNewFileImage() {

        Boolean b_return = false;
        List ii = this.getPicture();
        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();

        try {
            SaveUserItemImage(getImageFileNamePart(), ubean.getUserAction());
        } catch (Exception ex) {
            Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error in Saving Borrower File");;
        }

        ItemImages iii = (ItemImages) ii.get(0);
        iii.setItemImagesId(getId());
        iii.setParticipantId(ubean.getUserAction());
        iii.setImageFileName(ubean.getUserAction() + "_" + getFileName(getImageFileNamePart()));
        iii.setImageContentType(getImageFileNamePart().getContentType());

        try {
            sb.save(iii);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error on Retreiving Image by Id");
        } finally {
            b_return = true;
            if (sb != null) {
                sb.close();
            }
            tx = null;
            sb = null;
        }

        return b_return;

    }

    public String saveItemEdit() {

        List result = null;
        //Session sb = hib_session();
        //Transaction tx = sb.beginTransaction();
        Boolean bret = false;
        String strRetId = doesImageExist();
        if (strRetId != null) {
            bret = deleteExistingUserImage(strRetId);
        }
        if ((bret == true) && (this.imageFileNamePart != null)) {
            bret = processNewFileImage();
        }

        if ((bret == true) && (this.notify == 1)) {
            bret = sendNotification("lenders");
        }

        if (bret == true) {
//            tx = null;
//            sb = null;
            message(
                    null,
                    "ItemRecordUpdated",
                    null);
        } else {
            message(
                    null,
                    "ItemRecordNotUpdated",
                    null);

        }
        return "index?faces-redirect=true";

    }

    public String saveBorrowerRegistration() throws IOException {

        List ii = getPicture();
        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        Date today_date = new Date();
        String getAbstId = getId();
        String current_user = null;
        try {

            current_user = ubean.getUser_id();
            //ut = getUser_type();

        } catch (Exception e) {
            System.out.println("Testing inject vs session Map");
            e.printStackTrace();
        }

        if (getImageFileNamePart() != null) {
            try {
                SaveUserItemImage(getImageFileNamePart(), getAbstId);
            } catch (Exception e) {
                System.out.println("Error in Saving Borrower File");;

            }

            ItemImages iii = (ItemImages) ii.get(0);
            iii.setItemImagesId(getId());
            iii.setParticipantId(getAbstId);

            // Did this becuase graphicImage does not recognize dynmically build attribute library
            iii.setImageFileName(getAbstId + "_" + getFileName(getImageFileNamePart()));
            iii.setImageContentType(getImageFileNamePart().getContentType());
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            sb.save(iii);
            tx.commit();

        } else {

            ItemImages iii = (ItemImages) ii.get(0);
            iii.setParticipantId(getAbstId);

            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            sb.save(iii);
            tx.commit();
        }

        message(
                null,
                "BorrowerRegistionRecordSaved",
                null);

        return "index?faces-redirect=true";
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

    private void SaveUserItemImage(Part ui, String bid) throws IOException {
        OutputStream out = null;
        InputStream filecontent = null;
        String itemImagePath = null;
        //String sPath1 = new File(".").getCanonicalPath();  -- tested many 
        // Just for development purposes.... Need to make this into separate function
        String sPath1 = "C://Users//emm//Documents//NetBeansProjects//giving_taking//web//resources";
        String sPath2 = "//borrower_images//";
        String buildFileName = bid + "_" + getFileName(ui);
        String sPath3 = buildFileName;
        File files = new File(sPath1 + sPath2);
        //Boolean makeDirectory = files.mkdirs();
        itemImagePath = sPath1 + sPath2 + sPath3;
        files = new File(itemImagePath);
//   Testing... leaving at true status for the moment... 
        if (true) {

            if (files.exists()) {
                /// User may be using same image file name but has been editted
                files.delete();
            }

            try {
                files = new File(itemImagePath);   /// not sure I have to run it again??
                out = new FileOutputStream(files);
                filecontent = ui.getInputStream();
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

            } catch (FileNotFoundException fne) {
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
        } else {

            /// Oracle documentation says that mkdri = false is not necessarily an error...
        }

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

    public List getCurrentBorrowerImage(String bid) {
        ubean.setUserAction(bid);
        return getExistingPicture();

    }

    public List getCurrentBorrower(String bid) {
        System.out.println("getCurrent Borrower Called");
        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String query = null;
        try {
            query = "FROM Item as b WHERE b.user_id = '" + bid + "'";
            result = session.createQuery(query).list();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error in getCurrentB");
            e.printStackTrace();

        } finally {
            tx = null;
            //session = null;

        }

        return result;
    }

    public void getItemRecord(String bid) {

        this.editable = 0;

    }

    
    public List getExistingPicture() {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String[] results = null;
        String queryString = "from ItemImages where borrower_id = :bid ";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", ubean.getUserAction())
                    .list();
            tx.commit();
        } catch (Exception e) {
        } finally {
            tx = null;
            hib = null;
        }

        Integer size_of_list = result.size();
        if (size_of_list == 0) {
            return getPicture();
        } else {
//            ItemImages a_array = (ItemImages) result.get(0);
////            ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(
////                    new ItemImages(a_array.getId(), a_array.getBorrower_id(), a_array.getLender_id(), a_array.getImageContentType(),
////                            a_array.getImageHeight(), a_array.getImageWidth(), a_array.getIsActive(), a_array.getDateCreated(), a_array.getDateDeleted(),
////                            a_array.getDateUpdated(), a_array.getImageFileName(), a_array.getItemImageCaption(), a_array.getAdvertiserId())
////            ));
//            setPicture(tmp_picture);

            return getPicture();
        }

    }

    private Boolean DeleteImageFile(String fileName) {

        Boolean return_delete_true = false;
        String sPath1 = "C://Users//emm//Documents//NetBeansProjects//giving_taking//web//resources";
        String sPath2 = "//borrower_images//";
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

    public void deleteItem(String bid, String itemDesc) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Items where participant_id = :bid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", bid)
                    .list();

            if (result.size() > 0) {

                hib.delete((Items) result.get(0));
                tx.commit();
            } else {
            }

        } catch (Exception ex) {
            System.out.println("Error in deleting borrower record");
            Logger.getLogger(ItemBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            result = null;
            tx = null;
            hib = null;

            message(
                    null,
                    "DeleteSelecteBorrower",
                    new Object[]{itemDesc});
        }
        //return "borrower_history";
    }
}
