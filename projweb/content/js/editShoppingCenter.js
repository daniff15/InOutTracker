﻿$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shoppingId = a[0];

    $("form").submit(function (event) {
        var formData = {
            name: $("#name").val(),
            opening_time: $("#opening-time").val(),
            closing_time: $("#closing-time").val(),
            max_capacity: $("#capacity").val(),
        };
        
        $.ajax({
            url: "http://localhost:8000/api/v1/shopping/update/" + shoppingId,
            type: "PUT",
            data: JSON.stringify(formData),
            contentType: "application/json",
        }).done(function (data) {
            console.log(data);
            window.location.href = "http://127.0.0.1:5500/admin-shoppings.html";
        });
    
        event.preventDefault();
    });

    $(function() {
        $('.time').datetimepicker({
            format: 'HH:mm',
        });
    });
});
