package dao;

import com.andersenlab.App;
import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class,
        classes = App.class)
public class ReservationRepositoryIT
{
    @Autowired
    private ReservationRepository repository;

    private Reservation reservation;

    @Before
    public void setUp(){
        reservation = repository.save(createReservation());
    }

    @Test
    public void savePersonTest(){
        assertEquals(reservation, repository.findById(reservation.getId()).get());
    }

    private Reservation createReservation()
    {
        Reservation reservation = new Reservation();
        reservation.setVersion(1);
        reservation.setDateBegin(LocalDate.parse("2016-09-21"));
        reservation.setDateEnd(LocalDate.parse("2016-09-25"));

        return reservation;
    }

}


