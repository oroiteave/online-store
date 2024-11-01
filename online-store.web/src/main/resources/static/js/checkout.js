document.addEventListener("DOMContentLoaded", function() {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('id');
    
    if (productId) {
        fetch(`/product?id=${encodeURIComponent(productId)}`)
            .then(response => response.json())
            .then(product => {
                document.getElementById('product-details').innerHTML = `
            <div class="me-3 position-relative">
            	<img src="/product-images/${product.imgName}" style="height: 96px; width: 96x;" class="img-sm rounded border" />
            </div>
            <div class="">
            	<a href="#" class="nav-link">${product.productName}</a>
            	<div class="price text-muted">Total: $${product.price}</div>
            </div>
                `;
                document.getElementById('product-price').innerHTML =`
                	<p class="mb-2">Precio total:</p>
            		<p class="mb-2 fw-bold">$${product.price}</p>`;
            
            addressFormSave();
            paypalButton(product);
            })
            .catch(error => console.error('Error fetching product details:', error));
    }
});


function paypalButton(product) {
    //client account credentials:
    //sb-vlqft27291221@personal.example.com
    //'TI5>vpK

    paypal.Buttons({
        style: {
            layout: 'vertical',
            color: 'blue',
            shape: 'rect',
            label: 'paypal',
        },
        createOrder: function (data, actions) {
            return actions.order.create({
                purchase_units: [{
                    description: product.productName,
                    amount: {
                        value: product.price
                    }
                }]
            });
        },
        onApprove: function (data, actions) {
            actions.order.capture()
                .then(function (details) {
                const formElement = document.querySelector('#checkoutForm');
                const addressForm = document.querySelector('#addressForm');
				const params = new URLSearchParams();
				
				if(addressForm.style.display ==='none'){
					params.append('useSaveAddress','true');
				}else{
					params.append('useSaveAddress','false');
				}
					new FormData(formElement).forEach((value, key) => {
				        params.append(key, value);
				    });
				
				params.append('productId', product.id);
                
                fetch('/purchase', {
                    method: 'POST',
                    headers: {
        				'Content-Type': 'application/x-www-form-urlencoded'
    				},
                    body: params.toString()
                }).then(response => {
				    if (response.redirected) {
				        window.location.href = response.url; 
				    } else {
				        return response.json();
				    }
				})
				.catch(error => console.error('Error en el envío:', error));
            });
        },
        onCancel: function (data) {
            alert("Pago cancelado");
            console.log(data);
        }
    }).render('#paypal-button-container');
};



function addressFormSave(){
    const addressDetailsElement = document.getElementById('address-details');
    const savedAddressContainer = document.getElementById('saved-address');
    const paypalButtonContainer = document.getElementById('paypal-button-container');
    const formInputs = document.querySelectorAll('input[required], select[required]');
    const radioOption1 = document.getElementById('flexRadioDefault1');
    const radioOption2 = document.getElementById('flexRadioDefault2');
    const radioLocalPickup = document.getElementById('flexRadioDefault3');
    const modifyAddressBtn = document.getElementById('modify-address-btn');
    const useSaveAddressBtn = document.getElementById('use-address-btn');
    const useSaveAddressConteiner = document.getElementById('use-address-container');
    const addressForm = document.querySelector('#addressForm');
    
    function checkFormValidity() {
        let allValid = true;
        formInputs.forEach(input => {
            if (!input.value) {
                allValid = false;
            }
        });

        if (allValid || (radioLocalPickup && radioLocalPickup.checked) || savedAddressContainer.style.display !== 'none') {
            paypalButtonContainer.style.display = 'block';
        } else {
            paypalButtonContainer.style.display = 'none';
        }
    }
	    
	fetch('/address/user')
	.then(response => {
		if (response.ok) {
		            return response.text().then(text => text ? JSON.parse(text) : null);
		        } else {
		            throw new Error('Error inesperado');
				}
		})
	.then(address =>{
	function radioButtonsCheck() {
	    const radioInputs = document.querySelectorAll('input[name="flexRadioDefault"]');
	    const addressForm = document.querySelector('#addressForm');
		const savedAddressContainer = document.getElementById('saved-address');
		const useSaveAddressConteiner = document.getElementById('use-address-container');
		
	    function handleRadioChange() {
	        if ( !(document.getElementById("flexRadioDefault3").checked) && savedAddressContainer.style.display === 'none') {
	            addressForm.style.display = "block";
	            if(address){
	            	useSaveAddressConteiner.style.display = 'block';
				}
	        } else {
	            addressForm.style.display = "none";
	            useSaveAddressConteiner.style.display = 'none';
	        }
	    }
	
	    radioInputs.forEach(radio => {
	        radio.addEventListener('change', handleRadioChange);
	    });
	
	    handleRadioChange();
	}
	    if (address) {
	        addressDetailsElement.innerHTML = `
	            ${address.phoneNumber}<br>
	            ${address.firstDirection} ${address.secondDirection}<br>
	            ${address.city}, ${address.houseNumber}<br>
	            Código postal: ${address.postalCode}
	        `;
	        savedAddressContainer.style.display = 'block';
	        addressForm.style.display = 'none';
	    }
	
	    formInputs.forEach(input => {
	        input.addEventListener('input', checkFormValidity);
	    });
	    
	    [radioLocalPickup, radioOption1, radioOption2].forEach(radio => {
	        if (radio) {
	            radio.addEventListener('change', checkFormValidity);
	        }
	    });
	    useSaveAddressBtn.addEventListener('click',function(){
			savedAddressContainer.style.display = 'block';
	        addressForm.style.display = 'none';
	        useSaveAddressConteiner.style.display = 'none';
		    checkFormValidity();
		})
		
	    modifyAddressBtn.addEventListener('click', function() {
	        savedAddressContainer.style.display = 'none';
	        if(!radioLocalPickup.checked){
		        addressForm.style.display = 'block';
		        useSaveAddressConteiner.style.display = 'block';
			}
		    checkFormValidity();
	    });
	    checkFormValidity();
	    radioButtonsCheck();
	}).catch(error => console.error('Error fetching address details:', error));
}