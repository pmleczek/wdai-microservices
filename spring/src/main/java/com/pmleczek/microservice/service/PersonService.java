package com.pmleczek.microservice.service;

import com.pmleczek.microservice.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPersons();


    Person getPerson(String surname);
    Person getPerson(long id);

    Person create(Person person);
}
