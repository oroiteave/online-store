document.addEventListener("DOMContentLoaded", function() {
    const urlParams = new URLSearchParams(window.location.search);
    const categoryId = urlParams.get('id');
    let currentPage = 1;

    function fetchProductsByCategory(page) {
        fetch(`/online-store.web/getproductsbycategory?id=${encodeURIComponent(categoryId)}&page=${page}`)
            .then(response => response.json())
            .then(products => {
                renderProducts(products);
                renderPagination(page, 2);
            })
            .catch(error => console.error('Error fetching products:', error));
    }

    function renderProducts(products) {
        const productsContainer = document.getElementById('products-container');
        productsContainer.innerHTML = products.data.map(product => `
            <div class="col-md-4">
                <a href="product.html?id=${product.id}" class="text-decoration-none text-dark">
                    <div class="card mb-4 product-card">
                        <div class="image-container">
                            <img src="images/${product.imgName}" alt="${product.productName}">
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">${product.productName}</h5>
                            <p class="card-text">${product.description}</p>
                            <p class="card-text">${product.price} $</p>
                        </div>
                    </div>
                </a>
            </div>
        `).join('');
    }
    function renderPagination(currentPage, totalPages) {
        const pagination = document.getElementById('pagination');
        pagination.innerHTML = '';

        // Previous button
        const prevButton = document.createElement('li');
        prevButton.classList.add('page-item');
        if (currentPage === 1) {
            prevButton.classList.add('disabled');
        }
        prevButton.innerHTML = `
            <a class="page-link" href="#" aria-label="Previous" data-page="${currentPage - 1}">
                <span aria-hidden="true">&laquo;</span>
            </a>
        `;
        pagination.appendChild(prevButton);

        // Page numbers
        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('li');
            pageButton.classList.add('page-item');
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.innerHTML = `
                <a class="page-link" href="#" data-page="${i}">${i}</a>
            `;
            pagination.appendChild(pageButton);
        }

        // Next button
        const nextButton = document.createElement('li');
        nextButton.classList.add('page-item');
        if (currentPage === totalPages) {
            nextButton.classList.add('disabled');
        }
        nextButton.innerHTML = `
            <a class="page-link" href="#" aria-label="Next" data-page="${currentPage + 1}">
                <span aria-hidden="true">&raquo;</span>
            </a>
        `;
        pagination.appendChild(nextButton);

        // Add event listeners to pagination links
        const pageLinks = pagination.querySelectorAll('a.page-link');
        pageLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                event.preventDefault();
                const page = parseInt(this.getAttribute('data-page'));
                if (page > 0 && page <= totalPages) {
                    fetchProductsByCategory(page);
                }
            });
        });
    }
    
    fetchProductsByCategory(currentPage);
});