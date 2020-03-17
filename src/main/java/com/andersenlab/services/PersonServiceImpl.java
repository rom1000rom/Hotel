package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dto.PersonDTO;
import com.andersenlab.dto.PersonUsernameLoginDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**Класс реализует сервисные функции по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
@Transactional//Позволяет избежать ошибки "ленивой" инициализации прокси
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MapperFacade mapperFacade;

    private static final String EXCEPTION_MESSAGE = "Such a person does not exist";

    @Override
    public List<PersonDTO> findAllPersons() {
        List<Person> listPerson = (List<Person>)personRepository.findAll();
        return mapperFacade.mapAsList(listPerson, PersonDTO.class);
    }

    @Override
    public PersonDTO findPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(person, PersonDTO.class);
    }

    @Override
    public Long savePerson(PersonUsernameLoginDTO personUsernameLoginDTO) {
        return personRepository.save(mapperFacade.map(personUsernameLoginDTO, Person.class)).getId();
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
    public PersonUsernameLoginDTO updatePerson(PersonUsernameLoginDTO personUsernameLoginDTO) {
        Person person = personRepository.findById(personUsernameLoginDTO.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));

        person.setPersonName(personUsernameLoginDTO.getPersonName());
        person.setEncrytedPassword(personUsernameLoginDTO.getEncrytedPassword());

            return mapperFacade.map(personRepository.save(person), PersonUsernameLoginDTO.class);
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
