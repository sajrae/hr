package com.company.hr.core.controller;

import com.company.hr.common.response.ApiResponse;
import com.company.hr.core.dto.PayrollRecordDto;
import com.company.hr.core.entity.Payroll;
import com.company.hr.core.entity.PayrollPeriod;
import com.company.hr.core.mapper.PayrollMapper;
import com.company.hr.core.repository.PayrollPeriodRepository;
import com.company.hr.core.repository.PayrollRepository;
import com.company.hr.core.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/payroll")
public class PayrollController {

    private final PayrollService payrollService;
    private final PayrollRepository payrollRepository;
    private final PayrollPeriodRepository periodRepository;
    private final PayrollMapper payrollMapper;

    @PostMapping("/run/{periodId}")
    public ResponseEntity<ApiResponse<String>> runPayroll(@PathVariable Long periodId) {

        try {
            PayrollPeriod period = periodRepository.findById(periodId)
                    .orElseThrow(() -> new RuntimeException("Payroll Period Not found"));

            payrollService.runPayroll(period);

            return ResponseEntity.ok(ApiResponse.success("Payroll processed successfully", period.getId().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/records/{periodId}")
    public ResponseEntity<ApiResponse<List<PayrollRecordDto>>> getEmployeePayRoll(@PathVariable Long periodId) {

        try {
            List<Payroll> records = payrollRepository.findByPayrollPeriodId(periodId);

            List<PayrollRecordDto> recordDTOS = records.stream()
                    .map(payrollMapper::convertPayroll)
                    .toList();
            return ResponseEntity.ok(ApiResponse.success("Employee Payroll", recordDTOS));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/employee/{employeeId}/period/{periodId}")
    public ResponseEntity<ApiResponse<List<PayrollRecordDto>>> getEmployeeRecords(@PathVariable Long employeeId, @PathVariable Long periodId) {
        try {
            List<Payroll> records = payrollRepository.findByEmployeeIdAndPayrollPeriodId(employeeId, periodId);

            List<PayrollRecordDto> recordDTOS = records.stream()
                    .map(payrollMapper::convertPayroll)
                    .toList();
            return ResponseEntity.ok(ApiResponse.success("Employee Payroll", recordDTOS));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
