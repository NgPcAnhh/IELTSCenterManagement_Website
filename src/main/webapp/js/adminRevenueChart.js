document.addEventListener("DOMContentLoaded", () => {
    fetchRevenueData();
});

function fetchRevenueData() {
    fetch('/WEB_QLTT_IELTS/adminRevenueData')
        .then(response => response.json())
        .then(data => generateQuarterlyRevenueChart(data))
        .catch(error => console.error("Error fetching revenue data:", error));
}

function generateQuarterlyRevenueChart(data) {
    // Nhóm dữ liệu theo quý
    const quarterlyData = groupDataByQuarter(data);
    const labels = [];
    const revenues = [];

    // Chuyển đổi dữ liệu theo định dạng quý
    Object.entries(quarterlyData).forEach(([key, value]) => {
        labels.push(key);
        revenues.push(value / 1_000_000); // Chuyển đổi sang đơn vị triệu
    });

    const ctx = document.getElementById("revenueChart").getContext("2d");

    new Chart(ctx, {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Quarterly Revenue (in Millions)",
                data: revenues,
                backgroundColor: getComputedStyle(document.documentElement).getPropertyValue('--chart-bg-color') || "#2CFF05",
                borderColor: getComputedStyle(document.documentElement).getPropertyValue('--chart-border-color') || "#2CFF05",
                borderWidth: 1
            }]
        },
        options: {
            maintainAspectRatio: false,
            scales: {
                x: {
                    title: {
                        display: true,
                        text: "Quarter / Year"
                    },
                    grid: {
                        display: false
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: "Revenue (in Millions)"
                    },
                    ticks: {
                        callback: function(value) {
                            return value + "M";
                        }
                    }
                }
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return `Revenue: ${tooltipItem.raw}M`;
                        }
                    }
                },
                legend: {
                    display: true,
                    position: "top"
                }
            }
        }
    });
}

function groupDataByQuarter(data) {
    const quarterlyData = {};

    data.forEach(record => {
        const month = parseInt(record.month);
        const year = record.year;
        const quarter = Math.ceil(month / 3);
        const key = `Q${quarter}/${year}`;

        if (!quarterlyData[key]) {
            quarterlyData[key] = 0;
        }
        quarterlyData[key] += record.total_price;
    });

    // Sắp xếp các quý theo thứ tự thời gian
    return Object.fromEntries(
        Object.entries(quarterlyData)
            .sort((a, b) => {
                const [q1, y1] = a[0].split('/');
                const [q2, y2] = b[0].split('/');
                if (y1 !== y2) return parseInt(y1) - parseInt(y2);
                return q1.localeCompare(q2);
            })
    );
}