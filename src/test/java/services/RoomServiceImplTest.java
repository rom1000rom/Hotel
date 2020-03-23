package services;


import com.andersenlab.dao.HotelDao;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.HotelDto;
import com.andersenlab.dto.RoomDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Hotel;
import com.andersenlab.model.Room;
import com.andersenlab.services.RoomService;
import com.andersenlab.services.impl.RoomServiceImpl;
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
    HotelDao hotelDao;

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

        List<RoomDto> listRoomDto = new ArrayList<>();
        listRoomDto.add(new RoomDto("TEST"));
        listRoomDto.add(new RoomDto("TEST2"));
        when(mapperFacade.mapAsList(listRoom, RoomDto.class)).thenReturn(listRoomDto);

        //Проверяю поведение тестируемого объекта
        assertEquals(listRoomDto, testObject.findAllRooms());
    }

    @Test
    public void testFindRoomById() {
        Long id = 10L;
        Room room = new Room("TEST");
        room.setId(id);
        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        RoomDto roomDTO = new RoomDto("TEST");
        roomDTO.setId(id);
        when(mapperFacade.map(room, RoomDto.class)).thenReturn(roomDTO);

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

        Hotel hotel = new Hotel();
        hotel.setId(id);

        HotelDto hotelDTO = new HotelDto();
        hotelDTO.setId(id);

        RoomDto roomDTO = new RoomDto("TEST" );
        roomDTO.setHotelId(hotelDTO);
        Room room = new Room("TEST");

        Room roomWithId = new Room("TEST");
        roomWithId.setId(id);
        roomWithId.setHotelId(hotel);

        when(hotelDao.findById(roomDTO.getHotelId().getId())).thenReturn(Optional.of(hotel));
        when(mapperFacade.map(roomDTO, Room.class)).thenReturn(room);
        room.setHotelId(hotel);
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
        RoomDto roomDTO = new RoomDto("TEST_NEW" );
        roomDTO.setId(id);
        Room room = new Room("TEST");
        room.setId(id);

        when(roomRepository.findById(id)).thenReturn(
                Optional.of(room));
        room.setNumber("TEST_NEW");
        when(roomRepository.save(room)).thenReturn(room);
        when(mapperFacade.map(room, RoomDto.class)).thenReturn(roomDTO);

        assertEquals(roomDTO, testObject.updateRoom(roomDTO));
    }

}
