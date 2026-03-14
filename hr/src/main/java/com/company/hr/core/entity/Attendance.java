package com.company.hr.core.entity;

import com.company.hr.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
public class Attendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    private LocalDate date;

    private LocalTime timeIn;

    private LocalTime timeOut;

    private Double workedHours;

    private Double overtimeHours;

    private Integer lateMinutes;

    private boolean underTime;
}
