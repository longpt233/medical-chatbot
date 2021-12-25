package com.teamwork.chatbot.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "covid-overview")
public class CovidOverviewEveryWeek {
    @Id
    private String id;
    private CovidOverviewEveryDay[] overview;
    private String time;
}
