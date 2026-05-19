package com.adverto.dejonghe.tablet;

import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Locale;

@SpringBootApplication(
        scanBasePackages = "com.adverto.dejonghe"
)
@EnableMongoRepositories(
        basePackages = "com.adverto.dejonghe.common.repos"
)
public class App implements AppShellConfigurator
{
    public static void main(String[] args) {
        Locale.setDefault(new Locale("nl", "BE"));
        SpringApplication.run(App.class, args);
    }
}
