package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.request.ChangePasswordForm;
import com.teamwork.chatbot.dto.response.TokenResponse;
import com.teamwork.chatbot.entity.RegistrationUserInfo;
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

    @PostMapping(value = "/signup",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createUser(@RequestBody RegistrationUserInfo registrationUserInfo) {
        log.info("User: {}", registrationUserInfo);
        Response response = authKeyCloakService.signup(registrationUserInfo);
        return new ResponseEntity<>(HttpStatus.valueOf(response.getStatus()));
    }
    // TODO error when input invalid token: not response as ResponseBuilder Object (code, message, data)
    @PostMapping(value = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {
        ResponseBuilder response;
        TokenResponse tokenResponse;
        try {
            tokenResponse = authKeyCloakService.login(username,password);
            response = new ResponseBuilder.Builder(HttpStatus.OK.value())
                    .buildMessage("login successfully")
                    .buildData(tokenResponse)
                    .build();
        } catch (Exception e) {
            response = new ResponseBuilder.Builder(HttpStatus.UNAUTHORIZED.value())
                    .buildMessage("incorrect password")
                    .buildData("")
                    .build();
        }
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
        ResponseBuilder response;
        try {
            response = new ResponseBuilder.Builder(200)
                    .buildMessage("refresh token successfully")
                    .buildData(authKeyCloakService.refreshToken(refreshToken))
                    .build();
        } catch (Exception e) {
            response = new ResponseBuilder.Builder(HttpStatus.BAD_REQUEST.value())
                    .buildMessage("refresh token is invalid!")
                    .buildData("")
                    .build();
        }
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


    @PostMapping(value = "/update-password",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updatePassword(@RequestBody ChangePasswordForm changePasswordForm,
                                                 @RequestHeader("Authorization") String bearerToken) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder response = authKeyCloakService.changePassword(changePasswordForm, accessToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


}
