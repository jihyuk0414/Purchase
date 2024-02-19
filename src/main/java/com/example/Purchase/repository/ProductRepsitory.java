package com.example.Purchase.repository;

import com.example.Purchase.dto.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepsitory extends JpaRepository<Product, String> {


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Product SET amount = amount - 1 WHERE PID = :insertid")
    int minusOneAmount(@Param("insertid") String insertid);


}
