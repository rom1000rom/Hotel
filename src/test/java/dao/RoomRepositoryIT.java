package dao;


import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;


public class RoomRepositoryIT extends AbstractDaoTest {
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

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservationRepository.save(reservation));
        room.setReservations(reservationList);
        roomRepository.save(room);

        assertEquals(1, roomRepository.findIntersectingReservations(room.getId()
                , LocalDate.parse("2016-09-21"), LocalDate.parse("2016-09-23")).intValue());

        assertEquals(0, roomRepository.findIntersectingReservations(room.getId()
                , LocalDate.parse("2016-09-10"), LocalDate.parse("2016-09-14")).intValue());
    }

    private Room createRoom() {
        return new Room("1");
    }

}

