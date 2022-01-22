$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shoppingId = a[0];

    var isLoggedIn = false;
    user = readCookie('login');
    if(user) {
        user = JSON.parse(user);
        isLoggedIn = true;
        $("#account").html(
            `<li class="nav-item">
                <a class="nav-link" href="account.html">${user["username"]}</a>
            </li>`
        );
    }

    var favorites = $.parseJSON($.ajax({
        url: "http://" + self.location.hostname + ":8000/api/v1/user/" + user["id"] + "/favorites",
        async: false
    }).responseText);

    // function showFavorites() {
    //     for(var relation of favorites) {
    //         storeId = "#" + relation.store_id;
    //         console.log(storeId);
    //         if($(storeId)) {
    //             $(storeId).find("button").html(`<i class="fa fa-heart" aria-hidden="true"></i>`)
    //         }
    //     }
    // }

    function showStores(stores) {
        if(isLoggedIn) {
            var head = 
                `<thead>
                    <tr>
                        <th scope="col" class="delete-shop-column"></th>
                        <th scope="col">Shop Name</th>
                        <th scope="col" class="text-right">Capacity</th>
                    </tr>
                </thead>`
            var body = "<tbody>";
            for(var store of stores) {
                body +=
                `<tr id="${store.id}">
                    <td>
                        <button class="btn btn-outline-danger fav-shop-button">`
                if(!containsObject(store.id, favorites)) {
                    body += `<i class="fa fa-heart-o" aria-hidden="true"></i>`
                }else {
                    body += `<i class="fa fa-heart" aria-hidden="true"></i>`
                }
                body +=
                        `</button>
                    </td>
                    <th scope="row" style="vertical-align: middle;">${store.name}</th>
                    <td class="text-right" style="vertical-align: middle;">${store.people_count}/${store.max_capacity}</td>
                </tr>`
            }
            body += "</tbody>"
            // showFavorites();
        }else {
            var head = 
                `<thead>
                    <tr>
                        <th scope="col">Shop Name</th>
                        <th scope="col" class="text-right">Capacity</th>
                    </tr>
                </thead>`
            var body = "<tbody>";
            for(var store of stores) {
                body +=
                `<tr id="${store.id}">
                    <th scope="row" style="vertical-align: middle;">${store.name}</th>
                    <td class="text-right" style="vertical-align: middle;">${store.people_count}/${store.max_capacity}</td>
                </tr>`
            }
            body += "</tbody>"
        }
        $('#stores').html(head + body);
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

    function containsObject(obj, list) {
        var i;
        for (i = 0; i < list.length; i++) {
            if (list[i] == obj) {
                return true;
            }
        }
    
        return false;
    }

    $(document).on("click", ".fav-shop-button", function() {
        var shopId = $(this).parent().parent().attr("id");
        var data = {
            user_id: user["id"],
            store_id: shopId,
        };
        if(!containsObject(shopId, favorites)) {
            favorites.push(shopId);
            $.ajax({
                url: "http://" + self.location.hostname + ":8000/api/v1/add/favorite",
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
            }).done(function (data) {
                console.log(data);
            });
        }else {
            favorites.splice(favorites.indexOf(shopId));
            $.ajax({
                url: "http://" + self.location.hostname + ":8000/api/v1/remove/favorite",
                type: "DELETE",
                data: JSON.stringify(data),
                contentType: "application/json",
            }).done(function (data) {
                console.log(data);
            });
        }
    });
});
