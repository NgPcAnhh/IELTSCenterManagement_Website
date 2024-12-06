document.addEventListener("DOMContentLoaded", () => {
    const cardsContainer = document.getElementById("cardsContainer");
    const notificationContent = document.getElementById("notificationContent");
    const searchInput = document.getElementById("searchInput");

    let notificationsData = []; // Lưu trữ dữ liệu thông báo để tìm kiếm

    // Lấy dữ liệu từ backend
    const fetchNotifications = async () => {
        try {
            const response = await fetch('/WEB_QLTT_IELTS/STUNoti'); // API endpoint từ servlet
            if (!response.ok) throw new Error("Failed to fetch notifications");
            return await response.json();
        } catch (error) {
            console.error("Error fetching notifications:", error);
            return [];
        }
    };

    // Xử lý xuống dòng cho nội dung
    const formatContent = (content) => {
        if (!content) return "";
        return content.replace(/\n/g, "<br>"); // Chuyển đổi ký tự xuống dòng thành thẻ <br>
    };

    // Render danh sách thẻ thông báo
    const renderCards = (data) => {
        cardsContainer.innerHTML = ""; // Xóa nội dung cũ
        data.forEach(item => {
            // Tạo từng thẻ
            const card = document.createElement("div");
            card.className = "card";
            card.innerHTML = `
                <img src="${item.picture}" alt="Notification Image">
                <div class="card-info">
                    <div class="card-title">${item.notiName}</div>
                    <div class="card-meta">${item.writerName} - ${new Date(item.time).toLocaleDateString("vi-VN")}</div>
                </div>
            `;
            card.addEventListener("click", (e) => {
                e.stopPropagation();
                showNotificationContent(item); // Hiển thị chi tiết khi click
            });
            cardsContainer.appendChild(card);
        });
    };

    // Hiển thị nội dung chi tiết của thông báo
    const showNotificationContent = (noti) => {
        notificationContent.innerHTML = `
            <h2>${noti.notiName}</h2>
            <p>${formatContent(noti.content)}</p>
            <img src="${noti.picture}" alt="Notification Image">
        `;
        notificationContent.classList.remove("hidden");
    };

    // Đóng nội dung chi tiết
    const closeNotificationContent = () => {
        notificationContent.classList.add("hidden");
    };

    // Lọc thông báo theo từ khóa
    const filterNotifications = (keyword) => {
        const filteredData = notificationsData.filter(item =>
            item.notiName.toLowerCase().includes(keyword.toLowerCase()) ||
            item.writerName.toLowerCase().includes(keyword.toLowerCase()) ||
            new Date(item.time).toLocaleDateString("vi-VN").includes(keyword)
        );
        renderCards(filteredData); // Hiển thị kết quả lọc
    };

    // Gắn sự kiện tìm kiếm
    searchInput.addEventListener("input", (e) => {
        const keyword = e.target.value;
        filterNotifications(keyword);
    });

    // Fetch dữ liệu từ backend và render thẻ thông báo
    fetchNotifications().then(data => {
        notificationsData = data; // Lưu dữ liệu vào biến toàn cục
        renderCards(data); // Hiển thị tất cả thông báo
    });

    // Đóng layout chi tiết nếu click ra ngoài
    document.addEventListener("click", (e) => {
        if (!notificationContent.contains(e.target)) {
            closeNotificationContent();
        }
    });

    // Ngăn việc đóng layout khi click bên trong nội dung chi tiết
    notificationContent.addEventListener("click", (e) => e.stopPropagation());
});
