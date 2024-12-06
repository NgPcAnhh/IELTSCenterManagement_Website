document.addEventListener("DOMContentLoaded", function () {
    const modalOverlay = document.querySelector(".form-modal-overlay");
    const closeButton = document.querySelector(".form-close-button");
    const cancelButton = document.querySelector(".form-btn-cancel");
    const studentForm = document.getElementById("studentForm");
    const submitButton = document.querySelector(".form-btn-submit");

    // Hiển thị modal
    const createAccountButton = document.querySelector(".form-create-account-button");
    if (createAccountButton) {
        createAccountButton.addEventListener("click", function () {
            modalOverlay.classList.remove("hidden");
        });
    }

    // Đóng modal
    if (closeButton) {
        closeButton.addEventListener("click", function () {
            modalOverlay.classList.add("hidden");
        });
    }

    if (cancelButton) {
        cancelButton.addEventListener("click", function () {
            modalOverlay.classList.add("hidden");
        });
    }

    // Xử lý khi form được submit
    if (studentForm) {
        studentForm.addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn reload trang

            const formData = new FormData(studentForm);

            // Chuyển FormData thành JSON
            const data = {};
            formData.forEach((value, key) => {
                data[key] = value.trim() === "" ? null : value.trim();
            });

            // Debug dữ liệu JSON
            console.log("Dữ liệu JSON gửi đi:", JSON.stringify(data));

            // Kiểm tra nếu không có ID học sinh
            if (!data.studentId) {
                alert("Vui lòng nhập ID học sinh!");
                return;
            }

            // Gửi dữ liệu qua Fetch API
            fetch("/WEB_QLTT_IELTS/ADMINCreateAccountServlet", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8"
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Lỗi khi gửi dữ liệu!");
                    }
                    return response.json();
                })
                .then(result => {
                    alert(result.message || "Dữ liệu đã được lưu thành công!");
                    modalOverlay.classList.add("hidden");
                    studentForm.reset();
                })
                .catch(error => {
                    console.error("Error:", error);
                    alert(error.message || "Đã xảy ra lỗi khi lưu dữ liệu!");
                })
                .finally(() => {
                    if (submitButton) {
                        submitButton.disabled = false;
                        submitButton.textContent = "Lưu";
                    }
                });
        });
    } else {
        console.error("Không tìm thấy form với id 'studentForm'.");
    }
});




document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.getElementById("worker-data-table-body");

    /**
     * Load all workers and populate the table
     */
    function loadWorkers() {
        fetch("/WEB_QLTT_IELTS/ADMINTrackingWorkerServlet?action=getAll")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch workers data");
                }
                return response.json();
            })
            .then(data => {
                tableBody.innerHTML = ""; // Clear existing table rows
                data.forEach(worker => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${worker.id}</td>
                        <td contenteditable="true">${worker.name}</td>
                        <td contenteditable="true">${worker.role}</td>
                        <td contenteditable="true">${worker.phone_number}</td>
                        <td contenteditable="true">${worker.email}</td>
                        <td contenteditable="true">${worker.wage}</td>
                        <td contenteditable="true">${worker.attendance_tracking}</td>
                        <td>
                            <button class="worker-update-button" data-id="${worker.id}">Update</button>
                        </td>
                    `;
                    tableBody.appendChild(row);
                });

                // Add event listeners for update buttons
                addUpdateEventListeners();
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Failed to load worker data.");
            });
    }

    /**
     * Add event listeners to all update buttons
     */
    function addUpdateEventListeners() {
        const updateButtons = document.querySelectorAll(".worker-update-button");
        updateButtons.forEach(button => {
            button.addEventListener("click", function () {
                const id = this.dataset.id;
                const row = this.closest("tr");
                const updatedData = {
                    id: id,
                    name: row.cells[1].innerText.trim(),
                    role: row.cells[2].innerText.trim(),
                    phone_number: row.cells[3].innerText.trim(),
                    email: row.cells[4].innerText.trim(),
                    wage: row.cells[5].innerText.trim(),
                    attendance_tracking: row.cells[6].innerText.trim(),
                };

                if (validateWorkerData(updatedData)) {
                    updateWorker(updatedData);
                }
            });
        });
    }

    /**
     * Validate worker data before sending to the server
     * @param {Object} data - Worker data
     * @returns {boolean} - Whether the data is valid
     */
    function validateWorkerData(data) {
        if (!data.id || !data.name || !data.role || !data.phone_number || !data.email) {
            alert("All fields except Wage and Attendance Tracking are required.");
            return false;
        }

        if (isNaN(data.wage)) {
            alert("Wage must be a valid number.");
            return false;
        }

        return true;
    }

    /**
     * Update worker data
     * @param {Object} data - Worker data to update
     */
    function updateWorker(data) {
        console.log("Dữ liệu gửi đi:", data); // Log dữ liệu JSON

        fetch("/WEB_QLTT_IELTS/ADMINTrackingWorkerServlet?action=update", {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to update worker data");
                }
                return response.json();
            })
            .then(result => {
                alert(result.message || "Worker data updated successfully!");
                loadWorkers(); // Reload workers after successful update
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Failed to update worker data.");
            });
    }

    // Load workers when the page loads
    loadWorkers();
});






document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.querySelector(".attendance-table tbody");

    /**
     * Định dạng thời gian thành `yyyy-MM-dd HH:mm:ss` cho MySQL và Java
     * @param {string} datetime - Chuỗi thời gian từ input (datetime-local)
     * @returns {string} Chuỗi thời gian định dạng MySQL
     */
    function formatToMySQLDatetime(datetime) {
        if (!datetime) return null;
        const date = new Date(datetime);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0");
        const day = String(date.getDate()).padStart(2, "0");
        const hours = String(date.getHours()).padStart(2, "0");
        const minutes = String(date.getMinutes()).padStart(2, "0");
        const seconds = String(date.getSeconds()).padStart(2, "0");
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    /**
     * Tìm kiếm dữ liệu dựa trên ID hoặc khoảng thời gian
     */
    function searchTracking() {
        const id = document.getElementById("searchById").value.trim();
        const startTimeRaw = document.getElementById("startTime").value.trim();
        const endTimeRaw = document.getElementById("endTime").value.trim();

        // Định dạng lại startTime và endTime
        const startTime = formatToMySQLDatetime(startTimeRaw);
        const endTime = formatToMySQLDatetime(endTimeRaw);

        // Kiểm tra tính hợp lệ của input
        if (!id && (!startTime || !endTime)) {
            alert("Vui lòng nhập ID hoặc cả khoảng thời gian (Start Time và End Time).");
            return;
        }

        if ((startTime && !endTime) || (!startTime && endTime)) {
            alert("Vui lòng nhập cả Start Time và End Time.");
            return;
        }

        // Xây dựng query string chỉ với các tham số hợp lệ
        const queryParams = new URLSearchParams();
        if (id) queryParams.append("id", id);
        if (startTime) queryParams.append("startTime", startTime);
        if (endTime) queryParams.append("endTime", endTime);

        fetch(`/WEB_QLTT_IELTS/ADMINLoginTrackingServlet?action=search&${queryParams.toString()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch search results");
                }
                return response.json();
            })
            .then(data => {
                renderTableRows(data);
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Không tìm thấy dữ liệu phù hợp hoặc đã xảy ra lỗi.");
            });
    }

    /**
     * Hiển thị dữ liệu lên bảng
     * @param {Array} data - Mảng dữ liệu từ server
     */
    function renderTableRows(data) {
        tableBody.innerHTML = ""; // Xóa dữ liệu cũ

        if (!data || data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="4" style="text-align: center;">No records found</td></tr>`;
            return;
        }

        data.forEach(item => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${item.login_name}</td>
                <td>${item.time_in ? item.time_in : "N/A"}</td>
                <td>${item.time_out ? item.time_out : "N/A"}</td>
                <td>${item.id}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    // Gắn sự kiện click cho nút Search
    document.querySelector("button[onclick='searchById()']").addEventListener("click", searchTracking);

    // Tải dữ liệu mặc định khi trang được mở
    function loadDefaultTracking() {
        fetch("/WEB_QLTT_IELTS/ADMINLoginTrackingServlet?action=default")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch default login tracking data");
                }
                return response.json();
            })
            .then(data => {
                renderTableRows(data);
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Không thể tải dữ liệu mặc định.");
            });
    }

    loadDefaultTracking();
});





document.addEventListener("DOMContentLoaded", function () {
    // Gọi API để lấy danh sách học sinh không đăng nhập
    fetch("/WEB_QLTT_IELTS/ADMINNotLogin")
        .then((response) => {
            if (!response.ok) {
                throw new Error(`Failed to fetch not login data: ${response.status}`);
            }
            return response.json();
        })
        .then((data) => {
            if (data.error) {
                console.error(data.error);
                alert("Không thể tải danh sách học sinh. Vui lòng thử lại sau!");
                return;
            }

            // Hiển thị dữ liệu lên bảng
            populateNLTable(data);
        })
        .catch((error) => {
            console.error("Error fetching not login data:", error.message);
            alert("Đã xảy ra lỗi khi tải dữ liệu. Vui lòng thử lại!");
        });

    // Hàm hiển thị dữ liệu lên bảng
    function populateNLTable(data) {
        const tableBody = document.getElementById("NLtableBody");
        tableBody.innerHTML = ""; // Xóa nội dung cũ trước khi thêm mới

        // Kiểm tra nếu không có dữ liệu
        if (!data || data.length === 0) {
            const noDataRow = document.createElement("tr");
            noDataRow.innerHTML = `
                <td colspan="7" style="text-align: center;">Không có dữ liệu</td>
            `;
            tableBody.appendChild(noDataRow);
            return;
        }

        // Duyệt qua danh sách và thêm các hàng vào bảng
        data.forEach((item) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${item.studentID || "N/A"}</td> <!-- Thêm ID -->
                <td>${item.studentName || "N/A"}</td>
                <td>${item.studentPhone || "N/A"}</td>
                <td>${item.parentName || "N/A"}</td>
                <td>${item.parentPhone || "N/A"}</td>
                <td>${formatDate(item.latestLogin) || "N/A"}</td>
                <td>${item.monthsNotLoggedIn || "N/A"}</td>
            `;

            tableBody.appendChild(row);
        });
    }

    // Hàm định dạng ngày thành kiểu dd/MM/yyyy
    function formatDate(dateString) {
        if (!dateString) return null;
        const date = new Date(dateString);
        return `${String(date.getDate()).padStart(2, "0")}/${String(date.getMonth() + 1).padStart(2, "0")}/${date.getFullYear()}`;
    }
});


