package com.andersenlab.services;

import com.andersenlab.dao.HotelDao;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.HotelDto;
import com.andersenlab.dto.RoomDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Hotel;
import com.andersenlab.model.Person;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


/**Класс реализует сервисные функции по работе с номерами отеля.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelDao hotelDao;

    @Autowired
    private MapperFacade mapperFacade;

    private static final String EXCEPTION_MESSAGE = "Such a room does not exist";

    @Override
    public List<RoomDTO> findAllRooms() {
        List<Room> listRoom = (List<Room>)roomRepository.findAll();
        return mapperFacade.mapAsList(listRoom, RoomDTO.class);
    }

    @Override
    public RoomDTO findRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(room, RoomDTO.class);
    }

    @Override
    public Long saveRoom(RoomDTO roomDTO) {

        Room room = mapperFacade.map(roomDTO, Room.class);

        return roomRepository.save(room).getId();
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        room.setNumber(roomDTO.getNumber());
        return mapperFacade.map(roomRepository.save(room), RoomDTO.class);
    }

    @Override
    public Long deleteRoom(Long id) {
        if(roomRepository.findById(id).isPresent()) {//Если Room с таким id существует
            roomRepository.deleteById(id);
            return id;
        }
        else {
            throw new HotelServiceException(EXCEPTION_MESSAGE);
        }
    }
}
