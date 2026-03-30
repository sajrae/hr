package com.company.hr.core.service;

import com.company.hr.core.dto.AssignSalaryRequest;
import com.company.hr.core.dto.EmployeeCompensationResponse;
import com.company.hr.core.entity.Department;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.EmployeeSalary;
import com.company.hr.core.entity.Position;
import com.company.hr.core.repository.DepartmentRepository;
import com.company.hr.core.repository.EmployeeRepository;
import com.company.hr.core.repository.EmployeeSalaryRepository;
import com.company.hr.core.repository.PositionRepository;
import com.company.hr.core.entity.OverTimeRule;
import com.company.hr.core.entity.PayrollSchedule;
import com.company.hr.core.entity.TaxRule;
import com.company.hr.core.repository.OverTimeRuleRepository;
import com.company.hr.core.repository.PayrollScheduleRepository;
import com.company.hr.core.repository.TaxRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeSalaryRepository employeeSalaryRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final PayrollScheduleRepository payrollScheduleRepository;
    private final OverTimeRuleRepository overTimeRuleRepository;
    private final TaxRuleRepository taxRuleRepository;

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

    public List<PayrollSchedule> getAllPayroll() {
        return payrollScheduleRepository.findAll();
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

    public EmployeeCompensationResponse getCompensation(final String code) {
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

    public void saveEmployee(final Employee employee) {
        employeeRepository.save(employee);
    }

    public boolean duplicateEmployee(final Employee employee) {

        return employeeRepository.findByEmployeeCode(employee.getFirstName() + "." + employee.getLastName()).isPresent();
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public void saveSalary(EmployeeSalary employeeSalary, Employee employee) {
        employeeSalary.setEmployee(employee);
        employeeSalaryRepository.save(employeeSalary);
    }

    public void deleteSalary(EmployeeSalary employeeSalary) {
        employeeSalary.setEmployee(null);
        employeeSalaryRepository.delete(employeeSalary);
    }

    public List<OverTimeRule> getOverTime() {
        return overTimeRuleRepository.findAll();
    }

    public List<TaxRule> getTaxRule() {
        return taxRuleRepository.findAll();
    }
}
