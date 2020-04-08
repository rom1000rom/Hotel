package com.andersenlab.dao;


import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**Интерфейс служит для определения функций хранилища данных о бронированиях
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByRoom(Room room, Pageable page);
    List<Reservation> findByPerson(Person person, Pageable page);

}
