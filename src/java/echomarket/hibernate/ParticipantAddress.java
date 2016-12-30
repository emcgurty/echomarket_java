package echomarket.hibernate;

public class ParticipantAddress implements java.io.Serializable {

  private Participant part;
  private String addressType;

  public ParticipantAddress() {
  }
    
  public ParticipantAddress(Participant part, String addressType) {
    this.part = part;
    this.addressType = addressType;

  }

  /**
   * @return the part
   */
  public Participant getPart() {
    return part;
  }

  /**
   * @param part the part to set
   */
  public void setPart(Participant part) {
    this.part = part;
  }

  /**
   * @return the addressType
   */
  public String getAddressType() {
    return addressType;
  }

  /**
   * @param addressType the addressType to set
   */
  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }
  
  
}
