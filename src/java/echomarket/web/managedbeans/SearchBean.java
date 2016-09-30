package echomarket.web.managedbeans;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ManagedBean(name = "search")
@RequestScoped
public class SearchBean extends AbstractBean implements Serializable {

    @Inject
    UserBean ubean;
    private Integer found_zip_codes;
    private Date start_date;
    private Date end_date;
    private String keyword;
    private String postal_code;
    private Integer categoryId;
    private Integer lender_or_borrower;
    private Integer is_community;
    private Integer zip_code_radius;

    /**
     * @return the found_zip_codes
     */
    public Integer getFound_zip_codes() {
        return found_zip_codes;
    }

    /**
     * @param found_zip_codes the found_zip_codes to set
     */
    public void setFound_zip_codes(Integer found_zip_codes) {
        this.found_zip_codes = found_zip_codes;
    }

    /**
     * @return the start_date
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date the start_date to set
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * @return the end_date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the postal_code
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * @param postal_code the postal_code to set
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

   
    /**
     * @return the lender_or_borrower
     */
    public Integer getLender_or_borrower() {
        return lender_or_borrower;
    }

    /**
     * @param lender_or_borrower the lender_or_borrower to set
     */
    public void setLender_or_borrower(Integer lender_or_borrower) {
        this.lender_or_borrower = lender_or_borrower;
    }

    /**
     * @return the is_community
     */
    public Integer getIs_community() {
        return is_community;
    }

    /**
     * @param is_community the is_community to set
     */
    public void setIs_community(Integer is_community) {
        this.is_community = is_community;
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
     * @return the categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    
    public List getSearchResults() {
        
        List result = null;
        
        return result;
        
    }

}
