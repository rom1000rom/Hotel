package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.PersonController;
import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistartionDto;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.services.PersonService;
import com.andersenlab.services.impl.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllPersons() throws Exception
    {
        //Подготавливаю ожидаемый результат
        List<PersonDto> listPersonDto = new ArrayList<>();
        listPersonDto.add(new PersonDto("TEST"));
        listPersonDto.add(new PersonDto("TEST2"));
        //Настраиваю поведение мока
        when(personService.findAllPersons()).thenReturn(listPersonDto);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(listPersonDto)));//Конвертируем в json
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
        String password = "password";
        String encrytedPassword = "ENCR_PASSWORD";

        PersonRegistartionDto actual= new PersonRegistartionDto("TEST_NAME");
        actual.setEncrytedPassword(password);
        PersonRegistartionDto expected  = new PersonRegistartionDto("TEST_NAME");

        when(passwordEncoder.encode(actual.getEncrytedPassword())).thenReturn(encrytedPassword);
        expected.setEncrytedPassword(encrytedPassword);
        when(personService.savePerson(actual)).thenReturn(id);
        expected.setId(id);

        mockMvc.perform(post("/persons")
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
        String password = "password";
        String encrytedPassword = "ENCR_PASSWORD";

        PersonRegistartionDto actual= new PersonRegistartionDto("TEST_NAME_NEW");
        actual.setEncrytedPassword(password);
        actual.setId(id);
        PersonRegistartionDto expected  = new PersonRegistartionDto("TEST_NAME_NEW");
        expected.setId(id);

        when(passwordEncoder.encode(actual.getEncrytedPassword())).thenReturn(encrytedPassword);
        expected.setEncrytedPassword(encrytedPassword);
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
