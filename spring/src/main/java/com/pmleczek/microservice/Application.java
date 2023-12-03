package com.pmleczek.microservice;

import com.pmleczek.microservice.entity.Person;
import com.pmleczek.microservice.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(PersonRepository repository) {
        return (args) -> {
            // Save 2 people
            repository.save(new Person("John", "Doe", "IT"));
            repository.save(new Person("John", "Smith", "Tester"));

            // Fetch all people
            logger.info("People found with findAll()");
            repository.findAll().forEach(person -> {
                logger.info(person.toString());
            });
        };
    }

}
