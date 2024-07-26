package com.ohgiraffers.chap02securityjwt.common.utils;

import com.ohgiraffers.chap02securityjwt.user.model.entity.OhUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;
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
    public static String generateJwtToken(OhUser user){
        // 현재 시간부터 tokenValidateTime의 시간까지 유효시간
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);

        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
    }

    /**
     * token의 header를 설정하는 함수
     * @return Map<String, Object> header의 설정 정보
     * */
    private static Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt");
        header.put("alg", "HS256");
        header.put("data", System.currentTimeMillis());
        return header;
    }
}
