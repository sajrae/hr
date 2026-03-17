package com.company.hr.core.mapper;

import com.company.hr.core.dto.PayslipDTO;
import com.company.hr.core.entity.Payslip;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PayslipMapper {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "payroll.id", target = "payrollId")
    PayslipDTO toDto(Payslip payslip);
}
