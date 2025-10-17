package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.entities.User;
import com.greggtrunnelldashboard.backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController  {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }
}
