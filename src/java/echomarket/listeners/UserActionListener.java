/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */
package echomarket.listeners;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * <p>
 * Action listener for the command links on the index page.</p>
 */
public class UserActionListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(
            "echomarket.listeners.UserActionLister");

    public UserActionListener() {

    }

    @Override
    public void processAction(ActionEvent event)
            throws AbortProcessingException {
        logger.log(Level.INFO, "Entering echomarket.UserActionLister.processAction");
        
        FacesContext context = null;
        String current = event.getComponent()
                .getId();
        logger.log(Level.INFO, "action is {0}", current);
        
       context = FacesContext.getCurrentInstance();
       context.getExternalContext().getSessionMap().put("user_action", current);
       context = null;
           


    }

}
