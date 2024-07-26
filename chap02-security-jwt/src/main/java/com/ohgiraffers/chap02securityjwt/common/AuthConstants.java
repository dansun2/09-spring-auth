package com.ohgiraffers.chap02securityjwt.common;

// jwt 관련 상수필드
public class AuthConstants {

    // 상수필드를 스태틱으로 쓰는 이유는 (이건 털려도 상관없음..기본값같은 느낌?)
    public static final String AUTH_HEADER = "Authorization"; // 토큰이 시작될 떄 헤더의 키값
    public static final String TOKEN_TYPE = "BEARER"; // 토큰이 시작될 떄 헤더의 밸류값
}
