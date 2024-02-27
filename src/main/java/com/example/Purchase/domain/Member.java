package com.example.Purchase.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "member")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;

    String useremail;

    String pw;

    String phonenumber;

    Date Birthday;

    String username;

    String profileimage;

    int point;

    String nickname;
}
