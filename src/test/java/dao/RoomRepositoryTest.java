package dao;

import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;


import static junit.framework.Assert.assertEquals;


public class RoomRepositoryTest extends AbstractDaoTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Room room;

    @Before
    public void setUp() {
        room = roomRepository.save(createRoom());
    }

    @Test
    public void saveRoomTest() {
        assertEquals(room, roomRepository.findById(room.getId()).orElse(new Room()));
    }

    @Test
    public void findIntersectingReservationsTest() {
        Reservation reservation = new Reservation(
                LocalDate.parse("2016-09-15"), LocalDate.parse("2016-09-21"));
        reservation.setRoom(room);
        reservationRepository.save(reservation);

        assertEquals(1, roomRepository.findIntersectingReservations(room.getId()
                , LocalDate.parse("2016-09-20"), LocalDate.parse("2016-09-20")).intValue());

        assertEquals(0, roomRepository.findIntersectingReservations(room.getId()
                , LocalDate.parse("2016-09-10"), LocalDate.parse("2016-09-14")).intValue());

        assertEquals(1, roomRepository.findIntersectingReservations(room.getId()
                , LocalDate.parse("2016-09-20"), LocalDate.parse("2016-09-22")).intValue());

    }

    private Room createRoom() {
        return new Room("1");
    }

}

