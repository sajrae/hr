package com.company.hr.employee.entity;

import com.company.hr.payroll.entity.PayrollSchedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeCode;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnore
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @JsonIgnore
    private Position position;

    @ManyToOne
    @JoinColumn(name = "payrollScheduleCode_id")
    @JsonIgnore
    private PayrollSchedule payrollSchedule;

    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private String phone;
    private String hireDate;

    @PrePersist
    public void generateEmployeeCode() {
        if (employeeCode == null) {
            employeeCode = "TEMP-" + System.currentTimeMillis();
        }
    }
}
