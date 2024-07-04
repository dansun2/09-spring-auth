package com.ohgiraffers.security.user.service;

import com.ohgiraffers.security.user.model.dto.SignupDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private PasswordEncoder encoder;
    public Integer regist(SignupDTO signupDTO){
        signupDTO.setUserPass();
    }
}
