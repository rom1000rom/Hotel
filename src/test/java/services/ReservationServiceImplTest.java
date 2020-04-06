package services;


import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.*;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import com.andersenlab.service.ReservationService;
import com.andersenlab.service.impl.ReservationServiceImpl;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MapperFacade mapperFacade;

    @InjectMocks
    private ReservationService testObject = new ReservationServiceImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllReservation() {
        //Подготавливаю ожидаемый результат
        List<Reservation> listReservation = new ArrayList<>();
        listReservation.add(new Reservation(LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21")));
        listReservation.add(new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23")));
        //Настраиваю поведение мока
        when(reservationRepository.findAll()).thenReturn(listReservation);

        List<ReservationDto> listReservationDto = new ArrayList<>();
        listReservationDto.add(new ReservationDto(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21")));
        listReservationDto.add(new ReservationDto(
                LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23")));

        when(mapperFacade.mapAsList(listReservation, ReservationDto.class))
                .thenReturn(listReservationDto);
        //Проверяю поведение тестируемого объекта
        assertEquals(listReservationDto, testObject.findAllReservations());

    }

    @Test
    public void testFindReservationById() {
        Long id = 10L;
        Reservation reservation = new Reservation(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
        reservation.setId(id);
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        ReservationDto reservationDTO = new ReservationDto();
        reservationDTO.setDateBegin(reservation.getDateBegin());
        reservationDTO.setDateEnd(reservation.getDateEnd());
        reservationDTO.setId(id);
        when(mapperFacade.map(reservation, ReservationDto.class)).thenReturn(reservationDTO);

        assertEquals(reservationDTO, testObject.findReservationById(id));
    }

    @Test(expected = HotelServiceException.class)
    public void testFindReservationByIdNotExist() {
        Long id = 10L;
        when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        testObject.findReservationById(id);
    }

    @Test(expected = HotelServiceException.class)
    public void testSaveReservationPersonNotExist() {
        Long id = 12L;
        ReservationDto reservationDTO = new ReservationDto();
        PersonDto personDTO = new PersonDto("TEST");
        personDTO.setId(id);
        reservationDTO.setPerson(personDTO);

        when(personRepository.findById(id)).thenReturn(
                Optional.empty());
        testObject.saveReservation(reservationDTO);
    }

    @Test
    public void testSaveReservation() {
        Long id = 12L;
        String dateBegin = "2016-09-23";
        String dateEnd = "2016-09-25";

        ReservationDto reservationDTO = new ReservationDto(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));

        ReservationDto reservationDtoWithId = new ReservationDto(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));

        PersonDto personDTO = new PersonDto();
        personDTO.setId(id);
        reservationDTO.setPerson(personDTO);
        reservationDtoWithId.setPerson(personDTO);

        RoomDto roomDTO = new RoomDto();
        roomDTO.setId(id);
        reservationDTO.setRoom(roomDTO);
        reservationDtoWithId.setRoom(roomDTO);
        reservationDtoWithId.setId(id);

        Reservation reservation = new Reservation(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));
        Reservation reservationWithId = new Reservation(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));
        reservationWithId.setId(id);

        Person person = new Person();
        person.setId(id);
        Room room = new Room();
        room.setId(id);

        when(personRepository.findById(id)).thenReturn(
                Optional.of(person));
        when(roomRepository.findById(id)).thenReturn(
                Optional.of(room));
        when(roomRepository.findIntersectingReservation(
                room, reservationDTO.getDateBegin(), reservationDTO.getDateEnd())).thenReturn(0);

        when(mapperFacade.map(reservationDTO, Reservation.class)).thenReturn(reservation);
        reservation.setPerson(person);
        reservation.setRoom(room);

        reservationWithId.setPerson(person);
        reservationWithId.setRoom(room);
        when(reservationRepository.save(reservation)).thenReturn(reservationWithId);

        assertEquals(id, testObject.saveReservation(reservationDTO));
    }


    @Test
    public void testDeleteReservation() {
        Long id = 12L;
        when(reservationRepository.findById(id)).thenReturn(Optional.of(
                new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23"))));
        Mockito.doNothing().when(reservationRepository).deleteById(id);

        assertEquals(id, testObject.deleteReservation(id));
    }

}
