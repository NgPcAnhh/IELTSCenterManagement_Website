// Fetch mock test data and display in table
function fetchMockTests() {
    fetch('/WEB_QLTT_IELTS/mocktestquery')
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(data => displayMockTests(data))
        .catch(error => {
            console.error('Error fetching mock test data:', error);
            alert("Lỗi khi kết nối đến server.");
        });
}

// Display mock tests in the table
function displayMockTests(data) {
    const tableBody = document.querySelector('#mockTestTable tbody');
    tableBody.innerHTML = ''; // Clear existing data

    data.forEach(mockTest => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${mockTest.id}</td>
            <td>${mockTest.idTest}</td>
            <td>${mockTest.time}</td>
            <td>${mockTest.reading}</td>
            <td>${mockTest.listening}</td>
            <td>${mockTest.writing}</td>
            <td>${mockTest.speaking}</td>
            <td>${mockTest.overall}</td>
            <td><button onclick="showFeedbackDetails(${JSON.stringify(mockTest).replace(/"/g, '&quot;')})">Xem Feedbacks</button></td>
        `;
        tableBody.appendChild(row);
    });
}

// Show feedback details in popup
function showFeedbackDetails(mockTest) {
    const feedbackList = document.getElementById('feedbackList');
    feedbackList.innerHTML = `
        <li><strong>READING:</strong> ${mockTest.reading}<br><em>Feedback:</em> ${mockTest.feedback_r || 'No feedback available'}</li>
        <li><strong>LISTENING:</strong> ${mockTest.listening}<br><em>Feedback:</em> ${mockTest.feedback_l || 'No feedback available'}</li>
        <li><strong>WRITING:</strong> ${mockTest.writing}<br><em>Feedback:</em> ${mockTest.feedback_w || 'No feedback available'}</li>
        <li><strong>SPEAKING:</strong> ${mockTest.speaking}<br><em>Feedback:</em> ${mockTest.feedback_s || 'No feedback available'}</li>
    `;
    document.getElementById('feedbackPopup').style.display = 'flex';
}

// Close feedback popup
function closeFeedbackPopup() {
    document.getElementById('feedbackPopup').style.display = 'none';
}

// Call fetchMockTests when the page loads
window.onload = fetchMockTests;
