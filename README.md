# Online Store - Backend

This project is the backend of an online store built with **Java**, **Spring Boot**, and a decoupled modular architecture. The main focus is on layer separation and secure data handling, as well as providing a complete and flexible administrative experience.

## Table of Contents
- [Features](#features)
- [Project Architecture](#project-architecture)
- [Technologies Used](#technologies-used)
- [Screenshots and Detailed Explanation of the Project](#screenshots-and-detailed-explanation-of-the-project)

## Features

- **Authentication and Authorization**:  
  - Login with two roles: **client** and **admin**, using **Spring Security**.
  - Passwords encrypted with **BCrypt**.
  
- **Purchase Flow**:  
  - **PayPal** integration to process payments.
  - Accessible purchase history for the client.
  - Automatic address saving upon purchase, so the client does not need to re-enter it each time.
  
- **Dynamic Search Bar**:  
  - Real-time product suggestions as you type, querying the database directly.
  
- **Administration Panel**:  
  - View all purchases made by customers.
  - Clicking on each purchase displays the associated shipping address.
  - Modify purchase statuses (e.g., pending, shipped, completed, canceled).
  - Remove purchases from the history.
  - Full product CRUD: create, update, and delete products.
  
## Project Architecture

The project is divided into 3 main modules:

1. **Persistence**:  
   - Contains **DTO** and **DAO** classes, implementing a design pattern that separates data access logic.
   - Manages communication with the database and entity mapping.
   
2. **Core**:  
   - Includes **Facade** classes that serve as intermediaries between the **Persistence** and **Web** modules.
   - Concentrates the core business logic.
   
3. **Web**:  
   - Contains controllers and REST endpoints built with **Spring Boot Web**.
   - Exposes functionality to the client and handles HTTP requests.
   
## Technologies Used

- **Java 17+**
- **Spring Boot (Security, Web, JDBC)**
- **BCrypt** for password encryption
- **Database**: MySQL
- **PayPal Integration**: Official PayPal API
- **Maven** for dependency management
- **Jakarta** for handling HTTP sessions and identifying different clients over the web.

## Screenshots and Detailed Explanation of the Project

**Main page with featured categories, header, and footer.**  
![opera_KqAPnXSnyB](https://github.com/user-attachments/assets/e3802b7f-240c-45dd-b26b-c2edfecabc57)

**User login and registration.**  
![opera_FOJ3K2OrqQ](https://github.com/user-attachments/assets/2038b9a1-c1ac-430a-9ae2-68b64b6dbdf7)![opera_SoOaeLvLSM](https://github.com/user-attachments/assets/6b19d59d-1e46-47aa-901a-6f540ea58555)

**Once logged in as a client, you can access the user panel by clicking on the user welcome message in the header.**  
![opera_5gZFNunX8p](https://github.com/user-attachments/assets/83313e8d-51ee-42a0-9a6b-5ae5c907756c)

**On the purchases page, you can view the purchase history along with their respective statuses.**  
![opera_d4v4910pDu](https://github.com/user-attachments/assets/9495c9c2-531b-4dd4-88e6-94bfbd3a741b)

**On the profile page, clients can modify their data, including the address if it has been saved.**  
![opera_mpNYosQp5T](https://github.com/user-attachments/assets/de46df2b-5617-4761-8a94-c66b744573dc)
![opera_qpz5Bxh6Su](https://github.com/user-attachments/assets/ef8d285f-af33-42b3-9a20-ff31661f2d3d)

**If you log in with an admin role, you are redirected to an administration panel.**  
![opera_2QjLf3cOdo](https://github.com/user-attachments/assets/a7827285-d69e-4410-affe-7c50451c312a)

**On the purchases page as an admin, you can see all user purchases, view their addresses, modify their statuses, and delete purchases.**  
![opera_blXVKpTDkF](https://github.com/user-attachments/assets/14cab819-4119-4437-8ab2-a50623071291)

**On the product management page, you can modify product features, as well as delete or add new products.**  
![opera_MNfzEO9YkD](https://github.com/user-attachments/assets/24ed7cca-53b3-43e0-a135-b2375306ef76)

**Search bar with product suggestions.**  
![opera_w7VqV58u2p](https://github.com/user-attachments/assets/7f55a073-336d-4333-8127-638f221dbefd)

**Specific category pages.**  
![opera_FfYPlCfjYv](https://github.com/user-attachments/assets/3d1fe3ab-d9c7-4125-87e9-f2063f339707)

**Product page: a user session is required to make a purchase.**  
![opera_wjdcAiZhBw](https://github.com/user-attachments/assets/77cbee51-9018-43fe-ab77-6077bab05364)

**Checkout page, with logic ensuring that all required address data must be provided before completing the purchase.**  
![opera_XJs33T2qem](https://github.com/user-attachments/assets/0d671306-0b5d-4af9-bd3a-2a1cf8753621)![opera_Gegf3vGZ46](https://github.com/user-attachments/assets/8d87da25-4b02-4fc4-aa6b-c669288ed458)
