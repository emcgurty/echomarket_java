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
    private int isCommunity;
    private String user_id;
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

    public String SearchResults() {

        Session sb = hib_session();
        Transaction tx = sb.beginTransaction();
        String foo = null;
        String whichDatabase = "";
        List address_list;
        // Build query string
        String queryString = "";
        String buildPC = "";
        String forceString = this.found_zip_codes;
        if (forceString.matches(".*\\d.*")) {
            if (this.lenderOrBorrower == 2) {

                queryString = " postal_code in (\'" + forceString + "\') AND lender_id =  NULL";
                whichDatabase = "from Addresses  where " + queryString;
                address_list = sb.createQuery(whichDatabase).list();
                tx.commit();
                if (address_list.size() > 0) {
                    for (int i = 0; i < address_list.size(); i++) {
                        Addresses cArray = (Addresses) address_list.get(i);
                        buildPC = buildPC + cArray.getBorrower_id() + ",";
                    }
                    if (!(buildPC.isEmpty())) {
                        buildPC = buildPC.replace(buildPC.substring(buildPC.length() - 1), "");
                        queryString = " borrower_id in (\'" + buildPC + "\')";
                    }
                }
            } else {
                queryString = " postal_code in (\'" + forceString + "\') AND borrower_id =  NULL";
                whichDatabase = "from Addresses  where " + queryString;
                address_list = sb.createQuery(whichDatabase).list();
                tx.commit();
                if (address_list.size() > 0) {
                    for (int i = 0; i < address_list.size(); i++) {
                        Addresses cArray = (Addresses) address_list.get(i);
                        buildPC = buildPC + cArray.getLender_id() + ",";
                    }
                    if (!(buildPC.isEmpty())) {
                        buildPC = buildPC.replace(buildPC.substring(buildPC.length() - 1), "");
                        queryString = " lender_id in (\'" + buildPC + "\')";
                    }
                }
            }

        } else {
            forceString = this.postalCode;
            if (forceString.matches(".*\\d.*")) {
                if (this.lenderOrBorrower == 2) {
                    queryString = " postal_code like (\'" + forceString + "%\') AND lender_id =  NULL";
                    whichDatabase = "from Addresses  where " + queryString;
                    address_list = sb.createQuery(whichDatabase).list();
                    tx.commit();
                    if (address_list.size() > 0) {
                        for (int i = 0; i < address_list.size(); i++) {
                            Addresses cArray = (Addresses) address_list.get(i);
                            buildPC = buildPC + cArray.getBorrower_id() + ",";
                        }
                        if (!(buildPC.isEmpty())) {
                            buildPC = buildPC.replace(buildPC.substring(buildPC.length() - 1), "");
                            queryString = " borrower_id in (\'" + buildPC + "\')";
                        }
                    }
                } else {
                    queryString = " postal_code like (\'" + forceString + "%\') AND borrower_id =  NULL";
                    whichDatabase = "from Addresses  where " + queryString;
                    address_list = sb.createQuery(whichDatabase).list();
                    tx.commit();
                    if (address_list.size() > 0) {
                        for (int i = 0; i < address_list.size(); i++) {
                            Addresses cArray = (Addresses) address_list.get(i);
                            buildPC = buildPC + cArray.getBorrower_id() + ",";
                        }
                        if (!(buildPC.isEmpty())) {
                            buildPC = buildPC.replace(buildPC.substring(buildPC.length() - 1), "");
                            queryString = " lender_id in (\'" + buildPC + "\')";
                        }
                    }
                }
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
                queryString = queryString + " ( date_created >= \'" + this.startDate + "\' AND date_created <= \'" + this.endDate + "\' ) ";

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
        // Okay now let's build the query
        if (this.lenderOrBorrower == 2) {
            whichDatabase = "from Borrowers where " + queryString;
            System.out.println(whichDatabase);
        } else {
            whichDatabase = "from Lenders where " + queryString;
        }

        try {
            if (sb.isOpen() == false) {
                sb = hib_session();
            }
            if (tx.isActive() == false) {
                tx = sb.beginTransaction();
            }
            setSearchResultList(sb.createQuery(whichDatabase).list());
            tx.commit();
        } catch (Exception ex) {
            Logger.getLogger(SearchesBean.class.getName()).log(Level.INFO, null, ex);
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
            Session hib = hib_session();
            Transaction tx = hib.beginTransaction();
            List results = null;
            String queryString = "from Categories where category_id = :cat";
            results = hib.createQuery(queryString).setParameter("cat", num).list();
            tx.commit();
            Categories cat_Array = new Categories();
            cat_Array = (Categories) results.get(0);
            build = build + " in Category, " + cat_Array.getCategoryType() + ",";
            hib = null;
            tx = null;
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
            return false; }
        else {
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
