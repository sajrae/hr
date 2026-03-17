package com.company.hr.core.dto;


import lombok.Data;

@Data
public class PayslipDTO {

    private Long id;

    private Long employeeId;

    private Long payrollId;

    private Double grossSalary;

    private Double totalDeductions;

    private Double netSalary;
}
