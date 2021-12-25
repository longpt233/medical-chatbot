package com.teamwork.chatbot.entity;

import lombok.Data;

@Data
public class CovidOverviewEveryDay {
    private String date;
    private int death;
    private int treating;
    private int cases;
    private int recovered;
    private int avgCases7day;
    private int avgRecovered7day;
    private int avgDeath7day;
}
