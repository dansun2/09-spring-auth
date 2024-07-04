package com.ohgiraffers.security.user.controller;


import com.ohgiraffers.security.user.model.dto.SignupDTO;
import com.ohgiraffers.security.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public void signup(){
    }

    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute SignupDTO signupDTO, ModelAndView mv){
        Integer result = userService.regist(signupDTO); // 회원가입요청이 들어오면
    }
}
