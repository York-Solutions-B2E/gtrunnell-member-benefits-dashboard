package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.entities.User;
import com.greggtrunnelldashboard.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public User create(User user) {
        return userRepository.save(user);
    }
    public Optional<User> getByProviderAndSub(String provider, String sub){
        return userRepository.findByAuthProviderAndAuthSub(provider, sub);
    }
}
