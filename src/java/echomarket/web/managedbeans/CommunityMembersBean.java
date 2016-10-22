package echomarket.web.managedbeans;

import echomarket.hibernate.Communities;
import echomarket.hibernate.CommunityMembers;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "communityMembersBean")
@SessionScoped
public class CommunityMembersBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    private String community_member_id;
    private String community_id;
    private String remoteIp;
    private String firstName;
    private String mi;
    private String lastName;
    private String alias;
    private Integer isActive;
    private Integer isCreator;
    private String email;
    private Integer editable;
    private String editWhichRecord;
    private CommunityMembers[] existing_member;
    private String isActive_boolean;

    private String efirstName;
    private String elastName;
    private String ealias;
    private Integer eisActive;
    private String eemail;

    private List getExistingMemberList() {

        List result = null;
        CommunityMembers[] a_array = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;

        queryString = "FROM CommunityMembers where community_member_id = :cid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", this.getEditWhichRecord())
                    .list();
            tx.commit();

        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
            }
        }

        return result;
    }

    public String load_community_members() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;
        List result = null;

        queryString = "FROM Communities where community_id = :cid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", ubean.getUser_id())
                    .list();

            if (result.size() > 0) {
//                For each record in result set editable to 0
//               comm_Array = (Communities) result.get(0);
//               queryString = comm_Array.getCommunityName();
            } else {
            }
        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
            }
        }

//        this.communityName = queryString;
//        return "community_members.xhtml?faces-redirect=true";
        this.editable = 0;
        return "community_members.xhtml?faces-redirect=true";
    }

    public String getCName() {

        return ubean.getCommunityName();
    }

    public void editAction(CommunityMembers cmid) {

        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        cmid.setEditable(1);
        this.firstName = cmid.getFirstName();
        this.lastName = cmid.getLastName();
        this.alias = cmid.getAlias();
        this.community_member_id = cmid.getCommunity_member_id();
        this.email = cmid.getEmail();

        if (cmid.getIsActive() == 1) {
            setIsActive_boolean("true");
        } else {
            setIsActive_boolean("false");
        }

        try {
            hib.update(cmid);
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
            }
        }

        //http://stackoverflow.com/questions/8744162/difference-between-returning-null-and-from-a-jsf-action
        //return "";
    }

    public void addAction() {
        this.editable = 3;
        this.efirstName = null;
        this.elastName = null;
        this.ealias = null;
        this.eemail = null;
        this.eisActive = 1;
        this.isActive_boolean = "true";
        //return "community_members";

    }

    public void saveAction() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        Date today_date = new Date();
        String newMemberName = getEfirstName() + " " + getElastName();
        String asd = this.isActive_boolean;
        Integer holdia = 0;
        if ("true".equals(asd)) {
            holdia = 1;
        }
        CommunityMembers comm = new CommunityMembers(getId(), ubean.getUser_id(), ubean.getUser_id(), "NA", getEfirstName(), getElastName(), getEalias(), getEemail(), holdia, today_date, today_date, 0);

        try {
            hib.save(comm);
            tx.commit();

            message(
                    null,
                    "NewMemberRecordSaved",
                    new Object[]{newMemberName});

        } catch (Exception ex) {
            Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, "COMMUNITY MEMEBER NOT SAVED", ex);
            message(
                    null,
                    "NewMemberRecordWasNotSaved",
                    new Object[]{newMemberName});
        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
            }
        }

        this.editable = -1;
        //return "community_members.xhtml?faces-redirect=true";

    }

    public void updateAction(CommunityMembers cm) {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        Date today_date = new Date();
        String newMemberName = getFirstName() + " " + getLastName();
        String asd = this.isActive_boolean;
        Integer holdia = 0;
        if ("true".equals(asd)) {
            holdia = 1;
        }
        CommunityMembers comm = new CommunityMembers(cm.getCommunity_member_id(), cm.getCommunity_id(), cm.getUser_id(), "NA", getFirstName(), getLastName(), getAlias(), getEmail(), holdia, today_date, today_date, 0);

        try {
            hib.update(comm);
            tx.commit();

            message(
                    null,
                    "NewMemberRecordSaved",
                    new Object[]{newMemberName});

        } catch (Exception ex) {
            Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, "COMMUNITY MEMEBER NOT SAVED", ex);
            message(
                    null,
                    "NewMemberRecordWasNotSaved",
                    new Object[]{newMemberName});
        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
            }
        }

        //return "community_members.xhtml?faces-redirect=true";
    }

    public List buildCommunityMembersList() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;
        List result = null;

        queryString = "FROM CommunityMembers where community_id = :cid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", ubean.getUser_id())
                    .list();
            tx.commit();

        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, "ERROR IN build Community Member list", ex);
            System.out.println("ERROR IN build Community Member list");
            System.out.println(ex);

        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
            }
        }

        return result;

    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the mi
     */
    public String getMi() {
        return mi;
    }

    /**
     * @param mi the mi to set
     */
    public void setMi(String mi) {
        this.mi = mi;
    }

    /**
     * @return the lastName
     */
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the community_id
     */
    public String getCommunity_id() {
        return community_id;
    }

    /**
     * @param community_id the community_id to set
     */
    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
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

    /**
     * @return the existing_alternative
     */
    public List getExisting_member() {
        return getExistingMemberList();
    }

    /**
     * @param existing_alternative the existing_alternative to set
     */
    public void setExisting_member(CommunityMembers[] cm) {
        this.setExisting_member(cm);
    }

    /**
     * @return the editWhichRecord
     */
    public String getEditWhichRecord() {
        return editWhichRecord;
    }

    /**
     * @param editWhichRecord the editWhichRecord to set
     */
    public void setEditWhichRecord(String editWhichRecord) {
        this.editWhichRecord = editWhichRecord;
    }

    /**
     * @return the isActive_boolean
     */
    public String getIsActive_boolean() {
        return isActive_boolean;
    }

    /**
     * @param isActive_boolean the isActive_boolean to set
     */
    public void setIsActive_boolean(String isActive_boolean) {
        this.isActive_boolean = isActive_boolean;
    }

    /**
     * @return the efirstName
     */
    public String getEfirstName() {
        return efirstName;
    }

    /**
     * @param efirstName the efirstName to set
     */
    public void setEfirstName(String efirstName) {
        this.efirstName = efirstName;
    }

    /**
     * @return the elastName
     */
    public String getElastName() {
        return elastName;
    }

    /**
     * @param elastName the elastName to set
     */
    public void setElastName(String elastName) {
        this.elastName = elastName;
    }

    /**
     * @return the ealias
     */
    public String getEalias() {
        return ealias;
    }

    /**
     * @param ealias the ealias to set
     */
    public void setEalias(String ealias) {
        this.ealias = ealias;
    }

    /**
     * @return the eisActive
     */
    public Integer getEisActive() {
        return eisActive;
    }

    /**
     * @param eisActive the eisActive to set
     */
    public void setEisActive(Integer eisActive) {
        this.eisActive = eisActive;
    }

    /**
     * @return the eemail
     */
    public String getEemail() {
        return eemail;
    }

    /**
     * @param eemail the eemail to set
     */
    public void setEemail(String eemail) {
        this.eemail = eemail;
    }

}
