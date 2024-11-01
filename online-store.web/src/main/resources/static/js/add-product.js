document.getElementById('product-form').addEventListener('submit', function(event) {
        event.preventDefault(); // Evita que el formulario se envíe de forma tradicional
        
        const formData = new FormData(this);
        
        // Puedes enviar los datos al servidor usando fetch
        fetch('/product/add', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            alert('Producto agregado con éxito');
            // Opcionalmente, puedes redirigir o limpiar el formulario
            this.reset();
        })
        .catch(error => {
            console.error('Error al agregar el producto:', error);
        });
    });