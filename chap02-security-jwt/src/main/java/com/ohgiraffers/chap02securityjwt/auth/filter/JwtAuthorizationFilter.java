package com.ohgiraffers.chap02securityjwt.auth.filter;

import com.ohgiraffers.chap02securityjwt.auth.model.DetailsUser;
import com.ohgiraffers.chap02securityjwt.common.AuthConstants;
import com.ohgiraffers.chap02securityjwt.common.utils.TokenUtils;
import com.ohgiraffers.chap02securityjwt.user.model.entity.OhUser;
import com.ohgiraffers.chap02securityjwt.user.model.entity.OhgiraffersRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;
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

        String header = request.getHeader(AuthConstants.AUTH_HEADER);

        try {
            // 사용자가 요청을 할 시 헤더정보에 뭔가 있다고 하면 밸류를 꺼내옴
            if (header != null && !header.equalsIgnoreCase("")) {
                String token = TokenUtils.splitHeader(header);
                if (TokenUtils.isValidToken(token)) { // 토큰을 복호화 해보고 되면 아직 살아있는 토큰이라는거?
                    Claims claims = TokenUtils.getClaimsFormToken(token);

                    DetailsUser authentication = new DetailsUser();
                    OhUser user = new OhUser();
                    user.setUserId(claims.get("userId").toString());
                    user.setRole(OhgiraffersRole.valueOf(claims.get("Role").toString()));
                    authentication.setUser(user);

                    AbstractAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(authentication, token, authentication.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    chain.doFilter(request, response);
                } else {
                    throw new RuntimeException("토큰이 유효하지 않습니다.");
                }
            }else {
                throw new RuntimeException("토큰이 존재하지 않습니다.");
            }
        }catch (Exception e){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            JSONObject jsonObject = jsonresponseWrapper(e);
            printWriter.println(jsonObject);
            printWriter.flush();
            printWriter.close();
        }
    }
}