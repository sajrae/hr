package com.company.hr.backoffice.form;


import com.company.hr.core.entity.EmployeeSalary;
import com.company.hr.core.entity.OverTimeRule;
import com.company.hr.core.entity.TaxRule;
import com.company.hr.core.service.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeSalaryForm extends FormLayout {


    private final NumberField baseSalary = new NumberField("Base Salary");
    private final DatePicker effectiveDate = new DatePicker("Effective Date");
    private final ComboBox<OverTimeRule> overTimeRuleSelect = new ComboBox<>("Over Time");
    private final ComboBox<TaxRule> taxRuleSelect = new ComboBox<>("Tax rule");

    public final Binder<EmployeeSalary> binder = new Binder<>(EmployeeSalary.class);

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    public EmployeeSalaryForm(EmployeeService employeeService) {


        List<OverTimeRule> otRules = employeeService.getOverTime();
        overTimeRuleSelect.setItems(otRules);
        overTimeRuleSelect.setItemLabelGenerator(OverTimeRule::getName);

        List<TaxRule> taxRules = employeeService.getTaxRule();
        taxRuleSelect.setItems(taxRules);
        taxRuleSelect.setItemLabelGenerator(TaxRule::getName);


        add(baseSalary, effectiveDate, overTimeRuleSelect, taxRuleSelect, createButtons());

        binder.forField(baseSalary)
                .withConverter(
                        value -> value != null ? BigDecimal.valueOf(value) : null, // Double -> BigDecimal
                        value -> value != null ? value.doubleValue() : null          // BigDecimal -> Double
                )
                .bind(EmployeeSalary::getBaseSalary, EmployeeSalary::setBaseSalary);

        binder.bindInstanceFields(this);
    }

    public void setEmployeeSalary(EmployeeSalary employeeSalary) {
        binder.setBean(employeeSalary);
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
