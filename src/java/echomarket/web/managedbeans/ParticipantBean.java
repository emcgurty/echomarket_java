package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Participant;
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
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "participant")
@RequestScoped
public class ParticipantBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    private String participantId;
    private int contactDescribeId;
    private String organizationName;
    private int displayOrganization;
    private String otherDescribeYourself;
    private String firstName;
    private String mi;
    private String lastName;
    private String alias;
    private int displayName;
    private int displayAddress;
    private String homePhone;
    private String cellPhone;
    private String alternativePhone;
    private String emailAlternative;
    private Integer displayHomePhone;
    private Integer displayCellPhone;
    private Integer displayAlternativePhone;
    private Integer displayAlternativeAddress;
    private Integer goodwill;
    private Integer age18OrMore;
    private Integer isActive;
    private Integer editable;
    private Integer isCreator;
    private Date dateCreated;
    private Date dateUpdated;
    private Date dateDeleted;
    private String remoteIp;
    private int approved;
    private String processId;

    private static ArrayList<Addresses> primary
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(), null, null, null, null, null, null, null, null, null, "primary")));

    private static ArrayList<Addresses> alternative
            = new ArrayList<Addresses>(Arrays.asList(new Addresses(UUID.randomUUID().toString(), null, null, null, null, null, null, null, null, null, "alternative")));

    private Addresses[] existing_primary;
    private Addresses[] existing_alternative;

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

    public Boolean notifyLenders() {
        // Not complete
        return true;
    }

    public String load_ud(Integer which) {

        ubean.setEditable(which);

        return "user_detail";
    }

    public String getUserDefinedAlternativeEmail(String pid) {
        Session sb;
        Transaction tx;
        sb = null;
        tx = null;
        List result = null;
        String alt_email = null;
        sb = hib_session();
        tx = sb.beginTransaction();

        try {
            String query = "FROM Participant WHERE user_id = :pid ";
            result = sb.createQuery(query)
                    .setParameter("pid", pid)
                    .list();
            tx.commit();
            if (result.size() > 0) {
                Participant part = (Participant) result.get(0);
                alt_email = part.getEmailAlternative();
            }
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error in getCurrentB");
            e.printStackTrace();
            System.out.println("IS TX STILL ACTIVE 110");
            System.out.println(tx.isActive());
            System.out.println("IS TX STILL ACTIVE 110 - close");

        } finally {
            System.out.println("IS TX STILL ACTIVE 116");
            System.out.println(tx.isActive());
            System.out.println("IS TX STILL ACTIVE 116 - close");
            tx = null;
            sb = null;

        }

        if (alt_email == null) {
            return "Not provided";
        } else {
            return alt_email;
        }
    }

    public String updateNAE() {

        List padrs = getPrimary();
        List aadrs = getAlternative();
        String reqPO = null;
        Session sb;
        Transaction tx;
        sb = null;
        tx = null;
        sb = hib_session();
        tx = sb.beginTransaction();

        Participant part = new Participant(participantId, null, contactDescribeId, organizationName, displayOrganization, otherDescribeYourself, firstName, mi, lastName, "NA", displayName, displayAddress, homePhone, cellPhone,
                alternativePhone, emailAlternative, displayHomePhone, displayCellPhone, displayAlternativePhone, displayAlternativeAddress, "NA");

        try {
            sb.update(part);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error in Save/Update Particpant");
            Logger.getLogger(ParticipantBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (sb != null) {
                sb.close();
            }

            Addresses balt = (Addresses) aadrs.get(0);
            reqPO = balt.getPostalCode();
            if (reqPO != null) {
                balt.setParticipantId(getParticipantId());

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
        }

        Addresses ba = (Addresses) padrs.get(0);
        reqPO = ba.getPostalCode();
        if (reqPO != null) {
            ba.setParticipantId(getParticipantId());
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
        }
        sb = null;
        tx = null;
        ubean.setEditable(3);
        return "user_detail";

    }

    public String userAgreement() {

        if ((goodwill != 1) || (age18OrMore != 1)) {
            message(
                    null,
                    "threeStrikesYourOut",
                    null);
            ubean.setEditable(-1);

        } else {
            Session sb = hib_session();
            Transaction tx = sb.beginTransaction();

            Participant part = new Participant(getId(), ubean.getUser_id(), goodwill, age18OrMore, 1, new Date(), "NA");
            try {
                sb.save(part);
                tx.commit();
            } catch (Exception ex) {
                tx.rollback();
                System.out.println("Error in Save/Update Particpant");
                Logger.getLogger(ParticipantBean.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                sb = null;
                tx = null;
                ubean.setEditable(0);

            }
        }

        return "user_detail";
    }

    private Boolean processAddress(Addresses[] address) {

        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        Boolean b_return = false;
        try {
            sb.update(address);
            tx.commit();
            b_return = true;
        } catch (Exception ex) {
            tx.rollback();
            Logger
                    .getLogger(ParticipantBean.class
                            .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (sb != null) {
                sb.close();
            }
            tx = null;
            sb = null;

        }

        return b_return;
    }

    public String saveParticipantEdit() {

        List result = null;
        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        Date today_date = new Date();
        String current_user = null;
        Boolean bret = false;

        try {
            current_user = ubean.getUser_id();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /// Need to implement onChange Listener to learn if dirty??
        Participant bb = new Participant();

        try {
            sb.update(bb);
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error on Update Participant");
            Logger
                    .getLogger(ParticipantBean.class
                            .getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (sb != null) {
                sb.close();
            }

            bret = processAddress(this.existing_alternative);
            bret = processAddress(this.existing_primary);
        }

        if (bret == true) {
            tx = null;
            sb = null;
            message(
                    null,
                    "ParticipantRecordUpdated",
                    null);
        } else {
            message(
                    null,
                    "ParticipantRecordNotUpdated",
                    null);

        }

        return "index?faces-redirect=true";

    }

    public String saveParticipantRegistration() {

        List padrs = getPrimary();
        List aadrs = getAlternative();
        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        Date today_date = new Date();
        String getAbstId = getId();
        String current_user = null;
        try {

            current_user = ubean.getUser_id();

        } catch (Exception e) {
            System.out.println("Testing inject vs session Map");
            e.printStackTrace();
        }
        Participant bb = new Participant();

        sb.save(bb);
        try {
            tx.commit();
        } catch (Exception e) {
        }

        Addresses balt = (Addresses) aadrs.get(0);
        balt.setParticipantId(getAbstId);

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

        Addresses ba = (Addresses) padrs.get(0);
        ba.setParticipantId(getAbstId);

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
                    "ParticipantRegistionRecordSaved",
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
        if ((organizationName != null) || (ubean.getEditable() == 3)) {
            return organizationName;
        } else {
            return "Not provided";
        }

    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOtherDescribeYourself() {
        if ((otherDescribeYourself != null) || (ubean.getEditable() == 3)) {
            return otherDescribeYourself;
        } else {
            return "Not provided";
        }
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
        if (mi == null) {
            return " ";
        } else {
            return mi;
        }
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
     * @return the emailAlternative
     */
    public String getEmailAlternative() {
//        if ((emailAlternative != null) || (ubean.getEditable() == 8)) {
            return emailAlternative;
//        } else {
//            return "Not provided";
//        }

    }

    /**
     * @param emailAlternative the emailAlternative to set
     */
    public void setEmailAlternative(String emailAlternative) {
        this.emailAlternative = emailAlternative;
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

    private static Date hold_date() {
        Date hold_date = new Date();
        return hold_date;

    }

    public List getCurrentParticipant(String bid) {
        System.out.println("getCurrent Participant Called");
        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String query = null;
        try {
            query = "FROM Participant as b WHERE b.user_id = '" + bid + "'";
            result = session.createQuery(query).list();

        } catch (Exception e) {
            System.out.println("Error in getCurrentB");
            e.printStackTrace();

        } finally {
            tx = null;
            session = null;

        }

        return result;
    }

    public void getCurrentEditRecord(String bid) {

        this.setEditable((Integer) 0);

    }

    private List getExistingParticipantAddress(String which) {

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

    public String deleteCurrentRecord(String bid, String itemDesc) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Participant where borrower_id = :bid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", bid)
                    .list();

            if (result.size() > 0) {

                hib.delete((Participant) result.get(0));
                tx.commit();
            } else {
            }

        } catch (Exception ex) {
            System.out.println("Error in deleting borrower record");
            Logger
                    .getLogger(ParticipantBean.class
                            .getName()).log(Level.SEVERE, null, ex);
        } finally {

            result = null;
            tx = null;
            hib = null;

            message(
                    null,
                    "DeleteSelecteParticipant",
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

    public String load_borrower_seeking() {

        return "borrower_seeking.xhtml";
    }

    /**
     * @return the displayOrganization
     */
    public int getDisplayOrganization() {
        return displayOrganization;
    }

    /**
     * @param displayOrganization the displayOrganization to set
     */
    public void setDisplayOrganization(int displayOrganization) {
        this.displayOrganization = displayOrganization;
    }

    /**
     * @return the displayName
     */
    public int getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(int displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the displayAddress
     */
    public int getDisplayAddress() {
        return displayAddress;
    }

    /**
     * @param displayAddress the displayAddress to set
     */
    public void setDisplayAddress(int displayAddress) {
        this.displayAddress = displayAddress;
    }

    /**
     * @return the displayHomePhone
     */
    public Integer getDisplayHomePhone() {
        return displayHomePhone;
    }

    /**
     * @param displayHomePhone the displayHomePhone to set
     */
    public void setDisplayHomePhone(Integer displayHomePhone) {
        this.displayHomePhone = displayHomePhone;
    }

    /**
     * @return the displayCellPhone
     */
    public Integer getDisplayCellPhone() {
        return displayCellPhone;
    }

    /**
     * @param displayCellPhone the displayCellPhone to set
     */
    public void setDisplayCellPhone(Integer displayCellPhone) {
        this.displayCellPhone = displayCellPhone;
    }

    /**
     * @return the displayAlternativePhone
     */
    public Integer getDisplayAlternativePhone() {
        return displayAlternativePhone;
    }

    /**
     * @param displayAlternativePhone the displayAlternativePhone to set
     */
    public void setDisplayAlternativePhone(Integer displayAlternativePhone) {
        this.displayAlternativePhone = displayAlternativePhone;
    }

    /**
     * @param displayAlternativeAddress the displayAlternativeAddress to set
     */
    public void setDisplayAlternativeAddress(Integer displayAlternativeAddress) {
        this.displayAlternativeAddress = displayAlternativeAddress;
    }

    /**
     * @return the editable
     */
    public Integer getEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    /**
     * @return the isCreator
     */
    public Integer getIsCreator() {
        return isCreator;
    }

    /**
     * @param isCreator the isCreator to set
     */
    public void setIsCreator(Integer isCreator) {
        this.isCreator = isCreator;
    }

    public List getExisting_primary() {
        return getExistingAddress("primary");
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
        return getExistingAddress("alternative");
    }

    /**
     * @param existing_alternative the existing_alternative to set
     */
    public void setExisting_alternative(Addresses[] existing_alternative) {
        this.existing_alternative = existing_alternative;
    }

    private List getExistingAddress(String which) {

        List result = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();

        String queryString = "from Addresses where participant_id = :bid AND address_type = :which";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("bid", ubean.getUserAction())
                    .setParameter("which", which)
                    .list();

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

    }

    /**
     * @return the displayAlternativeAddress
     */
    public Integer getDisplayAlternativeAddress() {
        return displayAlternativeAddress;
    }

    /**
     * @return the participantId
     */
    @Id
    public String getParticipantId() {
        return participantId;
    }

    /**
     * @param participantId the participantId to set
     */
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

}
