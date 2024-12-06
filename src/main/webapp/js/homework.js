document.addEventListener("DOMContentLoaded", () => {
    const hwId = localStorage.getItem('HW_id');

    fetchData('/WEB_QLTT_IELTS/getHWName', hwId, data => {
        if (data.status === "success") {
            document.querySelector('.box-select-name').textContent = data.HW_name;
        } else {
            console.error(data.message);
            alert(data.message);
        }
    });

    fetchData('/WEB_QLTT_IELTS/getAssignmentDetails', hwId, data => {
        const tbody = document.querySelector('#assignmentTable tbody');
        if (data.status === "success") {
            tbody.innerHTML = `
                <tr>
                    <td>${data.HW_name || ""}</td>
                    <td>${data.deadline || ""}</td>
                    <td>${data.checking || ""}</td>
                    <td>${data.hand_on || ""}</td>
                    <td>${data.submit_time || ""}</td>
                </tr>
            `;
        } else {
            console.error(data.message);
            alert(data.message);
        }
    });

    fetchData('/WEB_QLTT_IELTS/getFeedback', hwId, data => {
        const feedbackBox = document.querySelector('.box-feedback');
        if (data.status === "success") {
            feedbackBox.textContent = data.feedbacks;
        } else {
            feedbackBox.textContent = "Không có feedback.";
            console.error(data.message);
        }
    });

    fetchData('/WEB_QLTT_IELTS/getHandOn', hwId, data => {
        const handOnBox = document.querySelector('.box-hand-on');
        if (data.status === "success") {
            handOnBox.textContent = data.hand_on;
        } else {
            handOnBox.textContent = "Không có dữ liệu hand-on.";
            console.error(data.message);
        }
    });
});

function fetchData(url, hwId, callback) {
    const studentId = sessionStorage.getItem("userId");

    fetch(`${url}?hwId=${hwId}&studentId=${studentId}`)
        .then(response => response.json())
        .then(data => callback(data))
        .catch(error => {
            console.error('Fetch error:', error);
            alert("Lỗi kết nối đến server.");
        });
}

document.addEventListener("DOMContentLoaded", () => {
    const hwId = localStorage.getItem('HW_id'); // Lấy HW_id từ localStorage

    if (!hwId) {
        console.error("Không tìm thấy HW_id trong localStorage.");
        document.querySelector('.small-box-name').textContent = "Không có dữ liệu bài tập.";
        return;
    }

    // Gửi yêu cầu để lấy tên giáo viên và hiển thị trong small-box-name
    fetchTeacherName(hwId, (data) => {
        const smallBoxName = document.querySelector('.small-box-name');
        if (data.status === "success") {
            smallBoxName.textContent = data.teacher_name; // Hiển thị tên giáo viên
        } else {
            console.error(data.message);
            smallBoxName.textContent = "Không tìm thấy tên giáo viên.";
        }
    });
});

function fetchTeacherName(hwId, callback) {
    fetch(`/WEB_QLTT_IELTS/getTeacherName?hwId=${encodeURIComponent(hwId)}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json; charset=UTF-8' // Đảm bảo dữ liệu gửi đi được mã hóa UTF-8
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            callback(data); // Truyền dữ liệu tới callback
        })
        .catch(error => {
            console.error('Fetch error:', error);
            alert("Lỗi khi kết nối đến server.");
        });
}





// Show the popup when clicking "Submit"
document.querySelector('.submit-button').addEventListener('click', function() {
    document.getElementById('popupOverlay').style.display = 'flex';
});


