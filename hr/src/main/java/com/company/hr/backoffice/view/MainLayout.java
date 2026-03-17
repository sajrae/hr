package com.company.hr.backoffice.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class MainLayout extends AppLayout {

    public MainLayout() {

        H1 logo = new H1("HR Backoffice");
        logo.addClassName("logo");

        addToNavbar(new DrawerToggle(), logo);

        SideNav nav = new SideNav();

        nav.addItem(createNavItem("Employees", EmployeeView.class));
        nav.addItem(createNavItem("Department", DepartmentView.class));
        nav.addItem(createNavItem("System Config", SystemSettingView.class));

        addToDrawer(nav);
    }

    private SideNavItem createNavItem(String label, Class<? extends Component> view) {
        return new SideNavItem(label, view);
    }
}
