package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import com.teamwork.chatbot.service.gateway.CallApiOtherContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway")
public class ApiGateWayController {

    @Autowired
    private CallApiOtherContainerService callApiOtherContainerService;

    @Autowired
    private AuthKeyCloakService authKeyCloakService;

    // call module : phai chay docker len nhe
    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestHeader("Authorization") String bearerToken) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseEntity<String> response = authKeyCloakService.verifyUser(accessToken);

        System.out.println(response);
        return ResponseEntity.ok("oki nha ");

    }

    // call module : phai chay docker len nhe
    @GetMapping("/medical-image/predict")
    public ResponseEntity<String> predict() {

        return ResponseEntity.ok(callApiOtherContainerService.connAnotherContainer());

    }

    // call module chatbot
    @GetMapping("/medical-chatbot/chat")
    public ResponseEntity<String> chat() {
        return ResponseEntity.ok(callApiOtherContainerService.connAnotherContainer());

    }
}
