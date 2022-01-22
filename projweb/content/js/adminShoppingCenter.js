$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shoppingId = a[0];
    function showStores(stores) {
        var rows = "";
        for(var store of stores) {
            rows +=
            `<tr id="${store.id}">
                <td>
                    <button class="btn btn-danger delete-shop-button" type="button">
                        <i class="fa fa-trash" aria-hidden="true"></i>
                    </button>
                </td>
                <td>
                    <a href="edit-shop.html?${store.id}|${shoppingId}|${store.name}|${store.opening_time}|${store.closing_time}|${store.max_capacity}" 
                        class="btn btn-primary edit-shop-button">
                        <i class="fa fa-pencil" aria-hidden="true"></i>
                    </a>
                </td>
                <th scope="row">${store.name}</th>
                <td class="text-right">${store.people_count}/${store.max_capacity}</td>
            </tr>`
        }
        $('#stores').html(rows);
    }
    
    (function worker() {
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/shopping/" + shoppingId + "/stores",
            success: function(data) {
                showStores(data);
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
        if(user["type"] == 0) {
            window.location.href = "http://" + self.location.hostname + ":5500/shopping-center.html?" + shoppingId;
        }else {
            $("#account").html(
                `<li class="nav-item">
                    <a class="nav-link" href="account.html">${user["username"]}</a>
                </li>`
            );
        }
    }

    $("#add-shop").html(
        `<a href="add-shop.html?${shoppingId}" class="add-shop-button">
            <button class="btn btn-outline-success my-2 my-sm-0" type="button">Add Shop</button>
        </a>`
    );

    $(document).on("click", ".delete-shop-button", function() {
        var shopId = $(this).parent().parent().attr("id");
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/stores/" + shopId,
            type: "DELETE",
        }).done(function (data) {
            console.log(data);
        });
    });
});
