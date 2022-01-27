$(document).ready(function() {
    var d = new Date();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var currentDate = (day<10 ? '0' : '') + day + '/' +
        (month<10 ? '0' : '') + month + '/' +
        d.getFullYear();
    $('#dateInput').val(currentDate)
    
    var queryString = location.search.substring(1);
    var a = queryString.split("|");
    var shopId = a[0];
    var shopName = a[1].replaceAll("%20", " ").replaceAll("%27", "'");
    $('#shop-name').html(shopName);
    
    $("#no-data").hide();
    
    getData(currentDate);

    function getData(date) {
        $.ajax({
            url: "http://" + self.location.hostname + ":8000/api/v1/daily/" + shopId + "/" + date, 
            success: function(data) {
                if(data.length > 0) {
                    $("#no-data").hide();
                    $(".chart-container").show();
                    showInfo(data);
                }else {
                    $(".chart-container").hide();
                    $("#no-data").show();
                }
            }
        });
    }

    function resetCanvas() {
        $('#myChart').remove(); // this is my <canvas> element
        $('.chart-container').append('<canvas id="myChart"><canvas>');
        canvas = document.querySelector('#myChart');
        ctx = canvas.getContext('2d');
        return ctx;
    };

    function showInfo(data) {
        data.sort(function(a, b) {
            return a.hour - b.hour;
        });

        const dataset = data.map(a => a.count);
        const labels = data.map(a => a.hour);

        let ctx = resetCanvas();
        let myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'People Count',
                    data: dataset,
                    fill: false,
                    borderColor: 'rgb(75, 192, 192)',
                    rension: 0.1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        title: {
                            color: 'grey',
                            display: true,
                            text: 'nº people'
                        },
                        beginAtZero: true
                    },
                    x: {
                        title: {
                            color: 'grey',
                            display: true,
                            text: 'hours'
                        },
                        ticks: {
                            callback: function(value, index, ticks) {
                                return labels[index] + ':00';
                            }
                        }
                    }
                }
            }
        });
    }

    $('.datepicker').on('change', function() {
        const date = $('#dateInput').val().replaceAll('/', '-');
        getData(date);
    });

    $('.datepicker').datepicker({
        format: 'dd/mm/yyyy'
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
        if(user["type"] == 0 || user["type"] == 1) {
            isLoggedIn = true;
            $("#account").html(
                `<li class="nav-item">
                    <a class="nav-link" href="account.html">${user["username"]}</a>
                </li>`
            );
        }else if(user["type"] == 2) {
            isSecurity = true;
            isLoggedIn = true;
            $("#account").html(
                `<li class="nav-item">
                    <a class="nav-link" href="account.html">${user["username"]}</a>
                </li>`
            );
            (function securityWorker() {
                $.ajax({
                    url: "http://" + self.location.hostname + ":8000/api/v1/stores", 
                    success: function(data) {
                        for(let store of data) {
                            if(store.people_count > store.max_capacity) {
                                alert("Too many people at " + store.name);
                            }
                        }
                    },
                    complete: function() {
                        // Schedule the next request when the current one's complete
                        setTimeout(securityWorker, 10000);
                    }
                });
            })();
        }
    }
});
