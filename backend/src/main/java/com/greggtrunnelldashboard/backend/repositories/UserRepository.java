package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

//Extends: repository inherits a bunch of CRUD methods automatically
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAuthProviderAndAuthSub(String authProvider, String authSub);
}
