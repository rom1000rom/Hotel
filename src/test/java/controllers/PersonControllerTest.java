package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.PersonController;
import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistartionDto;
import com.andersenlab.dto.ReservationDto;
import com.andersenlab.dto.page.PersonPageDto;
import com.andersenlab.dto.page.ReservationPageDto;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.service.PersonService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private MapperFacade mapperFacade;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllPersons() throws Exception
    {
        Integer pageNum = 0;
        Integer pageSize = 2;
        List<PersonDto> listPersonDto = new ArrayList<>();
        listPersonDto.add(new PersonDto("TEST"));
        listPersonDto.add(new PersonDto("TEST2"));
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<PersonDto> personPage = new PageImpl<>(
                listPersonDto, pageable, listPersonDto.size());
        //Настраиваю поведение мока
        when(personService.findAllPersons(pageable)).thenReturn(personPage);
        PersonPageDto result = new PersonPageDto();
        mapperFacade.map(personPage, result);

        mockMvc.perform(get("/persons")
                .param("pageNumber", pageNum.toString())
                .param("pageSize", pageSize.toString()))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(result)));//Конвертируем в json
    }

    @Test
    public void testFindPersonById() throws Exception
    {
        Long id = 10L;
        PersonDto personDTO = new PersonDto("TEST");
        personDTO.setId(id);
        when(personService.findPersonById(id)).thenReturn(personDTO);

        mockMvc.perform(get("/persons/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(personDTO)));
    }

    @Test
    public void testSavePerson() throws Exception
    {
        Long id = 0L;
        String pas = "password";
        String encrytedPas = "ENCR_PASSWORD";

        PersonRegistartionDto actual= new PersonRegistartionDto("TEST_NAME");
        actual.setEncrytedPassword(pas);
        PersonRegistartionDto expected  = new PersonRegistartionDto("TEST_NAME");

        when(passwordEncoder.encode(actual.getEncrytedPassword())).thenReturn(encrytedPas);
        expected.setEncrytedPassword(encrytedPas);
        when(personService.savePerson(actual)).thenReturn(id);
        expected.setId(id);

        mockMvc.perform(post("/persons/registration")
                .content(objectMapper.writeValueAsString(actual))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testDeletePerson() throws Exception
    {
        Long id = 1L;
        when(personService.deletePerson(id)).thenReturn(id);

        mockMvc.perform(delete("/persons/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(id)));//Конвертируем в json
    }

    @Test
    public void testUpdatePerson() throws Exception
    {
        Long id = 10L;
        String pas = "password";
        String encrytedPas = "ENCR_PASSWORD";

        PersonRegistartionDto actual= new PersonRegistartionDto("TEST_NAME_NEW");
        actual.setEncrytedPassword(pas);
        actual.setId(id);
        PersonRegistartionDto expected  = new PersonRegistartionDto("TEST_NAME_NEW");
        expected.setId(id);

        when(passwordEncoder.encode(actual.getEncrytedPassword())).thenReturn(encrytedPas);
        expected.setEncrytedPassword(encrytedPas);
        when(personService.updatePerson(expected)).thenReturn(expected);

        mockMvc.perform(put("/persons")
                .content(objectMapper.writeValueAsString(actual))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

    @Test
    public void testAddToBlacklist() throws Exception
    {
        Long id = 1L;
        when(personService.addToBlacklist(id)).thenReturn(id);

        mockMvc.perform(put("/persons/addToBlacklist/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(id)));//Конвертируем в json
    }

    @Test
    public void testRemoveFromBlacklist() throws Exception
    {
        Long id = 1L;
        when(personService.removeFromBlacklist(id)).thenReturn(id);

        mockMvc.perform(put("/persons/removeFromBlacklist/" + id))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(id)));//Конвертируем в json
    }

}
