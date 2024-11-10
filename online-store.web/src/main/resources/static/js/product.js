document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

	function renderProductDetails(productId){
		fetch(`/product?id=${encodeURIComponent(productId)}`)
            .then(response => response.json())
            .then(product => {
                if (product) {
                    document.getElementById('product-name').innerText = product.productName;
                    document.getElementById('product-price').innerText = `${product.price} $`;
                    document.getElementById('product-description').innerText = product.description;
                    document.getElementById('product-img').src = `/product-images/${product.imgName}`;
                    document.getElementById('product-img').alt = product.productName;
                    document.getElementById('product-stock').innerText = (product.stock > 0) ?  "stock: " + product.stock : "No hay stock del producto";
                    if(product.stock > 0){
	                    renderBuyButton(productId);
					}
                } else {
                    document.getElementById('product-name').innerText = 'Producto no encontrado';
                }
            })
            .catch(error => console.error('Error fetching product:', error));
	}
	function renderBuyButton(productId){
		fetch(`/user/current`)
            .then(response => {
				if (response.ok) {
		            return response.text().then(text => text ? JSON.parse(text) : null);
		        } else {
		            throw new Error('Error inesperado');
		        }})
            .then(data => {
                if (data != null) {
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
    } else {
        document.getElementById('product-name').innerText = 'Producto no especificado';
    }
});