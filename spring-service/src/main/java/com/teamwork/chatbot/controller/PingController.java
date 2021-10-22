package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.entity.Ping;
import com.teamwork.chatbot.service.PingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PingController {

    private final PingService pingService;

    public PingController(PingService pingService) {this.pingService = pingService;}


    // call service
    @GetMapping("/ping")
    public ResponseEntity<Ping> ping(){
        return ResponseEntity.ok(pingService.readFirst());

    }

    // call module : phai chay docker len nhe
    @GetMapping("/pong")
    public ResponseEntity<String> pong(){
        return ResponseEntity.ok(pingService.connAnotherContainer());

    }
}
