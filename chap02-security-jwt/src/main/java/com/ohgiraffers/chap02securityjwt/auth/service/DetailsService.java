package com.ohgiraffers.chap02securityjwt.auth.service;

import com.ohgiraffers.chap02securityjwt.auth.model.DetailsUser;
import com.ohgiraffers.chap02securityjwt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public DetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    // 인증된 유저 객체를 전달하면서 여기서 username은 사용자id
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.equals("")){
            throw new AuthenticationServiceException(username + "is empty");
        }else {
            return userService.findUser(username)

                    //여기부턴 Optional에 대한 참조
                    .map(data -> new DetailsUser(data))
                    .orElseThrow(() -> new AuthenticationServiceException(username));
        }
    }
}
