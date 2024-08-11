document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

	function renderProductDetails(productId){
		fetch(`/online-store.web/getProduct?id=${encodeURIComponent(productId)}`)
            .then(response => response.json())
            .then(product => {
                if (product) {
                    document.getElementById('product-name').innerText = product.productName;
                    document.getElementById('product-price').innerText = `${product.price} $`;
                    document.getElementById('product-description').innerText = product.description;
                    document.getElementById('product-img').src = `images/${product.imgName}`;
                    document.getElementById('product-img').alt = product.productName;
                } else {
                    document.getElementById('product-name').innerText = 'Producto no encontrado';
                }
            })
            .catch(error => console.error('Error fetching product:', error));
	}
	function renderBuyButton(productId){
		fetch(`/online-store.web/getUserName`)
            .then(response => response.json())
            .then(data => {
                if (data.userName != null) {
                    document.getElementById("payment-button-container").innerHTML=`
                    <button id="buyButton" class="btn btn-primary">Comprar</button>`;
                    
                    const realButton = document.getElementById("buyButton");
                    realButton.addEventListener('click', function() {
            			window.location.href = `checkout.html?id=${encodeURIComponent(productId)}`;
        			});
				}
				else{
					document.getElementById("payment-button-container").innerHTML=`
                    <button id="buyButton" class="btn btn-primary">Iniciar sesion</button>`;
                    
                    const realButton = document.getElementById("buyButton");
                    realButton.addEventListener('click', function() {
            			window.location.href = `sign-in.html`;
        			});
				}
			})
            .catch(error => console.error('Error:', error));
	}
		
    if (productId) {
        renderProductDetails(productId);
        renderBuyButton(productId);
    } else {
        document.getElementById('product-name').innerText = 'Producto no especificado';
    }
});