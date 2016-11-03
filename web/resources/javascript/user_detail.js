$(document).ready(function () {

    $(".contact_information").css("display", "block");
    
    $('input').on('change', function () {
        checkLegal();

    });

});

function checkLegal() {

    
    var legal_check = $("input[type=radio]");
    var getValue = false;
    var foundValue = 0;
    try {

        for (var i = 0; i < legal_check.length; i++) {
            var id = legal_check[i].id;
            if (id.includes('age18OrMore')) {
                if (legal_check[i].value == 1) {
                    if (legal_check[i].checked == false) {
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
                if (legal_check[i].value == 1) {
                    if (legal_check[i].checked == false) {
                        $("span#goodwill_span.error-message").text("You must be acting in goodwill.");
                        $("span#goodwill_span.error-message").css("visibility", "visible");
                    } else {
                        $("span#goodwill_span.error-message").text("");
                        $("span#goodwill_span.error-message").css("visibility", "hidden");
                        getValue = true;
                    }
                }
                foundValue++;
            } else if (foundValue == 2) {
                break;
            }
        }

    } catch (err) {
    }

    return getValue;
}
