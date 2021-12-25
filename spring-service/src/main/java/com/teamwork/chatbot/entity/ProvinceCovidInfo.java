package com.teamwork.chatbot.entity;

import lombok.Data;

@Data
public class ProvinceCovidInfo {
    private String name;
    private int death;
    private int treating;
    private int cases;
    private int recovered;
    private int casesToday;
}
