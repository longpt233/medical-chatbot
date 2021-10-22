package com.teamwork.chatbot.service;

import com.teamwork.chatbot.entity.Ping;
import com.teamwork.chatbot.repository.PingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PingService {

    private final PingRepository pingRepository;

    public Ping readFirst(){
        return pingRepository.findAll().get(0);
    }
}
