package com.company.hr.core.entity;

import com.company.hr.common.BaseEntity;
import com.company.hr.common.enums.PayrollType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PayrollSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PayrollType payrollType;

    private String payDays;

    @OneToMany(mappedBy = "payrollSchedule")
    private List<Employee> employees;

    @OneToMany(mappedBy = "payrollSchedule")
    private List<PayrollRun> payrollRuns;
}
