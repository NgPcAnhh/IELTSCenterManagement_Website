document.addEventListener("DOMContentLoaded", () => {
    fetch('/WEB_QLTT_IELTS/stuprocess')
        .then(response => response.json())
        .then(data => {
            const coursesList = document.getElementById("coursesList");
            if (!data.success) {
                coursesList.innerHTML = `<p>${data.message}</p>`;
                return;
            }

            data.courses.forEach(course => {
                const startDate = new Date(course.start_date);
                const durationDays = course.duration_days;

                const currentDate = new Date();
                const daysParticipated = Math.floor((currentDate - startDate) / (1000 * 60 * 60 * 24));
                const completionPercentage = Math.min((daysParticipated / durationDays) * 100, 100).toFixed(2);

                // Tạo phần tử hiển thị phần trăm và tên khóa học
                const courseContainer = document.createElement("div");
                courseContainer.className = "course-progress";

                // Vòng tròn tiến độ và tên khóa học
                courseContainer.innerHTML = `
    <div class="course-progress">
        <div class="percent" style="position: relative; width: 100px; height: 100px; display: flex; align-items: center; justify-content: center;">
            <svg style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; transform: rotate(-90deg);">
                <circle cx="50" cy="50" r="40" style="fill: none; stroke: #e6e6e6; stroke-width: 8;"></circle>
                <circle cx="50" cy="50" r="40" style="fill: none; stroke: #03a9f4; stroke-width: 8; stroke-dasharray: 251; stroke-dashoffset: ${251 - (251 * completionPercentage) / 100}; transition: stroke-dashoffset 0.5s ease;"></circle>
            </svg>
            <div class="num" style="position: relative; font-size: 10px; font-weight: bold; color: #111;">
                <h2>${completionPercentage}<span>%</span></h2>
            </div>
        </div>
        <h3 class="course-name" style="margin-top: 10px; font-weight: bold; font-size: 20px; color: #111;">${course.ma_mon_hoc}</h3>
    </div>
`;


                // Thêm phần tử vào danh sách khóa học
                coursesList.appendChild(courseContainer);
            });
        })
        .catch(error => console.error("Error fetching course progress:", error));
});
