package com.generic.khatabook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.generic.*"})
@EnableJpaRepositories("com.generic.khatabook.repository")
public class GenericKhatabookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericKhatabookApplication.class, args);
    }

}
