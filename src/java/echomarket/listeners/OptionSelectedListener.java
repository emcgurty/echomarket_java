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
public class OptionSelectedListener implements ActionListener {

    private static final Logger logger = Logger.getLogger(
            "echomarket.listeners.OptionSelectedListener");

    public OptionSelectedListener() {

    }

    @Override
    public void processAction(ActionEvent event)
            throws AbortProcessingException {
        logger.log(Level.INFO, "Entering echomarket.Optionselected.processAction");

        String current = event.getComponent()
                .getId();
        logger.log(Level.INFO, "current is {0}", current);

            FacesContext context = FacesContext.getCurrentInstance();
            logger.log(Level.INFO, "option selected {0}", current);
            context.getExternalContext()
                    .getSessionMap()
                    .put("option_selected", current);


    }

}
