package echomarket.web.managedbeans;

import echomarket.hibernate.CommunityMembers;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "communityMembersBean")
@RequestScoped
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
    private Boolean editable;
    private String editWhichRecord;
    private CommunityMembers[] existing_member; 

    private List getExistingMember() {

        List result = null;
        CommunityMembers[] a_array = null;
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;

        queryString = "FROM CommunityMembers where community_member_id = :cid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", this.editWhichRecord)
                    .list();
            tx.commit();

        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            tx = null;
        }

         return result;
        }


    public void editAction(CommunityMembers cmid) {

        this.editWhichRecord = cmid.getCommunity_member_id();

    }

    public String addAction() {

        return "index";
        //this.editWhichRecord = cmid;
        //return "community_members.xhtml?faces-redirect=true";
    }

    public String goToIndex() {

        return "index";
    }

    public String getCName() {

        return ubean.getCommunityName();
    }

    public String saveMember() {
        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        Date today_date = new Date();
//        String newMemberName = cm.getFirstName() + " " + cm.getLastName();
//        CommunityMembers comm = new CommunityMembers(getId(), ubean.getUser_id(), "NA", cm.getFirstName(), "NA", cm.getLastName(), cm.getAlias(), cm.getIsActive(), today_date, today_date, 0);

//        try {
//            sb.update(comm);
//            tx.commit();
//
//            message(
//                    null,
//                    "NewMemberRecordSaved",
//                    new Object[]{newMemberName});
//        } catch (Exception ex) {
//            Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
//            message(
//                    null,
//                    "NewMemberRecordWasNotSaved",
//                     new Object[]{newMemberName});
//        }
        sb = null;
        tx = null;

        return null;
    }

//    public List editCurrentMemberList() {
//        Session hib = hib_session();
//        Transaction tx = hib.beginTransaction();
//        String queryString = null;
//        List result = null;
//
////        if (ubean.getIsCommunity() == 1) {
//        queryString = "FROM CommunityMembers where community_member_id = :cid";
//        try {
//            result = hib.createQuery(queryString)
//                    .setParameter("cid", this.editWhichRecord)
//                    .list();
//            tx.commit();
//
//        } catch (Exception ex) {
//            Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
//
//        } finally {
//            tx = null;
//        }
////        }
//        List<CommunityMembers> cm_a = result.get(0);
//        setExisting_member(cm_a);
//        return result;
//
//    }

    public List buildCommunityMembersList() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;
        List result = null;

//        if (ubean.getIsCommunity() == 1) {
        queryString = "FROM CommunityMembers where community_id = :cid";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", ubean.getUser_id())
                    .list();
            tx.commit();

        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            tx = null;
        }
//        }
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
    public Boolean getEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(Boolean editable) {
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
    public List getExisting_alternative() {
        return getExistingMember();
    }

    /**
     * @param existing_alternative the existing_alternative to set
     */
    public void setExisting_alternative(CommunityMembers[] cm) {
        this.existing_member = cm;
    }

}
