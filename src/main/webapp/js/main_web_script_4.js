document.addEventListener('DOMContentLoaded', function () {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
            }
        });
    }, {
        threshold: 0.1 // Kích hoạt khi 10% của phần tử xuất hiện
    });

    const hrImage = document.querySelector('.hr');
    observer.observe(hrImage);
});