package com.company.hr.backoffice.view;

import com.company.hr.backoffice.BackofficeUtil;
import com.company.hr.backoffice.form.SystemSettingForm;
import com.company.hr.common.SystemSettingService;
import com.company.hr.common.SystemSettings;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = "/backoffice/config", layout = MainLayout.class)

public class SystemSettingView extends VerticalLayout {

    private final transient SystemSettingService settingService;
    private final Grid<SystemSettings> grid = new Grid<>(SystemSettings.class);

    public SystemSettingView(SystemSettingService settingService) {

        this.settingService = settingService;
        setSizeFull();

// --- Grid setup ---
        grid.setColumns("id", "settingKey", "settingValue", "description");
        grid.setItems(settingService.findAll());
        grid.setSizeFull();

        BackofficeUtil.addAuditColumns(grid);

        Button addButton = new Button("Add Config");
        addButton.addClickListener(e -> openDialog(new SystemSettings()));

        TextField searchField = searchField(settingService);
        HorizontalLayout toolbar = new HorizontalLayout(addButton, searchField);
        toolbar.setWidthFull();
        toolbar.expand(searchField);
        customizeToolBar(toolbar);
        add(toolbar, grid);
        expand(grid); // grid fills the remaining space

        // --- Grid selection opens dialog ---
        grid.asSingleSelect().addValueChangeListener(event -> {
            SystemSettings selected = event.getValue();
            if (selected != null) {
                openDialog(selected);
            }
        });
    }

    private void openDialog(SystemSettings systemSettings) {

        SystemSettingForm form = new SystemSettingForm();
        form.setSystemConfig(systemSettings);

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("System Config");
        dialog.setWidth("400px");
        dialog.setHeight("300px");
        dialog.add(form);

        BackofficeUtil.bindSaveDeleteButtons(form.getSaveButton(), form.getDeleteButton(),
                grid, systemSettings, settingService::save, settingService::delete, dialog);

        dialog.open();
    }

    private static void customizeToolBar(HorizontalLayout toolbar) {
        toolbar.setPadding(true);
        toolbar.setSpacing(true);
        toolbar.getStyle().set("background-color", "#f5f5f5");
    }

    private TextField searchField(SystemSettingService settingService) {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search Config...");
        searchField.setClearButtonVisible(true);
        searchField.setWidth("300px");

        // Add listener to filter grid
        searchField.addValueChangeListener(event -> {
            String filter = event.getValue().trim().toLowerCase();
            if (filter.isEmpty()) {
                grid.setItems(settingService.findAll());
            } else {
                grid.setItems(settingService.findAll().stream()
                        .filter(settings ->
                                settings.getSettingValue().contains(filter) ||
                                        settings.getSettingKey().contains(filter)
                        ).toList()
                );
            }
        });

        return searchField;
    }
}
