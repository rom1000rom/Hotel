package services;


import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dto.PersonDTO;
import com.andersenlab.dto.PersonUsernameLoginDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.services.PersonService;
import com.andersenlab.services.PersonServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private MapperFacade mapperFacade;

    @InjectMocks
    private PersonService testObject = new PersonServiceImpl();

    @Before
    public void setUp()
    {
        //Вызывая метод initMocks() перед каждым тестом мы скидываем состояние мока(моков)
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllPersons() {
        //Подготавливаю ожидаемый результат
        List<Person> listPerson = new ArrayList<>();
        listPerson.add(new Person("TEST"));
        listPerson.add(new Person("TEST2"));
        //Настраиваю поведение мока
        when(personRepository.findAll()).thenReturn(listPerson);

        List<PersonDTO> listPersonDTO = new ArrayList<>();
        listPersonDTO.add(new PersonDTO("TEST"));
        listPersonDTO.add(new PersonDTO("TEST2"));

        when(mapperFacade.mapAsList(listPerson, PersonDTO.class)).thenReturn(listPersonDTO);

        //Проверяю поведение тестируемого объекта
        assertEquals(listPersonDTO, testObject.findAllPersons());
    }

    @Test
    public void testFindPersonById() throws HotelServiceException {
        Long id = 10L;
        Person person = new Person("TEST");
        person.setId(id);
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        PersonDTO personDTO = new PersonDTO("TEST");
        personDTO.setId(id);
        when(mapperFacade.map(person, PersonDTO.class)).thenReturn(personDTO);

        assertEquals(personDTO, testObject.findPersonById(id));
    }

    @Test(expected = HotelServiceException.class)
    public void testFindPersonByIdNotExist() {
        Long id = 10L;
        when(personRepository.findById(id)).thenReturn(Optional.empty());

        testObject.findPersonById(id);
    }

    @Test
    public void testSavePerson() {
        Long id = 12L;
        PersonUsernameLoginDTO personUsernameLoginDTO = new PersonUsernameLoginDTO("TEST" );
        Person person = new Person("TEST");

        Person personWithId = new Person("TEST");
        personWithId.setId(id);

        when(mapperFacade.map(personUsernameLoginDTO, Person.class)).thenReturn(person);
        when(personRepository.save(person)).thenReturn(personWithId);

        assertEquals(id, testObject.savePerson(personUsernameLoginDTO));
    }

    @Test
    public void testUpdatePerson() {
        Long id = 12L;
        PersonUsernameLoginDTO personUsernameLoginDTO = new PersonUsernameLoginDTO("TEST_NEW" );
        personUsernameLoginDTO.setId(id);
        Person person = new Person("TEST");
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(
                Optional.of(person));
        person.setPersonName("TEST_NEW");
        when(personRepository.save(person)).thenReturn(person);
        when(mapperFacade.map(person, PersonUsernameLoginDTO.class)).thenReturn(personUsernameLoginDTO);

        assertEquals(personUsernameLoginDTO, testObject.updatePerson(personUsernameLoginDTO));
    }

    @Test
    public void testDeletePerson() {
        Long id = 12L;
        when(personRepository.findById(id)).thenReturn(
                Optional.of(new Person("TEST")));
        doNothing().when(personRepository).deleteById(id);

        assertEquals(id, testObject.deletePerson(id));
    }

    @Test(expected = HotelServiceException.class)
    public void testDeletePersonNotExist() {
        Long id = 12L;
        when(personRepository.findById(id)).thenReturn(
                Optional.empty());

        testObject.deletePerson(id);
    }

    @Test(expected = HotelServiceException.class)
    public void testAddToBlacklistNotExist() {
        Long id = 12L;
        when(personRepository.findById(id)).thenReturn(
                Optional.empty());

        testObject.addToBlacklist(id);
    }

    @Test
    public void testAddToBlacklist() {
        Long id = 12L;
        Person person = new Person("TEST");
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        assertEquals(id, testObject.addToBlacklist(id));
        assertTrue(person.getBlacklisted());
    }

    @Test
    public void testRemoveFromBlacklist() {
        Long id = 12L;
        Person person = new Person("TEST");
        person.setId(id);

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        assertEquals(id, testObject.removeFromBlacklist(id));
        assertFalse(person.getBlacklisted());
    }
}
