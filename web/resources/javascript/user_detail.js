$(document).ready(function () {

  $(".contact_information").css("display", "block");
  $("input[type=radio]").on('change', function () {
    var getid = this.id;
    if (getid.includes('questionAltAddress'))
      checkAltAddress(this);
    if (getid.includes('questionAltEmail'))
      checkAltEmailProvide(this);
    if (getid.includes('age18OrMore'))
      check18OrMore(this);
    if (getid.includes('gooodwill'))
      checkGoodWill(this);
        if (getid.includes('smallFee'))
      checkSmallFee(this);

  });
});

function checkSmallFee(input) {
  
        if (input.value == 1) {
          if (input.checked == true) {
            $("span#smallFeeAmount").css("visibility", "visible");
            $("span#smallFee").text("Please provide an amount.");
          }
        } else {
          if (input.checked == true) {
            $("span#smallFeeAmount").css("visibility", "hidden");
            $("span#smallFee").text("");
          }
        }
}

function checkGoodWill(input) {

  if (input.value == 0) {
    if (input.checked == true) {
      $("span#goodwill_span.error-message").text("You must be acting in goodwill.");
      $("span#goodwill_span.error-message").css("visibility", "visible");
    } else {
      $("span#goodwill_span.error-message").text("");
      $("span#goodwill_span.error-message").css("visibility", "hidden");
    }
  }
}

function check18OrMore(input) {
  if (input.value == 1) {
    if (input.checked == true) {
      $("span#age18OrMore_span.error-message").text("You must be 18 years of age.");
      $("span#age18OrMore_span.error-message").css("visibility", "visible");
    } else {
      $("span#age18OrMore_span.error-message").text("");
      $("span#age18OrMore_span.error-message").css("visibility", "hidden");
    }
  }
}

function checkAltEmailProvide(input) {
  if (input.value == 1) {
    if (input.checked == false) {
      $("div#emailAlternative").css("display", "none");
    } else {
      $("div#emailAlternative").css("display", "block");
    }
  }
}

function checkAltAddress(input) {
  if (input.value == 1) {
    if (input.checked == true) {
      $("div#addressAlternative").css("display", "block");
    } else {
      $("div#addressAlternative").css("display", "none");
    }
  } else {
    if (input.checked == true) {
      $("div#addressAlternative").css("display", "none");
    }
  }
}





