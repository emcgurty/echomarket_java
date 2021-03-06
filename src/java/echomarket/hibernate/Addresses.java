package echomarket.hibernate;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Addresses implements java.io.Serializable {

  private String addressId;
  private String participant_id;
  private String addressLine1;
  private String addressLine2;
  private String postalCode;
  private String city;
  private String province;
  private String usStateId;
  private String region;
  private String countryId;
  private String addressType;

  /// On the user_nae gui there are two possible instances of addresses
  /// In the browser on autocomplete the other address objects is being changed
  private String addressLine1a;
  private String addressLine2a;
  private String postalCodea;
  private String citya;
  private String provincea;
  private String usStateIda;
  private String regiona;
  private String countryIda;
  private String addressTypea;
  private Set<Items> item = new HashSet<Items>();

  public Addresses() {
  }

  public Addresses(String addressId, String participant_id, String postalCode) {
    this.addressId = addressId;
    this.participant_id = participant_id;
    this.postalCode = postalCode;
  }

  public Addresses(String addressId, String participant_id, String addressLine1, String addressLine2, String postalCode, String city, String province, String usStateId, String region, String countryId, String addressType) {
    this.addressId = addressId;
    this.participant_id = participant_id;
    this.addressLine1 = addressLine1;
    this.addressLine2 = addressLine2;
    this.postalCode = postalCode;
    this.city = city;
    this.province = province;
    this.usStateId = usStateId;
    this.region = region;
    this.countryId = countryId;
    this.addressType = addressType;

  }

  @Id
  public String getAddressId() {
    return this.addressId;
  }

  public void setAddressId(String addressId) {
    this.addressId = addressId;
  }

  public String getAddressLine1() {
    return this.addressLine1;
  }

  public void setAddressLine1(String addressLine1) {
    this.addressLine1 = addressLine1;
  }

  public String getAddressLine2() {
    return this.addressLine2;
  }

  public void setAddressLine2(String addressLine2) {
    this.addressLine2 = addressLine2;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return this.province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getUsStateId() {
    return this.usStateId;
  }

  public void setUsStateId(String usStateId) {
    this.usStateId = usStateId;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getCountryId() {
    return this.countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getAddressType() {
    return this.addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  /**
   * @return the participant_id
   */
  public String getParticipant_id() {
    return participant_id;
  }

  /**
   * @param participant_id the participant_id to set
   */
  public void setParticipant_id(String participant_id) {
    this.participant_id = participant_id;
  }

  /**
   * @return the addressLine1a
   */
  public String getAddressLine1a() {
    return addressLine1a;
  }

  /**
   * @param addressLine1a the addressLine1a to set
   */
  public void setAddressLine1a(String addressLine1a) {
    this.addressLine1a = addressLine1a;
  }

  /**
   * @return the addressLine2a
   */
  public String getAddressLine2a() {
    return addressLine2a;
  }

  /**
   * @param addressLine2a the addressLine2a to set
   */
  public void setAddressLine2a(String addressLine2a) {
    this.addressLine2a = addressLine2a;
  }

  /**
   * @return the postalCodea
   */
  public String getPostalCodea() {
    return postalCodea;
  }

  /**
   * @param postalCodea the postalCodea to set
   */
  public void setPostalCodea(String postalCodea) {
    this.postalCodea = postalCodea;
  }

  /**
   * @return the citya
   */
  public String getCitya() {
    return citya;
  }

  /**
   * @param citya the citya to set
   */
  public void setCitya(String citya) {
    this.citya = citya;
  }

  /**
   * @return the provincea
   */
  public String getProvincea() {
    return provincea;
  }

  /**
   * @param provincea the provincea to set
   */
  public void setProvincea(String provincea) {
    this.provincea = provincea;
  }

  /**
   * @return the usStateIda
   */
  public String getUsStateIda() {
    return usStateIda;
  }

  /**
   * @param usStateIda the usStateIda to set
   */
  public void setUsStateIda(String usStateIda) {
    this.usStateIda = usStateIda;
  }

  /**
   * @return the regiona
   */
  public String getRegiona() {
    return regiona;
  }

  /**
   * @param regiona the regiona to set
   */
  public void setRegiona(String regiona) {
    this.regiona = regiona;
  }

  /**
   * @return the countryIda
   */
  public String getCountryIda() {
    return countryIda;
  }

  /**
   * @param countryIda the countryIda to set
   */
  public void setCountryIda(String countryIda) {
    this.countryIda = countryIda;
  }

  /**
   * @return the addressTypea
   */
  public String getAddressTypea() {
    return addressTypea;
  }

  /**
   * @param addressTypea the addressTypea to set
   */
  public void setAddressTypea(String addressTypea) {
    this.addressTypea = addressTypea;
  }

  /**
   * @return the item
   */
  @OneToMany
  @JoinTable(name = "echomarket.hibernate.Items")
  @JoinColumn(name = "participant_id")
  public Set<Items> getItem() {
    return item;
  }

  /**
   * @param item the item to set
   */
  public void setItem(Set<Items> item) {
    this.item = item;
  }

}
