# D2-backend

This project is the backend for a **Point of Sale (POS)** and inventory management system, built with **Spring Boot**. Its main purpose is to provide a robust and scalable API to handle critical business operations such as user management, product catalog, shopping carts, and transactional sales processing.

---

## 📋 Table of Contents
1. [Entity Relationship Diagram](#-entity-relationship-diagram)
2. [Use Cases](#-use-cases)
3. [Kanban Board](#-kanban-board)
4. [Key Technologies](#-key-technologies)
5. [Architecture & Structure](#-architecture--structure)
6. [Installation & Execution](#-installation--execution)
7. [API Documentation](#-api-documentation)
8. [Security & Roles](#-security--roles)

---

## 📐 Entity Relationship Diagram
The following diagram represents the data model supporting the business logic, including the relationships between Users, Products, Shopping Cart, and Sales.

![ERD](https://i.postimg.cc/WzxZHjtF/ERR-drawio.png)

---

## 🎯 Use Cases

The system is designed to support the following main business workflows:

### 1. **Sales Processing (Transactional)**
- Allows a cashier to convert an active shopping cart into a completed sale.
- Automatically validates product stock before confirming.
- Calculates total amount and change (if cash payment is used).
- Automatically links cart items to the sale history.

### 2. **Inventory Management**
- Full CRUD operations for products.
- Fast product search by name to speed up checkout.
- Real-time stock control (decreases on sale, increases on cancellation).

### 3. **Shopping Cart Management**
- Temporary persistence of items before finishing a sale.
- Ability to add, update quantities, or remove cart items.
- View historical items from past transactions (Pagination supported).

### 4. **Administration & Security**
- Role-based access: `ADMIN` (full access), `MANAGER` (supervision), `CASHIER` (operational).
- Sale cancellation (restricted to same-day sales).
- Delete or edit past sales (ADMIN only).

---

## 📊 Kanban Board

Development progress and pending tasks were tracked using the following board:

![Kanban Board](https://i.postimg.cc/59hWtqj8/image-2025-11-15-153321363.png)

---

## 🚀 Key Technologies

- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Database:** H2 (In-memory for development/testing)
- **Persistence:** Spring Data JPA
- **Dependency Management:** Maven
- **Tools:** Lombok, Swagger/OpenAPI

---

## 📂 Architecture & Structure

The project follows a clean layered architecture to ensure maintainability:

```
src/main/java/com/events_cav/events_venues
├── config/                                 → Project settings (Swagger, security, etc.)      
│   └── OpenApiConfig.java
├── controllers/                             → REST controllers (handle HTTP requests)
│   ├── CategoryController.java
│   ├── ProductController.java
│   ├── SaleController.java
│   ├── ShoppingController.java
│   ├── SupplierController.java
│   └── UserController.java
├── dtos/                                    → Data transfer objects (inputs/outputs)
│   ├── category/
│   │   ├── CategoryRequest.java
│   │   └── CategoryResponseDTO.java
│   ├── products/
│   │   ├── request/
│   │   │   └── ProductCreatedDTO.java
│   │   └── responses/
│   │       ├── ProductCreateResponseDTO.java
│   │       ├── ProductGetIdResponseDTO.java
│   │       ├── ProductResponseDeleteDTO.java
│   │       ├── ProductSearchResponseDTO.java
│   │       └── ProductUpdateResponseDTO.java
│   ├── sales/
│   │   ├── requests/
│   │   │   ├── ProcessSaleRequest.java
│   │   │   └── UpdatePaymentMethod.java
│   │   └── responses/
│   │       ├── ProcessSaleResponse.java
│   │       ├── SaleItemResponse.java
│   │       └── SaleResponse.java
│   ├── shoppingCar/
│   │   ├── requests/
│   │   │   ├── AddCartItemRequest.java
│   │   │   ├── UpdateCartItemPriceRequest.java
│   │   │   └── UpdateCartItemQuantityRequest.java
│   │   └── responses/
│   │       ├── ShoppingCartItemResponse.java
│   │       └── ShoppingCar.java
│   ├── supplier/
│   │   ├── request/
│   │   │   └── SupplierCreateDTO.java
│   │   └── responses/
│   │       └── SupplierResponseDTO.java
│   └── users/
│       ├── requests/
│       │   ├── LoginDTO.java
│       │   ├── RegisterUserDTO.java
│       │   ├── UpdateUserAdminDTO.java
│       │   └── UpdateUserDTO.java
│       └── responses/
│           └── UserResponse.java
├── exception/                              → Custom exceptions
│   ├── ResourceNotFoundException.java
│   └── BadRequestException.java
├── mapper/                                 → MapStruct mappers (Entity ↔ DTO)
│   ├── EventMapper.java
│   └── VenueMapper.java
├── model/                                  → JPA entities (representing the tables)
│   ├── Event.java
│   └── Venue.java
├── repository/                             → Data access (JPA interfaces)
│   ├── impl/
│   │   ├── EventRepositoryImpl.java
│   │   └── VenueRepositoryImpl.java
│   └── interfaces/
│       ├── DataEventRepository.java
│       └── DataVenueRepository.java
└── service/                                → Business logic and validations
    ├── impl/
    │   ├── EventServiceImpl.java
    │   └── VenueServiceImpl.java
    └── interfaces/
        ├── IEventService.java
        └── IVenueService.java  
```

---

⚙️ **Instalación y Ejecución**

**Clonar el repositorio:**

``` bash
git clone [URL_DEL_REPOSITORIO]
```

**Configuración:**\
El archivo `src/main/resources/application.properties` está
pre-configurado para usar H2.

**Compilar y Ejecutar:**

``` bash
./mvnw spring-boot:run
```

**Acceso:**\
- API Base: http://localhost:8080\
- Consola H2: http://localhost:8080/h2-console

------------------------------------------------------------------------

# 🌐 **Documentación de la API**

------------------------------------------------------------------------

# 💰 **Ventas (Sales)**

  ----------------------------------------------------------------------------------------------------------------------
Método       Endpoint                              Descripción         Params / Body
  ------------ ------------------------------------- ------------------- -----------------------------------------------
**POST**     `/api/v1/sales/process`               Procesa una venta   `{ "cashMethod": "CASH", "amountPaid": 100 }`
desde el carrito    
activo.

**GET**      `/api/v1/sales`                       Listar ventas       `?search=txt&page=0&size=10`
(Paginado y         
Filtrado).

**GET**      `/api/v1/sales/{id}`                  Ver detalle de      \-
venta (Valida       
roles).

**PATCH**    `/api/v1/sales/{id}/cancel`           Cancelar venta      \-
(Solo mismo día).

**PATCH**    `/api/v1/sales/{id}/payment-method`   Actualizar método   `{ "newPaymentMethod": "CARD" }`
de pago.

**DELETE**   `/api/v1/sales/{id}`                  Eliminar venta      \-
(Solo ADMIN).
  ----------------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------

# 🛒 **Carrito de Compras (Shopping Car)**

  -----------------------------------------------------------------------------------------
Método             Endpoint                                 Descripción
  ------------------ ---------------------------------------- -----------------------------
**POST**           `/api/v1/shopping-car/{cashierId}/add`   Agregar item al carrito.

**PATCH**          `/api/v1/shopping-car/item/{id}/qty`     Actualizar cantidad de un
item.

**DELETE**         `/api/v1/shopping-car/item/{id}`         Eliminar item del carrito.

**GET**            `/api/v1/shopping-car/sale/{saleId}`     Ver items de venta pasada
(Paginado).
  -----------------------------------------------------------------------------------------

------------------------------------------------------------------------

# 🏷️ **Productos** y 👥 **Usuarios**

  ----------------------------------------------------------------------------
Método             Endpoint                    Descripción
  ------------------ --------------------------- -----------------------------
**GET**            `/api/v1/products`          Obtener todos los productos.

**GET**            `/api/v1/products/search`   Buscar productos por nombre.

**POST**           `/api/v1/users/login`       Iniciar sesión (Genera
token/acceso).
  ----------------------------------------------------------------------------