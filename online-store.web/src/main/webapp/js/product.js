document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    if (productId) {
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
    } else {
        document.getElementById('product-name').innerText = 'Producto no especificado';
    }
});