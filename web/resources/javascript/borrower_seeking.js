$(document).ready(function () {

  if ($("input[id$='organizationName']").val()) {
    $("div[id$='yesNoOrganization']").css("display", "block");
  }

  var contactAddressCheck = $("input");
  findContactAddressValue(contactAddressCheck);
  function findContactAddressValue(cad) {
    var foundValue = 0;
    var y_n = 0;
    for (var i = 0; i < cad.length; i++) {
      var id = cad[i].id;
      if (cad[i].checked == true) {
        if (id.includes('useWhichContactAddress')) {
          if ((id.includes('1')) || (id.includes('2'))) {
            $("div[id$='buildAlternativeAddress']").css("display", "block");
            foundValue++;
          }
        }
      }

      if (id.includes('thirdPartyPresenceL2b')) {

        if (id.includes('0')) {
          $("div#thirdPartyPresenceL2BGroup").css("display", "block");

          foundValue++;
        }
      }
      if (id.includes('thirdPartyPresenceB2l')) {
        if (id.includes('0')) {
          $("div#thirdPartyPresenceB2lGroup").css("display", "block");

          foundValue++;
        }
      }

      if (foundValue == 4) {
        break;
      }
    }
  }

  function validateSelects(input) {
    // Need to utilize include() here
    $("span.error-message").css("visibility", "hidden");
    var returnResult = false;
    var select_value = input.value;
    var select_id = getChildID(input.id);
    //        On database all 'Please select' have value -2
    if (select_value == -2) {

      $("span#" + select_id + ".error-message").css("visibility", "visible");
      $("span#" + select_id).text("Please make a selection.");
    } else {
      $("span#" + select_id + ".error-message").css("visibility", "hidden");
      $("span#" + select_id).text("");
      returnResult = true;
    }

    if ((select_id == 'contact_describe_id') && (select_value == '10')) {
      $("div#otherDescribeYourselfText").css("display", "block");
    } else {
      $("input#otherDescribeYourself").html("");
      $("div#otherDescribeYourselfText").css("display", "none");
    }

    if ((select_id == 'categoryId') && (select_value == 0)) {
      $("div#other_category").css("display", "block");
    } else if ((select_id == 'categoryId') && (select_value == -2)) {
      $("span#" + select_id + ".error-message").css("visibility", "visible");
      $("span#" + select_id + ".error-message").text("Please make a selection.");
      $("input#otherItemCategory").text("");
      $("div#other_category").css("display", "none");
    } else if ((select_id == 'categoryId') && (select_value > 0)) {
      $("span#" + select_id + ".error-message").css("visibility", "hidden");
      $("span#" + select_id + ".error-message").text("");
      $("input#otherItemCategory").text("");
      $("div#other_category").css("display", "none");
    }

    if ((select_id == 'usStateId') && (select_value != '-2')) {

      $("select[id$='countryId']").val("US");
    }
    if ((select_id == 'usStateIda') && (select_value != '-2')) {

      $("select[id$='countryIda']").val("US");
    }




    return returnResult;
  }


  $('select').on('change', function () {
    validateSelects(this);
  });
  $('input').on('change', function () {

    var y_n = null;
    var getOrgNameVal = null;
    if (this.id.includes('useWhichContactAddress')) {
      y_n = $(this).val();
      if ((y_n == 1) || (y_n == 2)) {
        $("div#buildAlternativeAddress").css("display", "block");
      } else {
        $("div#buildAlternativeAddress").css("display", "none");
      }
    } else if (this.id.includes('thirdPartyPresenceL2b')) {
      y_n = $(this).val();

      if (y_n == 1) {
        $("div#thirdPartyPresenceL2BGroup").css("display", "block");
      } else {
        $("div#thirdPartyPresenceL2BGroup").css("display", "none");
      }
    } else if (this.id.includes('thirdPartyPresenceB2l')) {
      y_n = $(this).val();

      if (y_n == 1) {
        $("div#thirdPartyPresenceB2lGroup").css("display", "block");
      } else {
        $("div#thirdPartyPresenceB2lGroup").css("display", "none");
      }

    } else if (this.id.includes('displayBorrowerOrganizationName')) {
      y_n = $(this).val();
      try {
        getOrgNameVal = $("input[class='org_name']").val();
      } catch (err) {

      }

      if ((y_n == 1) && (getOrgNameVal == "")) {
        $("span#organizationName.error-message").css("visibility", "visible");
        $("span#organizationName.error-message").text("Please provide an organization name.");
      } else {
        $("span#organizationName.error-message").text("");
        $("span#organizationName.error-message").css("visibility", "hidden");
      }
    } else if (this.id.includes('displayHomePhone')) {
      y_n = $(this).val();
      try {
        var getHomePhone = $("input[class='homePhone']").val();
      } catch (err) {

      }

      if ((y_n == 1) && (getHomePhone == "")) {
        $("span#homePhone.error-message").css("visibility", "visible");
        $("span#homePhone.error-message").text("Please provide your Home Phone.");
      } else {
        $("span#homePhone.error-message").text("");
        $("span#homePhone.error-message").css("visibility", "hidden");
      }
    } else if (this.id.includes('displayCellPhone')) {
      y_n = $(this).val();
      try {
        var getCellPhone = $("input[class='cellPhone']").val();
      } catch (err) {

      }

      if ((y_n == 1) && (getCellPhone == "")) {
        $("span#cellPhone.error-message").css("visibility", "visible");
        $("span#cellPhone.error-message").text("Please provide your Cell Phone.");
      } else {
        $("span#cellPhone.error-message").text("");
        $("span#cellPhone.error-message").css("visibility", "hidden");
      }
    } else if (this.id.includes('displayAlternativePhone')) {
      y_n = $(this).val();
      try {
        var getAlternativePhone = $("input[class='alternativePhone']").val();
      } catch (err) {

      }

      if ((y_n == 1) && (getAlternativePhone == "")) {
        $("span#alternativePhone.error-message").css("visibility", "visible");
        $("span#alternativePhone.error-message").text("Please provide your Alternative Phone.");
      } else {
        $("span#alternativePhone.error-message").text("");
        $("span#alternativePhone.error-message").css("visibility", "hidden");
      }


    } else if (this.id.includes('organizationName')) {
      $("div#yesNoOrganization").css("display", "block");
    } else if (this.id.includes('itemCount')) {

      if (isNaN(this.value)) {
        $("span#itemCount.error-message").css("visibility", "visible");
        $("span#itemCount.error-message").text("Please provide an item count");
      } else {
        $("span#itemCount.error-message").text("");
        $("span#itemCount.error-message").css("visibility", "hidden");
      }

    } else if (this.id.includes('itemDescription')) {

      if (this.value == '') {
        $("span#itemDescription.error-message").css("visibility", "visible");
        $("span#itemDescription.error-message").text("Please provide an item description");
      } else {
        $("span#itemDescription.error-message").text("");
        $("span#itemDescription.error-message").css("visibility", "hidden");
      }
//
    } else if (this.id.includes('imageFileNamePart')) {

      displayImage(this);
//
    } else {
      return false;
    }

  });
  function displayImage(input) {

    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        var imagePreviewID = $("img[id$=imagePreview]");
        imagePreviewID.attr('src', e.target.result);
      }

      reader.readAsDataURL(input.files[0]);
    }

  }

});
function showBLegal() {
  var returnResult = validateItem();
  if (returnResult) {
    hideAllBFormHrefs();
    $("li#tab_item_1").css("display", "block");
    $("li#tab_item_2").css("display", "block");
    $("li#tab_item_3").css("display", "block");
    $("li#tab_item_4").css("display", "block");
    whichSaveMenu(1);
    $("#form_legal").css("display", "block");
  }
  return false;
}

function ValdateThenShowReview() {
  var validL = checkBLegal();
  if (validL) {
    showReview();
  }

}



function showLegal() {
  hideAllBFormHrefs();
//    $("li#tab_item_1").css("display", "block");
//    $("li#tab_item_2").css("display", "block");
//    $("li#tab_item_3").css("display", "block");
//    $("li#tab_item_4").css("display", "block");
  whichSaveMenu(1);
  $("#form_legal").css("display", "block");
  return false;
}
function showConditions() {
  hideAllBFormHrefs();
//    $("li#tab_item_1").css("display", "block");
//    $("li#tab_item_2").css("display", "block");
//    $("li#tab_item_3").css("display", "block");
//    $("li#tab_item_4").css("display", "block");
  whichSaveMenu(1);
  $("#form_conditions").css("display", "block");
  return false;
}


function validateItem() {

  var return_value = true;
  var item_id = null;
  var item_value = null;
  var getItemInputs = $("input[type=text].items");
  for (var i = 0; i < getItemInputs.length; i++) {
    item_id = getItemInputs[i].id;
    item_id = item_id.replace('rb:', '');
    item_value = getItemInputs[i].value;
    if (item_id.includes('itemCount')) {

      if ((isNaN(item_value)) || (item_value == '')) {
        $("span#" + item_id + ".error-message").css("visibility", "visible");
        $("span#" + item_id + ".error-message").text("Please provide an item count.");
        return_value = false;
      }

    } else if (item_id.includes('itemDescription')) {

      if (item_value == '') {
        $("span#" + item_id + ".error-message").css("visibility", "visible");
        $("span#" + item_id + ".error-message").text("Please provide a item description.");
        return_value = false;
      }
    } else if (item_id.includes('otherItemCategory')) {
      if ($("div#other_category").css("display") == 'block') {
        if (item_value == '') {

          $("span#" + item_id + ".error-message").css("visibility", "visible");
          $("span#" + item_id + ".error-message").text("Please provide your Item Category.");
          return_value = false;
        }
      }

    } else {
    }


  }

  var getItemSelect = $(".items.select");
  for (var i = 0; i < getItemSelect.length; i++) {
    item_id = getChildID(getItemSelect[i].id);
    item_value = getItemSelect[i].value;
//        if (item_id.includes('itemConditionId')) {
    if (item_value == -2) {

      $("span#" + item_id + ".error-message").css("visibility", "visible");
      $("span#" + item_id + ".error-message").text("Please make a selection.");
      return_value = false;
    }
  }

  return return_value;
}

function validateAlternativeAddress() {


  var return_value = true;
  if ($("div#buildAlternativeAddress").css("display") == 'block') {

    var item_id = null;
    var item_value = null;
    var getItemInputs = $("input[type=text].alternativeAddress");
    for (var i = 0; i < getItemInputs.length; i++) {
      item_id = getChildID(getItemInputs[i].id);
      item_value = getItemInputs[i].value;
      if (item_id.includes('addressLine1')) {

        if (item_value == '') {

          $("span#" + item_id + "_alternative.error-message").css("visibility", "visible");
          $("span#" + item_id + "_alternative.error-message").text("Please provide your first Address Line.");
          return_value = false;
        }
      } else if (item_id.includes('city')) {

        if (item_value == '') {
          try {
            $("span#" + item_id + "_alternative.error-message").css("visibility", "visible");
          } catch (err) {

          }
          $("span#" + item_id + "_alternative.error-message").text("Please provide your City.");
          return_value = false;
        }
      } else if (item_id.includes('postalCode')) {

        if (item_value == '') {
          $("span#" + item_id + "_alternative.error-message").css("visibility", "visible");
          $("span#" + item_id + "_alternative.error-message").text("Please provide your Postal Code.");
          return_value = false;
        }
      } else {
      }


    }

    var getItemSelect = $(".alternativeAddress.select");
    for (var i = 0; i < getItemSelect.length; i++) {
      item_id = getChildID(getItemSelect[i].id);
      item_value = getItemSelect[i].value;
      if (item_id.includes('countryId')) {
        if (item_value == -2) {
          $("span#" + item_id + "_alternative.error-message").css("visibility", "visible");
          $("span#" + item_id + "_alternative.error-message").text("Please make a selection.");
          return_value = false;
        }
      }
    }
  }
  return return_value;
}

function hideAllBFormHrefs() {
  clearMenu();
  $(".contact_information").css("display", "none");
  return false;
}


function showBorrowerToLender() {
  hideAllBFormHrefs();
//    $("li[id^=tab_item_1]").css("display", "block");
  whichSaveMenu(1);
  $("#form_borrower_to_lender").css("display", "block");
  return false;
}

function showLenderToBorrower() {
  hideAllBFormHrefs();
//    $("li[id^=tab_item_1]").css("display", "block");
  whichSaveMenu(1);
  $("#form_lender_to_borrower").css("display", "block");
  return false;
}

function showFormContact() {
  hideAllBFormHrefs();
  $("li[id^=tab_item_1]").css("display", "block");
  whichSaveMenu(1);
  $("#form_contact_information").css("display", "block");
  return false;
}

function showLoginUpdate() {
  hideAllBFormHrefs();
  $("li[id^=tab_item_1]").css("display", "block");
  whichSaveMenu(1);
  $("#form_login_information").css("display", "block");
  return false;
}


function showMonetary() {
  hideAllBFormHrefs();
//    $("li#tab_item_1").css("display", "block");
//    $("li#tab_item_2").css("display", "block");
//    $("li#tab_item_3").css("display", "block");
  whichSaveMenu(1);
  $("#form_monetary").css("display", "block");
  return false;
}

function showDuration() {
  hideAllBFormHrefs();
//    $("li#tab_item_1").css("display", "block");
//    $("li#tab_item_2").css("display", "block");
//    $("li#tab_item_3").css("display", "block");
  whichSaveMenu(1);
  $("#form_duration").css("display", "block");
  return false;
}


function showItem() {
  var returnResult = validateAlternativeAddress();
  if (returnResult == true) {
    hideAllBFormHrefs();
    $("li#tab_item_1").css("display", "block");
    $("li#tab_item_2").css("display", "block");
    $("li#tab_item_3").css("display", "block");
    whichSaveMenu(1);
    $("#form_item").css("display", "block");
  }
  return false;
}

function showBorrowersContactPreferences() {

  var returnResult = ValidateContactInformation();
  if (returnResult == true) {
    displayPhone();
    hideAllBFormHrefs();
    $("li#tab_item_1").css("display", "block");
    $("li#tab_item_2").css("display", "block");
    whichSaveMenu(1);
    $("#form_borrower_preference").css("display", "block");
  }

  return false;
}


function showReview() {
  $("a.right_div").css("display", "none");
  $("div.top").css("display", "none");
  $("div.top.review").css("display", "block");
  $("a.right_div.review").css("display", "block");
  $("li#tab_item_1").css("display", "block");
  $("li#tab_item_2").css("display", "block");
  $("li#tab_item_3").css("display", "block");
  $("li#tab_item_4").css("display", "block");
  whichSaveMenu(1);
  $("div[class=contact_information]").css("display", "block");
  $("div[id^=menu_item_]").css("display", "none");
  return false;
}

function size_of_menu() {

  var bmw = $("ul#nav.borrower_menu").css("width");
  var lmw = $("ul#nav.lender_menu").css("width");
  var imw = $("div#index_div").css("width");
  var div_top = $("div#content.center_content").css("width");
  if (bmw) {

    $("div.borrower_registration").css("width", bmw);
    $("div.application_footer").css("width", bmw);
  } else if (imw) {

    $("div.application_footer").css("width", imw);
  } else if (lmw) {

    $("div.lender_registration").css("width", lmw);
    $("div.application_footer").css("width", lmw);
  } else {
    $("div.application_footer").css("width", div_top);
  }

  return true;
}

function clearMenu() {
  $("li[id^=tab_item_]").css("display", "none");
  return false;
}

function whichSaveMenu(whichSave) {

//    if (whichSave == 1) {
//        $("li#save_later").css("display", "block");
//        $("li#save_now").css("display", "none");
//    } else {
//        $("li#save_now").css("display", "block");
//        $("li#save_later").css("display", "none");
//    }
  return false;
}


function ValidateContactInformation() {

  var returnValue = true;
  var foundComponent = 0;
  var cdi = null;
  var ocdi = null;
  var firstName = null;
  var lastName = null;
  var addressLine1 = null;
  var city = null;
  var postalCode = null;
  var countryId = null
  var orgName = null;
  var uid = null;
  var this_class = null;
  $("span.error-message").css("visibility", "hidden");
  $("select").each(function () {
    uid = getChildID(this.id);
    this_class = $(this).attr("class");
    if (this.id.includes('contact_describe_id')) {
      cdi = this.value;
      foundComponent++;
    } else if ((this.id == 'countryId') && (this_class == 'primary')) {
      countryId = this.value;

      foundComponent++;
    } else if (foundComponent == 2) {
      return false;
    } else {
    }
  });
  foundComponent = 0;
  $("input").each(function () {
    uid = getChildID(this.id);
    this_class = $(this).attr("class");
    if (uid.includes('otherDescribeYourself')) {
      ocdi = this.value;
      foundComponent++;
    } else if (uid == 'organizationName') {
      orgName = this.value;
      foundComponent++;
    } else if (uid == 'firstName') {
      firstName = this.value;
      foundComponent++;
    } else if (uid == 'lastName') {
      lastName = this.value;
      foundComponent++;
    } else if ((uid == 'addressLine1') && (this_class == 'primary')) {
      addressLine1 = this.value;
      foundComponent++;
    } else if ((uid == 'city') && (this_class == 'primary')) {
      city = this.value;
      foundComponent++;
    } else if ((uid == 'postalCode') && (this_class == 'primary')) {
      postalCode = this.value;
      foundComponent++;
    } else if (foundComponent == 10) {

      return false;
    }

  });

  if ((cdi == 100) && (ocdi == '')) {
    $("span#otherDescribeYourself.error-message").css("visibility", "visible");
    $("span#otherDescribeYourself.error-message").text("Please provide an 'Other' description");
    returnValue = false;
  }

  if (cdi == -2) {

    $("span#contact_describe_id.error-message").css("visibility", "visible");
    $("span#contact_describe_id.error-message").text("Please make a selection");
    returnValue = false;
  }

  if (countryId == -2) {
    $("span#countryId.error-message").css("visibility", "visible");
    $("span#countryId.error-message").text("Please provide your Country");
    returnValue = false;
  }

  if (firstName == '') {
    $("span#firstName.error-message").css("visibility", "visible");
    $("span#firstName.error-message").text("Please provide your First Name");
    returnValue = false;
  }

  if (lastName == '') {
    $("span#lastName.error-message").css("visibility", "visible");
    $("span#lastName.error-message").text("Please provide your Last Name");
    returnValue = false;
  }

  if (city == '') {
    $("span#city.error-message").css("visibility", "visible");
    $("span#city.error-message").text("Please provide your City");
    returnValue = false;
  }

  if (postalCode == '') {
    $("span#postalCode.error-message").css("visibility", "visible");
    $("span#postalCode.error-message").text("Please provide your PostalCode");
    returnValue = false;
  }

  if (addressLine1 == '') {
    $("span#addressLine1.error-message").css("visibility", "visible");
    $("span#addressLine1.error-message").text("Please provide your First Address Line");
    returnValue = false;
  }

  return returnValue;
}

function displayPhone() {

  var homePhone = null;
  var cellPhone = null;
  var alternativePhone = null;
  var foundComponent = 0;
  $("input").each(function () {
    if (this.id == 'rb:homePhone') {
      homePhone = this.value;
      foundComponent++;
    } else if (this.id == 'rb:cellPhone') {
      cellPhone = this.value;
      foundComponent++;
    } else if (this.id == 'rb:alternativePhone') {
      alternativePhone = this.value;
      foundComponent++;
    } else if (foundComponent == 3) {
      return false;
    }
  });
  try {
    if (homePhone) {
      $("div#display_homePhone.phone").css("display", "block");
    } else {
      $("div#display_homePhone.phone").css("display", "none");
    }
  } catch (err) {
    $("div#display_homePhone.phone").css("display", "none");
  }

  try {
    if (cellPhone) {
      $("div#display_cellPhone.phone").css("display", "block");
    } else {
      $("div#display_cellPhone.phone").css("display", "none");
    }
  } catch (err) {
    $("div#display_cellPhone.phone").css("display", "none");
  }

  try {
    if (alternativePhone) {
      $("div#display_alternativePhone.phone").css("display", "block");
    } else {
      $("div#display_alternativePhone.phone").css("display", "none");
    }
  } catch (err) {
    $("div#display_alternativePhone.phone").css("display", "none");
  }

  if ((homePhone) || (cellPhone) || (alternativePhone)) {
    $("div#display_phone.div_p").css("display", "block");
  } else {
    $("div#display_phone.div_p").css("display", "none");
  }


}

function getChildID(parentID) {
  var child_id = null;
  child_id = parentID.replace('rb:', '');
  var lastColon = child_id.lastIndexOf(":");
  if (lastColon > 0) {
    var lengthOfSelectId = child_id.length;
    child_id = child_id.substring(lastColon + 1, lengthOfSelectId);
  }
  return child_id;
}


function removeImage() {
  var imagePreviewID = $("img[id$=imagePreview]");
  imagePreviewID.attr('src', '');
  var imageFileInput = $("input[id$=imageFileName]");
  // imageFileInput.replaceWith( imageFileInput.val('').clone( true ) );
  // Really bad, disables ImagePreview functionality
  imageFileInput.val("");
}

