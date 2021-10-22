package com.teamwork.chatbot.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Ping {
    @Id
    private String id;
    private String name;
    private String color;
}
