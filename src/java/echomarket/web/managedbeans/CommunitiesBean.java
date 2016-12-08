package echomarket.web.managedbeans;

import echomarket.hibernate.Communities;
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
@ManagedBean
@RequestScoped
public class CommunitiesBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  private String communityId;
  private String communityName;
  private Integer approved;
  private String firstName;
  private String mi;
  private String lastName;
  private String addressLine1;
  private String addressLine2;
  private String postalCode;
  private String city;
  private String province;
  private String region;
  private String usStateId;
  private String countryId;
  private String homePhone;
  private String cellPhone;
  private String email;
  private Integer isActive;
  private Integer isSaved;
  private String remoteIp;

  public String load_community_detail() {

    List result = null;
    Session hib = null;
    Transaction tx = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
    } catch (Exception ex) {
      tx.rollback();
    }
    String queryString = null;
    Communities comm_Array = new Communities();
    queryString = "FROM Communities where community_id = :cid";
    try {
      result = hib.createQuery(queryString)
              .setParameter("cid", ubean.getCommunityId())
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      hib = null;
      tx = null;
    }

    if (result != null) {
      if (result.size() == 1) {
        comm_Array = (Communities) result.get(0);
        this.communityId = comm_Array.getCommunityId();
        this.communityName = comm_Array.getCommunityName();
        this.firstName = comm_Array.getFirstName();
        this.lastName = comm_Array.getLastName();
        this.email = ubean.getEmail();
        this.addressLine1 = comm_Array.getAddressLine1();
        this.addressLine2 = comm_Array.getAddressLine2();
        this.postalCode = comm_Array.getPostalCode();
        this.city = comm_Array.getCity();
        this.province = comm_Array.getProvince();
        this.region = comm_Array.getRegion();
        this.usStateId = comm_Array.getUsStateId();
        this.countryId = comm_Array.getCountryId();
        this.homePhone = comm_Array.getHomePhone();
        this.cellPhone = comm_Array.getCellPhone();
        this.email = comm_Array.getEmail();

      }
    }

    return "community_detail.xhtml?faces-redirect=true";
  }

  public String saveCommunityDetail() {

    Session sb = null;
    Transaction tx = null;
    Communities comm = null;

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      comm = new Communities(getId(), this.communityName, 0, this.firstName, null, this.lastName, this.addressLine1, this.addressLine2, this.postalCode, this.city, this.province, this.usStateId, this.countryId, this.homePhone, this.cellPhone, this.email, 1, this.region, "NA");
      sb.update(comm);
      tx.commit();
      message(null, "CommunityDetailRecordSaved", null);
    } catch (Exception ex) {
      Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
      message(null, "CommunityDetailRecordWasNotSaved", null);
    } finally {
      sb = null;
      tx = null;
    }
    return "index";
  }

  /**
   * @return the communityName
   */
  public String getCommunityName() {
    return communityName;
  }

  /**
   * @param communityName the communityName to set
   */
  public void setCommunityName(String communityName) {
    this.communityName = communityName;
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
   * @return the addressLine1
   */
  public String getAddressLine1() {
    return addressLine1;
  }

  /**
   * @param addressLine1 the addressLine1 to set
   */
  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  /**
   * @return the addressLine2
   */
  public String getAddressLine2() {
    return addressLine2;
  }

  /**
   * @param addressLine2 the addressLine2 to set
   */
  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the province
   */
  public String getProvince() {
    return province;
  }

  /**
   * @param province the province to set
   */
  public void setProvince(String province) {
    this.province = province;
  }

  /**
   * @return the usStateId
   */
  public String getUsStateId() {
    return usStateId;
  }

  /**
   * @param usStateId the usStateId to set
   */
  public void setUsStateId(String usStateId) {
    this.usStateId = usStateId;
  }

  /**
   * @return the countryId
   */
  public String getCountryId() {
    return countryId;
  }

  /**
   * @param countryId the countryId to set
   */
  public void setCountryId(String countryId) {
    this.countryId = countryId;
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
   * @return the isSaved
   */
  public Integer getIsSaved() {
    return isSaved;
  }

  /**
   * @param isSaved the isSaved to set
   */
  public void setIsSaved(Integer isSaved) {
    this.isSaved = isSaved;
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
   * @return the region
   */
  public String getRegion() {
    return region;
  }

  /**
   * @param region the region to set
   */
  public void setRegion(String region) {
    this.region = region;
  }

  /**
   * @return the communityId
   */
  public String getCommunityId() {
    return communityId;
  }

  /**
   * @param communityId the communityId to set
   */
  public void setCommunityId(String communityId) {
    this.communityId = communityId;
  }

}
