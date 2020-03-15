package services;



import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.PersonDTO;
import com.andersenlab.dto.ReservationDTO;
import com.andersenlab.dto.RoomDTO;
import com.andersenlab.exceptions.HotelServiceException;
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
import static org.mockito.Mockito.when;

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
        when(reservationRepository.findAll()).thenReturn(listReservation);

        List<ReservationDTO> listReservationDTO = new ArrayList<>();
        listReservationDTO.add(new ReservationDTO(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21")));
        listReservationDTO.add(new ReservationDTO(
                LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23")));

        //Проверяю поведение тестируемого объекта
        assertEquals(listReservationDTO, testObject.findAllReservations());

    }

    @Test
    public void testFindReservationById() {
        Long id = 10L;
        Reservation reservation = new Reservation(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
        reservation.setId(id);
        when(reservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setDateBegin(reservation.getDateBegin());
        reservationDTO.setDateEnd(reservation.getDateEnd());
        reservationDTO.setId(id);

        assertEquals(reservationDTO, testObject.findReservationById(id));
    }

    @Test(expected = HotelServiceException.class)
    public void testFindReservationByIdNotExist() {
        Long id = 10L;
        Mockito.when(reservationRepository.findById(id)).thenReturn(Optional.empty());

        testObject.findReservationById(id);
    }

    @Test(expected = HotelServiceException.class)
    public void testSaveReservationPersonNotExist() {
        Long id = 12L;
        ReservationDTO reservationDTO = new ReservationDTO();
        PersonDTO personDTO = new PersonDTO("TEST");
        personDTO.setId(id);
        reservationDTO.setPerson(personDTO);

        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.empty());
        testObject.saveReservation(reservationDTO);
    }

    @Test
    public void testSaveReservation() {
        Long id = 12L;
        String dateBegin = "2016-09-23";
        String dateEnd = "2016-09-25";

        ReservationDTO reservationDTO = new ReservationDTO(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));

        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(id);
        reservationDTO.setPerson(personDTO);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(id);
        reservationDTO.setRoom(roomDTO);

        Reservation reservation = new Reservation(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));
        Reservation reservationWithId = new Reservation(
                LocalDate.parse(dateBegin), LocalDate.parse(dateEnd));
        reservationWithId.setId(id);

        Person person = new Person();
        person.setId(id);
        Room room = new Room();
        room.setId(id);

        Mockito.when(personRepository.findById(id)).thenReturn(
                Optional.of(person));
        Mockito.when(roomRepository.findById(id)).thenReturn(
                Optional.of(room));
        Mockito.when(reservationRepository.save(reservation)).thenReturn(reservationWithId);

        assertEquals(id, testObject.saveReservation(reservationDTO));
    }


    @Test
    public void testDeleteReservation() {
        Long id = 12L;
        Mockito.when(reservationRepository.findById(id)).thenReturn(Optional.of(
                new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-23"))));
        Mockito.doNothing().when(reservationRepository).deleteById(id);

        assertEquals(id, testObject.deleteReservation(id));
    }

}
