package com.company.hr.backoffice;

import com.company.hr.common.BaseEntity;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

import java.util.function.Consumer;

public class BackofficeUtil {

    private BackofficeUtil() {
        /* This utility class should not be instantiated */
    }


    /**
     * Adds common audit columns to any Grid of entities extending BaseEntity
     *
     * @param grid The grid to add audit columns to
     * @param <T>  Entity type extending BaseEntity
     */
    public static <T extends BaseEntity> void addAuditColumns(Grid<T> grid) {
        grid.addColumn(BaseEntity::getCreatedBy)
                .setHeader("Created By")
                .setSortable(true);

        grid.addColumn(BaseEntity::getUpdatedBy)
                .setHeader("Updated By")
                .setSortable(true);

        grid.addColumn(BaseEntity::getCreationTime)
                .setHeader("Creation Time")
                .setSortable(true);

        grid.addColumn(BaseEntity::getModifiedTime)
                .setHeader("Modified Time")
                .setSortable(true);
    }

    /**
     * Binds Save/Delete buttons of a form to standard actions: save, delete, refresh grid, and close dialog.
     *
     * @param saveButton   Save button from the form
     * @param deleteButton Delete button from the form
     * @param grid         Grid to refresh after operation
     * @param entity       Entity being edited
     * @param saveAction   How to save the entity (e.g., service::save)
     * @param deleteAction How to delete the entity (e.g., service::delete)
     * @param dialog       Dialog that should be closed after operation
     * @param <T>          Entity type
     */
    public static <T> void bindSaveDeleteButtons(
            Button saveButton,
            Button deleteButton,
            Grid<T> grid,
            T entity,
            Consumer<T> saveAction,
            Consumer<T> deleteAction,
            Dialog dialog
    ) {
        saveButton.addClickListener(e -> {
            saveAction.accept(entity);
            grid.setItems(grid.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).toList()); // refresh grid
            dialog.close();
        });

        deleteButton.addClickListener(e -> {
            deleteAction.accept(entity);
            grid.setItems(grid.getDataProvider().fetch(new com.vaadin.flow.data.provider.Query<>()).toList()); // refresh grid
            dialog.close();
        });
    }
}
