package echomarket.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * <p>
 * Value change listener for the name entered on the
 * <code>bookcashier.xhtml</code> page.</p>
 */
public class NameChanged extends Object implements ValueChangeListener {

  private static final Logger logger = Logger.getLogger(
          "echo_market.listeners.NameChanged");

  @Override
  public void processValueChange(ValueChangeEvent event)
          throws AbortProcessingException {
    logger.log(Level.INFO, "Entering NameChanged.processValueChange");

    String current = event.getComponent().getId();
    logger.log(Level.INFO, "current in NameChanges is {0}", current);

    if (null != event.getNewValue()) {
      if ("name".equals(current)) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("name", event.getNewValue());
      } else if ("selected_zip_code".equals(current)) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selected_zip_code", event.getNewValue());
      }
    }
  }
}
