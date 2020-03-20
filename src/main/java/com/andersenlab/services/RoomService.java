package com.andersenlab.services;



import com.andersenlab.dto.RoomDTO;



import java.util.List;

/**Интерфейс служит для определения сервисных функций по работе с номерами отеля.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface RoomService {

     /**Метод возвращает список всех номеров.
     @return список объектов класса RoomDTO*/
     List<RoomDTO> findAllRooms();

     /**Метод возвращает объект номера по его id
      @param id id номера
      @return объект класса RoomDTO*/
     RoomDTO findRoomById(Long id);

     /**Метод сохраняет объект номера отеля
      @param room объект номера, которого нужно сохранить
      @return id объект созданного номера*/
     Long saveRoom(RoomDTO room);

     /**Метод обновляет объект номера в отеле
      @param room объект с данными номера
      @return объект обновлённого номера*/
     RoomDTO updateRoom(RoomDTO room);

     /**Метод удаляет объект номера по id
      @param id номера, которого нужно удалить
      @return id удалённого номера*/
      Long deleteRoom(Long id);
}
