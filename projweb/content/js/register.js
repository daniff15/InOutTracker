$(document).ready(function() {
    $("form").submit(function (event) {
        event.preventDefault();

        var formData = {
            type: 0,
            name: $("#name").val(),
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val(),
        };

        if (formData.password.length > 6 && formData.email.length > 0 && formData.username.length > 0 && formData.name.length > 0) {
            $.ajax({
                url: "http://" + self.location.hostname + ":8000/api/v1/users",
                type: "POST",
                data: JSON.stringify(formData),
                contentType: "application/json",
            }).done(function (data) {
                console.log(data);
                window.location.href = "http://" + self.location.hostname + ":5500/login.html"
            });
        } else{
            console.log("error");
            window.location.href = "#"
            
        }
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
            window.location.href = "http://" + self.location.hostname + ":5500/admin-shoppings.html";
        }
    }
});
