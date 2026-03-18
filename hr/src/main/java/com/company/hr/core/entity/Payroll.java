package com.company.hr.core.entity;

import com.company.hr.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Payroll extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private PayrollPeriod payrollPeriod;

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
