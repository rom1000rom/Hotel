package com.andersenlab.service.impl;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.ReservationDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import com.andersenlab.service.ReservationService;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализует сервисные функции по работе с бронированиями.
 * 
 * @author Артемьев Р.А.
 * @version 09.03.2020
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

  private static final Logger log = LogManager.getLogger(ReservationServiceImpl.class);

  private static final String EXCEPTION_MESSAGE_RESERVATION = "Such a reservation does not exist";
  private static final String EXCEPTION_PERSON_NOT_FOUND_MESSAGE = "Such person does not exist";
  private static final String EXCEPTION_ROOM_NOT_FOUND_MESSAGE = "Such room does not exist";
  private static final String EXCEPTION_INCORRECT_DATE_MESSAGE = "Incorrect dates";
  private static final String EXCEPTION_ROOM_ALREADY_BOOKED_MESSAGE =
      "This room is booked for these dates";
  private static final String EXCEPTION_GUEST_NUMBER_MESSAGE =
      "Number of guests is more than maximum";

  private static final Integer DEVIATION = 3;

  private ReservationRepository reservationRepository;
  private PersonRepository personRepository;
  private RoomRepository roomRepository;
  private MapperFacade mapperFacade;

  @Autowired
  public ReservationServiceImpl(ReservationRepository reservationRepository,
      PersonRepository personRepository, RoomRepository roomRepository, MapperFacade mapperFacade) {
    this.reservationRepository = reservationRepository;
    this.personRepository = personRepository;
    this.roomRepository = roomRepository;
    this.mapperFacade = mapperFacade;
  }


  @Override
  public Page<ReservationDto> findAllReservations(Pageable pageable) {
    Page<Reservation> listReservation = reservationRepository.findAll(pageable);
    return new PageImpl<>(mapperFacade.mapAsList(listReservation, ReservationDto.class), pageable,
        listReservation.getTotalElements());
  }

  @Override
  public ReservationDto findReservationById(Long id) {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_RESERVATION));
    return mapperFacade.map(reservation, ReservationDto.class);

  }

  @Override
  public Page<ReservationDto> findReservationsByPersonId(Long personId, Pageable pageable) {
    Person person = personRepository.findById(personId)
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_PERSON_NOT_FOUND_MESSAGE));

    List<Reservation> listReservation = reservationRepository.findByPerson(person, pageable);
    return new PageImpl<>(mapperFacade.mapAsList(listReservation, ReservationDto.class), pageable,
        listReservation.size());
  }

  @Override
  public Page<ReservationDto> findReservationsByRoomId(Long roomId, Pageable pageable) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_ROOM_NOT_FOUND_MESSAGE));
    List<Reservation> listReservation = reservationRepository.findByRoom(room, pageable);
    return new PageImpl<>(mapperFacade.mapAsList(listReservation, ReservationDto.class), pageable,
        listReservation.size());
  }

  @Override
  public Long saveReservation(ReservationDto reservationDTO) {

    Person person = personRepository.findById(reservationDTO.getPerson().getId())
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_PERSON_NOT_FOUND_MESSAGE));
    Room room = roomRepository.findById(reservationDTO.getRoom().getId())
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_ROOM_NOT_FOUND_MESSAGE));

    LocalDate dateBegin = reservationDTO.getDateBegin();
    LocalDate dateEnd = reservationDTO.getDateEnd();

    if (dateBegin.isAfter(dateEnd))
      throw new HotelServiceException("Incorrect dates");

    if (roomRepository.findIntersectingReservation(room, dateBegin, dateEnd) > 0) {
      /*
       * Если номер забронирован на указанные даты, возвращаем список дат в которые номер свободен
       */
      printFreeDates(room, dateBegin, dateEnd);
    }

    if (room.getMaxGuests() < reservationDTO.getCurrentGuestsNumber()) {
      throw new HotelServiceException(EXCEPTION_GUEST_NUMBER_MESSAGE);
    }

    Reservation reservation = mapperFacade.map(reservationDTO, Reservation.class);
    reservation.setPerson(person);
    reservation.setRoom(room);
    return reservationRepository.save(reservation).getId();
  }

  @Override
  public ReservationDto updateReservation(ReservationDto reservationDto) {
    Reservation reservation = reservationRepository.findById(reservationDto.getId())
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_RESERVATION));

    Room room = roomRepository.findById(reservationDto.getRoom().getId())
        .orElseThrow(() -> new HotelServiceException(EXCEPTION_ROOM_NOT_FOUND_MESSAGE));

    LocalDate dateBegin = reservationDto.getDateBegin();
    LocalDate dateEnd = reservationDto.getDateEnd();
    if (dateBegin.isAfter(dateEnd))
      throw new HotelServiceException("Incorrect dates");

    if (roomRepository.findIntersectingReservation(room, dateBegin, dateEnd) > 0) {
      printFreeDates(room, dateBegin, dateEnd);
    }

    if (room.getMaxGuests() < reservationDto.getCurrentGuestsNumber()) {
      throw new HotelServiceException(EXCEPTION_GUEST_NUMBER_MESSAGE);
    }
    reservation.setDateBegin(dateBegin);
    reservation.setDateEnd(dateEnd);
    reservation.setRoom(room);
    return mapperFacade.map(reservationRepository.save(reservation), ReservationDto.class);
  }

  @Override
  public Long deleteReservation(Long id) {
    if (reservationRepository.findById(id).isPresent()) {
      reservationRepository.deleteById(id);
      return id;
    } else {
      throw new HotelServiceException(EXCEPTION_MESSAGE_RESERVATION);
    }
  }

  private void printFreeDates(Room room, LocalDate dateBegin, LocalDate dateEnd) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(
        "Room(id = " + room.getId() + ") is booked from " + dateBegin + " to " + dateEnd + ". ");
    log.debug(stringBuilder);
    stringBuilder.append("Free dates: ");

    LocalDate checkDataBegin = dateBegin.minusDays(DEVIATION);
    Period period = Period.between(checkDataBegin, dateEnd.plusDays(DEVIATION + 1));
    for (int i = 0; i < period.getDays(); i++) {
      if (roomRepository.findIntersectingReservation(room, checkDataBegin.plusDays(i),
          checkDataBegin.plusDays(i)) < 1) {
        stringBuilder.append(checkDataBegin.plusDays(i) + ", ");
      }
    }
    /*
     * Обрезаем у итоговой строки последние два символа для корректного отображения(без ", ")
     */
    throw new HotelServiceException(stringBuilder.substring(0, stringBuilder.length() - 2));
  }


}
