package com.company.hr.core.mapper;

import com.company.hr.core.dto.EmployeeDto;
import com.company.hr.core.dto.PayrollPeriodDto;
import com.company.hr.core.dto.PayrollRecordDto;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.Payroll;
import com.company.hr.core.entity.PayrollPeriod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PayrollMapper {


    public EmployeeDto covertEmployeeDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setEmployeeCode(employee.getEmployeeCode());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());

        return employeeDto;
    }

    public PayrollPeriodDto convertPeriodDto(PayrollPeriod period) {
        if (period == null) {
            return null;
        }

        PayrollPeriodDto dto = new PayrollPeriodDto();
        dto.setId(period.getId());
        dto.setStatus(period.getStatus());
        dto.setStartDate(period.getStartDate());
        dto.setEndDate(period.getEndDate());

        return dto;
    }

    public PayrollRecordDto converPayroll(Payroll payroll) {

        if (payroll == null) {
            return null;
        }

        PayrollRecordDto dto = new PayrollRecordDto();
        dto.setEmployeeDto(covertEmployeeDto(payroll.getEmployee()));
        dto.setPeriodDto(convertPeriodDto(payroll.getPayrollPeriod()));

        dto.setId(payroll.getId());
        dto.setBasicSalary(payroll.getBasicSalary());
        dto.setOvertimePay(payroll.getOvertimePay());
        dto.setLateDeduction(payroll.getLateDeduction());
        dto.setLeaveDeduction(payroll.getLeaveDeduction());
        dto.setSss(payroll.getSss());
        dto.setPhilHealth(payroll.getPhilHealth());
        dto.setPagibig(payroll.getPagibig());
        dto.setGrossSalary(payroll.getGrossSalary());
        dto.setNetSalary(payroll.getNetSalary());

        return dto;
    }
}
