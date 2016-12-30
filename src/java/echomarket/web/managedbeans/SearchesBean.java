package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Categories;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "searchesBean")
@SessionScoped
public class SearchesBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
  @Inject
  ReadOnlyBean robean;
  private List searchResultList;
  private String found_zip_codes;
  private String keyword;
  private String postalCode;
  private Integer categoryId;
  private String startDate;
  private String endDate;
  private Integer lenderOrBorrower;
  private Integer zip_code_radius;
  private String remoteIp;
  private String searchCriteria;
  private String imageLibrary;
  private String which;

  /**
   * @return the found_zip_codes
   */
  public String getFound_zip_codes() {
    return this.found_zip_codes;

  }

  /**
   * @param found_zip_codes the found_zip_codes to set
   */
  public void setFound_zip_codes(String found_zip_codes) {
    this.found_zip_codes = found_zip_codes;
  }

  ///  This is why I designed the database.... INNER JOIN works rather nicely
  public String load_RO(String itype, String iid, String pid) {

    robean.setItemId(iid);
    robean.setParticipant_id(pid);
    robean.setWhich(itype);

    return "RO_item";
  }

  public String SearchResults() {

    Session sb = null;
    Transaction tx = null;
    String queryString = "";
    String forceString = this.found_zip_codes;
    List results = null;
    if (this.lenderOrBorrower == 2) {
      this.which = "borrow";
    } else {
      this.which = "lend";
    }

    this.imageLibrary = this.which + "_images";

    String fromStatement = "SELECT itm.itemId, itm.itemModel, itm.itemDescription, "
            + " itmImages.imageFileName, part.participant_id "
            + " FROM Users user "
            + " INNER JOIN user.participant part "
            + " INNER JOIN part.addresses addr "
            + " INNER JOIN part.item itm "
            + " INNER JOIN itm.itemImages itmImages "
            + " WHERE user.userType LIKE '%" + this.which + "%') AND addr.addressType = 'primary'"
            + " GROUP BY user.userType, addr.addressType, itm.itemId, part.participant_id ";

    if (forceString.matches(".*\\d.*")) {
      queryString = " AND addr.postalCode in (\'" + forceString + "\') ";
    }

    if (this.postalCode.isEmpty() == false) {
      queryString = " AND addr.postalCode LIKE '" + this.postalCode + "%'";
    }
// Need to check for null or isEmpty dates.
    if ((this.startDate.isEmpty() == false) && (this.endDate.isEmpty() == false)) {
      try {
        Date sd = new Date();
        try {
          sd = ConvertDate(this.startDate);
        } catch (Exception ex) {
          Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
        }

        Date ed = new Date();
        try {
          ed = ConvertDate(this.endDate);
        } catch (Exception ex) {
          Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
        }

        if ((sd != null) || (ed != null)) {
          //if (queryString.length() > 0) {
          queryString = queryString + " OR ";
          //}
          queryString = queryString + " ( itm.dateCreated >= \'" + sd + "\' AND itm.dateCreated <= \'" + ed + "\' ) ";
        }
      } catch (Exception ex) {
        Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
      }
    }
    forceString = this.keyword;
    if (forceString.isEmpty() == false) {
      //if (queryString.length() > 0) {
      queryString = queryString + " OR ";
      //}
      queryString = queryString + " (itm.itemDescription like \'%" + forceString + "%\' OR itm.itemModel like \'%" + forceString + "%\')";

    }

    if ((this.categoryId != -2)) {
      // if (queryString.length() > 0) {
      queryString = queryString + " OR ";
      // }
      queryString = queryString + " itm.categoryId = \'" + this.categoryId + "\' ";
    }

    if (ubean.getComDetailID() != null) {
      queryString = queryString + "AND part.communityId = " + ubean.getComDetailID();
    } else {
      queryString = queryString + "AND part.communityId = ''";
    }

    fromStatement = fromStatement + queryString;
    System.out.println(fromStatement);

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      results = sb.createQuery(fromStatement).list();
      tx.commit();
      setSearchResultList(results);
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      sb = null;
    }

    return "search";
  }

  private Date ConvertDate(String strDate) {

    Date convert_date = new Date();
    try {
      convert_date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
    } catch (ParseException ex) {
      Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
      convert_date = null;

    }
    return convert_date;
  }

  /**
   * @return the keyword
   */
  public String getKeyword() {
    return this.keyword;
  }

  /**
   * @param keyword the keyword to set
   */
  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return this.postalCode;
  }

  /**
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return the categoryId
   */
  public Integer getCategoryId() {
    return this.categoryId;
  }

  /**
   * @param categoryId the categoryId to set
   */
  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * @return the startDate
   */
  public String getStartDate() {
    return this.startDate;
  }

  /**
   * @param startDate the startDate to set
   */
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return this.endDate;
  }

  /**
   * @param endDate the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the lenderOrBorrower
   */
  public Integer getLenderOrBorrower() {
    return this.lenderOrBorrower;
  }

  /**
   * @param lenderOrBorrower the lenderOrBorrower to set
   */
  public void setLenderOrBorrower(Integer lenderOrBorrower) {
    this.lenderOrBorrower = lenderOrBorrower;
  }

  /**
   * @return the remoteIp
   */
  public String getRemoteIp() {
    return this.remoteIp;
  }

  /**
   * @param remoteIp the remoteIp to set
   */
  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  /**
   * @return the searchResultList
   */
  public List getSearchResultList() {
    return searchResultList;
  }

  /**
   * @param searchResultList the searchResultList to set
   */
  public void setSearchResultList(List searchResultList) {
    this.searchResultList = searchResultList;
  }

  /**
   * @return the searchCriteria
   */
  public String getSearchCriteria() {
    return searchCriteria;
  }

  /**
   * @param searchCriteria the searchCriteria to set
   */
  public void setSearchCriteria(String searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  /**
   * @return the zip_code_radius
   */
  public Integer getZip_code_radius() {
    return zip_code_radius;
  }

  /**
   * @param zip_code_radius the zip_code_radius to set
   */
  public void setZip_code_radius(Integer zip_code_radius) {
    this.zip_code_radius = zip_code_radius;
  }

  /**
   * @return the imageLibrary
   */
  public String getImageLibrary() {
    return imageLibrary;
  }

  /**
   * @param imageLibrary the imageLibrary to set
   */
  public void setImageLibrary(String imageLibrary) {
    this.imageLibrary = imageLibrary;
  }

  /**
   * @return the which
   */
  public String getWhich() {
    return which;
  }

  /**
   * @param which the which to set
   */
  public void setWhich(String which) {
    this.which = which;
  }

}
