package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**Класс реализует сервисные функции по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> findAllPersons() {
        return (List<Person>)personRepository.findAll();
    }

    @Override
    public Person findPersonById(Long id) {
        if(id == null)
            return null;
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person savePerson(Person person) {
        if(person == null)
            return null;
        return personRepository.save(person);
    }

    @Override
    public Long deletePerson(Long id) {
        if(id == null)
            return null;
        if(personRepository.findById(id).isPresent()) {//Если Person с таким id существует
            personRepository.deleteById(id);
            return id;
        }
        else {
            return null;
        }
    }

    @Override
    public Long addToBlacklist(Long id) {
        if(id == null)
            return null;
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {//Если Person с таким id существует
            person.get().setBlacklisted(true);
            return id;
        }
        else {
            return null;
        }
    }

    @Override
    public Long removeFromBlacklist(Long id) {
        if(id == null)
            return null;
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {//Если Person с таким id существует
            person.get().setBlacklisted(false);
            return id;
        }
        else {
            return null;
        }
    }


}
