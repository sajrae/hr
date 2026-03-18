package com.company.hr.backoffice.view;

import com.company.hr.backoffice.BackofficeUtil;
import com.company.hr.backoffice.form.EmployeeForm;
import com.company.hr.core.entity.Department;
import com.company.hr.core.entity.Employee;
import com.company.hr.core.entity.PayrollSchedule;
import com.company.hr.core.entity.Position;
import com.company.hr.core.service.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@RolesAllowed("ADMIN")
@Route(value = "/backoffice/employees", layout = MainLayout.class)
public class EmployeeView extends VerticalLayout implements BeforeEnterObserver {

    private final Grid<Employee> grid = new Grid<>(Employee.class);

    private final transient EmployeeService employeeService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        grid.setItems(employeeService.findAll());
    }

    public EmployeeView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        setSizeFull();

        // --- Grid setup ---
        grid.setColumns("id", "employeeCode", "firstName", "lastName");
        grid.setItems(employeeService.findAll());
        grid.setSizeFull();
        grid.setColumnReorderingAllowed(true);
        // Nested fields
        grid.addColumn(emp -> emp.getDepartment() != null ? emp.getDepartment().getName() : "")
                .setHeader("Department")
                .setSortable(true);

        grid.addColumn(emp -> emp.getPosition() != null ? emp.getPosition().getTitle() : "")
                .setHeader("Position")
                .setSortable(true);

        grid.addColumn(emp -> emp.getPayrollSchedule() != null ? emp.getPayrollSchedule().getName() : "")
                .setHeader("Payroll Schedule")
                .setSortable(true);

        BackofficeUtil.addAuditColumns(grid);

        // --- Toolbar ---
        Button addButton = new Button("Add Employee");
        addButton.addClickListener(e -> openDialog(new Employee()));

        TextField searchField = searchField(employeeService);
        HorizontalLayout toolbar = new HorizontalLayout(addButton, searchField);
        toolbar.setWidthFull();
        toolbar.expand(searchField);
        customizeToolBar(toolbar);
        add(toolbar, grid);
        expand(grid); // grid fills the remaining space

        // --- Grid selection opens dialog ---
        grid.asSingleSelect().addValueChangeListener(event -> {
            Employee selected = event.getValue();
            if (selected != null) {
                openDialog(selected);
            }
        });
    }


    private static void customizeToolBar(HorizontalLayout toolbar) {
        toolbar.setPadding(true);
        toolbar.setSpacing(true);
        toolbar.getStyle().set("background-color", "#f5f5f5");
    }

    // --- Method to open dialog with EmployeeForm ---
    private void openDialog(Employee employee) {

        List<Department> departments = employeeService.getAllDepartment();
        List<Position> positions = employeeService.getPosition();
        List<PayrollSchedule> payrollSchedules = employeeService.getAllPayroll();

        EmployeeForm form = new EmployeeForm(departments, positions, payrollSchedules);
        form.setEmployee(employee);

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Employee Editor");
        dialog.setWidth("800px");
        dialog.setHeight("500px");
        dialog.add(form);

        BackofficeUtil.bindSaveDeleteButtons(form.getSaveButton(), form.getDeleteButton(),
                grid, employee, employeeService::save, employeeService::delete, dialog, employeeService::findAll);

        dialog.open();
    }

    private TextField searchField(EmployeeService employeeService) {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search employees...");
        searchField.setClearButtonVisible(true);
        searchField.setWidth("300px");

        // Add listener to filter grid
        searchField.addValueChangeListener(event -> {
            String filter = event.getValue().trim().toLowerCase();
            if (filter.isEmpty()) {
                grid.setItems(employeeService.findAll());
            } else {
                grid.setItems(employeeService.findAll().stream()
                        .filter(emp ->
                                emp.getEmployeeCode().toLowerCase().contains(filter) ||
                                        emp.getFirstName().toLowerCase().contains(filter) ||
                                        emp.getLastName().toLowerCase().contains(filter) ||
                                        (emp.getDepartment() != null && emp.getDepartment().getName().toLowerCase().contains(filter)) ||
                                        (emp.getPosition() != null && emp.getPosition().getTitle().toLowerCase().contains(filter))
                        ).toList()
                );
            }
        });

        return searchField;
    }
}
