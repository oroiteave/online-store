function updateStatus(purchaseId) {
	const statusSelect = document.getElementById(`status-${purchaseId}`);
	const newStatus = statusSelect.value;
	const params = new URLSearchParams();
	params.append("purchaseId",purchaseId);
	params.append("newStatus",newStatus);
    fetch(`/purchase`,{
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
fetch('/purchase')
.then(response => response.json())
.then(purchases => {
	function loadPurchases() {
            const tbody = document.getElementById('purchase-table-body');
            tbody.innerHTML = '';
			let i=1;
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


        loadPurchases();
}).catch(error => console.error('Error fetching purchases:', error));