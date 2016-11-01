package echomarket.hibernate;
public class ContactDescribes  implements java.io.Serializable {


     private String id;
     private String purposeType;
     private int optionValue;
     private String borrowerOrLenderText;

    public ContactDescribes() {
    }

	
    public ContactDescribes(String id, int optionValue) {
        this.id = id;
        this.optionValue = optionValue;
    }
    public ContactDescribes(String id,  String borrowerOrLenderText, int optionValue) {
       this.id = id;
       this.optionValue = optionValue;
       this.borrowerOrLenderText = borrowerOrLenderText;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getPurposeType() {
        return this.purposeType;
    }
    
    public void setPurposeType(String purposeType) {
        this.purposeType = purposeType;
    }
    public int getOptionValue() {
        return this.optionValue;
    }
    
    public void setOptionValue(int optionValue) {
        this.optionValue = optionValue;
    }
    public String getBorrowerOrLenderText() {
        return this.borrowerOrLenderText;
    }
    
    public void setBorrowerOrLenderText(String borrowerOrLenderText) {
        this.borrowerOrLenderText = borrowerOrLenderText;
    }




}


