document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const errorMessage = document.getElementById('error-message');

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault(); // Ngăn form gửi yêu cầu mặc định

        const loginName = document.getElementById('loginName').value.trim();
        const password = document.getElementById('password').value.trim();

        // Kiểm tra nếu có trường bị bỏ trống
        if (!loginName || !password) {
            errorMessage.textContent = 'Vui lòng điền đầy đủ thông tin!';
            return;
        }

        // Thực hiện yêu cầu POST đến servlet
        fetch('/WEB_QLTT_IELTS/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({ loginName: loginName, password: password })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    // Kiểm tra 3 ký tự đầu của ID để quyết định trang chuyển hướng
                    const userId = data.userId;
                    if (userId.startsWith('STU')) {
                        window.location.href = 'STU-INF.html';
                    } else {
                        window.location.href = 'ADMIN-INF.html';
                    }
                } else {
                    // Hiển thị thông báo lỗi nếu đăng nhập thất bại
                    errorMessage.textContent = data.message || 'Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin.';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                errorMessage.textContent = 'Đã xảy ra lỗi trong quá trình đăng nhập. Vui lòng thử lại sau.';
            });
    });
});
