$(document).ready(function () {

    size_of_menu();
    $(window).resize(function () {
        size_of_menu();
    });

    var remove_div_css = $("div#nav.dumby").css("background", "white");
    remove_div_css.css("height", "0px");
    remove_div_css.css("padding", "0px");
    remove_div_css.css("border-bottom", "#3B5998 double medium");
    

});
function size_of_menu() {

    var bmw = $("ul#nav.borrower_menu").css("width");
    var lmw = $("ul#nav.lender_menu").css("width");
    var nav_menu = $("div#nav").css("width");

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
