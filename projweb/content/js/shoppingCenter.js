$(document).ready(function() {
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shoppingId = a[0];
    function showStores(stores) {
        var rows = "";
        for(var store of stores) {
            rows +=
            `<tr>
                <td>
                    <button class="btn btn-outline-danger delete-shop-button">
                        <i class="fa fa-heart" aria-hidden="true"></i>
                    </button>
                </td>
                <th scope="row" style="vertical-align: middle;">${store.name}</th>
                <td class="text-right" style="vertical-align: middle;">${store.people_count}/${store.max_capacity}</td>
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
        $("#account").html(
            `<li class="nav-item">
                <a class="nav-link" href="account.html">${user["username"]}</a>
            </li>`
        );
    }
});
