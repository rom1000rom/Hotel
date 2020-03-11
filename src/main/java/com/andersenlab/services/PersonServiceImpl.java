package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**Класс реализует сервисные функции по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    public static final String EXCEPTION_MESSAGE = "Such a person does not exist";


    @Override
    public List<Person> findAllPersons() {
        return (List<Person>)personRepository.findAll();
    }

    @Override
    public Person findPersonById(Long id) throws HotelServiceException {
        return personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
    }

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Long deletePerson(Long id) {
        if(personRepository.findById(id).isPresent()) {//Если Person с таким id существует
            personRepository.deleteById(id);
            return id;
        }
        else {
            throw new HotelServiceException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public Long addToBlacklist(Long id)  {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {//Если Person с таким id существует
            person.get().setBlacklisted(true);
            return id;
        }
        else {
            throw new HotelServiceException(EXCEPTION_MESSAGE);
        }
    }

    @Override
    public Long removeFromBlacklist(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()) {//Если Person с таким id существует
            person.get().setBlacklisted(false);
            return id;
        }
        else {
            throw new HotelServiceException(EXCEPTION_MESSAGE);
        }
    }


}
