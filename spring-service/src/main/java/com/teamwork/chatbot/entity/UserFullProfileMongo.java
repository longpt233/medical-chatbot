package com.teamwork.chatbot.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "account")
@Data
public class UserFullProfileMongo {
    @Id
    private String id;
    private String userKeycloakId;
    private String email;
    private String username;
    private String lastName;
    private String firstName;
    private String address;
}
