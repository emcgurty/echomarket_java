package echomarket.web.managedbeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ManagedBean(name = "search")
@RequestScoped
public class SearchesBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    private String found_zip_codes;
    private String keyword;
    private String postalCode;
    private Integer categoryId;
    private String startDate;
    private String endDate;
    private Integer lenderOrBorrower;
    private int isCommunity;
    private String user_id;
    private Integer zipCodeRadius;
    private String remoteIp;


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

    public String SearchResults() {

        // Build query string
        
        String queryString = "";
        String forceString = this.found_zip_codes;
        if (forceString.matches(".*\\d.*")) {
            queryString = " postal_code in (\'" + forceString + "\')";
        } else {
            forceString = this.postalCode;
            if (forceString.matches(".*\\d.*")) {
                queryString = " postal_code like \'" + forceString + "%\'";
            }
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
                if (queryString.length() > 0) {
                    queryString = queryString + " OR ";
                }
                queryString = queryString + " ( date_created > \'" + sd + "\' AND date_created < \'" + addDays(ed, 1) + "\' ) ";
                
            }
        } catch (Exception ex) {

            Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);

        }
        forceString = this.keyword;
        if (forceString.isEmpty() == false) {
        if (queryString.length() > 0) {
                queryString = queryString + " OR ";
            }
            queryString = queryString + " (item_description like \'%" + forceString + "%\' OR item_model like \'%" + forceString + "%\')";
            
        }

        if ((this.categoryId != -2)) {
            if (queryString.length() > 0) {
                queryString = queryString + " OR ";
            }
            queryString = queryString + " category_id = \'" + this.categoryId + "\' ";
            
        }

        System.out.println(queryString);

//    private Integer is_community;
        //List result = null;
        return "index";

    }

    private Date addDays(Date date, int days) {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        try {
            cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
        } catch (Exception ex) {
            Logger.getLogger(SearchesBean.class.getName()).log(Level.SEVERE, null, ex);
            return today;
        } finally {
            return cal.getTime();
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
     * @return the isCommunity
     */
    public int getIsCommunity() {
        return this.isCommunity;
    }

    /**
     * @param isCommunity the isCommunity to set
     */
    public void setIsCommunity(int isCommunity) {
        this.isCommunity = isCommunity;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return this.user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the zipCodeRadius
     */
    public Integer getZipCodeRadius() {
        return this.zipCodeRadius;
    }

    /**
     * @param zipCodeRadius the zipCodeRadius to set
     */
    public void setZipCodeRadius(Integer zipCodeRadius) {
        this.zipCodeRadius = zipCodeRadius;
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

}
