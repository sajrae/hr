package com.company.hr.payroll.entity;

import com.company.hr.common.BaseEntity;
import com.company.hr.employee.entity.EmployeeSalary;
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
public class TaxRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double rate;

    private String description;

    @OneToMany(mappedBy = "taxRule")
    private List<EmployeeSalary> employeeSalaries;
}
