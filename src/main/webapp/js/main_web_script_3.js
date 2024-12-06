(function () {
    const imageWrapper = document.getElementById('teacherin4-imageWrapper');
    const prevBtn = document.getElementById('teacherin4-prevBtn');
    const nextBtn = document.getElementById('teacherin4-nextBtn');
    const teacherNames = [
        "Nguyễn Văn An", "Trần Thị Mai", "Lê Minh Tuấn", "Phạm Thị Hương", "Hoàng Văn Hải",
        "Đỗ Thị Lan", "Bùi Minh Khoa", "Vũ Thị Thanh", "Nguyễn Thị Hồng", "Trịnh Văn Dũng",
        "Lê Thị Nhung", "Phan Minh Quân", "Nguyễn Văn Bình", "Trần Thị Lan", "Hoàng Minh Ngọc",
        "Lâm Thị Thảo", "Vũ Văn Phát", "Đinh Thị Cẩm Tú", "Nguyễn Thị Hoa", "Phạm Văn Hưng"
    ];
    const totalProfiles = teacherNames.length;
    let currentIndex = 0;

    teacherNames.forEach((name, index) => {
        const flipCard = document.createElement('div');
        flipCard.className = 'teacherin4-flip-card';
        flipCard.innerHTML = `
            <div class="teacherin4-flip-card-inner">
                <div class="teacherin4-flip-card-front">
                    <div class="teacherin4-profile-image">
                        <svg viewBox="0 0 122.88 122.88" class="teacherin4-pfp" xmlns="http://www.w3.org/2000/svg">
                            <g>
                                <path d="M61.44,0c8.32,0,16.25,1.66,23.5,4.66l0.11,0.05c7.47,3.11,14.2,7.66,19.83,13.3l0,0c5.66,5.65,10.22,12.42,13.34,19.95c3.01,7.24,4.66,15.18,4.66,23.49c0,8.32-1.66,16.25-4.66,23.5l-0.05,0.11c-3.12,7.47-7.66,14.2-13.3,19.83l0,0c-5.65,5.66-12.42,10.22-19.95,13.34c-7.24,3.01-15.18,4.66-23.49,4.66c-8.31,0-16.25-1.66-23.5-4.66l-0.11-0.05c-7.47-3.11-14.2-7.66-19.83-13.29L18,104.87C12.34,99.21,7.78,92.45,4.66,84.94C1.66,77.69,0,69.76,0,61.44s1.66-16.25,4.66-23.5l0.05-0.11c3.11-7.47,7.66-14.2,13.29-19.83L18.01,18c5.66-5.66,12.42-10.22,19.94-13.34C45.19,1.66,53.12,0,61.44,0L61.44,0z M16.99,94.47l0.24-0.14c5.9-3.29,21.26-4.38,27.64-8.83c0.47-0.7,0.97-1.72,1.46-2.83c0.73-1.67,1.4-3.5,1.82-4.74c-1.78-2.1-3.31-4.47-4.77-6.8l-4.83-7.69c-1.76-2.64-2.68-5.04-2.74-7.02c-0.03-0.93,0.13-1.77,0.48-2.52c0.36-0.78,0.91-1.43,1.66-1.93c0.35-0.24,0.74-0.44,1.17-0.59c-0.32-4.17-0.43-9.42-0.23-13.82c0.1-1.04,0.31-2.09,0.59-3.13c1.24-4.41,4.33-7.96,8.16-10.4c2.11-1.35,4.43-2.36,6.84-3.04c1.54-0.44-1.31-5.34,0.28-5.51c7.67-0.79,20.08,6.22,25.44,12.01c2.68,2.9,4.37,6.75,4.73,11.84l-0.3,12.54l0,0c1.34,0.41,2.2,1.26,2.54,2.63c0.39,1.53-0.03,3.67-1.33,6.6l0,0c-0.02,0.05-0.05,0.11-0.08,0.16l-5.51,9.07c-2.02,3.33-4.08,6.68-6.75,9.31C73.75,80,74,80.35,74.24,80.7c1.09,1.6,2.19,3.2,3.6,4.63c0.05,0.05,0.09,0.1,0.12,0.15c6.34,4.48,21.77,5.57,27.69,8.87l0.24,0.14c6.87-9.22,10.93-20.65,10.93-33.03c0-15.29-6.2-29.14-16.22-39.15c-10-10.03-23.85-16.23-39.14-16.23c-15.29,0-29.14,6.2-39.15,16.22C12.27,32.3,6.07,46.15,6.07,61.44C6.07,73.82,10.13,85.25,16.99,94.47L16.99,94.47L16.99,94.47z"></path>
                            </g>
                        </svg>
                    </div>
                    <div class="teacherin4-name">${name}</div>
                </div>
                <div class="teacherin4-flip-card-back">
                    <p class="teacherin4-description">
                        IELTS 8.0 <br> Speaking 7.5 <br> Writing 7.0 <br> Reading 8.5 <br> Listening 8.5
                    </p>
                </div>
            </div>
        `;
        imageWrapper.appendChild(flipCard);
    });

    function updateGallery() {
        imageWrapper.style.transform = `translateX(${-currentIndex * 132.5}px)`;
    }

    function nextImage() {
        currentIndex = (currentIndex + 1) % (totalProfiles - 3);
        updateGallery();
    }

    function prevImage() {
        currentIndex = (currentIndex - 1 + totalProfiles - 3) % (totalProfiles - 3);
        updateGallery();
    }

    nextBtn.addEventListener('click', nextImage);
    prevBtn.addEventListener('click', prevImage);

    updateGallery();
})();
