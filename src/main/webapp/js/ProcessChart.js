function calculateAverageOverall(scores) {
    if (!scores || scores.length === 0) return 0;
    let totalOverall = scores.reduce((sum, score) => sum + score, 0);
    let averageOverall = totalOverall / scores.length;

    if (averageOverall % 1 === 0.5 || averageOverall % 1 === 0) {
        return averageOverall.toFixed(1);
    } else if (averageOverall % 1 < 0.25) {
        return Math.floor(averageOverall);
    } else if (averageOverall % 1 >= 0.75) {
        return Math.ceil(averageOverall);
    } else {
        return (Math.floor(averageOverall) + 0.5).toFixed(1);
    }
}

function updateOverallBar(averageOverall) {
    const fillElement = document.getElementById("averageOverallFill");
    const scoreElement = document.getElementById("averageOverallScore");
    const maxScore = 9.0;
    const fillHeight = (averageOverall / maxScore) * 400;
    fillElement.style.height = `${fillHeight}px`;
    scoreElement.textContent = averageOverall;
}

async function fetchTestScores() {
    try {
        const response = await fetch(`/WEB_QLTT_IELTS/processChartScores`);
        if (!response.ok) throw new Error('Không thể lấy dữ liệu từ server');
        return await response.json();
    } catch (error) {
        console.error("Lỗi khi lấy điểm thi:", error);
        return [];
    }
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return `${date.getDate()}/${date.getMonth() + 1}/${date.getFullYear()}`;
}

function createOverallChart(canvasId, dates, overall) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    ctx.canvas.style.height = '600px'; // Tăng chiều cao của canvas để trục Y dài hơn

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: dates,
            datasets: [{
                label: 'Overall',
                data: overall,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgb(54, 162, 235)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false, // Cho phép kiểm soát chiều cao và chiều rộng độc lập
            layout: {
                padding: {
                    top: 20, // Thêm khoảng trống phía trên
                    right: 20, // Thêm khoảng trống bên phải
                    bottom: 40, // Thêm khoảng trống phía dưới
                    left: 20, // Thêm khoảng trống bên trái
                },
            },
            plugins: {
                title: {
                    display: true,
                    text: 'Overall Score Progress',
                    font: { size: 20 }
                }
            },
            scales: {
                x: {
                    ticks: {
                        font: { size: 12 }, // Kích thước font của trục X
                        padding: 10 // Thêm khoảng cách giữa nhãn và trục
                    }
                },
                y: {
                    min: 0,
                    max: 9.0, // Giới hạn trục Y từ 0 đến 9
                    ticks: {
                        stepSize: 1, // Chia bước nhảy trục Y thành 1 đơn vị
                        font: { size: 14 }, // Kích thước font của trục Y
                        padding: 10 // Thêm khoảng cách giữa nhãn và trục
                    },
                    title: {
                        display: true,
                        text: 'Scores', // Tiêu đề trục Y
                        font: { size: 14 }
                    }
                }
            }
        }
    });
}





document.addEventListener("DOMContentLoaded", function () {
    function loadTopStudentsChart() {
        fetch("/WEB_QLTT_IELTS/StuTopOverall")
            .then((response) => response.json())
            .then((data) => {
                if (!data || data.length === 0) {
                    console.error("No data available for the top students chart.");
                    return;
                }

                // Chuẩn bị dữ liệu cho biểu đồ
                const labels = data.map((student) => student.studentId); // ID học sinh
                const scores = data.map((student) => student.averageOverall); // Điểm trung bình

                // Lấy phần tử canvas
                const ctx = document.getElementById("topStudentsChart").getContext("2d");

                // Vẽ biểu đồ
                new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: "Top 5 Students",
                                data: scores,
                                backgroundColor: "rgba(255, 193, 7, 0.8)", // Màu vàng
                                borderColor: "rgba(255, 193, 7, 1)", // Viền vàng
                                borderWidth: 1,
                                barThickness: 30, // Chiều rộng của cột
                            },
                        ],
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        layout: {
                            padding: {
                                top: 20, // Thêm khoảng cách phía trên
                                right: 20, // Thêm khoảng cách bên phải
                                bottom: 40, // Đẩy nội dung phía dưới lên
                                left: 20, // Thêm khoảng cách bên trái
                            },
                        },
                        plugins: {
                            legend: {
                                display: true, // Hiển thị tên biểu đồ
                                position: "top", // Đặt tên biểu đồ lên trên
                            },
                            tooltip: {
                                enabled: true, // Hiển thị tooltip
                            },
                        },
                        scales: {
                            x: {
                                ticks: {
                                    display: true, // Hiển thị nhãn trên trục X
                                    font: {
                                        size: 12, // Kích thước chữ
                                    },
                                    padding: 10, // Thêm khoảng cách giữa trục X và nhãn
                                },
                            },
                            y: {
                                beginAtZero: true,
                                min: 0, // Giới hạn tối thiểu trục Y là 0
                                max: 9, // Giới hạn tối đa trục Y là 9
                                ticks: {
                                    stepSize: 1, // Bước nhảy trục Y là 1
                                    font: {
                                        size: 14, // Kích thước chữ
                                    },
                                },
                                title: {
                                    display: true,
                                    text: "Scores",
                                    font: {
                                        size: 14,
                                    },
                                },
                            },
                        },
                        animation: {
                            duration: 500, // Giảm thời gian hoạt ảnh
                        },
                    },
                });
            })
            .catch((error) => console.error("Error fetching data:", error));
    }

    // Gọi hàm khi trang được load
    loadTopStudentsChart();
});










function createSkillChart(canvasId, dates, scores, skillName) {
    const ctx = document.getElementById(canvasId).getContext('2d');
    ctx.canvas.style.height = '300px';

    const colors = {
        Reading: 'rgb(75, 192, 192)',
        Listening: 'rgb(255, 99, 132)',
        Writing: 'rgb(255, 205, 86)',
        Speaking: 'rgb(153, 102, 255)'
    };

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: dates,
            datasets: [{
                label: skillName,
                data: scores,
                borderColor: colors[skillName],
                backgroundColor: colors[skillName],
                tension: 0.1,
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: `${skillName} Progress`,
                    font: { size: 16 }
                }
            },
            scales: {
                y: {
                    min: 0,
                    max: 9.0,
                    ticks: {
                        stepSize: 1
                    }
                }
            }
        }
    });
}

async function initializeCharts() {
    document.body.style.overflowX = 'hidden';
    document.documentElement.style.overflowX = 'hidden';

    const testScores = await fetchTestScores();
    if (testScores.length === 0) return;

    const dates = testScores.map(score => formatDate(score.time));
    const overall = testScores.map(score => score.overall);

    createOverallChart('combinedChart', dates, overall);

    const skills = ['Reading', 'Listening', 'Writing', 'Speaking'];
    skills.forEach(skill => {
        const skillLower = skill.toLowerCase();
        createSkillChart(
            `${skillLower}Chart`,
            dates,
            testScores.map(score => score[skillLower]),
            skill
        );
    });

    const averageOverall = calculateAverageOverall(overall);
    updateOverallBar(averageOverall);
}

document.addEventListener("DOMContentLoaded", () => {
    const chartContainer = document.querySelector('.chart-container');
    if (chartContainer) {
        chartContainer.style.width = '90%';
        chartContainer.style.maxWidth = '1200px';
        chartContainer.style.margin = '20px auto';
        chartContainer.style.overflowX = 'hidden';
    }

    const skillsSection = document.querySelector('.skills-section');
    if (skillsSection) {
        skillsSection.style.width = '90%';
        skillsSection.style.maxWidth = '1200px';
        skillsSection.style.margin = '20px auto';
        skillsSection.style.display = 'grid';
        skillsSection.style.gridTemplateColumns = 'repeat(2, 1fr)';
        skillsSection.style.gap = '20px';
    }

    initializeCharts();
});




document.addEventListener("DOMContentLoaded", function () {
    fetch("/WEB_QLTT_IELTS/StuRanking")
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Failed to fetch ranking data: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            if (data.error) {
                console.error(data.error);
                alert("Không thể lấy dữ liệu xếp hạng. Vui lòng thử lại sau!");
                return;
            }

            // Hiển thị dữ liệu trên giao diện
            console.log("Displayed data:", data); // Log JSON để kiểm tra
            displayRankingData(data);
        })
        .catch((error) => {
            console.error("Error fetching ranking data:", error.message);
            alert("Đã xảy ra lỗi khi tải dữ liệu. Vui lòng thử lại!");
        });

    // Hàm hiển thị dữ liệu lên HTML
    function displayRankingData(data) {
        // Kiểm tra và gán giá trị
        document.getElementById("overallScore").textContent = data.overall || "N/A";
        document.getElementById("rank").textContent = data.rank || "N/A";
        document.getElementById("betterThan").textContent = `${data.betterThan || 0}%`;
        document.getElementById("worseThan").textContent = `${data.worseThan || 0}%`;

        // Xác định nhóm dựa trên điểm overall
        const group = classifyGroup(data.overall);
        document.getElementById("group").textContent = group;
    }

    // Hàm phân loại nhóm
    function classifyGroup(overallScore) {
        if (!overallScore || isNaN(overallScore)) return "Không xác định";
        if (overallScore <= 5.5) return "Cần cố gắng thật nhiều";
        if (overallScore <= 6.5) return "Sắp tới đích rồi";
        if (overallScore <= 7) return "Trung bình - Khá";
        if (overallScore <= 7.5) return "Giỏi";
        return "Xuất sắc";
    }
});




