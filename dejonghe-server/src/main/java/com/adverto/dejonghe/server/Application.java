package com.adverto.dejonghe.server;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Locale;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication(
        scanBasePackages = "com.adverto.dejonghe"
)
@EnableMongoRepositories(
        basePackages = "com.adverto.dejonghe.common.repos"
)
@Theme(value = "dejonghe-app")
public class Application implements AppShellConfigurator {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("nl", "BE"));
        SpringApplication.run(Application.class, args);
    }
}
