package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.SeedData.SeedData;
import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.entities.User;
import com.greggtrunnelldashboard.backend.repositories.MemberRepository;
import com.greggtrunnelldashboard.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final SeedData seedData;

    public Member getOrCreateMemberFromJwt(Jwt jwt) {
        String email = jwt.getClaim("email");
        String name = jwt.getClaim("name");
        String sub = jwt.getClaim("sub");
        String providerId = jwt.getClaimAsString("iss");

        User user = userRepository.findByAuthProviderAndAuthSub(providerId, sub)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAuthProvider(providerId != null ? providerId : "google");
                    newUser.setAuthSub(sub);
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });

        return memberRepository.findByUser(user)
                .orElseGet(() -> seedData.createMember(user, name));
    }
}
