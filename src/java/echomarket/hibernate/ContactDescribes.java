package echomarket.hibernate;

public class ContactDescribes implements java.io.Serializable {

  private String contact_describe_id;
  private String purposeType;
  private int optionValue;
  private String borrowerOrLenderText;

  public ContactDescribes() {
  }

  public ContactDescribes(String contact_describe_id, int optionValue) {
    this.contact_describe_id = contact_describe_id;
    this.optionValue = optionValue;
  }

  public ContactDescribes(String contact_describe_id, String borrowerOrLenderText, int optionValue) {
    this.contact_describe_id = contact_describe_id;
    this.optionValue = optionValue;
    this.borrowerOrLenderText = borrowerOrLenderText;
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

  /**
   * @return the contact_describe_id
   */
  public String getContact_describe_id() {
    return contact_describe_id;
  }

  /**
   * @param contact_describe_id the contact_describe_id to set
   */
  public void setContact_describe_id(String contact_describe_id) {
    this.contact_describe_id = contact_describe_id;
  }

}
