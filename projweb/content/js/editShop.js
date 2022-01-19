$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shopId = a[0];
    var shoppingId = a[1];
    var shopName = a[2].replaceAll("%20", " ").replaceAll("%27", "'");
    var shopOpeningTime = a[3];
    var shopClosingTime = a[4];
    var shopCapacity = a[5];
    
    $("#name").val(shopName);
    $("#opening-time").val(shopOpeningTime);
    $("#closing-time").val(shopClosingTime);
    $("#capacity").val(shopCapacity);

    $("form").submit(function (event) {
        var formData = {
            name: $("#name").val(),
            opening_time: $("#opening-time").val(),
            closing_time: $("#closing-time").val(),
            max_capacity: $("#capacity").val(),
        };
        
        $.ajax({
            url: "http://localhost:8000/api/v1/store/update/" + shopId,
            type: "PUT",
            data: JSON.stringify(formData),
            contentType: "application/json",
        }).done(function (data) {
            console.log(data);
            window.location.href = "http://127.0.0.1:5500/admin-shopping-center.html?" + shoppingId;
        });
    
        event.preventDefault();
    });

    $(function() {
        $('.time').datetimepicker({
            format: 'HH:mm',
        });
    });
});
