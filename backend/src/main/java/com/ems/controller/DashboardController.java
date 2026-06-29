package com.ems.controller;

import com.ems.dto.ApiResponse;
import com.ems.entity.*;
import com.ems.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dashboard Controller - Provides dashboard statistics and analytics
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Get dashboard statistics
     */
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'HR', 'TEAM_LEAD')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            // Total employees
            List<Employee> activeEmployees = employeeRepository.findByIsActiveTrue();
            stats.put("totalEmployees", activeEmployees.size());

            // Employees present today
            LocalDate today = LocalDate.now();
            long presentToday = activeEmployees.stream()
                .filter(emp -> {
                    var attendance = attendanceRepository.findByEmployeeAndAttendanceDate(emp, today);
                    return attendance.isPresent() && attendance.get().getStatus() == AttendanceStatus.PRESENT;
                })
                .count();
            stats.put("presentToday", presentToday);

            // Employees on leave today
            long leaveToday = leaveRepository.findByStatus("APPROVED").stream()
                .filter(leave -> !today.isBefore(leave.getFromDate()) && !today.isAfter(leave.getToDate()))
                .count();
            stats.put("leaveToday", leaveToday);

            // Active projects
            List<Project> activeProjects = projectRepository.findByStatus(ProjectStatus.IN_PROGRESS);
            stats.put("activeProjects", activeProjects.size());

            // Pending tasks
            long pendingTasks = taskRepository.findByStatus(TaskStatus.TODO).size();
            stats.put("pendingTasks", pendingTasks);

            // Month attendance
            stats.put("monthAttendance", attendanceRepository.findAll().size());

            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                true,
                "Dashboard statistics retrieved successfully",
                stats,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                false,
                "Failed to retrieve dashboard statistics: " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get attendance statistics for current month
     */
    @GetMapping("/attendance-stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'HR')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAttendanceStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            List<Attendance> allAttendance = attendanceRepository.findAll();
            
            long present = allAttendance.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();
            
            long absent = allAttendance.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.ABSENT)
                .count();
            
            long halfDay = allAttendance.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.HALF_DAY)
                .count();

            stats.put("present", present);
            stats.put("absent", absent);
            stats.put("halfDay", halfDay);
            stats.put("total", allAttendance.size());

            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                true,
                "Attendance statistics retrieved successfully",
                stats,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                false,
                "Failed to retrieve attendance statistics: " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get project statistics
     */
    @GetMapping("/project-stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'TEAM_LEAD')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProjectStats() {
        try {
            Map<String, Object> stats = new HashMap<>();

            List<Project> allProjects = projectRepository.findAll();
            
            long planning = allProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.PLANNING)
                .count();
            
            long inProgress = allProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS)
                .count();
            
            long completed = allProjects.stream()
                .filter(p -> p.getStatus() == ProjectStatus.COMPLETED)
                .count();

            stats.put("planning", planning);
            stats.put("inProgress", inProgress);
            stats.put("completed", completed);
            stats.put("total", allProjects.size());

            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                true,
                "Project statistics retrieved successfully",
                stats,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                false,
                "Failed to retrieve project statistics: " + e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
