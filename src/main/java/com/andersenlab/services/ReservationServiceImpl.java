package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public List<Reservation> findAllReservations() {
        return (List<Reservation>)reservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(Long id) {
        if(id == null)
            return null;
        return reservationRepository.findById(id).orElseThrow(() ->
                new HotelServiceException("Such a reservation does not exist"));
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        Person person = reservation.getPerson();
        if(!personRepository.findById(person.getId()).isPresent())//Если пользователя с указанным id не существует
            throw new HotelServiceException("Such a person does not exist");
        Room room = reservation.getRoom();
        if(!roomRepository.findById(room.getId()).isPresent())//Если номер с указанным id не существует
            throw new HotelServiceException("Such a room does not exist");
        LocalDate dateBegin = reservation.getDateBegin();
        LocalDate dateEnd = reservation.getDateEnd();
        if(room.isBooked(dateBegin, dateEnd))//Если номер забронирован на указанные даты
            throw new HotelServiceException("This room is booked");

        return reservationRepository.save(reservation);
    }

    @Override
    public Long deleteReservation(Long id) {
        if(id == null)
            return null;
        if(reservationRepository.findById(id).isPresent()) {
           reservationRepository.deleteById(id);
            return id;
        }
        else {
            throw new HotelServiceException("Such a reservation does not exist");
        }
    }

}
