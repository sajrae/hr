package com.company.hr.core.entity;

import com.company.hr.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class OverTimeRule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double multiplier;

    private String description;

    @OneToMany(mappedBy = "overTimeRule")
    private List<EmployeeSalary> employeeSalaries;
}
