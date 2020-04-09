package com.andersenlab.dao;



import com.andersenlab.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;



/**Интерфейс служит для определения функций хранилища данных об комнатах
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long>
{
    /** Метод проверяет забронирован ли номер на указанный период времени
     * @param room номер
     * @param dateBegin начало периода
     * @param dateEnd окончание периода
     * @return число Reservations, пересекающих указанный период */
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.room = ?1 AND \n" +
            "            ((r.dateBegin <= ?2 AND r.dateEnd >= ?2)\n" +
            "            OR (r.dateBegin <= ?3 AND r.dateEnd >= ?3))")
    Integer findIntersectingReservation(Room room, LocalDate dateBegin,
                                         LocalDate dateEnd);

    /** Метод ищет свободные номера по заданным параметрам
     * @param dateBegin дата заезда
     * @param dateEnd дата отъезда
     * @param minPrice минимальная цена номера
     * @param maxPrice максимальная цена номера
     * @param guests количество гостей
     * @return страница объектов класса RoomDTO, свободных номеров*/
    @Query("SELECT r FROM Room r LEFT OUTER JOIN r.reservations res " +
            "WHERE ((r.price BETWEEN ?3 AND ?4) AND r.maxGuests >= ?5) AND " +
            "(res IS NULL OR  " +
               "NOT((res.dateBegin <= ?1 AND res.dateEnd >= ?1) " +
               "OR (res.dateBegin <= ?2 AND res.dateEnd >= ?2))) " +
            "ORDER BY r.id")
    Page<Room> findAvailableRooms(LocalDate dateBegin, LocalDate dateEnd, Integer minPrice,
                                  Integer maxPrice, Integer guests, Pageable pageable);

}
