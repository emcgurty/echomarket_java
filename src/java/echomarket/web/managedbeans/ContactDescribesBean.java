package echomarket.web.managedbeans;

import echomarket.hibernate.ContactDescribes;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@RequestScoped
public class ContactDescribesBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;
  private Integer contact_describe_id;
  private Integer borrowerOrLender;
  private Integer optionValue;
  private String borrowerOrLenderText;

  public ContactDescribesBean() {
  }

  public String getOneCD(String cd) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String returnCD = null;

    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("from ContactDescribes WHERE contact_describe_id = :cd")
              .setParameter("cd", cd)
              .list();
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getOneCD");
      Logger.getLogger(ContactDescribesBean.class.getName()).log(Level.SEVERE, null, e);
    } finally {
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

  public ContactDescribes[] buildContactDArray(String which) {
    ContactDescribes[] cdArray = null;
    List cd_list = null;
    cd_list = cd_list(which);
    int size_of_list = cd_list.size();
    cdArray = new ContactDescribes[size_of_list];
    for (int i = 0; i < size_of_list; i++) {
      ContactDescribes to_Array = (ContactDescribes) cd_list.get(i);
      cdArray[i] = new ContactDescribes(to_Array.getContact_describe_id(), to_Array.getBorrowerOrLenderText(), to_Array.getOptionValue());
    }
    return cdArray;
  }

  private List cd_list(String which) {
    List result = null;
    Session session = null;
    Transaction tx = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      result = session.createQuery("FROM ContactDescribes WHERE purposeType = :which ORDER BY optionValue")
              .setParameter("which", which)
              .list();
      tx.commit();
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in cd_list");
      Logger.getLogger(ContactDescribesBean.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      session = null;
      tx = null;
    }
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

  /**
   * @return the contact_describe_id
   */
  @Id
  public Integer getContact_describe_id() {
    return contact_describe_id;
  }

  /**
   * @param contact_describe_id the contact_describe_id to set
   */
  public void setContact_describe_id(Integer contact_describe_id) {
    this.contact_describe_id = contact_describe_id;
  }

}
