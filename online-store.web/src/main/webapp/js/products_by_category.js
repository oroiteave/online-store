document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryId = urlParams.get('id');

    if (categoryId) {
        fetch(`/online-store.web/getproductsbycategory?id=${encodeURIComponent(categoryId)}`)
            .then(response => response.json())
            .then(products => {
                const productsContainer = document.getElementById('products-container');
                productsContainer.innerHTML = products.map(product => `
                    <div class="col-md-4">
                        <div class="card mb-4">
                            <img src="images/${product.imgName}" class="card-img-top" alt="${product.productName}">
                            <div class="card-body">
                                <h5 class="card-title">${product.productName}</h5>
                                <p class="card-text">${product.description}</p>
                                <p class="card-text">${product.price} $</p>
                            </div>
                        </div>
                    </div>
                `).join('');
            })
            .catch(error => console.error('Error fetching products:', error));
    } else {
        document.getElementById('category-title').innerText = 'Categoría no especificada';
    }
});