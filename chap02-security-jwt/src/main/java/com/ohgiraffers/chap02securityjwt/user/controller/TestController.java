package com.ohgiraffers.chap02securityjwt.user.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // controller와 responsebody를 합친 역할
@PreAuthorize("hasAuthority('USER')")
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/test")
    public String test2(){
        return "post_test";
    }
}
