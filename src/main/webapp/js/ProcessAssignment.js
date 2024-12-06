document.addEventListener("DOMContentLoaded", () => {
    fetch('/WEB_QLTT_IELTS/getAssignmentInfo')
        .then(response => response.json())
        .then(data => {
            let totalTasks = data.length;
            let completedTasks = data.filter(task => task.checking === "done").length;
            let uncompletedTasks = totalTasks - completedTasks;
            let feedbackCount = data.filter(task => task.feedbacks && task.feedbacks.trim() !== "").length;

            document.getElementById("total-tasks").textContent = totalTasks;
            document.getElementById("completed-tasks").textContent = completedTasks;
            document.getElementById("uncompleted-tasks").textContent = uncompletedTasks;
            document.getElementById("feedback-count").textContent = feedbackCount;
        })
        .catch(error => {
            console.error("Error fetching assignments:", error);
        });
});
