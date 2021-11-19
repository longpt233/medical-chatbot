package com.teamwork.chatbot.service.auth;

import com.google.gson.Gson;
import com.teamwork.chatbot.builder.ResponseBuilder;
import com.teamwork.chatbot.dto.response.TokenResponse;
import com.teamwork.chatbot.dto.response.UserProfile;
import com.teamwork.chatbot.entity.UserFullProfileMongo;
import com.teamwork.chatbot.entity.UserKeyCloak;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@Slf4j
public class AuthKeyCloakService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();

    @Value("${keycloak.resource}")
    private String keycloakClient;

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${is.keycloak.admin.user}")
    private String keycloakAdminUser;

    @Value("${is.keycloak.admin.password}")
    private String keycloakAdminPassword;

    @Value("${keycloak.credentials.secret}")
    private String keycloakClientSecret;

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                keycloakUrl,
                "master",
                keycloakAdminUser,
                keycloakAdminPassword,
                "admin-cli");
    }

    public Response signup(UserKeyCloak userKeyCloak) {

        // create credentials for UserRepresentation
        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType(CredentialRepresentation.PASSWORD);
        credentials.setValue(userKeyCloak.getPassword());
        credentials.setTemporary(false);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(userKeyCloak.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Arrays.asList(credentials));
        userRepresentation.setEnabled(true);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("description", Arrays.asList("Create a user"));
        userRepresentation.setAttributes(attributes);
        Keycloak keycloak = getKeycloakInstance();

        // call create
        Response result = keycloak.realm(keycloakRealm).users().create(userRepresentation);
        log.info("response: {}", result.getStatusInfo());

        // lay ra user do roi xet quyen user
        String userId = CreatedResponseUtil.getCreatedId(result);
        UserResource userResource = keycloak.realm(keycloakRealm).users().get(userId);
        RoleRepresentation userClientRole = keycloak.realm(keycloakRealm).roles().get("user-role").toRepresentation();
        userResource.roles().realmLevel().add(Arrays.asList(userClientRole));

        return result;
    }

    public TokenResponse login(String username, String password) {
        String loginKeyCloakUrl = keycloakUrl + "realms/"+keycloakRealm +"/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", keycloakClient);
        params.add("client_secret", keycloakClientSecret);
        params.add("username", username);
        params.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(loginKeyCloakUrl, entity, String.class);
        log.info("login response: {}", response.getBody());
        return gson.fromJson(response.getBody(), TokenResponse.class);
    }

    public TokenResponse refreshToken(String refreshToken) {
        String tokenKeyCloakUrl = keycloakUrl + "realms/"+keycloakRealm + "/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", keycloakClient);
        params.add("client_secret", keycloakClientSecret);
        params.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenKeyCloakUrl, entity, String.class);
        log.info("refresh token response: {}", response.getBody());
        return gson.fromJson(response.getBody(), TokenResponse.class);
    }

    public ResponseBuilder logout(String refreshToken) {
        String logoutUrl = keycloakUrl + "realms/"+keycloakRealm + "/protocol/openid-connect/logout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", keycloakClient);
        params.add("client_secret", keycloakClientSecret);
        params.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(logoutUrl, entity, String.class);
        ResponseBuilder svResponse;
        if (response.getStatusCode().value() == 204) {
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("logout successfully")
                    .buildData("")
                    .build();
        } else {
            svResponse = new ResponseBuilder.Builder(response.getStatusCodeValue())
                    .buildMessage("error when logout")
                    .buildData(response.getBody())
                    .build();
        }
        return svResponse;
    }



    public ResponseBuilder changePassword(String username, String oldPassword, String newPassword) {

        // TODO check old nhe @Tuan :D , ma k can auth a

        ResponseBuilder svResponse;
        Keycloak keycloak = getKeycloakInstance();
        Optional<UserRepresentation> user = keycloak.realm(keycloakRealm).users().search(username).stream().filter(
                u -> u.getUsername().equals(username)
        ).findFirst();
        if (user.isPresent()) {
            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(newPassword);
            credentials.setTemporary(false);
            UserRepresentation userRepresentation = user.get();
            UserResource userResource = keycloak.realm(keycloakRealm).users().get(userRepresentation.getId());
            userRepresentation.setCredentials(Arrays.asList(credentials));
            userResource.update(userRepresentation);
            svResponse = new ResponseBuilder.Builder(200)
                    .buildMessage("update password successfully")
                    .buildData("")
                    .build();
        } else {
            svResponse = new ResponseBuilder.Builder(400)
                    .buildMessage("username not found")
                    .buildData("")
                    .build();
        }
        return svResponse;
    }

    /**
     * lấy token call lên keycloak để lấy response
     * */
    public ResponseEntity<String> verifyUser(String accessToken){

        String userProfileUrl = keycloakUrl + "realms/"+keycloakRealm + "/protocol/openid-connect/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return  restTemplate.postForEntity(userProfileUrl, entity, String.class);

    }
}
