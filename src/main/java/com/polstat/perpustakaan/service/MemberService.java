package com.polstat.perpustakaan.service;

import com.polstat.perpustakaan.dto.MemberDto;
import java.util.List;

public interface MemberService {
    List<MemberDto> getAllMembers();
    MemberDto getMemberById(Long id);
    MemberDto createMember(MemberDto memberDto);
    MemberDto updateMember(Long id, MemberDto memberDto);
    void deleteMember(Long id);

    // Penambahan baru
    List<MemberDto> getMembersByName(String name);
}