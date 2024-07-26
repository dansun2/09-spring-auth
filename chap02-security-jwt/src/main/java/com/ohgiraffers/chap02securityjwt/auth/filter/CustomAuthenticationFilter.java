package com.ohgiraffers.chap02securityjwt.auth.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohgiraffers.chap02securityjwt.auth.model.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

// UsernamePasswordAuthenticationFilter 사용ㅈㅏ가 로그인을 했을때 임시저장하고 비교하여 로그인성공이냐 실패냐를 하기위한것
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // 생성자에 매개변수로 추가함
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;

        try {
            authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this.getAuthenticationManager().authenticate(authRequest); // 인증된 사용자 객체를 반환
    }

    // 임시토큰을 발급하기 위한 메서드
    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // 넘어온 json객체의 구문을 해석하기 위함. auto클로즈는 자동으로 닫아줌. 왜 닫아줘?
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        LoginDTO user = objectMapper.readValue(request.getInputStream(), LoginDTO.class);
        
        // 문제없이 통과되면
        return new UsernamePasswordAuthenticationToken(user.getId(), user.getPass());
    }
}
