package com.ohgiraffers.chap02securityjwt.auth.interceptor;

import com.ohgiraffers.chap02securityjwt.common.AuthConstants;
import com.ohgiraffers.chap02securityjwt.common.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.rmi.RemoteException;

public class JwtTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String header = request.getHeader(AuthConstants.AUTH_HEADER);
        String token = TokenUtils.splitHeader(header);

        if (token != null){
            if (TokenUtils.isValidToken(token)){ // TokenUtils에 token을 집어넣고
                return true; // true면 다음 요청을 처리하도록 con에 넘겨줌. false면 끊어버리고 사용자 요청은 손실됨
            }else {
                throw new RemoteException("token이 만료되었습니다.");
            }
        }else {
            throw new RemoteException("token 정보가 없습니다."); // 사용자 요청이 아예 손실되었기 때문.
        }
    }
}
