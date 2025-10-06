
package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Import List

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    // Metode untuk memeriksa apakah ada record peminjaman yang terkait dengan memberId
    boolean existsByMemberId(Long memberId);
}