package com.teamwork.chatbot.entity;

import lombok.Data;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    private String lastName;
    private String firstName;
}
