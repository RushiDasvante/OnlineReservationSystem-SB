package com.example.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reservation.model.User;
import com.example.reservation.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public boolean authenticate(String username, String password) {
        User user = userRepo.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
