// Toggle sidebar open/close
function chinhSideBar() {
    const sidebar = document.querySelector('.sidebar');
    const openSidebarButton = document.getElementById('openSidebar');
    const closeSidebarButton = document.getElementById('closeSidebar');

    // Toggle the closed class on the sidebar
    sidebar.classList.toggle('closed');

    if (sidebar.classList.contains('closed')) {
        openSidebarButton.style.display = 'block'; // Show open button when sidebar is closed
        closeSidebarButton.style.display = 'none'; // Hide close button
    } else {
        openSidebarButton.style.display = 'none'; // Hide open button
        closeSidebarButton.style.display = 'block'; // Show close button
    }
}

// Show logout confirmation dialog
function showLogoutConfirmation() {
    document.getElementById('logoutDialog').style.display = 'block';
}

// Handle logout confirmation response
function handleLogout(confirmed) {
    if (confirmed) {
        // If user clicks Yes, send request to server to logout and kill session
        fetch('/WEB_QLTT_IELTS/logout', {
            method: 'POST',
            credentials: 'include'
        })
            .then(response => {
                if (response.ok) {
                    // Redirect to login page after logout is processed
                    window.location.href = 'login4stu.html';
                } else {
                    alert('Logout failed, please try again.');
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        // If user clicks No, hide the dialog
        document.getElementById('logoutDialog').style.display = 'none';
    }
}

// cho phần homework.css
document.addEventListener("DOMContentLoaded", function() {
    const images = document.querySelectorAll('.profile-icon img');
    const randomIndex = Math.floor(Math.random() * images.length);
    images[randomIndex].style.display = 'block';
});

// cho phần noti

function showNotificationDetails(title, content, imageUrl) {
    document.getElementById('modalTitle').innerText = title;
    document.getElementById('modalContent').innerText = content;
    document.getElementById('modalImage').src = imageUrl;
    document.getElementById('notificationModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('notificationModal').style.display = 'none';
}

// Đóng modal khi click vào bên ngoài modal-content
window.onclick = function(event) {
    var modal = document.getElementById('notificationModal');
    if (event.target == modal) {
        closeModal();
    }
}

// Đóng modal khi bấm phím ESC
document.onkeydown = function(event) {
    event = event || window.event;
    if (event.key === "Escape" || event.key === "Esc") {
        closeModal();
    }
}