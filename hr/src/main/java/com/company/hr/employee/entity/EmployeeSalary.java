package com.company.hr.employee.entity;

import com.company.hr.payroll.entity.OverTimeRule;
import com.company.hr.payroll.entity.TaxRule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class EmployeeSalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @Column(unique = true,nullable = false)
    private Employee employee;


    @OneToOne
    @JoinColumn(name = "overTimeRule_id")
    private OverTimeRule overTimeRule;

    @OneToOne
    @JoinColumn(name = "taxRule_id")
    private TaxRule taxRule;

    private double baseSalary;
    private LocalDate effectiveDate;
}
