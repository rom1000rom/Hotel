package com.andersenlab.service.impl;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistartionDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.service.PersonService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**Класс реализует сервисные функции по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
@Transactional//Позволяет избежать ошибки "ленивой" инициализации прокси
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MapperFacade mapperFacade;

    private static final String EXCEPTION_MESSAGE = "Such a person does not exist";

    @Override
    public List<PersonDto> findAllPersons() {
        List<Person> listPerson = personRepository.findAll();
        return mapperFacade.mapAsList(listPerson, PersonDto.class);
    }

    @Override
    public PersonDto findPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(person, PersonDto.class);
    }

    @Override
    public Long savePerson(PersonRegistartionDto personRegistartionDto) {
        return personRepository.save(mapperFacade.map(personRegistartionDto, Person.class)).getId();
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
    public PersonRegistartionDto updatePerson(PersonRegistartionDto personRegistartionDto) {
        Person person = personRepository.findById(personRegistartionDto.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));

        person.setPersonName(personRegistartionDto.getPersonName());
        person.setEncrytedPassword(personRegistartionDto.getEncrytedPassword());

            return mapperFacade.map(personRepository.save(person), PersonRegistartionDto.class);
    }

    @Override
    public Long addToBlacklist(Long id)  {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        person.setBlacklisted(true);//Обновили запись
        personRepository.save(person);//Чтобы обновление сохранилось в базу
        return id;
    }

    @Override
    public Long removeFromBlacklist(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        person.setBlacklisted(false);//Обновили запись
        personRepository.save(person);//Чтобы обновление сохранилось в базу
        return id;
    }


}
