package services;


import com.andersenlab.dao.PersonRepository;
import com.andersenlab.model.Person;
import com.andersenlab.services.PersonService;
import com.andersenlab.services.PersonServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

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
        listPerson.add(new Person("TEST", "TEST"));
        listPerson.add(new Person("TEST2", "TEST2"));
        //Настраиваю поведение мока
        Mockito.when(personRepository.findAll()).thenReturn(listPerson);

        //Проверяю поведение тестируемого объекта
        assertEquals(listPerson, testObject.findAllPersons());
    }

    @Test
    public void testFindPersonById() {
        Long id = 10L;
        Person person = new Person("TEST", "TEST");
        person.setId(id);
        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));

        assertEquals(person, testObject.findPersonById(id));
    }

    @Test
    public void testFindPersonByIdNotExist() {
        Long id = 10L;
        Mockito.when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertNull(testObject.findPersonById(id));
    }

    @Test
    public void testSavePerson() {
        Long id = 12L;
        Person person = new Person("TEST", "TEST");
        person.setId(id);
        Mockito.when(personRepository.save(person)).thenReturn(person);

        assertEquals(person, testObject.savePerson(person));
    }

    @Test
    public void testDeletePerson() {
        Long id = 12L;
        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.of(new Person("TEST", "TEST")));
        Mockito.doNothing().when(personRepository).deleteById(id);

        assertEquals(id, testObject.deletePerson(id));
    }

    @Test
    public void testDeletePersonNotExist() {
        Long id = 12L;
        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.empty());

        assertNull(testObject.deletePerson(id));
    }

}
