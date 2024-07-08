package com.ohgiraffers.security.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/") // 기본 url
    public String root(){
        return "index";
    }

    @GetMapping("/admin/page") // 관리자 page
    public ModelAndView admin(ModelAndView mv){
        mv.setViewName("admin/page"); // admin폴더에 있는 page html 보여줌
        return mv;
    }

    @GetMapping("/user/page")
    public ModelAndView user(ModelAndView mv){
        mv.setViewName("user/page");
        return mv;
    }

}
