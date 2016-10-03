$(document).ready(function ($) {
    

    size_of_menu();
    $(window).resize(function () {
        size_of_menu();
    });
    $('select').on('change', function () {
        alert("select change");
    });
    $('input').on('change', function () {
//        alert("input change");
        if (this.id.includes('registrationType')) {
//            alert("Okay");
            var y_n = $(this).val();
            if (y_n == 5){

                $("div#communityNameDiv").css("display", "block");
                $("div#onePersonReg").css("display", "block");
            } else {
                $("div#communityNameDiv").css("display", "none");
                $("div#onePersonReg").css("display", "block");
            }
        }
    });
    
    function size_of_menu() {

    }

});



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

