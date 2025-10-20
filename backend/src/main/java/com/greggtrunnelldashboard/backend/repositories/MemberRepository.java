package com.greggtrunnelldashboard.backend.repositories;

import com.greggtrunnelldashboard.backend.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

}
