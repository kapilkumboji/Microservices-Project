package com.example.AuthService.service;

import com.example.AuthService.entity.User;

import java.util.List;

public interface UserService {

    User register(User user);

    User login(String username, String password);

    List<User> getUsers();

    void deleteUser(Integer id);
}