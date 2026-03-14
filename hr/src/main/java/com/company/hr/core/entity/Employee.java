package com.company.hr.core.entity;

import com.company.hr.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payrollScheduleCode_id")
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
            employeeCode = firstName.toLowerCase() + "." + lastName.toLowerCase();
        }
    }
}
