package com.teamwork.springsecuritylogin.repository;

import com.teamwork.springsecuritylogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
