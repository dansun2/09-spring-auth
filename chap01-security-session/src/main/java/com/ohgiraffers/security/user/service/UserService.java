package com.ohgiraffers.security.user.service;

import com.ohgiraffers.security.user.dao.UserRepository;
import com.ohgiraffers.security.user.model.dto.SignupDTO;
import com.ohgiraffers.security.user.model.dto.UserRole;
import com.ohgiraffers.security.user.model.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public Integer regist(SignupDTO signupDTO){
        User user = userRepository.findByuserId(signupDTO.getUserId()); // 기존에 있는 회원인지 조회함

        if(!Objects.isNull(user)) { // user가 null이 아니면
            return null; // 가입못하게 해줄거니까 null 리턴
        }
        user = new User(); // 새로 가입할 수 있게 할당해줌
        user.setUserId(signupDTO.getUserId());
        user.setUserName(signupDTO.getUserName());
        user.setUserRole(UserRole.valueOf(signupDTO.getRole())); // 왜 이것만 valueof지?
        user.setPassword(encoder.encode(signupDTO.getUserPass())); // 암호화때문에 encoder

        User savedUser = userRepository.save(user);

        if(Objects.isNull(savedUser)) {
            return 0;
        }else {
            return 1;
        }
    }

    public User findByUserId(String username) {
        User user = userRepository.findByuserId(username);
        if(Objects.isNull(user)) {
            return null;
        }
        return user;
    }
}
