package com.company.hr.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequestDto {

    private String employeeId;
    private Long leaveTypeId;
    private LocalDate startDate;
    private LocalDate endDate;
}
