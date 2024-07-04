package com.ohgiraffers.security.config;

import com.ohgiraffers.security.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // bean으로 등록
@EnableWebSecurity // 얘가 시큐리티의 설정을 담고 있는 config야~ 라는 뜻
public class SecurityConfig {

    private final AuthFailHandler failHandler;

    @Autowired
    public SecurityConfig(AuthFailHandler failHandler) {
        this.failHandler = failHandler;
    }


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
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->{

           // 어떤 요청 리소스를 매칭시킬건지=> 밑에 있는 url들이 들어오면 권한을 다 주겠다.
           auth.requestMatchers("/auth/login","user/signup","/auth/fail","/").permitAll();

           // 뒤에 있는 권한을 가진 사용자만 앞에 있는 경로를 허용한다.
           auth.requestMatchers("/admin/*").hasAnyAuthority("admin role type으로 변환 예정");
           auth.requestMatchers("/user/*").hasAnyAuthority("usser role type으로 변환 예정");

           // 이 외에 모든 요청은 신경쓰지 않겠다.
           auth.anyRequest().authenticated();
        }).formLogin(login ->{
            login.loginPage("/auth/login"); // 이 루트에 post 요청을 보내면 기본 로그인 리소스는 이 url로 쓰겠다.
            login.usernameParameter("user"); // 사용자가 로그인을 보냈을때 니가 전달한 키값이 뭐야? (html엘리먼트의 name)
            login.passwordParameter("pass");
            login.defaultSuccessUrl("/", true); // 로그인 성공하면 어떤 url로 보낼거야? true 는 뭐지
            login.failureHandler(failHandler); // 로그인 실패했을때 failHandler가 동작됨
        }).logout(logout ->{
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            logout.deleteCookies("JSESSIONID"); // logout 요청이 들어오면 JSESSIONID를 지우겠다
            logout.invalidateHttpSession(true); // 세션을 소멸시키겠다. 강제로 만료처리
            logout.logoutSuccessUrl("/"); // 로그아웃 성공하면 어떤 url로 보낼거야?
        }).sessionManagement(session ->{ // 우리 서버에서 너 세션 관리를 어떻게 할거야?
            session.maximumSessions(1); // 이 계정으로 세션을 몇 개 만들수 있는지? 중복로그인 몇개까지?
            session.invalidSessionUrl("/"); // 세션이 만료되면 어디로 보낼건지 url

        }).csrf(csrf -> csrf.disable()); // 대리자공격??????? 내가 요청을 보낼 때?????? 무슨얘긴지 아예 모르겠음......

        return http.build();
    }
}
