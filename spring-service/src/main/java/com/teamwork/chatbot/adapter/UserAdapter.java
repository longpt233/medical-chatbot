package com.teamwork.chatbot.adapter;

import com.teamwork.chatbot.entity.UserFullProfileMongo;
import com.teamwork.chatbot.entity.RegistrationUserInfo;
import org.springframework.stereotype.Component;

@Component("EntityAdapter")
public class UserAdapter implements EntityAdapter<RegistrationUserInfo, UserFullProfileMongo> {

    @Override
    public UserFullProfileMongo toMapper(RegistrationUserInfo entity) {
        UserFullProfileMongo user = new UserFullProfileMongo();
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setAddress(entity.getAddress());
        return user;
    }
}
