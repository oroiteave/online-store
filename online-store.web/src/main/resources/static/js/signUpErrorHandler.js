document.addEventListener("DOMContentLoaded", function() {
    fetch('/errorMessage')
        .then(response => response.text())
        .then(data => {
            if (data) {
                const errorMessageDiv = document.getElementById("error-message");
                errorMessageDiv.innerText = data;
                errorMessageDiv.style.display = 'block';
            }
        })
        .catch(error => console.error('Error:', error));
});