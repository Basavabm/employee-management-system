package com.ems.controller;

import com.ems.dto.ApiResponse;
import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Employee Controller - Handles employee-related operations
 */
@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get all employees
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
        try {
            List<Employee> employees = employeeRepository.findByIsActiveTrue();
            ApiResponse<List<Employee>> response = new ApiResponse<>(
                true,
                "Employees retrieved successfully",
                employees,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Employee>> response = new ApiResponse<>(
                false,
                "Failed to retrieve employees: " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get employee by ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER', 'TEAM_LEAD')")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
            ApiResponse<Employee> response = new ApiResponse<>(
                true,
                "Employee retrieved successfully",
                employee,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Employee> response = new ApiResponse<>(
                false,
                "Failed to retrieve employee: " + e.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Create new employee
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeRepository.save(employee);
            ApiResponse<Employee> response = new ApiResponse<>(
                true,
                "Employee created successfully",
                savedEmployee,
                HttpStatus.CREATED.value()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Employee> response = new ApiResponse<>(
                false,
                "Failed to create employee: " + e.getMessage(),
                null,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update employee
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
            
            if (employee.getDepartment() != null) existingEmployee.setDepartment(employee.getDepartment());
            if (employee.getDesignation() != null) existingEmployee.setDesignation(employee.getDesignation());
            if (employee.getSalary() != null) existingEmployee.setSalary(employee.getSalary());
            
            Employee updatedEmployee = employeeRepository.save(existingEmployee);
            ApiResponse<Employee> response = new ApiResponse<>(
                true,
                "Employee updated successfully",
                updatedEmployee,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Employee> response = new ApiResponse<>(
                false,
                "Failed to update employee: " + e.getMessage(),
                null,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Delete employee (soft delete)
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id) {
        try {
            Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
            employee.setIsActive(false);
            employeeRepository.save(employee);
            
            ApiResponse<String> response = new ApiResponse<>(
                true,
                "Employee deleted successfully",
                null,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(
                false,
                "Failed to delete employee: " + e.getMessage(),
                null,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get team members under a team lead
     */
    @GetMapping("/team-lead/{teamLeadId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'TEAM_LEAD')")
    public ResponseEntity<ApiResponse<List<Employee>>> getTeamMembers(@PathVariable Long teamLeadId) {
        try {
            Employee teamLead = employeeRepository.findById(teamLeadId)
                .orElseThrow(() -> new RuntimeException("Team lead not found"));
            List<Employee> teamMembers = employeeRepository.findByTeamLead(teamLead);
            
            ApiResponse<List<Employee>> response = new ApiResponse<>(
                true,
                "Team members retrieved successfully",
                teamMembers,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Employee>> response = new ApiResponse<>(
                false,
                "Failed to retrieve team members: " + e.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
