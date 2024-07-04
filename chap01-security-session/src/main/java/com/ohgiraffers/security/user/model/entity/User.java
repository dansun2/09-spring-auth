package com.ohgiraffers.security.user.model.entity;

import com.ohgiraffers.security.user.model.dto.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @Column(name = "user_no")
    private int userNo;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


}
