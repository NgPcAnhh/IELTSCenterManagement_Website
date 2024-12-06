document.querySelector('.web5-form').addEventListener('submit', function (event) {
    event.preventDefault(); // Ngăn form reload trang

    const formData = new FormData(this);

    // Kiểm tra nếu class_id chưa được chọn
    if (!formData.get('class_id')) {
        alert('Please select a class ID.');
        return;
    }

    // Tạo đối tượng dữ liệu
    const data = {
        full_name: formData.get('full_name'),
        phone_number: formData.get('phone_number'),
        date_birth: formData.get('date_birth'),
        email: formData.get('email'),
        class_id: formData.get('class_id'),
        more_information: formData.get('more_information')
    };

    // Gửi dữ liệu lên servlet
    fetch('/WEB_QLTT_IELTS/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then((text) => {
                    throw new Error(text);
                });
            }
            return response.text();
        })
        .then(result => {
            alert(result); // Hiển thị kết quả trả về
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred: ' + error.message);
        });
});
