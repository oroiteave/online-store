let currentPage = 1;
function fetchPurchases(currentPage){
	fetch(`/purchase/pages?page=${encodeURIComponent(currentPage)}`)
	.then(response => response.json())
	.then(data => {
	    loadPurchases(data.purchases);
	    renderPagination(currentPage, data.numberOfPages);
	}).catch(error => console.error('Error fetching purchases:', error));
}

function updateStatus(purchaseId) {
	const statusSelect = document.getElementById(`status-${purchaseId}`);
	const newStatus = statusSelect.value;
	const params = new URLSearchParams();
	params.append("purchaseId",purchaseId);
	params.append("newStatus",newStatus);
    fetch('/purchase',{
		method: 'PUT',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
        body: params.toString()
	})
	.then(response => response.text())
	.then(data => {
		alert(data);
	})
    
}

function loadPurchases(purchases) {
    const tbody = document.getElementById('purchase-table-body');
    tbody.innerHTML = '';
    for (const purchase of purchases) {
		fetch(`/user?id=${encodeURIComponent(purchase.customerId)}`)
		.then(response => response.json())
		.then(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${purchase.id}</td>
                <td>${user.email}</td>
                <td>${purchase.products[0].productName}</td>
                <td>${purchase.shippingCompany}</td>
                <td>
                    <select class="form-select" id="status-${purchase.id}" name="status")">
                        <option value="CONFIRMADO" ${purchase.status === 'CONFIRMADO' ? 'selected' : ''}>Confirmado</option>
                        <option value="PREPARANDO" ${purchase.status === 'PREPARANDO' ? 'selected' : ''}>Preparando</option>
                        <option value="ENVIADO" ${purchase.status === 'ENVIADO' ? 'selected' : ''}>Enviado</option>
                        <option value="ENTREGADO" ${purchase.status === 'ENTREGADO' ? 'selected' : ''}>Entregado</option>
                    </select>
                </td>
                <td><button class="btn btn-sm btn-primary" onClick = "updateStatus(${purchase.id}, status.value)">Actualizar</button></td>
            `;
            tbody.appendChild(row);
		})
		.catch(error => console.error('Error fetching user data:', error));
    }
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
                    fetchPurchases(page);
                }
            });
        });
    }
fetchPurchases(currentPage);    
