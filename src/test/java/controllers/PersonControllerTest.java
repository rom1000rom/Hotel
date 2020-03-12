package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.PersonController;
import com.andersenlab.dto.PersonDTO;
import com.andersenlab.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testFindAllPersons() throws Exception
    {
        //Подготавливаю ожидаемый результат
        List<PersonDTO> listPersonDTO = new ArrayList<>();
        listPersonDTO.add(new PersonDTO("TEST", "TEST"));
        listPersonDTO.add(new PersonDTO("TEST2", "TEST2"));
        //Настраиваю поведение мока
        Mockito.when(personService.findAllPersons()).thenReturn(listPersonDTO);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(listPersonDTO)));//Конвертируем в json
    }

    @Test
    public void testFindPersonById() throws Exception
    {
        Long id = 10L;
        PersonDTO personDTO = new PersonDTO("TEST", "TEST");
        Mockito.when(personService.findPersonById(id)).thenReturn(personDTO);

        mockMvc.perform(get("/persons/10"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(personDTO)));
    }

    @Test
    public void testSavePerson() throws Exception
    {
        Long id = 10L;
        String password = "password";
        PersonDTO actual= new PersonDTO("TEST_NAME", password);
        actual.setId(0L);
        PersonDTO expected  = new PersonDTO("TEST_NAME","ENCR_PASSWORD");


        when(passwordEncoder.encode(password)).thenReturn("ENCR_PASSWORD");
        when(personService.savePerson(actual)).thenReturn(id);
        expected.setId(id);

        mockMvc.perform(post("/persons")
                .content(objectMapper.writeValueAsString(actual))
                .param("password", password)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201))//Проверяем Http-ответ
                .andExpect(content().string(
                        objectMapper.writeValueAsString(expected)));//Конвертируем в json
    }

}
