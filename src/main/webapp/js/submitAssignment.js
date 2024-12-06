document.addEventListener("DOMContentLoaded", () => {
    document.querySelector('.submit-button').addEventListener('click', function() {
        document.getElementById('popupOverlay').style.display = 'flex';
    });

    document.querySelector('.popup-submit').addEventListener('click', function() {
        submitAssignment();
    });
});

function submitAssignment() {
    const hwId = localStorage.getItem('HW_id');
    const linkInput = document.getElementById('linkInput').value;
    const fileInput = document.getElementById('fileInput').files[0];
    const formData = new FormData();

    formData.append('HW_id', hwId);
    formData.append('linkInput', linkInput);

    if (fileInput) {
        formData.append('fileInput', fileInput);
    }

    fetch('/WEB_QLTT_IELTS/submitAssignment', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                alert(data.message);
                closePopup();
            } else {
                alert(data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Lỗi khi gửi yêu cầu.");
        });
}

function closePopup() {
    document.getElementById('popupOverlay').style.display = 'none';
}
