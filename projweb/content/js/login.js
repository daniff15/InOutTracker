$(document).ready(function() {
    function writeCookie(name,value,days) {
        var date, expires;
        if (days) {
            date = new Date();
            date.setTime(date.getTime()+(days*24*60*60*1000));
            expires = "; expires=" + date.toGMTString();
                }else{
            expires = "";
        }
        document.cookie = name + "=" + value + expires + "; path=/";
    }
    
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
    
    $("form").submit(function (event) {
        event.preventDefault();

        var formData = {
            email: $("#email").val(),
            password: $("#password").val(),
        };

        if(formData.email.length > 0 && formData.password.length > 6) {
    
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/users/login",
            type: "POST",
            data: JSON.stringify(formData),
            contentType: "application/json",
        }).done(function (data) {
            console.log(data);
            if(data["status"] == "success") {
                value = {
                    type: data["type"],
                    name: data["name"],
                    username: data["username"],
                    email: data["email"],
                    id: data["id"],
                }
                writeCookie("login", JSON.stringify(value), 1);
            }
            if(data["type"] == 0)
                window.location.href = "http://" + self.location.hostname + ":5500/shoppings.html";
            else if(data["type"] == 1)
                window.location.href = "http://" + self.location.hostname + ":5500/admin-shoppings.html";
        });
        }  else {
            console.log("error");
        }
    });

    user = readCookie('login');
    if(user) {
        user = JSON.parse(user);
        if(user["type"] == 0) {
            window.location.href = "http://" + self.location.hostname + ":5500/shoppings.html";
        }else {
            window.location.href = "http://" + self.location.hostname + ":5500/admin-shoppings.html";
        }
    }
});
