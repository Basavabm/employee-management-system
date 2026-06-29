-- Create Database
CREATE DATABASE IF NOT EXISTS ems_db;
USE ems_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    phone_number VARCHAR(20),
    profile_image_url VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    employee_id VARCHAR(50) UNIQUE NOT NULL,
    department VARCHAR(100),
    designation VARCHAR(100),
    date_of_joining DATE,
    date_of_birth DATE,
    gender VARCHAR(20),
    address VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    pincode VARCHAR(10),
    salary DECIMAL(10, 2),
    bank_account_number VARCHAR(50),
    pan_number VARCHAR(20),
    aadhar_number VARCHAR(20),
    team_lead_id BIGINT,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (team_lead_id) REFERENCES employees(id)
);

-- Create attendance table
CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    attendance_date DATE NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    status VARCHAR(50),
    remarks TEXT,
    working_hours DECIMAL(5, 2),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    UNIQUE KEY unique_attendance (employee_id, attendance_date)
);

-- Create leaves table
CREATE TABLE IF NOT EXISTS leaves (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    leave_type VARCHAR(50) NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    number_of_days INT,
    reason TEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    approved_by BIGINT,
    approval_date TIMESTAMP,
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (approved_by) REFERENCES employees(id)
);

-- Create projects table
CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT,
    project_code VARCHAR(50) UNIQUE NOT NULL,
    team_lead_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'PLANNING',
    priority VARCHAR(50) DEFAULT 'MEDIUM',
    progress INT DEFAULT 0,
    budget DECIMAL(15, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (team_lead_id) REFERENCES employees(id)
);

-- Create tasks table
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL,
    task_title VARCHAR(255) NOT NULL,
    description TEXT,
    task_code VARCHAR(50) UNIQUE NOT NULL,
    assigned_to BIGINT NOT NULL,
    created_by BIGINT,
    start_date DATE,
    due_date DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'TODO',
    priority VARCHAR(50) DEFAULT 'MEDIUM',
    progress INT DEFAULT 0,
    estimated_hours DECIMAL(5, 2),
    actual_hours DECIMAL(5, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (assigned_to) REFERENCES employees(id),
    FOREIGN KEY (created_by) REFERENCES employees(id)
);

-- Insert sample users (passwords should be hashed with BCrypt in real scenario)
-- Sample password: password123
INSERT INTO users (username, email, password, first_name, last_name, phone_number, role, enabled) VALUES
('admin', 'admin@ems.com', '$2a$10$YourHashedPasswordHere', 'Admin', 'User', '9876543210', 'ADMIN', true),
('manager1', 'manager1@ems.com', '$2a$10$YourHashedPasswordHere', 'Ramesh', 'Kumar', '9876543211', 'MANAGER', true),
('teamlead1', 'teamlead1@ems.com', '$2a$10$YourHashedPasswordHere', 'Priya', 'Singh', '9876543212', 'TEAM_LEAD', true),
('hr1', 'hr1@ems.com', '$2a$10$YourHashedPasswordHere', 'Anjali', 'Patel', '9876543213', 'HR', true),
('dev1', 'dev1@ems.com', '$2a$10$YourHashedPasswordHere', 'Arjun', 'Desai', '9876543214', 'BACKEND_DEV', true),
('dev2', 'dev2@ems.com', '$2a$10$YourHashedPasswordHere', 'Nikhil', 'Verma', '9876543215', 'FRONTEND_DEV', true),
('qa1', 'qa1@ems.com', '$2a$10$YourHashedPasswordHere', 'Sneha', 'Gupta', '9876543216', 'QA_ENG', true),
('devops1', 'devops1@ems.com', '$2a$10$YourHashedPasswordHere', 'Rajesh', 'Sharma', '9876543217', 'DEVOPS_ENG', true),
('intern1', 'intern1@ems.com', '$2a$10$YourHashedPasswordHere', 'Aditya', 'Singh', '9876543218', 'INTERN', true),
('employee1', 'employee1@ems.com', '$2a$10$YourHashedPasswordHere', 'Pooja', 'Mishra', '9876543219', 'EMPLOYEE', true);

-- Insert sample employees
INSERT INTO employees (user_id, employee_id, department, designation, date_of_joining, salary, is_active) VALUES
(1, 'EMP001', 'Administration', 'Administrator', '2020-01-15', 100000, true),
(2, 'EMP002', 'Management', 'Manager', '2020-03-20', 85000, true),
(3, 'EMP003', 'Development', 'Team Lead', '2020-06-10', 75000, true),
(4, 'EMP004', 'Human Resources', 'HR Manager', '2020-08-05', 70000, true),
(5, 'EMP005', 'Development', 'Senior Developer', '2021-01-12', 65000, true),
(6, 'EMP006', 'Development', 'Frontend Developer', '2021-02-18', 60000, true),
(7, 'EMP007', 'Quality Assurance', 'QA Engineer', '2021-04-22', 55000, true),
(8, 'EMP008', 'DevOps', 'DevOps Engineer', '2021-05-30', 70000, true),
(9, 'EMP009', 'Development', 'Intern', '2024-01-10', 25000, true),
(10, 'EMP010', 'Development', 'Junior Developer', '2023-06-01', 45000, true);

-- Update team lead references
UPDATE employees SET team_lead_id = 3 WHERE id IN (5, 6, 7, 9, 10);

-- Insert sample projects
INSERT INTO projects (project_name, description, project_code, team_lead_id, start_date, status, priority, progress, budget) VALUES
('Employee Management System', 'Complete EMS platform', 'PRJ001', 3, '2024-01-01', 'IN_PROGRESS', 'HIGH', 60, 500000),
('Mobile App Development', 'iOS and Android app', 'PRJ002', 3, '2024-02-15', 'IN_PROGRESS', 'HIGH', 40, 750000),
('Database Optimization', 'Performance improvement', 'PRJ003', 3, '2024-01-20', 'COMPLETED', 'MEDIUM', 100, 200000),
('Cloud Migration', 'AWS migration project', 'PRJ004', 3, '2024-03-01', 'PLANNING', 'HIGH', 0, 1000000);

-- Insert sample tasks
INSERT INTO tasks (project_id, task_title, description, task_code, assigned_to, created_by, start_date, due_date, status, priority, progress, estimated_hours) VALUES
(1, 'Setup Backend API', 'Create REST API endpoints', 'TASK001', 5, 3, '2024-01-05', '2024-01-20', 'COMPLETED', 'HIGH', 100, 40),
(1, 'Frontend Dashboard', 'Create dashboard UI', 'TASK002', 6, 3, '2024-01-10', '2024-02-15', 'IN_PROGRESS', 'HIGH', 70, 60),
(1, 'Testing and QA', 'Test all features', 'TASK003', 7, 3, '2024-01-25', '2024-02-28', 'TODO', 'MEDIUM', 0, 50),
(2, 'Mobile UI Design', 'Design mobile interface', 'TASK004', 6, 3, '2024-02-20', '2024-03-20', 'IN_PROGRESS', 'HIGH', 50, 80),
(3, 'Query Optimization', 'Optimize slow queries', 'TASK005', 5, 3, '2024-01-20', '2024-02-10', 'COMPLETED', 'MEDIUM', 100, 30);

-- Insert sample attendance data
INSERT INTO attendance (employee_id, attendance_date, check_in_time, check_out_time, status, working_hours) VALUES
(5, CURDATE(), '09:00:00', '18:00:00', 'PRESENT', 9),
(6, CURDATE(), '09:15:00', '17:45:00', 'PRESENT', 8.5),
(7, CURDATE(), '10:00:00', NULL, 'PRESENT', 0),
(8, CURDATE(), '08:45:00', '17:30:00', 'PRESENT', 8.75),
(10, CURDATE(), '09:30:00', '18:15:00', 'PRESENT', 8.75);

-- Insert sample leave requests
INSERT INTO leaves (employee_id, leave_type, from_date, to_date, number_of_days, reason, status, approved_by, approval_date) VALUES
(5, 'CASUAL', '2024-04-10', '2024-04-12', 3, 'Personal reasons', 'APPROVED', 3, NOW()),
(6, 'SICK', '2024-03-15', '2024-03-16', 2, 'Medical checkup', 'APPROVED', 3, NOW()),
(7, 'CASUAL', '2024-04-20', '2024-04-20', 1, 'Family event', 'PENDING', NULL, NULL),
(10, 'EARNED', '2024-05-01', '2024-05-05', 5, 'Vacation', 'PENDING', NULL, NULL);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_employees_user_id ON employees(user_id);
CREATE INDEX idx_employees_employee_id ON employees(employee_id);
CREATE INDEX idx_attendance_employee_date ON attendance(employee_id, attendance_date);
CREATE INDEX idx_leaves_employee ON leaves(employee_id);
CREATE INDEX idx_projects_team_lead ON projects(team_lead_id);
CREATE INDEX idx_tasks_project ON tasks(project_id);
CREATE INDEX idx_tasks_assigned_to ON tasks(assigned_to);
