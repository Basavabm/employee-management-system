package com.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Employee Entity - Represents employee details
 */
@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeId;
    
    @Column(name = "department")
    private String department;
    
    @Column(name = "designation")
    private String designation;
    
    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "pincode")
    private String pincode;
    
    @Column(name = "salary")
    private Double salary;
    
    @Column(name = "bank_account_number")
    private String bankAccountNumber;
    
    @Column(name = "pan_number")
    private String panNumber;
    
    @Column(name = "aadhar_number")
    private String aadharNumber;
    
    @ManyToOne
    @JoinColumn(name = "team_lead_id")
    private Employee teamLead;
    
    @OneToMany(mappedBy = "teamLead", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> teamMembers;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
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
