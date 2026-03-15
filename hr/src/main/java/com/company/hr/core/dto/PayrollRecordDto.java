package com.company.hr.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PayrollRecordDto {

    private Long id;

    private EmployeeDto employeeDto;

    private PayrollPeriodDto periodDto;

    private BigDecimal basicSalary;

    private BigDecimal totalAllowance;

    private BigDecimal overtimePay;

    private BigDecimal holidayPay;

    private BigDecimal leaveDeduction;

    private BigDecimal lateDeduction;

    private BigDecimal sss;

    private BigDecimal philHealth;

    private BigDecimal pagibig;

    private BigDecimal tax;


    private BigDecimal grossSalary;

    private BigDecimal netSalary;
}

