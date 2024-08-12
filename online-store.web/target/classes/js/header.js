document.addEventListener("DOMContentLoaded", function() {
    fetch('header.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('header-placeholder').innerHTML = data;
            insertFunctions();
        });
});

function insertFunctions(){
	updateLoggedInUserHeader();
    searchQuery();
}

function updateLoggedInUserHeader() {
        fetch('/online-store.web/getUserName')
            .then(response => response.json())
            .then(data => {
                if (data.userName != null) {
                    document.getElementById("loggedInControllerUserName").innerHTML = `
                    <li class="nav-item"><a class="nav-link" href="profile-page.html#">Bienvenido ${data.userName}</a></li>`;
        			document.getElementById("loggedInControllerLogOut").innerHTML =`
        			<li class="nav-item"><form action="logout" method="GET">
                        <button class="btn btn-link nav-link" type="submit">Cerrar sesión</button>
                    </form></li>`;
                } else {
                    document.getElementById("loggedInControllerUserName").innerHTML = `
                    <li class="nav-item"><a class="nav-link" href="sign-in.html#">Iniciar sesión</a></li>`;
        			document.getElementById("loggedInControllerLogOut").innerHTML =`
        			<li class="nav-item"><a class="nav-link" href="sign-up.html#">Registrarse</a></li>`;
                }
            })
            .catch(error => console.error('Error:', error));
}

function searchQuery(){
    const sessionInput = document.getElementById('searchQuery');
    const suggestions = document.getElementById('suggestions');
    
	sessionInput.addEventListener('input',updateValue);
	document.addEventListener('click', function(event) {
        if (!event.target.closest('.form-inline')) {
            suggestions.classList.remove('show');
        }
    });  
	
    
    function updateValue(e){
        const query = e.target.value;
		if(query.length>0){
			fetch(`/online-store.web/getproductslikesearch?query=${encodeURIComponent(query)}`)
	        .then(response => response.json())
	        .then(products => {
	            if (products.length > 0) {
	                suggestions.innerHTML = products.map(product => 
	                    `<a class="dropdown-item" href="product.html?id=${product.id}">${product.productName}</a>`
	                ).join('');
	                suggestions.classList.add('show');
	            } else {
	                suggestions.innerHTML = '';
	                suggestions.classList.remove('show');
	            }
	        })
	        .catch(error => console.error('Error:', error));
		}else{
			suggestions.innerHTML = '';
            suggestions.classList.remove('show');
		}
	}
}
