package echomarket.web.managedbeans;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import echomarket.hibernate.Addresses;
import echomarket.hibernate.Borrowers;
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

/// Credit due: https://www.javacodegeeks.com/2015/11/jsf-scopes-tutorial-jsfcdi-session-scope.html
/// Added return "index?faces-redirect=true"; Source: http://stackoverflow.com/questions/3642919/javax-faces-application-viewexpiredexception-view-could-not-be-restored
@Named
@ManagedBean(name = "bb")
@RequestScoped
public class BorrowersBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    private int contactDescribeId;
    private String organizationName;
    private int displayBorrowerOrganizationName;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private int displayBorrowerName;
    private int displayBorrowerAddress;
    private int displayBorrowerAlternativeAddress;
    private String homePhone;
    private String cellPhone;
    private String alternativePhone;
    private Integer publicDisplayHomePhone;
    private Integer publicDisplayCellPhone;
    private Integer publicDisplayAlternativePhone;
    private int useWhichContactAddress;
    private String emailAlternative;
    private String borrowerContactByEmail;
    private Integer borrowerContactByHomePhone;
    private Integer borrowerContactByCellPhone;
    private Integer borrowerContactByAlternativePhone;
    private String borrowerContactByFacebook;
    private String borrowerContactByTwitter;
    private String borrowerContactByInstagram;
    private String borrowerContactByLinkedIn;
    private String borrowerContactByOtherSocialMedia;
    private String borrowerContactByOtherSocialMediaAccess;
    private Integer categoryId;
    private String otherItemCategory;
    private String itemModel;
    private String itemDescription;
    private Integer itemConditionId;
    private Integer itemCount;
    private Integer goodwill;
    private Integer age18OrMore;
    private Integer isActive;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;
    private int approved;
    private int notifyLenders;
    private Integer receiveLenderNotification;
    private Integer isCommunity;
    private String alias;
    private String remoteIp;
    private String comment;
    private String advertiserId;
    private String processId;
    private Part imageFileNamePart;
    

    private static ArrayList<ItemImages> picture
            = new ArrayList<ItemImages>(Arrays.asList(
                    new ItemImages(UUID.randomUUID().toString(), null, null, null, null, null, null, hold_date(), hold_date(), hold_date(), "temp", null, null)
            ));

    private static ArrayList<Addresses> primary
            = new ArrayList<Addresses>(Arrays.asList(
                    new Addresses(UUID.randomUUID().toString(), null, null, null, null, null, null, null, null, null, null, "primary")
            ));

    private static ArrayList<Addresses> alternative
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(), null, null, null, null, null, null, null, null, null, null, "alternative")));

    private Addresses[] existing_primary; //= getExistingBorrowerAddress("primary");
    private Addresses[] existing_alternative; // = getExistingBorrowerAddress("alternative");

    public ArrayList<ItemImages> getPicture() {
        return picture;
    }

    public static void setPicture(ArrayList<ItemImages> aPicture) {
        picture = aPicture;
    }

    public ArrayList<Addresses> getPrimary() {
        return primary;
    }

    public ArrayList<Addresses> getAlternative() {
        return alternative;
    }

    public static void setPrimary(ArrayList<Addresses> aPrimary) {
        primary = aPrimary;
    }

    public static void setAlternative(ArrayList<Addresses> aAlternative) {
        alternative = aAlternative;
    }

    public String saveBorrowerEdit() {

//        List padrs = getExisting_primary();
//        List aadrs = getExisting_alternative();
        List ii = this.getPicture();
        List result = null;
        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        Date today_date = new Date();
        String current_user = null;
        String existingFileNamestr = null;
        try {
            current_user = ubean.getUser_id();
        } catch (Exception e) {

            e.printStackTrace();
        }
        /// Need to implement onChange Listener to learn if dirty
        Borrowers bb = new Borrowers(ubean.getUserAction(), current_user, getContactDescribeId(), getOrganizationName(), getDisplayBorrowerOrganizationName(), getOtherDescribeYourself(),
                getFirstName(), getMi(), getLastName(), getDisplayBorrowerName(), getDisplayBorrowerAddress(), getHomePhone(),
                getCellPhone(), getAlternativePhone(), getPublicDisplayHomePhone(), getPublicDisplayCellPhone(),
                getPublicDisplayAlternativePhone(), getUseWhichContactAddress(), getEmailAlternative(), getBorrowerContactByEmail(),
                getBorrowerContactByHomePhone(), getBorrowerContactByCellPhone(), getBorrowerContactByAlternativePhone(),
                getBorrowerContactByFacebook(), getBorrowerContactByTwitter(), getBorrowerContactByInstagram(), getBorrowerContactByLinkedIn(),
                getBorrowerContactByOtherSocialMedia(), getBorrowerContactByOtherSocialMediaAccess(), getCategoryId(), getOtherItemCategory(),
                getItemModel(), getItemDescription(), getItemConditionId(), getItemCount(), getGoodwill(), getAge18OrMore(),
                getIsActive(), today_date, today_date, null, getApproved(), getNotifyLenders(), getReceiveLenderNotification(),
                getIsCommunity(), null, getComment(), getAdvertiserId(), getDisplayBorrowerAlternativeAddress());

        sb.update(bb);
        try {
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error on Update Borrower");
        }

        //// Have to code for case that they remove original picture, and do not replace
        if (sb.isOpen() == false) {
            sb = hib_session();
        }
        if (tx.isActive() == false) {
            tx = sb.beginTransaction();
        }

        /// Delete existing image file name record becuase maybe be using same name but had editted
        String queryString = "from ItemImages where borrower_id = :bid ";

        result = sb.createQuery(queryString)
                .setParameter("bid", ubean.getUserAction())
                .list();
        tx.commit();

        /// Delete the record, get the file name for later delete if exists
        if (result.size() > 0) {
            ItemImages existingImageobj = (ItemImages) result.get(0);
            existingFileNamestr = existingImageobj.getImageFileName();
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            sb.delete((ItemImages) result.get(0));
            tx.commit();

            try {

                if (existingFileNamestr != null) {
                    // Will manage return later
                    Boolean ret_result = false;
                    ret_result = DeleteImageFile(existingFileNamestr);
                    // if result false provide user information
                }

            } catch (Exception e) {
                System.out.println("Error on deleting exsitng Image Record");

            }
        }
        /// Now process editted image information
        if (this.imageFileNamePart != null) {

            try {
                SaveUserItemImage(getImageFileNamePart(), ubean.getUserAction());
            } catch (Exception e) {
                System.out.println("Error in Saving Borrower File");;
            }
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }

            try {
                ItemImages iii = (ItemImages) ii.get(0);
                iii.setId(getId());
                iii.setBorrower_id(ubean.getUserAction());
                iii.setLender_id("NA");
                iii.setImageFileName(ubean.getUserAction() + "_" + getFileName(getImageFileNamePart()));
                iii.setImageContentType(getImageFileNamePart().getContentType());
                //ItemImages create_record = new ItemImages(getId(), ubean.getUserAction(), null, getImageFileNamePart().getContentType(), null, null, this.isActive, today_date, null, today_date,  + "_" + getFileName(getImageFileNamePart()), , null);
                sb.save(iii);
                tx.commit();
            } catch (Exception ex) {
                Logger.getLogger(BorrowersBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

            }

        }

        if ((getUseWhichContactAddress() == 2) || (getUseWhichContactAddress() == 1)) {

            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }

            try {
                sb.update(this.existing_alternative);
                tx.commit();
            } catch (Exception ex) {
                Logger.getLogger(BorrowersBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }

        }

//        Addresses ba = (Addresses) padrs.get(0);
        if (sb.isOpen() == false) {
            sb = hib_session();
        }
        if (tx.isActive() == false) {
            tx = sb.beginTransaction();
        }

        try {
            sb.update(this.existing_primary);
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(BorrowersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            message(
                    null,
                    "BorrowerRegistionRecordUpdated",
                    null);

        }
        return "index?faces-redirect=true";

    }

    public String saveBorrowerRegistration() throws IOException {

        List padrs = getPrimary();
        List aadrs = getAlternative();
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
        Borrowers bb = new Borrowers(getAbstId, current_user, getContactDescribeId(), getOrganizationName(), getDisplayBorrowerOrganizationName(), getOtherDescribeYourself(),
                getFirstName(), getMi(), getLastName(), getDisplayBorrowerName(), getDisplayBorrowerAddress(), getHomePhone(),
                getCellPhone(), getAlternativePhone(), getPublicDisplayHomePhone(), getPublicDisplayCellPhone(),
                getPublicDisplayAlternativePhone(), getUseWhichContactAddress(), getEmailAlternative(), getBorrowerContactByEmail(),
                getBorrowerContactByHomePhone(), getBorrowerContactByCellPhone(), getBorrowerContactByAlternativePhone(),
                getBorrowerContactByFacebook(), getBorrowerContactByTwitter(), getBorrowerContactByInstagram(), getBorrowerContactByLinkedIn(),
                getBorrowerContactByOtherSocialMedia(), getBorrowerContactByOtherSocialMediaAccess(), getCategoryId(), getOtherItemCategory(),
                getItemModel(), getItemDescription(), getItemConditionId(), getItemCount(), getGoodwill(), getAge18OrMore(),
                getIsActive(), today_date, today_date, null, getApproved(), getNotifyLenders(), getReceiveLenderNotification(),
                getIsCommunity(), null, getComment(), getAdvertiserId(), getDisplayBorrowerAlternativeAddress());

        sb.save(bb);
        try {
            tx.commit();
        } catch (Exception e) {
        }
        //public Addresses(String id, String lender_id, String borrower_id, String addressLine1, String addressLine2, String postalCode, String city, String province, String usStateId, String region, String countryId, String addressType) {

        if (getImageFileNamePart() != null) {
            try {
                SaveUserItemImage(getImageFileNamePart(), getAbstId);
            } catch (Exception e) {
                System.out.println("Error in Saving Borrower File");;

            }

            ItemImages iii = (ItemImages) ii.get(0);
            iii.setId(getId());
            iii.setBorrower_id(getAbstId);
            iii.setLender_id("NA");
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
            iii.setBorrower_id(getAbstId);
            iii.setLender_id("NA");
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            sb.save(iii);
            tx.commit();
        }

        if ((getUseWhichContactAddress() == 2) || (getUseWhichContactAddress() == 1)) {

            Addresses balt = (Addresses) aadrs.get(0);
            balt.setBorrower_id(getAbstId);

            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }

            try {
                sb.save(balt);
                tx.commit();
            } catch (Exception e) {
            } finally {
            }

        }

        Addresses ba = (Addresses) padrs.get(0);
        ba.setBorrower_id(getAbstId);

        if (sb.isOpen() == false) {
            sb = hib_session();
        }
        if (tx.isActive() == false) {
            tx = sb.beginTransaction();
        }

        try {
            sb.save(ba);
            tx.commit();
        } catch (Exception e) {
        } finally {
            message(
                    null,
                    "BorrowerRegistionRecordSaved",
                    null);

        }
        return "index?faces-redirect=true";
    }

    public int getContactDescribeId() {
        return contactDescribeId;
    }

    public void setContactDescribeId(int contactDescribeId) {
        this.contactDescribeId = contactDescribeId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getDisplayBorrowerOrganizationName() {
        return displayBorrowerOrganizationName;
    }

    public void setDisplayBorrowerOrganizationName(int displayBorrowerOrganizationName) {
        this.displayBorrowerOrganizationName = displayBorrowerOrganizationName;
    }

    public String getOtherDescribeYourself() {
        return otherDescribeYourself;
    }

    public void setOtherDescribeYourself(String otherDescribeYourself) {
        this.otherDescribeYourself = otherDescribeYourself;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the displayBorrowerName
     */
    public int getDisplayBorrowerName() {
        return displayBorrowerName;
    }

    /**
     * @param displayBorrowerName the displayBorrowerName to set
     */
    public void setDisplayBorrowerName(int displayBorrowerName) {
        this.displayBorrowerName = displayBorrowerName;
    }

    /**
     * @return the displayBorrowerAddress
     */
    public int getDisplayBorrowerAddress() {
        return displayBorrowerAddress;
    }

    /**
     * @param displayBorrowerAddress the displayBorrowerAddress to set
     */
    public void setDisplayBorrowerAddress(int displayBorrowerAddress) {
        this.displayBorrowerAddress = displayBorrowerAddress;
    }

    /**
     * @return the homePhone
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * @param homePhone the homePhone to set
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * @return the cellPhone
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * @param cellPhone the cellPhone to set
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * @return the alternativePhone
     */
    public String getAlternativePhone() {
        return alternativePhone;
    }

    /**
     * @param alternativePhone the alternativePhone to set
     */
    public void setAlternativePhone(String alternativePhone) {
        this.alternativePhone = alternativePhone;
    }

    /**
     * @return the publicDisplayHomePhone
     */
    public Integer getPublicDisplayHomePhone() {
        return publicDisplayHomePhone;
    }

    /**
     * @param publicDisplayHomePhone the publicDisplayHomePhone to set
     */
    public void setPublicDisplayHomePhone(Integer publicDisplayHomePhone) {
        this.publicDisplayHomePhone = publicDisplayHomePhone;
    }

    /**
     * @return the publicDisplayCellPhone
     */
    public Integer getPublicDisplayCellPhone() {
        return publicDisplayCellPhone;
    }

    /**
     * @param publicDisplayCellPhone the publicDisplayCellPhone to set
     */
    public void setPublicDisplayCellPhone(Integer publicDisplayCellPhone) {
        this.publicDisplayCellPhone = publicDisplayCellPhone;
    }

    /**
     * @return the publicDisplayAlternativePhone
     */
    public Integer getPublicDisplayAlternativePhone() {
        return publicDisplayAlternativePhone;
    }

    /**
     * @param publicDisplayAlternativePhone the publicDisplayAlternativePhone to
     * set
     */
    public void setPublicDisplayAlternativePhone(Integer publicDisplayAlternativePhone) {
        this.publicDisplayAlternativePhone = publicDisplayAlternativePhone;
    }

    /**
     * @return the useWhichContactAddress
     */
    public int getUseWhichContactAddress() {
        return useWhichContactAddress;
    }

    /**
     * @param useWhichContactAddress the useWhichContactAddress to set
     */
    public void setUseWhichContactAddress(int useWhichContactAddress) {
        this.useWhichContactAddress = useWhichContactAddress;
    }

    /**
     * @return the emailAlternative
     */
    public String getEmailAlternative() {
        return emailAlternative;
    }

    /**
     * @param emailAlternative the emailAlternative to set
     */
    public void setEmailAlternative(String emailAlternative) {
        this.emailAlternative = emailAlternative;
    }

    /**
     * @return the borrowerContactByEmail
     */
    public String getBorrowerContactByEmail() {
        return borrowerContactByEmail;
    }

    /**
     * @param borrowerContactByEmail the borrowerContactByEmail to set
     */
    public void setBorrowerContactByEmail(String borrowerContactByEmail) {
        this.borrowerContactByEmail = borrowerContactByEmail;
    }

    /**
     * @return the borrowerContactByHomePhone
     */
    public Integer getBorrowerContactByHomePhone() {
        return borrowerContactByHomePhone;
    }

    /**
     * @param borrowerContactByHomePhone the borrowerContactByHomePhone to set
     */
    public void setBorrowerContactByHomePhone(Integer borrowerContactByHomePhone) {
        this.borrowerContactByHomePhone = borrowerContactByHomePhone;
    }

    /**
     * @return the borrowerContactByCellPhone
     */
    public Integer getBorrowerContactByCellPhone() {
        return borrowerContactByCellPhone;
    }

    /**
     * @param borrowerContactByCellPhone the borrowerContactByCellPhone to set
     */
    public void setBorrowerContactByCellPhone(Integer borrowerContactByCellPhone) {
        this.borrowerContactByCellPhone = borrowerContactByCellPhone;
    }

    /**
     * @return the borrowerContactByAlternativePhone
     */
    public Integer getBorrowerContactByAlternativePhone() {
        return borrowerContactByAlternativePhone;
    }

    /**
     * @param borrowerContactByAlternativePhone the
     * borrowerContactByAlternativePhone to set
     */
    public void setBorrowerContactByAlternativePhone(Integer borrowerContactByAlternativePhone) {
        this.borrowerContactByAlternativePhone = borrowerContactByAlternativePhone;
    }

    /**
     * @return the borrowerContactByFacebook
     */
    public String getBorrowerContactByFacebook() {
        return borrowerContactByFacebook;
    }

    /**
     * @param borrowerContactByFacebook the borrowerContactByFacebook to set
     */
    public void setBorrowerContactByFacebook(String borrowerContactByFacebook) {
        this.borrowerContactByFacebook = borrowerContactByFacebook;
    }

    /**
     * @return the borrowerContactByTwitter
     */
    public String getBorrowerContactByTwitter() {
        return borrowerContactByTwitter;
    }

    /**
     * @param borrowerContactByTwitter the borrowerContactByTwitter to set
     */
    public void setBorrowerContactByTwitter(String borrowerContactByTwitter) {
        this.borrowerContactByTwitter = borrowerContactByTwitter;
    }

    /**
     * @return the borrowerContactByInstagram
     */
    public String getBorrowerContactByInstagram() {
        return borrowerContactByInstagram;
    }

    /**
     * @param borrowerContactByInstagram the borrowerContactByInstagram to set
     */
    public void setBorrowerContactByInstagram(String borrowerContactByInstagram) {
        this.borrowerContactByInstagram = borrowerContactByInstagram;
    }

    /**
     * @return the borrowerContactByLinkedIn
     */
    public String getBorrowerContactByLinkedIn() {
        return borrowerContactByLinkedIn;
    }

    /**
     * @param borrowerContactByLinkedIn the borrowerContactByLinkedIn to set
     */
    public void setBorrowerContactByLinkedIn(String borrowerContactByLinkedIn) {
        this.borrowerContactByLinkedIn = borrowerContactByLinkedIn;
    }

    /**
     * @return the borrowerContactByOtherSocialMedia
     */
    public String getBorrowerContactByOtherSocialMedia() {
        return borrowerContactByOtherSocialMedia;
    }

    /**
     * @param borrowerContactByOtherSocialMedia the
     * borrowerContactByOtherSocialMedia to set
     */
    public void setBorrowerContactByOtherSocialMedia(String borrowerContactByOtherSocialMedia) {
        this.borrowerContactByOtherSocialMedia = borrowerContactByOtherSocialMedia;
    }

    /**
     * @return the borrowerContactByOtherSocialMediaAccess
     */
    public String getBorrowerContactByOtherSocialMediaAccess() {
        return borrowerContactByOtherSocialMediaAccess;
    }

    /**
     * @param borrowerContactByOtherSocialMediaAccess the
     * borrowerContactByOtherSocialMediaAccess to set
     */
    public void setBorrowerContactByOtherSocialMediaAccess(String borrowerContactByOtherSocialMediaAccess) {
        this.borrowerContactByOtherSocialMediaAccess = borrowerContactByOtherSocialMediaAccess;
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
     * @return the goodwill
     */
    public Integer getGoodwill() {
        return goodwill;
    }

    /**
     * @param goodwill the goodwill to set
     */
    public void setGoodwill(Integer goodwill) {
        this.goodwill = goodwill;
    }

    /**
     * @return the age18OrMore
     */
    public Integer getAge18OrMore() {
        return age18OrMore;
    }

    /**
     * @param age18OrMore the age18OrMore to set
     */
    public void setAge18OrMore(Integer age18OrMore) {
        this.age18OrMore = age18OrMore;
    }

    /**
     * @return the isActive
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
     * @return the notifyLenders
     */
    public int getNotifyLenders() {
        return notifyLenders;
    }

    /**
     * @param notifyLenders the notifyLenders to set
     */
    public void setNotifyLenders(int notifyLenders) {
        this.notifyLenders = notifyLenders;
    }

    /**
     * @return the receiveLenderNotification
     */
    public Integer getReceiveLenderNotification() {
        return receiveLenderNotification;
    }

    /**
     * @param receiveLenderNotification the receiveLenderNotification to set
     */
    public void setReceiveLenderNotification(Integer receiveLenderNotification) {
        this.receiveLenderNotification = receiveLenderNotification;
    }

    /**
     * @return the isCommunity
     */
    public Integer getIsCommunity() {
        return isCommunity;
    }

    /**
     * @param isCommunity the isCommunity to set
     */
    public void setIsCommunity(Integer isCommunity) {
        this.isCommunity = isCommunity;
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

    /**
     * @return the advertiserId
     */
    public String getAdvertiserId() {
        return advertiserId;
    }

    /**
     * @param advertiserId the advertiserId to set
     */
    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    /**
     * @return the displayBorrowerAlternativeAddress
     */
    public int getDisplayBorrowerAlternativeAddress() {
        return displayBorrowerAlternativeAddress;
    }

    /**
     * @param displayBorrowerAlternativeAddress the
     * displayBorrowerAlternativeAddress to set
     */
    public void setDisplayBorrowerAlternativeAddress(int displayBorrowerAlternativeAddress) {
        this.displayBorrowerAlternativeAddress = displayBorrowerAlternativeAddress;
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
            query = "FROM Borrowers as b WHERE b.user_id = '" + bid + "'";
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

    public String getCurrentEditRecord(String bid) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String[] results = null;
        Borrowers[] a_array = null;
        String queryString = "from Borrowers where borrower_id = :bid order by date_created";
        ubean.setUserAction(bid);

        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", bid)
                    .list();
            tx.commit();
        } catch (Exception e) {
        } finally {

            tx = null;
            hib = null;
        }
        // Must be just one record.. will error check later..

        Borrowers to_Array = (Borrowers) result.get(0);
        this.contactDescribeId = to_Array.getContactDescribeId();
        this.organizationName = to_Array.getOrganizationName();
        this.displayBorrowerOrganizationName = to_Array.getDisplayBorrowerOrganizationName();
        this.otherDescribeYourself = to_Array.getOtherDescribeYourself();
        this.firstName = to_Array.getFirstName();
        this.mi = to_Array.getMi();
        this.lastName = to_Array.getLastName();
        this.displayBorrowerName = to_Array.getDisplayBorrowerName();
        this.displayBorrowerAddress = to_Array.getDisplayBorrowerAddress();
        this.homePhone = to_Array.getHomePhone();
        this.cellPhone = to_Array.getCellPhone();
        this.alternativePhone = to_Array.getAlternativePhone();
        this.publicDisplayHomePhone = to_Array.getPublicDisplayHomePhone();
        this.publicDisplayCellPhone = to_Array.getPublicDisplayCellPhone();
        this.publicDisplayAlternativePhone = to_Array.getPublicDisplayAlternativePhone();
        this.useWhichContactAddress = to_Array.getUseWhichContactAddress();
        this.emailAlternative = to_Array.getEmailAlternative();
        this.borrowerContactByEmail = to_Array.getBorrowerContactByEmail();
        this.borrowerContactByHomePhone = to_Array.getBorrowerContactByHomePhone();
        this.borrowerContactByCellPhone = to_Array.getBorrowerContactByCellPhone();
        this.borrowerContactByAlternativePhone = to_Array.getBorrowerContactByAlternativePhone();
        this.borrowerContactByFacebook = to_Array.getBorrowerContactByFacebook();
        this.borrowerContactByTwitter = to_Array.getBorrowerContactByTwitter();
        this.borrowerContactByInstagram = to_Array.getBorrowerContactByInstagram();
        this.borrowerContactByLinkedIn = to_Array.getBorrowerContactByLinkedIn();
        this.borrowerContactByOtherSocialMedia = to_Array.getBorrowerContactByOtherSocialMedia();
        this.borrowerContactByOtherSocialMediaAccess = to_Array.getBorrowerContactByOtherSocialMediaAccess();
        this.categoryId = to_Array.getCategoryId();
        this.otherItemCategory = to_Array.getOtherItemCategory();
        this.itemModel = to_Array.getItemModel();
        this.itemDescription = to_Array.getItemDescription();
        this.itemConditionId = to_Array.getItemConditionId();
        this.itemCount = to_Array.getItemCount();
        this.goodwill = to_Array.getGoodwill();
        this.age18OrMore = to_Array.getAge18OrMore();
        this.isActive = to_Array.getIsActive();
        this.dateCreated = to_Array.getDateCreated();
        this.dateUpdated = to_Array.getDateUpdated();
        this.approved = to_Array.getApproved();
        this.notifyLenders = to_Array.getNotifyLenders();
        this.receiveLenderNotification = to_Array.getReceiveLenderNotification();
        this.isCommunity = to_Array.getIsCommunity();
        this.comment = to_Array.getComment();
        this.advertiserId = to_Array.getAdvertiserId();
        this.displayBorrowerAlternativeAddress = to_Array.getDisplayBorrowerAlternativeAddress();

        return "edit_borrower";

    }

    private List getExistingBorrowerAddress(String which) {

        List result = null;
        Addresses[] a_array = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Addresses where borrower_id = :bid AND address_type = :which";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", ubean.getUserAction())
                    .setParameter("which", which)
                    .list();
            tx.commit();
        } catch (Exception e) {

        } finally {
            tx = null;
            hib = null;
        }

        Integer size_of_list = result.size();
        if ((size_of_list == 0) && (which == "alternative")) {
            return getAlternative();
        } else if ((size_of_list == 0) && (which == "primary")) {
            return getPrimary();
        } else {
            return result;
        }

//        a_array = new Addresses[size_of_list];
//        for (int i = 0; i < size_of_list; i++) {
//            Addresses to_Array = (Addresses) result.get(i);
//            //(String id, String lender_id, String borrower_id, String addressLine1, String addressLine2, String postalCode, String city, String province, String usStateId, String region, String countryId, String addressType) {
//            a_array[i] = new Addresses(to_Array.getId(), to_Array.getLender_id(), to_Array.getBorrower_id(), to_Array.getAddressLine1(), to_Array.getAddressLine2(), to_Array.getPostalCode(), to_Array.getCity(), to_Array.getProvince(), to_Array.getUsStateId(), to_Array.getRegion(), to_Array.getCountryId(), to_Array.getAddressType());
//        }
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
            ItemImages a_array = (ItemImages) result.get(0);
            ArrayList<ItemImages> tmp_picture = new ArrayList<ItemImages>(Arrays.asList(
                    new ItemImages(a_array.getId(), a_array.getBorrower_id(), a_array.getLender_id(), a_array.getImageContentType(),
                            a_array.getImageHeight(), a_array.getImageWidth(), a_array.getIsActive(), a_array.getDateCreated(), a_array.getDateDeleted(),
                            a_array.getDateUpdated(), a_array.getImageFileName(), a_array.getItemImageCaption(), a_array.getAdvertiserId())
            ));
            setPicture(tmp_picture);

            return getPicture();
        }

    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * @return the existing_primary
     */
    public List getExisting_primary() {
        return getExistingBorrowerAddress("primary");
    }

    /**
     * @param existing_primary the existing_primary to set
     */
    public void setExisting_primary(Addresses[] existing_primary) {
        this.existing_primary = existing_primary;
    }

    /**
     * @return the existing_alternative
     */
    public List getExisting_alternative() {
        return getExistingBorrowerAddress("alternative");
    }

    /**
     * @param existing_alternative the existing_alternative to set
     */
    public void setExisting_alternative(Addresses[] existing_alternative) {
        this.existing_alternative = existing_alternative;
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

    public String deleteCurrentRecord(String bid, String itemDesc) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Borrowers where borrower_id = :bid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", bid)
                    .list();
            
            if (result.size() > 0) {
                
                hib.delete((Borrowers) result.get(0));
                tx.commit();
            } else {
            }

        } catch (Exception ex) {
            System.out.println("Error in deleting borrower record");
            Logger.getLogger(BorrowersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            result = null;
            tx = null;
            hib = null;

            message(
                    null,
                    "DeleteSelecteBorrower",
                    new Object[]{itemDesc});
        }
        return "borrower_history";
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
}
