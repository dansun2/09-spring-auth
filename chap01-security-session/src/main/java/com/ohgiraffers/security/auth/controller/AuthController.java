package com.ohgiraffers.security.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/fail") // 로그인 실패했을때
    public ModelAndView loginFail(@RequestParam String message, ModelAndView mv){
        mv.addObject("message", message);
        mv.setViewName("/auth/fail"); // templates 폴더/auth/fail 페이지 보여줌
        return mv;
    }
}
