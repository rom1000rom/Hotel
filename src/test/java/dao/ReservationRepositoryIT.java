package dao;



import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Reservation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import static junit.framework.Assert.assertEquals;


public class ReservationRepositoryIT extends AbstractDaoTest
{
    @Autowired
    private ReservationRepository repository;

    private Reservation reservation;

    @Before
    public void setUp(){
        reservation = repository.save(createReservation());
    }

    @Test
    public void savePersonTest() {
        assertEquals(reservation, repository.findById(reservation.getId()).orElseThrow(() ->
                new HotelServiceException("Such a reservation does not exist")));
    }

    private Reservation createReservation()
    {
        return new Reservation(LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
    }

}


