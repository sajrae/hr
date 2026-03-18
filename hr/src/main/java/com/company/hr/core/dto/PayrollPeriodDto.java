package com.company.hr.core.dto;

import com.company.hr.common.enums.PayrollPeriodType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PayrollPeriodDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    private PayrollPeriodType status;
}
