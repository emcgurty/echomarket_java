package echomarket.web.managedbeans;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import echomarket.hibernate.Addresses;
import echomarket.hibernate.Lenders;
import echomarket.hibernate.ItemImages;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
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
@ManagedBean(name = "ll")
@RequestScoped
public class LenderBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    private int contactDescribeId;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private int displayLenderName;
    private int displayLenderAddress;
    private int displayLenderAlternativeAddress;
    private String homePhone;
    private String cellPhone;
    private String alternativePhone;
    private Integer publicDisplayHomePhone;
    private Integer publicDisplayCellPhone;
    private Integer publicDisplayAlternativePhone;
    private int useWhichContactAddress;
    private String emailAlternative;
    private Integer borrowerContactByEmail;
    private Integer borrowerContactByHomePhone;
    private Integer borrowerContactByCellPhone;
    private Integer borrowerContactByAlternativePhone;
    private String borrowerContactByFacebook;
    private String borrowerContactByTwitter;
    private String borrowerContactByInstagram;
    private String borrowerContactByLinkedIn;
    private String borrowerContactByOtherSocialMedia;
    private String borrowerContactByOtherSocialMediaAccess;
    private Integer BComesToWhichAddress;
    private Integer meetBorrowerAtAgreedL2b;
    private Integer willDeliverToBorrowerPreferredL2b;
    private Integer thirdPartyPresenceL2b;
    private Integer lenderThirdPartyChoiceB2l;
    private Integer lenderThirdPartyChoiceL2b;
    private Integer agreedThirdPartyChoiceL2b;
    private Integer BReturnsToWhichAddress;
    private Integer meetBorrowerAtAgreedB2l;
    private Integer willPickUpPreferredLocationB2l;
    private Integer thirdPartyPresenceB2l;
    private Integer agreedThirdPartyChoiceB2l;
    private Integer borrowerChoice;
    private Integer categoryId;
    private String otherItemCategory;
    private String itemModel;
    private String itemDescription;
    private Integer itemCount;
    private Integer forFree;
    private Integer availableForPurchase;
    private BigDecimal availableForPurchaseAmount;
    private Integer smallFee;
    private BigDecimal smallFeeAmount;
    private Integer availableForDonation;
    private Integer donateAnonymous;
    private Integer trade;
    private String tradeItem;
    private Integer agreedNumberOfDays;
    private Integer agreedNumberOfHours;
    private Integer indefiniteDuration;
    private Integer presentDuringBorrowingPeriod;
    private Integer entirePeriod;
    private Integer partialPeriod;
    private Integer provideProperUseTraining;
    private String specificConditions;
    private Integer goodwill;
    private Integer age18OrMore;
    private Integer isActive;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;
    private String organizationName;
    private Integer displayLenderOrganizationName;
    private int approved;
    private Integer notifyBorrowers;
    private Integer receiveBorrowerNotifications;
    private int itemConditionId;
    private BigDecimal securityDepositAmount;
    private Integer securityDeposit;
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

    private Addresses[] existing_primary; //= getExistingLenderAddress("primary");
    private Addresses[] existing_alternative; // = getExistingLenderAddress("alternative");

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

    public String saveLenderEdit() {

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

        Lenders bb = new Lenders(ubean.getUserAction(), current_user, getContactDescribeId(), getOtherDescribeYourself(), getFirstName(), getMi(), getLastName(), getDisplayLenderName(),
                getDisplayLenderAddress(), getDisplayLenderAlternativeAddress(), getHomePhone(), getCellPhone(), getAlternativePhone(), getPublicDisplayHomePhone(), getPublicDisplayCellPhone(),
                getPublicDisplayAlternativePhone(), getUseWhichContactAddress(), getEmailAlternative(), getBorrowerContactByEmail(), getBorrowerContactByHomePhone(), getBorrowerContactByCellPhone(), getBorrowerContactByAlternativePhone(),
                getBorrowerContactByFacebook(), getBorrowerContactByTwitter(), getBorrowerContactByInstagram(), getBorrowerContactByLinkedIn(), getBorrowerContactByOtherSocialMedia(),
                getBorrowerContactByOtherSocialMediaAccess(), getBComesToWhichAddress(), getMeetBorrowerAtAgreedL2b(), getWillDeliverToBorrowerPreferredL2b(), getThirdPartyPresenceL2b(),
                getLenderThirdPartyChoiceL2b(), getAgreedThirdPartyChoiceL2b(), getBReturnsToWhichAddress(), getMeetBorrowerAtAgreedB2l(), getWillPickUpPreferredLocationB2l(), getThirdPartyPresenceB2l(),
                getLenderThirdPartyChoiceB2l(), getAgreedThirdPartyChoiceB2l(), getBorrowerChoice(), getCategoryId(), getOtherItemCategory(), getItemModel(), getItemDescription(), getItemCount(), getForFree(),
                getAvailableForPurchase(), getAvailableForPurchaseAmount(), getSmallFee(), getSmallFeeAmount(), getAvailableForDonation(), getDonateAnonymous(), getTrade(), getTradeItem(),
                getAgreedNumberOfDays(), getAgreedNumberOfHours(), getIndefiniteDuration(), getPresentDuringBorrowingPeriod(), getEntirePeriod(), getPartialPeriod(), getProvideProperUseTraining(),
                getSpecificConditions(), getGoodwill(), getAge18OrMore(), getIsActive(), today_date, null, null, getOrganizationName(), getDisplayLenderOrganizationName(), getApproved(), getNotifyBorrowers(),
                getReceiveBorrowerNotifications(), getItemConditionId(), getSecurityDepositAmount(), getSecurityDeposit(), getIsCommunity(), null, getComment(), getAdvertiserId());

        try {
            sb.update(bb);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error on Update Lender");
        }

        //// Have to code for case that they remove original picture, and do not replace
        if (sb.isOpen() == false) {
            sb = hib_session();
        } else {
            sb.flush();
            sb.clear();
        }
        if (tx.isActive() == false) {
            tx = sb.beginTransaction();
        }

        /// Delete existing image file name record becuase maybe be using same name but had editted
        String queryString = "from ItemImages where lender_id = :lid ";

        result = sb.createQuery(queryString)
                .setParameter("lid", ubean.getUserAction())
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
                System.out.println("Error on deleting existng Image Record");

            }
        }
        /// Now process editted image information
        if (this.getImageFileNamePart() != null) {

            try {
                SaveUserItemImage(getImageFileNamePart(), ubean.getUserAction());
            } catch (Exception e) {
                System.out.println("Error in Saving Lender File");
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
                iii.setLender_id(ubean.getUserAction());
                iii.setBorrower_id("NA");
                iii.setImageFileName(ubean.getUserAction() + "_" + getFileName(getImageFileNamePart()));
                iii.setImageContentType(getImageFileNamePart().getContentType());
                //ItemImages create_record = new ItemImages(getId(), ubean.getUserAction(), null, getImageFileNamePart().getContentType(), null, null, this.isActive, today_date, null, today_date,  + "_" + getFileName(getImageFileNamePart()), , null);
                sb.save(iii);
                tx.commit();
            } catch (Exception ex) {
                Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

            }

        }

        if ((getUseWhichContactAddress() == 2) || (getUseWhichContactAddress() == 1)) {

//            Addresses balt = (Addresses) aadrs.get(0);
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }

            try {
                sb.update(this.getExisting_alternative());
                tx.commit();
            } catch (Exception ex) {
                Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
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
            sb.update(this.getExisting_primary());
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            tx = null;
            sb = null;
            message(
                    null,
                    "LenderRegistionRecordUpdated",
                    null);

        }
        return "index?faces-redirect=true";

    }

    public String saveLenderRegistration() throws IOException {

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
        ///  getNine becuase database was unpredictably failing on these fields when null... 
        Lenders bb;
        bb = new Lenders(getAbstId, current_user,
                getContactDescribeId(),
                getOtherDescribeYourself(),
                getFirstName(),
                getMi(),
                getLastName(),
                getDisplayLenderName(),
                getDisplayLenderAddress(),
                getDisplayLenderAlternativeAddress(),
                getHomePhone(), getCellPhone(), getAlternativePhone(), getPublicDisplayHomePhone(), getPublicDisplayCellPhone(),
                getPublicDisplayAlternativePhone(), getUseWhichContactAddress(), getEmailAlternative(), getBorrowerContactByEmail(), getBorrowerContactByHomePhone(), getBorrowerContactByCellPhone(), getBorrowerContactByAlternativePhone(),
                getBorrowerContactByFacebook(), getBorrowerContactByTwitter(), getBorrowerContactByInstagram(), getBorrowerContactByLinkedIn(), getBorrowerContactByOtherSocialMedia(),
                getBorrowerContactByOtherSocialMediaAccess(), getBComesToWhichAddress(), getMeetBorrowerAtAgreedL2b(), getWillDeliverToBorrowerPreferredL2b(), getThirdPartyPresenceL2b(),
                getNine(getLenderThirdPartyChoiceL2b()),
                getNine(getAgreedThirdPartyChoiceL2b()),
                getNine(getBReturnsToWhichAddress()),
                getNine(getMeetBorrowerAtAgreedB2l()),
                getNine(getWillPickUpPreferredLocationB2l()),
                getNine(getThirdPartyPresenceB2l()),
                getNine(getLenderThirdPartyChoiceB2l()),
                getNine(getAgreedThirdPartyChoiceB2l()),
                getBorrowerChoice(), getCategoryId(),
                getOtherItemCategory(), getItemModel(), getItemDescription(),
                getItemCount(),
                getForFree(),
                getAvailableForPurchase(),
                getAvailableForPurchaseAmount(), getSmallFee(), getSmallFeeAmount(), getAvailableForDonation(), getDonateAnonymous(), getTrade(), getTradeItem(),
                getAgreedNumberOfDays(),
                getAgreedNumberOfHours(),
                getIndefiniteDuration(),
                getPresentDuringBorrowingPeriod(),
                getEntirePeriod(),
                getPartialPeriod(),
                getProvideProperUseTraining(),
                getSpecificConditions(),
                getGoodwill(), getAge18OrMore(),
                getIsActive(), today_date, today_date, null,
                getOrganizationName(), getDisplayLenderOrganizationName(),
                getApproved(), getNotifyBorrowers(),
                getReceiveBorrowerNotifications(),
                getItemConditionId(), getSecurityDepositAmount(), getSecurityDeposit(), getIsCommunity(), null, getComment(), getAdvertiserId());

        try {
            sb.save(bb);
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error in Saving Lender File");
            Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        //public Addresses(String id, String lender_id, String borrower_id, String addressLine1, String addressLine2, String postalCode, String city, String province, String usStateId, String region, String countryId, String addressType) {

        if (getImageFileNamePart() != null) {
            try {
                SaveUserItemImage(getImageFileNamePart(), getAbstId);
            } catch (Exception ex) {
                System.out.println("Error in Saving Lender Image File");
                Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            ItemImages iii = (ItemImages) ii.get(0);
            iii.setId(getId());
            iii.setLender_id(getAbstId);
            iii.setBorrower_id("NA");
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
            iii.setLender_id(getAbstId);
            iii.setBorrower_id("NA");
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            try {
                sb.save(iii);
                tx.commit();
            } catch (Exception ex) {
                System.out.println("Error in Saving Lender Image");
                Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if ((getUseWhichContactAddress() == 2) || (getUseWhichContactAddress() == 1)) {

            Addresses balt = (Addresses) aadrs.get(0);
            balt.setLender_id(getAbstId);

            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }

            try {
                sb.save(balt);
                tx.commit();
            } catch (Exception ex) {
                System.out.println("Error in Saving Lender Alternative Address");
                Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            }

        }

        Addresses ba = (Addresses) padrs.get(0);
        ba.setLender_id(getAbstId);
        if (sb.isOpen() == false) {
            sb = hib_session();
        }
        if (tx.isActive() == false) {
            tx = sb.beginTransaction();
        }

        try {
            sb.save(ba);
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Error in Saving Lender Primary Address");
            Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            tx = null;
            try {
                sb = null;
            } catch (Exception ex) {
                System.out.println("Error in Closing Hibernate Session after all transactions on Save new Lender Record");
                Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            message(
                    null,
                    "LenderRegistionRecordSaved",
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
        this.setItemConditionId((int) itemConditionId);
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
     * @return the displayLenderAlternativeAddress
     */
    public int getDisplayLenderAlternativeAddress() {
        return displayLenderAlternativeAddress;
    }

    /**
     * @param displayLenderAlternativeAddress the
     * displayLenderAlternativeAddress to set
     */
    public void setDisplayLenderAlternativeAddress(int displayLenderAlternativeAddress) {
        this.displayLenderAlternativeAddress = displayLenderAlternativeAddress;
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
        String sPath2 = "//lender_images//";
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

    public List getCurrentLenderImage(String bid) {
        ubean.setUserAction(bid);
        return getExistingPicture();

    }

    public List getCurrentLender(String bid) {

//        System.out.println("called");
        List result = null;
//        if (ubean.getActionTaken() == null) {

        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String query = null;
        try {
            query = "FROM Lenders as b WHERE b.user_id = '" + bid + "'";
            result = session.createQuery(query).list();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error in getCurrentL");
            e.printStackTrace();

        } finally {
            tx = null;
            session = null;

        }
        return result;
    }

    public String getCurrentEditRecord(String lid) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String[] results = null;
        Lenders[] a_array = null;
        String queryString = "from Lenders where lender_id = :lid order by date_created";
        ubean.setUserAction(lid);

        try {
            result = hib.createQuery(queryString)
                    .setParameter("lid", lid)
                    .list();
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            tx = null;
            hib = null;
        }
        // Must be just one record.. will error check later..

        Lenders to_Array = (Lenders) result.get(0);

        this.setContactDescribeId(to_Array.getContactDescribeId());
        this.setOrganizationName(to_Array.getOrganizationName());
        this.setDisplayLenderOrganizationName(to_Array.getDisplayLenderOrganizationName());
        this.setOtherDescribeYourself(to_Array.getOtherDescribeYourself());
        this.setFirstName(to_Array.getFirstName());
        this.setMi(to_Array.getMi());
        this.setLastName(to_Array.getLastName());
        this.setDisplayLenderName(to_Array.getDisplayLenderName());
        this.setDisplayLenderAddress(to_Array.getDisplayLenderAddress());
        this.setHomePhone(to_Array.getHomePhone());
        this.setCellPhone(to_Array.getCellPhone());
        this.setAlternativePhone(to_Array.getAlternativePhone());
        this.setPublicDisplayHomePhone(to_Array.getPublicDisplayHomePhone());
        this.setPublicDisplayCellPhone(to_Array.getPublicDisplayCellPhone());
        this.setPublicDisplayAlternativePhone(to_Array.getPublicDisplayAlternativePhone());
        this.setUseWhichContactAddress(to_Array.getUseWhichContactAddress());
        this.setEmailAlternative(to_Array.getEmailAlternative());
        this.setBorrowerContactByEmail(to_Array.getBorrowerContactByEmail());
        this.setBorrowerContactByHomePhone(to_Array.getBorrowerContactByHomePhone());
        this.setBorrowerContactByCellPhone(to_Array.getBorrowerContactByCellPhone());
        this.setBorrowerContactByAlternativePhone(to_Array.getBorrowerContactByAlternativePhone());
        this.setBorrowerContactByFacebook(to_Array.getBorrowerContactByFacebook());
        this.setBorrowerContactByTwitter(to_Array.getBorrowerContactByTwitter());
        this.setBorrowerContactByInstagram(to_Array.getBorrowerContactByInstagram());
        this.setBorrowerContactByLinkedIn(to_Array.getBorrowerContactByLinkedIn());
        this.setBorrowerContactByOtherSocialMedia(to_Array.getBorrowerContactByOtherSocialMedia());
        this.setBorrowerContactByOtherSocialMediaAccess(to_Array.getBorrowerContactByOtherSocialMediaAccess());
        this.setCategoryId(to_Array.getCategoryId());
        this.setOtherItemCategory(to_Array.getOtherItemCategory());
        this.setItemModel(to_Array.getItemModel());
        this.setItemDescription(to_Array.getItemDescription());
        this.setItemConditionId((int) to_Array.getItemConditionId());
        this.setItemCount(to_Array.getItemCount());
        this.setGoodwill(to_Array.getGoodwill());
        this.setAge18OrMore(to_Array.getAge18OrMore());
        this.setIsActive(to_Array.getIsActive());
        this.setDateCreated(to_Array.getDateCreated());
        this.setDateUpdated(to_Array.getDateUpdated());
        this.setApproved(to_Array.getApproved());
        this.setNotifyBorrowers(to_Array.getNotifyBorrowers());
        this.setReceiveBorrowerNotifications(to_Array.getReceiveBorrowerNotifications());
        this.setIsCommunity(to_Array.getIsCommunity());
        this.setComment(to_Array.getComment());
        this.setAdvertiserId(to_Array.getAdvertiserId());
        this.setDisplayLenderAlternativeAddress(to_Array.getDisplayLenderAlternativeAddress());
        this.setForFree(to_Array.getForFree());
        this.setAvailableForPurchase(to_Array.getAvailableForPurchase());
        this.setAvailableForPurchaseAmount(to_Array.getAvailableForPurchaseAmount());
        this.setSmallFee(to_Array.getSmallFee());
        this.setSmallFeeAmount(to_Array.getSmallFeeAmount());
        this.setAvailableForDonation(to_Array.getAvailableForDonation());
        this.setDonateAnonymous(to_Array.getDonateAnonymous());
        this.setTrade(to_Array.getTrade());
        this.setTradeItem(to_Array.getTradeItem());
        this.setAgreedNumberOfDays(to_Array.getAgreedNumberOfDays());
        this.setAgreedNumberOfHours(to_Array.getAgreedNumberOfHours());
        this.setIndefiniteDuration(to_Array.getIndefiniteDuration());
        this.setPresentDuringBorrowingPeriod(to_Array.getPresentDuringBorrowingPeriod());
        this.setEntirePeriod(to_Array.getEntirePeriod());
        this.setPartialPeriod(to_Array.getPartialPeriod());
        this.setProvideProperUseTraining(to_Array.getProvideProperUseTraining());
        this.setSpecificConditions(to_Array.getSpecificConditions());
        this.setSecurityDepositAmount(to_Array.getSecurityDepositAmount());
        this.setSecurityDeposit(to_Array.getSecurityDeposit());
        this.setRemoteIp(to_Array.getRemoteIp());

        this.setAgreedThirdPartyChoiceB2l(to_Array.getAgreedThirdPartyChoiceB2l());
        this.setAgreedThirdPartyChoiceL2b(to_Array.getAgreedThirdPartyChoiceL2b());
        this.setBorrowerChoice(to_Array.getBorrowerChoice());
        this.setBorrowerContactByAlternativePhone(to_Array.getBorrowerContactByAlternativePhone());
        this.setBReturnsToWhichAddress(to_Array.getBReturnsToWhichAddress());
        this.setLenderThirdPartyChoiceB2l(to_Array.getLenderThirdPartyChoiceB2l());
        this.setLenderThirdPartyChoiceL2b(to_Array.getLenderThirdPartyChoiceL2b());
        this.setMeetBorrowerAtAgreedB2l(to_Array.getMeetBorrowerAtAgreedB2l());
        this.setMeetBorrowerAtAgreedL2b(to_Array.getMeetBorrowerAtAgreedL2b());
        this.setThirdPartyPresenceB2l(to_Array.getThirdPartyPresenceB2l());
        this.setThirdPartyPresenceL2b(to_Array.getThirdPartyPresenceL2b());
        this.setUseWhichContactAddress(to_Array.getUseWhichContactAddress());
        this.setWillDeliverToBorrowerPreferredL2b(to_Array.getWillDeliverToBorrowerPreferredL2b());
        this.setWillPickUpPreferredLocationB2l(to_Array.getWillPickUpPreferredLocationB2l());

        return "edit_lender";

    }

    private List getExistingLenderAddress(String which) {

        List result = null;
        Addresses[] a_array = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Addresses where lender_id = :lid AND address_type = :which";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("lid", ubean.getUserAction())
                    .setParameter("which", which)
                    .list();
            tx.commit();
        } catch (Exception e) {

        } finally {
            hib = null;
            tx = null;
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
//            a_array[i] = new Addresses(to_Array.getId(), to_Array.getLender_id(), to_Array.getLender_id(), to_Array.getAddressLine1(), to_Array.getAddressLine2(), to_Array.getPostalCode(), to_Array.getCity(), to_Array.getProvince(), to_Array.getUsStateId(), to_Array.getRegion(), to_Array.getCountryId(), to_Array.getAddressType());
//        }
    }

    public List getExistingPicture() {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String[] results = null;
        String queryString = "from ItemImages where lender_id = :lid ";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("lid", ubean.getUserAction())
                    .list();
            tx.commit();
        } catch (Exception e) {
        } finally {
            hib = null;
            tx = null;
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
        return getExistingLenderAddress("primary");
    }

    /**
     * @param existing_primary the existing_primary to set
     */
    public void setExisting_primary(Addresses[] existing_primary) {
        this.setExisting_primary(existing_primary);
    }

    /**
     * @return the existing_alternative
     */
    public List getExisting_alternative() {
        return getExistingLenderAddress("alternative");
    }

    /**
     * @param existing_alternative the existing_alternative to set
     */
    public void setExisting_alternative(Addresses[] existing_alternative) {
        this.setExisting_alternative(existing_alternative);
    }

    private Boolean DeleteImageFile(String fileName) {

        Boolean return_delete_true = false;
        String sPath1 = "C://Users//emm//Documents//NetBeansProjects//giving_taking//web//resources";
        String sPath2 = "//lender_images//";
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

    public String deleteCurrentRecord(String bid) {
        // Finish later
        message(
                null,
                "DeleteSelecteBorrowe",
                null);

        return "index?faces-redirect=true";
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
    public Integer getBorrowerContactByEmail() {
        return borrowerContactByEmail;
    }

    /**
     * @param borrowerContactByEmail the borrowerContactByEmail to set
     */
    public void setBorrowerContactByEmail(Integer borrowerContactByEmail) {
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
     * @return the BComesToWhichAddress
     */
    public Integer getBComesToWhichAddress() {
        return BComesToWhichAddress;
    }

    /**
     * @param BComesToWhichAddress the BComesToWhichAddress to set
     */
    public void setBComesToWhichAddress(Integer BComesToWhichAddress) {
        this.BComesToWhichAddress = BComesToWhichAddress;
    }

    /**
     * @return the agreedThirdPartyChoiceL2b
     */
    public Integer getAgreedThirdPartyChoiceL2b() {
        return agreedThirdPartyChoiceL2b;
    }

    /**
     * @param agreedThirdPartyChoiceL2b the agreedThirdPartyChoiceL2b to set
     */
    public void setAgreedThirdPartyChoiceL2b(Integer agreedThirdPartyChoiceL2b) {
        this.agreedThirdPartyChoiceL2b = agreedThirdPartyChoiceL2b;
    }

    /**
     * @return the BReturnsToWhichAddress
     */
    public Integer getBReturnsToWhichAddress() {
        return BReturnsToWhichAddress;
    }

    /**
     * @param BReturnsToWhichAddress the BReturnsToWhichAddress to set
     */
    public void setBReturnsToWhichAddress(Integer BReturnsToWhichAddress) {
        this.BReturnsToWhichAddress = BReturnsToWhichAddress;
    }

    /**
     * @return the meetBorrowerAtAgreedB2l
     */
    public Integer getMeetBorrowerAtAgreedB2l() {
        return meetBorrowerAtAgreedB2l;
    }

    /**
     * @param meetBorrowerAtAgreedB2l the meetBorrowerAtAgreedB2l to set
     */
    public void setMeetBorrowerAtAgreedB2l(Integer meetBorrowerAtAgreedB2l) {
        this.meetBorrowerAtAgreedB2l = meetBorrowerAtAgreedB2l;
    }

    /**
     * @return the willPickUpPreferredLocationB2l
     */
    public Integer getWillPickUpPreferredLocationB2l() {
        return willPickUpPreferredLocationB2l;
    }

    /**
     * @param willPickUpPreferredLocationB2l the willPickUpPreferredLocationB2l
     * to set
     */
    public void setWillPickUpPreferredLocationB2l(Integer willPickUpPreferredLocationB2l) {
        this.willPickUpPreferredLocationB2l = willPickUpPreferredLocationB2l;
    }

    /**
     * @return the thirdPartyPresenceB2l
     */
    public Integer getThirdPartyPresenceB2l() {
        return thirdPartyPresenceB2l;
    }

    /**
     * @param thirdPartyPresenceB2l the thirdPartyPresenceB2l to set
     */
    public void setThirdPartyPresenceB2l(Integer thirdPartyPresenceB2l) {
        this.thirdPartyPresenceB2l = thirdPartyPresenceB2l;
    }

    /**
     * @return the agreedThirdPartyChoiceB2l
     */
    public Integer getAgreedThirdPartyChoiceB2l() {
        return agreedThirdPartyChoiceB2l;
    }

    /**
     * @param agreedThirdPartyChoiceB2l the agreedThirdPartyChoiceB2l to set
     */
    public void setAgreedThirdPartyChoiceB2l(Integer agreedThirdPartyChoiceB2l) {
        this.agreedThirdPartyChoiceB2l = agreedThirdPartyChoiceB2l;
    }

    /**
     * @return the borrowerChoice
     */
    public Integer getBorrowerChoice() {
        return borrowerChoice;
    }

    /**
     * @param borrowerChoice the borrowerChoice to set
     */
    public void setBorrowerChoice(Integer borrowerChoice) {
        this.borrowerChoice = borrowerChoice;
    }

    /**
     * @return the forFree
     */
    public Integer getForFree() {
        return forFree;
    }

    /**
     * @param forFree the forFree to set
     */
    public void setForFree(Integer forFree) {
        this.forFree = forFree;
    }

    /**
     * @return the availableForPurchase
     */
    public Integer getAvailableForPurchase() {
        return availableForPurchase;
    }

    /**
     * @param availableForPurchase the availableForPurchase to set
     */
    public void setAvailableForPurchase(Integer availableForPurchase) {
        this.availableForPurchase = availableForPurchase;
    }

    /**
     * @return the availableForPurchaseAmount
     */
    public BigDecimal getAvailableForPurchaseAmount() {
        return availableForPurchaseAmount;
    }

    /**
     * @param availableForPurchaseAmount the availableForPurchaseAmount to set
     */
    public void setAvailableForPurchaseAmount(BigDecimal availableForPurchaseAmount) {
        this.availableForPurchaseAmount = availableForPurchaseAmount;
    }

    /**
     * @return the smallFee
     */
    public Integer getSmallFee() {
        return smallFee;
    }

    /**
     * @param smallFee the smallFee to set
     */
    public void setSmallFee(Integer smallFee) {
        this.smallFee = smallFee;
    }

    /**
     * @return the smallFeeAmount
     */
    public BigDecimal getSmallFeeAmount() {
        return smallFeeAmount;
    }

    /**
     * @param smallFeeAmount the smallFeeAmount to set
     */
    public void setSmallFeeAmount(BigDecimal smallFeeAmount) {
        this.smallFeeAmount = smallFeeAmount;
    }

    /**
     * @return the availableForDonation
     */
    public Integer getAvailableForDonation() {
        return availableForDonation;
    }

    /**
     * @param availableForDonation the availableForDonation to set
     */
    public void setAvailableForDonation(Integer availableForDonation) {
        this.availableForDonation = availableForDonation;
    }

    /**
     * @return the donateAnonymous
     */
    public Integer getDonateAnonymous() {
        return donateAnonymous;
    }

    /**
     * @param donateAnonymous the donateAnonymous to set
     */
    public void setDonateAnonymous(Integer donateAnonymous) {
        this.donateAnonymous = donateAnonymous;
    }

    /**
     * @return the trade
     */
    public Integer getTrade() {
        return trade;
    }

    /**
     * @param trade the trade to set
     */
    public void setTrade(Integer trade) {
        this.trade = trade;
    }

    /**
     * @return the tradeItem
     */
    public String getTradeItem() {
        return tradeItem;
    }

    /**
     * @param tradeItem the tradeItem to set
     */
    public void setTradeItem(String tradeItem) {
        this.tradeItem = tradeItem;
    }

    /**
     * @return the agreedNumberOfDays
     */
    public Integer getAgreedNumberOfDays() {
        return agreedNumberOfDays;
    }

    /**
     * @param agreedNumberOfDays the agreedNumberOfDays to set
     */
    public void setAgreedNumberOfDays(Integer agreedNumberOfDays) {
        this.agreedNumberOfDays = agreedNumberOfDays;
    }

    /**
     * @return the agreedNumberOfHours
     */
    public Integer getAgreedNumberOfHours() {
        return agreedNumberOfHours;
    }

    /**
     * @param agreedNumberOfHours the agreedNumberOfHours to set
     */
    public void setAgreedNumberOfHours(Integer agreedNumberOfHours) {
        this.agreedNumberOfHours = agreedNumberOfHours;
    }

    /**
     * @return the indefiniteDuration
     */
    public Integer getIndefiniteDuration() {
        return indefiniteDuration;
    }

    /**
     * @param indefiniteDuration the indefiniteDuration to set
     */
    public void setIndefiniteDuration(Integer indefiniteDuration) {
        this.indefiniteDuration = indefiniteDuration;
    }

    /**
     * @return the presentDuringBorrowingPeriod
     */
    public Integer getPresentDuringBorrowingPeriod() {
        return presentDuringBorrowingPeriod;
    }

    /**
     * @param presentDuringBorrowingPeriod the presentDuringBorrowingPeriod to
     * set
     */
    public void setPresentDuringBorrowingPeriod(Integer presentDuringBorrowingPeriod) {
        this.presentDuringBorrowingPeriod = presentDuringBorrowingPeriod;
    }

    /**
     * @return the entirePeriod
     */
    public Integer getEntirePeriod() {
        return entirePeriod;
    }

    /**
     * @param entirePeriod the entirePeriod to set
     */
    public void setEntirePeriod(Integer entirePeriod) {
        this.entirePeriod = entirePeriod;
    }

    /**
     * @return the partialPeriod
     */
    public Integer getPartialPeriod() {
        return partialPeriod;
    }

    /**
     * @param partialPeriod the partialPeriod to set
     */
    public void setPartialPeriod(Integer partialPeriod) {
        this.partialPeriod = partialPeriod;
    }

    /**
     * @return the provideProperUseTraining
     */
    public Integer getProvideProperUseTraining() {
        return provideProperUseTraining;
    }

    /**
     * @param provideProperUseTraining the provideProperUseTraining to set
     */
    public void setProvideProperUseTraining(Integer provideProperUseTraining) {
        this.provideProperUseTraining = provideProperUseTraining;
    }

    /**
     * @return the specificConditions
     */
    public String getSpecificConditions() {
        return specificConditions;
    }

    /**
     * @param specificConditions the specificConditions to set
     */
    public void setSpecificConditions(String specificConditions) {
        this.specificConditions = specificConditions;
    }

    /**
     * @return the displayLenderOrganizationName
     */
    public Integer getDisplayLenderOrganizationName() {
        return displayLenderOrganizationName;
    }

    /**
     * @param displayLenderOrganizationName the displayLenderOrganizationName to
     * set
     */
    public void setDisplayLenderOrganizationName(Integer displayLenderOrganizationName) {
        this.displayLenderOrganizationName = displayLenderOrganizationName;
    }

    /**
     * @return the notifyBorrowers
     */
    public Integer getNotifyBorrowers() {
        return notifyBorrowers;
    }

    /**
     * @param notifyBorrowers the notifyBorrowers to set
     */
    public void setNotifyBorrowers(Integer notifyBorrowers) {
        this.notifyBorrowers = notifyBorrowers;
    }

    /**
     * @return the receiveBorrowerNotifications
     */
    public Integer getReceiveBorrowerNotifications() {
        return receiveBorrowerNotifications;
    }

    /**
     * @param receiveBorrowerNotifications the receiveBorrowerNotifications to
     * set
     */
    public void setReceiveBorrowerNotifications(Integer receiveBorrowerNotifications) {
        this.receiveBorrowerNotifications = receiveBorrowerNotifications;
    }

    /**
     * @param itemConditionId the itemConditionId to set
     */
    public void setItemConditionId(int itemConditionId) {
        this.itemConditionId = itemConditionId;
    }

    /**
     * @return the securityDepositAmount
     */
    public BigDecimal getSecurityDepositAmount() {
        return securityDepositAmount;
    }

    /**
     * @param securityDepositAmount the securityDepositAmount to set
     */
    public void setSecurityDepositAmount(BigDecimal securityDepositAmount) {
        this.securityDepositAmount = securityDepositAmount;
    }

    /**
     * @return the securityDeposit
     */
    public Integer getSecurityDeposit() {
        return securityDeposit;
    }

    /**
     * @param securityDeposit the securityDeposit to set
     */
    public void setSecurityDeposit(Integer securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    /**
     * @return the displayLenderName
     */
    public int getDisplayLenderName() {
        return displayLenderName;
    }

    /**
     * @param displayLenderName the displayLenderName to set
     */
    public void setDisplayLenderName(int displayLenderName) {
        this.displayLenderName = displayLenderName;
    }

    /**
     * @return the displayLenderAddress
     */
    public int getDisplayLenderAddress() {
        return displayLenderAddress;
    }

    /**
     * @param displayLenderAddress the displayLenderAddress to set
     */
    public void setDisplayLenderAddress(int displayLenderAddress) {
        this.displayLenderAddress = displayLenderAddress;
    }

    /**
     * @param publicDisplayAlternativePhone the publicDisplayAlternativePhone to
     * set
     */
    public void setPublicDisplayAlternativePhone(Integer publicDisplayAlternativePhone) {
        this.publicDisplayAlternativePhone = publicDisplayAlternativePhone;
    }

    /**
     * @return the meetBorrowerAtAgreedL2B
     */
    public Integer getMeetBorrowerAtAgreedL2b() {
        return meetBorrowerAtAgreedL2b;
    }

    /**
     * @param meetBorrowerAtAgreedL2B the meetBorrowerAtAgreedL2B to set
     */
    public void setMeetBorrowerAtAgreedL2b(Integer meetBorrowerAtAgreedL2B) {
        this.meetBorrowerAtAgreedL2b = meetBorrowerAtAgreedL2B;
    }

    /**
     * @return the willDeliverToBorrowerPreferredL2B
     */
    public Integer getWillDeliverToBorrowerPreferredL2b() {
        return willDeliverToBorrowerPreferredL2b;
    }

    /**
     * @param willDeliverToBorrowerPreferredL2B the
     * willDeliverToBorrowerPreferredL2B to set
     */
    public void setWillDeliverToBorrowerPreferredL2b(Integer willDeliverToBorrowerPreferredL2B) {
        this.willDeliverToBorrowerPreferredL2b = willDeliverToBorrowerPreferredL2B;
    }

    /**
     * @return the thirdPartyPresenceL2b
     */
    public Integer getThirdPartyPresenceL2b() {
        return thirdPartyPresenceL2b;
    }

    /**
     * @param thirdPartyPresenceL2b the thirdPartyPresenceL2b to set
     */
    public void setThirdPartyPresenceL2b(Integer thirdPartyPresenceL2b) {
        this.thirdPartyPresenceL2b = thirdPartyPresenceL2b;
    }

    /**
     * @return the lenderThirdPartyChoiceB2l
     */
    public Integer getLenderThirdPartyChoiceB2l() {
        return lenderThirdPartyChoiceB2l;
    }

    /**
     * @param lenderThirdPartyChoiceB2l the lenderThirdPartyChoiceB2l to set
     */
    public void setLenderThirdPartyChoiceB2l(Integer lenderThirdPartyChoiceB2l) {
        this.lenderThirdPartyChoiceB2l = lenderThirdPartyChoiceB2l;
    }

    /**
     * @return the lenderThirdPartyChoiceL2b
     */
    public Integer getLenderThirdPartyChoiceL2b() {
        return lenderThirdPartyChoiceL2b;
    }

    /**
     * @param lenderThirdPartyChoiceL2b the lenderThirdPartyChoiceL2b to set
     */
    public void setLenderThirdPartyChoiceL2b(Integer lenderThirdPartyChoiceL2b) {
        this.lenderThirdPartyChoiceL2b = lenderThirdPartyChoiceL2b;
    }

    /// 
    private Integer getNine(Integer option) {

        if (option == null) {
            return -9;
        } else {
            return option;
        }
    }
    
    public String deleteCurrentRecord(String lid, String itemDesc) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Lenders where lender_id = :lid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("lid", lid)
                    .list();
            
            if (result.size() > 0) {
                
                hib.delete((Lenders) result.get(0));
                tx.commit();
            } else {
            }

        } catch (Exception ex) {
            System.out.println("Error in deleting lender record");
            Logger.getLogger(LenderBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            result = null;
            tx = null;
            hib = null;

            message(
                    null,
                    "DeleteSelecteLender",
                    new Object[]{itemDesc});
        }
        return "lender_history";
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
