package com.vira.prototype;

import com.vira.prototype.controller.rest.CarResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.vira.prototype.persistence.repo")
@EntityScan("com.vira.prototype.persistence.model")
@ComponentScan(value = {"com.vira.prototype.persistence.service","com.vira.prototype.controller.rest"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
