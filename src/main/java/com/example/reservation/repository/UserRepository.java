package com.example.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.reservation.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

	Optional<User> findByUsernameAndPassword(String username, String password);
}
