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
    
    user = readCookie('login');
    if(user) {
        user = JSON.parse(user);
        var favorites = "";
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/user/" + user["id"] + "/favorites", 
            success: function(data) {
                showFavorites(data);
            }
        });
    }

    function getStore(id) {
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/store/" + id,
            success: function(store) {
                console.log(store);
                favorites += 
                `<tr id="${store.id}">
                    <td>
                        <button class="btn btn-outline-danger fav-shop-button">
                            <i class="fa fa-heart" aria-hidden="true"></i>
                        </button>
                    </td>
                    <th scope="row" style="vertical-align: middle;">${store.name}</th>
                    <td class="text-right" style="vertical-align: middle;">${store.people_count}/${store.max_capacity}</td>
                </tr>`;
                $("#favorites").html(favorites);
            }
        });
    }

    function showFavorites(favorites) {
        for(var i = 0; i < favorites.length; i++) {
            getStore(favorites[i]);
        }
    }
});
