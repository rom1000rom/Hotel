package com.andersenlab.service;



import com.andersenlab.dto.PersonDto;
import com.andersenlab.dto.PersonRegistrationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**Интерфейс служит для определения сервисных функций по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface PersonService {

     /**Метод возвращает список всех пользователей.
     @return страница объектов класса PersonDTO*/
     Page<PersonDto> findAllPersons(Pageable pageable);

     /**Метод возвращает объект пользователя по его id
      @param id id пользователя
      @return объект класса PersonDTO*/
     PersonDto findPersonById(Long id);

     /**Метод сохраняет объект пользователя
      @param personRegistrationDto объект с данными пользователя, которого нужно сохранить
      @return id объекта пользователя в базе*/
      Long savePerson(PersonRegistrationDto personRegistrationDto);

     /**Метод удаляет объект пользователя по id
      @param id пользователя, которого нужно удалить
      @return id удалённого пользователя*/
      Long deletePerson(Long id);

    /**Метод обновляет объект пользователя
     @param personRegistrationDto объект с данными пользователя, которого нужно обновить
     @param id id пользователя
     @return объект обновлённого пользователя*/
    PersonRegistrationDto updatePerson(PersonRegistrationDto personRegistrationDto, Long id);

    /**Метод добавляет пользователя в чёрный список по id.
     * Если пользователь уже в списке, метод не сделает ничего.
     @param id пользователя, которого нужно добавить в чёрный список
     @return id добавленного пользователя*/
     Long addToBlacklist(Long id);

    /**Метод удаляет пользователя из чёрного списка по id.
     * Если пользователь не в списке, метод не сделает ничего.
     @param id пользователя, которого нужно удалить из черного списка
     @return id  пользователя*/
    Long removeFromBlacklist(Long id);

}
