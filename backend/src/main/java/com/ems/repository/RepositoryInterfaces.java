package com.ems.repository;

import com.ems.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Employee Repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
    List<Employee> findByTeamLead(Employee teamLead);
    List<Employee> findByIsActiveTrue();
}

/**
 * Attendance Repository
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeAndAttendanceDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    Optional<Attendance> findByEmployeeAndAttendanceDate(Employee employee, LocalDate date);
}

/**
 * Leave Repository
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployee(Employee employee);
    List<Leave> findByStatus(String status);
    List<Leave> findByEmployeeAndFromDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
}

/**
 * Project Repository
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectCode(String projectCode);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByTeamLead(Employee teamLead);
}

/**
 * Task Repository
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskCode(String taskCode);
    List<Task> findByAssignedTo(Employee employee);
    List<Task> findByProject(Project project);
    List<Task> findByStatus(TaskStatus status);
}
