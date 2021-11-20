package com.teamwork.chatbot.service.user;

import com.google.gson.Gson;
import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.request.ChangeUserProfileForm;
import com.teamwork.chatbot.dto.response.UserProfile;
import com.teamwork.chatbot.entity.UserFullProfileMongo;
import com.teamwork.chatbot.repository.UserRepository;
import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthKeyCloakService authKeyCloakService;

    private final Gson gson = new Gson();

    public ResponseBuilder getUserInfo(String accessToken) {
        ResponseBuilder svResponse;
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
            UserProfile userProfile = gson.fromJson(keycloakResponse.getBody(), UserProfile.class);

            //get user from mongodb with user keycloak id
            UserFullProfileMongo userFullProfileMongo = userRepository.findUserFullProfileMongoByUserKeycloakId(userProfile.getSub());
            if(userFullProfileMongo != null){
                svResponse = new ResponseBuilder.Builder(HttpStatus.OK.value())
                        .buildMessage("get user information successfully!")
                        .buildData(userFullProfileMongo)
                        .build();
            }else{
                svResponse = new ResponseBuilder.Builder(HttpStatus.NOT_FOUND.value())
                        .buildMessage("user not found")
                        .buildData("")
                        .build();
            }

        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage(keycloakResponse.getBody())
                    .buildData("")
                    .build();
        }

        return svResponse;
    }

    public ResponseBuilder changeUserProfile(ChangeUserProfileForm userProfileForm, String accessToken) {
        ResponseBuilder svResponse;
        //verify Token
        ResponseEntity<String> keycloakResponse = authKeyCloakService.verifyUser(accessToken);
        int serverResponseCode = keycloakResponse.getStatusCodeValue();
        if(serverResponseCode == 200){
            UserProfile userProfile = gson.fromJson(keycloakResponse.getBody(), UserProfile.class);

            //get user from mongodb with user keycloak id
            UserFullProfileMongo userMongo = userRepository.findUserFullProfileMongoByUserKeycloakId(userProfile.getSub());
            if(userMongo != null){
                userMongo.setAddress(userProfileForm.getAddress());
                userMongo.setFirstName(userProfileForm.getFirstName());
                userMongo.setLastName(userProfileForm.getLastName());
                userRepository.save(userMongo);
                svResponse = new ResponseBuilder.Builder(HttpStatus.OK.value())
                        .buildMessage("update user info successfully")
                        .buildData("")
                        .build();
            }else{
                svResponse = new ResponseBuilder.Builder(HttpStatus.NOT_FOUND.value())
                        .buildMessage("user not found")
                        .buildData("")
                        .build();
            }

        }else{
            svResponse = new ResponseBuilder.Builder(serverResponseCode)
                    .buildMessage(keycloakResponse.getBody())
                    .buildData("")
                    .build();
        }
        return svResponse;
    }
}
