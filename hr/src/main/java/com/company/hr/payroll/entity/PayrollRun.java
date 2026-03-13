package com.company.hr.payroll.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class PayrollRun {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private PayrollSchedule payrollSchedule;

    private LocalDate periodStart;

    private LocalDate periodEnd;

    private String status;

    private LocalDateTime runDate;

    @OneToMany(mappedBy = "payrollRun")
    private List<Payroll> payrolls;
}
