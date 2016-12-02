package echomarket.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.mail.Part;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "participant")
public class Participant implements java.io.Serializable {

  private String participant_id;
  private String userId;
  private String communityId;
  private int contactDescribeId;
  private String organizationName;
  private Integer displayOrganization;
  private String otherDescribeYourself;
  private String firstName;
  private String mi;
  private String lastName;
  private String alias;
  private Integer displayName;
  private Integer displayAddress;
  private String homePhone;
  private String cellPhone;
  private String alternativePhone;
  private String emailAlternative;
  private Integer displayHomePhone;
  private Integer questionAltEmail;
  private Integer questionAltAddress;
  private Integer displayCellPhone;
  private Integer displayAlternativePhone;
  private Integer displayAlternativeAddress;
  private Integer goodwill;
  private Integer age18OrMore;
  private Integer isActive;
  private Integer editable;
  private Integer isCreator;
  private Date dateCreated;
  private Date dateUpdated;
  private Date dateDeleted;
  private String remoteIp;
  private int approved;
  private Set<Addresses> addresses = new HashSet<Addresses>();
  private Set<ContactPreference> contactPreference = new HashSet<ContactPreference>();
  private Set<Items> item = new HashSet<Items>();

  public Participant() {
  }

  /// User agreement
  public Participant(String participant_id, String userId, Integer goodwill, Integer age18OrMore, Integer isActive, Date dateCreated, String remoteIp) {
    this.participant_id = participant_id;
    this.userId = userId;
    this.goodwill = goodwill;
    this.age18OrMore = age18OrMore;
    this.isActive = isActive;
    this.dateCreated = new Date();
    this.remoteIp = remoteIp;
    this.approved = 1;
  }
  // Update 

  public Participant(String participant_id, String userId, int contactDescribeId, String organizationName, int displayOrganization, String otherDescribeYourself, String firstName, String mi, String lastName, String alias, int displayName, int displayAddress, String homePhone, String cellPhone,
          String alternativePhone, String emailAlternative, Integer displayHomePhone, Integer displayCellPhone, Integer displayAlternativePhone, Integer displayAlternativeAddress, String remoteIp) {
    this.participant_id = participant_id;
    this.userId = userId;
    this.communityId = null;
    this.contactDescribeId = contactDescribeId;
    this.organizationName = organizationName;
    this.displayOrganization = displayOrganization;
    this.otherDescribeYourself = otherDescribeYourself;
    this.firstName = firstName;
    this.mi = mi;
    this.lastName = lastName;
    this.alias = alias;
    this.displayName = displayName;
    this.displayAddress = displayAddress;
    this.homePhone = homePhone;
    this.cellPhone = cellPhone;
    this.alternativePhone = alternativePhone;
    this.emailAlternative = emailAlternative;
    this.displayHomePhone = displayHomePhone;
    this.displayCellPhone = displayCellPhone;
    this.displayAlternativePhone = displayAlternativePhone;
    this.displayAlternativeAddress = displayAlternativeAddress;
    this.remoteIp = remoteIp;

  }

  /**
   * @return the addresses
   */
  @OneToMany
  @JoinTable(name = "echomarket.hibernate.Addresses")
  @JoinColumn(name = "participant_id")
  public Set<Addresses> getAddresses() {
    return addresses;
  }

  /**
   * @param addresses the addresses to set
   */
  public void setAddresses(Set<Addresses> addresses) {
    this.addresses = addresses;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCommunityId() {
    return this.communityId;
  }

  public void setCommunityId(String communityId) {
    this.communityId = communityId;
  }

  public int getContactDescribeId() {
    return this.contactDescribeId;
  }

  public void setContactDescribeId(int contactDescribeId) {
    this.contactDescribeId = contactDescribeId;
  }

  public String getOrganizationName() {
    return this.organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public int getDisplayOrganization() {
    return this.displayOrganization;
  }

  public void setDisplayOrganization(int displayOrganization) {
    this.displayOrganization = displayOrganization;
  }

  public String getOtherDescribeYourself() {
    return this.otherDescribeYourself;
  }

  public void setOtherDescribeYourself(String otherDescribeYourself) {
    this.otherDescribeYourself = otherDescribeYourself;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMi() {
    return this.mi;
  }

  public void setMi(String mi) {
    this.mi = mi;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAlias() {
    return this.alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public int getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(int displayName) {
    this.displayName = displayName;
  }

  public int getDisplayAddress() {
    return this.displayAddress;
  }

  public void setDisplayAddress(int displayAddress) {
    this.displayAddress = displayAddress;
  }

  public String getHomePhone() {
    return this.homePhone;
  }

  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  public String getCellPhone() {
    return this.cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getAlternativePhone() {
    return this.alternativePhone;
  }

  public void setAlternativePhone(String alternativePhone) {
    this.alternativePhone = alternativePhone;
  }

  public String getEmailAlternative() {
    return this.emailAlternative;
  }

  public void setEmailAlternative(String emailAlternative) {
    this.emailAlternative = emailAlternative;
  }

  public Integer getDisplayHomePhone() {
    return this.displayHomePhone;
  }

  public void setDisplayHomePhone(Integer displayHomePhone) {
    this.displayHomePhone = displayHomePhone;
  }

  public Integer getDisplayCellPhone() {
    return this.displayCellPhone;
  }

  public void setDisplayCellPhone(Integer displayCellPhone) {
    this.displayCellPhone = displayCellPhone;
  }

  public Integer getDisplayAlternativePhone() {
    return this.displayAlternativePhone;
  }

  public void setDisplayAlternativePhone(Integer displayAlternativePhone) {
    this.displayAlternativePhone = displayAlternativePhone;
  }

  public Integer getDisplayAlternativeAddress() {
    return this.displayAlternativeAddress;
  }

  public void setDisplayAlternativeAddress(Integer displayAlternativeAddress) {
    this.displayAlternativeAddress = displayAlternativeAddress;
  }

  public Integer getGoodwill() {
    return this.goodwill;
  }

  public void setGoodwill(Integer goodwill) {
    this.goodwill = goodwill;
  }

  public Integer getAge18OrMore() {
    return this.age18OrMore;
  }

  public void setAge18OrMore(Integer age18OrMore) {
    this.age18OrMore = age18OrMore;
  }

  public Integer getIsActive() {
    return this.isActive;
  }

  public void setIsActive(Integer isActive) {
    this.isActive = isActive;
  }

  public Integer getEditable() {
    return this.editable;
  }

  public void setEditable(Integer editable) {
    this.editable = editable;
  }

  public Integer getIsCreator() {
    return this.isCreator;
  }

  public void setIsCreator(Integer isCreator) {
    this.isCreator = isCreator;
  }

  public Date getDateCreated() {
    return this.dateCreated;
  }

  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getDateUpdated() {
    return this.dateUpdated;
  }

  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  public Date getDateDeleted() {
    return this.dateDeleted;
  }

  public void setDateDeleted(Date dateDeleted) {
    this.dateDeleted = dateDeleted;
  }

  public String getRemoteIp() {
    return this.remoteIp;
  }

  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  public int getApproved() {
    return this.approved;
  }

  public void setApproved(int approved) {
    this.approved = approved;
  }

  /**
   * @return the questionAltEmail
   */
  public int getQuestionAltEmail() {
    return questionAltEmail;
  }

  /**
   * @param questionAltEmail the questionAltEmail to set
   */
  public void setQuestionAltEmail(int questionAltEmail) {
    this.questionAltEmail = questionAltEmail;
  }

  /**
   * @return the questionAltAddress
   */
  public int getQuestionAltAddress() {
    return questionAltAddress;
  }

  /**
   * @param questionAltAddress the questionAltAddress to set
   */
  public void setQuestionAltAddress(int questionAltAddress) {
    this.questionAltAddress = questionAltAddress;
  }

  /**
   * @return the participant_id
   */
  @Id
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
   * @return the contactPreference
   */
  @OneToMany
  @JoinTable(name = "echomarket.hibernate.ContactPreference")
  @JoinColumn(name = "participant_id")
  public Set<ContactPreference> getContactPreference() {
    return contactPreference;
  }

  /**
   * @param contactPreference the contactPreference to set
   */
  public void setContactPreference(Set<ContactPreference> contactPreference) {
    this.contactPreference = contactPreference;
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
