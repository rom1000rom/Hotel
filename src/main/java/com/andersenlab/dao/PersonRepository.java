package com.andersenlab.dao;

import com.andersenlab.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**Интерфейс служит для определения функций хранилища данных об пользователях
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findOneByNameLike(String personName);
}
