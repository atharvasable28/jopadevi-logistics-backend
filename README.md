# 🚛 Jopadevi Logistics Management System

A full-stack Logistics Management System developed for **Jopadevi Logistics** to digitize and simplify day-to-day transportation and fleet management operations.

The system provides a centralized platform for managing vehicles, drivers, trips, fuel, maintenance, billing, expenses, salaries, and vehicle EMI payments with secure authentication and a RESTful backend architecture.

---

## 🌐 Live Application

**Frontend:**  
https://jopadevi-logistics.vercel.app

**Backend API:**  
https://jopadevi-logistics-backend.onrender.com

---

## 📌 Project Overview

Jopadevi Logistics Management System was developed to replace manual logistics record-keeping with a centralized digital system.

The application allows authorized users to manage and track important logistics operations from a single dashboard.

### Key Objectives

- Digitize logistics and fleet records
- Manage vehicles and drivers efficiently
- Track trips and transportation operations
- Maintain fuel and maintenance records
- Manage company billing and payments
- Track business expenses
- Manage driver salary payments
- Track vehicle EMI and EMI payments
- Provide secure user authentication
- Deploy the application for real-world use

---

## ✨ Features

### 🔐 Authentication & Security

- User login and authentication
- JWT-based authentication
- Password encryption using BCrypt
- Protected REST APIs
- Secure authorization
- CORS configuration for frontend-backend communication

### 🚚 Vehicle Management

- Add vehicles
- View vehicle records
- Update vehicle information
- Delete vehicles
- Track vehicle-related information

### 👨‍✈️ Driver Management

- Add drivers
- View driver records
- Update driver information
- Delete drivers
- Maintain driver details

### 🛣️ Trip Management

- Create trips
- View trip records
- Update trip information
- Delete trips
- Track transportation activity

### ⛽ Fuel Management

- Record fuel expenses
- Track fuel-related information
- Maintain fuel records for operations

### 🔧 Maintenance Management

- Record vehicle maintenance
- Track maintenance expenses
- Maintain maintenance history

### 🧾 Billing Management

- Create company bills
- View billing records
- Update bills
- Delete bills
- Record bill payments
- Maintain payment history
- Generate payment receipts

### 💰 Expense Management

- Record business expenses
- Track operational expenses
- Maintain expense history

### 👨‍💼 Salary Management

- Record salary payments
- Track salary-related transactions
- Maintain payment records

### 🏦 Vehicle EMI Management

- Add vehicle EMI records
- Track EMI details
- Record EMI payments
- View EMI payment history
- Delete EMI records safely

### 🏢 Company Management

- Add companies
- View company records
- Update company information
- Delete companies

### 📄 Document Management

- Maintain logistics-related documents
- Store document references associated with operations

---

## 🛠️ Technology Stack

### Backend

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Maven**

### Frontend

- **React**
- **Vite**
- **JavaScript**
- **HTML5**
- **CSS3**

### Database

- **MySQL**
- **Aiven**

### Deployment

- **Frontend:** Vercel
- **Backend:** Render
- **Database:** Aiven

---

## 🏗️ System Architecture

```text
                    ┌──────────────────────┐
                    │       User           │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │   React Frontend     │
                    │       (Vite)         │
                    └──────────┬───────────┘
                               │
                         REST API / JWT
                               │
                               ▼
                    ┌──────────────────────┐
                    │   Spring Boot API    │
                    │      Backend         │
                    └──────────┬───────────┘
                               │
                  ┌────────────┴────────────┐
                  │                         │
                  ▼                         ▼
        ┌──────────────────┐       ┌──────────────────┐
        │ Spring Security  │       │ Spring Data JPA  │
        │      + JWT       │       │    + Hibernate   │
        └──────────────────┘       └────────┬─────────┘
                                            │
                                            ▼
                                  ┌──────────────────┐
                                  │   MySQL Database │
                                  │      Aiven       │
                                  └──────────────────┘


---

## 📂 Project Structure

```text
jopadevi-logistics-backend/
├── src/
│   ├── main/
│   │   ├── java/com/jopadevi/logistics/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── pom.xml
├── mvnw
└── mvnw.cmd




This is particularly useful because recruiters can immediately understand your backend architecture.

---

#### 2. Add Environment Variables

Because you're making the repository public, this is actually useful documentation:

```markdown
---

## 🔐 Environment Variables

The application uses environment variables for sensitive configuration.

```properties
DB_URL=your_database_url
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
PORT=8090



---

#### 3. Add Developer section

At the bottom:

```markdown
---

## 👨‍💻 Developer

### Atharva Sable

Software Development Intern

Developed the Logistics Management System for Jopadevi Logistics, contributing to backend development, database integration, authentication, security, testing, and deployment.

GitHub:  
https://github.com/atharvasable28



## 📜 License

Copyright © 2026 All Rights Reserved.

This project was developed for Jopadevi Logistics.

The source code is publicly available for educational, portfolio, and demonstration purposes only.

Permission is not granted to copy, modify, distribute, publish, sublicense, or use this project or substantial portions of its source code for commercial purposes without prior written permission.
