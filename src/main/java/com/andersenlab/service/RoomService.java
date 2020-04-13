package com.andersenlab.service;



import com.andersenlab.dto.RoomDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


/**Интерфейс служит для определения сервисных функций по работе с номерами отеля.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface RoomService {

     /**Метод возвращает список всех номеров.
     @return страница объектов класса RoomDTO*/
     Page<RoomDto> findAllRooms(Pageable pageable);

     /**Метод возвращает объект номера по его id
      @param id id номера
      @return объект класса RoomDTO*/
     RoomDto findRoomById(Long id);

     /**Метод сохраняет объект номера отеля
      @param room объект номера, которого нужно сохранить
      @return id объект созданного номера*/
     Long saveRoom(RoomDto room);

     /**Метод обновляет объект номера в отеле
      @param room объект с данными номера
      @return объект обновлённого номера*/
     RoomDto updateRoom(RoomDto room);

     /**Метод удаляет объект номера по id
      @param id номера, которого нужно удалить
      @return id удалённого номера*/
      Long deleteRoom(Long id);

     /** Метод ищет свободные номера по заданным параметрам
      * @param dateBegin дата заезда
      * @param dateEnd дата отъезда
      * @param minPrice минимальная цена номера
      * @param maxPrice максимальная цена номера
      * @param guests количество гостей
      * @return страница объектов класса RoomDTO, свободных номеров*/
     Page<RoomDto> findAvailableRooms(Pageable pageable, LocalDate dateBegin, LocalDate dateEnd,
                                      Integer minPrice, Integer maxPrice, Integer guests);

}