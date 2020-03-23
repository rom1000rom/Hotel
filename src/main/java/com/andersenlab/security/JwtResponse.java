package com.andersenlab.security;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;



/**Класс необходим для создания ответа, содержащего JWT, который будет возвращен пользователю.
 @author Артемьев Р.А.
 @version 22.03.2020 */
@Data
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
}
