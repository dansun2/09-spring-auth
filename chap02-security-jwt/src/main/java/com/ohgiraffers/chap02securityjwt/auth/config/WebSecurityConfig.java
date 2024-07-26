package com.ohgiraffers.chap02securityjwt.auth.config;

import com.ohgiraffers.chap02securityjwt.auth.filter.CustomAuthenticationFilter;
import com.ohgiraffers.chap02securityjwt.auth.handler.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // PreAuth어쩌구 쓰기 위해서 이 어노테이션 걸어줌
@EnableWebSecurity
public class WebSecurityConfig {

    /*
    * 정적 자원에 대한 인증된 사용자의 접근을 설정하는 메서드
    * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(){
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/login"); // url요청 들어오면 니가 동작해
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthSuccessHandler()); // 검증이 성공했을때 핸들러
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler); // 검증이 실패했을때 핸들러
        return customAuthenticationFilter;
    }

    @Bean
    public CustomAuthSuccessHandler customAuthSuccessHandler(){
        return new CustomAuthSuccessHandler();
    }
}
