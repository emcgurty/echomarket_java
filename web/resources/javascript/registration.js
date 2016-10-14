$(document).ready(function ($) {

// size_of_menu();
//    $(window).resize(function () {
//        size_of_menu();
//    })
//    
    $('select').on('change', function () {
//        alert("select change");
    });
    $('input').on('change', function () {
//       alert("input change");
//       Below no longer needed.  Need to code for client side validation
//        if (this.id.includes('registrationType')) {
//            
//            var y_n = $(this).val();
//            if (y_n == 5) {
//
//                $("div#individualNameGroup").css("display", "none");
//                $("div#communityNameGroup").css("display", "block");
//
//
//            } else {
//                $("div#individualNameGroup").css("display", "block");
//                $("div#communityNameGroup").css("display", "none");
//            }
//        }
    });
   

});

//function size_of_menu() {
//    
//    var nav_menu = $("div#nav").css("width");
//
//    $("div.application_footer").css("width", nav_menu);
//    
//    return true;
//}


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

