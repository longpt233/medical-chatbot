package com.teamwork.chatbot.service.gateway;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.request.MedicalChatForm;
import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Slf4j
public class CallApiOtherContainerService {

    @Value("${medical-image.predict}")
    private String imageMedicalUrlBase;

    @Value("${chatbot.chat}")
    private String medicalChatbotUrlBase;

    @Value("${covid.info}")
    private String covidAPIUrl;

    @Autowired
    private AuthKeyCloakService authKeyCloakService;

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();

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
    public ResponseBuilder uploadImage(MultipartFile image, String accessToken){
        ResponseBuilder svResponse;
        //verify Token
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
            String url = imageMedicalUrlBase + "/upload";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("photo",image.getResource());
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(params,headers);
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, entity,byte[].class);
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("upload and detect image successfully!")
                    .buildData(response.getBody())
                    .build();
        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage("User is not verified!")
                    .buildData(keycloakResponse.getBody())
                    .build();
        }
        return svResponse;
    }

    public ResponseBuilder medicalChat(String accessToken, MedicalChatForm message){
        ResponseBuilder svResponse;
        //verify Token
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
            String url = medicalChatbotUrlBase + "/medical-chatbot/chat";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("content", message.getMessage());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
            ResponseEntity<Object> response = restTemplate.postForEntity(url,entity,Object.class);
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("chat success")
                    .buildData(response.getBody())
                    .build();
        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage("User is not verified!")
                    .buildData(keycloakResponse.getBody())
                    .build();
        }

        return svResponse;
    }

    public ResponseBuilder getCovidInfo(String accessToken){
        ResponseBuilder svResponse;
        //verify Token
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
            String covidInfoUrl = covidAPIUrl + "/covid-info";
            ResponseEntity<Object> response = restTemplate.getForEntity(covidInfoUrl, Object.class);
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("get covid info successfully!")
                    .buildData(response.getBody())
                    .build();
        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage("User is not verified!")
                    .buildData(keycloakResponse.getBody())
                    .build();
        }
        return svResponse;
    }

}
