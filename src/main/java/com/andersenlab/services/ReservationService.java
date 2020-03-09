package com.andersenlab.services;



import com.andersenlab.model.Reservation;

import java.util.List;

/**Интерфейс служит для определения сервисных функций по работе с бронированиями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface ReservationService {

     /**Метод возвращает список всех бронирований.
     @return список объектов класса Reservation*/
     List<Reservation> findAllReservations();

     /**Метод возвращает объект бронирования по его id
      @param id id бронирования
      @return объект класса Reservation или null, если такого нет*/
     Reservation findReservationById(Long id);

     /**Метод удаляет объект бронирования по id
      @param id бронирования, которого нужно удалить
      @return id удалённого бронирования, или null если такого нет*/
      Long deleteReservation(Long id);
}
