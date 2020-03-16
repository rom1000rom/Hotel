package com.andersenlab.services;

import com.andersenlab.dao.RoomRepository;
import com.andersenlab.dto.RoomDTO;
import com.andersenlab.exceptions.HotelServiceException;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**Класс реализует сервисные функции по работе с номерами отеля.
 @author Артемьев Р.А.
 @version 09.03.2020 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    private static final String EXCEPTION_MESSAGE = "Such a room does not exist";

    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    static {//Позволяет библиотеке Orika Mapper корректно отображать LocalDate
        mapperFactory.getConverterFactory().registerConverter(
                new PassThroughConverter(LocalDate.class));
    }

    @Override
    public List<RoomDTO> findAllRooms() {
        mapperFactory.classMap(Room.class, RoomDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        List<Room> listRoom = (List<Room>)roomRepository.findAll();
        return listRoom.stream().map(room -> mapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDTO findRoomById(Long id) {
        mapperFactory.classMap(Room.class, RoomDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Room room = roomRepository.findById(id).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));
        return mapper.map(room, RoomDTO.class);
    }

    @Override
    public Long saveRoom(RoomDTO roomDTO) {
        mapperFactory.classMap(RoomDTO.class, Room.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();
        Room room = roomRepository.save(mapper.map(roomDTO, Room.class));
        return room.getId();
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        Room room = roomRepository.findById(roomDTO.getId()).orElseThrow(() ->
                new HotelServiceException(EXCEPTION_MESSAGE));

        mapperFactory.classMap(Room.class, RoomDTO.class);
        MapperFacade mapper = mapperFactory.getMapperFacade();

        room.setNumber(roomDTO.getNumber());

        return mapper.map(roomRepository.save(room), RoomDTO.class);
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
