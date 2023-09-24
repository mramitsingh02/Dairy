package com.generic.khatabook.rating;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@ComponentScan(basePackages = {"com.generic.khatabook.rating"})
@EnableJpaRepositories("com.generic.khatabook.rating.repository")
public class RatingServiceApplication {

    public static void main(String[] args) {
        run(RatingServiceApplication.class, args);
    }

}
