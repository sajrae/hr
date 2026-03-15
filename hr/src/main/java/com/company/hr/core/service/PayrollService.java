package com.company.hr.core.service;

import com.company.hr.common.SystemSettingService;
import com.company.hr.common.constants.GlobalConstants;
import com.company.hr.common.enums.PayrollPeriodType;
import com.company.hr.common.enums.PayrollType;
import com.company.hr.core.entity.Attendance;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.EmployeeSalary;
import com.company.hr.core.entity.Payroll;
import com.company.hr.core.entity.PayrollPeriod;
import com.company.hr.core.entity.PayrollSchedule;
import com.company.hr.core.repository.AttendanceRepository;
import com.company.hr.core.repository.EmployeeLeaveRepository;
import com.company.hr.core.repository.EmployeeRepository;
import com.company.hr.core.repository.EmployeeSalaryRepository;
import com.company.hr.core.repository.PayrollPeriodRepository;
import com.company.hr.core.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static com.company.hr.common.enums.PayrollType.MONTHLY;
import static com.company.hr.common.enums.PayrollType.SEMI_MONTHLY;
import static com.company.hr.common.enums.PayrollType.WEEKLY;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private static final Logger log = LogManager.getLogger(PayrollService.class);

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final PayrollRepository payrollRepository;
    private final SystemSettingService settingService;
    private final EmployeeSalaryRepository salaryRepository;
    private final EmployeeLeaveRepository leaveRepository;
    private final DeductionService deductionService;
    private final PayrollPeriodRepository periodRepository;

    public void runPayroll(final PayrollPeriod period) {

        if (!period.getStatus().equals(PayrollPeriodType.OPEN)) {
            throw new IllegalStateException("Payroll period already processed");
        }

        List<Employee> employeeList = employeeRepository.findAll();
        List<Payroll> payrolls = new ArrayList<>();

        if (CollectionUtils.isEmpty(employeeList)) {
            throw new NullPointerException("No Employees Found");
        }

        for (Employee employee : employeeList) {
            PayrollSchedule schedule = employee.getPayrollSchedule();

            switch (schedule.getPayrollType()) {
                case WEEKLY:
                    List<PayrollPeriod> weeks = splitIntoWeeks(period);
                    for (PayrollPeriod week : weeks) {
                        payrolls.add(calculatePayrollForEmployee(employee, week));

                    }
                    break;

                case PayrollType.SEMI_MONTHLY:
                    List<PayrollPeriod> semiMonths = splitIntoSemiMonths(period);
                    for (PayrollPeriod semi : semiMonths) {
                        payrolls.add(calculatePayrollForEmployee(employee, semi));
                    }
                    break;
                case PayrollType.MONTHLY, PayrollType.DAILY:
                    // Daily treated same as custom period
                    payrolls.add(calculatePayrollForEmployee(employee, period));
                    break;

                default:
                    throw new IllegalStateException("Unknown payroll type: " + schedule.getPayrollType());
            }
        }
        payrolls.forEach(p -> p.setPayrollPeriod(period));
        payrollRepository.saveAll(payrolls);
        period.setStatus(PayrollPeriodType.PROCESSED);
        periodRepository.save(period);

    }

    private Payroll calculatePayrollForEmployee(final Employee employee, final PayrollPeriod period) {

        List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndDateBetween(employee.getId(), period.getStartDate(), period.getEndDate());
        EmployeeSalary salary = salaryRepository.findByEmployeeId(employee.getId()).orElseThrow(() -> new RuntimeException("Employee Salary Not found"));
        int unPaidLeave = leaveRepository.countByEmployeeIdAndLeaveType_IsPaid(employee.getId(), false);

        double totalOverTime = attendanceList.stream()
                .mapToDouble(Attendance::getOvertimeHours)
                .sum();


        int minimumWorkHours = settingService.getInt(GlobalConstants.MINIMUM_WORK_HOURS);
        int attendanceDays = attendanceList.size();
        log.info("Attendance Days for Employee: {}", attendanceDays);
        int workingDays = calculateWorkingDays(period.getStartDate(), period.getEndDate());
        log.info("Working Days by Period: {}", workingDays);
        if (workingDays <= 0) {
            throw new IllegalStateException("Working days must be greater than 0 for payroll calculation");
        }
        int absentDays = Math.max(0, workingDays - attendanceDays - unPaidLeave);
        log.info("Employee Absent days: {} - Employee Total Paid Leaves - {}", absentDays, unPaidLeave);

        int workingDaysForTheMonth = getWorkingDaysOfMonth();
        log.info("Working days for the month: {}", workingDaysForTheMonth);
        if (workingDaysForTheMonth <= 0) {
            throw new IllegalStateException("Working days of the month must be greater than 0 for payroll calculation");
        }

        BigDecimal monthlySalary = salary.getBaseSalary();
        //BigDecimal dailyRate = monthlySalary.divide(BigDecimal.valueOf(workingDays), 2, RoundingMode.HALF_UP);
        BigDecimal periodDailyRate = monthlySalary.divide(BigDecimal.valueOf(workingDaysForTheMonth), 2, RoundingMode.HALF_UP);
        BigDecimal periodSalary = getPeriodSalary(employee.getPayrollSchedule(), monthlySalary, attendanceDays, workingDaysForTheMonth, periodDailyRate);
        BigDecimal hourlyRate = periodDailyRate.divide(BigDecimal.valueOf(minimumWorkHours), 2, RoundingMode.HALF_UP);
        BigDecimal overTimePay = hourlyRate.multiply(BigDecimal.valueOf(totalOverTime)).multiply(BigDecimal.valueOf(salary.getOverTimeRule().getMultiplier()));

        BigDecimal lateMin = BigDecimal.valueOf(attendanceList.stream()
                .mapToDouble(Attendance::getLateMinutes)
                .sum());
        BigDecimal lateHours = lateMin.divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        BigDecimal lateDeduction = lateHours.multiply(hourlyRate);

        BigDecimal absentDeduction = periodDailyRate.multiply(BigDecimal.valueOf(absentDays));
        BigDecimal unpaidLeaveDeduction = periodDailyRate.multiply(BigDecimal.valueOf(unPaidLeave));
        BigDecimal totalLeaveDeduction = absentDeduction.add(unpaidLeaveDeduction);

        BigDecimal sss = deductionService.calculateContribution(employee, GlobalConstants.GOVERMENT_SSS);
        BigDecimal philHealth = deductionService.calculateContribution(employee, GlobalConstants.GOVERMENT_PHIL);
        BigDecimal pagibig = deductionService.calculateContribution(employee, GlobalConstants.GOVERMENT_PAG_IBIG);

        BigDecimal earnedSalary = periodDailyRate.multiply(BigDecimal.valueOf(attendanceDays));
        BigDecimal gross = getGrossSalary(earnedSalary, periodSalary, employee.getPayrollSchedule(), overTimePay, lateDeduction, totalLeaveDeduction);


        BigDecimal totalDeductions = sss.add(philHealth).add(pagibig);
        BigDecimal netSalary = gross.subtract(totalDeductions);

        Payroll payroll = new Payroll();
        payroll.setEmployee(employee);
        payroll.setPayrollPeriod(period);
        payroll.setBasicSalary(monthlySalary);
        payroll.setTotalAllowance(BigDecimal.ZERO);
        payroll.setOvertimePay(overTimePay);
        payroll.setLateDeduction(lateDeduction);
        payroll.setLeaveDeduction(totalLeaveDeduction);
        payroll.setSss(sss);
        payroll.setPhilHealth(philHealth);
        payroll.setPagibig(pagibig);
        payroll.setGrossSalary(gross);
        payroll.setNetSalary(netSalary);

        return payroll;
    }

    private BigDecimal getGrossSalary(final BigDecimal earnedSalary, final BigDecimal periodSalary, final PayrollSchedule payrollSchedule,
                                      final BigDecimal overTimePay, final BigDecimal lateDeduction, final BigDecimal totalLeaveDeduction) {
        BigDecimal gross;
        if (payrollSchedule.getPayrollType().equals(SEMI_MONTHLY) ||
                payrollSchedule.getPayrollType().equals(MONTHLY)) {
            // Use fixed prorated salary for period
            gross = periodSalary.add(overTimePay).subtract(lateDeduction.add(totalLeaveDeduction));
        } else {
            // Daily / attendance-based payroll
            gross = earnedSalary.add(overTimePay).subtract(lateDeduction.add(totalLeaveDeduction));
        }
        return gross;
    }

    private BigDecimal getPeriodSalary(final PayrollSchedule payrollSchedule, final BigDecimal monthlySalary, final int attendanceDays, final int workingDaysForTheMonth, BigDecimal periodDailyRate) {

        BigDecimal periodSalary;
        if (payrollSchedule.getPayrollType() == SEMI_MONTHLY) {
            periodSalary = monthlySalary.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP); // fixed 50%
        } else if (payrollSchedule.getPayrollType() == MONTHLY) {
            periodSalary = monthlySalary;
        } else if (payrollSchedule.getPayrollType() == WEEKLY) {
            periodSalary = monthlySalary.multiply(BigDecimal.valueOf(7))
                    .divide(BigDecimal.valueOf(workingDaysForTheMonth), 2, RoundingMode.HALF_UP);
        } else {
            // Daily payroll = dailyRate * attendanceDays
            periodSalary = periodDailyRate.multiply(BigDecimal.valueOf(attendanceDays));
        }

        return periodSalary;
    }

    private int getWorkingDaysOfMonth() {
        YearMonth yearMonth = YearMonth.now();
        int workingDays = 0;

        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);

            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }
        return workingDays;
    }

    private int calculateWorkingDays(LocalDate start, LocalDate end) {
        int days = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            DayOfWeek dow = date.getDayOfWeek();
            if (dow != DayOfWeek.SUNDAY) {
                days++;
            }
            date = date.plusDays(1);
        }
        return days;
    }

    private List<PayrollPeriod> splitIntoWeeks(PayrollPeriod period) {
        List<PayrollPeriod> weeks = new ArrayList<>();
        LocalDate start = period.getStartDate();
        LocalDate end = period.getEndDate();

        while (!start.isAfter(end)) {
            LocalDate weekEnd = start.plusDays(6);
            if (weekEnd.isAfter(end)) weekEnd = end;

            PayrollPeriod week = new PayrollPeriod();
            week.setStartDate(start);
            week.setEndDate(weekEnd);
            weeks.add(week);

            start = weekEnd.plusDays(1);
        }
        return weeks;
    }

    private List<PayrollPeriod> splitIntoSemiMonths(PayrollPeriod period) {
        List<PayrollPeriod> semiMonths = new ArrayList<>();
        LocalDate start = period.getStartDate();
        LocalDate mid = LocalDate.of(start.getYear(), start.getMonth(), 15);
        LocalDate end = period.getEndDate();

        if (!start.isAfter(mid)) {
            PayrollPeriod firstHalf = new PayrollPeriod();
            firstHalf.setStartDate(start);
            firstHalf.setEndDate(mid.isAfter(end) ? end : mid);
            semiMonths.add(firstHalf);
        }

        if (mid.plusDays(1).isBefore(end) || mid.plusDays(1).isEqual(end)) {
            PayrollPeriod secondHalf = new PayrollPeriod();
            secondHalf.setStartDate(mid.plusDays(1));
            secondHalf.setEndDate(end);
            semiMonths.add(secondHalf);
        }
        return semiMonths;
    }
}
