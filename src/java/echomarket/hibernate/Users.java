package echomarket.hibernate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users implements java.io.Serializable {

  private String user_id;
  private String username;
  private String communityName;
  private String email;
  private Date dateCreated;
  private Date dateUpdated;
  private String remoteIp;
  private byte[] cryptedPassword;
  private byte[] salt;
  private String resetCode;
  private Date activatedAt;
  private String userAlias;
  private String userType;
  private Integer roleId;
  private Set<Participant> participant = new HashSet<Participant>();

  public Users() {
  }

  // This constructor is used to create new, community member
  public Users(String id, String username, String communityName, String email, String password, String userAlias, String userType) {
    String local_password = password;
    this.user_id = id;
    this.username = username;
    this.communityName = communityName;
    this.email = email;
    this.userAlias = userAlias;
    this.userType = userType;
    this.roleId = 2;
    this.salt = passTheSalt();
    this.cryptedPassword = passThePepper(password);
    // Later make sure this is unique in database
    this.resetCode = null;
    this.activatedAt = null;
    this.dateCreated = new Date();
    this.activatedAt = new Date();

  }

// This constructor is used to create new, non-activated user
  public Users(String id, String username, String communityName, String email, String password, String resetCode, String userAlias, String userType, Integer roleId) {
    String local_password = password;
    this.user_id = id;
    this.username = username;
    this.communityName = communityName;
    this.email = email;
    this.userAlias = userAlias;
    this.userType = userType;
    this.salt = passTheSalt();
    this.cryptedPassword = passThePepper(password);
    // Later make sure this is unique in database
    this.resetCode = BuildRandomValue();
    this.activatedAt = null;
    this.roleId = roleId;
    this.dateCreated = new Date();

  }

  // This constructor is used for non-password updates
  public Users(String id, String username, String email, String userAlias, String userType) {
    this.user_id = id;
    this.username = username;
    this.email = email;
    this.userAlias = userAlias;
    this.userType = userType;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRemoteIp() {
    return this.remoteIp;
  }

  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  public byte[] getCryptedPassword() {
    return this.cryptedPassword;
  }

  public void setCryptedPassword(byte[] cryptedPassword) {
    this.cryptedPassword = cryptedPassword;
  }

  public byte[] getSalt() {
    return this.salt;
  }

  public void setSalt(byte[] salt) {
    this.salt = salt;
  }

  public String getResetCode() {
    return this.resetCode;
  }

  public void setResetCode(String resetCode) {
    this.resetCode = resetCode;
  }

  public Date getActivatedAt() {
    return this.activatedAt;
  }

  public void setActivatedAt(Date activatedAt) {
    this.activatedAt = activatedAt;
  }

  public String getUserAlias() {
    return this.userAlias;
  }

  public void setUserAlias(String userAlias) {
    this.userAlias = userAlias;
  }

  public String getUserType() {
    return this.userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public Integer getRoleId() {
    return this.roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  private byte[] passThePepper(String password) {

    byte[] result = new byte[8];
    PasswordEncryptionService pes = new PasswordEncryptionService();

    try {
      result = pes.getEncryptedPassword(password, this.salt);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeySpecException ex) {
      Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.cryptedPassword = result;
    return result;

  }

  private byte[] passTheSalt() {

    byte[] shaker = new byte[8];
    PasswordEncryptionService pes = new PasswordEncryptionService();
//
    try {
      shaker = pes.generateSalt();

    } catch (NoSuchAlgorithmException e) {
    }
    pes = null;
    this.salt = shaker;
    return shaker;
  }

  public String BuildRandomValue() {

    String uuid = UUID.randomUUID().toString();
    return uuid;

  }

  /**
   * @return the user_id
   */
  @Id
  public String getUser_id() {
    return user_id;
  }

  /**
   * @param user_id the user_id to set
   */
  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  /**
   * @return the dateCreated
   */
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * @param dateCreated the dateCreated to set
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  /**
   * @return the dateUpdated
   */
  public Date getDateUpdated() {
    return dateUpdated;
  }

  /**
   * @param dateUpdated the dateUpdated to set
   */
  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  /**
   * @return the participant
   */
  @OneToMany
  @JoinTable(
          name = "echomarket.hibernate.Participant",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "participant_id")
  )
  public Set<Participant> getParticipant() {
    return participant;
  }

  /**
   * @param participant the participant to set
   */
  public void setParticipant(Set<Participant> participant) {
    this.participant = participant;
  }

  /**
   * @return the communityName
   */
  public String getCommunityName() {
    return communityName;
  }

  /**
   * @param communityName the communityName to set
   */
  public void setCommunityName(String communityName) {
    this.communityName = communityName;
  }

}
