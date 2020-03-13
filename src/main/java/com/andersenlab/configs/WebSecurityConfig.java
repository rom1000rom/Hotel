package com.andersenlab.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    /*Метод configure(HttpSecurity) определяет, какие URL пути
    должны быть защищены, а какие нет.*/
    protected void configure(HttpSecurity http) throws Exception
    {

        http.csrf().disable();/*
        http
                .authorizeRequests()
                   .antMatchers("/users", "/registration").permitAll()
                   .anyRequest().authenticated()//Запросы доступны только авторизованным пользователям
                   .and()
                .formLogin()//Задаём параметры формы для ввода данных пользователя
                   .loginPage("/login")
                   .loginProcessingUrl("/perform_login") // Submit URL
                   .usernameParameter("username")
                   .passwordParameter("password")
                   .defaultSuccessUrl("/", true)
                   .failureUrl("/error_page")
                   .permitAll()//Страница /login доступна всем пользователям
                   .and()
                .logout()
                   .logoutUrl("/perform_logout")
                   .logoutSuccessUrl("/login")
                   .permitAll();*/
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}



