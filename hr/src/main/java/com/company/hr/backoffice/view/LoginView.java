package com.company.hr.backoffice.view;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView() {
        LoginForm login = new LoginForm();
        login.setAction("login");

        setSizeFull(); // take full screen

        // Center horizontally
        setAlignItems(Alignment.CENTER);

        // Center vertically
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(login);
    }
}
