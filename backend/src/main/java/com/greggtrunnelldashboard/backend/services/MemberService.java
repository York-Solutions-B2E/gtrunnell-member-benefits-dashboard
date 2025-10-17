package com.greggtrunnelldashboard.backend.services;

import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // Constructor-based dependency injection (Spring will wire it)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Get all members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Get one member by ID
    public Optional<Member> getMemberById(UUID id) {
        return memberRepository.findById(id);
    }

    // Create or save a member
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }
}