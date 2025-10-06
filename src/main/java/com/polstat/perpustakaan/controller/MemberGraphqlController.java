package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.dto.MemberDto;
import com.polstat.perpustakaan.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class MemberGraphqlController {

    @Autowired
    private MemberService memberService;

    @QueryMapping
    public List<MemberDto> members() {
        return memberService.getAllMembers();
    }

    @QueryMapping
    public MemberDto memberById(@Argument Long id) {
        return memberService.getMemberById(id);
    }

    // Resolver baru untuk pencarian berdasarkan nama
    @QueryMapping
    public List<MemberDto> membersByName(@Argument String name) {
        return memberService.getMembersByName(name);
    }

    @MutationMapping
    public MemberDto createMember(@Argument String memberID, @Argument String name, @Argument String address, @Argument String phoneNumber) {
        MemberDto memberDto = MemberDto.builder()
                .memberID(memberID)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
        return memberService.createMember(memberDto);
    }

    @MutationMapping
    public MemberDto updateMember(@Argument Long id, @Argument String memberID, @Argument String name, @Argument String address, @Argument String phoneNumber) {
        MemberDto memberDto = MemberDto.builder()
                .memberID(memberID)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
        return memberService.updateMember(id, memberDto);
    }

    @MutationMapping
    public boolean deleteMember(@Argument Long id) {
        memberService.deleteMember(id);
        return true;
    }
}