package echomarket.web.managedbeans;

import echomarket.hibernate.Communities;
import echomarket.hibernate.CommunityMembers;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private ArrayList<CommunityMembers> comm_member_rows;
    private String[] efirstName;
    private String[] elastName;
    private String[] ealias;
    private Integer[] eisActive;
    private String[] eemail;
    private Integer howManyRecords;

    private static Date hold_date() {
        Date hold_date = new Date();
        return hold_date;
    }

    private List getExistingMemberList() {

        ArrayList<CommunityMembers> comm_member = new ArrayList<CommunityMembers>();
        Integer howMany = getHowManyRecords();
        CommunityMembers new_cm;
        for (int i = 0; i < howMany; i++) {

            new_cm = new CommunityMembers(UUID.randomUUID().toString(), ubean.getUser_id(), ubean.getUser_id(), null, null, null, null, 1, hold_date(), hold_date(), 0, i);

            comm_member.add(new_cm);
        }
        this.comm_member_rows = comm_member;
        return comm_member;

    }

    public CommunityMembers[] listCMAlias() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = "from CommunityMembers where community_id = :cid order by alias";
        List result = hib.createQuery(queryString)
                .setParameter("cid", ubean.getUser_id())
                .list();
        tx.commit();
        CommunityMembers[] cmArray = null;
        Integer size_of_result = result.size();
        cmArray = new CommunityMembers[size_of_result];
        for (int i = 0; i < size_of_result; i++) {
            CommunityMembers to_Array = (CommunityMembers) result.get(i);
            cmArray[i] = new CommunityMembers(to_Array.getUser_id(), to_Array.getAlias());
        }
        return cmArray;
    }

    public String load_community_members() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;
        List result = null;
        Boolean isCreatorRights = false;

        queryString = "FROM CommunityMembers where community_id = :cid AND is_creator = 1";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", ubean.getUser_id())
                    .list();
            /// Don't understand why I have to use commit??
            tx.commit();

        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
            // return with message
        } finally {

            if (result.size() > 0) {

                // Then person has right privs
                if (hib.isOpen() == false) {
                    hib = hib_session();
                }
                if (tx.isActive() == false) {
                    tx = hib.beginTransaction();
                }

                // In case some user didn't finish an update
                queryString = "FROM CommunityMembers where community_id = :cid";
                try {
                    result = hib.createQuery(queryString)
                            .setParameter("cid", ubean.getUser_id())
                            .list();
                    for (int i = 0; i < result.size(); i++) {
                        CommunityMembers cm = (CommunityMembers) result.get(i);
                        if (cm.getEditable() == 1) {
                            cm.setEditable(0);
                            hib.update(cm);
                        }
                    }
                    tx.commit();

                } catch (Exception ex) {
                    Logger.getLogger(CommunityMembersBean.class.getName()).log(Level.SEVERE, null, ex);
                } finally {

                    if (hib.isOpen() == true) {
                        hib.close();
                    }
                    if (tx.isActive() == true) {
                        tx = null;
                        try {
                            hib = null;
                        } catch (Exception ex) {
                        }
                    }

                    isCreatorRights = true;
                }
            }

            if (isCreatorRights == true) {
                this.editable = 0;
                tx = null;
                try {
                    hib = null;
                } catch (Exception ex) {
                }
                return "community_members.xhtml?faces-redirect=true";
            } else {
                message(
                        null,
                        "MustBeCommunityCreatorAddMember",
                        null);
                return "index";

            }
        }
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
                try {
                    hib = null;
                } catch (Exception ex) {
                }
            }
        }

        //http://stackoverflow.com/questions/8744162/difference-between-returning-null-and-from-a-jsf-action
        //return "";
    }

    public void addAction() {
        this.editable = 3;
        //this.isActive_boolean = "true";
    }

    public void saveAction() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        List new_rows = getComm_member_rows();
        CommunityMembers comm;
        String new_uuid = null;

        for (int i = 0; i < new_rows.size(); i++) {
            CommunityMembers cm = (CommunityMembers) new_rows.get(i);
            new_uuid = UUID.randomUUID().toString();
            /// Will eventually use Hibernate to check for duplicates.
            if (cm.getAlias() != null && cm.getEmail() != null && cm.getFirstName() != null && cm.getLastName() != null) {

                comm = new CommunityMembers(new_uuid, ubean.getUser_id(), new_uuid, "NA", cm.getFirstName(), cm.getLastName(), cm.getAlias(), cm.getEmail(), cm.getIsActive(), hold_date(), hold_date(), cm.getIsCreator());

                try {
                    hib.save(comm);
                    tx.commit();
                    System.out.println("COMMUNITY MEMEBER SAVED" + new_uuid);

                } catch (Exception ex) {
                    Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, "COMMUNITY MEMEBER NOT SAVED" + new_uuid, ex);

                } finally {

                    if (hib.isOpen() == false) {
                        hib = hib_session();
                    }
                    if (tx.isActive() == false) {
                        tx = hib.beginTransaction();
                    }

                }

            }
        }

        if (hib.isOpen() == true) {
            hib.close();
        }
        if (tx.isActive() == true) {
            tx = null;
            try {
                hib = null;
            } catch (Exception ex) {
            }
        }

        this.editable = 0;

    }

    public void cancelAction() {

        load_community_members();
    }

    public void updateAction(CommunityMembers cm) {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        Date today_date = new Date();
        String newMemberName = getFirstName() + " " + getLastName();

        CommunityMembers comm = new CommunityMembers(cm.getCommunity_member_id(), cm.getCommunity_id(), cm.getUser_id(), "NA", getFirstName(), getLastName(), getAlias(), getEmail(), getIsActive(), getIsCreator(), cm.getDateCreated());

        try {
            hib.update(comm);
            tx.commit();

            message(
                    null,
                    "MemberRecordUpdated",
                    new Object[]{newMemberName});

        } catch (Exception ex) {
            Logger.getLogger(CommunitiesBean.class
                    .getName()).log(Level.SEVERE, "COMMUNITY MEMEBER NOT SAVED", ex);
            message(
                    null,
                    "MemberRecordNotUpdated",
                    new Object[]{newMemberName});
        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
                try {
                    hib = null;
                } catch (Exception ex) {
                }
            }
        }

        //return "community_members.xhtml?faces-redirect=true";
    }
//buildCommunityMemberCreators

    public List buildCommunityMemberCreators() {
        Session hib = hib_session();
        Transaction tx = hib.beginTransaction();
        String queryString = null;
        List result = null;

        queryString = "FROM CommunityMembers where community_id = :cid and isCreator = 1";
        try {
            result = hib.createQuery(queryString)
                    .setParameter("cid", ubean.getUser_id())
                    .list();
            tx.commit();

        } catch (Exception ex) {
            Logger.getLogger(CommunityMembersBean.class
                    .getName()).log(Level.SEVERE, "ERROR IN build Community Member list", ex);
            System.out.println("ERROR IN build Community Member list");
            System.out.println(ex);

        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
                try {
                    hib = null;
                } catch (Exception ex) {
                }
            }
        }

        return result;

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
            Logger.getLogger(CommunityMembersBean.class
                    .getName()).log(Level.SEVERE, "ERROR IN build Community Member list", ex);
            System.out.println("ERROR IN build Community Member list");
            System.out.println(ex);

        } finally {
            if (hib.isOpen() == true) {
                hib.close();
            }
            if (tx.isActive() == true) {
                tx = null;
                try {
                    hib = null;
                } catch (Exception ex) {
                }
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
     * @return the howManyRecords
     */
    public Integer getHowManyRecords() {
        return howManyRecords;
    }

    /**
     * @param howManyRecords the howManyRecords to set
     */
    public void setHowManyRecords(Integer howManyRecords) {
        this.howManyRecords = howManyRecords;
    }

    /**
     * @return the comm_member_rows
     */
    public ArrayList<CommunityMembers> getComm_member_rows() {
        return comm_member_rows;
    }

    /**
     * @param comm_member_rows the comm_member_rows to set
     */
    public void setComm_member_rows(ArrayList<CommunityMembers> comm_member_rows) {
        this.comm_member_rows = comm_member_rows;
    }

    /**
     * @return the efirstName
     */
    public String[] getEfirstName() {
        return efirstName;
    }

    /**
     * @param efirstName the efirstName to set
     */
    public void setEfirstName(String[] efirstName) {
        this.efirstName = efirstName;
    }

    /**
     * @return the elastName
     */
    public String[] getElastName() {
        return elastName;
    }

    /**
     * @param elastName the elastName to set
     */
    public void setElastName(String[] elastName) {
        this.elastName = elastName;
    }

    /**
     * @return the ealias
     */
    public String[] getEalias() {
        return ealias;
    }

    /**
     * @param ealias the ealias to set
     */
    public void setEalias(String[] ealias) {
        this.ealias = ealias;
    }

    /**
     * @return the eisActive
     */
    public Integer[] getEisActive() {
        return eisActive;
    }

    /**
     * @param eisActive the eisActive to set
     */
    public void setEisActive(Integer[] eisActive) {
        this.eisActive = eisActive;
    }

    /**
     * @return the eemail
     */
    public String[] getEemail() {
        return eemail;
    }

    /**
     * @param eemail the eemail to set
     */
    public void setEemail(String[] eemail) {
        this.eemail = eemail;
    }

}
