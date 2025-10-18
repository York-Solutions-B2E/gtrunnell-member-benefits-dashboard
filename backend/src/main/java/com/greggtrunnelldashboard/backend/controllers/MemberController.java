package com.greggtrunnelldashboard.backend.controllers;

import com.greggtrunnelldashboard.backend.entities.Member;
import com.greggtrunnelldashboard.backend.services.MemberService;
import com.greggtrunnelldashboard.backend.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:5173")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping
    public List<Member> findAll() {
        return memberService.getAllMembers();
    }
    @PostMapping
    public Member create(@RequestBody Member member) {
        return memberService.createMember(member);
    }

}