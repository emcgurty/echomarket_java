$.noConflict();
jQuery(document).ready(function ($) {

    size_of_menu();
    showFormContact();
    $(window).resize(function () {
        size_of_menu();
    });

    function validateSelects(input) {
        //alert(input.id);
        var returnResult = false;
        var select_value = input.value;
        var select_id = input.id;
        select_id = select_id.replace('rb:', '');
        //alert(select_value);
//        On database all 'Please select' have value -2
        if (select_value == '-2') {

            var lastColon = select_id.lastIndexOf(":");
            // alert(lastColon);
            var lengthOfSelectId = select_id.length;
            //alert(lengthOfSelectId);
            var select_id = select_id.substring(lastColon + 1, lengthOfSelectId);
            //alert(select_id);
            $("span#" + select_id).css("visibility", "visible");
            $("span#" + select_id).text("Please make a selection.");
        } else {
            $("span#" + select_id).css("visibility", "hidden");
            $("span#" + select_id).text("");
            returnResult = true
        }

        if ((select_id == 'contactDescribeId') && (select_value == '100')) {
//            Added this becuase rendered object are not retained in posting
            $("div#otherDescribeYourselfText").css("visibility", "visible");
        } else {
            $("input#otherDescribeYourself").html("");
            $("div#otherDescribeYourselfText").css("visibility", "hidden");


        }

        return returnResult;
    }


    $('select').on('change', function () {
        validateSelects(this);
    });


    $('input').on('change', function () {
           
        if (this.id.includes('useWhichContactAddress')) {
            if ((this.id.includes('1')) || (this.id.includes('2'))) {
             $("div#buildAlternativeAddress").css("display", "block");
           } else {
             $("div#buildAlternativeAddress").css("display", "none");
           }
          return false; 
        }
    });

    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#imagePreview').attr('src', e.target.result);
            }

            reader.readAsDataURL(input.files[0]);
        }

    }
    $("#itemImage").change(function () {
        readURL(this);
    });
    function makeorgright() {
//        var y_n = $("#displayBorrowerOrganizationName:checked").val();
//        
//        if ((y_n == 1) && ($("#organization_name").val() == "")) {
//            $("span#organization_name_error").text("Please provide an organization name.");
//            $("span#organization_name_error"))text("Please provide an organization name.");    
//        } else {
//            $("span#organization_name_error").text("");
//            $("span#organization_name_error").css("visibility", "hidden");
//        }

    }

    $("#displayBorrowerOrganizationName").change(function () {
        makeorgright();
    });


//    $("input[name='borrower[displayBorrowerAddress]']").bind('change', function () {
//        var y_n = $("input[name='borrower[displayBorrowerAddress]']:checked").val();
//        if (y_n == 1) {
//            if (
//                    ($(".borrower.primary.address_line_1").val() == "") ||
//                    ($(".borrower.primary.postal_code").val() == "") ||
//                    ($(".borrower.primary.city").val() == "") ||
//                    ($(".borrower.primary.country_id option:selected").text() == "Please select")
//                    ) {
//
//                $("span#display_address_error").text("Please provide a complete address.");
//                $("span#display_address_error").css("visibility", "visible");
//            } else {
//                $("span#display_address_error").text("");
//                $("span#display_address_error").css("visibility", "hidden");
//            }
//        }
//
//    });
//    $("input[name='borrower[displayBorrowerAddress]']").trigger('change');
//
//    $("input[name='borrower[displayBorrowerName]']").bind('change', function () {
//        var y_n = $("input[name='borrower[displayBorrowerName]']:checked").val();
//        if (y_n == 1) {
//            if (
//                    ($("#first_name").val() == "") ||
//                    ($("#last_name").val() == "")
//                    ) {
//
//                $("span#public_display_name_error").text("Please provide a complete name.");
//                $("span#public_display_name_error").css("visibility", "visible");
//            } else {
//                $("span#public_display_name_error").text("");
//                $("span#public_display_name_error").css("visibility", "hidden");
//            }
//        }
//
//    });
//    $("input[name='borrower[displayBorrowerName]']").trigger('change');
//
//    $("input[name='borrower[notify_lenders]']").bind('change', function () {
//        var y_n = $("input[name='borrower[notify_lenders]']:checked").val();
//        if (y_n == 1) {
//            $("span#category_notification_selection").text("Yes");
//        } else if (y_n == 0) {
//            $("span#category_notification_selection").text("No");
//        }
//    });
//    $("input[name='borrower[notify_lenders]']").trigger('change');
//    $("#age_18_or_more").bind('change', function () {
//        var is_c = $("#age_18_or_more").is(':checked');
//        if (is_c == true) {
//            $("span#18_selection").html("Yes");
//        } else if (is_c == false) {
//            $("span#18_selection").html("No");
//        }
//
//    });
//    $("#age_18_or_more").trigger('change');
//    $("#goodwill").bind('change', function () {
//        var is_c = $("#goodwill").is(':checked');
//        if (is_c == true) {
//            $("span#goodwill_selection").html("Yes");
//        } else if (is_c == false) {
//            $("span#goodwill_selection").html("No");
//        }
//
//    });
//    $("#goodwill").trigger('change');
//    $("#contact_by_home_phone").bind('change', function () {
//        if ($("#contact_by_home_phone").is(':checked')) {
//            $("span#home_phone_contact_permission_selection").html("Yes");
//        } else {
//            $("span#home_phone_contact_permission_selection").html("No");
//        }
//
//    });
//    $("#contact_by_home_phone").trigger('change');
//    $("#contact_by_cell_phone").bind('change', function () {
//        if ($("#contact_by_cell_phone").is(':checked')) {
//            $("span#cell_phone_contact_permission_selection").html("Yes");
//        } else {
//            $("span#cell_phone_contact_permission_selection").html("No");
//        }
//    });
//    $("#contact_by_cell_phone").trigger('change');
//    $("#contact_by_alternative_phone").bind('change', function () {
//        if ($("#contact_by_alternative_phone").is(':checked')) {
//            $("span#alternative_phone_contact_permission_selection").html("Yes");
//        } else {
//            $("span#alternative_phone_contact_permission_selection").html("No");
//        }
//    });
//    $("#contact_by_alternative_phone").trigger('change');
//    $("#contact_by_Facebook").bind('change', function () {
//
//        $("span#facebook_selection").html($(this).val());
//    });
//    $("#contact_by_Facebook").trigger('change');
//    $("#contact_by_Twitter").bind('change', function () {
//
//        $("span#twitter_selection").html($(this).val());
//    });
//    $("#contact_by_Twitter").trigger('change');
//    $("#contact_by_Instagram").bind('change', function () {
//
//        $("span#instagram_selection").html($(this).val());
//    });
//    $("#contact_by_Instagram").trigger('change');
//    $("#contact_by_LinkedIn").bind('change', function () {
//
//        $("span#linkedin_selection").html($(this).val());
//    });
//    $("#contact_by_LinkedIn").trigger('change');
//    $("#contact_by_Other_Social_Media").bind('change', function () {
//
//        $("span#other_media_selection").html($(this).val());
//    });
//    $("#contact_by_Other_Social_Media").trigger('change');
//    $("#contact_by_Other_Social_Media_Access").bind('change', function () {
//
//        $("span#other_media_access_selection").html($(this).val());
//    });
//    $("#contact_by_Other_Social_Media_Access").trigger('change');
//    $("#public_display_home_phone").bind('change', function () {
//
//        if (((($("#public_display_home_phone").prop('checked')))) && ((($("#home_phone").val() == '')))) {
//
//            $("span#home_phone_error").text("Whoops! Don't forget your home phone.");
//            $("span#home_phone_error").css("visibility", "visible");
//        } else {
//
//            $("span#home_phone_error").text("");
//            $("span#home_phone_error").css("visibility", "hidden");
//        }
//        if ($("#public_display_home_phone").prop('checked')) {
//            $("span#display_home_phone_selection").html("Yes");
//        } else {
//            $("span#display_home_phone_selection").html("No");
//        }
//
//    });
//    $("#public_display_home_phone").trigger('change');
//    $("#public_display_cell_phone").bind('change', function () {
//
//        if (((($("#public_display_cell_phone").prop('checked')))) && ((($("#cell_phone").val() == '')))) {
//
//            $("span#cell_phone_error").text("Whoops! Don't forget your cell phone.");
//            $("span#cell_phone_error").css("visibility", "visible");
//        } else {
//
//            $("span#cell_phone_error").text("");
//            $("span#cell_phone_error").css("visibility", "hidden");
//        }
//
//        if ($("#public_display_cell_phone").prop('checked')) {
//            $("span#display_cell_phone_selection").html("Yes");
//        } else {
//            $("span#display_cell_phone_selection").html("No");
//        }
//
//    });
//    $("#public_display_cell_phone").trigger('change');
//    $("#public_display_alternative_phone").bind('change', function () {
//
//        if (((($("#public_display_alternative_phone").prop('checked')))) && ((($("#alternative_phone").val() == '')))) {
//
//            $("span#alternative_phone_error").text("Whoops! Don't forget your alternative phone.");
//            $("span#alternative_phone_error").css("visibility", "visible");
//        } else {
//
//            $("span#alternative_phone_error").text("");
//            $("span#alternative_phone_error").css("visibility", "hidden");
//        }
//        if ($("#public_display_alternative_phone").prop('checked')) {
//            $("span#display_alternative_phone_selection").html("Yes");
//        } else {
//            $("span#display_alternative_phone_selection").html("No");
//        }
//    });
//    $("#public_display_alternative_phone").trigger('change');
//    $("#home_phone").bind('change', function () {
//
//        $("span#home_phone_error").text("");
//        $("span#home_phone_error").css("visibility", "hidden");
//        if ($("#home_phone").val() != '') {
//            if (($("#home_phone").val() == $("#cell_phone").val()) || ($("#home_phone").val() == $("#alternative_phone").val())) {
//
//                $("span#home_phone_error").text("Home phone should be distinct from others.");
//                $("span#home_phone_error").css("visibility", "visible");
//            }
//        }
//
//        if (($("#home_phone").val() == '') && ($("#public_display_home_phone").prop('checked') > -1)) {
//            $("span#home_phone_error").text("Empty Home phone will not be displayed.");
//            $("span#home_phone_error").css("visibility", "visible");
//        }
//
//        $("span#home_phone_contact_selection").html($(this).val());
//        $("span#contact_home_phone").html($(this).val());
//        $("span#home_phone_selection").html($(this).val());
//    });
//    $("#home_phone").trigger('change');
//    $("#cell_phone").bind('change', function () {
//        $("span#cell_phone_error").text("");
//        $("span#cell_phone_error").css("visibility", "hidden");
//        if ($("#cell_phone").val() != '') {
//            if (($("#cell_phone").val() == $("#home_phone").val()) || ($("#cell_phone").val() == $("#alternative_phone").val())) {
//
//                $("span#cell_phone_error").text("Cell phone should be distinct from others.");
//                $("span#cell_phone_error").css("visibility", "visible");
//            }
//        }
//
//        if (($("#cell_phone").val() == '') && ($("#public_display_cell_phone").prop('checked') > -1)) {
//            $("span#cell_phone_error").text("Empty Cell phone will not be displayed.");
//            $("span#cell_phone_error").css("visibility", "visible");
//        }
//        $("span#cell_phone_contact_selection").html($(this).val());
//        $("span#contact_cell_phone").html($(this).val());
//        $("span#cell_phone_selection").html($(this).val());
//    });
//    $("#cell_phone").trigger('change');
//    $("#alternative_phone").bind('change', function () {
//        $("span#alternative_phone_error").text("");
//        $("span#alternative_phone_error").css("visibility", "hidden");
//        if ($("#alternative_phone").val() != '') {
//            if (($("#alternative_phone").val() == $("#home_phone").val()) || ($("#alternative_phone").val() == $("#cell_phone").val())) {
//
//                $("span#alternative_phone_error").text("Alternative phone should be distinct from others.");
//                $("span#alternative_phone_error").css("visibility", "visible");
//            }
//        }
//        if (($("#alternative_phone").val() == '') && ($("#public_display_alternative_phone").prop('checked') > -1)) {
//            $("span#alternative_phone_error").text("Empty Alternative phone will not be displayed.");
//            $("span#alternative_phone_error").css("visibility", "visible");
//        }
//        $("span#alternative_phone_contact_selection").html($(this).val());
//        $("span#contact_alternative_phone").html($(this).val());
//        $("span#alternative_phone_selection").html($(this).val());
//    });
//    $("#alternative_phone").trigger('change');
//
//    $("#category_id").bind('change', function () {
//        var cat_cmb = $("select#category_id option:selected").text();
//
//        if (cat_cmb == "Other") {
//
//            $("#other_category").css("display", "inline-table");
//
//        } else {
//            $("#other_category").css('display', 'none');
//        }
//
//    });

//    $("#category_id").trigger('change');
//
//    $("#other_describe_yourself").bind('change', function () {
//        $("span#contact_describe_id").html($(this).val());
//
//    });
//    $("#other_describe_yourself").trigger('change');

//item_registration:contact_describe_id
//

//
//    $("input[name='borrower[contact_by_email]']").bind('change', function () {
//        $("span#email2_contact_error").css("visibility", "hidden");
//        $("span#email2_contact_error").text("");
//        var y_n = $("input[name='borrower[contact_by_email]']:checked").val();
//        if (y_n == 0) {
//            $("span#contact_by_email_selection").html("Neither Email");
//        } else if (y_n == 1) {
//            $("span#contact_by_email_selection").html("Either Email");
//            if ($("#email_alternative").val() == '') {
//                $("span#email2_contact_error").css("visibility", "visible");
//                $("span#email2_contact_error").text("Please provide an alternative email");
//            } else {
//                $("span#email2_contact_error").css("visibility", "hidden");
//                $("span#email2_contact_error").text("");
//            }
//        } else if (y_n == 2) {
//            $("span#contact_by_email_selection").html("Use Alternative Email");
//            if ($("#email_alternative").val() == '') {
//                $("span#email2_contact_error").css("visibility", "visible");
//                $("span#email2_contact_error").text("Please provide an alternative email");
//            } else {
//                $("span#email2_contact_error").css("visibility", "hidden");
//                $("span#email2_contact_error").text("");
//            }
//
//        } else if (y_n == 3) {
//            $("span#contact_by_email_selection").html("Use Login Email");
//
//        } else {
//        }
//
//    });
//    $("input[name='borrower[contact_by_email]']").trigger('change');
//
//    $("#email_alternative").bind('change', function () {
//        $("span#email_alternative_selection").html($(this).val());
//    });
//    $("#email_alternative").trigger('change');
//
//    $("#category_id").bind('change', function () {
//        var item_cat = $("#category_id option:selected").text();
//        $("span#category_selection").html(item_cat);
//    });
//    $("#category_id").trigger('change');
//
//    $("#other_item_category").bind('change', function () {
//        $("span#other_item_category_selection").html($(this).val());
//    });
//    $("#other_item_category").trigger('change');
//
//    $("#item_model").bind('change', function () {
//        $("span#item_model_selection").html($(this).val());
//    });
//    $("#item_model").trigger('change');
//
//    $("#item_description").bind('change', function () {
//        $("span#item_description_selection").html($(this).val());
//    });
//    $("#item_description").trigger('change');
//
//    $("#item_condition_id").bind('change', function () {
//        var item_cat = $("#item_condition_id option:selected").text();
//        $("span#item_condition_selection").html(item_cat);
//    });
//    $("#item_condition_id").trigger('change');
//
//    $(".borrower.primary.item_image_caption").bind('change', function () {
//        $("span#image_caption_selection").html($(this).val());
//    });
//    $(".borrower.primary.item_image_caption").trigger('change');
//
//    $("#item_count").bind('change', function () {
//        $("span#item_count_selection").html($(this).val());
//    });
//    $("#item_count").trigger('change');
//
//    /* Beginning of contact address */
//
//    /*   $("name^='borrower[primary_address]'").trigger('change'); */
//
//    $(".borrower.primary.address_line_1").bind('change', function () {
//        address1 = $(this).val();
//        $("span#address_1_selection").html($(this).val());
//    });
//    $(".borrower.primary.address_line_1").trigger('change');
//
//    $(".borrower.primary.address_line_2").bind('change', function () {
//        address2 = $(this).val();
//        $("span#address_2_selection").html($(this).val());
//    });
//    $(".borrower.primary.address_line_2").trigger('change');
//
//    $(".borrower.primary.province").bind('change', function () {
//        province = $(this).val();
//        $("span#province_selection").html($(this).val());
//
//    });
//    $(".borrower.primary.province").trigger('change');
//
//    $(".borrower.primary.city").bind('change', function () {
//        city = $(this).val();
//        $("span#city_selection").html($(this).val());
//
//    });
//    $(".borrower.primary.city").trigger('change');
//
//    $(".borrower.primary.postal_code").bind('change', function () {
//        postal_code = $(this).val();
//        $("span#postal_code_selection").html($(this).val());
//
//    });
//    $(".borrower.primary.postal_code").trigger('change');
//
//    $(".borrower.primary.us_state_id").bind('change', function () {
//
//        state_cmb = $(".borrower.primary.state_id option:selected").text();
//
//        if (state_cmb != 'Please select') {
//            $("span#us_state_selection").html(state_cmb);
//            $("span#us_state_selection").html("Region Placeholder");
//        }
//
//    });
//    $(".borrower.primary.us_state_id").trigger('change');
//
//
//    $(".borrower.primary.country_id").bind('change', function () {
//
//        var country_text = $(".borrower.primary.country_id option:selected").text();
//        $("span#country_selection").html(country_text);
//        if ((country_text != 'United States') || (country_text == 'Please select')) {
//            $(".borrower.primary.us_state_id option[value='99']").attr("selected", "selected");
//            $("span#us_state_selection").html('');
//            $("div#choose_us_state").css("display", "none");
//            $("div#provide_country_state").css("display", "inline");
//
//
//        } else {
//
//            $("div#provide_country_state").css("display", "none");
//            $("div#choose_us_state").css("display", "inline");
//        }
//
//    });
//    $(".borrower.primary.country_id").trigger('change');
//
//    $(".borrower.primary.region").bind('change', function () {
//        $("span#region_selection").html($(this).val());
//    });
//    $(".borrower.primary.region").trigger('change');
//
//
//
//
//    /*Beginning of Alternative Address*/
//
//    $("select.borrower.alternative.country_id").bind('change', function () {
//
//        var country_text = $("select.borrower.alternative.country_id option:selected").text();
//        $("span#country_alternative_selection").html(country_text);
//        if ((country_text != 'United States') || (country_text == 'Please select')) {
//            $("select.borrower.alternative.us_state_id option[value='99']").attr("selected", "selected");
//            $("span#state_alternative_selection").html('');
//            $("div#choose_us_state_alternative").css("display", "none");
//            $("div#provide_country_state_alternative").css("display", "inline");
//
//
//        } else {
//
//            $("div#provide_country_state_alternative").css("display", "none");
//            $("div#choose_us_state_alternative").css("display", "inline");
//        }
//
//    });
//    $("select.borrower.alternative.country_id").trigger('change');
//
//
//    $("input.borrower.alternative.region").bind('change', function () {
//        $("span#region_alternative_selection").html($(this).val());
//    });
//    $("input.borrower.alternative.region").trigger('change');
//
//    $("select.borrower.alternative.us_state_id").bind('change', function () {
//        var tmpID = $("select.borrower.alternative.us_state_id option:selected").text();
//        $("span#state_alternative_selection").html(tmpID);
//    });
//    $("select.borrower.alternative.us_state_id").trigger('change');
//
//
//
//    $(".borrower.alternative.address_line_1").bind('change', function () {
//        $("span#address_1_alternative_selection").html($(this).val());
//    });
//    $(".borrower.alternative.address_line_1").trigger('change');
//
//    $(".borrower.alternative.address_line_2").bind('change', function () {
//        $("span#address_2_alternative_selection").html($(this).val());
//    });
//    $(".borrower.alternative.address_line_2").trigger('change');
//
//    $(".borrower.alternative.province").bind('change', function () {
//        $("span#province_alternative_selection").html($(this).val());
//    });
//    $(".borrower.alternative.province").trigger('change');
//
//    $(".borrower.alternative.city").bind('change', function () {
//        $("span#city_alternative_selection").html($(this).val());
//    });
//    $(".borrower.alternative.city").trigger('change');
//
//    $(".borrower.alternative.postal_code").bind('change', function () {
//        $("span#postal_code_alternative_selection").html($(this).val());
//    });
//    $(".borrower.alternative.postal_code").trigger('change');



});




function validateUseWhichBorrowerContactAddress() {
//    var return_value = true;
//
//    var use_which_ca = $("input[name='borrower[useWhichContactAddress]']:checked").val();
//    if ((use_which_ca == 1) || (use_which_ca == 2)) {
//
//        if (($(".borrower.alternative.address_line_1").val() == "") ||
//                ($(".borrower.alternative.postal_code").val() == "") ||
//                ($(".borrower.alternative.city").val() == "") ||
//                ($(".borrower.alternative.country_id option:selected").text() == "Please select")) {
//            return_value = false;
//        }
//    }
//
//    return return_value;
}

function showBLegal() {
    hideAllBFormHrefs();
    $("li#tab_item_1").css("display", "block");
    $("li#tab_item_2").css("display", "block");
    $("li#tab_item_3").css("display", "block");
    $("li#tab_item_4").css("display", "block");
    whichSaveMenu(1);
    $("#form_legal").css("display", "block");
    return false;
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


function validateBItem() {
//    var return_value = true;
//    var whichType = 'borrower';
//    var item_count_element = $("#" + whichType + "_item_count");
//    var item_count_value = item_count_element.val();
//    var bad_count = isNaN(item_count_value);
//    $("span#item_description_error").css("visibility", "hidden");
//    $("span#item_count_error").css("visibility", "hidden");
//    $("span#item_condition_id_error").css("visibility", "hidden");
//    $("span#category_id_error").css("visibility", "hidden");
//
//    if (bad_count) {
//        $("span#item_count_error").css("visibility", "visible");
//        return_value = false;
//    } else if ((parseInt(item_count_value) < 1) || (parseInt(item_count_value) > 100)) {
//        $("span#item_count_error").css("visibility", "visible");
//        return_value = false;
//    }
//
//    if ($("#" + whichType + "_item_description").val() == '') {
//        $("span#item_description_error").css("visibility", "visible");
//        return_value = false;
//    }
//
//    if ($("#" + whichType + "_item_condition_id option:selected").text() == 'Please select') {
//        $("span#item_condition_id_error").css("visibility", "visible");
//        return_value = false;
//    }
//    if ($("#" + whichType + "_category_id option:selected").text() == 'Please select') {
//        $("span#category_id_error").css("visibility", "visible");
//        return_value = false;
//    }
//
//    return return_value;
}

function checkBLegal() {

    var notChecked = true;
//    var legal_18 = $("#age_18_or_more");
//    var legal_goodwill = $("#goodwill");
//
//    if (!(legal_18.is(':checked'))) {
//        notChecked = false;
//        $("span#18_or_more_error").text("You must be 18 years of age.");
//        $("span#18_or_more_error").css("visibility", "visible");
//    } else {
//        $("span#18_or_more_error").text("");
//        $("span#18_or_more_error").css("visibility", "hidden");
//    }
//    if (!(legal_goodwill.is(':checked'))) {
//        notChecked = false;
//        $("span#goodwill_error").text("You must be acting in goodwill.");
//        $("span#goodwill_error").css("visibility", "visible");
//    } else {
//        $("span#goodwill_error").text("");
//        $("span#goodwill_error").css("visibility", "hidden");
//    }

    return notChecked;
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
    hideAllBFormHrefs();
    $("li#tab_item_1").css("display", "block");
    $("li#tab_item_2").css("display", "block");
    $("li#tab_item_3").css("display", "block");
    whichSaveMenu(1);
    $("#form_item").css("display", "block");

    return false;
}

function showBorrowersContactPreferences() {

    var returnResult = ValidateContactInformation();
    //displayPhone();
//    alert("all okay");
//    alert(returnResult);
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
    $("li[id^=tab_item_]").css("display", "block");
    $("div[class=contact_information]").css("display", "block");
    $("div[id^=menu_item_]").css("display", "none");
    return false;
}

function size_of_menu() {

    var bmw = $("ul#nav.borrower_menu").css("width");
    var lmw = $("ul#nav.lender_menu").css("width");
    var imw = $("div#index_div").css("width");
//    var rap = $("div#right.app_panel").css("width");
//    var lap = $("div#left.app_panel").css("width");
//    var ah = $("div.application_header").css("width");
//    var center_without_menu = ah - rap - lap;
    var fur = $("div#form_user_registration").css("width");
    var uli = $("div#user_login");
    var flog = $("div#form_login.top").css("width");
    if (bmw) {
//        alert("b");
        $("div.borrower_registration").css("width", bmw);
        $("div.application_footer").css("width", bmw);
    } else if (imw) {
//        alert("i");
        $("div.application_footer").css("width", imw);
    } else if (lmw) {
//        alert("l");
        $("div.lender_registration").css("width", lmw);
        $("div.application_footer").css("width", lmw);
    } else if (fur) {
//        alert("f");
        $("div.application_footer").css("width", fur);
    } else if (uli) {
//        alert("f");
        $("div.application_footer").css("width", fur);
    } else if (flog) {
        $("div.application_footer").css("width", flog);
    }


    return false;
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
// Just for debugging
    return true;

    var returnValue = true;
    var foundComponent = 0;
    var cdi = null;
    var ocdi = null;
    var firstName = null;
    var lastName = null;
    var addressLine1 = null;
    var city = null;
    var postalCode = null;
    var countryId = null;
//    var homePhone = null;
//    var cellPhone = null;
//    var alternativePhone = null;
    $("span").css("visibility", "hidden");
    $("select").each(function () {
        if (this.id == 'rb:contactDescribeId') {
            cdi = this.value;
            foundComponent++;
//            Cheating here, eventailly need to parse for id
        } else if (this.id == 'rb:primary:0:countryId') {
            countryId = this.value;
            //alert(countryId);
            foundComponent++;
        } else if (foundComponent == 2) {
            return false;
        } else {
        }
    });
    foundComponent = 0;
    $("input").each(function () {
        if (this.id == 'rb:otherDescribeYourself') {
            ocdi = this.value;
            foundComponent++;
        } else if (this.id == 'rb:firstName') {
            firstName = this.value;
            foundComponent++;
        } else if (this.id == 'rb:lastName') {
            lastName = this.value;
            foundComponent++;
        } else if (this.id == 'rb:primary:0:addressLine1') {
            addressLine1 = this.value;
            foundComponent++;
        } else if (this.id == 'rb:primary:0:city') {
            city = this.value;
            foundComponent++;
        } else if (this.id == 'rb:primary:0:postalCode') {
            postalCode = this.value;
            foundComponent++;
//        } else if (this.id == 'rb:homePhone') {
//            homePhone = this.value;
//            alert("did get home phone value");
//            alert(homePhone);
//            foundComponent++;
//        } else if (this.id == 'rb:cellPhone') {
//            cellPhone = this.value;
//            foundComponent++;
//        } else if (this.id == 'rb:alternativePhone') {
//            alternativePhone = this.value;
//            foundComponent++;
        } else if (foundComponent == 9) {
//alert(foundComponent);
            return false;
        }

    });
    if ((cdi == '100') && (ocdi == null)) {
        $("span#otherDescribeYourself").css("visibility", "visible");
        $("span#otherDescribeYourself").text("Please provide an 'Other' description");
        returnValue = false;
    } else if ((cdi == '-2') && (ocdi == null)) {
        $("span#contactDescribeId").css("visibility", "visible");
        $("span#contactDescribeId").text("Please make a selection");
        returnValue = false;
    }

    if (countryId == -2) {
        $("span#countryId.error-message").css("visibility", "visible");
        $("span#countryId.error-message").text("Please provide your Country");
        returnValue = false;
    }

//    $("div.phone").each(function () {
//
//        if (this.id != "") {
//
//            $(this).css("display", "none");
//            if (this.id == 'display_homePhone') {
//                if (homePhone) {
//                    $(this).css("display", "block");
//                    alert(1);
//                }
//            }
//
//            if (this.id == 'display_cellPhone') {
//                if (cellPhone) {
//                    $(this).css("display", "block");
//                    alert(2);
//                }
//            }
//
//            if (this.id == 'display_alternativePhone') {
//                if (alternativePhone) {
//                    $(this).css("display", "block");
//                    alert(3);
//                }
//            }
//        }
//
//    });
//    if ((homePhone == '') && (cellPhone == '')&& (alternativePhone == '')) {
//        $("div#display_phone").css("display", "none");
//    }


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


//    alert($("div#display_homePhone.phone").css("display"));
//    alert($("div#display_cellPhone.phone").css("display"));
//    alert($("div#display_alternativePhone.phone").css("display"));

}


  