$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shoppingId = a[0];
    var shoppingName = a[1].replaceAll("%20", " ").replaceAll("%27", "'");
    var shoppingOpeningTime = a[2];
    var shoppingClosingTime = a[3];
    var shoppingCapacity = a[4];

    $("#name").val(shoppingName);
    $("#opening-time").val(shoppingOpeningTime);
    $("#closing-time").val(shoppingClosingTime);
    $("#capacity").val(shoppingCapacity);

    $("form").submit(function (event) {
        var formData = {
            name: $("#name").val(),
            opening_time: $("#opening-time").val(),
            closing_time: $("#closing-time").val(),
            max_capacity: $("#capacity").val(),
        };
        
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/shopping/update/" + shoppingId,
            type: "PUT",
            data: JSON.stringify(formData),
            contentType: "application/json",
        }).done(function (data) {
            console.log(data);
            window.location.href = "http://" + self.location.hostname + ":5500/admin-shoppings.html";
        });
    
        event.preventDefault();
    });

    $(function() {
        $('.time').datetimepicker({
            format: 'HH:mm',
        });
    });

    function readCookie(name) {
        var i, c, ca, nameEQ = name + "=";
        ca = document.cookie.split(';');
        for(i=0;i < ca.length;i++) {
            c = ca[i];
            while (c.charAt(0)==' ') {
                c = c.substring(1,c.length);
            }
            if (c.indexOf(nameEQ) == 0) {
                return c.substring(nameEQ.length,c.length);
            }
        }
        return '';
    }

    user = readCookie('login');
    if(user) {
        user = JSON.parse(user);
        if(user["type"] == 0) {
            window.location.href = "http://" + self.location.hostname + ":5500/shoppings.html";
        }else {
            $("#account").html(
                `<li class="nav-item">
                    <a class="nav-link" href="account.html">${user["username"]}</a>
                </li>`
            );
        }
    }
});
