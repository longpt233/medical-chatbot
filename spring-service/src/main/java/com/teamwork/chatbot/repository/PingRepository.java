package com.teamwork.chatbot.repository;

import com.teamwork.chatbot.entity.Ping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


@Component
public interface PingRepository extends MongoRepository<Ping,String> {
}
