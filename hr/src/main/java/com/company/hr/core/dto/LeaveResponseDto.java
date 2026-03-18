package com.company.hr.core.dto;

import com.company.hr.common.enums.LeaveTypes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class LeaveResponseDto {

    private String employeeName;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveTypes status;

}
