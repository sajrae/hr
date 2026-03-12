package com.company.hr.payroll.entity;

import com.company.hr.common.enums.PayrollType;
import com.company.hr.employee.entity.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class PayrollSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PayrollType payrollType;

    private String payDays;

    @OneToMany(mappedBy = "payrollSchedule")
    private List<Employee> employees;
}
