package echomarket.web.managedbeans;

import echomarket.hibernate.Participant;
import java.io.Serializable;
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
  
  private String found_zip_codes;
  private String keyword;
  private String postalCode;
  private Integer categoryId;
  private String startDate;
  private String endDate;
  private Integer lenderOrBorrower;
  private Integer zip_code_radius;
  private String remoteIp;
  private String imageLibrary;
  private String which;
  private List itemDetail;
  private Boolean performedSearch;

  public String getFound_zip_codes() {
    return this.found_zip_codes;
  }

  /**
   * @param found_zip_codes the found_zip_codes to set
   */
  public void setFound_zip_codes(String found_zip_codes) {
    this.found_zip_codes = found_zip_codes;
  }

  public String load_RO(String itype, String iid, String pid) {

    robean.setItemId(iid);
    robean.setParticipant_id(pid);
    robean.setWhich(itype);
    this.performedSearch = false;
    return "RO_item";
  }

  public List itemImage(String iid) {
    List results = null;
    Session sb = null;
    Transaction tx = null;
    String fromStatement = "";

    fromStatement = " FROM ItemImages iimag "
            + "  WHERE iimag.itemId = :iid ";

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      results = sb.createQuery(fromStatement)
              .setParameter("iid", iid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      sb = null;
    }
    return results;
  }

  public String SearchResults() {

    this.performedSearch = true;
    Session sb = null;
    Transaction tx = null;
    String queryString = "";
    String forceString = this.found_zip_codes;
    List results = null;
    String fromStatement = "";
    String hold_pid = "";
    if (this.lenderOrBorrower == 2) {
      this.which = "borrow";
    } else {
      this.which = "lend";
    }
    this.imageLibrary = this.which + "_images";

    fromStatement = " SELECT  part "
            + "  FROM Participant part "
            + "  INNER JOIN part.addresses addr "
            + "  WHERE addr.addressType = 'primary' ";

    if (ubean.getComDetailID() != null) {
      fromStatement = fromStatement + "  AND part.communityId = \'" + ubean.getComDetailID() + "\' ";
    } else {
      fromStatement = fromStatement + "  AND part.communityId = ''";
    }

//    This will be implemented in production
//    if (forceString.matches(".*\\d.*")) {
//      fromStatement = " OR addr.postal_code in (\'" + forceString + "\') ";
//    }
    if (this.postalCode.isEmpty() == false) {
      fromStatement = fromStatement + " OR addr.postalCode LIKE \'" + this.postalCode + "%\'";
    }

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      results = sb.createQuery(fromStatement).list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      tx = null;
      sb = null;
    }

    String[] pids = new String[results.size()];

    if (results != null) {
      if (results.size() > 0) {
        for (int i = 0; i < results.size(); i++) {
          Participant cArray = (Participant) results.get(i);
          if (cArray.getParticipant_id().isEmpty() == false) {
            hold_pid = "\'" + cArray.getParticipant_id() + "\'";
            pids[i] = hold_pid;
          }
        }
        hold_pid = String.join(",", pids);

      }
    }

    results = null;
    if (hold_pid.isEmpty() == false) {
      fromStatement = " FROM Items itm WHERE itm.itemType = :which "
              + (hold_pid.isEmpty() ? "" : " AND itm.participant_id IN ( " + hold_pid + " )");

      // Need to check for null or isEmpty dates.
      if ((this.startDate.isEmpty() == false) && (this.endDate.isEmpty() == false)) {
        try {
          queryString = queryString + " OR ";
          queryString = queryString + " ( itm.dateCreated >= \'" + this.startDate + "\' AND itm.dateCreated <= \'" + this.endDate + "\' ) ";

        } catch (Exception ex) {
          Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
        }
      }
      forceString = this.keyword;
      if (forceString.isEmpty() == false) {
        queryString = queryString + " OR ";
        queryString = queryString + " (itm.itemDescription like \'%" + forceString + "%\' OR itm.itemModel like \'%" + forceString + "%\')";
      }

      if ((this.categoryId != -2)) {
        queryString = queryString + " OR";
        queryString = queryString + " itm.categoryId = " + this.categoryId;
      }

      fromStatement = fromStatement + queryString;
      System.out.println(fromStatement);

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        results = sb.createQuery(fromStatement).setParameter("which", this.which).list();
        tx.commit();
      } catch (Exception ex) {
        tx.rollback();
        Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
        tx = null;
        sb = null;
      }
      
      this.itemDetail = results;
    }
    return "search";
  }

//  May need this in production
//  private Date ConvertDate(String strDate) {
//
//    Date convert_date = new Date();
//    try {
//      convert_date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
//    } catch (ParseException ex) {
//      Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
//      convert_date = null;
//
//    }
//    return convert_date;
//  }
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

  /**
   * @return the itemDetail
   */
  public List getItemDetail() {
    return itemDetail;
  }

  /**
   * @param itemDetail the itemDetail to set
   */
  public void setItemDetail(List itemDetail) {
    this.itemDetail = itemDetail;
  }

  /**
   * @return the performedSearch
   */
  public Boolean getPerformedSearch() {
    return performedSearch;
  }

  /**
   * @param performedSearch the performedSearch to set
   */
  public void setPerformedSearch(Boolean performedSearch) {
    this.performedSearch = performedSearch;
  }

}
