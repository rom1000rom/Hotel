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

    private static final String EXCEPTION_MESSAGE = "Such a person does not exist";

    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    static {//Позволяет библиотеке Orika Mapper корректно отображать LocalDate
        mapperFactory.getConverterFactory().registerConverter(
                new PassThroughConverter(LocalDate.class));
    }

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
        System.out.println(person.getReservations().size());
        return mapper.map(person, PersonDTO.class);
    }

    @Override
    public Long savePerson(PersonUsernameLoginDTO personUsernameLoginDTO) {
        mapperFactory.classMap(PersonUsernameLoginDTO.class, Person.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Person person = personRepository.save(mapper.map(personUsernameLoginDTO, Person.class));

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
    public PersonUsernameLoginDTO updatePerson(PersonUsernameLoginDTO personUsernameLoginDTO) {
        Person person = personRepository.findById(personUsernameLoginDTO.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));

        mapperFactory.classMap(Person.class, PersonUsernameLoginDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        person.setPersonName(personUsernameLoginDTO.getPersonName());
        person.setEncrytedPassword(personUsernameLoginDTO.getEncrytedPassword());

            return mapper.map(personRepository.save(person), PersonUsernameLoginDTO.class);
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
