document.addEventListener("DOMContentLoaded", function() {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('id');
    
    if (productId) {
        fetch(`/online-store.web/getProduct?id=${encodeURIComponent(productId)}`)
            .then(response => response.json())
            .then(product => {
                document.getElementById('product-details').innerHTML = `
            <div class="me-3 position-relative">
            	<img src="images/${product.imgName}" style="height: 96px; width: 96x;" class="img-sm rounded border" />
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
            radioButtonsCheck();
            })
            .catch(error => console.error('Error fetching product details:', error));
    }
});

function radioButtonsCheck() {
    const radioInputs = document.querySelectorAll('input[name="flexRadioDefault"]');
    const addressForm = document.querySelector('#addressForm');

    function handleRadioChange() {
        if (document.getElementById("flexRadioDefault3").checked) {
            addressForm.style.display = "none";
        } else {
            addressForm.style.display = "block";
        }
    }

    radioInputs.forEach(radio => {
        radio.addEventListener('change', handleRadioChange);
    });

    handleRadioChange();
};

function paypalButton(product) {
    const paypalButtonContainer = document.getElementById('paypal-button-container');
    const formInputs = document.querySelectorAll('input[required], select[required]');
    const radioOption1 = document.getElementById('flexRadioDefault1');
    const radioOption2 = document.getElementById('flexRadioDefault2');
    const radioLocalPickup = document.getElementById('flexRadioDefault3');
    const savedAddressContainer = document.getElementById('saved-address');
    const checkoutFormContainer = document.getElementById('checkoutForm');
    const modifyAddressBtn = document.getElementById('modify-address-btn');
    
    function checkFormValidity() {
        let allValid = true;
        formInputs.forEach(input => {
            if (!input.value) {
                allValid = false;
            }
        });

        if (allValid || (radioLocalPickup && radioLocalPickup.checked) || checkoutFormContainer.style.display === 'none') {
            paypalButtonContainer.style.display = 'block';
        } else {
            paypalButtonContainer.style.display = 'none';
        }
    }

	modifyAddressBtn.addEventListener('click',checkFormValidity);

    formInputs.forEach(input => {
        input.addEventListener('input', checkFormValidity);
    });
    
    [radioLocalPickup, radioOption1, radioOption2].forEach(radio => {
        if (radio) {
            radio.addEventListener('change', checkFormValidity);
        }
    });

    checkFormValidity();
    
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
				const params = new URLSearchParams(new FormData(formElement));
				
				params.append('productId', product.id);
                
                fetch('/online-store.web/addPurchase', {
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
	const savedAddress = {
        phone: "+569 0000 0000",
        address1: "Calle Falsa 123",
        address2: "Depto 4B",
        city: "Santiago",
        houseNumber: "45",
        postalCode: "7550000"
    };

    const addressDetailsElement = document.getElementById('address-details');
    const savedAddressContainer = document.getElementById('saved-address');
    const checkoutFormContainer = document.getElementById('checkoutForm');
    const modifyAddressBtn = document.getElementById('modify-address-btn');

    if (savedAddress) {
        addressDetailsElement.innerHTML = `
            ${savedAddress.phone}<br>
            ${savedAddress.address1} ${savedAddress.address2}<br>
            ${savedAddress.city}, ${savedAddress.houseNumber}<br>
            Código postal: ${savedAddress.postalCode}
        `;
        savedAddressContainer.style.display = 'block';
        checkoutFormContainer.style.display = 'none';
    }

    modifyAddressBtn.addEventListener('click', function() {
        savedAddressContainer.style.display = 'none';
        checkoutFormContainer.style.display = 'block';
    });
}