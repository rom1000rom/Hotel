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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**Класс реализует сервисные функции по работе с бронированиями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MapperFacade mapperFacade;

    private static final Logger log = LogManager.getLogger(
            ReservationServiceImpl.class);

    private static final Integer DEVIATION = 3;

    private static final String EXCEPTION_MESSAGE_RESERVATION =
            "Such a reservation does not exist";

    private static final String EXCEPTION_MESSAGE_ROOM =
            "Such a room does not exist";

    private static final String EXCEPTION_MESSAGE_PERSON =
            "Such a person does not exist";


    @Override
    public List<ReservationDto> findAllReservations() {
        List<Reservation> listReservation = reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation, ReservationDto.class);
    }

    @Override
    public ReservationDto findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE_RESERVATION));
        return mapperFacade.map(reservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findReservationsByPersonId(Long id) {
        List<Reservation> listReservation = reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation.stream()
                .filter(res-> res.getPerson().getId().equals(id))
                .collect(Collectors.toList()), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findReservationsByRoomId(Long roomId) {
        Pageable page = new PageRequest(
                0, 10, new Sort(Sort.Direction.ASC, "id"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_ROOM));
        List<Reservation> listReservation = reservationRepository.findByRoom(room, page);
        return mapperFacade.mapAsList(listReservation, ReservationDto.class);
    }

    @Override
    public Long saveReservation(ReservationDto reservationDTO) {

        Person person = personRepository.findById(reservationDTO.getPerson().getId())
                .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_PERSON));
        Room room = roomRepository.findById(reservationDTO.getRoom().getId())
                .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_ROOM));

        LocalDate dateBegin = reservationDTO.getDateBegin();
        LocalDate dateEnd = reservationDTO.getDateEnd();

        if(dateBegin.isAfter(dateEnd))
            throw new HotelServiceException("Incorrect dates");

        if(roomRepository.findIntersectingReservation(room, dateBegin, dateEnd)
                > 0) {
            /*Если номер забронирован на указанные даты, возвращаем список
            * дат в которые номер свободен*/
            printFreeDates(room, dateBegin, dateEnd);
        }
        Reservation reservation = mapperFacade.map(reservationDTO, Reservation.class);
        reservation.setPerson(person);
        reservation.setRoom(room);
        return reservationRepository.save(reservation).getId();
    }

    @Override
    public ReservationDto updateReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationDto.getId()).
                orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_RESERVATION));

        Room room = roomRepository.findById(reservationDto.getRoom().getId())
                .orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE_ROOM));

        LocalDate dateBegin = reservationDto.getDateBegin();
        LocalDate dateEnd = reservationDto.getDateEnd();
        if (dateBegin.isAfter(dateEnd))
            throw new HotelServiceException("Incorrect dates");

        if(roomRepository.findIntersectingReservation(room, dateBegin, dateEnd)
                > 0) {
            printFreeDates(room, dateBegin, dateEnd);
        }
            reservation.setDateBegin(dateBegin);
            reservation.setDateEnd(dateEnd);
            reservation.setRoom(room);
            return mapperFacade.map(reservationRepository.save(
                    reservation), ReservationDto.class);
    }

    @Override
    public Long deleteReservation(Long id) {
        if(reservationRepository.findById(id).isPresent()) {
           reservationRepository.deleteById(id);
            return id;
        }
        else {
            throw new HotelServiceException(EXCEPTION_MESSAGE_RESERVATION);
        }
    }

    private void printFreeDates(
            Room room, LocalDate dateBegin, LocalDate dateEnd){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Room(id = " + room.getId() + ") is booked from "
        + dateBegin + " to " + dateEnd + ". ");
        log.debug(stringBuilder);
        stringBuilder.append("Free dates: ");

        LocalDate checkDataBegin = dateBegin.minusDays(DEVIATION);
        Period period = Period.between(
                checkDataBegin, dateEnd.plusDays(DEVIATION + 1));
        for(int i = 0; i < period.getDays(); i++) {
            if(roomRepository.findIntersectingReservation(room,
                    checkDataBegin.plusDays(i), checkDataBegin.plusDays(i)) < 1) {
                stringBuilder.append(checkDataBegin.plusDays(i) + ", ");
            }
        }
            /*Обрезаем у итоговой строки последние два символа
            для корректного отображения(без ", ")*/
        throw new HotelServiceException(stringBuilder.substring(
                0, stringBuilder.length() - 2));
    }


}
