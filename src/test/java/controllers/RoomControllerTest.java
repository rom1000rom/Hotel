package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.RoomController;
import com.andersenlab.dto.HotelDto;
import com.andersenlab.dto.RoomDto;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.service.RoomService;
import com.andersenlab.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = App.class)
@RunWith(SpringRunner.class)
/*Аннотация @WithMockUser создает пользователя, который уже аутентифицирован, в тестовых целях.
По умолчанию его учетные данные: user, password*/
@WithMockUser
/*Аннотация @WebMvcTest создаёт тестовое окружение с настроенным
Spring MVC и входящим в него Jackson, в том виде, в каком они настроены в реальном приложении.*/
@WebMvcTest(RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {

    @MockBean
    private RoomService roomService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllRooms() throws Exception
    {
        //Подготавливаю ожидаемый результат
        List<RoomDto> listRoomDto = new ArrayList<>();
        listRoomDto.add(new RoomDto("TEST"));
        listRoomDto.add(new RoomDto("TEST2"));
        //Настраиваю поведение мока
        when(roomService.findAllRooms()).thenReturn(listRoomDto);

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(listRoomDto)));//Конвертируем в json
    }

    @Test
    public void testFindRoomById() throws Exception
    {
        Long id = 10L;
        RoomDto roomDTO = new RoomDto("TEST");
        roomDTO.setId(id);
        when(roomService.findRoomById(id)).thenReturn(roomDTO);

        mockMvc.perform(get("/rooms/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(roomDTO)));
    }

    @Test
    public void testSaveRoom() throws Exception
    {
        Long id = 0L;
        HotelDto hotelDto = new HotelDto();

        RoomDto actual= new RoomDto("TEST_NAME");
        actual.setHotelId(hotelDto);

        RoomDto expected  = new RoomDto("TEST_NAME");
expected.setHotelId(hotelDto);

        when(roomService.saveRoom(actual)).thenReturn(id);
        expected.setId(id);

        mockMvc.perform(post("/rooms")
                .content(objectMapper.writeValueAsString(actual))
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testDeleteRoom() throws Exception
    {
        Long id = 1L;
        when(roomService.deleteRoom(id)).thenReturn(id);

        mockMvc.perform(delete("/rooms/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(id)));//Конвертируем в json
    }

    @Test
    public void testUpdateRoom() throws Exception
    {
        Long id = 10L;
        RoomDto expected  = new RoomDto("TEST_NUM_NEW");
        expected.setId(id);

        when(roomService.updateRoom(expected)).thenReturn(expected);

        mockMvc.perform(put("/rooms")
                .content(objectMapper.writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

}
