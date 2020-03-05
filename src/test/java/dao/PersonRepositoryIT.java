package dao;

import com.andersenlab.App;
import com.andersenlab.dao.PersonRepository;
import com.andersenlab.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class,
        classes = App.class)
public class PersonRepositoryIT
{
    @Autowired
    private PersonRepository repository;

    private Person person;

    @Before
    public void setUp(){
        person = repository.save(createPerson());
    }

    @Test
    public void savePersonTest(){
        assertEquals(person, repository.findById(person.getId()).get());
    }

    private Person createPerson()
    {
        Person person = new Person();
        person.setPersonName("Roman");
        person.setVersion(1);
        return person;
    }

}



