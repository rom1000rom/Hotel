package com.andersenlab.service.impl;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.ReservationDto;
import com.andersenlab.dto.RoomDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import com.andersenlab.service.ReservationService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Integer DEVIATION = 3;

    private static final String EXCEPTION_MESSAGE = "Such a reservation does not exist";

    @Override
    public List<ReservationDto> findAllReservations() {
        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation, ReservationDto.class);
    }

    @Override
    public ReservationDto findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(reservation, ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findReservationsByPersonId(Long id) {
        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation.stream()
                .filter(res-> res.getPerson().getId().equals(id))
                .collect(Collectors.toList()), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findReservationsByRoomId(Long id) {
        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation.stream()
                .filter(res-> res.getRoom().getId().equals(id))
                .collect(Collectors.toList()), ReservationDto.class);
    }

    @Override
    public Long saveReservation(ReservationDto reservationDTO) {

        Person person = personRepository.findById(reservationDTO.getPerson().getId())
                .orElseThrow(() -> new HotelServiceException("Such a person does not exist"));

        Room room = roomRepository.findById(reservationDTO.getRoom().getId())
                .orElseThrow(() -> new HotelServiceException("Such a room does not exist"));

        LocalDate dateBegin = reservationDTO.getDateBegin();
        LocalDate dateEnd = reservationDTO.getDateEnd();

        if(dateBegin.isAfter(dateEnd))
            throw new HotelServiceException("Incorrect dates");

        if(roomRepository.findIntersectingReservations(room.getId(), dateBegin, dateEnd)
                > 0) {//Если номер забронирован на указанные даты
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("This room is booked for these dates. ");
            stringBuilder.append("Free dates: ");

            LocalDate checkDataBegin = dateBegin.minusDays(DEVIATION);
            Period period = Period.between(
                    checkDataBegin, dateEnd.plusDays(DEVIATION + 1));
            for(int i = 0; i < period.getDays(); i++) {
                if(roomRepository.findIntersectingReservations(room.getId(),
                        checkDataBegin.plusDays(i), checkDataBegin.plusDays(i)) < 1) {
                    stringBuilder.append(checkDataBegin.plusDays(i) + ", ");
                }
            }
            throw new HotelServiceException(stringBuilder.substring(
                    0, stringBuilder.length() - 2));
        }

        Reservation reservation = mapperFacade.map(reservationDTO, Reservation.class);
        reservation.setPerson(person);
        reservation.setRoom(room);

        return reservationRepository.save(reservation).getId();
    }

    @Override
    public ReservationDto updateReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationDto.getId()).
                orElseThrow(() -> new HotelServiceException(EXCEPTION_MESSAGE));

        Room room = roomRepository.findById(reservationDto.getRoom().getId())
                .orElseThrow(() -> new HotelServiceException("Such a room does not exist"));

        LocalDate dateBegin = reservationDto.getDateBegin();
        LocalDate dateEnd = reservationDto.getDateEnd();
        if (dateBegin.isAfter(dateEnd))
            throw new HotelServiceException("Incorrect dates");

        if (roomRepository.findIntersectingReservations(
                room.getId(), dateBegin, dateEnd)
                > 0) {//Если номер забронирован на указанные даты
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Room is booked for these dates. ");
            stringBuilder.append("Free dates: ");

            LocalDate checkDataBegin = dateBegin.minusDays(DEVIATION);
            Period period = Period.between(
                    checkDataBegin, dateEnd.plusDays(DEVIATION + 1));
            for (int y = 0; y < period.getDays(); y++) {
                if (roomRepository.findIntersectingReservations(room.getId(),
                        checkDataBegin.plusDays(y), checkDataBegin.plusDays(y)) <= 0) {
                    stringBuilder.append(checkDataBegin.plusDays(y) + ", ");
                }
            }
            throw new HotelServiceException(stringBuilder.substring(
                    0, stringBuilder.length() - 2));
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
            throw new HotelServiceException(EXCEPTION_MESSAGE);
        }
    }

}
