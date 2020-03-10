package com.andersenlab.services;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.dao.ReservationRepository;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**Класс реализует сервисные функции по работе с бронированиями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Reservation> findAllReservations() {
        return (List<Reservation>)reservationRepository.findAll();
    }

    @Override
    public Reservation findReservationById(Long id) {
        if(id == null)
            return null;
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        if(reservation == null)//Если объект бронирования null
            return null;
        Person person = reservation.getPerson();
        if(person == null)//Если объект пользователя в бронировании null
           return  null;
        if(!personRepository.findById(person.getId()).isPresent())//Если пользователя с указанным id не существует
            return null;
        Room room = reservation.getRoom();
        if(room == null)//Если объект номера отеля в бронировании null
            return null;
        if(!roomRepository.findById(room.getId()).isPresent())//Если номер с указанным id не существует
            return null;
        LocalDate dateBegin = reservation.getDateBegin();
        LocalDate dateEnd = reservation.getDateEnd();
        //Если даты начала/конца бронирования null
        if((dateBegin ==null)||(dateEnd==null))
            return null;
        if(dateBegin.isAfter(dateEnd))//Если дата начала бронирования позже даты конца
            return null;
        if(room.isBooked(dateBegin, dateEnd))//Если номер забронирован на указанные даты
            return null;

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
            return null;
        }
    }

}
