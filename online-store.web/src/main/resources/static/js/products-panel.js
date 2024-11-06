let currentPage = 1;
    let totalPages = 1;

    // Cargar los productos al cargar la página
    document.addEventListener('DOMContentLoaded', () => {
        loadProducts(currentPage);
    });

    function loadProducts(page) {
        fetch(`/product/products?page=${page}`)
            .then(response => response.json())
            .then(data => {
                const products = data.products;
                totalPages = data.numberOfPages;
                renderProducts(products);
                renderPagination();
            })
            .catch(error => console.error('Error al obtener los productos:', error));
    }

    function renderProducts(products) {
        const tbody = document.getElementById('products-table-body');
        tbody.innerHTML = ''; // Limpiar la tabla antes de renderizar

        products.forEach(product => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td class="id-column">${product.id}</td>
                <td class="image-column"><img src="/product-images/${product.imgName}" alt="${product.productName}" class="product-image"></td>
                <td class="name-column"><input type="text" class="form-control" value="${product.productName}" id="name-${product.id}"></td>
                <td class ="price-column"><input type="number" class="form-control" value="${product.price}" id="price-${product.id}" min="0" step="0.01"></td>
                <td class="description-column"><textarea class="form-control" id="description-${product.id}" rows="2">${product.description}</textarea></td>
                <td class="category-column">
                    <select class="form-control" id="category-${product.id}">
                        <!-- Las categorías se cargarán dinámicamente -->
                    </select>
                </td>
                <td class="stock-column"><input type="number" class="form-control" value="${product.stock}" id="stock-${product.id}" min="0"></td>
                <td class="actions-column">
                    <button class="btn btn-success btn-sm" onclick="updateProduct(${product.id})">Actualizar</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteProduct(${product.id})">Eliminar</button>
                </td>
            `;

            tbody.appendChild(row);

            // Cargar las categorías en el select
            loadCategories(product.id, product.categoryName);
        });
    }

    function loadCategories(productId, selectedCategory) {
        // Supongamos que tienes un endpoint para obtener las categorías
        fetch('/category')
            .then(response => response.json())
            .then(categories => {
                const select = document.getElementById(`category-${productId}`);
                select.innerHTML = ''; // Limpiar el select

                categories.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.id;
                    option.textContent = category.categoryName;

                    if (category.id === selectedCategory) {
                        option.selected = true;
                    }

                    select.appendChild(option);
                });
            })
            .catch(error => console.error('Error al cargar las categorías:', error));
    }

    function updateProduct(productId) {
        const name = document.getElementById(`name-${productId}`).value;
        const price = parseFloat(document.getElementById(`price-${productId}`).value);
        const description = document.getElementById(`description-${productId}`).value;
        const category = document.getElementById(`category-${productId}`).value;
        const stock = parseInt(document.getElementById(`stock-${productId}`).value);

        const updatedProduct = {
            id: productId,
            productName: name,
            price: price,
            description: description,
            categoryName: category,
            stock: stock
        };

        fetch(`/product/update`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedProduct)
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
            loadProducts(currentPage);
        })
        .catch(error => console.error('Error al actualizar el producto:', error));
    }

    function deleteProduct(productId) {
        if (confirm('¿Estás seguro de que deseas eliminar este producto?')) {
            fetch(`/product/delete?id=${productId}`, {
                method: 'DELETE',
            })
            .then(response => response.text())
            .then(data => {
                alert(data);
                loadProducts(currentPage);
            })
            .catch(error => console.error('Error al eliminar el producto:', error));
        }
    }

    function renderPagination() {
        const pagination = document.querySelector('.pagination');
        const prevPage = document.getElementById('prev-page');
        const nextPage = document.getElementById('next-page');

        // Limpiar números de página existentes
        pagination.querySelectorAll('.page-number').forEach(pageItem => pageItem.remove());

        // Deshabilitar botones si es necesario
        prevPage.classList.toggle('disabled', currentPage === 1);
        nextPage.classList.toggle('disabled', currentPage === totalPages);

        // Añadir números de página
        for (let i = 1; i <= totalPages; i++) {
            const pageItem = document.createElement('li');
            pageItem.classList.add('page-item', 'page-number');
            if (i === currentPage) {
                pageItem.classList.add('active');
            }

            const pageLink = document.createElement('a');
            pageLink.classList.add('page-link');
            pageLink.href = '#';
            pageLink.textContent = i;

            pageLink.addEventListener('click', (e) => {
                e.preventDefault();
                currentPage = i;
                loadProducts(currentPage);
            });

            pageItem.appendChild(pageLink);
            nextPage.before(pageItem);
        }

        // Eventos para botones anterior y siguiente
        prevPage.onclick =(e) => {
            e.preventDefault();
            if (currentPage > 1) {
                currentPage--;
                loadProducts(currentPage);
            }
        };

        nextPage.onclick =(e) => {
            e.preventDefault();
            if (currentPage < totalPages) {
                currentPage++;
                loadProducts(currentPage);
            }
        };
    }