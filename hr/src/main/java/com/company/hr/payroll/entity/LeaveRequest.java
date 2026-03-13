package com.company.hr.payroll.entity;

import com.company.hr.employee.entity.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private LeaveType leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private String reason;
}
