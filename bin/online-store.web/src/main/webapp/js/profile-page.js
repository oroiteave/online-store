document.addEventListener("DOMContentLoaded", function() {
	function userDataRender(){
		const userInfoContainer = document.getElementById('user-info-container');
	fetch('/online-store.web/getUserDetails')
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
			
		}
	});
	}
	function addressDataRender(){
		const addressInfoContainer = document.getElementById("address-info-container");
		fetch('/online-store.web/address')
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
		
		fetch('/online-store.web/updateUserDetails', {
                    method: 'POST',
                    headers: {
        				'Content-Type': 'application/x-www-form-urlencoded'
    				},
                    body: params.toString()
        })
        .then(response => response.json())
        .then(data => {
	        alert(data.msg);
		})
        .catch(error => console.error('Error en el envío de datos del usuario:', error));
    }
    function saveNewAddress(){
//		const phoneNumber = document.getElementById("phone");
//		const firstDirection = document.getElementById("address1");
//		const secondDirection = document.getElementById("address2");
//		const houseNumber = document.getElementById("houseNumber");
//		const postalCode = document.getElementById("postalCode");
//		const city = document.getElementById("city");
		
		const formElement = document.querySelector('#address-info-form');
		const params = new URLSearchParams();
		
		new FormData(formElement).forEach((value, key) => {
				        params.append(key, value);
		});
		
		console.log(params.toString());
		fetch('/online-store.web/address', {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
    		},
            body: params.toString()
		})
		.then(response => response.json())
		.then(data =>{
	        alert(data.msg);
		})
		.catch(error => console.error('Error en el envío de datos de la direccion', error));
	}

    // Función para cambiar la contraseña
    function changePassword() {
        const currentPassword = document.getElementById('currentPassword').value;
        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (newPassword !== confirmPassword) {
            alert('La nueva contraseña y la confirmación no coinciden.');
            return;
        }

        // Aquí podrías hacer un fetch para enviar los datos al servidor con POST
        console.log('Contraseña Cambiada:', { currentPassword, newPassword });

        alert('Contraseña actualizada correctamente.');
    }

    // Función para cambiar el correo electrónico
    function changeEmail() {
        const newEmail = document.getElementById('newEmail').value;
        const confirmEmail = document.getElementById('confirmEmail').value;

        if (newEmail !== confirmEmail) {
            alert('El nuevo correo y la confirmación no coinciden.');
            return;
        }

        // Aquí podrías hacer un fetch para enviar los datos al servidor con POST
        console.log('Correo Electrónico Cambiado:', { newEmail });

        alert('Correo electrónico actualizado correctamente.');
    }
