package dao;

import com.andersenlab.App;
import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.model.Person;
import com.andersenlab.model.Room;
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
public class RoomRepositoryIT
{
    @Autowired
    private RoomRepository repository;

    private Room room;

    @Before
    public void setUp(){
        room = repository.save(createRoom());
    }

    @Test
    public void saveRoomTest(){
        assertEquals(room, repository.findById(room.getId()).get());
    }

    private Room createRoom()
    {
        Room  room  = new Room();
        room.setNumber("1");
        room.setVersion(1);
        return  room;
    }

}

