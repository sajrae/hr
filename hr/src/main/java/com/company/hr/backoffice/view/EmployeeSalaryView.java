package com.company.hr.backoffice.view;


import com.company.hr.backoffice.BackofficeUtil;

import com.company.hr.backoffice.form.EmployeeSalaryForm;

import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.EmployeeSalary;
import com.company.hr.core.entity.OverTimeRule;

import com.company.hr.core.entity.TaxRule;
import com.company.hr.core.service.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;


import java.util.Optional;

@RolesAllowed("ADMIN")
@Route(value = "/backoffice/salary", layout = MainLayout.class)
public class EmployeeSalaryView extends VerticalLayout implements BeforeEnterObserver {

    private final Grid<Employee> grid = new Grid<>(Employee.class);
    private final transient EmployeeService employeeService;


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        grid.setItems(employeeService.getEmployee());
    }

    public EmployeeSalaryView(EmployeeService employeeService) {
        this.employeeService = employeeService;


        grid.setColumns("employeeCode");
        grid.setItems(employeeService.getEmployee());

        grid.addColumn(emp -> emp.getEmployeeSalary() != null ? emp.getEmployeeSalary().getBaseSalary() : "")
                .setHeader("Salary")
                .setSortable(true);


        grid.addColumn(emp -> emp.getEmployeeSalary() != null ? emp.getEmployeeSalary().getEffectiveDate() : "")
                .setHeader("Effective Date")
                .setSortable(true);

        grid.addColumn(emp -> Optional.ofNullable(emp.getEmployeeSalary())
                        .map(EmployeeSalary::getOverTimeRule)
                        .map(OverTimeRule::getName)
                        .orElse(""))
                .setHeader("Over Time Rule")
                .setSortable(true);

        grid.addColumn(emp -> Optional.ofNullable(emp.getEmployeeSalary())
                        .map(EmployeeSalary::getTaxRule)
                        .map(TaxRule::getName)
                        .orElse(""))
                .setHeader("Tax Rule")
                .setSortable(true);

        BackofficeUtil.addAuditColumns(grid);

        grid.addComponentColumn(emp -> {
            Button salaryButton = new Button();

            // Determine label dynamically
            if (emp.getEmployeeSalary() == null) {
                salaryButton.setText("Add Salary");
            } else {
                salaryButton.setText("Edit Salary");
            }

            // Click listener opens dialog with employee
            salaryButton.addClickListener(e -> openDialog(emp));

            return salaryButton;
        }).setHeader("Action").setFlexGrow(0); // keep button column narrow


        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidthFull();

        customizeToolBar(toolbar);
        add(toolbar, grid);
        expand(grid); // grid fills the remaining space

    }

    private static void customizeToolBar(HorizontalLayout toolbar) {
        toolbar.setPadding(true);
        toolbar.setSpacing(true);
        toolbar.getStyle().set("background-color", "#f5f5f5");
    }

    private void openDialog(Employee employee) {

        EmployeeSalary salary = employee.getEmployeeSalary() != null
                ? employee.getEmployeeSalary()
                : new EmployeeSalary();

        EmployeeSalaryForm form = new EmployeeSalaryForm(employeeService);
        form.setEmployeeSalary(salary);

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Salary Editor");
        dialog.setWidth("800px");
        dialog.setHeight("500px");

        dialog.add(form);

        // --- Save button ---
        form.getSaveButton().addClickListener(e -> {
            // validate all bound fields
            EmployeeSalary savedSalary = form.binder.getBean();

            // Save via service
            employeeService.saveSalary(savedSalary, employee);

            // Link back to employee for bi-directional
            employee.setEmployeeSalary(savedSalary);
            employeeService.saveEmployee(employee);
            // Refresh grid
            grid.getDataProvider().refreshItem(employee);

            dialog.close();
            Notification.show("Salary saved for " + employee.getEmployeeCode());

        });

        // --- Delete button ---
        form.getDeleteButton().addClickListener(e -> {
            if (employee.getEmployeeSalary() != null) {
                employeeService.deleteSalary(employee.getEmployeeSalary());

                // Unlink employee
                employee.setEmployeeSalary(null);
                employeeService.saveEmployee(employee);
                grid.getDataProvider().refreshItem(employee);

                dialog.close();
                Notification.show("Salary deleted for " + employee.getEmployeeCode());
            } else {
                Notification.show("No salary to delete");
            }
        });

        dialog.open();
    }

}
