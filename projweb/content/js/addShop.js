$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shoppingId = a[0];
    
    $("form").submit(function (event) {
        var formData = {
            name: $("#name").val(),
            shop_id: shoppingId,
            opening_time: $("#opening-time").val(),
            closing_time: $("#closing-time").val(),
            max_capacity: $("#capacity").val(),
            people_count: 0,
        };
        
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/stores",
            type: "POST",
            data: JSON.stringify(formData),
            contentType: "application/json",
        }).done(function (data) {
            console.log(data);
            window.location.href = "http://" + self.location.hostname + ":5500/admin-shopping-center.html?" + shoppingId;
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
