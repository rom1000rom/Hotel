package com.andersenlab.dao;



import com.andersenlab.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;


/**Интерфейс служит для определения функций хранилища данных об комнатах
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Repository
public interface RoomRepository extends CrudRepository<Room, Long>
{
    /** Метод проверяет забронирован ли номер на указанный период времени
     * @param roomId id номера
     * @param dateBegin начало периода
     * @param dateEnd   окончание периода
     * @return число Reservations, пересекающих указанный период */
    @Query(value = "SELECT count(*) FROM reservation WHERE room_id = ?1 AND \n" +
            "            ((date_begin <= ?2 AND date_end >= ?2)\n" +
            "            OR\n" +
            "            (date_begin <= ?3 AND date_end >= ?3));\n",
    nativeQuery = true)
    Integer findIntersectingReservations(Long roomId, LocalDate dateBegin,
                                                   LocalDate dateEnd);
}
