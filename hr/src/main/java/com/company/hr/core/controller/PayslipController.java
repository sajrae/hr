package com.company.hr.core.controller;


import com.company.hr.core.entity.Payroll;
import com.company.hr.core.entity.Payslip;
import com.company.hr.core.mapper.PayslipMapper;
import com.company.hr.core.service.PayrollService;
import com.company.hr.core.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/payslip")
public class PayslipController {

    private final PayrollService payrollService;
    private final PdfService pdfService;
    private final PayslipMapper payslipMapper;

    @PostMapping("/{payrollId}")
    public ResponseEntity<byte[]> generatePayslip(@PathVariable Long payrollId) throws IOException {

        final Payroll payroll = payrollService.getRecord(payrollId);
        final Payslip payslip = payrollService.processPayslip(payroll);
        final byte[] pdf = pdfService.generatePayslipPDF(payslip);

        final String fileName = String.format("attachment; filename=%s_payslip.pdf", payslip.getEmployee().getEmployeeCode());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
