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
        // date = date.split('/')[0];
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

    function showInfo(data) {
        data.sort(function(a, b) {
            return a.hour - b.hour;
        });

        const dataset = data.map(a => a.count);
        const labels = data.map(a => a.hour);
        console.log(dataset);
        console.log(labels);
        console.log(data);

        const ctx = document.getElementById('myChart').getContext('2d');
        const myChart = new Chart(ctx, {
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
});
