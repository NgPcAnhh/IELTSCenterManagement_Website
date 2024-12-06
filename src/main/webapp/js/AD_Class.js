document.getElementById("subjectCode").addEventListener("change", function() {
    const subjectCode = this.value;

    fetch(`/WEB_QLTT_IELTS/getStudentsBySubject?subjectCode=${subjectCode}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById("studentTable").getElementsByTagName("tbody")[0];
            tableBody.innerHTML = ""; // Xóa nội dung cũ

            data.forEach(student => {
                const row = document.createElement("tr");

                const nameCell = document.createElement("td");
                nameCell.textContent = student.studentName;
                row.appendChild(nameCell);

                const dobCell = document.createElement("td");
                dobCell.textContent = new Date(student.dateOfBirth).toLocaleDateString();
                row.appendChild(dobCell);

                const idCell = document.createElement("td");
                idCell.textContent = student.id;
                row.appendChild(idCell);

                const phoneCell = document.createElement("td");
                phoneCell.textContent = student.phoneNumber;
                row.appendChild(phoneCell);

                const emailCell = document.createElement("td");
                emailCell.textContent = student.gmail;
                row.appendChild(emailCell);

                const parentPhoneCell = document.createElement("td");
                parentPhoneCell.textContent = student.parentNumber;
                row.appendChild(parentPhoneCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error:", error));
});
