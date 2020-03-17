package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.ReservationDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    private static final String EXCEPTION_MESSAGE = "Such a reservation does not exist";

    @Override
    public List<ReservationDTO> findAllReservations() {
        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation, ReservationDTO.class);
    }

    @Override
    public ReservationDTO findReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(reservation, ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> findReservationsByPersonId(Long id) {
        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation.stream()
                .filter(res-> res.getPerson().getId().equals(id))
                .collect(Collectors.toList()), ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> findReservationsByRoomId(Long id) {
        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return mapperFacade.mapAsList(listReservation.stream()
                .filter(res-> res.getRoom().getId().equals(id))
                .collect(Collectors.toList()), ReservationDTO.class);
    }

    @Override
    public Long saveReservation(ReservationDTO reservationDTO) {
        Person person = personRepository.findById(reservationDTO.getPerson().getId())
                .orElseThrow(() -> new HotelServiceException("Such a person does not exist"));
        Room room = roomRepository.findById(reservationDTO.getRoom().getId())
                .orElseThrow(() -> new HotelServiceException("Such a room does not exist"));

        LocalDate dateBegin = reservationDTO.getDateBegin();
        LocalDate dateEnd = reservationDTO.getDateEnd();
        if(dateBegin.isAfter(dateEnd))//Если номер забронирован на указанные даты
            throw new HotelServiceException("Incorrect dates");
        if(room.isBooked(dateBegin, dateEnd))//Если номер забронирован на указанные даты
            throw new HotelServiceException("This room is booked for these dates");

        Reservation reservation = mapperFacade.map(reservationDTO, Reservation.class);
        reservation.setPerson(person);
        reservation.setRoom(room);

        return reservationRepository.save(reservation).getId();
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
