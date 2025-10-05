package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.MemberDto;
import com.polstat.perpustakaan.entity.Member;
import com.polstat.perpustakaan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<MemberDto> getAllMembers() {

        // Konversi Iterable<Member> menjadi List<Member>
        Iterable<Member> memberIterable = memberRepository.findAll();
        List<Member> members = new ArrayList<>();
        memberIterable.forEach(members::add);

        return members.stream().map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        return mapToDto(member);
    }

    @Override
    public MemberDto createMember(MemberDto memberDto) {
        Member member = mapToEntity(memberDto);
        Member savedMember = memberRepository.save(member);
        return mapToDto(savedMember);
    }

    @Override
    public MemberDto updateMember(Long id, MemberDto memberDto) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        existingMember.setMemberID(memberDto.getMemberID());
        existingMember.setName(memberDto.getName());
        existingMember.setAddress(memberDto.getAddress());
        existingMember.setPhoneNumber(memberDto.getPhoneNumber());

        Member updatedMember = memberRepository.save(existingMember);
        return mapToDto(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

    private MemberDto mapToDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .memberID(member.getMemberID())
                .name(member.getName())
                .address(member.getAddress())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }

    private Member mapToEntity(MemberDto memberDto) {
        return Member.builder()
                .id(memberDto.getId())
                .memberID(memberDto.getMemberID())
                .name(memberDto.getName())
                .address(memberDto.getAddress())
                .phoneNumber(memberDto.getPhoneNumber())
                .build();
    }
}