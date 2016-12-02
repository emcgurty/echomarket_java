$(document).ready(function () {

  $(".contact_information").css("display", "block");
  $('input').on('change', function () {
    checkLegal();
  });
  $('select').on('change', function () {
    checkType(this);
  });
//  Does not work...
//  $('input').on('focus', function () {
//    $(this).css("background", "#F2F3F4")
//  });

});
function checkLegal() {

  var legal_check = $("input[type=radio]");
  var getValue = false;
  var foundValue = 0;
  try {
    for (var i = 0; i < legal_check.length; i++) {
      var id = legal_check[i].id;
      if (id.includes('age18OrMore')) {
        if (legal_check[i].value == 0) {
          if (legal_check[i].checked == true) {
            $("span#age18OrMore_span.error-message").text("You must be 18 years of age.");
            $("span#age18OrMore_span.error-message").css("visibility", "visible");
          } else {
            $("span#age18OrMore_span.error-message").text("");
            $("span#age18OrMore_span.error-message").css("visibility", "hidden");
            getValue = true;
          }
        }
        foundValue++;
      } else if (id.includes('goodwill')) {

        if (legal_check[i].value == 0) {
          if (legal_check[i].checked == true) {
            $("span#goodwill_span.error-message").text("You must be acting in goodwill.");
            $("span#goodwill_span.error-message").css("visibility", "visible");
          } else {
            $("span#goodwill_span.error-message").text("");
            $("span#goodwill_span.error-message").css("visibility", "hidden");
            getValue = true;
          }
        }
        foundValue++;
      } else if (id.includes('questionAltEmail')) {
        if (legal_check[i].value == 1) {
          if (legal_check[i].checked == false) {
            $("div#emailAlternative").css("display", "none");
          } else {
            $("div#emailAlternative").css("display", "block");
            getValue = true;
          }
        }
        foundValue++;
      } else if (id.includes('questionAltAddress')) {
        if (legal_check[i].value == 1) {
          if (legal_check[i].checked == false) {
            $("div#addressAlternative").css("display", "none");
          } else {
            $("div#addressAlternative").css("display", "block");
            getValue = true;
          }
        }
        foundValue++;
      } else if (foundValue == 3) {
        break;
      }
    }

  } catch (err) {
  }

  return getValue;
}


function checkType(input) {

  var select_value = input.value;
  var select_id = input.id;
  var which_type_ = "";
  if (select_id.includes('which_type')) {
    if (select_value == 1) {
      which_type_ = "lend";
    } else {
      which_type_ = "borrow";
    }
    $("input[name='user_detail:whichType']").val(which_type_);
    $("image#imagePreview").css("src", which_type + "_images");
  }



  return;
}