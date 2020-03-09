package com.andersenlab.services;



import com.andersenlab.model.Room;

import java.util.List;

/**Интерфейс служит для определения сервисных функций по работе с номерами отеля.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface RoomService {

     /**Метод возвращает список всех номеров.
     @return список объектов класса Room*/
     List<Room> findAllRooms();

     /**Метод возвращает объект номера по его id
      @param id id номера
      @return объект класса Room или null, если такого нет*/
     Room findRoomById(Long id);

     /**Метод сохраняет объект номера отеля
      @param room объект номера, которого нужно сохранить
      @return объект номера в базе*/
     Room saveRoom(Room room);

     /**Метод удаляет объект номера по id
      @param id номера, которого нужно удалить
      @return id удалённого номера, или null если такого нет*/
      Long deleteRoom(Long id);
}
