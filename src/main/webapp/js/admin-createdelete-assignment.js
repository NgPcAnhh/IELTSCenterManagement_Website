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
                    // Optionally, refresh the assignment list here
                } else {
                    alert("Failed to delete assignment. Please try again.");
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        alert("Please enter an Assignment ID.");
    }
});


// Mở layout Create Assignment
document.querySelector('.create-button').addEventListener('click', () => {
    const assignmentModal = document.getElementById("assignmentModal");
    assignmentModal.classList.add("active");
    assignmentModal.style.display = "block"; // Hiển thị layout create assignment
});


window.addEventListener("click", (e) => {
    console.log("Window clicked", e.target); // Debugging line
    const assignmentModal = document.getElementById("assignmentModal");
    if (assignmentModal.classList.contains("active") && !assignmentModal.contains(e.target) && !e.target.closest('.create-button')) {
        assignmentModal.classList.remove("active");
        assignmentModal.style.display = "none";
    }
});


// Tạo mới Assignment và xử lý submit
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
            } else {
                alert("Failed to create assignment. Please try again.");
            }
        })
        .catch(error => console.error('Error:', error));
});