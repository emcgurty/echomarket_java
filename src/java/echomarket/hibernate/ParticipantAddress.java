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

  public Participant getPart() {
    return part;
  }

  public void setPart(Participant part) {
    this.part = part;
  }

  public String getAddressType() {
    return addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }
  
}
