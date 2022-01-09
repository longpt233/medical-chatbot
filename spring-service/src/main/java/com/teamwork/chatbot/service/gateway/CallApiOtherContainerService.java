package com.teamwork.chatbot.service.gateway;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.request.MedicalChatForm;
import com.teamwork.chatbot.entity.CovidDetailEveryDay;
import com.teamwork.chatbot.entity.CovidOverviewEveryWeek;
import com.teamwork.chatbot.entity.ProvinceCovidInfo;
import com.teamwork.chatbot.repository.CovidDetailRepository;
import com.teamwork.chatbot.repository.CovidOverviewRepository;
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

import java.sql.Date;


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

    @Autowired
    private CovidOverviewRepository covidOverviewRepository;

    @Autowired
    private CovidDetailRepository covidDetailRepository;

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

    public ResponseBuilder getCovidOverviewEveryWeek(String accessToken){
        ResponseBuilder svResponse;
        //verify Token
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
//            CovidOverviewEveryWeek overview = covidOverviewRepository.findCovidOverviewEveryWeeksByTime(time);
//            if(overview != null){
//                svResponse = new ResponseBuilder.Builder(200)
//                        .buildMessage("Get covid overview in this time successfully!")
//                        .buildData(overview)
//                        .build();
//            }else{
//                svResponse = new ResponseBuilder.Builder(404)
//                        .buildMessage("Can not get data overview in this time!")
//                        .buildData("")
//                        .build();
//            }
            String covidInfoUrl = covidAPIUrl + "/covid-overview";
            ResponseEntity<String> response = restTemplate.getForEntity(covidInfoUrl, String.class);
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("get covid overview successfully!")
                    .buildData(gson.fromJson(getDataFromJson(response.getBody()),Object.class))
                    .build();
        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage("User is not verified!")
                    .buildData(keycloakResponse.getBody())
                    .build();
        }
        return svResponse;
    }

    public ResponseBuilder getCovidDetailInProvince(String accessToken){
        ResponseBuilder svResponse;
        //verify Token
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
//            CovidDetailEveryDay detailEveryDay = covidDetailRepository.findCovidDetailEveryDayByTime(time);
//            if(detailEveryDay != null){
//                ProvinceCovidInfo[] detailProvinces = detailEveryDay.getLocations();
//                ProvinceCovidInfo rs = new ProvinceCovidInfo();
//                for(ProvinceCovidInfo p: detailProvinces){
//                    if(p.getName().equals(provinceName)){
//                        rs = p;
//                    }
//                }
//                svResponse = new ResponseBuilder.Builder(200)
//                        .buildMessage("Get covid detail in "  +provinceName+ " in this time successfully!")
//                        .buildData(rs)
//                        .build();
//            }else{
//                svResponse = new ResponseBuilder.Builder(404)
//                        .buildMessage("Can not get data detail in this time!")
//                        .buildData("")
//                        .build();
//            }
            String covidInfoUrl = covidAPIUrl + "/covid-detail";
            ResponseEntity<String> response = restTemplate.getForEntity(covidInfoUrl, String.class);
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("get covid info successfully!")
                    .buildData(gson.fromJson(getDataFromJson(response.getBody()),Object.class))
                    .build();
        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage("User is not verified!")
                    .buildData(keycloakResponse.getBody())
                    .build();
        }
        return svResponse;
    }
    public String getDataFromJson(String jsonData){
        JsonParser jsonParser = new JsonParser();
        return String.valueOf(jsonParser.parse(jsonData).getAsJsonObject().get("data"));
    }
}
