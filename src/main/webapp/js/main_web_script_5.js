const slider = document.querySelector('.web7-slider');
const prevBtn = document.getElementById('web7-prevBtn');
const nextBtn = document.getElementById('web7-nextBtn');
const slides = document.querySelectorAll('.web7-slide');
const slideWidth = 290; // 260px width + 30px margin
const totalSlides = slides.length;
let slideIndex = 0;

function showSlides() {
    slider.style.transform = `translateX(-${slideIndex * slideWidth}px)`;
}

prevBtn.addEventListener('click', () => {
    if (slideIndex > 0) {
        slideIndex--;
    } else {
        slideIndex = totalSlides - 3; // Hiển thị 3 slides cùng lúc
    }
    showSlides();
});

nextBtn.addEventListener('click', () => {
    if (slideIndex < totalSlides - 3) { // Hiển thị 3 slides cùng lúc
        slideIndex++;
    } else {
        slideIndex = 0;
    }
    showSlides();
});

// Khởi tạo slider
showSlides();

function openLibraryPage() {
    window.location.href = "STU-LIBRARY.html"; // Chuyển đến trang library.html
}
function openMockPage() {
    console.log("Navigating to New page");
    window.location.href = "STU-TEST.html";
}






