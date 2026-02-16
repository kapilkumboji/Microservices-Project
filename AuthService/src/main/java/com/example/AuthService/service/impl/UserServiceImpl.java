package com.example.AuthService.service.impl;

import com.example.AuthService.entity.Role;
import com.example.AuthService.entity.User;
import com.example.AuthService.repository.UserRepository;
import com.example.AuthService.security.JwtUtil;
import com.example.AuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;

    private String encode(String pass) {
        return Base64.getEncoder()
                .encodeToString(pass.getBytes());
    }

    @Override
    public
    User register(User user) {

        if (repo.existsByUsername(user.getUsername()))
            throw new RuntimeException("User exists");

        user.setPassword(encode(user.getPassword()));

        if (user.getRole() == null)
            user.setRole(Role.USER);

        return repo.save(user);
    }

    @Override
    public User login(String username, String password) {

        User user = repo.findByUsername(username);

        if (user == null ||
                !user.getPassword().equals(encode(password)))
            throw new RuntimeException("Invalid login");

        String token = jwtUtil.generateToken(
                username,
                user.getRole().name());

        user.setPassword(null);
        user.setToken(token);

        return user;
    }

    @Override
    public List<User> getUsers() {
        return repo.findAll();
    }

    @Override
    public void deleteUser(Integer id) {
        repo.deleteById(id);
    }
}