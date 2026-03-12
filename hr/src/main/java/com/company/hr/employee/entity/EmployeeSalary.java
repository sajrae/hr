package com.company.hr.employee.entity;

import com.company.hr.payroll.entity.OverTimeRule;
import com.company.hr.payroll.entity.TaxRule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "overTimeRule_id")
    private OverTimeRule overTimeRule;

    @ManyToOne
    @JoinColumn(name = "taxRule_id")
    private TaxRule taxRule;

    private double baseSalary;
    private LocalDate effectiveDate;
}
