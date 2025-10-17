package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.entities.User;
import com.greggtrunnelldashboard.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") // Adjust if needed
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // POST → Create a new user manually (e.g., in Postman)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.create(user);
        return ResponseEntity.ok(createdUser);
    }

    // GET → List all users
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAll();
//    }

}
