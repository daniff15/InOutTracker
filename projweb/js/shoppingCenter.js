function showStores(stores) {
    var rows = "";
    for(var store of stores) {
        rows +=
        `<tr>
            <th scope="row">${store.name}</th>
            <td class="text-right">${store.people_count}/${store.max_capacity}</td>
        </tr>`
    }
    document.getElementById('stores').innerHTML = rows;
}

(function worker() {
    $.ajax({
        url: 'http://localhost:8000/api/v1/stores', 
        success: function(data) {
            showStores(data);
        },
        complete: function() {
            // Schedule the next request when the current one's complete
            setTimeout(worker, 1000);
        }
    });
})();
