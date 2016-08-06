/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//$(document).ready(function () {
//    alert("image.js");
//
// function readURL(input) {
//     alert("inside image.js");
//                        if (input.files && input.files[0]) {
//                            var reader = new FileReader();
//
//                            reader.onload = function (e) {
//                                $('#imagePreview').attr('src', e.target.result);
//                            }
//
//                            reader.readAsDataURL(input.files[0]);
//                        }
//                    }
//
//                    $("#itemImage").change(function () {
//                        readURL(this);
//                    });
//                    
//                    });


function readURL(input) {
    alert("outside image.js");
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#imagePreview').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#itemImage").change(function () {
    readURL(this);
});
