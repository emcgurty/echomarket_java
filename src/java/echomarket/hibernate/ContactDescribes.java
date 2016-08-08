package echomarket.hibernate;
// Generated Jul 21, 2016 11:09:48 AM by Hibernate Tools 4.3.1

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ContactDescribes generated by hbm2java
 */
@Entity
@Table(name="contact_describes")
public class ContactDescribes implements java.io.Serializable {

    @Id
    private String id;
    private String purposeType;
    private Integer optionValue;
    private String borrowerOrLenderText;

    public ContactDescribes() {
    }

    public ContactDescribes(String id, String borrowerOrLenderText) {
        this.id = id;
        this.borrowerOrLenderText = borrowerOrLenderText;

    }

      public ContactDescribes(String id, String borrowerOrLenderText, Integer optionValue) {
        this.id = id;
        this.optionValue = optionValue;
        this.borrowerOrLenderText = borrowerOrLenderText;
    }
    
    public ContactDescribes(String id, String purpose_type, Integer optionValue, String borrowerOrLenderText) {
        this.id = id;
        this.purposeType = purpose_type;
        this.optionValue = optionValue;
        this.borrowerOrLenderText = borrowerOrLenderText;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getPurposeType() {
        return purposeType;
    }

    public void setPurposeType(String pt) {
        this.purposeType = pt;
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
