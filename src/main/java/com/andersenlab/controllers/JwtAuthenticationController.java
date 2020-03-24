package com.andersenlab.controllers;


import com.andersenlab.security.JwtRequest;
import com.andersenlab.security.JwtResponse;
import com.andersenlab.security.JwtTokenUtil;
import com.andersenlab.services.impl.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Контроллёр получает имя пользователя и пароль в теле. Используя
 * Spring Authentication Manager, мы аутентифицируем имя пользователя и пароль.
 * Если учетные данные действительны, токен JWT создается с использованием
 * JWTTokenUtil и предоставляется клиенту.
 *
 * @author Артемьев Р.А.
 * @version 22.03.2020
 */
@RestController
@CrossOrigin
@Api(description = "Operations pertaining to authorization")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "Request for authorization token")
    public ResponseEntity<JwtResponse> createAuthenticationToken(
            @RequestBody JwtRequest authenticationRequest) throws Exception {

        jwtTokenUtil.changeSecret();

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping(value = "isSessionActive")
    @ApiOperation(value = "Find out if a session is active",
            authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<Boolean> isSessionActive(HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getRequestedSessionId() != null
                && !httpServletRequest.isRequestedSessionIdValid()) {
            return ResponseEntity.ok().body(false);
        }
        else
            return ResponseEntity.ok().body(true);

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}