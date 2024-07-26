package com.ohgiraffers.chap02securityjwt.common.utils;

import com.ohgiraffers.chap02securityjwt.user.model.entity.OhUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {

    private static String jwtSecretKey;
    private static Long tokenValidateTime;

    @Value("${jwt.key}") // 리소스에 있는 값을 가져올때 yml에 있는거(컨트롤눌러서 확인해봐)
    public static void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public static void setTokenValidateTime(Long tokenValidateTime) {
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    // /* 말고 /** 이렇게 쓰면 함수형 주석
    /**
    * header의 token을 분리하는 메서드
    * @param header : Authrization의 header 값을 가져온다.
    * @return token : Authrization의 token 부분을 반환한다.
    * */
    public static String splitHeader(String header){
        if(!header.equals("")){
            return header.split(" ")[1];
        }else {
            return null;
        }
    }

    /**
     * 유효한 토큰인지 확인하는 메서드
     *
     * @param token : 토큰
     * @return boolean : 유효 여부
     * @throws io.jsonwebtoken.ExpiredJwtException,
    * */
    public static boolean isValidToken(String token){
        try{
            Claims claims = getClaimsFormToken(token);
            return true;
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch (JwtException e){
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 토큰을 복호화하는 메서드
     * @param token
     * @return Claims
     * */
    public static Claims getClaimsFormToken(String token){
        return Jwts.parser() // 토큰을 복호화하는 로직

                //DatatypeConverter는 외부 라이브러리고 jwtSecretKey는 솔트값
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody(); // 클레임에 담긴 바디부분만 확인함
    }

    /**
     * token을 생성하는 메서드
     * @param user - userEntity
     * @return String token
     * */
    // 토큰에 대한 정보를 생성하기 위한 클래스. 근데 얘가 static이라 하위 메서드들도 다 static이어야 함
    public static String generateJwtToken(OhUser user){
        // 현재 시간부터 tokenValidateTime의 시간까지 유효시간
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);

        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject("ohgiraffers token : " + user.getUserNo()) // 토큰의 이름을 정해줌
                .signWith(SignatureAlgorithm.HS256, createSignature()) // 알고리즘
                .setExpiration(expireTime);
        return builder.compact();
    }

    /**
     * token의 header를 설정하는 함수
     * @return Map<String, Object> header의 설정 정보
     * */
    private static Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt"); // jwt 타입으로 토큰을 만들었다고 표시
        header.put("alg", "HS256"); // 어떤 알고리즘을 썼는가
        header.put("data", System.currentTimeMillis()); // 언제 만들었는가
        return header; // 를 헤더에 담아줌
    }

    private static Map<String, Object> createClaims(OhUser user) {
        Map<String, Object> claims = new HashMap<>();

        // jwt의 바디(중요한 데이터는 여기 넣으면 안 됨)
        claims.put("userName", user.getUserName());
        claims.put("Role", user.getRole());
        claims.put("userEmail", user.getUserEmail());
        return claims;
    }

    private static Key createSignature(){
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
