let revenueChart;
let revenuePieChart;

document.addEventListener("DOMContentLoaded", function () {
    // Tải doanh thu tổng thể khi trang tải
    fetchTotalRevenue();

    // Khởi động fetchBills khi người dùng nhấp vào nút tìm kiếm
    document.getElementById("fetchBillsButton").addEventListener("click", fetchBills);
});

// Hàm vẽ biểu đồ tròn
function renderPieChart(revenueBySubject) {
    const subjectIds = Object.keys(revenueBySubject);
    const revenues = Object.values(revenueBySubject);

    if (revenuePieChart) {
        revenuePieChart.destroy();
    }

    const ctx = document.getElementById('revenuePieChart').getContext('2d');
    revenuePieChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: subjectIds,
            datasets: [{
                label: 'Tỷ lệ đăng ký mã môn học',
                data: revenues,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)',
                    'rgba(255, 159, 64, 0.6)',
                    'rgba(128, 0, 128, 0.6)',
                    'rgba(0, 255, 127, 0.6)',
                    'rgba(0, 191, 255, 0.6)'
                ],
                borderColor: 'rgba(255, 255, 255, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: {
                    position: 'right',
                    align: 'start',
                    labels: {
                        padding: 10,
                        boxWidth: 20 // Điều chỉnh kích thước hộp màu cho chú thích
                    }
                },
                tooltip: {
                    callbacks: {
                        label: context => {
                            const total = context.dataset.data.reduce((sum, val) => sum + val, 0);
                            const percentage = ((context.raw / total) * 100).toFixed(2);
                            return `${context.label}: ${percentage}%`;
                        }
                    }
                }
            },
            layout: {
                padding: {
                    left: 20,
                    right: 20
                }
            }
        }
    });

    // Đặt chiều rộng tối đa cho chú thích
    const legendItems = document.querySelectorAll('#revenuePieChart + div ul li');
    legendItems.forEach((item, index) => {
        item.style.width = '50%'; // Chia đều thành hai cột
    });
}


// Hàm vẽ biểu đồ cột
function renderChart(revenueBySubject) {
    const subjectIds = Object.keys(revenueBySubject);
    const revenues = Object.values(revenueBySubject);

    if (revenueChart) {
        revenueChart.destroy();
    }

    const ctx = document.getElementById('revenueChart').getContext('2d');
    revenueChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: subjectIds,
            datasets: [{
                label: 'Doanh thu theo mã môn học',
                data: revenues,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // Đảm bảo biểu đồ tăng chiều cao
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}


// Hàm lấy và hiển thị dữ liệu bills vào bảng, cũng như tính toán doanh thu và hiển thị
function fetchBills() {
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;

    if (!startDate || !endDate) {
        alert("Please select both start and end dates.");
        return;
    }

    fetch(`/WEB_QLTT_IELTS/getBillSummary?startDate=${startDate}&endDate=${endDate}`)
        .then(response => response.json())
        .then(data => {
            updateBillTable(data);
            calculateRevenue(data);
            updateCharts(data);
        })
        .catch(error => console.error('Error fetching bills:', error));
}

// Hàm cập nhật bảng với dữ liệu từ bills
function updateBillTable(data) {
    const billResults = document.getElementById("billResults");
    billResults.innerHTML = "";

    data.forEach(bill => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${bill.subjectId}</td>
            <td>${bill.price}</td>
            <td>${bill.totalPrice}</td>
            <td>${bill.quantity}</td>
            <td>${bill.percentage}</td>
        `;
        billResults.appendChild(row);
    });
}

// Hàm tính toán tổng doanh thu từ bảng bills và hiển thị kết quả
function calculateRevenue(data) {
    let totalRevenue = 0;
    data.forEach(bill => {
        const price = parseFloat(bill.totalPrice);
        if (!isNaN(price)) {
            totalRevenue += price;
        }
    });
    document.getElementById("revenueAmount").textContent = totalRevenue.toLocaleString();
}

// Hàm chuẩn bị dữ liệu và gọi hàm vẽ biểu đồ
function updateCharts(data) {
    const revenueBySubject = {};

    data.forEach(bill => {
        revenueBySubject[bill.subjectId] = bill.totalPrice;
    });

    renderChart(revenueBySubject);
    renderPieChart(revenueBySubject);
}

// Hàm tải doanh thu tổng thể từ server
function fetchTotalRevenue() {
    fetch('/WEB_QLTT_IELTS/getRevenue')
        .then(response => response.json())
        .then(data => {
            const totalRevenueData = data.totalRevenue;
            const monthsDifference = calculateMonthsFrom2020();
            const averageMonthlyRevenue = totalRevenueData / monthsDifference;

            document.getElementById('revenueAmounttotal').innerText = totalRevenueData.toLocaleString();
            document.getElementById('averageRevenue').innerText = averageMonthlyRevenue.toFixed(2);
        })
        .catch(error => {
            console.error("Error fetching revenue:", error);
            document.getElementById('revenueAmount').innerText = "Failed to load revenue data.";
        });
}

// Tính toán số tháng từ tháng 1/2020 đến thời điểm hiện tại
function calculateMonthsFrom2020() {
    const startRevenueDate = new Date(2020, 0, 1);
    const currentRevenueDate = new Date();
    return (currentRevenueDate.getFullYear() - startRevenueDate.getFullYear()) * 12
        + (currentRevenueDate.getMonth() - startRevenueDate.getMonth()) + 1;
}

// Hàm tính tổng chi phí và lợi nhuận trước thuế
function calculateTotal() {
    let totalCost = 0;
    const totalRevenue = parseFloat(document.getElementById('revenueAmount').textContent.replace(/,/g, '')) || 0;

    document.querySelectorAll('.cost').forEach(input => {
        totalCost += parseFloat(input.value) || 0;
    });

    const profitBeforeTax = totalRevenue - totalCost;
    document.getElementById('total-cost').textContent = totalCost.toLocaleString();
    document.getElementById('profitAmount').textContent = profitBeforeTax.toLocaleString();
}





document.getElementById("exportReportButton").addEventListener("click", function () {
    const content = document.querySelector("body");

    const sidebar = document.querySelector(".sidebar");
    const exportButton = document.getElementById("exportReportButton");
    const logoutButton = document.querySelector(".login-button");
    if (sidebar) sidebar.style.display = "none";
    if (exportButton) exportButton.style.display = "none";
    if (logoutButton) logoutButton.style.display = "none";

    html2canvas(content, {
        scale: 2,
        width: content.scrollWidth,
        height: content.scrollHeight
    }).then(canvas => {
        if (sidebar) sidebar.style.display = "block";
        if (exportButton) exportButton.style.display = "block";
        if (logoutButton) logoutButton.style.display = "block";

        canvas.toBlob(function(blob) {
            const url = URL.createObjectURL(blob);
            const link = document.createElement("a");
            link.href = url;
            link.download = "report.png";
            link.click();
            URL.revokeObjectURL(url);
        }, "image/png");
    });
});
