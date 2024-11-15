function fetchCategories() {
            fetch('/online-store/category')
                .then(response => response.json())
                .then(data => {
                    displayCategories(data);
                })
                .catch(error => console.error('Error al obtener las categorías:', error));
        }

        function displayCategories(categories) {
            const categoriesContainer = document.getElementById('categories');
            categoriesContainer.innerHTML = '';

            categories.forEach(category => {
                const categoryElement = document.createElement('div');
                categoryElement.className = 'col-md-3';
                categoryElement.innerHTML = `
                    <div class="card mb-4 shadow-sm">
                    	<a href="category.html?id=${category.id}" class="text-decoration-none text-dark">
                    	<img src="/online-store/product-images/${category.imgName}" class="card-img-top" alt="${category.categoryName}">
                        <div class="card-body">
                            <h5 class="card-title">${category.categoryName}</h5>
                        </div>
                    </div>
                `;
                categoriesContainer.appendChild(categoryElement);
            });
        }

        document.addEventListener('DOMContentLoaded', fetchCategories);
