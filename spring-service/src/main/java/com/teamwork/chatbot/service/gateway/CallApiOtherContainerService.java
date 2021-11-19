package com.teamwork.chatbot.service.gateway;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CallApiOtherContainerService {

    private String imageMedicalUrlBase = "http://medical-module:8083/";


    public String connAnotherContainer() {
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
