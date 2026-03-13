package com.company.hr.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttendanceResponse {

    private String employeeCode;
    private double hoursWorked;
    private double lateMinutes;
    private double overTimeHours;
    private boolean underTime;
}
