package com.ohgiraffers.chap02securityjwt.auth.handler;

import com.ohgiraffers.chap02securityjwt.auth.model.DetailsUser;
import com.ohgiraffers.chap02securityjwt.auth.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken loginToken = (UsernamePasswordAuthenticationToken) authentication;

        // 임시토큰에 담긴 id와 pass를 꺼내서
        String id = loginToken.getName();
        String pass = (String) loginToken.getCredentials();

        // 확인해보고 정상처리가 되면 null이 될 수 없음
        DetailsUser detailsUser = (DetailsUser) detailsService.loadUserByUsername(id);

        if (!passwordEncoder.matches(pass, detailsUser.getPassword())){ // matchers는 암호화 된 값이 동일하냐
            throw new BadCredentialsException("비밀번호가 달라유");
        }

        // 위에서 다 정상처리가 됐으면 유저 객체랑 비밀번호, 권한목록을 담아서 토큰을 다시 만들어줌
        return new UsernamePasswordAuthenticationToken(detailsUser, pass, detailsUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
