package com.company.hr.employee.service;

import com.company.hr.employee.dto.AssignSalaryRequest;
import com.company.hr.employee.dto.EmployeeCompensationResponse;
import com.company.hr.employee.entity.Department;
import com.company.hr.employee.entity.Employee;
import com.company.hr.employee.entity.EmployeeSalary;
import com.company.hr.employee.entity.Position;
import com.company.hr.employee.repository.DepartmentRepository;
import com.company.hr.employee.repository.EmployeeRepository;
import com.company.hr.employee.repository.EmployeeSalaryRepository;
import com.company.hr.employee.repository.PositionRepository;
import com.company.hr.payroll.entity.OverTimeRule;
import com.company.hr.payroll.entity.PayrollSchedule;
import com.company.hr.payroll.entity.TaxRule;
import com.company.hr.payroll.repository.OverTimeRuleRepository;
import com.company.hr.payroll.repository.PayrollScheduleRepository;
import com.company.hr.payroll.repository.TaxRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final PayrollScheduleRepository payrollScheduleRepository;
    private final OverTimeRuleRepository overTimeRuleRepository;
    private final TaxRuleRepository taxRuleRepository;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeSalaryRepository employeeSalaryRepository, PositionRepository positionRepository, DepartmentRepository departmentRepository, PayrollScheduleRepository payrollScheduleRepository, OverTimeRuleRepository overTimeRuleRepository, TaxRuleRepository taxRuleRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.payrollScheduleRepository = payrollScheduleRepository;
        this.overTimeRuleRepository = overTimeRuleRepository;
        this.taxRuleRepository = taxRuleRepository;
    }

    public List<Employee> getEmployee() {
        return employeeRepository.findAll();
    }

    public List<EmployeeSalary> getSalaray() {
        return employeeSalaryRepository.findAll();
    }

    public List<Position> getPosition() {
        return positionRepository.findAll();
    }

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    public void assignSalary(final String code, final AssignSalaryRequest salaryRequest) {

        Employee employee = getEmployeeById(code);

        PayrollSchedule payrollSchedule = payrollScheduleRepository.findById(salaryRequest.getPayrollScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        OverTimeRule overTimeRule = overTimeRuleRepository.findById(salaryRequest.getOverTimeRule())
                .orElseThrow(() -> new RuntimeException("Over Time Rule Not found"));

        TaxRule taxRule = taxRuleRepository.findById(salaryRequest.getTaxRule())
                .orElseThrow(() -> new RuntimeException("Tax Rule Not found"));

        employee.setPayrollSchedule(payrollSchedule);
        employeeRepository.save(employee);

        EmployeeSalary employeeSalary = employeeSalaryRepository.findByEmployeeId(employee.getId()).orElse(new EmployeeSalary());

        employeeSalary.setEmployee(employee);
        employeeSalary.setBaseSalary(salaryRequest.getBaseSalary());
        employeeSalary.setOverTimeRule(overTimeRule);
        employeeSalary.setTaxRule(taxRule);

        employeeSalaryRepository.save(employeeSalary);
    }

    public EmployeeCompensationResponse getCompensation(final String code){
        final Employee employee = getEmployeeById(code);
        final EmployeeSalary employeeSalary = employeeSalaryRepository.findByEmployeeId(employee.getId())
                .orElseThrow(() -> new RuntimeException("No Employee Salary"));

        EmployeeCompensationResponse response = new EmployeeCompensationResponse();

        response.setBasicSalary(employeeSalary.getBaseSalary());
        response.setOverTimeRate(employeeSalary.getOverTimeRule().getMultiplier());
        response.setSchedule(employee.getPayrollSchedule().getName());

        return response;
    }

    private Employee getEmployeeById(String code) {
        return employeeRepository.findByEmployeeCode(code).orElseThrow(() -> new RuntimeException("No Employee Found"));
    }

}
