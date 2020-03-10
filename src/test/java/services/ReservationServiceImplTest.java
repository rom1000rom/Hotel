package services;



import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import com.andersenlab.services.ReservationService;
import com.andersenlab.services.ReservationServiceImpl;
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
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationService testObject = new ReservationServiceImpl();

    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllReservation() {
        //Подготавливаю ожидаемый результат
        List<Reservation> listReservation = new ArrayList<>();
        listReservation.add(new Reservation(LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21")));
        listReservation.add(new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23")));
        //Настраиваю поведение мока
        Mockito.when(reservationRepository.findAll()).thenReturn(listReservation);

        //Проверяю поведение тестируемого объекта
        assertEquals(listReservation, testObject.findAllReservations());
    }

    @Test
    public void testFindReservationById() {
        Long id = 10L;
        Reservation reservation = new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23"));
        reservation.setId(id);
        Mockito.when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        assertEquals(reservation, testObject.findReservationById(id));
    }

    @Test
    public void testFindReservationByIdNotExist() {
        Long id = 10L;
        Mockito.when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        assertNull(testObject.findReservationById(id));
    }

    @Test
    public void testSaveReservationPersonOrRoomIsNull() {
        Reservation reservation = new Reservation();
        assertNull(testObject.saveReservation(reservation));
    }

    @Test
    public void testSaveReservationPersonNotExist() {
        Long id = 12L;
        Reservation reservation = new Reservation();
        reservation.setRoom(new Room());
        Person person = new Person();
        person.setId(id);
        reservation.setPerson(person);

        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.empty());
        assertNull(testObject.saveReservation(reservation));
    }

    @Test
    public void testSaveReservationRoomNotExist() {
        Long id = 12L;
        Reservation reservation = new Reservation();

        Person person = new Person();
        person.setId(id);
        reservation.setPerson(person);

        Room room = new Room();
        room.setId(id);
        reservation.setRoom(room);

        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.of(person));
        Mockito.when(roomRepository.findById(id)).thenReturn(
                Optional.empty());
        assertNull(testObject.saveReservation(reservation));
    }

    @Test
    public void testSaveReservation() {
        Long id = 12L;
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setDateBegin(LocalDate.parse("2016-09-23"));
        reservation.setDateEnd(LocalDate.parse("2016-09-25"));

        Person person = new Person();
        person.setId(id);
        reservation.setPerson(person);

        Room room = new Room();
        room.setId(id);
        reservation.setRoom(room);

        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.of(person));
        Mockito.when(roomRepository.findById(id)).thenReturn(
                Optional.of(room));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservation);

        assertEquals(reservation, testObject.saveReservation(reservation));
    }


    @Test
    public void testDeleteReservation() {
        Long id = 12L;
        Mockito.when(reservationRepository.findById(id)).thenReturn(
                Optional.of(new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23"))));
        Mockito.doNothing().when(reservationRepository).deleteById(id);

        assertEquals(id, testObject.deleteReservation(id));
    }

    @Test
    public void testDeleteReservationNotExist() {
        Long id = 12L;
        Mockito.when(reservationRepository.findById(id)).thenReturn(
                Optional.empty());
        assertNull(testObject.deleteReservation(id));
    }


}
