package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.ReservationController;
import com.andersenlab.dto.ReservationDTO;
import com.andersenlab.services.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
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
        List<ReservationDTO> listReservationDTO = new ArrayList<>();
        listReservationDTO.add(new ReservationDTO(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21")));
        listReservationDTO.add(new ReservationDTO(
                LocalDate.parse("2016-09-22"), LocalDate.parse("2016-09-24")));
        //Настраиваю поведение мока
        when(reservationService.findAllReservations()).thenReturn(listReservationDTO);

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(listReservationDTO)));//Конвертируем в json
    }

    @Test
    public void testFindReservationById() throws Exception
    {
        Long id = 10L;
        ReservationDTO reservationDTO = new ReservationDTO(
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
        Long id = 10L;
        ReservationDTO actual= new ReservationDTO(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));
        ReservationDTO expected  = new ReservationDTO(
                LocalDate.parse("2016-09-19"), LocalDate.parse("2016-09-21"));

        when(reservationService.saveReservation(actual)).thenReturn(id);
        expected.setId(id);

        mockMvc.perform(post("/reservations")
                .content(objectMapper.writeValueAsString(actual))
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is(201))//Проверяем Http-ответ
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
