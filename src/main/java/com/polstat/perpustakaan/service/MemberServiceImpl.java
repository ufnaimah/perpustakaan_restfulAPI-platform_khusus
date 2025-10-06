package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.MemberDto;
import com.polstat.perpustakaan.entity.Member;
// Tambahkan import untuk BorrowingRepository
import com.polstat.perpustakaan.repository.BorrowingRepository;
import com.polstat.perpustakaan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // --- TAMBAHKAN AUTOWIRED INI ---
    @Autowired
    private BorrowingRepository borrowingRepository;

    // ... (metode lainnya tetap sama) ...
    @Override
    public List<MemberDto> getAllMembers() {
        Iterable<Member> memberIterable = memberRepository.findAll();
        List<Member> members = new ArrayList<>();
        memberIterable.forEach(members::add);
        return members.stream().map(this::mapToDto).collect(Collectors.toList());
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

        if (memberDto.getMemberID() != null) {
            existingMember.setMemberID(memberDto.getMemberID());
        }
        if (memberDto.getName() != null) {
            existingMember.setName(memberDto.getName());
        }
        if (memberDto.getAddress() != null) {
            existingMember.setAddress(memberDto.getAddress());
        }
        if (memberDto.getPhoneNumber() != null) {
            existingMember.setPhoneNumber(memberDto.getPhoneNumber());
        }

        Member updatedMember = memberRepository.save(existingMember);
        return mapToDto(updatedMember);
    }


    // --- PERBAIKAN DI SINI ---
    @Override
    public void deleteMember(Long id) {
        // 1. Pastikan member ada
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found with id: " + id);
        }

        // 2. Periksa apakah member memiliki riwayat peminjaman
        if (borrowingRepository.existsByMemberId(id)) {
            throw new IllegalStateException("Cannot delete member: Member has existing borrowing records.");
        }

        // 3. Jika aman, hapus member
        memberRepository.deleteById(id);
    }

    @Override
    public List<MemberDto> getMembersByName(String name) {
        List<Member> members = memberRepository.findByNameContainingIgnoreCase(name);
        return members.stream().map(this::mapToDto).collect(Collectors.toList());
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