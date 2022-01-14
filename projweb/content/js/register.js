$("form").submit(function (event) {
    var formData = {
        type: 0,
        name: $("#name").val(),
        username: $("#username").val(),
        email: $("#email").val(),
        password: $("#password").val(),
    };

    $.ajax({
        url: "http://localhost:8000/api/v1/users",
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
    }).done(function (data) {
        console.log(data);
    });

    event.preventDefault();
});
