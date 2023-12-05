package com.pmleczek.microservice.controller;

import com.pmleczek.microservice.dto.Greeting;
import com.pmleczek.microservice.entity.Person;
import com.pmleczek.microservice.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {
    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/hello")
    public ResponseEntity<Greeting> hello(@RequestParam(defaultValue = "World", name = "name") String name) {
        return ResponseEntity.ok(new Greeting("Hello, " + name + "!"));
    }

    @GetMapping("/person")
    public ResponseEntity<List<Person>> getPersons() {
        return ResponseEntity.ok(personService.getPersons());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable long id) {
        Person person = personService.getPerson(id);

        if (person == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(person);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.ok(personService.create(person));
    }
}
