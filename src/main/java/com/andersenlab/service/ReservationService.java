package com.andersenlab.service;



import com.andersenlab.dto.ReservationDto;


import java.util.List;

/**Интерфейс служит для определения сервисных функций по работе с бронированиями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface ReservationService {

     /**Метод возвращает список всех бронирований.
     @return список объектов класса ReservationDTO*/
     List<ReservationDto> findAllReservations();

     /**Метод возвращает объект бронирования по его id
      @param id id бронирования
      @return объект класса ReservationDTO*/
     ReservationDto findReservationById(Long id);

     /**Метод возвращает список объектов бронирования по id пользователя
      @param id id пользователя
      @return список объектов класса ReservationDTO*/
     List<ReservationDto> findReservationsByPersonId(Long id);

     /**Метод возвращает список объектов бронирования по id номера в отеле
      @param id id номера
      @return список объектов класса ReservationDTO*/
     List<ReservationDto> findReservationsByRoomId(Long id);

     /**Метод сохраняет бронирование номера
      @param reservationDTO объект бронирования, который нужно сохранить
      @return id объект бронирования в базе*/
     Long saveReservation(ReservationDto reservationDTO);

     /**Метод обновляет объект бронирования
      @param reservationDto объект с данными бронирования
      @return объект обновлённого бронирования*/
     ReservationDto updateReservation(ReservationDto reservationDto);

     /**Метод удаляет объект бронирования по id
      @param id бронирования, которого нужно удалить
      @return id удалённого бронирования*/
      Long deleteReservation(Long id);
}
