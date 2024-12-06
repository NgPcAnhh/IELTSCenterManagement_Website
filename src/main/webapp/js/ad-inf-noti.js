// Tìm kiếm thông báo trong bảng
document.getElementById("searchInput").addEventListener("input", function () {
    const searchValue = this.value.toLowerCase();
    const rows = document.querySelectorAll(".noti-table tbody tr");

    rows.forEach((row) => {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(searchValue) ? "" : "none";
    });
});

// Tải dữ liệu thông báo từ server khi trang được load
document.addEventListener("DOMContentLoaded", () => {
    // Tải danh sách thông báo từ server
    function loadNotifications() {
        fetch("/WEB_QLTT_IELTS/AdminNotification", {
            method: "GET",
            headers: {
                "Content-Type": "application/json; charset=UTF-8"
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Lỗi khi tải thông báo!");
                }
                return response.json();
            })
            .then(data => {
                populateNotificationTable(data);
            })
            .catch(error => {
                console.error("Lỗi:", error);
            });
    }

    // Hiển thị dữ liệu lên bảng
    function populateNotificationTable(notifications) {
        const notiTableBody = document.getElementById("notiTableBody");
        notiTableBody.innerHTML = ""; // Xóa nội dung cũ

        notifications.forEach(noti => {
            const row = document.createElement("tr");

            // Cột ảnh
            const pictureCell = document.createElement("td");
            const img = document.createElement("img");
            img.src = noti.picture || "https://cdn-icons-png.flaticon.com/128/9146/9146433.png";
            img.alt = "Chưa upload ảnh";
            img.style.width = "50px";
            img.style.height = "50px";
            pictureCell.appendChild(img);

            // Cột ID
            const idCell = document.createElement("td");
            idCell.textContent = noti.id;

            // Cột Tên thông báo
            const nameCell = document.createElement("td");
            nameCell.textContent = noti.notiName;
            nameCell.classList.add("clickable");
            nameCell.addEventListener("click", () => openNotificationModal(noti));

            // Cột Ngày đăng
            const dateCell = document.createElement("td");
            dateCell.textContent = new Date(noti.time).toLocaleDateString("vi-VN");

            // Cột Người đăng
            const writterCell = document.createElement("td");
            writterCell.textContent = noti.writter;

            // Thêm các ô vào hàng
            row.appendChild(pictureCell);
            row.appendChild(idCell);
            row.appendChild(nameCell);
            row.appendChild(dateCell);
            row.appendChild(writterCell);

            // Thêm hàng vào bảng
            notiTableBody.appendChild(row);
        });
    }

    // Mở modal khi ấn vào tên thông báo
    function openNotificationModal(notification) {
        document.getElementById("notificationModal").style.display = "block";
        document.getElementById("notiName").value = notification.notiName;
        document.getElementById("notiName").dataset.id = notification.id; // Lưu ID thông báo
        document.getElementById("content").value = notification.content;
        document.getElementById("writterId").value = notification.writter;

        // Hiển thị tên file nếu có
        const fileNameDisplay = document.getElementById("fileNameDisplay");
        if (notification.picture) {
            fileNameDisplay.textContent = notification.picture;
        } else {
            fileNameDisplay.textContent = "";
        }
    }

    // Đóng modal
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }

    // Xem trước thông báo
    function showPreview() {
        const previewModal = document.getElementById("previewModal");
        const title = document.getElementById("notiName").value;
        const content = document.getElementById("content").value;
        const writterId = document.getElementById("writterId").value;

        document.querySelector(".preview-title").textContent = title;
        document.querySelector(".preview-date").textContent = new Date().toLocaleDateString("vi-VN");
        document.querySelector(".preview-writer").textContent = `Người viết: ${writterId}`;
        document.querySelector(".preview-content").textContent = content;

        const imageFile = document.getElementById("picture").files[0];
        if (imageFile) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById("previewImage").src = e.target.result;
                document.getElementById("previewImage").style.display = "block";
            }
            reader.readAsDataURL(imageFile);
        } else {
            document.getElementById("previewImage").style.display = "none";
        }

        previewModal.style.display = "block";
    }

    // Cập nhật thông báo lên server
    function publishNotification() {
        const id = document.getElementById("notiName").dataset.id; // Lấy ID thông báo
        const notiName = document.getElementById("notiName").value;
        const content = document.getElementById("content").value;
        const writter = document.getElementById("writterId").value;

        // Nếu người dùng không chọn ảnh, giữ lại ảnh cũ
        const pictureInput = document.getElementById("picture");
        const picture = pictureInput.files[0]
            ? URL.createObjectURL(pictureInput.files[0])
            : document.getElementById("fileNameDisplay").textContent;

        const updatedNoti = {
            id: id,
            notiName: notiName,
            time: new Date(), // Lấy thời gian hiện tại
            content: content,
            picture: picture,
            writter: writter
        };

        fetch("/WEB_QLTT_IELTS/AdminUpdateNoti", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(updatedNoti)
        })
            .then(response => response.json())
            .then(result => {
                if (result) {
                    alert("Cập nhật thông báo thành công!");
                    document.getElementById("notificationModal").style.display = "none";
                    loadNotifications(); // Tải lại danh sách thông báo
                } else {
                    alert("Cập nhật thông báo thất bại!");
                }
            })
            .catch(error => {
                console.error("Lỗi:", error);
                alert("Đã xảy ra lỗi khi cập nhật thông báo.");
            });
    }

    // Xóa thông báo
    function deleteNotification() {
        const id = document.getElementById("notiName").dataset.id; // Ensure this element has the data-id attribute

        if (confirm("Bạn có chắc chắn muốn xóa thông báo này?")) {
            fetch("/WEB_QLTT_IELTS/AdminDeleteNoti", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify({ id: id })
            })
                .then(response => response.json())
                .then(result => {
                    if (result) {
                        alert("Xóa thông báo thành công!");
                        document.getElementById("notificationModal").style.display = "none";
                        loadNotifications(); // Tải lại danh sách thông báo
                    } else {
                        alert("Xóa thông báo thất bại!");
                    }
                })
                .catch(error => {
                    console.error("Lỗi:", error);
                    alert("Đã xảy ra lỗi khi xóa thông báo.");
                });
        }
    }

    // Đặt sự kiện cho các nút
    document.querySelectorAll(".close-button").forEach(button => {
        button.addEventListener("click", function () {
            const modal = this.closest(".modal-overlay");
            if (modal) {
                modal.style.display = "none";
            }
        });
    });

    document.getElementById("previewButton").addEventListener("click", showPreview);
    document.getElementById("publishButton").addEventListener("click", publishNotification);
    document.getElementById("deleteButton").addEventListener("click", deleteNotification);

    // Gọi hàm tải thông báo khi trang load
    loadNotifications();
});

document.addEventListener("DOMContentLoaded", () => {
    // Lắng nghe sự kiện click vào nút "Tạo thông báo"
    document.getElementById("createButton").addEventListener("click", () => {
        // Làm trống các trường trong modal tạo thông báo
        document.getElementById("createNotiName").value = "";
        document.getElementById("createContent").value = "";
        document.getElementById("createPicture").value = "";
        document.getElementById("createWritterId").value = "";
        document.getElementById("createNotiId").value = "";

        // Hiển thị modal tạo thông báo
        document.getElementById("createNotificationModal").style.display = "block";
    });

    // Lắng nghe sự kiện click vào nút "Xuất bản"
    document.getElementById("publishNotificationButton").addEventListener("click", () => {
        // Lấy giá trị từ các trường nhập
        const notiName = document.getElementById("createNotiName").value;
        const content = document.getElementById("createContent").value;
        const picture = document.getElementById("createPicture").value;
        const writterId = document.getElementById("createWritterId").value;
        const notiId = document.getElementById("createNotiId").value;

        // Kiểm tra dữ liệu nhập vào
        if (!notiName || !content || !writterId || !notiId) {
            alert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Xử lý logic xuất bản (bạn có thể thêm fetch để gửi dữ liệu)
        console.log({
            notiName,
            content,
            picture,
            writterId,
            notiId
        });

        // Hiển thị thông báo xuất bản thành công
        alert("Thông báo đã được xuất bản thành công!");

        // Đóng modal sau khi xuất bản
        closeModal("createNotificationModal");
    });

    // Hàm đóng modal
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }

    // Đóng modal khi nhấn ra ngoài vùng modal-content
    document.getElementById("createNotificationModal").addEventListener("click", (event) => {
        if (event.target.id === "createNotificationModal") {
            closeModal("createNotificationModal");
        }
    });

    // Đóng modal khi nhấn ra ngoài vùng modal-content của preview modal
    document.getElementById("previewModal").addEventListener("click", (event) => {
        if (event.target.id === "previewModal") {
            closeModal("previewModal");
        }
    });
});



document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("publishNotificationButton").addEventListener("click", () => {
        // Lấy giá trị từ form
        const notiName = document.getElementById("createNotiName").value;
        const content = document.getElementById("createContent").value;
        const picture = document.getElementById("createPicture").value;
        const writterId = document.getElementById("createWritterId").value;
        const notiId = document.getElementById("createNotiId").value;

        // Kiểm tra dữ liệu nhập vào
        if (!notiName || !content || !writterId || !notiId) {
            alert("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Lấy thời gian hiện tại (theo định dạng ISO)
        const currentTime = new Date().toISOString();

        // Tạo đối tượng JSON để gửi tới backend
        const newNoti = {
            id: notiId,
            notiName: notiName,
            time: currentTime,// Gửi thời gian hiện tại
            content: content,
            picture: picture || null, // Nếu không có ảnh, gửi null
            writter: writterId
        };

        // Gửi yêu cầu tới servlet
        fetch("/WEB_QLTT_IELTS/AdminInsertNoti", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(newNoti)
        })
            .then(response => response.json())
            .then(result => {
                if (result) {
                    alert("Thông báo đã được xuất bản thành công!");
                    document.getElementById("createNotificationModal").style.display = "none";
                    loadNotifications(); // Hàm tải lại danh sách thông báo (nếu có)
                } else {
                    alert("Xuất bản thông báo thất bại!");
                }
            })
            .catch(error => {
                console.error("Lỗi:", error);
                alert("Đã xảy ra lỗi khi xuất bản thông báo.");
            });
    });

    // Đóng modal khi nhấn ra ngoài
    document.getElementById("createNotificationModal").addEventListener("click", (event) => {
        if (event.target.id === "createNotificationModal") {
            closeModal("createNotificationModal");
        }
    });

    // Hàm đóng modal
    function closeModal(modalId) {
        document.getElementById(modalId).style.display = "none";
    }
});
