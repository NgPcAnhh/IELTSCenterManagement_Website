document.addEventListener("DOMContentLoaded", () => {
    loadAssignmentDetails();
});

// Thêm sự kiện thay đổi cho bộ lọc subjectCode
document.getElementById("subjectCode").addEventListener("change", function () {
    const subjectCode = this.value;
    if (subjectCode !== "none") {
        fetch(`/WEB_QLTT_IELTS/ADMINFetchAssignments?subjectCode=${subjectCode}`)
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById("data-table").getElementsByTagName("tbody")[0];
                tableBody.innerHTML = ""; // Xóa các hàng cũ

                data.forEach(assignment => {
                    const row = tableBody.insertRow();

                    // Tạo ô có thể nhấp vào cho HW-ID
                    const hwIdCell = row.insertCell(0);
                    const hwIdLink = document.createElement("a");
                    hwIdLink.href = "#";
                    hwIdLink.textContent = assignment.hw_id;
                    hwIdLink.classList.add("hw-id-link");

                    // Thêm sự kiện nhấn để mở assignment-container và lưu vào localStorage
                    hwIdLink.addEventListener("click", function (e) {
                        e.preventDefault();
                        setAssignmentDataToLocalStorage(assignment.hw_id, assignment.student_id);
                        showAssignmentContainer(assignment);
                    });

                    hwIdCell.appendChild(hwIdLink);

                    // Thêm các ô dữ liệu khác
                    row.insertCell(1).textContent = assignment.hw_name;
                    row.insertCell(2).textContent = assignment.student_id;
                    row.insertCell(3).textContent = assignment.teacher_id;
                    row.insertCell(4).textContent = new Date(assignment.deadline).toLocaleDateString();
                    row.insertCell(5).textContent = assignment.checking;
                    row.insertCell(6).textContent = assignment.submit_time;
                });
            })
            .catch(error => console.error("Error fetching assignments:", error));
    }
});

// Gửi yêu cầu tìm kiếm và hiển thị kết quả trong bảng
document.getElementById("searchForm").addEventListener("submit", function (event) {
    event.preventDefault();
    const keyword = document.getElementById("searchInput").value;
    const resultMessage = document.getElementById("resultMessage");

    fetch(`/WEB_QLTT_IELTS/adminSearchAssignments?query=${encodeURIComponent(keyword)}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("data-table").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; // Xóa các hàng cũ

            if (data.length === 0) {
                resultMessage.style.display = "block";
            } else {
                resultMessage.style.display = "none";
                data.forEach(assignment => {
                    const row = tableBody.insertRow();

                    // Tạo ô có thể nhấp vào cho HW-ID
                    const hwIdCell = row.insertCell(0);
                    const hwIdLink = document.createElement("a");
                    hwIdLink.href = "#";
                    hwIdLink.textContent = assignment.hw_id;
                    hwIdLink.classList.add("hw-id-link");

                    hwIdLink.addEventListener("click", function (e) {
                        e.preventDefault();
                        setAssignmentDataToLocalStorage(assignment.hw_id, assignment.student_id);
                        showAssignmentContainer(assignment);
                    });

                    hwIdCell.appendChild(hwIdLink);

                    row.insertCell(1).textContent = assignment.hw_name;
                    row.insertCell(2).textContent = assignment.student_id;
                    row.insertCell(3).textContent = assignment.teacher_id;
                    row.insertCell(4).textContent = new Date(assignment.deadline).toLocaleDateString();
                    row.insertCell(5).textContent = assignment.checking;
                    row.insertCell(6).textContent = assignment.submit_time;
                });
            }
        })
        .catch(error => console.error("Error fetching search results:", error));
});

// Hàm lưu assignmentId và studentId vào localStorage
function setAssignmentDataToLocalStorage(assignmentId, studentId) {
    localStorage.setItem("assignmentId", assignmentId);
    localStorage.setItem("studentId", studentId);
}

// Hiển thị chi tiết Assignment trong assignment-container
function showAssignmentContainer(assignment) {
    const assignmentContainer = document.querySelector(".assignment-container");
    assignmentContainer.classList.add("active");
    assignmentContainer.style.display = "block";

    document.getElementById('assignment-data').innerHTML = `
        <tr>
            <td>${assignment.hw_id}</td>
            <td>${assignment.student_id}</td>
            <td>${assignment.hw_name}</td>
            <td>${assignment.teacher_id}</td>
            <td>${new Date(assignment.deadline).toLocaleDateString()}</td>
        </tr>
    `;

    fetchAssignmentDetails(assignment.hw_id, assignment.student_id);
}

// Đóng assignment-container khi nhấp ra ngoài
window.addEventListener("click", (e) => {
    const assignmentContainer = document.querySelector(".assignment-container");
    if (assignmentContainer.classList.contains("active") && !assignmentContainer.contains(e.target) && !e.target.classList.contains("hw-id-link")) {
        assignmentContainer.classList.remove("active");
        assignmentContainer.style.display = "none";
    }
});

// Hiển thị chi tiết Assignment trong container
function fetchAssignmentDetails(hwId, studentId) {
    fetch(`/WEB_QLTT_IELTS/adminGetAssignmentDetails?assignmentId=${hwId}&studentId=${studentId}`)
        .then(response => response.json())
        .then(data => {
            const handOnSection = document.querySelector('.hand-section .hand-box');
            handOnSection.innerHTML = "";

            if (data.hand_on) {
                const link = document.createElement("a");
                link.style.fontStyle = "italic";
                link.style.textDecoration = "underline";
                link.style.color = "#FF8C00";

                if (data.hand_on.startsWith("https://")) {
                    // Xử lý link web
                    link.href = data.hand_on;
                    link.target = "_blank";
                    link.textContent = data.hand_on;
                } else if (data.hand_on.startsWith("HW-storage")) {
                    const fileName = data.hand_on.replace("HW-storage/", "");
                    // Quan trọng: Đảm bảo đường dẫn đúng tới thư mục chứa file
                    const filePath = `/WEB_QLTT_IELTS/HW-storage/${fileName}`;
                    const fileExtension = fileName.split('.').pop().toLowerCase();

                    // Xử lý từng loại file
                    switch(fileExtension) {
                        case 'jpg':
                        case 'jpeg':
                        case 'png':
                        case 'gif':
                            // Xử lý ảnh - mở trực tiếp
                            link.href = filePath;
                            link.target = "_blank";
                            link.textContent = fileName;
                            break;

                        case 'pdf':
                            // Xử lý PDF - mở trực tiếp
                            link.href = filePath;
                            link.target = "_blank";
                            link.type = "application/pdf";
                            link.textContent = fileName;
                            break;

                        case 'doc':
                        case 'docx':
                            // Sử dụng full URL cho Google Docs Viewer
                            const fullUrl = window.location.protocol + "//" + window.location.host + filePath;
                            link.href = `https://docs.google.com/viewer?url=${encodeURIComponent(fullUrl)}&embedded=true`;
                            link.target = "_blank";
                            link.textContent = fileName;
                            break;

                        case 'txt':
                            // Xử lý file text
                            link.href = filePath;
                            link.target = "_blank";
                            link.type = "text/plain";
                            link.textContent = fileName;
                            break;

                        default:
                            // Cho phép tải về với các định dạng khác
                            link.href = filePath;
                            link.download = fileName;
                            link.textContent = `Download ${fileName}`;
                            break;
                    }
                }

                handOnSection.appendChild(link);
            } else {
                handOnSection.textContent = "No submission available";
            }

            // Xử lý feedback
            const feedbackInput = document.querySelector('.feedback-section .input-box');
            feedbackInput.value = data.feedbacks || "";
        })
        .catch(error => {
            console.error('Error fetching assignment details:', error);
            handOnSection.textContent = "Error loading submission";
        });
}


// Xử lý xóa Assignment
document.querySelector('.submit-btn').addEventListener('click', function() {
    const assignmentId = document.getElementById('assignmentIdInput').value;
    if (assignmentId) {
        fetch('/WEB_QLTT_IELTS/adminDeleteAssignment', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ hw_id: assignmentId })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Assignment deleted successfully!");
                    document.getElementById('deleteLayoutOverlay').style.display = 'none';
                    loadAssignmentDetails();
                } else {
                    alert("Failed to delete assignment. Please try again.");
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        alert("Please enter an Assignment ID.");
    }
});

// Hiển thị và ẩn layout Delete Assignment
document.querySelector('.delete-button').addEventListener('click', function() {
    document.getElementById('deleteLayoutOverlay').style.display = 'block';
});

window.addEventListener('click', function(event) {
    const overlay = document.getElementById('deleteLayoutOverlay');
    if (event.target === overlay) {
        overlay.style.display = 'none';
    }
});

// Mở layout Create Assignment
document.querySelector('.create-button').addEventListener('click', () => {
    const assignmentModal = document.getElementById("assignmentModal");
    assignmentModal.classList.add("active");
    assignmentModal.style.display = "block";
});

window.addEventListener('click', function(event) {
    const assignmentModal = document.getElementById('assignmentModal');
    if (event.target === assignmentModal) {
        assignmentModal.style.display = 'none';
        assignmentModal.classList.remove('active');
    }
});

// Tạo mới Assignment
document.getElementById('assignmentForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = {
        hwId: document.getElementById('hw-id').value,
        hwName: document.getElementById('hw-name').value,
        teacherId: document.getElementById('teacher-id').value,
        deadline: document.getElementById('deadline').value,
        subjectCode: document.getElementById('subjectCode').value
    };

    fetch('/WEB_QLTT_IELTS/adminCreateAssignment', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Assignment created successfully!");
                document.getElementById("assignmentModal").classList.remove("active");
                document.getElementById("assignmentModal").style.display = "none";
                document.getElementById('assignmentForm').reset();
                loadAssignmentDetails(); // Refresh assignment list
            } else {
                alert("Failed to create assignment. Please try again.");
            }
        })
        .catch(error => console.error('Error:', error));
});

// Tải dữ liệu assignment khi trang tải xong
function loadAssignmentDetails() {
    const assignmentId = localStorage.getItem("assignmentId");
    const studentId = localStorage.getItem("studentId");

    if (assignmentId && studentId) {
        fetch(`/WEB_QLTT_IELTS/adminGetAssignmentDetails?assignmentId=${assignmentId}&studentId=${studentId}`)
            .then(response => response.json())
            .then(data => {
                if (data) {
                    const handBox = document.querySelector(".hand-box");
                    handBox.innerHTML = `<p>${data.hand_on || 'No data available'}</p>`;

                    const feedbackBox = document.querySelector(".input-box");
                    feedbackBox.value = data.feedbacks || '';
                }
            })
            .catch(error => console.error("Error fetching assignment details:", error));
    } else {
        console.error("Assignment ID or Student ID is missing in localStorage.");
    }
}

function submitFeedback() {
    const assignmentId = localStorage.getItem("assignmentId");
    const studentId = localStorage.getItem("studentId");
    const feedback = document.querySelector(".input-box").value;

    if (assignmentId && studentId && feedback !== undefined) {
        fetch("/WEB_QLTT_IELTS/adminGetAssignmentDetails", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ assignmentId, studentId, feedback })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Feedback submitted successfully!");
                } else {
                    alert("Failed to submit feedback.");
                }
            })
            .catch(error => console.error("Error submitting feedback:", error));
    } else {
        alert("Assignment ID, Student ID, or feedback is missing.");
    }
}
