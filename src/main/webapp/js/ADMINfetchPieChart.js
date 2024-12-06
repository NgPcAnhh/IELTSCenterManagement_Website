document.addEventListener("DOMContentLoaded", fetchChartData);

function fetchChartData() {
    fetch('/WEB_QLTT_IELTS/adminGetCourseCounts')
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                renderCombinedChart(data.data);
            } else {
                console.error(data.message);
                alert("Failed to fetch course and revenue data.");
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            alert("An error occurred while connecting to the server.");
        });
}

function renderCombinedChart(data) {
    const ctx = document.getElementById("combinedChart").getContext("2d");

    // Extract data for chart
    const labels = data.map(item => item.ma_mon_hoc);
    const counts = data.map(item => item.SL);
    const revenues = data.map(item => item.revenue);

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    type: 'bar',
                    label: 'Course Count',
                    data: counts,
                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
                    yAxisID: 'y'
                },
                {
                    type: 'line',
                    label: 'Revenue (VND)',
                    data: revenues,
                    borderColor: 'rgba(255, 99, 132, 1)',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    fill: false,
                    yAxisID: 'y1'
                }
            ]
        },
        options: {
            responsive: true,
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Course Code'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Course Count'
                    }
                },
                y1: {
                    beginAtZero: true,
                    position: 'right',
                    title: {
                        display: true,
                        text: 'Revenue (VND)'
                    },
                    ticks: {
                        callback: function(value) {
                            return value >= 1000000 ? value / 1000000 + 'M' : value;
                        }
                    }
                }
            },
            plugins: {
                legend: { display: true }
            }
        }
    });
}
