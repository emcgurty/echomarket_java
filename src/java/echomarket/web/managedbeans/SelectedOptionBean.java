/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */
package echomarket.web.managedbeans;


import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


@ManagedBean(name="selectedOption")
@RequestScoped
public class SelectedOptionBean  {

    private static final Logger logger = Logger.getLogger(
            "select option bean");
    private String selected_option;
    private String selected_zip_code;
    private Integer selected_category;
    private String selected_item;

    public SelectedOptionBean() {

    }
    
    public String getSelectedZipCode() {
        return this.selected_zip_code;
        
    }
    public void setSelectedZipCode(String zip_code) {
        this.selected_zip_code = zip_code;
        
    }
    
    public String getSelectedItem() {
        return this.selected_item;
        
    }
    public void setSelectedItem(String item) {
        this.selected_item = item;
        
    }
    
        
    
    
    
    
    
    
    
    public Integer getSelectedCategory() {
        return this.selected_category;
        
    }
    public void setSelectedCategory(Integer cat) {
        this.selected_category = cat;
        
    }

    public String setOption() {

        String option = null;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            option = (String) context.getExternalContext()
                    .getSessionMap()
                    .get("option_selected");

        } catch (Exception e) {

        }
        this.selected_option = option;
        return option;
    }

    public String getOption() {
        this.selected_option = this.setOption();
        return this.selected_option;

    }

}
