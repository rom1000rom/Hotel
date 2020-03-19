package com.andersenlab.services;

import com.andersenlab.dao.HotelDao;
import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.RoomDTO;
import com.andersenlab.dto.RoomPostPutDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Hotel;
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
    public RoomPostPutDTO saveRoom(RoomPostPutDTO roomPostPutDTO) {
        Hotel hotel = hotelDao.findById(roomPostPutDTO.getHotelId().getId())
                .orElseThrow(() -> new HotelServiceException("Such a hotel does not exist"));
        Room room = mapperFacade.map(roomPostPutDTO, Room.class);
        room.setHotelId(hotel);
        return mapperFacade.map(roomRepository.save(room), RoomPostPutDTO.class);
    }

    @Override
    public RoomPostPutDTO updateRoom(RoomPostPutDTO roomPostPutDTO) {
        Room room = roomRepository.findById(roomPostPutDTO.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        room.setNumber(roomPostPutDTO.getNumber());
        return mapperFacade.map(roomRepository.save(room), RoomPostPutDTO.class);
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
