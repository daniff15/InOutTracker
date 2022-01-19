$(document).ready(function() {
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

    function get_cookie(name){
        return document.cookie.split(';').some(c => {
            return c.trim().startsWith(name + '=');
        });
    }

    function delete_cookie(name) {
        if(get_cookie(name)) {
          document.cookie = name + "=" + "expires=Thu, 01 Jan 1970 00:00:01 GMT";
        }
    }

    user = readCookie('login');
    if(user) {
        user = JSON.parse(user);
        $("#account").html(
            `<li class="nav-item">
                <a class="nav-link" href="account.html">${user["username"]}</a>
            </li>`
        );
        
        $("#account-details").html(
            `<p>${user["name"]}</p>
            <p>${user["username"]}</p>
            <p>${user["email"]}</p>`
        );

        if(user["type"] == 0)
            $("#home-link").html(`<a class="navbar-brand" href="shoppings.html">InOutTracker</a>`)
        else if(user["type"] == 1)
            $("#home-link").html(`<a class="navbar-brand" href="admin-shoppings.html">InOutTracker</a>`)
    }else {
        $("#account").html("");
    }

    $("#logout-btn").click(function() {
        delete_cookie("login");
        if(user["type"] == 0)
            window.location.href = "http://127.0.0.1:5500/shoppings.html";
        else if(user["type"] == 1)
            window.location.href = "http://127.0.0.1:5500/admin-shoppings.html";
    });
});
