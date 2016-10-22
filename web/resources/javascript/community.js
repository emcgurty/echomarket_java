$(document).ready(function () {

    $('input').on('change', function () {

        var current_value = null;
        $("span.error-message").css("visibility", "hidden");
        $("span.error-message").text("");
        var current_id = null;
       if (this.id.includes('howManyRecords')) {
            current_id = this.id;
            current_value = $(this).val();
            
            if ( (current_value < 1) || (current_value > 25)  || (isNaN(current_value))) {
                $("span#howManyRecords"  + ".error-message").css("visibility", "visible");
                $("span#howManyRecords"  + ".error-message").text("Please enter a numeric value betweem 1 and 25.");
            }
                
        }
    });
});
