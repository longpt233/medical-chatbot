package com.teamwork.chatbot.entity;

import lombok.Data;

@Data
public class RegistrationUserInfo {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
}
