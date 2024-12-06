document.addEventListener('DOMContentLoaded', function () {
    console.log("Checking session...");

    // Gửi yêu cầu để lấy thông tin session
    fetch('/WEB_QLTT_IELTS/getSession')
        .then(response => {
            if (!response.ok) {
                throw new Error("Không thể lấy thông tin session.");
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                const userId = data.userId;

                // Log thông tin session
                console.log("Session thành công: userId =", userId);

                // Nếu session bắt đầu bằng "TE", chặn quyền truy cập
                if (userId.startsWith("TE")) {
                    displayAccessDeniedCard();
                }
            } else {
                console.error("Session không hợp lệ:", data.message);
            }
        })
        .catch(error => {
            console.error("Lỗi khi kiểm tra session:", error);
        });
});

// Hàm hiển thị thông báo "Bạn không có quyền truy cập"
function displayAccessDeniedCard() {
    // Tạo overlay để phủ toàn bộ trang
    const overlay = document.createElement('div');
    overlay.style.position = 'fixed';
    overlay.style.top = '0';
    overlay.style.left = '0';
    overlay.style.width = '100vw';
    overlay.style.height = '100vh';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.8)';
    overlay.style.display = 'flex';
    overlay.style.justifyContent = 'center';
    overlay.style.alignItems = 'center';
    overlay.style.zIndex = '9999';

    // Thêm thẻ HTML thông báo chặn
    overlay.innerHTML = `
        <style>
        .card {
          background-color: #222;
          border: 10px solid rgb(161, 156, 116);
          padding: 30px;
          width: 400px;
          text-align: center;
          position: relative;
          box-shadow: 9px 11px 1px rgb(0, 0, 0);
          transition: transform 0.3s ease, box-shadow 0.3s ease;
          cursor: pointer;
          overflow: hidden;
          z-index: 1;
        }
        
        .card::before {
          content: "";
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          background: repeating-linear-gradient(
            0deg,
            rgba(255, 255, 255, 0.05),
            rgba(255, 255, 255, 0.05) 1px,
            transparent 1px,
            transparent 2px
          );
        
          opacity: 0.3;
          pointer-events: none;
          z-index: 2;
          animation: tv-static 0.2s infinite linear alternate;
        }
        
        .card::after {
          content: "";
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          background: linear-gradient(rgba(18, 16, 16, 0) 50%, rgba(0, 0, 0, 0.25) 50%),
            linear-gradient(
              90deg,
              rgba(255, 0, 0, 0.06),
              rgba(0, 255, 0, 0.02),
              rgba(0, 0, 255, 0.06)
            );
          background-size: 100% 2px, 3px 100%;
          animation: tv-flicker 0.15s infinite;
          z-index: 35;
          pointer-events: none;
          opacity: 0.5;
        }
        
        @keyframes tv-static {
          0% {
            opacity: 0.3;
          }
          100% {
            opacity: 0.1;
          }
        }
        
        .eye-of-providence {
          position: relative;
          width: 220px;
          height: 220px;
          margin: 0 auto 20px;
        }
        
        .triangle {
          width: 0;
          height: 0;
          border-left: 110px solid transparent;
          border-right: 110px solid transparent;
          border-bottom: 190px solid rgb(198, 181, 113);
          position: absolute;
          top: 0;
          left: 0;
          z-index: 1;
          transition: transform 0.3s ease;
        }
        
        .card:hover .triangle {
          transform: scale(1.05);
        }
        
        .eye {
          width: 90px;
          height: 50px;
          background: radial-gradient(circle at 30% 30%, #fff, #e6e6e6);
          border-radius: 50%;
          position: absolute;
          top: 90px;
          left: 65px;
          overflow: hidden;
          box-shadow: 0 0 0 10px rgb(115, 112, 92), 15px 15px 0 #000,
            inset -5px -5px 10px rgba(0, 0, 0, 0.2),
            inset 5px 5px 10px rgba(255, 255, 255, 0.3);
          z-index: 2;
          animation: blink 6s infinite cubic-bezier(0.76, 0, 0.24, 1);
        }
        
        .pupil {
          width: 16px;
          height: 16px;
          background: rgb(207, 19, 19);
          border-radius: 50%;
          background: radial-gradient(circle at 50% 50%, #000, #444);
          border: 2px solid #fff;
          position: absolute;
          top: 14px;
          left: 14px;
          box-shadow: inset 0 0 10px rgba(203, 4, 4, 0.2), 0 0 10px rgba(0, 0, 0, 0.5);
        }
        
        @keyframes blink {
          0%,
          90%,
          100% {
            height: 50px;
          }
          92%,
          98% {
            height: 0px;
          }
        }
        
        @keyframes blink {
          0%,
          92%,
          100% {
            transform: scaleY(1);
          }
          94% {
            transform: scaleY(0.1);
          }
        }
        
        .iris {
          width: 45px;
          height: 45px;
          background: #000;
          border-radius: 50%;
          position: absolute;
          top: 2px;
          left: 22px;
          animation: look-around 6s infinite;
          animation-timing-function: ease-in-out;
        }
        
        @keyframes look-around {
          0%,
          100% {
            transform: translate(-2, 10px) scale(1);
          }
          25% {
            transform: translate(-18px, 10px) scale(1.1);
          }
          50% {
            transform: translate(0, 15px) scale(1.2);
          }
          75% {
            transform: translate(20px, 10px) scale(1.1);
          }
        }
        
        .triangle-overlay {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          border: 4px solid #f00;
          clip-path: polygon(50% 0%, 100% 100%, 0% 100%);
          animation: rotate 15s linear infinite;
          z-index: 0;
        }
        
        @keyframes rotate {
          0% {
            transform: rotate(0deg);
          }
          100% {
            transform: rotate(360deg);
          }
        }
        
        .title {
          color: rgb(255, 255, 255);
          margin-top: 30px;
          text-transform: uppercase;
          letter-spacing: 2px;
          font-size: 28px;
          padding-bottom: 33px;
          text-shadow: 3px 3px #000;
          z-index: 3;
          position: relative;
          display: inline-block;
        }
        
        .title::before,
        .title::after {
          content: attr(data-text);
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
        }
        
        .title::before {
          left: 2px;
          text-shadow: -1px 0 red;
          animation: glitch-anim-1 2s infinite linear alternate-reverse;
        }
        
        .title::after {
          left: -2px;
          text-shadow: -1px 0 blue;
          animation: glitch-anim-2 3s infinite linear alternate-reverse;
        }
        
        @keyframes glitch-anim-1 {
          0% {
            clip: rect(30px, 9999px, 10px, 0);
          }
          100% {
            clip: rect(42px, 9999px, 78px, 0);
          }
        }
        
        @keyframes glitch-anim-2 {
          0% {
            top: -1px;
            left: 1px;
            clip: rect(65px, 9999px, 119px, 0);
          }
          100% {
            top: 1px;
            left: -1px;
            clip: rect(79px, 9999px, 16px, 0);
          }
        }
        
        .glitch-text {
          color: #fff;
          font-size: 20px;
          margin-top: 20px;
          position: relative;
          display: inline-block;
          text-shadow: 2px 2px #000;
          z-index: 3;
        }
        
        .glitch-text {
          color: #fff;
          font-size: 20px;
          margin-top: 20px;
          position: relative;
          display: inline-block;
          text-shadow: 2px 2px #000;
          z-index: 3;
        }
        
        .glitch-text::before,
        .glitch-text::after {
          content: "The Eye of Providence";
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
        }
        
        .glitch-text::before {
          left: 3px;
          text-shadow: -3px 0 #f00;
          clip: rect(44px, 450px, 56px, 0);
          animation: glitch-anim 3s infinite linear alternate-reverse;
        }
        
        .glitch-text::after {
          left: -3px;
          text-shadow: -3px 0 #0ff;
          clip: rect(44px, 450px, 56px, 0);
          animation: glitch-anim 3s infinite linear alternate-reverse;
        }
        
        @keyframes glitch-anim {
          0% {
            clip: rect(34px, 9999px, 11px, 0);
          }
          5% {
            clip: rect(92px, 9999px, 89px, 0);
          }
          10% {
            clip: rect(6px, 9999px, 22px, 0);
          }
          15% {
            clip: rect(75px, 9999px, 4px, 0);
          }
          20% {
            clip: rect(6px, 9999px, 87px, 0);
          }
          25% {
            clip: rect(45px, 9999px, 98px, 0);
          }
          30% {
            clip: rect(39px, 9999px, 57px, 0);
          }
          35% {
            clip: rect(90px, 9999px, 65px, 0);
          }
          40% {
            clip: rect(7px, 9999px, 90px, 0);
          }
          45% {
            clip: rect(30px, 9999px, 88px, 0);
          }
          50% {
            clip: rect(76px, 9999px, 23px, 0);
          }
          55% {
            clip: rect(57px, 9999px, 35px, 0);
          }
          60% {
            clip: rect(85px, 9999px, 87px, 0);
          }
          65% {
            clip: rect(54px, 9999px, 12px, 0);
          }
          70% {
            clip: rect(32px, 9999px, 56px, 0);
          }
          75% {
            clip: rect(36px, 9999px, 98px, 0);
          }
          80% {
            clip: rect(12px, 9999px, 43px, 0);
          }
          85% {
            clip: rect(54px, 9999px, 76px, 0);
          }
          90% {
            clip: rect(43px, 9999px, 23px, 0);
          }
          95% {
            clip: rect(76px, 9999px, 87px, 0);
          }
        }
        
        .scan-line {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          height: 6px;
          background: rgba(75, 68, 68, 0.5);
          animation: scan 2.5s linear infinite;
          z-index: 4;
        }
        
        @keyframes scan {
          0% {
            top: 0;
          }
          100% {
            top: 100%;
          }
        }
        
        .triangle-overlay {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          border: 4px solid #f00;
          clip-path: polygon(50% 0%, 100% 100%, 0% 100%);
          animation: rotate 15s linear infinite;
          z-index: 0;
        }
        
        @keyframes rotate {
          0% {
            transform: rotate(0deg);
          }
          100% {
            transform: rotate(360deg);
          }
        }
        
        .title {
          color: rgb(255, 255, 255);
          margin-top: 30px;
          text-transform: uppercase;
          letter-spacing: 2px;
          font-size: 28px;
          text-shadow: 3px 3px #000;
          z-index: 3;
        }
        
        .glitch-text {
          color: #fff;
          font-size: 20px;
          margin-top: 20px;
          position: relative;
          display: inline-block;
          text-shadow: 2px 2px #000;
          z-index: 3;
        }
        
        .glitch-text::before,
        .glitch-text::after {
          content: "The Eye of Providence";
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
        }
        
        .glitch-text::before {
          left: 3px;
          text-shadow: -3px 0 #f00;
          clip: rect(44px, 450px, 56px, 0);
          animation: glitch-anim 3s infinite linear alternate-reverse;
        }
        
        .glitch-text::after {
          left: -3px;
          text-shadow: -3px 0 #0ff;
          clip: rect(44px, 450px, 56px, 0);
          animation: glitch-anim 3s infinite linear alternate-reverse;
        }
        
        @keyframes glitch-anim {
          0% {
            clip: rect(34px, 9999px, 11px, 0);
          }
          5% {
            clip: rect(92px, 9999px, 89px, 0);
          }
          10% {
            clip: rect(6px, 9999px, 22px, 0);
          }
          15% {
            clip: rect(75px, 9999px, 4px, 0);
          }
          20% {
            clip: rect(6px, 9999px, 87px, 0);
          }
          25% {
            clip: rect(45px, 9999px, 98px, 0);
          }
          30% {
            clip: rect(39px, 9999px, 57px, 0);
          }
          35% {
            clip: rect(90px, 9999px, 65px, 0);
          }
          40% {
            clip: rect(7px, 9999px, 90px, 0);
          }
          45% {
            clip: rect(30px, 9999px, 88px, 0);
          }
          50% {
            clip: rect(76px, 9999px, 23px, 0);
          }
          55% {
            clip: rect(57px, 9999px, 35px, 0);
          }
          60% {
            clip: rect(85px, 9999px, 87px, 0);
          }
          65% {
            clip: rect(54px, 9999px, 12px, 0);
          }
          70% {
            clip: rect(32px, 9999px, 56px, 0);
          }
          75% {
            clip: rect(36px, 9999px, 98px, 0);
          }
          80% {
            clip: rect(12px, 9999px, 43px, 0);
          }
          85% {
            clip: rect(54px, 9999px, 76px, 0);
          }
          90% {
            clip: rect(43px, 9999px, 23px, 0);
          }
          95% {
            clip: rect(76px, 9999px, 87px, 0);
          }
        }
        
        .scan-line {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          height: 6px;
          background: rgba(75, 68, 68, 0.5);
          animation: scan 2.5s linear infinite;
          z-index: 4;
        }
        
        @keyframes scan {
          0% {
            top: 0;
          }
          100% {
            top: 100%;
          }
        }
        
        .triangle-overlay {
          position: absolute;
          top: 0;
          left: 0;
          right: 0;
          bottom: 0;
          border: 4px solid #f00;
          clip-path: polygon(50% 0%, 100% 100%, 0% 100%);
          animation: rotate 15s linear infinite;
          z-index: 0;
        }
        
        @keyframes rotate {
          0% {
            transform: rotate(0deg);
          }
          100% {
            transform: rotate(360deg);
          }
        }
        
        .title {
          color: rgb(255, 255, 255);
          margin-top: 30px;
          text-transform: uppercase;
          letter-spacing: 2px;
          font-size: 28px;
          text-shadow: 3px 3px #000;
          z-index: 3;
        }
        
        .glitch-text {
          color: #fff;
          font-size: 20px;
          margin-top: 20px;
          position: relative;
          display: inline-block;
          text-shadow: 2px 2px #000;
          z-index: 3;
        }
        
        .glitch-text::before,
        .glitch-text::after {
          content: "Always Watching";
          position: absolute;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
        }
        
        .glitch-text::before {
          left: 3px;
          text-shadow: -3px 0 #f00;
          clip: rect(44px, 450px, 56px, 0);
          animation: glitch-anim 3s infinite linear alternate-reverse;
        }
        
        .glitch-text::after {
          left: -3px;
          text-shadow: -3px 0 #0ff;
          clip: rect(44px, 450px, 56px, 0);
          animation: glitch-anim 3s infinite linear alternate-reverse;
        }
        
        .footer-text {
          color: #fff;
          font-size: 14px;
          margin-top: 20px;
          text-transform: uppercase;
          letter-spacing: 1px;
          opacity: 0.7;
          position: relative;
          z-index: 3;
        }
        
        .footer-text::before {
          content: "";
          position: absolute;
          top: -10px;
          left: 50%;
          width: 50px;
          height: 1px;
          background-color: rgba(255, 255, 255, 0.3);
          transform: translateX(-50%);
        }
        </style>
        <div class="card">
          <div data-text=" You are not allowed" class="title">
            You are not allowed
          </div>
          <div class="eye-of-providence">
            <div class="triangle"></div>
            <div class="eye">
              <div class="eyelid"></div>
              <div class="iris">
                <div class="pupil"></div>
              </div>
            </div>
          </div>
          <div class="spotlight"></div>
          <div class="scan-line"></div>
          <div class="glitch-text">Only ADMIN can access</div>
          <div class="footer-text">You should return to main interface</div>
        </div>
    `;

    // Thêm overlay vào trang
    document.body.appendChild(overlay);

    // Đóng overlay khi nhấn ra ngoài
    overlay.addEventListener('click', function () {
        document.body.removeChild(overlay);
        window.location.href = 'ADMIN-INF.html'; // Chuyển hướng về trang chính
    });
}
