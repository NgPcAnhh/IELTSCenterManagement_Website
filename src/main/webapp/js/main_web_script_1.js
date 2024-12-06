document.getElementById('scrollToTopBtn').addEventListener('click', () => {
    window.scrollTo({top: 0, behavior: 'smooth'});
});

document.getElementById('Phone').addEventListener('click', function() {
    window.location.href = 'tel:+84987654321';
});

document.getElementById('Mail').addEventListener('click', function() {
    window.location.href = 'mailto:the.liems.welcome@gmail.com';
});

document.getElementById('Chat').addEventListener('click', function() {
    window.open('https://www.facebook.com/messages/t/102302961167459', '_blank');
});
