package com.teamwork.chatbot.service.gateway;

import com.teamwork.chatbot.builder.ResponseBuilder;
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

    @Autowired
    private AuthKeyCloakService authKeyCloakService;

    private final RestTemplate restTemplate = new RestTemplate();

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
                    .buildMessage("success")
                    .buildData(response.getBody())
                    .build();
        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage("keycloakResponse.getBody()")
                    .buildData("")
                    .build();
        }
        return svResponse;
    }

}
