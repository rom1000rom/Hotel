package com.andersenlab.service.impl;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistrationDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.service.PersonService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public Page<PersonDto> findAllPersons(Pageable pageable) {
        Page<Person> listPerson = personRepository.findAll(pageable);
        return new PageImpl<>(
                mapperFacade.mapAsList(listPerson, PersonDto.class),
                pageable, listPerson.getTotalElements());
    }

    @Override
    public PersonDto findPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(person, PersonDto.class);
    }

    @Override
    public Long savePerson(PersonRegistrationDto personRegistrationDto) {
        return personRepository.save(mapperFacade.map(personRegistrationDto, Person.class)).getId();
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
    public PersonRegistrationDto updatePerson(PersonRegistrationDto personRegistrationDto, Long id) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        person.setName(personRegistrationDto.getName());
        person.setSurname(personRegistrationDto.getSurname());
        person.setPassword(personRegistrationDto.getPassword());
        person.setCountry(personRegistrationDto.getCountry());
        person.setCity(personRegistrationDto.getCity());
        person.setStreet(personRegistrationDto.getStreet());
        person.setHouse(personRegistrationDto.getHouse());
        person.setApartment(personRegistrationDto.getApartment());
        person.setPathToPhoto(personRegistrationDto.getPathToPhoto());
            return mapperFacade.map(personRepository.save(person), PersonRegistrationDto.class);
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
