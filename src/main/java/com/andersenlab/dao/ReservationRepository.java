package com.andersenlab.dao;

import com.andersenlab.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**Интерфейс служит для определения функций хранилища данных о бронированиях
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long>
{ }
