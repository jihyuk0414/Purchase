package com.example.Purchase.repository;

import com.example.Purchase.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByUseremail(String useremail);
}
