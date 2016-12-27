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
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ManagedBean(name = "search")
@RequestScoped
public class SearchesBean extends AbstractBean implements Serializable {

  @Inject
  UserBean ubean;
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
  private Boolean displayResults;

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
  public String SearchResults() {

    Session sb = null;
    Transaction tx = null;
    String queryString = "";
    String forceString = this.found_zip_codes;
    String which = null;
    List results = null;
    if (this.lenderOrBorrower == 2) {
      which = "borrow";
    } else {
      which = "lend";
    }
    String fromStatement = "SELECT itm from Users user "
            + " INNER JOIN user.participant part "
            + " INNER JOIN part.addresses addr "
            + " INNER JOIN part.item itm "
            + " WHERE user.userType LIKE '%" + which + "%') ";

    if (forceString.matches(".*\\d.*")) {
      queryString = " AND addr.postalCode in (\'" + forceString + "\') ";
    }

    if (this.postalCode.isEmpty() == false) {
      queryString = " AND addr.postalCode LIKE '" + this.postalCode + "%'";
    }

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
        queryString = queryString + " ( itm.dateCreated >= \'" + this.startDate + "\' AND itm.dateCreated <= \'" + this.endDate + "\' ) ";
      }
    } catch (Exception ex) {
      Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
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
      queryString = queryString + "AND part.communityId = NULL";
    }

    fromStatement = fromStatement + queryString;
    System.out.println(fromStatement);

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      results = sb.createQuery(fromStatement).list();
      setSearchResultList(results);
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      sb = null;
      //Build pretty search criteria
      setSearchCriteria(buildSearchCriteria());
      setDisplayResults(true);
    }
    return "search";

  }

  private String buildSearchCriteria() {

    String build = "";
    String foo = null;
    String hold = null;
    Integer num = null;
    Session hib = null;
    Transaction tx = null;
    List results = null;

    if (this.lenderOrBorrower == 1) {
      build = "Retrieve from LENDER records all items";
    } else {
      build = "Retrieve from BORROWER records all items";
    }

    hold = this.keyword;
    if (!(hold.equals(foo))) {
      build = build + " containing the keyword, " + hold + ", in either the item description or item model,";
    }

    num = this.categoryId;
    if (num != -2) {
      try {
        hib = hib_session();
        tx = hib.beginTransaction();
        String queryString = "from Categories where id = :cat";
        results = hib.createQuery(queryString).setParameter("cat", num).list();
        tx.commit();
      } catch (Exception ex) {
        tx.rollback();
      } finally {

        hib = null;
        tx = null;
      }
    }
    if (results != null) {
      Categories cat_Array = new Categories();
      cat_Array = (Categories) results.get(0);
      build = build + " in Category, " + cat_Array.getCategoryType() + ",";
    }
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
      build = build + "created between the dates, " + this.startDate + " and " + this.endDate + ".";
    }

    return build;
  }

  private String buildRadiusPhrase(String h, Integer n) {

    String phrase = null;

    switch (n) {
      case 1:
        phrase = " within a one mile radius of " + h;
        break;

      case 5:
        phrase = " within a five mile radius of " + h;
        break;

      case 10:
        phrase = " within a ten mile radius of " + h;
        break;

      case 25:
        phrase = " within a twenty-five mile radius of " + h;
        break; // optional

      default: // Optional

    }

    return phrase;

  }

  private Date addDays(Date date, int days) {
    Date today = new Date();
    Calendar cal = Calendar.getInstance();
    Boolean catchError = false;
    try {
      cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.DATE, days); //minus number would decrement the days
      catchError = true;
    } catch (Exception ex) {
      Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      /// 
    }
    if (catchError == true) {
      return cal.getTime();
    } else {
      return today;
    }
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
   * @return the displayResults
   */
  public Boolean getDisplayResults() {
    if (this.searchCriteria == null) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * @param displayResults the displayResults to set
   */
  public void setDisplayResults(Boolean displayResults) {

    this.displayResults = displayResults;
  }

}
