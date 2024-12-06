// Gửi yêu cầu đến servlet để lấy tất cả các assignment và hiển thị trong bảng
function fetchAllAssignments() {
    fetch('/WEB_QLTT_IELTS/getAllAssignments', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data) && data.length > 0) {
                loadAssignmentsData(data);
            } else {
                alert("Không có dữ liệu assignment.");
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin assignment:', error);
            alert("Lỗi khi kết nối đến server hoặc không nhận được dữ liệu hợp lệ.");
        });
}

// Gửi yêu cầu đến servlet để lấy các assignment đã hoàn thành và hiển thị trong bảng
function fetchCompletedAssignments() {
    fetch('/WEB_QLTT_IELTS/getCompletedAssignments', { method: 'GET' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (Array.isArray(data) && data.length > 0) {
                loadAssignmentsData(data);
            } else {
                alert("Không có assignment đã hoàn thành.");
            }
        })
        .catch(error => {
            console.error('Lỗi khi lấy thông tin assignment:', error);
            alert("Lỗi khi kết nối đến server hoặc không nhận được dữ liệu hợp lệ.");
        });
}

// Chuyển hướng sang Homework.html
function loadAssignmentsData(assignments) {
    const tableBody = document.getElementById('Homework-Table').querySelector('tbody');
    tableBody.innerHTML = ''; // Xóa dữ liệu cũ

    assignments.forEach(assignment => {
        const row = document.createElement('tr');

        // Dữ liệu ASSIGNMENT ID dưới dạng liên kết đến trang Homework.html
        const hwIdCell = document.createElement('td');
        const hwIdLink = document.createElement('a');
        hwIdLink.textContent = assignment.hwId;

        // Xử lý sự kiện click để chuyển hướng đến Homework.html
        hwIdLink.onclick = (e) => {
            e.preventDefault(); // Ngăn hành động mặc định của thẻ <a>
            localStorage.setItem('HW_id', assignment.hwId); // Lưu HW ID vào localStorage
            window.location.href = 'Homework.html'; // Chuyển hướng đến Homework.html
        };
        hwIdCell.appendChild(hwIdLink);

        // Các cột khác
        const hwNameCell = document.createElement('td');
        hwNameCell.textContent = assignment.hwName;

        const teacherIdCell = document.createElement('td');
        teacherIdCell.textContent = assignment.teacherId;

        const deadlineCell = document.createElement('td');
        deadlineCell.textContent = assignment.deadline;

        const checkingCell = document.createElement('td');
        checkingCell.textContent = assignment.checking || 'Incomplete';

        // Thêm tất cả các ô vào hàng
        row.appendChild(hwIdCell);
        row.appendChild(hwNameCell);
        row.appendChild(teacherIdCell);
        row.appendChild(deadlineCell);
        row.appendChild(checkingCell);

        // Thêm hàng vào bảng
        tableBody.appendChild(row);
    });
}

// Hiển thị hộp thoại xác nhận đăng xuất
function showLogoutConfirmation() {
    const logoutDialog = document.getElementById('logoutDialog');
    if (logoutDialog) {
        logoutDialog.style.display = 'block';
    }
}

// Xử lý phản hồi đăng xuất
function handleLogout(confirmed) {
    if (confirmed) {
        window.location.href = 'login.html';
    } else {
        const logoutDialog = document.getElementById('logoutDialog');
        if (logoutDialog) {
            logoutDialog.style.display = 'none';
        }
    }
}

// Đóng mở sidebar
function chinhSideBar() {
    const sidebar = document.querySelector('.sidebar');
    const openSidebarButton = document.getElementById('openSidebar');
    sidebar.classList.toggle('closed');

    if (sidebar.classList.contains('closed')) {
        openSidebarButton.style.display = 'block';
    } else {
        openSidebarButton.style.display = 'none';
    }
}
