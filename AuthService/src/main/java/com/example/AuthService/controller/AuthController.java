package com.example.AuthService.controller;

import com.example.AuthService.entity.User;
import com.example.AuthService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return service.login(
                user.getUsername(),
                user.getPassword());
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Integer id) {
        service.deleteUser(id);
        return "Deleted";
    }
}