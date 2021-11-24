package com.teamwork.chatbot.repository;

import com.teamwork.chatbot.entity.UserFullProfileMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserFullProfileMongo, String> {
    UserFullProfileMongo findUserFullProfileMongoByUserKeycloakId(String userKeycloakId);
}
