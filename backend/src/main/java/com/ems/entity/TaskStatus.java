package com.ems.entity;

/**
 * TaskStatus Enum - Status of a task
 */
public enum TaskStatus {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    REVIEW("In Review"),
    COMPLETED("Completed"),
    BLOCKED("Blocked");
    
    private final String displayName;
    
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}

/**
 * TaskPriority Enum - Priority of a task
 */
class TaskPriority {
    public static final String LOW = "LOW";
    public static final String MEDIUM = "MEDIUM";
    public static final String HIGH = "HIGH";
    public static final String URGENT = "URGENT";
}
