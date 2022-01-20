$(document).ready(function() {
    function showShoppings(shoppings) {
        var cards = "";
        for (var shopping of shoppings) {
            cards += 
            `<div class="col-lg-3 col-md-4 col-sm-6 col-12">
                <div class="card" id=${shopping.id}>
                    <img class="card-img-top" src="imgs/shopping2.jpeg" alt="Card image cap">
                    <div class="card-body">
                        <h4 class="card-title">${shopping.name}</h4>
                        <h5>${shopping.people_count} / ${shopping.max_capacity}</h5>
                        <p>Open: ${shopping.opening_time}-${shopping.closing_time}</p>
                        <a href="admin-shopping-center.html?${shopping.id}" class="btn btn-primary">Details</a>
                        <button class="btn btn-danger delete-shopping-button" type="button"><i class="fa fa-trash" aria-hidden="true"></i></button>
                        <a href="edit-shopping-center.html?${shopping.id}|${shopping.name}|${shopping.opening_time}|${shopping.closing_time}|${shopping.max_capacity}" 
                            class="btn btn-primary edit-shopping-button">
                            <i class="fa fa-pencil" aria-hidden="true"></i>
                        </a>
                    </div>
                </div>
            </div>`
        }
        $('#shoppings').html(cards);
    }

    (function worker() {
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/shoppings", 
            success: function(data) {
                showShoppings(data);
            },
            complete: function() {
                // Schedule the next request when the current one's complete
                setTimeout(worker, 1000);
            }
        });
    })();
    
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
        $("#account").html(
            `<li class="nav-item">
                <a class="nav-link" href="account.html">${user["username"]}</a>
            </li>`
        );
    }

    $(document).on("click", ".delete-shopping-button", function() {
        var shoppingId = $(this).parent().parent().attr("id");
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/shoppings/" + shoppingId,
            type: "DELETE",
        }).done(function (data) {
            console.log(data);
        });
    });
});
