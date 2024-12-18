# Tienda Online - Backend

Este proyecto es el backend de una tienda online construida con **Java**, **Spring Boot** y una arquitectura de módulos desacoplados. El enfoque principal es la separación de capas y el manejo seguro de datos, además de ofrecer una experiencia administrativa completa y flexible.

## Índice
- [Características](#características)

- [Arquitectura del proyecto](#arquitectura-del-proyecto)

- [Tecnologías utilizadas](#tecnologías-utilizadas)

- [Capturas y explicacion extensa del proyecto](#capturas-y-explicación-detallada-del-proyecto)

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
   - Expone las funcionalidades al cliente y recibe las peticiones HTTP.
   
## Tecnologías utilizadas

- **Java 17+**
- **Spring Boot (Security, Web, JDBC)**
- **BCrypt** para encriptación de contraseñas
- **Base de datos**: MySQL
- **Integración con PayPal**: API oficial de PayPal
- **Maven** para la gestión de dependencias
- **Jakarta** para manejar sesiones HTTP e identificar distintos clientes a través de la web.

## Capturas y explicación detallada del proyecto.

**Página principal con las categorías destacadas, header y footer.**

![opera_KqAPnXSnyB](https://github.com/user-attachments/assets/e3802b7f-240c-45dd-b26b-c2edfecabc57)

**Inicio de sesión y registro de usuario.**

![opera_FOJ3K2OrqQ](https://github.com/user-attachments/assets/2038b9a1-c1ac-430a-9ae2-68b64b6dbdf7)![opera_SoOaeLvLSM](https://github.com/user-attachments/assets/6b19d59d-1e46-47aa-901a-6f540ea58555)

**Al iniciar sesión como cliente, puedes entrar al panel haciendo clic en la bienvenida al usuario del header.**

![opera_5gZFNunX8p](https://github.com/user-attachments/assets/83313e8d-51ee-42a0-9a6b-5ae5c907756c)

**En la página de compras se puede ver el historial de las compras con su respectivo estado.**

![opera_d4v4910pDu](https://github.com/user-attachments/assets/9495c9c2-531b-4dd4-88e6-94bfbd3a741b)

**En la página de perfil se pueden modificar los datos del cliente, incluyendo la dirección en caso de que haya guardado su dirección.**

![opera_mpNYosQp5T](https://github.com/user-attachments/assets/de46df2b-5617-4761-8a94-c66b744573dc)
![opera_qpz5Bxh6Su](https://github.com/user-attachments/assets/ef8d285f-af33-42b3-9a20-ff31661f2d3d)

**Si inicias sesión con rol de administrador, la página redirige a un panel de administración.**

![opera_2QjLf3cOdo](https://github.com/user-attachments/assets/a7827285-d69e-4410-affe-7c50451c312a)

**En la página de compras, como administrador, se pueden ver todas las compras hechas por los usuarios, ver sus direcciones, modificar los estados y eliminar las compras.**

![opera_blXVKpTDkF](https://github.com/user-attachments/assets/14cab819-4119-4437-8ab2-a50623071291)

**En la página de administrar productos se pueden modificar las características de los productos, así como eliminarlos o agregar nuevos productos.**

![opera_MNfzEO9YkD](https://github.com/user-attachments/assets/24ed7cca-53b3-43e0-a135-b2375306ef76)

**Barra de búsqueda con sugerencias de productos.**

![opera_w7VqV58u2p](https://github.com/user-attachments/assets/7f55a073-336d-4333-8127-638f221dbefd)

**Páginas de categorías específicas.**

![opera_FfYPlCfjYv](https://github.com/user-attachments/assets/3d1fe3ab-d9c7-4125-87e9-f2063f339707)

**Página de producto: es necesario tener la sesión iniciada para comprar.**

![opera_wjdcAiZhBw](https://github.com/user-attachments/assets/77cbee51-9018-43fe-ab77-6077bab05364)

**Página de checkout, con lógica para que se pueda comprar solo si están todos los datos obligatorios de la dirección.**

![opera_XJs33T2qem](https://github.com/user-attachments/assets/0d671306-0b5d-4af9-bd3a-2a1cf8753621)![opera_Gegf3vGZ46](https://github.com/user-attachments/assets/8d87da25-4b02-4fc4-aa6b-c669288ed458)

