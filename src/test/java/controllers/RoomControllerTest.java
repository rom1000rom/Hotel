package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.RoomController;
import com.andersenlab.dto.HotelDto;
import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.RoomDto;
import com.andersenlab.dto.page.PersonPageDto;
import com.andersenlab.dto.page.RoomPageDto;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.service.RoomService;
import com.andersenlab.service.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @MockBean
    private MapperFacade mapperFacade;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllRooms() throws Exception
    {
        Integer pageNum = 0;
        Integer pageSize = 2;
        List<RoomDto> listRoomDto = new ArrayList<>();
        listRoomDto.add(new RoomDto("TEST"));
        listRoomDto.add(new RoomDto("TEST2"));
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<RoomDto> roomPage = new PageImpl<>(
                listRoomDto, pageable, listRoomDto.size());
        //Настраиваю поведение мока
        when(roomService.findAllRooms(pageable)).thenReturn(roomPage);
        RoomPageDto result = new RoomPageDto();
        mapperFacade.map(roomPage, result);

        mockMvc.perform(get("/rooms")
                .param("pageNumber", pageNum.toString())
                .param("pageSize", pageSize.toString()))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(result)));//Конвертируем в json
    }

    @Test
    public void testFindAvailableRooms() throws Exception
    {
        Integer pageNum = 0;
        Integer pageSize = 2;
        Integer minPrice = 500;
        Integer maxPrice = 1000;
        Integer guests = 2;
        LocalDate dateBegin = LocalDate.parse("2016-09-23");
        LocalDate dateEnd = LocalDate.parse("2016-09-25");
        List<RoomDto> listRoomDto = new ArrayList<>();
        listRoomDto.add(new RoomDto("TEST"));
        listRoomDto.add(new RoomDto("TEST2"));
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<RoomDto> roomPage = new PageImpl<>(
                listRoomDto, pageable, listRoomDto.size());
        //Настраиваю поведение мока
        when(roomService.findAvailableRooms(pageable, dateBegin, dateBegin,
                minPrice, maxPrice, guests)).thenReturn(roomPage);
        RoomPageDto result = new RoomPageDto();
        mapperFacade.map(roomPage, result);

        mockMvc.perform(get("/rooms/findAvailableRooms")
                .param("dateBegin", dateBegin.toString())
                .param("dateEnd", dateEnd.toString())
                .param("minPrice", minPrice.toString())
                .param("maxPrice", pageNum.toString())
                .param("guests", guests.toString())
                .param("pageNumber", pageNum.toString())
                .param("pageSize", pageSize.toString()))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(result)));//Конвертируем в json
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
