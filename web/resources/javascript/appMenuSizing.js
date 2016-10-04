$(document).ready(function () {

    size_of_menu();
    $(window).resize(function () {
        size_of_menu();
    });

  });
function size_of_menu() {

    var bmw = $("ul#nav.borrower_menu").css("width");
    var lmw = $("ul#nav.lender_menu").css("width");
    var nav_menu = $("div#nav.dumby").css("width");

    if (bmw) {
//        alert(1);
        $("div.application_footer").css("width", bmw);
    } else if (lmw) {
//        alert(2);
        $("div.application_footer").css("width", lmw);
    } else {
//        alert(3);
        $("div.application_footer").css("width", nav_menu);
    }

    return true;
}
