package com.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Task Entity - Represents tasks assigned to employees
 */
@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Column(name = "task_title", nullable = false)
    private String taskTitle;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "task_code", unique = true, nullable = false)
    private String taskCode;
    
    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private Employee assignedTo;
    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Employee createdBy;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;
    
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.MEDIUM;
    
    @Column(name = "progress")
    private Integer progress = 0;
    
    @Column(name = "estimated_hours")
    private Double estimatedHours;
    
    @Column(name = "actual_hours")
    private Double actualHours;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
