package com.example.Purchase.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "point")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    @Column(name = "point_id")
    private int pointid;

    @Column(name = "point_name")
    private String pointname;

    @Column(name = "point_price")
    private int pointprice;

    @Column(name = "point_amount")
    private int pointamount;

}
