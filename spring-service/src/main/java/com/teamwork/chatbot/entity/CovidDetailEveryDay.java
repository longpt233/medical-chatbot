package com.teamwork.chatbot.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;

@Data
@Document(collection = "covid-details")
public class CovidDetailEveryDay {
    @Id
    private String id;
    private String time;
    private Object total;
    private Object today;
    private ProvinceCovidInfo[] locations;
}
