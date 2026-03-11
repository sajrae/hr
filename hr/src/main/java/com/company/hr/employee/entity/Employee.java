package com.company.hr.employee.entity;

import com.company.hr.attendance.entity.AttendanceLog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy ="employee")
    private List<AttendanceLog> attendanceLogs;

    private String employeeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String status;
}
