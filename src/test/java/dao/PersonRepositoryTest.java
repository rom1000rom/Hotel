package dao;




import com.andersenlab.dao.PersonRepository;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;


public class PersonRepositoryTest extends AbstractDaoTest
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
        assertEquals(person, repository.findById(person.getId()).orElseThrow(() ->
                new HotelServiceException("Such a person does not exist")));
    }

    @Test
    public void findOneByPersonNameLikeTest(){
        assertEquals(person, repository.findOneByPersonNameLike(person.getPersonName()));
    }

    private Person createPerson()
    {
        return new Person("Roman");
    }

}



