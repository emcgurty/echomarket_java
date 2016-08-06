/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.resources;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class SelectOneMenuValidator implements Validator {
    /* (non-Javadoc)
     * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */

    public void validate(FacesContext context, UIComponent arg1, Object value)
            throws ValidatorException {

         String emValue = (String)value;

         if(emValue != null && emValue.toUpperCase().equals("PLEASE SELECT")){ 
             FacesMessage message = new FacesMessage();  
             message.setSeverity(FacesMessage.SEVERITY_ERROR);  
             message.setSummary("Please Select a question!");  
             message.setDetail("Please Select a question!");              
             context.addMessage("Please make selection.", message);      
             throw new ValidatorException(message); 
         }

    }
}