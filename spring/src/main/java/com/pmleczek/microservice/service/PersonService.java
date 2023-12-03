package com.pmleczek.microservice.service;

import com.pmleczek.microservice.entity.Person;
import com.pmleczek.microservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public Person getPerson(String surname) {
        return personRepository.findBySurname(surname);
    }

    public Person getPerson(long id) {
        Optional<Person> personOptional = personRepository.findById(id);

        return personOptional.orElseGet(() -> null);
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }
}
