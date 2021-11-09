package com.teamwork.chatbot.repository;

import com.teamwork.chatbot.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Account, String> {

}
