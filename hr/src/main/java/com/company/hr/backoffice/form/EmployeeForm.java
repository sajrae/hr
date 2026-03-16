package com.company.hr.backoffice.form;

import com.company.hr.core.entity.Department;
import com.company.hr.core.entity.Employee;

import com.company.hr.core.entity.PayrollSchedule;
import com.company.hr.core.entity.Position;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class EmployeeForm extends FormLayout {

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    private final TextField lastName = new TextField("Last Name");
    private final TextField firstName = new TextField("First Name");
    private final EmailField email = new EmailField("Email");
    private final TextField phone = new TextField("Phone");
    private final DatePicker hireDate = new DatePicker("Hire Date");

    private final ComboBox<PayrollSchedule> payrollSchedule = new ComboBox<>("Payroll Schedule");
    private ComboBox<Department> department = new ComboBox<>("Department");
    private ComboBox<Position> position = new ComboBox<>("Position");
    private ComboBox<String> status = new ComboBox<>("Status");

    private final Binder<Employee> binder = new Binder<>(Employee.class);

    public EmployeeForm(List<Department> departments,
                        List<Position> positions,
                        List<PayrollSchedule> payrollSchedules) {


        status.setItems("Active", "Inactive");

        // Drop-downs for entities

        department.setItems(departments);
        department.setItemLabelGenerator(Department::getName);


        position.setItems(positions);
        position.setItemLabelGenerator(Position::getTitle);


        payrollSchedule.setItems(payrollSchedules);
        payrollSchedule.setItemLabelGenerator(PayrollSchedule::getName);


        add(firstName, lastName, email, phone, status, hireDate, department, position, payrollSchedule, createButtons());

        binder.bindInstanceFields(this);
    }

    public void setEmployee(Employee employee) {
        binder.setBean(employee);
    }

    private HorizontalLayout createButtons() {
        return new HorizontalLayout(save, delete);
    }

    public Button getSaveButton() {
        return save;
    }

    public Button getDeleteButton() {
        return delete;
    }
}
