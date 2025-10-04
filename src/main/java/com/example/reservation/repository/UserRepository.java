package com.example.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.reservation.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
