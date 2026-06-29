package com.ems.entity;

/**
 * LeaveType Enum - Types of leaves available
 */
public enum LeaveType {
    CASUAL("Casual Leave"),
    SICK("Sick Leave"),
    EARNED("Earned Leave"),
    MATERNITY("Maternity Leave"),
    PATERNITY("Paternity Leave"),
    BEREAVEMENT("Bereavement Leave"),
    STUDY("Study Leave"),
    UNPAID("Unpaid Leave");
    
    private final String displayName;
    
    LeaveType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}

/**
 * LeaveStatus Enum - Status of leave request
 */
class LeaveStatus {
    public static final String PENDING = "PENDING";
    public static final String APPROVED = "APPROVED";
    public static final String REJECTED = "REJECTED";
    public static final String CANCELLED = "CANCELLED";
}
