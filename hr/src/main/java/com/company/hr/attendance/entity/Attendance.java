package com.company.hr.attendance.entity;

import com.company.hr.common.BaseEntity;
import com.company.hr.employee.entity.Employee;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDate date;

    private LocalDateTime timeIn;

    private LocalDateTime timeOut;

    private Double workedHours;

    private Double overtimeHours;

    private Integer lateMinutes;
}
