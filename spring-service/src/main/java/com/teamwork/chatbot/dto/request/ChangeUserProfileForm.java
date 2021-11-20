package com.teamwork.chatbot.dto.request;

import lombok.Data;

@Data
public class ChangeUserProfileForm {
    private String firstName;
    private String lastName;
    private String address;
}
