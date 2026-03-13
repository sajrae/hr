package com.company.hr.payroll.entity;

import com.company.hr.employee.entity.EmployeeAllowance;
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
public class AllowanceType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Boolean taxable;

    @OneToMany(mappedBy = "allowanceType")
    private List<EmployeeAllowance> employeeAllowances;
}
