package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dto.PersonDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**Класс реализует сервисные функции по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    private static final String EXCEPTION_MESSAGE = "Such a person does not exist";

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Override
    public List<PersonDTO> findAllPersons() {
        mapperFactory.classMap(Person.class, PersonDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        List<Person> listPerson = (List<Person>)personRepository.findAll();
        return listPerson.stream().map((person) -> mapper.map(person, PersonDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PersonDTO findPersonById(Long id) {
        mapperFactory.classMap(Person.class, PersonDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapper.map(person, PersonDTO.class);
    }

    @Override
    public Long savePerson(PersonDTO personDTO) {
        mapperFactory.classMap(PersonDTO.class, Person.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Person person = personRepository.save(mapper.map(personDTO, Person.class));

        return person.getId();
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
