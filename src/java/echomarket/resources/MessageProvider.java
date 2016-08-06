/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.resources;
 
import java.util.MissingResourceException; 
import java.util.ResourceBundle; 
import javax.faces.context.FacesContext; 
 
/**
 * Read resources file and retrieve value for entered key. 
 * @author Priya 
 */ 
public class MessageProvider { 
 
    private ResourceBundle bundle; 
    public ResourceBundle getBundle() { 
        if (bundle == null) { 
            FacesContext context = FacesContext.getCurrentInstance(); 
            bundle = context.getApplication().getResourceBundle(context, "msg"); 
        } 
        return bundle; 
    } 
 
    public String getValue(String key) { 
 
        String result = null; 
        try { 
            result = getBundle().getString(key); 
        } catch (MissingResourceException e) { 
            result = null; 
        } 
        return result; 
    } 
}
