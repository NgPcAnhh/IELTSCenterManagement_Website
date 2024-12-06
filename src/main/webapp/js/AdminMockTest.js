document.addEventListener("DOMContentLoaded", function() {
    loadAllMocktests();
});

let mockTests = []; // Lưu trữ toàn bộ danh sách mock tests

function loadAllMocktests() {
    fetch(`/WEB_QLTT_IELTS/MocktestManagement?action=getAllMockTests`)
        .then(response => response.json())
        .then(data => {
            // Sắp xếp dữ liệu theo thời gian gần nhất
            data.sort((a, b) => new Date(b.time) - new Date(a.time));
            // Lấy 10 giá trị gần nhất
            mockTests = data.slice(0, 0);
            displayMocktests(mockTests);
        })
        .catch(error => console.error("Error loading mock tests:", error));
}

function displayMocktests(mockTests) {
    const tableBody = document.getElementById("mockTestTableBody");
    tableBody.innerHTML = ""; // Xóa nội dung cũ của bảng

    mockTests.forEach(mockTest => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${mockTest.id}</td>
            <td>${mockTest.idTest}</td>
            <td>${mockTest.time}</td>
            <td>${mockTest.reading}</td>
            <td>${mockTest.listening}</td>
            <td>${mockTest.writing}</td>
            <td>${mockTest.speaking}</td>
            <td>${mockTest.overall}</td>
            <td>
                <button onclick="editMockTest('${mockTest.id}', '${mockTest.idTest}')">Edit Mock Test</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}



function searchMocktests() {
    // Lấy giá trị từ ô tìm kiếm
    const searchInput = document.getElementById("searchInput").value.toLowerCase();

    // Kiểm tra nếu input rỗng thì không thực hiện tìm kiếm
    if (!searchInput.trim()) {
        alert("Please enter a search term.");
        return;
    }

    // Gửi yêu cầu GET tới servlet
    fetch(`/WEB_QLTT_IELTS/adminMocktestSearch?query=${encodeURIComponent(searchInput)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // Hiển thị kết quả tìm kiếm
            displayMocktests(data);
        })
        .catch(error => console.error("Error searching mock tests:", error));
}



function showFeedbackDetails(mockTest) {
    const feedbackList = document.getElementById('feedbackList');
    feedbackList.innerHTML = `
        <li><strong>READING:</strong> ${mockTest.reading}<br><em>Feedback:</em> ${mockTest.feedback_r || 'No feedback available'}</li>
        <li><strong>LISTENING:</strong> ${mockTest.listening}<br><em>Feedback:</em> ${mockTest.feedback_l || 'No feedback available'}</li>
        <li><strong>WRITING:</strong> ${mockTest.writing}<br><em>Feedback:</em> ${mockTest.feedback_w || 'No feedback available'}</li>
        <li><strong>SPEAKING:</strong> ${mockTest.speaking}<br><em>Feedback:</em> ${mockTest.feedback_s || 'No feedback available'}</li>
    `;
    document.getElementById('feedbackPopup').style.display = 'flex';
}

// Close feedback popup
function closeFeedbackPopup() {
    document.getElementById('feedbackPopup').style.display = 'none';
}



function editMockTest(id, idTest) {
    // Gửi yêu cầu đến backend để lấy dữ liệu mock test
    fetch(`/WEB_QLTT_IELTS/MocktestManagement?action=getMocktest&id=${id}&idTest=${idTest}`)
        .then(response => response.json())
        .then(mockTest => {
            // Điền dữ liệu vào các trường trong popup
            document.getElementById("popupTitle").textContent = "Edit Mock Test";
            document.getElementById("mockTestStudentId").value = mockTest.id;
            document.getElementById("mockTestIdTest").value = mockTest.idTest;
            document.getElementById("mockTestDate").value = mockTest.time;
            document.getElementById("mockTestReading").value = mockTest.reading;
            document.getElementById("mockTestListening").value = mockTest.listening;
            document.getElementById("mockTestWriting").value = mockTest.writing;
            document.getElementById("mockTestSpeaking").value = mockTest.speaking;
            document.getElementById("mockTestOverall").value = mockTest.overall;
            document.getElementById("feedbackReading").value = mockTest.feedback_r || '';
            document.getElementById("feedbackListening").value = mockTest.feedback_l || '';
            document.getElementById("feedbackWriting").value = mockTest.feedback_w || '';
            document.getElementById("feedbackSpeaking").value = mockTest.feedback_s || '';

            // Hiển thị popup chỉnh sửa
            document.getElementById("mockTestPopup").style.display = "flex";

            // Gắn hàm xử lý lưu dữ liệu
            document.getElementById("saveButton").onclick = updateMockTest;
        })
        .catch(error => console.error("Error fetching mock test for edit:", error));
}

function closePopup() {
    document.getElementById("mockTestPopup").style.display = "none"; // Ẩn popup
    document.getElementById("mockTestPopup").querySelector(".popup-content").classList.remove("large"); // Xóa lớp "large" nếu có
}






function openAddMockTestPopup() {
    document.getElementById("popupTitle").textContent = "Add Mock Test";
    document.getElementById("mockTestForm").reset();

    // Bỏ readonly để người dùng có thể nhập
    document.getElementById("mockTestStudentId").readOnly = false;
    document.getElementById("mockTestIdTest").readOnly = false;

    document.getElementById("saveButton").onclick = addMockTest;
    document.getElementById("mockTestPopup").style.display = "flex";
}

function addMockTest() {
    const id = document.getElementById("mockTestStudentId").value;
    const idTest = document.getElementById("mockTestIdTest").value;
    const date = document.getElementById("mockTestDate").value;
    const reading = parseFloat(document.getElementById("mockTestReading").value);
    const listening = parseFloat(document.getElementById("mockTestListening").value);
    const writing = parseFloat(document.getElementById("mockTestWriting").value);
    const speaking = parseFloat(document.getElementById("mockTestSpeaking").value);
    const overall = parseFloat(document.getElementById("mockTestOverall").value);
    const feedbackR = document.getElementById("feedbackReading").value;
    const feedbackL = document.getElementById("feedbackListening").value;
    const feedbackW = document.getElementById("feedbackWriting").value;
    const feedbackS = document.getElementById("feedbackSpeaking").value;

    const mockTestData = {
        id,
        idTest,
        time: date,
        reading,
        listening,
        writing,
        speaking,
        overall,
        feedback_r: feedbackR,
        feedback_l: feedbackL,
        feedback_w: feedbackW,
        feedback_s: feedbackS,
        feedback_s: feedbackS
    };

    fetch('/WEB_QLTT_IELTS/MocktestManagement?action=addMocktest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(mockTestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Mock test added successfully!");
                closePopup();
                loadAllMocktests();
            } else {
                alert("Error adding mock test: " + (data.error || "Unknown error"));
            }
        })
        .catch(error => console.error("Error adding mock test:", error));
}


function updateMockTest() {
    const id = document.getElementById("mockTestStudentId").value;
    const idTest = document.getElementById("mockTestIdTest").value;
    const date = document.getElementById("mockTestDate").value;
    const reading = parseFloat(document.getElementById("mockTestReading").value);
    const listening = parseFloat(document.getElementById("mockTestListening").value);
    const writing = parseFloat(document.getElementById("mockTestWriting").value);
    const speaking = parseFloat(document.getElementById("mockTestSpeaking").value);
    const overall = parseFloat(document.getElementById("mockTestOverall").value);
    const feedbackR = document.getElementById("feedbackReading").value;
    const feedbackL = document.getElementById("feedbackListening").value;
    const feedbackW = document.getElementById("feedbackWriting").value;
    const feedbackS = document.getElementById("feedbackSpeaking").value;

    // Tạo object dữ liệu để gửi đến backend
    const mockTestData = {
        id,
        idTest,
        time: date,
        reading,
        listening,
        writing,
        speaking,
        overall,
        feedback_r: feedbackR,
        feedback_l: feedbackL,
        feedback_w: feedbackW,
        feedback_s: feedbackS
    };

    // Gửi dữ liệu đến backend để cập nhật
    fetch('/WEB_QLTT_IELTS/MocktestManagement?action=updateMockTest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(mockTestData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Mock test updated successfully!");
                closePopup();
                loadAllMocktests(); // Làm mới bảng sau khi cập nhật
            } else {
                alert("Error updating mock test: " + (data.error || "Unknown error"));
            }
        })
        .catch(error => console.error("Error updating mock test:", error));
}






document.addEventListener("DOMContentLoaded", function () {
    // Hàm gọi API để lấy dữ liệu top 10
    fetch("/WEB_QLTT_IELTS/ADMINTop10Overall")
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Failed to fetch top 10 overall: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            if (data.error) {
                console.error(data.error);
                alert("Không thể tải dữ liệu top 10. Vui lòng thử lại sau!");
                return;
            }

            // Hiển thị dữ liệu lên bảng
            populateTop10OverallTable(data);

            // Hiển thị dữ liệu biểu đồ
            renderChart(data);
        })
        .catch((error) => {
            console.error("Error fetching top 10 overall:", error.message);
            alert("Đã xảy ra lỗi khi tải dữ liệu. Vui lòng thử lại!");
        });

    // Hàm hiển thị dữ liệu lên bảng
    function populateTop10OverallTable(data) {
        const tableBody = document.getElementById("top1OverallTableBody");
        tableBody.innerHTML = ""; // Xóa nội dung cũ

        // Duyệt qua danh sách và thêm các hàng vào bảng
        data.forEach((item) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${item.studentId}</td>
                <td>${item.studentName}</td>
                <td>${item.phoneNumber}</td>
                <td>${item.gmail}</td>
            `;

            tableBody.appendChild(row);
        });
    }

    function renderChart(data) {
        const ctx = document.getElementById("top10OverallChart").getContext("2d");

        const studentIds = data.map((item) => item.studentId);
        const overallScores = data.map((item) => item.averageOverall);
        const mockTestCounts = data.map((item) => item.mockTestCount);

        new Chart(ctx, {
            type: "bar",
            data: {
                labels: studentIds,
                datasets: [
                    {
                        label: "Mock Test Count",
                        data: mockTestCounts,
                        type: "line",
                        borderColor: "skyblue",
                        fill: false,
                        yAxisID: "y2",
                    },
                    {
                        label: "Average Overall",
                        data: overallScores,
                        backgroundColor: [
                            "#FFE0B2", "#FFCC80", "#FFB74D", "#FFA726", "#FF9800",
                            "#FB8C00", "#F57C00", "#EF6C00", "#E65100", "#D84315",
                        ],
                        borderColor: "#FEB702",
                        borderWidth: 1,
                        yAxisID: "y1",
                    },
                ],
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true, // Hiển thị tiêu đề
                        text: "Top 10 Rankings of Average Overall", // Tiêu đề biểu đồ
                        font: {
                            size: 18, // Kích thước chữ
                            weight: "bold", // Đậm chữ
                        },
                        padding: {
                            top: 10,
                            bottom: 20,
                        },
                    },
                },

                scales: {
                    y1: {
                        type: "linear",
                        position: "left",
                        min: 7,
                        max: 9,
                        ticks: {
                            stepSize: 0.25, // Bước cố định
                            autoSkip: false, // Không tự động bỏ qua nhãn
                        },
                        title: {
                            display: true,
                            text: "Average Overall",
                        },
                    },
                    y2: {
                        type: "linear",
                        position: "right",
                        min: 3,
                        max: 15,
                        ticks: {
                            stepSize: 1,
                        },
                        title: {
                            display: true,
                            text: "Mock Test Count",
                        },
                    },
                },
            },
        });
    }
});
