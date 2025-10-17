package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//interface: Spring Data JPA will auto-implement at runtime.
//JpaRepository is a generic interface provided by Spring Data JPA.
//Extends: repository inherits a bunch of CRUD methods automatically
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuthProviderAndAuthSub(String authProvider, String authSub);
}
