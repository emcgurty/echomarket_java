$(document).ready(function () {



    $('input').on('change', function () {

        var current_value = null;
var getHidden = null;
        if (this.id.includes('dt_firstName')) {

            current_value = $(this).val();
            alert(current_value);
            
            getHidden = $('input:hidden[id=firstName]').val();
            getHidden.val(current_value);
            alert(getHidden.val());
        } else if (this.id.includes('dt_lastName')) {
            current_value = $(this).val();
            $("input#lastName").val(current_value);
        } else if (this.id.includes('dt_email')) {
            current_value = $(this).val();
            $("input#email").val(current_value);
        } else if (this.id.includes('dt_alias')) {
            current_value = $(this).val();
            $("input#alias").val(current_value);
        }
    });
});
