package services;


import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.PersonDTO;
import com.andersenlab.dto.PersonUsernameLoginDTO;
import com.andersenlab.dto.RoomDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Room;
import com.andersenlab.services.RoomService;
import com.andersenlab.services.RoomServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    MapperFacade mapperFacade;

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
        listRoom.add(new Room("TEST"));
        listRoom.add(new Room("TEST2"));
        //Настраиваю поведение мока
        when(roomRepository.findAll()).thenReturn(listRoom);

        List<RoomDTO> listRoomDTO = new ArrayList<>();
        listRoomDTO.add(new RoomDTO("TEST"));
        listRoomDTO.add(new RoomDTO("TEST2"));
        when(mapperFacade.mapAsList(listRoom, RoomDTO.class)).thenReturn(listRoomDTO);

        //Проверяю поведение тестируемого объекта
        assertEquals(listRoomDTO, testObject.findAllRooms());
    }

    @Test
    public void testFindRoomById() {
        Long id = 10L;
        Room room = new Room("TEST");
        room.setId(id);
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        RoomDTO roomDTO = new RoomDTO("TEST");
        roomDTO.setId(id);
        when(mapperFacade.map(room, RoomDTO.class)).thenReturn(roomDTO);

        assertEquals(roomDTO, testObject.findRoomById(id));
    }

    @Test(expected = HotelServiceException.class)
    public void testFindRoomByIdNotExist() {
        Long id = 10L;
        Mockito.when(roomRepository.findById(id)).thenReturn(Optional.empty());

        testObject.findRoomById(id);
    }

    @Test
    public void testSaveRoom() {
        Long id = 12L;
        RoomDTO roomDTO = new RoomDTO("TEST" );
        Room room = new Room("TEST");

        Room roomWithId = new Room("TEST");
        roomWithId.setId(id);

        when(mapperFacade.map(roomDTO, Room.class)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(roomWithId);

        assertEquals(id, testObject.saveRoom(roomDTO));
    }

    @Test
    public void testDeleteRoom() {
        Long id = 12L;
        when(roomRepository.findById(id)).thenReturn(
                Optional.of(new Room("TEST")));
        doNothing().when(roomRepository).deleteById(id);

        assertEquals(id, testObject.deleteRoom(id));
    }

    @Test(expected = HotelServiceException.class)
    public void testDeletePersonNotExist() {
        Long id = 12L;
        Mockito.when(roomRepository.findById(id)).thenReturn(
                Optional.empty());

        testObject.deleteRoom(id);
    }

    @Test
    public void testUpdateRoom() {
        Long id = 12L;
        RoomDTO roomDTO = new RoomDTO("TEST_NEW" );
        roomDTO.setId(id);
        Room room = new Room("TEST");
        room.setId(id);

        when(roomRepository.findById(id)).thenReturn(
                Optional.of(room));
        room.setNumber("TEST_NEW");
        when(roomRepository.save(room)).thenReturn(room);
        when(mapperFacade.map(room, RoomDTO.class)).thenReturn(roomDTO);

        assertEquals(roomDTO, testObject.updateRoom(roomDTO));
    }

}
