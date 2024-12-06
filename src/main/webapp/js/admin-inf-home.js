// Toggle sidebar open/close
function chinhSideBar() {
    const sidebar = document.querySelector('.sidebar');
    const openSidebarButton = document.getElementById('openSidebar');
    const closeSidebarButton = document.getElementById('closeSidebar');

    if (sidebar && openSidebarButton && closeSidebarButton) {
        sidebar.classList.toggle('closed');
        openSidebarButton.style.display = sidebar.classList.contains('closed') ? 'block' : 'none';
        closeSidebarButton.style.display = sidebar.classList.contains('closed') ? 'none' : 'block';
    }
}

// Show and handle logout confirmation
function showLogoutConfirmation() {
    const logoutDialog = document.getElementById('logoutDialog');
    if (logoutDialog) logoutDialog.style.display = 'block';
}

function handleLogout(confirmed) {
    const logoutDialog = document.getElementById('logoutDialog');
    if (confirmed) {
        fetch('/WEB_QLTT_IELTS/logout', { method: 'POST', credentials: 'include' })
            .then(response => response.ok ? window.location.href = 'login4stu.html' : alert('Logout failed, please try again.'))
            .catch(error => console.error('Error:', error));
    } else if (logoutDialog) {
        logoutDialog.style.display = 'none';
    }
}

// Display random profile icon image
document.addEventListener("DOMContentLoaded", () => {
    const images = document.querySelectorAll('.profile-icon img');
    if (images.length > 0) images[Math.floor(Math.random() * images.length)].style.display = 'block';
});

// Show and close notification modal
function showNotificationDetails(title, content, imageUrl) {
    const modalTitle = document.getElementById('modalTitle');
    const modalContent = document.getElementById('modalContent');
    const modalImage = document.getElementById('modalImage');
    const notificationModal = document.getElementById('notificationModal');

    if (modalTitle && modalContent && modalImage && notificationModal) {
        modalTitle.innerText = title;
        modalContent.innerText = content;
        modalImage.src = imageUrl;
        notificationModal.style.display = 'block';
    }
}

function closeModal() {
    const notificationModal = document.getElementById('notificationModal');
    if (notificationModal) notificationModal.style.display = 'none';
}

// Close modal on outside click or ESC key
window.onclick = event => {
    const modal = document.getElementById('notificationModal');
    if (event.target == modal) closeModal();
};

document.onkeydown = event => {
    if ((event.key === "Escape" || event.key === "Esc") && document.getElementById('notificationModal')) closeModal();
};
