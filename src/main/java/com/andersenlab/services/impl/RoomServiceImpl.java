package com.andersenlab.services.impl;

import com.andersenlab.dao.HotelDao;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.RoomDto;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Hotel;
import com.andersenlab.model.Room;
import com.andersenlab.services.RoomService;
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
    public List<RoomDto> findAllRooms() {
        List<Room> listRoom = (List<Room>)roomRepository.findAll();
        return mapperFacade.mapAsList(listRoom, RoomDto.class);
    }

    @Override
    public RoomDto findRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapperFacade.map(room, RoomDto.class);
    }

    @Override
    public Long saveRoom(RoomDto roomDTO) {
       Hotel hotel = hotelDao.findById(roomDTO.getHotelId().getId())
                .orElseThrow(() -> new HotelServiceException("Such a hotel does not exist"));
        Room room = mapperFacade.map(roomDTO, Room.class);
        room.setHotelId(hotel);
        return roomRepository.save(room).getId();
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDTO) {
        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        room.setNumber(roomDTO.getNumber());
        return mapperFacade.map(roomRepository.save(room), RoomDto.class);
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
