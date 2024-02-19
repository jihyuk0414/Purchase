package com.example.Purchase.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Product")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    //getter setter가 필요하띾?

    @Id
    String PID ;

    @Column(name = "price")
    long price;

    @Column(name = "Pname")
    String Pname;

    @Column(name = "amount")
    long amount;

}
