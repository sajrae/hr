package com.company.hr.backoffice.form;

import com.company.hr.common.SystemSettings;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class SystemSettingForm extends FormLayout {

    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");

    private final TextField settingKey = new TextField("key");
    private final TextField settingValue = new TextField("value");
    private final TextField description = new TextField("description");

    private final Binder<SystemSettings> binder = new Binder<>(SystemSettings.class);

    public SystemSettingForm() {


        add(settingKey, settingValue, description, createButtons());
        binder.bindInstanceFields(this);
    }

    private HorizontalLayout createButtons() {
        return new HorizontalLayout(save, delete);
    }

    public void setSystemConfig(SystemSettings systemSettings) {
        binder.setBean(systemSettings);
    }

    public Button getSaveButton() {
        return save;
    }

    public Button getDeleteButton() {
        return delete;
    }
}
