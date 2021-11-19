package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/user-info")
@Slf4j
public class UserInforController {

    @Autowired
    private UserService service;

    //  TODO @RolesAllowed("user-role")       // user nay thi set quuyen o tren keycloak co duoc khong

    @GetMapping(value = "/getUserProfile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserProfile(@RequestHeader("Authorization") String bearerToken) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder response = new ResponseBuilder.Builder(200)
                .buildMessage("get user information successfully")
                .buildData(service.getUserInfo(accessToken))
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


    // TODO k can acess token a
    @PostMapping(value = "/updateProfile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProfile(@RequestParam String username,
                                                @RequestParam String newFirstName,
                                                @RequestParam String newLastName) {
        ResponseBuilder response = service.changeUserProfile(username, newFirstName, newLastName);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }



}
