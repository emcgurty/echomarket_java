package echomarket.web.managedbeans;

import echomarket.hibernate.Addresses;
import echomarket.hibernate.Communities;
import echomarket.hibernate.Participant;
import echomarket.hibernate.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.Id;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@SessionScoped
public class ParticipantBean extends AbstractBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String participant_id;
  private String communityId;
  private int contact_describe_id;
  private String organizationName;
  private int displayOrganization;
  private String otherDescribeYourself;
  private String firstName;
  private String firstNamea;
  private String mi;
  private String lastName;
  private String alias;
  private int displayName;
  private int displayAddress;
  private String homePhone;
  private String cellPhone;
  private String alternativePhone;
  private String emailAlternative;
  private int questionAltEmailProvide;
  private int questionAltEmailDelete;
  private int questionAltAddressProvide;
  private int questionAltAddressDelete;
  private Integer displayHomePhone;
  private Integer displayCellPhone;
  private Integer displayAlternativePhone;
  private Integer displayAlternativeAddress;
  private Integer goodwill;
  private Integer instructions;
  private Integer age18OrMore;
  private Integer threeStrikesYourOut;
  private Integer isCreator;
  private Date dateCreated;
  private Date dateUpdated;
  private Date dateDeleted;
  private String remoteIp;
  private int approved;

  private ArrayList<Addresses> primary
          = new ArrayList<Addresses>(Arrays.asList(new Addresses(null, null, null, null, null, null, null, null, null, null, "primary")));

  private ArrayList<Addresses> alternative
          = new ArrayList<Addresses>(Arrays.asList(new Addresses(null, null, null, null, null, null, null, null, null, null, "alternative")));

  public ArrayList<Addresses> getPrimary() {
    return primary;
  }

  //  emm 1.8
  public ParticipantBean() {

  }

  public ArrayList<Addresses> getAlternative() {
    return alternative;
  }

  public void setPrimary(ArrayList<Addresses> aPrimary) {
    this.primary = aPrimary;
  }

  public void setAlternative(ArrayList<Addresses> aAlternative) {
    this.alternative = aAlternative;
  }

  public Boolean notifyLenders() {
    // Not complete
    return true;
  }

  public String load_ud(String uid) {

    if (uid != null) {
      if ("-1".equals(uid) == true) {
        app.setEditable(-1);
        uid = app.getUser_id();
      } else if ("-1".equals(uid) == false) {
        if (app.getEditable() == 0) {
          app.setEditable(1);
        } else {
          app.setEditable(0);
        }
      }
    }
    /// emm 123
    app.setItemId("");
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String action = params.get("action");

    if (action != null) {
      if ("participant".equals(action)) {
        app.setEditable(1);
      }
    }

    List partlist = null;
    Participant pp = null;
    partlist = getCurrentParticipant(app.getParticipant_id());
    if (partlist.size() == 1) {
      pp = (Participant) partlist.get(0);
      this.participant_id = pp.getParticipant_id();
      this.communityId = pp.getCommunityId();
      this.contact_describe_id = pp.getContact_describe_id();
      this.organizationName = pp.getOrganizationName();
      this.displayOrganization = pp.getDisplayOrganization();
      this.otherDescribeYourself = pp.getOtherDescribeYourself();
      this.firstName = pp.getFirstName();
      this.mi = pp.getMi();
      this.lastName = pp.getLastName();
      this.alias = pp.getAlias();
      this.displayName = pp.getDisplayName();
      this.displayAddress = pp.getDisplayAddress();
      this.homePhone = pp.getHomePhone();
      this.cellPhone = pp.getCellPhone();
      this.alternativePhone = pp.getAlternativePhone();
      this.emailAlternative = pp.getEmailAlternative();
      this.displayHomePhone = pp.getDisplayHomePhone();
      this.displayCellPhone = pp.getDisplayCellPhone();
      this.displayAlternativePhone = pp.getDisplayAlternativePhone();

      this.displayAlternativeAddress = pp.getDisplayAlternativeAddress();
      if (pp.getQuestionAltAddress() == null) {
        this.questionAltAddressProvide = -9;
      } else {
        this.questionAltAddressProvide = pp.getQuestionAltAddress();
      }

      if (pp.getQuestionAltEmail() == null) {
        this.questionAltEmailProvide = -9;
      } else {
        this.questionAltEmailProvide = pp.getQuestionAltEmail();
      }

      this.goodwill = pp.getGoodwill();
      this.age18OrMore = pp.getAge18OrMore();
      this.isCreator = pp.getIsCreator();
      if (app != null) {
        app.setCommunityId(communityId);
        app.setParticipant_id(participant_id);
      }
    }
    getExistingAddress("primary");
    getExistingAddress("alternative");

    if (app != null) {
      if (app.getEditable() == -1) {
        return "user_agreement";
      } else {
        this.setQuestionAltAddressDelete(-9);
        this.setQuestionAltEmailDelete(-9);
//      return "user_nae.xhtml?faces-redirect=true";  ///lose data in redirect
        return "user_nae";
      }
    } else {
      return "user_nae";
    }

  }

  public String getUserDefinedAlternativeEmail(String pid) {
    System.out.println("Called getUserDefinedAlternativeEmail");
    Session sb;
    Transaction tx;
    sb = null;
    tx = null;
    List result = null;
    String alt_email = "";
    sb = hib_session();
    tx = sb.beginTransaction();

    try {
      String query = "FROM Participant WHERE user_id = :pid ";
      result = sb.createQuery(query)
              .setParameter("pid", pid)
              .list();
      tx.commit();
      if (result.size() > 0) {
        Participant part = (Participant) result.get(0);
        if (part.getEmailAlternative() != null) {
          alt_email = part.getEmailAlternative();
        }
      }
    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getCurrentB");
      e.printStackTrace();

    } finally {
      tx = null;
      sb = null;

    }
    if (alt_email.isEmpty() == true) {
      return "Not provided";
    } else {
      return alt_email;
    }
  }

  public String updateNAE() {
    // if questionAltAddress  == 1, delete alternative address
    List padrs = getPrimary();
    List aadrs = getAlternative();
    String reqPO = null;
    Addresses aalt = (Addresses) aadrs.get(0);
    reqPO = aalt.getPostalCode();
    String query = null;
    Session sb = null;
    Transaction tx = null;
    List result = null;
    String pid = null;
    Boolean updateSuccess = false;

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      query = "FROM Participant WHERE user_id = :uid";
      result = sb.createQuery(query)
              .setParameter("uid", app.getUser_id())
              .list();
      tx.commit();
    } catch (Exception ex) {
      System.out.println("Error in Save/Update Particpant, line 187");
      Logger
              .getLogger(ParticipantBean.class
                      .getName()).log(Level.SEVERE, null, ex);
      tx.rollback();
    } finally {
      tx = null;
      sb = null;
    }

    try {
      sb = hib_session();
      tx = sb.beginTransaction();
      Participant part = (Participant) result.get(0);
      pid = part.getParticipant_id();
      part.setCommunityId(communityId);
      part.setContact_describe_id(contact_describe_id);
      part.setOrganizationName(organizationName);
      part.setDisplayOrganization(displayOrganization);
      part.setOtherDescribeYourself(otherDescribeYourself);
      part.setFirstName(firstName);
      part.setMi(mi);
      part.setLastName(lastName);
      part.setDisplayName(displayName);
      part.setDisplayAddress(displayAddress);
      part.setHomePhone(homePhone);
      part.setCellPhone(cellPhone);
      part.setAlternativePhone(alternativePhone);
      if (reqPO.isEmpty() == true) {   /// If postalCode is empty dont save record
        part.setQuestionAltAddress(0);
      } else {
        part.setQuestionAltAddress(questionAltAddressProvide);
      }

      part.setQuestionAltEmail(questionAltEmailProvide);
      if (app.getCommunityName() != null) {
        part.setIsCreator(1);
      } else {
        part.setIsCreator(0);
      }

      if (this.getQuestionAltEmailProvide() == 0) {
        part.setEmailAlternative(null);
        part.setQuestionAltEmail(0);
      } else {
        part.setEmailAlternative(emailAlternative);
      }

      if (this.getQuestionAltEmailDelete() == 1) {
        part.setQuestionAltEmail(0);
        part.setEmailAlternative(null);
      }

      part.setDisplayHomePhone(displayHomePhone);
      part.setDisplayCellPhone(displayCellPhone);
      part.setDisplayAlternativePhone(displayAlternativePhone);
      part.setDisplayAlternativeAddress(displayAlternativeAddress);

      sb.update(part);
      tx.commit();
      updateSuccess = true;
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in Save/Update Particpant, line 228");
      Logger
              .getLogger(ParticipantBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {

      tx = null;
      sb = null;

      if (reqPO.isEmpty() == false) {

        try {
          sb = hib_session();
          tx = sb.beginTransaction();

          if (aalt.getAddressId() == null && this.getQuestionAltAddressProvide() == 1) {
            aalt.setParticipant_id(pid);
            aalt.setAddressId(UUID.randomUUID().toString());
            sb.save(aalt);
          } else if (aalt.getAddressId() != null && this.getQuestionAltAddressProvide() == 1) {
            sb.update(aalt);
          } else {
          }

          if ((aalt.getAddressId() != null) && ((this.getQuestionAltAddressDelete() == 1) || (this.getQuestionAltAddressProvide() == 0))) {
            Addresses addr = new Addresses();
            String current_id = aalt.getAddressId();
            addr.setAddressId(current_id);
            sb.delete(addr);
            ArrayList<Addresses> clear_alternative
                    = new ArrayList<Addresses>(Arrays.asList(new Addresses(null, null, null, null, null, null, null, null, null, null, "alternative")));
            setAlternative(clear_alternative);
            // sb.delete(aalt); this wasn't producing an immediate result in xhtml
            sb.flush();
          } else {
          }

          tx.commit();
          updateSuccess = true;
          aalt = null;
        } catch (Exception ex) {
          tx.rollback();
          System.out.println("Error in Save/Update Particpant, line 254");
          Logger
                  .getLogger(ParticipantBean.class
                          .getName()).log(Level.SEVERE, null, ex);

        } finally {
          tx = null;
          sb = null;
        }
      }
    }

    Addresses ba = (Addresses) padrs.get(0);
    reqPO = ba.getPostalCode();
    if (reqPO.isEmpty() == false) {
      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        if (ba.getAddressId() == null) {
          ba.setParticipant_id(pid);
          ba.setAddressId(UUID.randomUUID().toString());
          sb.save(ba);
        } else {
          sb.update(ba);
        }

        tx.commit();
        updateSuccess = true;
        //message(null, "ParticipantInformationRecordSaved", null);
      } catch (Exception ex) {
        tx.rollback();
        System.out.println("Error in Save/Update Particpant, 276");
        Logger
                .getLogger(ParticipantBean.class
                        .getName()).log(Level.SEVERE, null, ex);
        message(null, "ParticipantInformationRecordNotSaved", null);
      } finally {
        sb = null;
        tx = null;

      }
    }
    if (updateSuccess == true) {
      app.setParticipant_id(pid);
      app.setEditable(1);
      if (app.getCommunityId().isEmpty() == false) {
        app.setCreatorDetailID(updateSuccess); //allows the Community Members option to be available in menu
      } else {
        app.setPartID(updateSuccess);
      }
    } else {
      app.setEditable(0);
    }
    return load_ud(app.getUser_id());

  }

  public String userAgreement() {

    String return_string = null;
    Session sb = null;
    Transaction tx = null;
    List result = null;
    Participant part = null;
    String new_ID = null;
    String cid_id = null;
    if ((goodwill != 1) || (age18OrMore != 1)) {
      if (this.threeStrikesYourOut == null) {
        threeStrikesYourOut = 0;
      }
      this.threeStrikesYourOut = this.threeStrikesYourOut + 1;
      if (this.threeStrikesYourOut == 3) {
        message(null, "threeStrikesYourOut", null);
        return_string = app.Logout();
      }
    } else {

      try {
        sb = hib_session();
        tx = sb.beginTransaction();
        new_ID = getId();
        cid_id = UUID.randomUUID().toString();

        Communities comm = null;

        switch (app.getRoleId()) {
          case 0:
            part = new Participant(new_ID, app.getUser_id(), null, goodwill, age18OrMore, instructions, 1, new Date(), getClientIpAddr(), 0);
            break;
          case 1:
            comm = new Communities(cid_id);
            part = new Participant(new_ID, app.getUser_id(), cid_id, goodwill, age18OrMore, instructions, 1, new Date(), getClientIpAddr(), 1);
            sb.save(comm);
            break;
          case 2:
            part = new Participant(new_ID, app.getUser_id(), null, goodwill, age18OrMore, instructions, 1, new Date(), getClientIpAddr(), 0);
            break;
        }

        sb.save(part);
        tx.commit();
        app.setEditable(0);  /// which will be toggled as 1 = edit in load_ud
        app.setParticipant_id(new_ID);
        app.setAcceptID(true);
        app.setCommunityId(cid_id);
        result = app.findUserName();
        if (result != null) {
          if (result.size() == 1) {

            Users uu = (Users) result.get(0);  /// User result that has current user data
            Integer memberCreator = uu.getRoleId();
            app.setRoleId(memberCreator);
            app.setEmail(uu.getEmail());
            app.setUserAlias(uu.getUserAlias());
            app.setUsername(uu.getUsername());
            app.setUserType(uu.getUserType());
            String hold_ut = app.userIsWhichType();  // getting a WELD error
            app.setUserType(hold_ut);
            app.setUser_id(uu.getUser_id());
            app.setPartID(false);
            app.setCpId(false);
            app.setLICid(false);
            app.setLITid(false);
            app.setCommunityName(uu.getCommunityName());
            result = null;
          }
        }
        if (app.getRoleId() > 0) {
          //app.getCreatorDetail(app.getUser_id());  // sets CommunityName and userType
        }
        return_string = load_ud(app.getUser_id());
        message(null, "thanksForAcceptingAgreement", null);
      } catch (Exception ex) {
        message(null, "failedToSaveAgreement", null);
        return_string = app.Logout();
        tx.rollback();
        System.out.println("Error in Save/Update Particpant");
        Logger
                .getLogger(ParticipantBean.class
                        .getName()).log(Level.SEVERE, null, ex);
      } finally {
        sb = null;
        tx = null;

      }
    }
    return return_string;

  }

  private Boolean processAddress(Addresses[] address) {

    Session sb = hib_session();
    Transaction tx = sb.beginTransaction();
    Boolean b_return = false;
    try {
      sb.update(address);
      tx.commit();
      b_return = true;
    } catch (Exception ex) {
      tx.rollback();
      Logger
              .getLogger(ParticipantBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (sb != null) {
        sb.close();
      }
      tx = null;
      sb = null;

    }

    return b_return;
  }

  public int getContact_describe_id() {
    return contact_describe_id;
  }

  public void setContact_describe_id(int contactDescribeId) {
    this.contact_describe_id = contactDescribeId;
  }

  public String getOrganizationName() {
    if ((organizationName != null) || (app.getEditable() == 1)) {
      return organizationName;
    } else {
      return "Not provided";
    }

  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getOtherDescribeYourself() {
    if ((otherDescribeYourself != null) || (app.getEditable() == 1)) {
      return otherDescribeYourself;
    } else {
      return "Not provided";
    }
  }

  public void setOtherDescribeYourself(String otherDescribeYourself) {
    this.otherDescribeYourself = otherDescribeYourself;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMi() {
    if (mi == null) {
      return " ";
    } else {
      return mi;
    }
  }

  public void setMi(String mi) {
    this.mi = mi;
  }

  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the homePhone
   */
  public String getHomePhone() {
    return homePhone;
  }

  /**
   * @param homePhone the homePhone to set
   */
  public void setHomePhone(String homePhone) {
    this.homePhone = homePhone;
  }

  /**
   * @return the cellPhone
   */
  public String getCellPhone() {
    return cellPhone;
  }

  /**
   * @param cellPhone the cellPhone to set
   */
  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  /**
   * @return the alternativePhone
   */
  public String getAlternativePhone() {
    return alternativePhone;
  }

  /**
   * @param alternativePhone the alternativePhone to set
   */
  public void setAlternativePhone(String alternativePhone) {
    this.alternativePhone = alternativePhone;
  }

  /**
   * @return the emailAlternative
   */
  public String getEmailAlternative() {
//        if ((emailAlternative != null) || (app.getEditable() == 8)) {
    return emailAlternative;
//        } else {
//            return "Not provided";
//        }

  }

  /**
   * @param emailAlternative the emailAlternative to set
   */
  public void setEmailAlternative(String emailAlternative) {
    this.emailAlternative = emailAlternative;
  }

  /**
   * @return the goodwill
   */
  public Integer getGoodwill() {
    return goodwill;
  }

  /**
   * @param goodwill the goodwill to set
   */
  public void setGoodwill(Integer goodwill) {
    this.goodwill = goodwill;
  }

  /**
   * @return the age18OrMore
   */
  public Integer getAge18OrMore() {
    return age18OrMore;
  }

  /**
   * @param age18OrMore the age18OrMore to set
   */
  public void setAge18OrMore(Integer age18OrMore) {
    this.age18OrMore = age18OrMore;
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
   * @return the dateDeleted
   */
  public Date getDateDeleted() {
    return dateDeleted;
  }

  /**
   * @param dateDeleted the dateDeleted to set
   */
  public void setDateDeleted(Date dateDeleted) {
    this.dateDeleted = dateDeleted;
  }

  /**
   * @return the approved
   */
  public int getApproved() {
    return approved;
  }

  /**
   * @param approved the approved to set
   */
  public void setApproved(int approved) {
    this.approved = approved;
  }

  /**
   * @return the remoteIp
   */
  public String getRemoteIp() {
    return remoteIp;
  }

  /**
   * @param remoteIp the remoteIp to set
   */
  public void setRemoteIp(String remoteIp) {
    this.remoteIp = remoteIp;
  }

  public List getCurrentParticipant(String pid) {

    List result = null;
    Session session = null;
    Transaction tx = null;
    String query = null;
    try {
      session = hib_session();
      tx = session.beginTransaction();
      session.flush();  // delete result not appearing in xhtml
      String queryString = "FROM Participant WHERE participant_id = :pid GROUP BY participant_id ";
      result = session.createQuery(queryString)
              .setParameter("pid", pid)
              .list();
      tx.commit();
    } catch (Exception ex) {
      System.out.println("Error in getCurrentParticipant");
      Logger
              .getLogger(ParticipantBean.class
                      .getName()).log(Level.SEVERE, null, ex);
      tx.rollback();
    } finally {
      tx = null;
      session = null;

    }
    return result;
  }

  public String deleteCurrentRecord(String pid, String itemDesc) {

    List result = null;
    Session hib = null;
    Transaction tx = null;

    String queryString = "from Participant where participant_id = :pid";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("pid", pid)
              .list();
      if (result != null) {
        if (result.size() == 1) {
          hib.delete((Participant) result.get(0));
          tx.commit();
        }
      }
    } catch (Exception ex) {
      tx.rollback();
      System.out.println("Error in deleteCurrentRecord");
      Logger
              .getLogger(ParticipantBean.class
                      .getName()).log(Level.SEVERE, null, ex);
    } finally {
      result = null;
      tx = null;
      hib = null;

      message(null, "DeleteSelecteParticipant", new Object[]{itemDesc});
    }
    return "borrower_history";
  }

  /**
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  /**
   * @param alias the alias to set
   */
  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String load_borrower_seeking() {

    return "borrower_seeking.xhtml";
  }

  /**
   * @return the displayOrganization
   */
  public int getDisplayOrganization() {
    return displayOrganization;
  }

  /**
   * @param displayOrganization the displayOrganization to set
   */
  public void setDisplayOrganization(int displayOrganization) {
    this.displayOrganization = displayOrganization;
  }

  /**
   * @return the displayName
   */
  public int getDisplayName() {
    return displayName;
  }

  /**
   * @param displayName the displayName to set
   */
  public void setDisplayName(int displayName) {
    this.displayName = displayName;
  }

  /**
   * @return the displayAddress
   */
  public int getDisplayAddress() {
    return displayAddress;
  }

  /**
   * @param displayAddress the displayAddress to set
   */
  public void setDisplayAddress(int displayAddress) {
    this.displayAddress = displayAddress;
  }

  /**
   * @return the displayHomePhone
   */
  public Integer getDisplayHomePhone() {
    return displayHomePhone;
  }

  /**
   * @param displayHomePhone the displayHomePhone to set
   */
  public void setDisplayHomePhone(Integer displayHomePhone) {
    this.displayHomePhone = displayHomePhone;
  }

  /**
   * @return the displayCellPhone
   */
  public Integer getDisplayCellPhone() {
    return displayCellPhone;
  }

  /**
   * @param displayCellPhone the displayCellPhone to set
   */
  public void setDisplayCellPhone(Integer displayCellPhone) {
    this.displayCellPhone = displayCellPhone;
  }

  /**
   * @return the displayAlternativePhone
   */
  public Integer getDisplayAlternativePhone() {
    return displayAlternativePhone;
  }

  /**
   * @param displayAlternativePhone the displayAlternativePhone to set
   */
  public void setDisplayAlternativePhone(Integer displayAlternativePhone) {
    this.displayAlternativePhone = displayAlternativePhone;
  }

  /**
   * @param displayAlternativeAddress the displayAlternativeAddress to set
   */
  public void setDisplayAlternativeAddress(Integer displayAlternativeAddress) {
    this.displayAlternativeAddress = displayAlternativeAddress;
  }

  /**
   * @return the isCreator
   */
  public Integer getIsCreator() {
    return isCreator;
  }

  /**
   * @param isCreator the isCreator to set
   */
  public void setIsCreator(Integer isCreator) {
    this.isCreator = isCreator;
  }

  private void getExistingAddress(String which) {

    List result = null;
    ArrayList<Addresses> return_result = null;
    Session hib = null;
    Transaction tx = null;

    String queryString = "from Addresses where participant_id = :pid AND address_type = :which GROUP BY address_type ";
    try {
      hib = hib_session();
      tx = hib.beginTransaction();
      result = hib.createQuery(queryString)
              .setParameter("pid", app.getParticipant_id())
              .setParameter("which", which)
              .list();
      tx.commit();

    } catch (Exception e) {
      tx.rollback();
      System.out.println("Error in getExistingAddress in PartBean");
      e.printStackTrace();

    } finally {
      tx = null;
      hib = null;
    }

    Integer size_of_list = result.size();
    if ((size_of_list == 0) && ("alternative".equals(which))) {
      return_result = getAlternative();
    } else if ((size_of_list == 0) && ("primary".equals(which))) {
      return_result = getPrimary();
    } else if ((size_of_list > 0) && ("alternative".equals(which))) {
      Addresses alt = (Addresses) result.get(0);
      ArrayList<Addresses> new_alternative
              = new ArrayList<Addresses>(Arrays.asList(new Addresses(alt.getAddressId(), alt.getParticipant_id(), alt.getAddressLine1(), alt.getAddressLine2(),
                      alt.getPostalCode(), alt.getCity(), alt.getProvince(), alt.getUsStateId(), alt.getRegion(), alt.getCountryId(), alt.getAddressType())));
      setAlternative(new_alternative);
      return_result = getAlternative();
    } else if ((size_of_list > 0) && ("primary".equals(which))) {
      Addresses pri = (Addresses) result.get(0);
      ArrayList<Addresses> new_primary
              = new ArrayList<Addresses>(Arrays.asList(new Addresses(pri.getAddressId(), pri.getParticipant_id(), pri.getAddressLine1(), pri.getAddressLine2(),
                      pri.getPostalCode(), pri.getCity(), pri.getProvince(), pri.getUsStateId(), pri.getRegion(), pri.getCountryId(), pri.getAddressType())));
      setPrimary(new_primary);
      return_result = getPrimary();
    } else {
    }

    result = null;

  }

  /**
   * @return the displayAlternativeAddress
   */
  public Integer getDisplayAlternativeAddress() {
    return displayAlternativeAddress;
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
   * @return the communityId
   */
  public String getCommunityId() {
    return communityId;
  }

  /**
   * @param communityId the communityId to set
   */
  public void setCommunityId(String communityId) {
    this.communityId = communityId;
  }

  /**
   * @return the questionAltEmailProvide
   */
  public int getQuestionAltEmailProvide() {
    return questionAltEmailProvide;
  }

  /**
   * @param questionAltEmailProvide the questionAltEmailProvide to set
   */
  public void setQuestionAltEmailProvide(int questionAltEmailProvide) {
    this.questionAltEmailProvide = questionAltEmailProvide;
  }

  /**
   * @return the questionAltEmailDelete
   */
  public int getQuestionAltEmailDelete() {
    return questionAltEmailDelete;
  }

  /**
   * @param questionAltEmailDelete the questionAltEmailDelete to set
   */
  public void setQuestionAltEmailDelete(int questionAltEmailDelete) {
    this.questionAltEmailDelete = questionAltEmailDelete;
  }

  /**
   * @return the questionAltAddressProvide
   */
  public int getQuestionAltAddressProvide() {
    return questionAltAddressProvide;
  }

  /**
   * @param questionAltAddressProvide the questionAltAddressProvide to set
   */
  public void setQuestionAltAddressProvide(int questionAltAddressProvide) {
    this.questionAltAddressProvide = questionAltAddressProvide;
  }

  /**
   * @return the questionAltAddressDelete
   */
  public int getQuestionAltAddressDelete() {
    return questionAltAddressDelete;
  }

  /**
   * @param questionAltAddressDelete the questionAltAddressDelete to set
   */
  public void setQuestionAltAddressDelete(int questionAltAddressDelete) {
    this.questionAltAddressDelete = questionAltAddressDelete;
  }

  /**
   * @return the firstNamea
   */
  public String getFirstNamea() {
    return firstNamea;
  }

  /**
   * @param firstNamea the firstNamea to set
   */
  public void setFirstNamea(String firstNamea) {
    this.firstNamea = firstNamea;
  }

  /**
   * @return the instructions
   */
  public Integer getInstructions() {
    return instructions;
  }

  /**
   * @param instructions the instructions to set
   */
  public void setInstructions(Integer instructions) {
    this.instructions = instructions;
  }

  /**
   * @return the threeStrikesYourOut
   */
  public Integer getThreeStrikesYourOut() {
    return threeStrikesYourOut;
  }

  /**
   * @param threeStrikesYourOut the threeStrikesYourOut to set
   */
  public void setThreeStrikesYourOut(Integer threeStrikesYourOut) {
    this.threeStrikesYourOut = threeStrikesYourOut;
  }

}
