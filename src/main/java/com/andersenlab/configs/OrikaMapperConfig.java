package com.andersenlab.configs;


import com.andersenlab.dto.*;
import com.andersenlab.model.Facilities;
import com.andersenlab.model.Hotel;
import com.andersenlab.model.Invoice;
import com.andersenlab.model.Person;
import com.andersenlab.model.Reservation;
import com.andersenlab.model.Room;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;



/**
 * Класс представляет собой конфигурацию Orika Mapper
 * 
 * @author Артемьев Р.А.
 * @version 17.03.2020
 */
@Configuration
public class OrikaMapperConfig {

  @Bean("mapperFacade")
  public MapperFacade getMapperFacade() {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    // Позволяет библиотеке Orika Mapper корректно отображать LocalDate
    mapperFactory.getConverterFactory()
        .registerConverter(new PassThroughConverter(LocalDate.class));

    mapperFactory.classMap(Reservation.class, ReservationDto.class).byDefault().register();

    mapperFactory.classMap(Person.class, PersonDto.class).byDefault().register();

    mapperFactory.classMap(Person.class, PersonRegistartionDto.class).byDefault().register();

    mapperFactory.classMap(Room.class, RoomDto.class).byDefault().register();

    mapperFactory.classMap(HotelDto.class, Hotel.class).exclude("version").mapNulls(false)
        .byDefault().register();
    mapperFactory.classMap(Hotel.class, HotelDto.class).exclude("version").mapNulls(false)
        .byDefault().register();

    mapperFactory.classMap(FacilitiesDto.class, Facilities.class).byDefault().register();

    mapperFactory.classMap(Facilities.class, FacilitiesDto.class).byDefault().register();

    mapperFactory.classMap(InvoiceDto.class, Invoice.class).exclude("version").mapNulls(false)
        .byDefault().register();

    mapperFactory.classMap(Invoice.class, InvoiceDto.class).exclude("version").mapNulls(false)
        .byDefault().register();

    return mapperFactory.getMapperFacade();
  }

}
