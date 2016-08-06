package echomarket.listeners;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Action listener for the command links on the index page.</p>
 */
public class EndSession implements ActionListener {

    private static final Logger logger = Logger.getLogger(
            "echomarket.listeners.EndSession");

    public EndSession() {

    }

    @Override
    public void processAction(ActionEvent event)
            throws AbortProcessingException {
        try {
            logger.log(Level.INFO, "Entering echomarket.EndSession.processAction");
        
            String current = event.getComponent()
                    .getId();
            logger.log(Level.INFO, "End Session id {0}", current);

            ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletResponse response = (HttpServletResponse) ectx.getResponse();
            HttpSession session = (HttpSession) ectx.getSession(false);
            session.invalidate();
           
            //return null;
        } catch (Exception ex) {
            Logger.getLogger(EndSession.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
