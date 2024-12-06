document.addEventListener("DOMContentLoaded", () => {
    const apiUrl = "/WEB_QLTT_IELTS/STULibrary"; // URL của servlet
    const bookCardOverlay = document.querySelector(".book-card-overlay"); // Overlay chứa layout chi tiết
    const bookCardClose = document.querySelector(".book-card-close"); // Nút đóng layout

    // Hàm hiển thị thông tin chi tiết
    function showBookDetails(data) {
        if (!bookCardOverlay) {
            console.error("Book card layout not found.");
            return;
        }

        // Lấy các phần tử trong layout
        const bookTitle = bookCardOverlay.querySelector(".book-title");
        const bookId = bookCardOverlay.querySelector(".book-id");
        const bookAuthor = bookCardOverlay.querySelector(".book-meta .meta-item:nth-child(1) .meta-value"); // Sửa selector cho Author
        const uploadDate = bookCardOverlay.querySelector(".book-meta .meta-item:nth-child(2) .meta-value"); // Sửa selector cho Upload Date
        const description = bookCardOverlay.querySelector(".book-description p");
        const categoriesContainer = bookCardOverlay.querySelector(".categories");
        const fileLink = bookCardOverlay.querySelector(".file-link");

        // Cập nhật dữ liệu vào layout
        if (bookTitle) bookTitle.textContent = data.title || "Unknown Title";
        if (bookId) bookId.textContent = data.id || "Unknown ID";
        if (bookAuthor) bookAuthor.textContent = data.author || "Unknown Author"; // Hiển thị Author đúng
        if (uploadDate) uploadDate.textContent = new Date(data.uploadDate).toLocaleString("vi-VN") || "Unknown Date"; // Hiển thị Upload Date đúng
        if (description) description.textContent = data.description || "No description available.";

        // Xử lý danh mục
        if (categoriesContainer) {
            categoriesContainer.innerHTML = ""; // Xóa danh mục cũ
            const categories = data.category ? data.category.split(",").map(c => c.trim()) : ["Other"];
            categories.forEach(category => {
                const categoryTag = document.createElement("span");
                categoryTag.className = "category-tag";
                categoryTag.textContent = category;
                categoriesContainer.appendChild(categoryTag);
            });
        }

        // Đường dẫn tài nguyên
        if (fileLink) {
            fileLink.href = data.filePath || "#";
            fileLink.textContent = "Download";
        }

        // Hiển thị layout chi tiết
        bookCardOverlay.style.display = "flex";
    }

    // Hàm đóng layout
    function closeBookDetails() {
        bookCardOverlay.style.display = "none";
    }

    // Tạo thẻ card
    function createCard(data) {
        const card = document.createElement("div");
        card.className = "card";

        const topCard = document.createElement("div");
        topCard.className = "top-card";
        topCard.innerHTML = `<strong>${data.title || "No Title"}</strong><br>${data.author || "Unknown Author"}`;

        const bottomCard = document.createElement("div");
        bottomCard.className = "bottom-card";

        const cardContent = document.createElement("div");
        cardContent.className = "card-content";

        const cardTitle = document.createElement("span");
        cardTitle.className = "card-title";
        cardTitle.textContent = data.id || "Unknown ID";

        const cardTxt = document.createElement("p");
        cardTxt.className = "card-txt";
        cardTxt.textContent = new Date(data.uploadDate).toLocaleDateString("vi-VN") || "Unknown Date";

        const cardBtn = document.createElement("button");
        cardBtn.className = "card-btn";
        cardBtn.textContent = "Read More";
        cardBtn.addEventListener("click", () => {
            showBookDetails(data);
        });

        cardContent.appendChild(cardTitle);
        cardContent.appendChild(cardTxt);
        cardContent.appendChild(cardBtn);

        bottomCard.appendChild(cardContent);
        card.appendChild(topCard);
        card.appendChild(bottomCard);

        return card;
    }

    // Đóng layout khi nhấn vùng ngoài
    bookCardOverlay.addEventListener("click", (event) => {
        if (event.target === bookCardOverlay) {
            closeBookDetails();
        }
    });

    // Đóng layout khi nhấn nút đóng
    bookCardClose.addEventListener("click", closeBookDetails);

    // Fetch dữ liệu từ servlet
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
            return response.json();
        })
        .then(data => {
            const sections = {
                Reading: document.querySelectorAll(".section-container .cards-container")[0],
                Listening: document.querySelectorAll(".section-container .cards-container")[1],
                Writing: document.querySelectorAll(".section-container .cards-container")[2],
                Speaking: document.querySelectorAll(".section-container .cards-container")[3],
                Other: document.querySelectorAll(".section-container .cards-container")[4]
            };

            data.forEach((item) => {
                const categories = item.category ? item.category.split(",").map(c => c.trim()) : ["Other"];
                categories.forEach((category) => {
                    if (sections[category]) {
                        sections[category].appendChild(createCard(item));
                    } else {
                        sections["Other"].appendChild(createCard(item));
                    }
                });
            });
        })
        .catch(error => {
            console.error("Error fetching library data:", error);
        });
});






function performSearch() {
    // Lấy giá trị tìm kiếm từ thanh input
    const searchQuery = document.getElementById("searchInput").value.trim().toLowerCase();

    // Lấy tất cả các thẻ card trong giao diện
    const cards = document.querySelectorAll(".cards-container .card");

    // Lặp qua từng thẻ card để kiểm tra
    cards.forEach((card) => {
        // Lấy dữ liệu từ các thành phần trong card
        const title = card.querySelector(".top-card strong")?.textContent.toLowerCase() || "";
        const author = card.querySelector(".top-card br + text")?.textContent.toLowerCase() || ""; // Nếu cần lấy author
        const id = card.querySelector(".card-title")?.textContent.toLowerCase() || "";

        // Kiểm tra nếu bất kỳ dữ liệu nào khớp với từ khóa tìm kiếm
        if (title.includes(searchQuery) || author.includes(searchQuery) || id.includes(searchQuery)) {
            card.style.display = "block"; // Hiển thị nếu khớp
        } else {
            card.style.display = "none"; // Ẩn nếu không khớp
        }
    });
}
