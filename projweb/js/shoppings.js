function show(data) {
    let cards = "";
    for (let r of data) {
        cards += 
        `<div class="col-lg-3 col-md-4 col-sm-6 col-12">
            <div class="card">
                <img class="card-img-top" src="imgs/shopping2.jpeg" alt="Card image cap">
                <div class="card-body">
                    <h4 class="card-title">${r.name}</h4>
                    <h5>${r.people_count}</h5>
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
            show(data);
        },
        complete: function() {
            // Schedule the next request when the current one's complete
            setTimeout(worker, 1000);
        }
    });
})();
