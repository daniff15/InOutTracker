function showShoppings(shoppings) {
    var cards = "";
    for (var shopping of shoppings) {
        cards += 
        `<div class="col-lg-3 col-md-4 col-sm-6 col-12">
            <div class="card">
                <img class="card-img-top" src="imgs/shopping2.jpeg" alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">${shopping.name}</h4>
                    <h5>${shopping.people_count} / ${shopping.max_capacity}</h5>
                    <p>Open: ${shopping.opening_time}-${shopping.closing_time}</p>
                    <a href="shopping-center.html" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>
        </div>`
    }
    document.getElementById('shoppings').innerHTML = cards;
}

(function worker() {
    $.ajax({
        url: 'http://localhost:8000/api/v1/shoppings', 
        success: function(data) {
            showShoppings(data);
        },
        complete: function() {
            // Schedule the next request when the current one's complete
            setTimeout(worker, 1000);
        }
    });
})();
