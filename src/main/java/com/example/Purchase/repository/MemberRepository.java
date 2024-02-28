package com.example.Purchase.repository;

import com.example.Purchase.domain.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmail(String useremail);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Member SET point = point+ :totalamount WHERE memberid= :memberid")
    void updatememberpointbymemberid(@Param("totalamount") int totalamount, @Param("memberid") Long memberid);

}
