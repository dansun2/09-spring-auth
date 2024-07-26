package com.ohgiraffers.chap02securityjwt.auth.filter;

import com.ohgiraffers.chap02securityjwt.common.AuthConstants;
import com.ohgiraffers.chap02securityjwt.common.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// 요청시 기본필터
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 권한이 필요없는 요청목록들 설정
        List<String> rolelessList = Arrays.asList("/signup");

        // '/signup' 이걸 포함하고 있어? -> true면 끝내버리고 다음 메서드
        if(rolelessList.contains(request.getRequestURI())){ // 요청받은 uri가 rolelesslist에 포함되어있냐
            chain.doFilter(request,response);
            return;
        }

        // 베어
        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        // 사용자가 요청을 할 시 헤더정보에 뭔가 있다고 하면 밸류를 꺼내옴
        try {
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = TokenUtils.splitHeader(header);
            }
        }
    }
}
