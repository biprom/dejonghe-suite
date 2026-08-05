package com.adverto.dejonghe.tabletdemo.tabletdemo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
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
@Theme(value = "dejonghe-tablet")
public class TabletDemoApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("nl", "BE"));
        SpringApplication.run(TabletDemoApplication.class, args);
    }

}
