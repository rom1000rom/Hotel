package com.andersenlab.dao;



import com.andersenlab.model.Room;
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
     * @param dateEnd   окончание периода
     * @return число Reservations, пересекающих указанный период */
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.room = ?1 AND \n" +
            "            ((r.dateBegin <= ?2 AND r.dateEnd >= ?2)\n" +
            "            OR (r.dateBegin <= ?3 AND r.dateEnd >= ?3))")
    Integer findIntersectingReservation(Room room, LocalDate dateBegin,
                                         LocalDate dateEnd);
}
