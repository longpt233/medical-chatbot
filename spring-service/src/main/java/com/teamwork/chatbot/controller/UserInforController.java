package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.request.ChangeUserProfileForm;
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


    @GetMapping(value = "/getUserProfile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUserProfile(@RequestHeader("Authorization") String bearerToken) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder response = service.getUserInfo(accessToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }


    @PostMapping(value = "/updateProfile",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateProfile(@RequestHeader("Authorization") String bearerToken,
                                                @RequestBody ChangeUserProfileForm userProfileForm) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder response = service.changeUserProfile(userProfileForm,accessToken);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getCode()));
    }



}
