package com.company.hr.core.service;

import com.company.hr.common.SystemSettingService;
import com.company.hr.common.constants.GlobalConstants;
import com.company.hr.core.entity.Payroll;
import com.company.hr.core.entity.PayrollPeriod;
import com.company.hr.core.entity.Payslip;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PdfService {

    private SystemSettingService settingService;

    public byte[] generatePayslipPDF(Payslip payslip) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            content.setFont(font, 16);

            final String companyName = settingService.get(GlobalConstants.COMPANY_NAME, "Company A");

            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText(companyName);
            content.endText();

            content.setFont(font, 14);

            content.beginText();
            content.newLineAtOffset(50, 720);
            content.showText("Payslip");
            content.endText();

            content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);

            String fullName = payslip.getEmployee().getLastName() + "," + payslip.getEmployee().getFirstName();
            content.beginText();
            content.newLineAtOffset(50, 690);
            content.showText("Employee: " + fullName);
            content.endText();

            PayrollPeriod period = payslip.getPayroll().getPayrollPeriod();
            content.beginText();
            content.newLineAtOffset(50, 670);
            content.showText("Period: " + period.getStartDate() + " - " + period.getEndDate());
            content.endText();

            int y = 630;

            Payroll payroll = payslip.getPayroll();

            y = writeRow(content, "Basic Salary", payroll.getBasicSalary(), y);
            y = writeRow(content, "Overtime Pay", payroll.getOvertimePay(), y);
            y = writeRow(content, "Leave Deduction", payroll.getLeaveDeduction(), y);

            y = writeRow(content, "SSS", payroll.getSss(), y);
            y = writeRow(content, "PhilHealth", payroll.getPhilHealth(), y);
            y = writeRow(content, "Pag-IBIG", payroll.getPagibig(), y);

            y = writeRow(content, "Gross Salary", payroll.getGrossSalary(), y);
            y = writeRow(content, "Net Salary", payroll.getNetSalary(), y);

            content.close();

            document.save(outputStream);
        }

        return outputStream.toByteArray();
    }

    private int writeRow(PDPageContentStream content, String label, BigDecimal value, int y) throws IOException {

        content.beginText();
        content.newLineAtOffset(50, y);
        content.showText(label);
        content.endText();

        content.beginText();
        content.newLineAtOffset(300, y);
        content.showText(value.toString());
        content.endText();

        return y - 20;
    }
}
