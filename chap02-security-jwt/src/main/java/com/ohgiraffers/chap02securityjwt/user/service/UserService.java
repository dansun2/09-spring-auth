package com.ohgiraffers.chap02securityjwt.user.service;

import com.ohgiraffers.chap02securityjwt.user.model.UserDTO;
import com.ohgiraffers.chap02securityjwt.user.model.entity.OhUser;
import com.ohgiraffers.chap02securityjwt.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO saveUser(UserDTO saveUserDTO){
        // 비즈니스 로직 추가
        OhUser ohUser = new OhUser();
        ohUser.setUserName(saveUserDTO.getUserName());
        ohUser.setUserId(saveUserDTO.getUserId());
        ohUser.setUserPass(saveUserDTO.getUserPass());

        OhUser saveUser = userRepository.save(ohUser);

        // 등록 완료 로직 추가
        saveUserDTO.setUserId(saveUser.getUserId());
        saveUserDTO.setUserPass(saveUser.getUserPass());
        saveUserDTO.setUserName(saveUser.getUserName());

        return saveUserDTO;
    }
}
