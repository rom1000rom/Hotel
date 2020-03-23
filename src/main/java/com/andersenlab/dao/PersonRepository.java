package com.andersenlab.dao;

import com.andersenlab.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**Интерфейс служит для определения функций хранилища данных об пользователях
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findOneByPersonNameLike(String personName);
}
