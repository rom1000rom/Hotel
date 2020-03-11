package controllers;



import com.andersenlab.App;
import com.andersenlab.controllers.PersonController;
import com.andersenlab.model.Person;
import com.andersenlab.services.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class PersonControllerTest {
    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllPersons() throws Exception
    {
        //Подготавливаю ожидаемый результат
        List<Person> listPerson = new ArrayList<>();
        listPerson.add(new Person("TEST", "TEST"));
        listPerson.add(new Person("TEST2", "TEST2"));
        //Настраиваю поведение мока
        Mockito.when(personService.findAllPersons()).thenReturn(listPerson);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())//Проверяем Http-ответ
                .andExpect(content().string(
                        new ObjectMapper().writeValueAsString(listPerson)));//Конвертируем в json
    }
}
