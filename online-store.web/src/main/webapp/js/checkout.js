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
            		
            //sb-vlqft27291221@personal.example.com
            //'TI5>vpK
            paypal.Buttons({
    	  style: {
    		    layout: 'vertical',
    		    color:  'blue',
    		    shape:  'rect',
    		    label:  'paypal'
    		  },
    		  createOrder: function(data,actions){
				return actions.order.create({
					purchase_units: [{
						description: product.productName,
						amount:{
							value: product.price
						}
					}]
				})
			  },
				onApprove: function(data,actions){
					actions.order.capture()
						.then(function (details){
							const form = document.createElement('form');
				            form.method = 'POST';
				            form.action = '/online-store.web/addPurchase';
				
				            const input = document.createElement('input');
				            input.type = 'hidden';
				            input.name = 'productId';
				            input.value = productId;
				
				            form.appendChild(input);
				            document.body.appendChild(form);
				
				            form.submit();
					});
				},
				onCancel: function(data){
					alert("Pago cancelado");
					console.log(data);
				}
    		}).render('#paypal-button-container');		
            })
            .catch(error => console.error('Error fetching product details:', error));
    }
});

document.addEventListener("DOMContentLoaded", function() {
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
});