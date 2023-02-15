package com.project.borad2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Borad2Application {

    public static void main(String[] args) {
        SpringApplication.run(Borad2Application.class, args);
    }

}
