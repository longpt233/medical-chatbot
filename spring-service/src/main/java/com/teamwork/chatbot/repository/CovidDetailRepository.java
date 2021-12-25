package com.teamwork.chatbot.repository;

import com.teamwork.chatbot.entity.CovidDetailEveryDay;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Date;

public interface CovidDetailRepository extends MongoRepository<CovidDetailEveryDay, String> {
    CovidDetailEveryDay findCovidDetailEveryDayByTime(String time);
}
