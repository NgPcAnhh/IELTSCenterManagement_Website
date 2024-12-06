// Hàm tìm kiếm sinh viên
function searchStudent() {
    const searchInput = document.getElementById('searchInput').value.trim();
    if (!searchInput) {
        alert('Vui lòng nhập ID sinh viên để tìm kiếm');
        return;
    }

    fetch(`admin-student-search?id=${encodeURIComponent(searchInput)}`)
        .then(response => response.json())
        .then(data => {
            if (Object.keys(data).length === 0) {
                alert('Không tìm thấy sinh viên với ID này');
                clearTable();
                return;
            }
            displayStudentData(data);
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Đã xảy ra lỗi khi tìm kiếm');
        });
}

// Hàm hiển thị dữ liệu sinh viên
function displayStudentData(student) {
    const tbody = document.querySelector('tbody');
    const row = tbody.querySelector('tr');
    const date_birth = student.date_birth ? new Date(student.date_birth).toLocaleDateString() : '';

    const cells = row.getElementsByTagName('td');
    cells[0].textContent = student.student_name || '';
    cells[1].textContent = date_birth;
    cells[2].textContent = student.id || '';
    cells[3].textContent = student.phone_number || '';
    cells[4].textContent = student.gmail || '';
    cells[5].textContent = student.parent_name || '';
    cells[6].textContent = student.parent_number || '';
    cells[7].textContent = student.ma_mon || '';
    cells[8].textContent = student.ma_mon_1 || '';
    cells[9].textContent = student.ma_mon_2 || '';
    cells[10].textContent = student.ss1 || '';
}

// Hàm xóa dữ liệu trong bảng
function clearTable() {
    const tbody = document.querySelector('tbody');
    const row = tbody.querySelector('tr');
    const cells = row.getElementsByTagName('td');

    for (let cell of cells) {
        cell.textContent = '';
    }
}

// Thêm sự kiện cho phím Enter trong ô tìm kiếm
document.getElementById('searchInput').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        searchStudent();
    }
});





function confirmChange() {
    const tableRow = document.querySelector("table tbody tr");

    // Collect data from the row
    const studentData = {
        student_name: tableRow.cells[0].innerText.trim(),
        date_birth: convertToMySQLDateTime(tableRow.cells[1].innerText.trim()), // Convert date
        id: tableRow.cells[2].innerText.trim(),
        phone_number: tableRow.cells[3].innerText.trim(),
        gmail: tableRow.cells[4].innerText.trim(),
        parent_name: tableRow.cells[5].innerText.trim(),
        parent_number: tableRow.cells[6].innerText.trim(),
        ma_mon: tableRow.cells[7].innerText.trim(),
        ma_mon_1: tableRow.cells[8].innerText.trim(),
        ma_mon_2: tableRow.cells[9].innerText.trim(),
        ss1: tableRow.cells[10].innerText.trim()
    };

    console.log("Data being sent to servlet:", studentData);

    // Validate the date before sending
    if (!studentData.date_birth) {
        alert("Invalid date format! Please enter a valid date (e.g., MM/DD/YYYY).");
        return;
    }

    // Send JSON to servlet
    fetch("/WEB_QLTT_IELTS/ADMINStudentUpdateServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(studentData)
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.message || "Unknown error");
                });
            }
            return response.json();
        })
        .then(data => {
            alert(data.message);
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Failed to update: " + error.message);
        });
}

// Helper function to convert date to MySQL DATETIME format (yyyy-MM-dd HH:mm:ss)
function convertToMySQLDateTime(date) {
    try {
        const parts = date.split("/");
        if (parts.length === 3) {
            // Parse date in MM/DD/YYYY format
            const month = parts[0].padStart(2, "0");
            const day = parts[1].padStart(2, "0");
            const year = parts[2];

            // Create a new Date object
            const dateObj = new Date(`${year}-${month}-${day}T00:00:00`);

            // Check if the date is valid
            if (isNaN(dateObj.getTime())) {
                console.error("Invalid date:", date);
                return null;
            }

            // Format to MySQL DATETIME
            const formattedDate = dateObj.toISOString().slice(0, 19).replace("T", " ");
            return formattedDate;
        }
    } catch (error) {
        console.error("Error converting date:", error);
        return null;
    }
    return null; // Return null if the date format is incorrect
}



// thống kê hóa đơn.
document.addEventListener("DOMContentLoaded", function () {
    // Fetch bills from backend
    fetch("/WEB_QLTT_IELTS/ADMINBillTrackingServlet")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch bills.");
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById("unique-data-table-body");
            tableBody.innerHTML = ""; // Clear previous rows

            // Populate table rows
            data.forEach(bill => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${bill.mamonhoc}</td>
                    <td>${bill.studentid}</td>
                    <td>${formatDate(bill.time)}</td>
                    <td>${bill.price}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error loading bills data.");
        });
});

// Format timestamp to a readable date-time format
function formatDate(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString("en-GB", { hour12: false }); // e.g., "17/11/2024 14:30:00"
}





document.addEventListener("DOMContentLoaded", function () {
    const searchButton = document.getElementById("searchButton"); // Giả sử bạn có nút với id "searchButton"
    searchButton.addEventListener("click", function (event) {
        event.preventDefault(); // Ngăn trang reload lại
        leftSearch(); // Gọi hàm xử lý tìm kiếm
    });
});

function leftSearch() {
    const query = document.getElementById("leftSearchInput").value.trim();

    if (!query) {
        alert("Vui lòng nhập dữ liệu để tìm kiếm!");
        return;
    }

    fetch("/WEB_QLTT_IELTS/admin-search-bills", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: new URLSearchParams({ query }) // Gửi dữ liệu query dưới dạng form-urlencoded
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch bills.");
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById("unique-data-table-body");
            tableBody.innerHTML = ""; // Xóa dữ liệu cũ của bảng

            if (data.length === 0) {
                // Trường hợp không tìm thấy dữ liệu
                const noDataRow = document.createElement("tr");
                const noDataCell = document.createElement("td");
                noDataCell.setAttribute("colspan", "4");
                noDataCell.textContent = "Không tìm thấy hóa đơn phù hợp.";
                noDataCell.style.textAlign = "center";
                noDataRow.appendChild(noDataCell);
                tableBody.appendChild(noDataRow);
                return;
            }

            // Hiển thị dữ liệu mới lên bảng
            data.forEach(bill => {
                const row = document.createElement("tr");

                const maMonHocCell = document.createElement("td");
                maMonHocCell.textContent = bill.maMonHoc;

                const studentIdCell = document.createElement("td");
                studentIdCell.textContent = bill.studentId;

                const timeCell = document.createElement("td");
                timeCell.textContent = formatDate(bill.time);

                const priceCell = document.createElement("td");
                priceCell.textContent = bill.price;

                row.appendChild(maMonHocCell);
                row.appendChild(studentIdCell);
                row.appendChild(timeCell);
                row.appendChild(priceCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Đã xảy ra lỗi khi tìm kiếm hóa đơn.");
        });
}

// Helper function để định dạng timestamp
function formatDate(timestamp) {
    if (!timestamp) return "";
    const date = new Date(timestamp);
    return date.toLocaleString("en-GB", { hour12: false }); // Định dạng: "17/11/2024 14:30:00"
}



// hiển thị danh sách đăng kí trong hàng chờ
document.addEventListener("DOMContentLoaded", function () {
    fetch("/WEB_QLTT_IELTS/admin-tracking-register")
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch registrations.");
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.getElementById("registration-data-table-body");
            tableBody.innerHTML = ""; // Clear previous rows

            if (data.length === 0) {
                const noDataRow = document.createElement("tr");
                const noDataCell = document.createElement("td");
                noDataCell.setAttribute("colspan", "6");
                noDataCell.textContent = "Không có đơn đăng kí nào đang trong hàng chờ.";
                noDataCell.style.textAlign = "center";
                noDataRow.appendChild(noDataCell);
                tableBody.appendChild(noDataRow);
                return;
            }

            // Populate table with data
            data.forEach(registration => {
                const row = document.createElement("tr");

                const fullNameCell = document.createElement("td");
                fullNameCell.textContent = registration.fullName || "";

                const phoneNumberCell = document.createElement("td");
                phoneNumberCell.textContent = registration.phoneNumber || "";

                const dateOfBirthCell = document.createElement("td");
                dateOfBirthCell.textContent = formatDate(registration.dateOfBirth);

                const emailCell = document.createElement("td");
                emailCell.textContent = registration.email || "";

                const classIdCell = document.createElement("td");
                classIdCell.textContent = registration.classId || "";

                const moreInfoCell = document.createElement("td");
                moreInfoCell.textContent = registration.moreInfo || "";

                row.appendChild(fullNameCell);
                row.appendChild(phoneNumberCell);
                row.appendChild(dateOfBirthCell);
                row.appendChild(emailCell);
                row.appendChild(classIdCell);
                row.appendChild(moreInfoCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Đã xảy ra lỗi khi tải dữ liệu đơn đăng kí.");
        });
});

// Helper function to format date
function formatDate(dateString) {
    if (!dateString) return "";
    const date = new Date(dateString);
    return date.toLocaleDateString("en-GB"); // Format: "dd/mm/yyyy"
}




