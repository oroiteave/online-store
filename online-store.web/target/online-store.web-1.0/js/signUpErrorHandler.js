document.addEventListener("DOMContentLoaded", function() {
    fetch('/online-store.web/getError')
        .then(response => response.json())
        .then(data => {
            if (data) {
                const errorMessageDiv = document.getElementById("error-message");
                errorMessageDiv.innerText = data;
                errorMessageDiv.style.display = 'block';
            }
        })
        .catch(error => console.error('Error:', error));
});