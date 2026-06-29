# Employee Management System (EMS)

A complete, production-ready Employee Management System built with Java 21, Spring Boot 3, React, and Material UI.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [User Roles & Permissions](#user-roles--permissions)
- [Sample Data](#sample-data)
- [Troubleshooting](#troubleshooting)
- [Docker Setup (Optional)](#docker-setup-optional)

## 🎯 Project Overview

The Employee Management System is a comprehensive solution for managing:
- Employee information and profiles
- Attendance tracking
- Leave management
- Project and task assignment
- Role-based access control with JWT authentication
- Dashboard with analytics and reporting

## ✨ Features

### Core Features
- ✅ Secure login/logout with JWT authentication
- ✅ Role-based access control (RBAC)
- ✅ Dashboard with real-time statistics
- ✅ Employee management
- ✅ Attendance tracking
- ✅ Leave management
- ✅ Project management
- ✅ Task assignment and tracking
- ✅ Responsive UI with Material Design

### User Roles
- **Admin** - Full system access
- **Manager** - Team and project oversight
- **Team Lead** - Team member management
- **HR** - Employee and leave management
- **Backend Developer** - Task assignment
- **Frontend Developer** - Task assignment
- **QA Engineer** - Quality assurance tasks
- **DevOps Engineer** - Infrastructure tasks
- **Intern** - Limited access
- **Employee** - Basic access

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot 3.2.1
- Spring Security with JWT
- Spring Data JPA
- MySQL 8.0
- Maven

### Frontend
- React 18.2
- Vite 5
- Material UI (MUI)
- Recharts for charts
- Axios for API calls
- React Router for navigation

## 📦 Prerequisites

### For Ubuntu/Linux Server

1. **Java 21**
   ```bash
   sudo apt update
   sudo apt install openjdk-21-jdk -y
   java -version
   ```

2. **Maven 3.8+**
   ```bash
   sudo apt install maven -y
   mvn -version
   ```

3. **Node.js 18+ and npm**
   ```bash
   curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
   sudo apt install nodejs -y
   node --version
   npm --version
   ```

4. **MySQL 8.0**
   ```bash
   sudo apt install mysql-server -y
   sudo mysql_secure_installation
   mysql --version
   ```

5. **Git**
   ```bash
   sudo apt install git -y
   ```

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd ems-project
```

### 2. Database Setup

#### Create Database and User
```bash
sudo mysql -u root -p
```

Then run in MySQL:
```sql
CREATE DATABASE IF NOT EXISTS ems_db;
CREATE USER 'ems_user'@'localhost' IDENTIFIED BY 'ems_password';
GRANT ALL PRIVILEGES ON ems_db.* TO 'ems_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### Import Sample Data
```bash
mysql -u ems_user -p ems_db < database/init.sql
```

### 3. Backend Setup

Navigate to backend directory:
```bash
cd backend
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=ems_user
spring.datasource.password=ems_password
spring.datasource.url=jdbc:mysql://localhost:3306/ems_db
```

Build the project:
```bash
mvn clean install
```

### 4. Frontend Setup

Navigate to frontend directory:
```bash
cd frontend
```

Install dependencies:
```bash
npm install
```

## ▶️ Running the Application

### Start MySQL (if not running as service)
```bash
sudo service mysql start
```

### Start Backend
```bash
cd backend
mvn spring-boot:run
```

Backend will run on: `http://localhost:3000/api/v1`

### Start Frontend (in a new terminal)
```bash
cd frontend
npm run dev
```

Frontend will run on: `http://localhost:5173`

## 📁 Project Structure

```
ems-project/
├── backend/
│   ├── src/main/java/com/ems/
│   │   ├── controller/          # REST Controllers
│   │   ├── entity/              # JPA Entities
│   │   ├── repository/          # Data Access Layer
│   │   ├── service/             # Business Logic
│   │   ├── security/            # JWT & Security
│   │   ├── config/              # Configuration
│   │   ├── dto/                 # Data Transfer Objects
│   │   └── EmployeeManagementSystemApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── pages/               # React Pages
│   │   ├── components/          # React Components
│   │   ├── services/            # API Services
│   │   ├── App.jsx
│   │   └── main.jsx
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
├── database/
│   └── init.sql                 # Database schema & sample data
└── README.md
```

## 🔌 API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/logout` - User logout
- `GET /api/v1/auth/me` - Get current user

### Employees
- `GET /api/v1/employees` - Get all employees
- `GET /api/v1/employees/{id}` - Get employee by ID
- `POST /api/v1/employees` - Create employee
- `PUT /api/v1/employees/{id}` - Update employee
- `DELETE /api/v1/employees/{id}` - Delete employee

### Dashboard
- `GET /api/v1/dashboard/stats` - Get dashboard statistics
- `GET /api/v1/dashboard/attendance-stats` - Get attendance stats
- `GET /api/v1/dashboard/project-stats` - Get project stats

## 👥 User Roles & Permissions

| Feature | Admin | Manager | Team Lead | HR | Others |
|---------|-------|---------|-----------|-----|--------|
| View Dashboard | ✓ | ✓ | ✓ | ✓ | ✗ |
| Manage Employees | ✓ | ✗ | ✗ | ✓ | ✗ |
| View Team Members | ✓ | ✓ | ✓ | ✓ | ✗ |
| Manage Projects | ✓ | ✓ | ✓ | ✗ | ✗ |
| Assign Tasks | ✓ | ✓ | ✓ | ✗ | ✗ |
| Approve Leave | ✓ | ✓ | ✓ | ✓ | ✗ |

## 📊 Sample Data

### Test Accounts

| Username | Password | Role |
|----------|----------|------|
| admin | password123 | Admin |
| manager1 | password123 | Manager |
| teamlead1 | password123 | Team Lead |
| hr1 | password123 | HR |
| dev1 | password123 | Backend Dev |

## 🐛 Troubleshooting

### MySQL Connection Error
```bash
# Check MySQL is running
sudo service mysql status

# Restart MySQL
sudo service mysql restart

# Check connection
mysql -u ems_user -p -e "SELECT 1"
```

### Port Already in Use
```bash
# Backend (3000)
sudo lsof -i :3000

# Frontend (5173)
sudo lsof -i :5173
```

### Dependencies Issues
```bash
# Clear Maven cache
mvn clean

# Reinstall npm packages
rm -rf node_modules package-lock.json
npm install
```

## 🐳 Docker Setup (Optional)

### Create Dockerfile for Backend
```dockerfile
FROM openjdk:21-slim
COPY target/employee-management-system-1.0.0.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Create Dockerfile for Frontend
```dockerfile
FROM node:20-alpine as builder
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80
```

### Docker Compose
```bash
docker-compose up -d
```

## 📝 Build Commands

### Backend
```bash
# Clean and build
mvn clean install

# Run with Maven
mvn spring-boot:run

# Build JAR
mvn clean package
java -jar target/employee-management-system-1.0.0.jar
```

### Frontend
```bash
# Install dependencies
npm install

# Development server
npm run dev

# Production build
npm run build

# Preview build
npm run preview
```

## 🔒 Security Features

- ✅ JWT Token-based authentication
- ✅ Password encryption with BCrypt
- ✅ Role-based access control (RBAC)
- ✅ CORS protection
- ✅ SQL injection prevention via JPA
- ✅ XSS protection via React

## 📧 Support & Contribution

For issues, feature requests, or contributions, please open an issue or pull request.

## 📄 License

This project is open source and available under the MIT License.

## 🎓 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [Material UI](https://mui.com)
- [JWT Authentication](https://jwt.io)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**Last Updated:** January 2024
**Version:** 1.0.0
