package com.andersenlab.services.impl;

import com.andersenlab.dao.PersonRepository;
import com.andersenlab.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**Класс позволяет получить из источника данных объект пользователя и сформировать из него
 объект UserDetails который будет использоваться контекстом Spring Security.
 Для этого реализуем единственный метод loadUserByUsername(String username)
 интерфейса UserDetailsService .
 @author Артемьев Р.А.
 @version 06.03.2020 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    private static final Logger log = Logger.getLogger(UserDetailsServiceImpl .class.getName());

    @Override
    public UserDetails loadUserByUsername(String userName)  {
        Person appUser = personRepository.findOneByPersonNameLike(userName);

        if (appUser == null) {
            log.info("User " + userName + " not found!");
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        log.info("Found User: " + appUser);

        GrantedAuthority authority;
        List<GrantedAuthority> grantList = new ArrayList<>();

        if(appUser.getAdmin()){
            authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        } else {
            authority = new SimpleGrantedAuthority("ROLE_USER");
        }

        grantList.add(authority);

        return  new User(appUser.getPersonName(),
                appUser.getEncrytedPassword(), grantList);
    }

}