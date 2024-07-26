document.getElementById('togglePassword').addEventListener('click', function () {
        togglePasswordVisibility('password', 'togglePassword');
});

document.getElementById('togglePasswordConfirm').addEventListener('click', function () {
    togglePasswordVisibility('confirmPassword', 'togglePasswordConfirm');
});

function togglePasswordVisibility(inputId, toggleButtonId) {
    var passwordInput = document.getElementById(inputId);
    var icon = document.getElementById(toggleButtonId).querySelector('i');
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}