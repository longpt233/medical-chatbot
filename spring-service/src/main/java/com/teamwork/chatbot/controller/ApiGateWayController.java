package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import com.teamwork.chatbot.service.gateway.CallApiOtherContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/gateway")
public class ApiGateWayController {

    @Autowired
    private CallApiOtherContainerService callApiOtherContainerService;

    @Autowired
    private AuthKeyCloakService authKeyCloakService;

    // call module : phai chay docker len nhe
    @GetMapping(value = "/medical-image/predict",
    produces = MediaType.IMAGE_GIF_VALUE)
    public Object predict(@RequestParam("Authorization") String bearerToken,@RequestParam("photo") MultipartFile photo) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder svResponse = callApiOtherContainerService.uploadImage(photo,accessToken);
        if(svResponse.getCode() == 200){
            return svResponse.getData();
        }
        else return "Error when upload image: " + svResponse.getMessage();
    }

//    // call module chatbot
//    @GetMapping("/medical-chatbot/chat")
//    @RolesAllowed("user-role")
//    public ResponseEntity<String> chat() {
//        return ResponseEntity.ok(callApiOtherContainerService.connAnotherContainer());
//
//    }
}
