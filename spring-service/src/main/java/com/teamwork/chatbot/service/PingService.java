package com.teamwork.chatbot.service;

import com.teamwork.chatbot.entity.Ping;
import com.teamwork.chatbot.repository.PingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PingService {

    private String imageMedicalUrlBase = "http://medical-module:8083/";

    private final PingRepository pingRepository;

    public Ping readFirst(){

        return pingRepository.findAll().get(0);
    }

    public String connAnotherContainer(){
        try {
            Document doc = Jsoup.connect(imageMedicalUrlBase)
                    .ignoreContentType(true)
                    .timeout(20 * 1000)
                    .get();

            return doc.text();
        } catch (Exception e) {
            return "-1";
        }

    }
}
