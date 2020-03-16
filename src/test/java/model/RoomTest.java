package model;

import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomTest {

    @Test
    public void testIsBooked() {
        Room testedObject = new Room();
        List<Reservation> listReservation= new ArrayList<>();
        listReservation.add(new Reservation(LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-25")));
        listReservation.add(new Reservation(LocalDate.parse("2016-09-26"), LocalDate.parse("2016-09-29")));
        testedObject.setReservations(listReservation);

        assertTrue(testedObject.isBooked(LocalDate.parse("2016-09-20"),
                LocalDate.parse("2016-09-23")));
        assertTrue(testedObject.isBooked(LocalDate.parse("2016-09-28"),
                LocalDate.parse("2017-10-01")));
        assertFalse(testedObject.isBooked(LocalDate.parse("2016-09-18"),
                LocalDate.parse("2016-09-22")));
        assertFalse(testedObject.isBooked(LocalDate.parse("2016-09-29"),
                LocalDate.parse("2016-09-30")));
        testedObject.setReservations(null);
        assertFalse(testedObject.isBooked(LocalDate.parse("2016-09-18"),
                LocalDate.parse("2016-09-22")));
    }
}
