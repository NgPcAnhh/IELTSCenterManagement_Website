// Gửi yêu cầu đến servlet để lấy thông tin sinh viên và cập nhật giao diện
function fetchStudentInfo() {
    fetch('/WEB_QLTT_IELTS/getPersonalInfo', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Kiểm tra nếu dữ liệu sinh viên có tồn tại
            if (data.studentName) {
                loadStudentInfo({
                    name: data.studentName,
                    studentID: data.studentId,
                    birthDate: data.dateOfBirth,
                    phoneNumber: data.phoneNumber,
                    email: data.email,
                    parent: data.parentName
                });
            } else {
                alert(data.message || "Không tìm thấy thông tin sinh viên.");
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin sinh viên:', error);
            alert("Lỗi khi kết nối đến server hoặc không nhận được dữ liệu hợp lệ.");
        });
}

// Gửi yêu cầu đến servlet để lấy thông tin assignment và cập nhật giao diện
function fetchAssignmentInfo() {
    fetch('/WEB_QLTT_IELTS/getAssignmentInfo', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data) && data.length > 0) {
                // Sort assignments by deadline in descending order (newest to oldest)
                data.sort((a, b) => new Date(b.deadline) - new Date(a.deadline));
                loadAssignmentInfo(data);
            } else {
                alert("Không có dữ liệu assignment.");
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin assignment:', error);
            alert("Lỗi khi kết nối đến server hoặc không nhận được dữ liệu hợp lệ.");
        });
}

// Hàm để tải dữ liệu vào phần thông tin cá nhân trong giao diện
function loadStudentInfo(data) {
    const infoContent = document.getElementById('student-info');
    if (infoContent) {
        infoContent.innerHTML = `
            <li>Name: ${data.name}</li>
            <li>Student’s ID: ${data.studentID}</li>
            <li>Date of Birth: ${data.birthDate}</li>
            <li>Phone Number: ${data.phoneNumber}</li>
            <li>Email: ${data.email}</li>
            <li>Parent: ${data.parent}</li>
        `;
    }
}

// Hàm để tải dữ liệu assignment vào bảng trong giao diện
function loadAssignmentInfo(assignments) {
    const assignmentContent = document.getElementById('assignment-data');
    assignmentContent.innerHTML = ''; // Clear existing data

    assignments.forEach(assignment => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${assignment.hwId}</td>
            <td>${assignment.hwName}</td>
            <td>${assignment.checking}</td>
            <td>${assignment.deadline}</td>
        `;
        assignmentContent.appendChild(row);
    });
}

// Tự động gọi fetchStudentInfo và fetchAssignmentInfo khi trang tải
window.onload = function() {
    fetchStudentInfo();
    fetchAssignmentInfo();
};


// Gửi yêu cầu đến servlet để lấy thông tin mock test gần nhất và cập nhật giao diện
function fetchLatestMockTest() {
    fetch('/WEB_QLTT_IELTS/getLatestMockTest', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.idTest) {
                loadMockTestInfo(data);
            } else {
                alert(data.message || "Không có dữ liệu mock test.");
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin mock test:', error);
            alert("Lỗi khi kết nối đến server hoặc không nhận được dữ liệu hợp lệ.");
        });
}

// Hàm để tải dữ liệu mock test vào giao diện
function loadMockTestInfo(data) {
    const mockTestContent = document.getElementById('mock-test-info');
    if (mockTestContent) {
        mockTestContent.innerHTML = `
            <p>ID Test: ${data.idTest}</p>
            <p>Time: ${data.time}</p>
            <p>Reading: ${data.reading}</p>
            <p>Listening: ${data.listening}</p>
            <p>Writing: ${data.writing}</p>
            <p>Speaking: ${data.speaking}</p>
            <p>Overall: ${data.overall}</p>
        `;
    }
}

// Tự động gọi fetchLatestMockTest khi trang tải
window.onload = function() {
    fetchStudentInfo();
    fetchAssignmentInfo();
    fetchLatestMockTest();
};



// Gửi yêu cầu đến servlet để lấy danh sách mock test và tạo biểu đồ
function fetchMockTests() {
    fetch('/WEB_QLTT_IELTS/getMockTests', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data) && data.length > 0) {
                renderMockTestChart(data);    // Tạo biểu đồ mock test
            } else {
                alert(data.message || "Không có dữ liệu mock test.");
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin mock test:', error);
            alert("Lỗi khi kết nối đến server hoặc không nhận được dữ liệu hợp lệ.");
        });
}

// Hàm vẽ biểu đồ cột mock test với Chart.js trong chart-container
function renderMockTestChart(mockTests) {
    const ctx = document.getElementById('mockTestChart').getContext('2d');

    // Dữ liệu cho biểu đồ
    const labels = mockTests.map(test => test.time);     // Thời gian của mock test
    const scores = mockTests.map(test => test.overall);  // Điểm số overall

    new Chart(ctx, {
        type: 'bar', // Sử dụng biểu đồ cột
        data: {
            labels: labels,
            datasets: [{
                label: 'Overall Score',
                data: scores,
                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            indexAxis: 'y', // Đặt trục Y làm trục chính cho cột
            scales: {
                x: {
                    min: 0,
                    max: 9.0,
                    ticks: {
                        stepSize: 0.5
                    },
                    title: {
                        display: true,
                        text: 'Overall Score'
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Test Time'
                    }
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                }
            },
            responsive: true,
            maintainAspectRatio: false // Giữ tỉ lệ phù hợp với kích thước container
        }
    });
}

// Gọi tất cả các hàm fetch khi trang tải
window.onload = function() {
    fetchStudentInfo();
    fetchAssignmentInfo();
    fetchLatestMockTest();
    fetchMockTests();
};
