# 🍽️ Restaurant Backend

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Backend-brightgreen?logo=springboot) ![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql) ![Postman](https://img.shields.io/badge/Postman-API%20Testing-orange?logo=postman)

Backend REST API for a restaurant/food ordering application built using
Java, Spring Boot, and MySQL.

## 📖 Description

This project provides the backend services for a restaurant
food-ordering application.

It handles user authentication, food management, cart management,
vouchers, orders, and payments through RESTful APIs.

The application follows a layered architecture using Controller,
Service, Repository, and Entity layers.

## 🛠️ Skills & Technologies Used

-   Java 21
-   Spring Boot
-   Spring MVC
-   RESTful Web Services
-   Spring Data JPA
-   Hibernate
-   MySQL
-   Spring Security
-   JWT Authentication
-   Maven
-   Bean Validation
-   Exception Handling
-   Postman
-   Git & GitHub

## ✨ Features

-   User registration and login
-   JWT-based authentication
-   User management
-   Food item management
-   Add, update, and remove cart items
-   Voucher management and validation
-   Order creation and order management
-   Order item management
-   Payment processing
-   Order and payment status management
-   Global exception handling
-   Input validation

## 🏗️ Architecture

The application follows a layered architecture:

``` text
Client / Frontend
       |
       ↓
Controller Layer
       |
       ↓
Service Layer
       |
       ↓
Repository Layer
       |
       ↓
MySQL Database
```

## 🗄️ Database

MySQL is used as the relational database for storing application data.

### Main Entities

-   **User** -- Stores user account and authentication details
-   **Food** -- Stores food item information
-   **CartItem** -- Stores items added to the user's cart
-   **Order** -- Stores customer order details
-   **OrderItem** -- Stores individual items belonging to an order
-   **Voucher** -- Stores discount voucher information
-   **Payment** -- Stores payment details and payment status

Spring Data JPA and Hibernate are used for object-relational mapping
between Java entities and MySQL tables.

## 🔗 API Endpoints

### 🔐 Authentication

  Method   Endpoint               Description
  -------- ---------------------- ------------------------------------
  POST     `/api/auth/register`   Register a new user
  POST     `/api/auth/login`      Authenticate user and generate JWT

### 🍔 Food

  Method   Endpoint            Description
  -------- ------------------- ---------------------
  GET      `/api/foods`        Get all food items
  GET      `/api/foods/{id}`   Get food item by ID
  POST     `/api/foods`        Add a new food item
  PUT      `/api/foods/{id}`   Update a food item
  DELETE   `/api/foods/{id}`   Delete a food item

### 🛒 Cart

  Method   Endpoint           Description
  -------- ------------------ -----------------------
  GET      `/api/cart`        Get cart items
  POST     `/api/cart`        Add item to cart
  PUT      `/api/cart/{id}`   Update cart item
  DELETE   `/api/cart/{id}`   Remove item from cart

### 📦 Orders

  Method   Endpoint             Description
  -------- -------------------- --------------------
  POST     `/api/orders`        Create a new order
  GET      `/api/orders`        Get orders
  GET      `/api/orders/{id}`   Get order by ID

### 🎟️ Vouchers

  Method   Endpoint               Description
  -------- ---------------------- ------------------------
  GET      `/api/vouchers`        Get available vouchers
  POST     `/api/vouchers`        Create a voucher
  DELETE   `/api/vouchers/{id}`   Delete a voucher

### 💳 Payments

  Method   Endpoint               Description
  -------- ---------------------- ---------------------
  POST     `/api/payments`        Process a payment
  GET      `/api/payments/{id}`   Get payment details

### 🧪 API Testing

Postman was used to test and verify the REST API endpoints.

The APIs were tested for:

-   User registration and login
-   JWT authentication
-   Food management
-   Cart management
-   Voucher operations
-   Order creation and retrieval
-   Payment operations
-   Request validation
-   Exception handling

## 🚀 How to Run

### 📋 Prerequisites

-   Java 21
-   Maven
-   MySQL

### 1️⃣ Clone the repository

``` bash
git clone https://github.com/Meganath78/Restaurant_backend.git
cd Restaurant_backend
```

### 2️⃣ Create the MySQL database

Open MySQL Workbench and execute:

### 3️⃣ Configure database connection

Open:

``` text
src/main/resources/application.properties
```


### 4️⃣ Run the application

Using Maven:

``` bash
mvn spring-boot:run
```

Or on Windows:

``` powershell
.\mvnw.cmd spring-boot:run
```

The application will start on:

``` text
http://localhost:8080
```

### 5️⃣ Test the APIs

Use Postman to send requests to the REST endpoints.

Example:

``` text
POST http://localhost:8080/api/auth/register
```

## 👨‍💻 Author

**Meganath**

GitHub: https://github.com/Meganath78
