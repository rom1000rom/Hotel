package com.andersenlab.services;

import com.andersenlab.dao.RoomRepository;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**Класс реализует сервисные функции по работе с номерами отеля.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> findAllRooms() {
        return (List<Room>)roomRepository.findAll();
    }

    @Override
    public Room findRoomById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new HotelServiceException("Such a room does not exist"));
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Long deleteRoom(Long id) {
        if(roomRepository.findById(id).isPresent()) {//Если Room с таким id существует
            roomRepository.deleteById(id);
            return id;
        }
        else {
            throw new HotelServiceException("Such a room does not exist");
        }
    }
}
