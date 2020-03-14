package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.ReservationDTO;
import com.andersenlab.dto.RoomDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**Класс реализует сервисные функции по работе с бронированиями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoomRepository roomRepository;

    private static final String EXCEPTION_MESSAGE = "Such a reservation does not exist";

    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    static {
        mapperFactory.getConverterFactory().registerConverter(
                new PassThroughConverter(LocalDate.class));
    }

    @Override
    public List<ReservationDTO> findAllReservations() {
        mapperFactory.classMap(Reservation.class, ReservationDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        List<Reservation> listReservation = (List<Reservation>)reservationRepository.findAll();
        return listReservation.stream().map((reservation) ->
                mapper.map(reservation, ReservationDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO findReservationById(Long id) {
        mapperFactory.classMap(Reservation.class, ReservationDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public Long saveReservation(ReservationDTO reservationDTO) {
        mapperFactory.classMap(ReservationDTO.class, Reservation.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        Person person = personRepository.findById(reservationDTO.getPersonId())
                .orElseThrow(() -> new HotelServiceException("Such a person does not exist"));
        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new HotelServiceException("Such a room does not exist"));

        LocalDate dateBegin = reservationDTO.getDateBegin();
        LocalDate dateEnd = reservationDTO.getDateEnd();
        if(room.isBooked(dateBegin, dateEnd))//Если номер забронирован на указанные даты
            throw new HotelServiceException("This room is booked for these dates");
        Reservation reservation = mapper.map(reservationDTO, Reservation.class);
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
