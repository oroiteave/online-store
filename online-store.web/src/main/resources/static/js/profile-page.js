document.addEventListener("DOMContentLoaded", function() {
	function userDataRender(){
		const userInfoContainer = document.getElementById('user-info-container');
		const userEmailContainer = document.getElementById('user-email-container');
	fetch('/user/current')
	.then(response => response.json())
	.then(user => {
		if(user!=null){
			userInfoContainer.innerHTML = `
			<h4>Datos Personales</h4>
	        <form id="personal-info-form">
	        	<div class="row">
		        	<div class="col-sm-8 mb-3">
		                <p class="mb-0">Nombre</p>
			            <div class="form-outline">
			                <input type="text" id="firstName" name="firstName" class="form-control" value="${user.firstName}"/>
			            </div>
		            </div>
	            </div>
	            <div class="row">
		            <div class="col-sm-8 mb-3">
		                <p class="mb-0">Apellido</p>
			            <div class="form-outline">
			                <input type="text" id="lastName" name="lastName" class="form-control" value="${user.lastName}"/>
			            </div>
		            </div>
	            </div>
	            <button type="button" class="btn btn-primary" onClick="savePersonalInfo()">Guardar Cambios</button>
	        </form>
			`;
			userEmailContainer.innerHTML = `
			<h4>Cambiar Correo Electrónico</h4>
	        <form id="email-change-form">
	            <div class="form-group">
	                <label for="newEmail">Nuevo Correo Electrónico</label>
	                <input type="email" id="newEmail" class="form-control" value="${user.email}" required>
	            </div>
	            <div class="form-group">
	                <label for="confirmEmail">Confirmar Nuevo Correo Electrónico</label>
	                <input type="email" id="confirmEmail" class="form-control" placeholder="Confirmar nuevo correo electrónico" required>
	            </div>
	            <button type="button" class="btn btn-primary" onclick="changeEmail()">Cambiar Correo Electrónico</button>
	        </form>
			`
		}
	});
	}
	function addressDataRender(){
		const addressInfoContainer = document.getElementById("address-info-container");
		fetch('/address/user')
		.then(response => response.json())
		.then(address => {
			if(address!=null){
				const userCity = address.city;
				addressInfoContainer.innerHTML=`
			<h4>Dirección para envío</h4>
			<form id="address-info-form">
			  <div class="row">
			    <div class="col-sm-8 mb-3">
			      <p class="mb-0">Teléfono</p>
			      <div class="form-outline">
			        <input type="tel" id="typePhone" name="phone" value="${address.phoneNumber}" class="form-control" />
			      </div>
			    </div>
			  </div>
			  
			  <div class="row">
			    <div class="col-sm-8 mb-3">
			      <p class="mb-0">Dirección 1</p>
			      <div class="form-outline">
			        <input type="text" id="address1" name="address1" value="${address.firstDirection}" class="form-control" />
			      </div>
			    </div>
			
			    <div class="col-sm-4 mb-3">
			      <p class="mb-0">Ciudad</p>
			      <select class="form-select" id="city" name="city">
			        <option value="Arica">Arica</option>
			        <option value="Iquique">Iquique</option>
			        <option value="Antofagasta">Antofagasta</option>
			        <option value="Copiapo">Copiapo</option>
			        <option value="Coquimbo">Coquimbo</option>
			        <option value="Viña del mar">Viña del mar</option>
			        <option value="Valparaiso">Valparaiso</option>
			        <option value="Santiago">Santiago</option>
			        <option value="Rancagua">Rancagua</option>
			        <option value="Concepcion">Concepcion</option>
			        <option value="Los Angeles">Los Angeles</option>
			        <option value="Temuco">Temuco</option>
			        <option value="Puerto Mont">Puerto Mont</option>
			        <option value="Punta Arenas">Punta Arenas</option>
			      </select>
			    </div>
			  </div>
			
			  <div class="row">
			    <div class="col-sm-8 mb-3">
			      <p class="mb-0">Dirección 2</p>
			      <div class="form-outline">
			        <input type="text" id="address2" name="address2" value="${address.secondDirection}" class="form-control" />
			      </div>
			    </div>
			    
			    <div class="col-sm-8 mb-3">
			      <p class="mb-0">Casa/Depto</p>
			      <div class="form-outline">
			        <input type="text" id="houseNumber" name="houseNumber" value="${address.houseNumber}" class="form-control" />
			      </div>
			    </div>
			
			    <div class="col-sm-8 col-6 mb-3">
			      <p class="mb-0">Código postal</p>
			      <div class="form-outline">
			        <input type="text" id="postalCode" name="postalCode" value="${address.postalCode}" class="form-control" />
			      </div>
			    </div>
			  </div>
			
			  <button type="button" class="btn btn-primary" onclick="saveNewAddress()">Guardar Cambios</button>
			</form>

				`;
				const citySelect = document.getElementById("city");
				citySelect.value = userCity;
			}
		});
	}
	userDataRender();
	addressDataRender();
});
	
	
	function savePersonalInfo() {
        const formElement = document.querySelector('#personal-info-form');
		const params = new URLSearchParams();
		
		new FormData(formElement).forEach((value, key) => {
				        params.append(key, value);
		});
		
		fetch('/user', {
            method: 'PUT',
            headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
            body: params.toString()
        })
        .then(response => response.text())
        .then(data => {
	        alert(data);
		})
        .catch(error => console.error('Error en el envío de datos del usuario:', error));
    }
    function saveNewAddress(){
		const formElement = document.querySelector('#address-info-form');
		const params = new URLSearchParams();
		
		new FormData(formElement).forEach((value, key) => {
				        params.append(key, value);
		});
		
		console.log(params.toString());
		fetch('/address', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
    		},
            body: params.toString()
		})
		.then(response => response.text())
		.then(data =>{
	        alert(data);
		})
		.catch(error => console.error('Error en el envío de datos de la direccion', error));
	}

    function changePassword() {
        const currentPassword = document.getElementById('currentPassword').value;
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
		const params = new URLSearchParams();

        if (newPassword !== confirmPassword) {
            alert('La nueva contraseña y la confirmación no coinciden.');
            return;
        }
        
        if(newPassword === currentPassword){
			alert('no puedes poner la misma contraseña que la anterior.')
			return;
		}
	
		params.append('password',currentPassword);
		params.append('newPassword',newPassword);
	
		fetch('/user/password', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			 body: params.toString()
		})
		.then(response => response.text())
		.then(data => {
			alert(data);
		})
		.catch(error => console.error('Error al enviar la nueva contraseña', error));
    }

    function changeEmail() {
        const newEmail = document.getElementById('newEmail').value;
        const confirmEmail = document.getElementById('confirmEmail').value;
		
        if (newEmail !== confirmEmail) {
            alert('El nuevo correo y la confirmación no coinciden.');
            return;
        }
		
		const params = new URLSearchParams();
		params.append("email",newEmail);
		
        fetch('user/email', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: params.toString()
		})
		.then(response => response.text())
		.then(data => {
			alert(data);
		})
		.catch(error => console.error('Error al enviar el nuevo email', error));
    }
