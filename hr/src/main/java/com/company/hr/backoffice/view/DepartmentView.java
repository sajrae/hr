package com.company.hr.backoffice.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@RolesAllowed("ADMIN")
@Route(value = "/backoffice/department", layout = MainLayout.class)
public class DepartmentView extends VerticalLayout {
}
