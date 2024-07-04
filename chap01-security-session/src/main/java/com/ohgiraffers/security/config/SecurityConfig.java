package com.ohgiraffers.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // bean으로 등록
@EnableWebSecurity // 얘가 시큐리티의 설정을 담고 있는 config야~ 라는 뜻
public class SecurityConfig {

    // 비밀번호 암호화

    /*
    * 비밀번호를 인코딩 하기 위한 bean
    * Bcrypt는 비밀번호 해싱에 가장 많이 사용되는 알고리즘 중 하나이다.
    * */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
//        return new BCryptPasswordEncoder(1); // 매개변수를 쓰면 이걸 검증할 때 1초의 시간을 두겠다. 해킹때문에 의도적으로 시간을 두는 것.
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){ // 이거는 니가 빡세게 관리하지 않아도 돼. 정적리소스는 제거해. 보안처리하지마
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    // 스프링시큐리티 필터 레이어를 내가 조작하겠다. 위에 시큐리티 커스터마이저도 여기 들어감
    public SecurityFilterChain configure(HttpSecurity http){
        http.authorizeHttpRequests(auth ->{

           // 어떤 요청 리소스를 매칭시킬건지=> 밑에 있는 url들이 들어오면 권한을 다 주겠다.
           auth.requestMatchers("/auth/login","user/signup","/auth/fail","/").permitAll();
        });
    }
}
