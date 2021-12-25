package com.teamwork.chatbot.repository;

import com.teamwork.chatbot.entity.CovidOverviewEveryWeek;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CovidOverviewRepository extends MongoRepository<CovidOverviewEveryWeek, String> {
    CovidOverviewEveryWeek findCovidOverviewEveryWeeksByTime(String time);
}
