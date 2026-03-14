package com.company.hr.attendance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class AttendanceResponse {

    private String employeeCode;
    private double hoursWorked;
    private int lateMinutes;
    private double overTimeHours;
    private boolean underTime;
    private LocalDate date;
    private LocalTime timeIn;
    private LocalTime timeOut;
}