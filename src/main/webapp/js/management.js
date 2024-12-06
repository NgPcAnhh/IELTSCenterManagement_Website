document.addEventListener("DOMContentLoaded", function() {
    loadAllAccounts(); // Tải toàn bộ tài khoản khi trang được tải
});

function loadAllAccounts() {
    fetch(`/WEB_QLTT_IELTS/listAccounts`)
        .then(response => response.json())
        .then(data => {
            displayAccounts(data);
        })
        .catch(error => console.error("Error loading accounts:", error));
}

function searchAccount() {
    const accountId = document.getElementById("accountId").value;
    fetch(`/WEB_QLTT_IELTS/searchAccount?id=${accountId}`)
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                alert("Không tìm thấy tài khoản!");
                loadAllAccounts(); // Tải lại toàn bộ tài khoản nếu không tìm thấy
            } else {
                displayAccounts(data); // Hiển thị kết quả tìm kiếm
            }
        })
        .catch(error => console.error("Error searching account:", error));
}

function displayAccounts(accounts) {
    const tableBody = document.getElementById("accountTableBody");
    tableBody.innerHTML = ""; // Xóa nội dung hiện tại trong bảng

    // Đảm bảo rằng `accounts` luôn là một mảng
    if (!Array.isArray(accounts)) {
        accounts = [accounts]; // Chuyển đổi thành mảng nếu chỉ là một đối tượng
    }

    accounts.forEach(account => {
        const row = document.createElement("tr");

        const idCell = document.createElement("td");
        idCell.textContent = account.id;
        row.appendChild(idCell);

        const loginNameCell = document.createElement("td");
        loginNameCell.textContent = account.loginName || "N/A";
        row.appendChild(loginNameCell);

        const passwordCell = document.createElement("td");
        passwordCell.textContent = account.password || "N/A";
        row.appendChild(passwordCell);

        const actionsCell = document.createElement("td");

        const changePasswordButton = document.createElement("button");
        changePasswordButton.textContent = "Đổi mật khẩu";
        changePasswordButton.onclick = function () {
            changePassword(account.id);
        };

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Xóa";
        deleteButton.onclick = function () {
            deleteAccount(account.id);
        };

        actionsCell.appendChild(changePasswordButton);
        actionsCell.appendChild(deleteButton);
        row.appendChild(actionsCell);

        tableBody.appendChild(row);
    });
}


function changePassword(id) {
    const newPassword = prompt("Nhập mật khẩu mới:");
    if (newPassword) {
        fetch(`/WEB_QLTT_IELTS/changePassword`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `id=${id}&newPassword=${newPassword}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Đổi mật khẩu thành công");
                    loadAllAccounts(); // Cập nhật lại danh sách sau khi đổi mật khẩu
                } else {
                    alert("Đổi mật khẩu thất bại");
                }
            })
            .catch(error => console.error("Error changing password:", error));
    }
}

function deleteAccount(id) {
    if (confirm("Bạn có chắc chắn muốn xóa tài khoản này?")) {
        fetch(`/WEB_QLTT_IELTS/deleteAccount`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: `id=${id}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Xóa tài khoản thành công");
                    loadAllAccounts(); // Cập nhật lại danh sách sau khi xóa
                } else {
                    alert("Xóa tài khoản thất bại");
                }
            })
            .catch(error => console.error("Error deleting account:", error));
    }
}
