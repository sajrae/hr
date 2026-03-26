package com.company.hr.config;

import com.company.hr.backoffice.view.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Allow public endpoints if needed (API for Flutter later)
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true));

        super.configure(http);

        // Vaadin login view
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}nimda") // no encoding for now
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
