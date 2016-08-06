/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import echomarket.hibernate.Purpose;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author emm
 */
@ManagedBean
@RequestScoped
public class PurposeBean extends AbstractBean implements Serializable {

    /**
     * Creates a new instance of PurposeBean
     */
    
    private Integer id;
    private String purposeType;


    public PurposeBean() {
    }

       
    /**
     * @return the id
     */
    private Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    private void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the purposeType
     */
    private String getPurposeType() {
        return purposeType;
    }

    /**
     * @param purposeType the purposeType to set
     */
    private void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }
    
    public Purpose[] buildPurposeArray() {
        Purpose[] purposeArray = null;
        List purpose_list = null;
        purpose_list = purpose_list();
        int size_of_list = purpose_list.size();
        purposeArray = new Purpose[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            Purpose to_Array = (Purpose) purpose_list.get(i);
            purposeArray[i] = new Purpose(to_Array.getId(), to_Array.getPurposeType());
        }
        return purposeArray;
    }

    private List purpose_list() {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();

        try {
            result = session.createQuery("from Purpose ORDER BY purpose_order").list();
        } catch (Exception e) {
            System.out.println("Error at line 57 in PurposeBeans");
            e.printStackTrace();

        }
        tx.commit();

        return result;
    }

}
