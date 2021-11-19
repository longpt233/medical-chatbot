package com.teamwork.chatbot.service.user;

import com.google.gson.Gson;
import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.response.UserProfile;
import com.teamwork.chatbot.repository.UserRepository;
import com.teamwork.chatbot.service.auth.AuthKeyCloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public UserProfile getUserInfo(String accessToken) {

        ResponseEntity<String> response = authKeyCloakService.verifyUser(accessToken);

        // TODO : tu cai user tahng key cloak tra ve -> lay ra user trong trong mongo (du tren cai id j j day )
        // userRepository.getByID ??

        return gson.fromJson(response.getBody(), UserProfile.class);
    }

    public ResponseBuilder changeUserProfile(String username, String newFirstName, String newLastName) {

        // TODO : k can access Token a
        return null;
    }
}
