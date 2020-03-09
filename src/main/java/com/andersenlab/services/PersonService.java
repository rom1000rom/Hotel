package com.andersenlab.services;


import com.andersenlab.model.Person;


import java.util.List;

/**Интерфейс служит для определения сервисных функций по работе с пользователями.
 @author Артемьев Р.А.
 @version 09.03.2020 */
public interface PersonService {

     /**Метод возвращает список всех пользователей.
     @return список объектов класса Person*/
     List<Person> findAllPersons();

     /**Метод возвращает объект пользователя по его id
      @param id id пользователя
      @return объект класса Person или null, если такого нет*/
     Person findPersonById(Long id);

     /**Метод сохраняет объект пользователя
      @param person объект пользователя, которого нужно сохранить
      @return объект пользователя в базе*/
      Person savePerson(Person person);

     /**Метод удаляет объект пользователя по id
      @param id пользователя, которого нужно удалить
      @return id удалённого пользователя, или null если такого нет*/
      Long deletePerson(Long id);
}
