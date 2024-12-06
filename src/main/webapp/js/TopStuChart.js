async function fetchTopStudents() {
    const response = await fetch('/WEB_QLTT_IELTS/Top5Students');
    const data = await response.json();

    const labels = data.map(student => student.studentName);
    const scores = data.map(student => student.overallScore);

    const ctx = document.getElementById('scoreChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Overall Score',
                data: scores,
                backgroundColor: 'rgba(255, 165, 0, 0.8)', // Màu cam cho thanh bar
                borderColor: 'rgba(255, 165, 0, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,

            plugins: {
                title: {
                    display: true,
                    text: 'Top 5 highest-scoring students',
                    font: {
                        size: 20, // Tăng kích thước tiêu đề
                        weight: 'bold' // Đặt tiêu đề in đậm
                    }
                },
                legend: {
                    display: false
                },
                tooltip: {
                    enabled: false
                },
                datalabels: {
                    color: 'black',
                    anchor: 'end', // Giữ tên ở trung tâm thanh bar

                    align: 'start',// Giữ tên ở trung tâm thanh bar
                    rotation: -90, // Xoay tên theo chiều dọc
                     // Khoảng cách giữa tên và thanh bar
                     // Giữ vị trí giữa
                    formatter: (value, context) => context.chart.data.labels[context.dataIndex] // Hiển thị tên học sinh trong thanh bar

                },

            },
            scales: {
                y: {
                    display: true, // Ẩn số điểm trên trục y
                    min: 4,
                    max: 9,
                    grid: {
                        display: false // Ẩn các đường kẻ ngang
                    },

                },
                x: {
                    display: false, // Ẩn tên học sinh trên trục x
                    grid: {
                        display: false // Ẩn các đường kẻ dọc
                    }
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}

// Load data and render the chart
fetchTopStudents();
