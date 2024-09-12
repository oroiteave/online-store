let currentPage = 1;
function fetchPurchases(currentPage){
	fetch(`/purchase/pages?page=${encodeURIComponent(currentPage)}`)
	.then(response => response.json())
	.then(data => {
	    loadPurchases(data.purchases,data.userEmails);
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

function loadPurchases(purchases,userEmails) {
    const tbody = document.getElementById('purchase-table-body');
    tbody.innerHTML = '';
    let i =0;
    for (const purchase of purchases) {
            const row = document.createElement('tr');
            row.id = `purchase-row-${purchase.id}`;
            const addressRow = document.createElement('tr');
            row.innerHTML = `
                <td>${purchase.id}</td>
                <td>${userEmails[i++]}</td>
                <td>${purchase.products[0].productName}</td>
                <td>${purchase.shippingCompany}</td>
                <td>
                    <select class="form-select" id="status-${purchase.id}" name="status" onClick="event.stopPropagation()">
                        <option value="CONFIRMADO" ${purchase.status === 'CONFIRMADO' ? 'selected' : ''}>Confirmado</option>
                        <option value="PREPARANDO" ${purchase.status === 'PREPARANDO' ? 'selected' : ''}>Preparando</option>
                        <option value="ENVIADO" ${purchase.status === 'ENVIADO' ? 'selected' : ''}>Enviado</option>
                        <option value="ENTREGADO" ${purchase.status === 'ENTREGADO' ? 'selected' : ''}>Entregado</option>
                    </select>
                </td>
                <td><button class="btn btn-sm btn-primary" onClick = "event.stopPropagation();  updateStatus(${purchase.id}, status.value)">Actualizar</button></td>
                <td><button class="btn btn-sm btn-danger" onClick="event.stopPropagation(); deletePurchase(${purchase.id})">Eliminar</button></td>
            `;
            addressRow.id = `address-info-${purchase.id}`;
        	addressRow.style.display = 'none'; // Ocultar por defecto
        	addressRow.innerHTML = `
            <td colspan="6">
                <div id="address-details-${purchase.id}"></div>
            </td>
        `;
            row.addEventListener('click', () => showAddress(purchase.id));
            tbody.appendChild(row);
            tbody.appendChild(addressRow);
    }
}
function deletePurchase(purchaseId){
	fetch(`/purchase?id=${purchaseId}`,{
		method: 'DELETE',
	})
	.then(response => response.text())
	.then(data =>{
		const row = document.getElementById(`purchase-row-${purchaseId}`);
        if (row) {
            row.remove();  // Elimina la fila correspondiente al purchase
        }
		alert(data);
	})
	.catch(error => console.error('Error fetching purchase:', error));
}

function showAddress(purchaseId) {
    fetch(`/address/purchase?purchaseId=${encodeURIComponent(purchaseId)}`)
    .then(response => {
		if (response.ok) {
            return response.text().then(text => text ? JSON.parse(text) : null);
        } else {
            throw new Error('Error inesperado');
		}
	})
    .then(data => {
		if(data){
	        const addressInfoDiv = document.getElementById(`address-info-${purchaseId}`);
	        const addressDetails = document.getElementById(`address-details-${purchaseId}`);
			
			
	        addressDetails.innerHTML = `
	    <div style="display: flex; flex-direction: column; gap: 10px; margin-top: 10px;">
	        <div style="display: flex; justify-content: space-between; width: 50%;">
	            <span><strong>Dirección 1:</strong> ${data.firstDirection}</span>
	            <span><strong>Dirección 2:</strong> ${data.secondDirection || 'N/A'}</span>
	        </div>
	        <div style="display: flex; justify-content: space-between; width: 50%;">
	            <span><strong>Ciudad:</strong> ${data.city}</span>
	            <span><strong>Teléfono:</strong> ${data.phoneNumber}</span>
	        </div>
	        <div style="display: flex; justify-content: space-between; width: 50%;">
	            <span><strong>Número de casa:</strong> ${data.houseNumber}</span>
	            <span><strong>Código postal:</strong> ${data.postalCode}</span>
	        </div>
    	</div>
	        `;
	        if(addressInfoDiv.style.display === 'table-row' ){
				addressInfoDiv.style.display = 'none';
			}else{
		        addressInfoDiv.style.display = 'table-row'; 
			}
		}
    })
    .catch(error => console.error('Error fetching address:', error));
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
