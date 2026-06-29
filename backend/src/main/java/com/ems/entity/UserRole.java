package com.ems.entity;

/**
 * UserRole Enum - Represents different roles in the Employee Management System
 */
public enum UserRole {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    TEAM_LEAD("Team Lead"),
    HR("HR"),
    BACKEND_DEV("Backend Developer"),
    FRONTEND_DEV("Frontend Developer"),
    DEVOPS_ENG("DevOps Engineer"),
    QA_ENG("QA Engineer"),
    INTERN("Intern"),
    EMPLOYEE("Employee");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
