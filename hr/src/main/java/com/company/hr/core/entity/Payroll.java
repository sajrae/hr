package com.company.hr.core.entity;

import com.company.hr.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

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
    private PayrollRun payrollRun;

    private Double basicSalary;

    private Double totalAllowance;

    private Double overtimePay;

    private Double holidayPay;

    private Double leaveDeduction;

    private Double lateDeduction;

    private Double tax;

    private Double netSalary;
}
