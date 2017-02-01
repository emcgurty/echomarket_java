package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Communities;
import echomarket.hibernate.Participant;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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
  @Inject
  CommunityMembersBean cmBean;
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
  private String remoteIp;

  public String load_community_detail() {

    System.out.println("Called load_community_detail");
    List result = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;
    Communities comm_Array = null;
    String cid = ubean.getCommunityId();
    queryString = "FROM Communities where community_id = :cid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("cid", cid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in load_community_detail, get result");
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

      } else if (result.size() == 0) {

        queryString = "FROM Participant where community_id = :cid";
        try {
          hib = hib_session();
          tx = hib.beginTransaction();
          result = hib.createQuery(queryString)
                  .setParameter("cid", ubean.getCommunityId())
                  .list();
          tx.commit();
        } catch (Exception ex) {
          tx.rollback();
          System.out.println("Error in load_community_detail, get result2");
          Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          hib = null;
          tx = null;
        }

        if (result != null) {
          if (result.size() == 1) {
            Participant part_array = (Participant) result.get(0);
            Boolean needCommunityValues = ubean.getCreatorDetail(ubean.getUser_id());
            this.communityName = ubean.getCommunityName();
            this.firstName = part_array.getFirstName();
            this.lastName = part_array.getLastName();
            this.email = ubean.getEmail();
            this.cellPhone = part_array.getCellPhone();

            List getPrimaryAddress = getCommunityPrimaryAddress();
            if (getPrimaryAddress != null) {
              if (getPrimaryAddress.size() == 1) {
                Addresses addr = (Addresses) getPrimaryAddress.get(0);
                this.addressLine1 = addr.getAddressLine1();
                this.addressLine2 = addr.getAddressLine2();
                this.postalCode = addr.getPostalCode();
                this.city = addr.getCity();
                this.province = addr.getProvince();
                this.region = addr.getRegion();
                this.usStateId = addr.getUsStateId();
                this.countryId = addr.getCountryId();
              }
            }
          }
        }

      } else {
      }
    }

    return "community_detail";
  }

  private List getCommunityPrimaryAddress() {

    List primaryAddress = null;
    Session hib = null;
    Transaction tx = null;
    String queryString = null;

    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      queryString = " FROM Addresses "
              + " WHERE participant_id = :pid AND addressType = 'primary'";
      primaryAddress = hib.createQuery(queryString)
              .setParameter("pid", ubean.getParticipant_id())
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error on getCommunityPrimaryAddress");
      Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    } finally {
      tx = null;
      hib = null;
    }
    return primaryAddress;
  }

  public String saveCommunityDetail() {

    Session sb = null;
    Transaction tx = null;
    Boolean updateSuccess = false;
    String query = null;
    List result = null;
    Communities comm_result = null;

    if (this.communityId.isEmpty() == true) {
      ///  Should never be empty

    } else {
      /// Do update 
      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        query = "FROM Communities WHERE community_id = :cid";
        result = sb.createQuery(query)
                .setParameter("cid", this.communityId)
                .list();
        tx.commit();
      } catch (Exception ex) {
        System.out.println("Error in saveCommunityDetail on save");
        Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
        tx.rollback();
      } finally {
        tx = null;
        sb = null;
      }

      try {
        if (result != null) {
          if (result.size() == 1) {
            comm_result = (Communities) result.get(0);
            comm_result.setAddressLine1(addressLine1);
            comm_result.setAddressLine2(addressLine2);
            comm_result.setCellPhone(cellPhone);
            comm_result.setHomePhone(homePhone);
            comm_result.setCommunityName(communityName);
            comm_result.setCountryId(countryId);
            comm_result.setUsStateId(usStateId);
            comm_result.setCity(city);
            comm_result.setEmail(email);
            comm_result.setFirstName(firstName);
            comm_result.setMi(mi);
            comm_result.setLastName(lastName);
            comm_result.setPostalCode(postalCode);
            comm_result.setProvince(province);
            comm_result.setRegion(region);
            comm_result.setRemoteIp(getClientIpAddr());
            comm_result.setIsActive(1);

            sb = hib_session();
            tx = sb.beginTransaction();
            sb.update(comm_result);
            tx.commit();
            updateSuccess = true;
            ubean.setCommunityId(comm_result.getCommunityId());
            ubean.setCommunityName(comm_result.getCommunityName());
            message(null, "CommunityDetailRecordUpdated", null);
          }
        }
      } catch (Exception ex) {
        System.out.println("Error in saveCommunityDetail on update");
        Logger.getLogger(CommunitiesBean.class.getName()).log(Level.SEVERE, null, ex);
        tx.rollback();
      } finally {
        comm_result = null;
        tx = null;
        sb = null;
      }
    }
    if (updateSuccess == true) {
      ubean.setComMemberDetailID(updateSuccess);
      ubean.setCreatorDetailID(updateSuccess); //allows the Community Members option to be available in menu
    }
    
    return cmBean.load_community_members();
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
