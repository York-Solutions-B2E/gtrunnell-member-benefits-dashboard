package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.entities.User;
import com.greggtrunnelldashboard.backend.repositories.MemberRepository;
import com.greggtrunnelldashboard.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    public User findOrCreateUserWithMember(String authProvider, String authSub, String email) {

        // Step 1: Find existing user
        return userRepository.findByAuthProviderAndAuthSub(authProvider, authSub)
                .orElseGet(() -> {
                    // Step 2: Create new user
                    User newUser = new User();
                    newUser.setAuthProvider(authProvider);
                    newUser.setAuthSub(authSub);
                    newUser.setEmail(email);
                    User savedUser = userRepository.save(newUser);

                    // Step 3: Seed default Member data
                    Member member = new Member();
                    member.setUser(savedUser);
                    member.setFirstName("Gregg");
                    member.setLastName("Trunnell");
                    member.setDateOfBirth(LocalDateTime.of(1990, 5, 12, 0, 0));
                    member.setEmail(email);
                    member.setPhone("555-1234");
                    member.setMailingAddress("123 Main St");

                    memberRepository.save(member);

                    return savedUser;
                });
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
