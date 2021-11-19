package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.entity.UserKeyCloak;
import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;


@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthKeyCloakService authKeyCloakService;

    @PostMapping(value = "/sign-up",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createUser(@RequestBody UserKeyCloak userKeyCloak) {
        log.info("User: {}", userKeyCloak);
        Response response = authKeyCloakService.signup(userKeyCloak);
        return new ResponseEntity<>(HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        ResponseBuilder response = new ResponseBuilder.Builder(200)
                .buildMessage("login successfully")
                .buildData(authKeyCloakService.login(username, password))
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PostMapping(value = "/logout",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> logout(@RequestParam String refreshToken) {
        ResponseBuilder response = authKeyCloakService.logout(refreshToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }

    @PostMapping(value = "/refresh",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> refresh(@RequestHeader("Authorization") String bearerToken) {
        String refreshToken = bearerToken.split(" ")[1];
        ResponseBuilder response = new ResponseBuilder.Builder(200)
                .buildMessage("refresh token successfully")
                .buildData(authKeyCloakService.refreshToken(refreshToken))
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


    @PostMapping(value = "/update-password",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePassword(@RequestParam String username,
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        ResponseBuilder response = authKeyCloakService.changePassword(username, oldPassword, newPassword);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


}
