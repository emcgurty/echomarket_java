/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.web.managedbeans;

import echomarket.hibernate.ContactDescribes;
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
@ManagedBean(name = "cd")
@RequestScoped
public class ContactDescribesBean extends AbstractBean implements Serializable {

//    private Integer id;
    private Integer borrowerOrLender;
    private Integer optionValue;
    private String borrowerOrLenderText;

    public ContactDescribesBean() {
    }

    public String getOneCD(String cd) {

        List result = null;
        Session session = hib_session();
        Transaction tx = session.beginTransaction();
        String returnCD = null;

        try {
            result = session.createQuery("from ContactDescribes WHERE id = :cd")
                    .setParameter("cd", cd)
                    .list();
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error at line 42 in CD Bean");
            e.printStackTrace();

        } finally {
//            session.close();
            session = null;
            tx = null;
        }

        if (result.size() > 0) {
            ContactDescribes returnedCDName = (ContactDescribes) result.get(0);
            returnCD = returnedCDName.getBorrowerOrLenderText();
            returnedCDName = null;
        } else {
            returnCD = "Contact Description not found";
        }

        result = null;
        return returnCD;
    }

    public ContactDescribes[] buildContactDArray(String whichpurpose) {
        ContactDescribes[] cdArray = null;
        List cd_list = null;
        cd_list = cd_list(whichpurpose);
        int size_of_list = cd_list.size();
        cdArray = new ContactDescribes[size_of_list];
        for (int i = 0; i < size_of_list; i++) {
            ContactDescribes to_Array = (ContactDescribes) cd_list.get(i);
            cdArray[i] = new ContactDescribes(to_Array.getId(), to_Array.getBorrowerOrLenderText(), to_Array.getOptionValue());
        }
        return cdArray;
    }

    private List cd_list(String purpose) {

        List result = null;
        Session session = null;
        Transaction tx = null;
        
        try {
            session = hib_session();
            tx = session.beginTransaction();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error at line 87 in CDBeans");
            ex.printStackTrace();
        } finally {
            
        }

        try {
            result = session.createQuery("FROM ContactDescribes Where purposeType  = :pt ORDER BY optionValue")
                    .setParameter("pt", purpose)
                    .list();
            tx.commit();
        } catch (Exception ex) {
            tx.rollback();
            System.out.println("Error at line 57 in CDBeans");
            ex.printStackTrace();

        }
        session = null;
        tx = null;

        return result;
    }

    /**
     * @return the borrowerOrLender
     */
    public Integer getBorrowerOrLender() {
        return borrowerOrLender;
    }

    /**
     * @param borrowerOrLender the borrowerOrLender to set
     */
    public void setBorrowerOrLender(Integer borrowerOrLender) {
        this.borrowerOrLender = borrowerOrLender;
    }

    /**
     * @return the optionValue
     */
    public Integer getOptionValue() {
        return optionValue;
    }

    /**
     * @param optionValue the optionValue to set
     */
    public void setOptionValue(Integer optionValue) {
        this.optionValue = optionValue;
    }

    /**
     * @return the borrowerOrLenderText
     */
    public String getBorrowerOrLenderText() {
        return borrowerOrLenderText;
    }

    /**
     * @param borrowerOrLenderText the borrowerOrLenderText to set
     */
    public void setBorrowerOrLenderText(String borrowerOrLenderText) {
        this.borrowerOrLenderText = borrowerOrLenderText;
    }

}
