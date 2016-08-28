/*
 * Copyright 2012 Oracle and/or its affiliates.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developers.sun.com/license/berkeley_license.html
 */
package echomarket.web.managedbeans;

import echomarket.hibernate.HibernateUtil;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.Id;
import org.hibernate.Session;

/**
 * <p>
 * Abstract base class for managed beans to share utility methods.</p>
 */
@Named
@SessionScoped
public abstract class AbstractBean implements Serializable {

//  
//    Source:  Setting UUID ids: http://blog.xebia.com/jpa-implementation-patterns-using-uuids-as-primary-keys/

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    public AbstractBean() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractBean)) {
            return false;
        }
        AbstractBean other = (AbstractBean) obj;
        return getId().equals(other.getId());
    }

    /**
     * @return the id
     */
    protected String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    protected FacesContext context() {
        return (FacesContext.getCurrentInstance());
    }

    protected Session hib_session() {
        return HibernateUtil.getSessionFactory().getCurrentSession();

    }

    protected void message(
            String clientId,
            String key) {
        // Look up the requested message text
        String text = "";

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(
                    "echomarket.web.messages.Messages",
                    context().getViewRoot().getLocale());
            text = bundle.getString(key);
        } catch (Exception e) {
            text = "???" + key + "???";
        }

        // Construct and add a FacesMessage containing it
        context()
                .addMessage(
                        clientId,
                        new FacesMessage(text));
    }

    /**
     * <p>
     * Add a localized message to the <code>FacesContext</code> for the current
     * request.</p>
     *
     * @param clientId Client identifier of the component this message relates
     * to, or <code>null</code> for global messages
     * @param key Message key of the message to include
     * @param params Substitution parameters for using the localized text as a
     * message format
     */
    protected void message(
            String clientId,
            String key,
            Object[] params) {
        // Look up the requested message text
        String text = "";

        try {
            ResourceBundle bundle = ResourceBundle.getBundle(
                    "echomarket.web.messages.Messages",
                    context().getViewRoot().getLocale());
            text = bundle.getString(key);
        } catch (Exception e) {
            text = "???" + key + "???";
        }

        // Perform the requested substitutions
        if ((params != null) && (params.length > 0)) {
            text = MessageFormat.format(text, params);
        }

        // Construct and add a FacesMessage containing it
        context()
                .addMessage(
                        clientId,
                        new FacesMessage(text));
    }

    
}
