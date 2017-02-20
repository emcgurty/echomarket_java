$(document).ready(function () {
    var remove_div_css = $("div#nav.dumby").css("background", "white");
    remove_div_css.css("height", "0px");
    remove_div_css.css("padding", "0px");
    remove_div_css.css("border-bottom", "#3B5998 double medium");

    size_of_menu();
    $(window).resize(function () {
        size_of_menu();
    });




});
function size_of_menu() {

    var bmw = $("ul#nav.borrower_menu").css("width");
    var lmw = $("ul#nav.lender_menu").css("width");
    var nav_menu = $("div#nav").css("width");

    if (bmw) {

        $("div.application_footer").css("width", bmw);
    } else if (lmw) {

        $("div.application_footer").css("width", lmw);
    } else {

        $("div.application_footer").css("width", nav_menu);
    }

    return true;
}
