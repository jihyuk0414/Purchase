package com.example.Purchase.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Point")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {

    @Id
    private int pointid;

    private String pointname;

    private int pointprice;

    private int pointamount;

}
