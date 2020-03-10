package services;


import com.andersenlab.dao.RoomRepository;
import com.andersenlab.model.Person;
import com.andersenlab.model.Room;
import com.andersenlab.services.RoomService;
import com.andersenlab.services.RoomServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @InjectMocks
    RoomService testObject = new RoomServiceImpl();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllRooms() {
        //Подготавливаю ожидаемый результат
        List<Room> listRoom = new ArrayList<>();
        listRoom.add(new Room("1"));
        listRoom.add(new Room("2"));
        //Настраиваю поведение мока
        Mockito.when(roomRepository.findAll()).thenReturn(listRoom);

        //Проверяю поведение тестируемого объекта
        assertEquals(listRoom, testObject.findAllRooms());
    }

    @Test
    public void testFindRoomById() {
        Long id = 10L;
        Room room = new Room("3");
        room.setId(id);
        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        assertEquals(room, testObject.findRoomById(id));
    }

    @Test
    public void testFindRoomByIdNotExist() {
        Long id = 10L;
        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.empty());

        assertNull(testObject.findRoomById(id));
    }

    @Test
    public void testSaveRoom() {
        Long id = 12L;
        Room room = new Room("TEST");
        room.setId(id);
        Mockito.when(roomRepository.save(room)).thenReturn(room);

        assertEquals(room, testObject.saveRoom(room));
    }

    @Test
    public void testDeleteRoom() {
        Long id = 12L;
        Mockito.when(roomRepository.findById(id)).thenReturn(
                Optional.of(new Room("TEST")));
        Mockito.doNothing().when(roomRepository).deleteById(id);

        assertEquals(id, testObject.deleteRoom(id));
    }

    @Test
    public void testDeletePersonNotExist() {
        Long id = 12L;
        Mockito.when(roomRepository.findById(id)).thenReturn(
                Optional.empty());

        assertNull(testObject.deleteRoom(id));
    }



}
