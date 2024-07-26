package com.ohgiraffers.chap02securityjwt.user.controller;

import com.ohgiraffers.chap02securityjwt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

}
