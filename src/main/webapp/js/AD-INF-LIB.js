// DOM Elements
const categorySelect = document.getElementById('categorySelect');
const categoryTags = document.getElementById('categoryTags');
const selectedCategories = new Set();

// Show the layout form
function showAddBookForm() {
    const layout = document.getElementById('addBookLayout');
    layout.classList.remove('hidden'); // Remove hidden class to display the layout
}

// Hide the layout form
function hideAddBookForm() {
    const layout = document.getElementById('addBookLayout');
    layout.classList.add('hidden'); // Add hidden class to hide the layout
}

// Save the book to the library
function saveBook() {
    // Collect data from the form
    const id = document.querySelector('input[name="id"]').value.trim();
    const title = document.querySelector('input[name="title"]').value.trim();
    const author = document.querySelector('input[name="author"]').value.trim();
    const filePath = document.querySelector('input[name="file_path"]').value.trim(); // Collect data from the file_path field
    const description = document.querySelector('textarea[name="description"]').value.trim();

    const categories = Array.from(selectedCategories).join(', ');
    const uploadTime = new Date().toISOString().slice(0, 19).replace('T', ' '); // Get current time in YYYY-MM-DD HH:mm:ss format

    // Validate required fields
    if (!id || !title || !author || !categories || !filePath) {
        alert("Please fill all required fields!");
        return;
    }

    // Prepare data to send to the server
    const requestData = {
        id,
        title,
        author,
        file_path: filePath, // Include file_path in the data
        description,
        categories,
        upload_time: uploadTime
    };

    // Send data to the server
    fetch("/WEB_QLTT_IELTS/ADMINLibraryUpdate", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(requestData) // Send requestData as JSON
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to save book!");
            }
            alert("Book saved successfully!");
            // Hide the form after successful save
            hideAddBookForm();
            // Reset the form
            resetForm();
        })
        .catch(error => {
            console.error(error);
            alert("Failed to save book! Error: " + error.message);
        });
}

// Reset the form
function resetForm() {
    document.querySelector('input[name="id"]').value = '';
    document.querySelector('input[name="title"]').value = '';
    document.querySelector('input[name="author"]').value = '';
    document.querySelector('textarea[name="description"]').value = '';
    document.querySelector('input[name="file_path"]').value = ''; // Reset the file_path field
    selectedCategories.clear();
    categoryTags.innerHTML = ''; // Clear category tags
    categorySelect.value = ''; // Reset category dropdown
}

// Handle category selection logic
categorySelect.addEventListener('change', (e) => {
    const selectedValue = e.target.value;
    const selectedText = e.target.options[e.target.selectedIndex].text;

    if (selectedValue && !selectedCategories.has(selectedValue)) {
        selectedCategories.add(selectedValue);

        const tag = document.createElement('div');
        tag.className = 'category-tag';
        tag.innerHTML = `${selectedText} <span class="category-tag-remove">×</span>`;

        tag.querySelector('.category-tag-remove').addEventListener('click', () => {
            selectedCategories.delete(selectedValue);
            categoryTags.removeChild(tag);
        });

        categoryTags.appendChild(tag);
        e.target.value = '';
    }
});

// Close layout when clicking outside the form
document.addEventListener('click', (event) => {
    const layout = document.getElementById('addBookLayout');
    const formContainer = document.querySelector('#addBookLayout .form-container');

    if (!layout.classList.contains('hidden') && !formContainer.contains(event.target) && !event.target.closest('.add-doc-btn')) {
        hideAddBookForm();
    }
});








document.addEventListener("DOMContentLoaded", () => {
    const validCategories = ["Reading", "Listening", "Speaking", "Writing"];
    const categories = [...validCategories, "Other"];
    const categoryContainers = {};

    // Liên kết danh mục với container tương ứng
    categories.forEach(category => {
        categoryContainers[category] = document.querySelector(`ul.folder-list[data-category="${category.toLowerCase()}"]`);
    });

    let allFiles = []; // Lưu trữ tất cả các tài liệu để hỗ trợ tìm kiếm

    // Fetch dữ liệu từ servlet
    fetch("/WEB_QLTT_IELTS/ADMINLibraryArrange")
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Data fetched from backend:", data); // In dữ liệu JSON trả về

            // Lặp qua từng tài liệu
            data.forEach(item => {
                const { id, title, category, author, uploadDate, description, filePath } = item;

                // Kiểm tra và phân loại danh mục
                const categoryList = category.split(", ");
                categoryList.forEach(cat => {
                    const targetCategory = validCategories.includes(cat) ? cat : "Other";

                    const categoryContainer = categoryContainers[targetCategory];
                    if (categoryContainer) {
                        // Tạo phần tử HTML cho tài liệu
                        const fileDiv = document.createElement("div");
                        fileDiv.classList.add("file");
                        fileDiv.setAttribute("data-id", id);
                        fileDiv.setAttribute("data-title", title);
                        fileDiv.setAttribute("data-author", author || "Unknown");
                        fileDiv.setAttribute("data-date", uploadDate || "Unknown");
                        fileDiv.setAttribute("data-description", description || "No description provided.");
                        fileDiv.setAttribute("data-path", filePath || "Not available"); // Sử dụng filePath thay vì path

                        fileDiv.innerHTML = `
                            <div class="work-5"></div>
                            <div class="work-4"></div>
                            <div class="work-3"></div>
                            <div class="work-2"></div>
                            <div class="work-1"></div>
                            <div class="folder-id" style="text-align: center;">${id}</div>
                            <p style="text-align: center; margin-top: 10px;">${title}</p>
                        `;

                        // Gắn sự kiện click cho file
                        fileDiv.addEventListener("click", () => {
                            const fileData = {
                                id: fileDiv.getAttribute("data-id"),
                                title: fileDiv.getAttribute("data-title"),
                                author: fileDiv.getAttribute("data-author"),
                                uploadDate: fileDiv.getAttribute("data-date"),
                                description: fileDiv.getAttribute("data-description"),
                                filePath: fileDiv.getAttribute("data-path"), // Sử dụng filePath
                            };
                            showFileDetailsModal(fileData);
                        });

                        // Lưu tài liệu vào danh sách toàn cục
                        allFiles.push(fileDiv);

                        // Thêm tài liệu vào danh mục tương ứng
                        categoryContainer.appendChild(fileDiv);
                    }
                });
            });
        })
        .catch(error => {
            console.error("Error loading library data:", error);
        });

    // Xử lý tìm kiếm
    const searchInput = document.querySelector(".search-input");
    searchInput.addEventListener("input", (event) => {
        const searchTerm = event.target.value.toLowerCase();

        // Lặp qua tất cả các file và kiểm tra điều kiện tìm kiếm
        allFiles.forEach(file => {
            const fileId = file.getAttribute("data-id").toLowerCase();
            const fileTitle = file.getAttribute("data-title").toLowerCase();

            if (fileId.includes(searchTerm) || fileTitle.includes(searchTerm)) {
                file.style.display = ""; // Hiển thị tài liệu phù hợp
            } else {
                file.style.display = "none"; // Ẩn tài liệu không phù hợp
            }
        });
    });
});

// Hàm hiển thị modal với thông tin chi tiết tài liệu
function showFileDetailsModal(fileData) {
    const modal = document.getElementById("file-details-modal");

    // Gán dữ liệu vào modal
    document.getElementById("file-title").textContent = fileData.title;
    document.getElementById("file-author").textContent = fileData.author;
    document.getElementById("file-date").textContent = fileData.uploadDate;
    document.getElementById("file-id").textContent = fileData.id;
    document.getElementById("file-description").textContent = fileData.description;
    document.getElementById("file-path").textContent = fileData.filePath; // Sử dụng filePath

    // Hiển thị modal
    modal.style.display = "flex";
}

// Hàm đóng modal
function closeFileDetailsModal() {
    const modal = document.getElementById("file-details-modal");
    modal.style.display = "none";
}

// Đóng modal khi nhấn vào nút đóng hoặc bên ngoài modal
document.addEventListener("click", (event) => {
    const modal = document.getElementById("file-details-modal");
    if (event.target.classList.contains("close-btn") || event.target.id === "file-details-modal") {
        closeFileDetailsModal();
    }
});


function deleteBook(id) {
    if (!confirm("Are you sure you want to delete this document?")) {
        return;
    }

    fetch(`/WEB_QLTT_IELTS/ADMINLibDeleteServlet?id=${id}`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse the response as JSON
        })
        .then(data => {
            alert(data.message);

            // Check if the deletion was successful
            if (data.message === "Document deleted successfully") {
                // Remove the deleted document from the UI
                const deletedFileElement = document.querySelector(`.file[data-id="${id}"]`);
                if (deletedFileElement) {
                    deletedFileElement.remove();
                }

                // Close the modal after deletion
                closeFileDetailsModal();
            }
        })
        .catch(error => {
            console.error("Deletion error:", error);
            alert("Failed to delete document. Please try again.");
        });
}

// Update the delete button in the modal dynamically
document.addEventListener("DOMContentLoaded", () => {
    const deleteDocBtn = document.querySelector(".delete-doc-btn");
    deleteDocBtn.addEventListener("click", () => {
        const documentId = document.getElementById("file-id").textContent.trim();
        deleteBook(documentId);
    });
});

