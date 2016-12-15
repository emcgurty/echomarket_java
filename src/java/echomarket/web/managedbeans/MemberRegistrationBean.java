package echomarket.web.managedbeans;

import echomarket.hibernate.Communities;
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
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "memberRegistrationBean")
/// Had to change to Session becuase interactive form.
@RequestScoped
public class MemberRegistrationBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  private String pid;
  private String cid;
  private String remoteIp;
  private String firstName;
  private String mi;
  private String lastName;
  private String alias;
  private Integer isActive;
  private Integer isCreator;
  private String emailAlternative;
  private Integer editable;

  public List getMemberInformation() {

    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Participant pt = null;
    queryString = "FROM Participant where participant_id = :pid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("pid", getPid())
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    } finally {
      tx = null;
      hib = null;

    }

    if (result != null) {
      if (result.size() == 1) {
        pt = (Participant) result.get(0);
        this.cid = pt.getCommunityId();
        
      }
    }

    return result;
  }

  public String getCommunityName(String cid) {
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Communities pt = null;
    queryString = "FROM Communities where community_id = :cid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", getCid())
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
    } finally {
      tx = null;
      hib = null;

    }

    if (result != null) {
      if (result.size() == 1) {
        pt = (Communities) result.get(0);
        queryString = pt.getCommunityName();
      }
    }
    result = null;
    pt = null;
    return queryString;

  }
  
  public String processMemberRegistration() {
    return ubean.registerUser();
    
  }

  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
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
   * @return the cid
   */
  public String getCid() {
    return cid;
  }

  /**
   * @param cid the cid to set
   */
  public void setCid(String cid) {
    this.cid = cid;
  }
}
