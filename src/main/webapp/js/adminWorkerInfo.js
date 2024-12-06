// Function to fetch worker info from the servlet and display it on the page
function fetchWorkerInfo() {
    fetch('/WEB_QLTT_IELTS/adminWorkerInfo')
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            displayWorkerInfo(data);
        })
        .catch(error => {
            console.error("Error fetching worker info:", error);
            alert("Error loading worker information.");
        });
}

// Function to display worker info in the HTML
function displayWorkerInfo(data) {
    const infoContent = document.getElementById("student-info");
    infoContent.innerHTML = "";  // Clear any existing content

    if (data.error) {
        infoContent.innerHTML = `<li>${data.error}</li>`;
    } else {
        const listItem = document.createElement("li");
        listItem.innerHTML = `
            <strong>ID:</strong> ${data.id}<br>
            <strong>Name:</strong> ${data.name}<br>
            <strong>Role:</strong> ${data.role}<br>
            <strong>Phone:</strong> ${data.phone_number}<br>
            <strong>Email:</strong> ${data.email}
        `;
        infoContent.appendChild(listItem);
    }
}

// Call fetchWorkerInfo when the page loads
document.addEventListener("DOMContentLoaded", fetchWorkerInfo);
