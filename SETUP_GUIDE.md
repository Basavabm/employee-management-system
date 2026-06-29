# 🚀 Quick Start Guide - Employee Management System

## Prerequisites Installation (Ubuntu 22.04)

### 1️⃣ Install Java 21
```bash
sudo apt update
sudo apt install openjdk-21-jdk -y
java -version
```

### 2️⃣ Install Maven
```bash
sudo apt install maven -y
mvn -version
```

### 3️⃣ Install Node.js & npm
```bash
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install nodejs -y
node --version
npm --version
```

### 4️⃣ Install MySQL
```bash
sudo apt install mysql-server -y
sudo mysql_secure_installation
```

## Database Setup

### Create Database
```bash
mysql -u root -p
```

Paste these commands in MySQL:
```sql
CREATE DATABASE ems_db;
CREATE USER 'ems_user'@'localhost' IDENTIFIED BY 'ems_password';
GRANT ALL PRIVILEGES ON ems_db.* TO 'ems_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

### Import Sample Data
```bash
mysql -u ems_user -p ems_db < database/init.sql
```

## Backend Setup & Run

```bash
cd backend

# Update application.properties with your credentials
# Then build and run
mvn clean install
mvn spring-boot:run
```

**Backend URL:** `http://localhost:3000/api/v1`

## Frontend Setup & Run

```bash
cd frontend

# Install dependencies
npm install

# Run development server
npm run dev
```

**Frontend URL:** `http://localhost:5173`

## 🔑 Default Login Credentials

| Username | Password | Role |
|----------|----------|------|
| admin | password123 | Admin |
| manager1 | password123 | Manager |
| teamlead1 | password123 | Team Lead |
| hr1 | password123 | HR |
| dev1 | password123 | Backend Developer |

## 📊 First Login Steps

1. Open `http://localhost:5173` in your browser
2. Login with **admin** / **password123**
3. View Dashboard with real-time statistics
4. Explore Employee, Project, and Task management features

## 🐛 Common Issues & Fixes

### Port 3000 or 5173 Already in Use
```bash
# Find process
lsof -i :3000
lsof -i :5173

# Kill process
kill -9 <PID>
```

### MySQL Connection Error
```bash
sudo service mysql restart
mysql -u ems_user -p -e "SELECT 1"
```

### Maven Build Failed
```bash
mvn clean install -U
```

### npm Dependencies Issue
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
```

## 📁 Project Structure

```
ems-project/
├── backend/          # Spring Boot Application
├── frontend/         # React Application
├── database/         # SQL Scripts
├── UI_MOCKUPS.html   # UI Preview
├── README.md         # Full Documentation
└── SETUP_GUIDE.md    # This file
```

## 🎯 Next Steps

1. ✅ View Dashboard and Statistics
2. ✅ Manage Employees
3. ✅ Create Projects and Tasks
4. ✅ Track Attendance
5. ✅ Manage Leave Requests
6. ✅ Generate Reports

## 📚 API Documentation

### Base URL
```
http://localhost:3000/api/v1
```

### Key Endpoints

**Authentication:**
- `POST /auth/login` - Login
- `GET /auth/me` - Get current user

**Employees:**
- `GET /employees` - Get all employees
- `POST /employees` - Create employee
- `PUT /employees/{id}` - Update employee

**Dashboard:**
- `GET /dashboard/stats` - Get statistics
- `GET /dashboard/attendance-stats` - Attendance data
- `GET /dashboard/project-stats` - Project data

## 🔒 Security Notes

- Default passwords should be changed in production
- Use HTTPS in production environment
- Keep JWT secret secure
- Enable database backups
- Use environment variables for sensitive data

## 📞 Support

For detailed documentation, see `README.md`

## 🎉 You're All Set!

Your Employee Management System is ready to use. Happy coding! 🚀
