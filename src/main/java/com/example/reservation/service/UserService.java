package com.example.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reservation.model.User;
import com.example.reservation.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        // Find user by username & password
        return  userRepository.findByUsernameAndPassword(username, password).isPresent();
    }
}
