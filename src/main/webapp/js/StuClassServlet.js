// Mảng lưu mã môn (sẽ được điền từ server sau khi đăng nhập)
let maMonList = [];

// Hàm gọi đến servlet để lấy danh sách mã môn của sinh viên
function fetchMaMonList() {
    fetch("/WEB_QLTT_IELTS/stuclass", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ action: "getMaMonByStudentId" })
    })
        .then(response => response.json())
        .then(jsonResponse => {
            if (jsonResponse.success) {
                maMonList = jsonResponse.maMonList;
                displayMaMonButtons(maMonList);
            } else {
                console.log("Lỗi:", jsonResponse.message);
            }
        })
        .catch(error => console.error("Có lỗi xảy ra:", error));
}

// Hàm hiển thị các nút mã môn
function displayMaMonButtons(maMonList) {
    const container = document.querySelector(".class-buttons");
    container.innerHTML = ""; // Xóa các nút cũ nếu có

    maMonList.forEach((maMon) => {
        if (maMon) { // Kiểm tra nếu mã môn không null
            const button = document.createElement("button");
            button.className = "class-button";
            button.textContent = maMon;
            button.onclick = () => loadClassData(maMon); // Gọi loadClassData với mã môn cụ thể
            container.appendChild(button);
        }
    });
}

// Hàm lấy danh sách lớp cho mã môn cụ thể và hiển thị
function loadClassData(maMon) {
    fetch("/WEB_QLTT_IELTS/stuclass", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ action: "getStudentListByMaMon", maMon: maMon })
    })
        .then(response => response.json())
        .then(jsonResponse => {
            if (jsonResponse.success) {
                renderClassList(jsonResponse.classList);
            } else {
                console.log("Lỗi:", jsonResponse.message);
            }
        })
        .catch(error => console.error("Có lỗi xảy ra:", error));
}

// Hàm hiển thị danh sách lớp trong bảng
function renderClassList(classList) {
    const tbody = document.getElementById("classTable").querySelector("tbody");
    tbody.innerHTML = ""; // Xóa nội dung cũ

    classList.forEach(student => {
        const row = document.createElement("tr");

        const idCell = document.createElement("td");
        idCell.textContent = student.id;
        row.appendChild(idCell);

        const dobCell = document.createElement("td");
        dobCell.textContent = student.date_birth;
        row.appendChild(dobCell);

        const nameCell = document.createElement("td");
        nameCell.textContent = student.student_name;
        row.appendChild(nameCell);

        const phoneCell = document.createElement("td");
        phoneCell.textContent = student.phone_number;
        row.appendChild(phoneCell);

        const emailCell = document.createElement("td");
        emailCell.textContent = student.gmail;
        row.appendChild(emailCell);

        tbody.appendChild(row);
    });
}

// Gọi hàm fetchMaMonList khi trang được tải
fetchMaMonList();
