# Tienda Online - Backend

Este proyecto es el backend de una tienda online construida con **Java**, **Spring Boot** y una arquitectura de módulos desacoplados. El enfoque principal es la separación de capas y el manejo seguro de datos, además de ofrecer una experiencia administrativa completa y flexible.

## Características

- **Autenticación y autorización**:  
  - Login con dos roles: **cliente** y **admin**, utilizando **Spring Security**.
  - Contraseñas encriptadas con **BCrypt**.
  
- **Flujo de compra**:  
  - Integración con **PayPal** para procesar pagos.
  - Historial de compras accesible para el cliente.
  - Guardado automático de dirección al comprar, de forma que el cliente no tenga que ingresarla en cada compra.
  
- **Barra de búsqueda dinámica**:  
  - Al teclear, se van mostrando sugerencias de productos en tiempo real, consultando directamente la base de datos.
  
- **Panel de administración**:  
  - Visualización de todas las compras realizadas por los clientes.
  - Al hacer clic en cada compra, se despliega una pestaña con la dirección asociada a dicha compra.
  - Modificación de estados de las compras (por ejemplo: pendiente, enviado, completado, cancelado).
  - Eliminación de compras del historial.
  - CRUD completo de productos: creación, modificación, eliminación.
  
## Arquitectura del proyecto

El proyecto está dividido en 3 módulos principales:

1. **Persistence**:  
   - Contiene las clases **DTO** y **DAO**, implementando el patrón de diseño que separa la lógica de acceso a datos de la aplicación.
   - Gestiona la comunicación con la base de datos y el mapeo de entidades.
   
2. **Core**:  
   - Incluye las clases **Facade**, que funcionan como capa intermedia entre el módulo **Persistence** y el módulo **Web**.
   - Aquí se concentra la lógica de negocio pura.
   
3. **Web**:  
   - Contiene los controladores y endpoints REST creados con **Spring Boot Web**.
   - Expone las funcionalidades al cliente (frontend) y recibe las peticiones HTTP.
   
## Tecnologías utilizadas

- **Java 17+**
- **Spring Boot (Security, Web, Data JPA)**
- **BCrypt** para encriptación de contraseñas
- **Base de datos**: MySQL
- **Integración con PayPal**: API oficial de PayPal
- **Maven** para la gestión de dependencias

