package com.andersenlab.security;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**Класс необходим для хранения имени пользователя и пароля, которые мы получаем от клиента
 @author Артемьев Р.А.
 @version 22.03.2020 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
}
