// ADMINfetchLatestRegistrations.js

// Function to fetch the latest 50 bills and populate the table
async function fetchLatestRegistrations() {
    try {
        // Fetch data from the servlet
        const response = await fetch('/WEB_QLTT_IELTS/adminGetLatestBills');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();

        // Check if the status is success and data contains bills
        if (data.status === "success" && data.bills) {
            const tableBody = document.getElementById('latestRegistrationsTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ""; // Clear any existing rows

            // Populate the table with the retrieved data
            data.bills.forEach(bill => {
                const row = document.createElement('tr');

                const subjectIdCell = document.createElement('td');
                subjectIdCell.textContent = bill.subject_id;
                row.appendChild(subjectIdCell);

                const studentIdCell = document.createElement('td');
                studentIdCell.textContent = bill.student_id;
                row.appendChild(studentIdCell);

                const timeCell = document.createElement('td');
                timeCell.textContent = new Date(bill.time).toLocaleString(); // Format timestamp
                row.appendChild(timeCell);

                const priceCell = document.createElement('td');
                priceCell.textContent = bill.price.toFixed(2); // Format price to 2 decimal places
                row.appendChild(priceCell);

                tableBody.appendChild(row);
            });
        } else {
            console.error("Failed to fetch bills data or data format is incorrect.");
        }
    } catch (error) {
        console.error("Error fetching latest registrations:", error);
    }
}

// Call the function to fetch and display data when the page loads
document.addEventListener("DOMContentLoaded", fetchLatestRegistrations);