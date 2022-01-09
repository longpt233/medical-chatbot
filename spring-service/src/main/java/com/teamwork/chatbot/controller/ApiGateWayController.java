package com.teamwork.chatbot.controller;

import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.request.MedicalChatForm;
import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import com.teamwork.chatbot.service.gateway.CallApiOtherContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;

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
    public Object predict(@RequestHeader("Authorization") String bearerToken,@RequestParam("photo") MultipartFile photo) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder svResponse = callApiOtherContainerService.uploadImage(photo,accessToken);
        if(svResponse.getCode() == 200){
            return svResponse.getData();
        }
        else return "Error when upload image: " + svResponse.getMessage();
    }

//    // call module chatbot
    @PostMapping("/medical-chatbot/chat")
    public ResponseEntity<Object> medicalChat(@RequestHeader("Authorization") String bearerToken,
                                              @RequestBody MedicalChatForm message) {
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder svResponse = callApiOtherContainerService.medicalChat(accessToken, message);
        return new ResponseEntity<>(svResponse, HttpStatus.valueOf(svResponse.getCode()));
    }

    @GetMapping("/covid-overview")
    public ResponseEntity<Object> getCovidOverview(@RequestHeader("Authorization") String bearerToken){
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder svResponse = callApiOtherContainerService.getCovidOverviewEveryWeek(accessToken);
        return new ResponseEntity<>(svResponse, HttpStatus.valueOf(svResponse.getCode()));
    }
    @GetMapping("/covid-detail")
    public ResponseEntity<Object> getCovidDetailInProvince(@RequestHeader("Authorization") String bearerToken){
        String accessToken = bearerToken.split(" ")[1];
        ResponseBuilder svResponse = callApiOtherContainerService.getCovidDetailInProvince(accessToken);
        return new ResponseEntity<>(svResponse, HttpStatus.valueOf(svResponse.getCode()));
    }
}
