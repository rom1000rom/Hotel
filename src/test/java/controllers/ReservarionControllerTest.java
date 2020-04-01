package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.ReservationController;
import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.ReservationDto;
import com.andersenlab.dto.RoomDto;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.service.ReservationService;
import com.andersenlab.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = App.class)
@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(ReservationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReservarionControllerTest {

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @BeforeClass
    public static void setUpClass()
    {
        objectMapper = new ObjectMapper();
        //Это позволяет корректно отражать LocalDate библиотекой Jackson
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    public void testFindAllReservations() throws Exception
    {
        //Подготавливаю ожидаемый результат
        List<ReservationDto> listReservationDto = new ArrayList<>();
        listReservationDto.add(new ReservationDto(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21")));
        listReservationDto.add(new ReservationDto(
                LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-24")));
        //Настраиваю поведение мока
        when(reservationService.findAllReservations()).thenReturn(listReservationDto);

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(listReservationDto)));//Конвертируем в json
    }

    @Test
    public void testFindReservationById() throws Exception
    {
        Long id = 10L;
        ReservationDto reservationDTO = new ReservationDto(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
        reservationDTO.setId(id);
        when(reservationService.findReservationById(id)).thenReturn(reservationDTO);

        mockMvc.perform(get("/reservations/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(reservationDTO)));
    }

    @Test
    public void testSaveReservation() throws Exception
    {
        Long id = 0L;
        ReservationDto actual= new ReservationDto(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));

        PersonDto personDTO = new PersonDto("TEST");
        personDTO.setId(id);

        RoomDto roomDTO = new RoomDto("TEST");
        roomDTO.setId(id);

        actual.setRoom(roomDTO);
        actual.setPerson(personDTO);

        ReservationDto expected  = new ReservationDto(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
        expected.setRoom(roomDTO);
        expected.setPerson(personDTO);

        when(reservationService.saveReservation(actual)).thenReturn(id);
        expected.setId(id);

        mockMvc.perform(post("/reservations")
                .content(objectMapper.writeValueAsString(actual))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testUpdateReservation() throws Exception
    {
        Long id = 10L;
        ReservationDto expected  = new ReservationDto(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
        expected.setId(id);
        when(reservationService.updateReservation(expected)).thenReturn(expected);

        mockMvc.perform(put("/reservations")
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testDeleteReservation() throws Exception
    {
        Long id = 1L;
        when(reservationService.deleteReservation(id)).thenReturn(id);

        mockMvc.perform(delete("/reservations/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(id)));//Конвертируем в json
    }

}
