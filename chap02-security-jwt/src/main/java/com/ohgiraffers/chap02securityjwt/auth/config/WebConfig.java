package com.ohgiraffers.chap02securityjwt.auth.config;

import com.ohgiraffers.chap02securityjwt.auth.filter.HeaderFilter;
import com.ohgiraffers.chap02securityjwt.auth.interceptor.JwtTokenInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // SpringMVC 기능을 활성화. @Configuration 클래스에서 사용됨.
public class WebConfig implements WebMvcConfigurer {

    // 서버의 정적 자원에 요청할 수 있게끔 security에서 허용하도록 설정함.
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/static/", "classpath:/public/",
            "classpath:/", "classpath:/resources/", "classpath:/META-INF/resources/",
            "classpath:/META-INF/resources/webjars/"};

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS); //모든 요청에 대해
    }

    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean(){
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<HeaderFilter>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE); // bean의 등록순서. 0번째로 등록하겠다
        registrationBean.addUrlPatterns("/*"); // 모든 요청에 대해 다 처리하겠다
        return registrationBean;
    }

    @Bean
    public HeaderFilter createHeaderFilter(){
        return new HeaderFilter();
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor(){
        return new JwtTokenInterceptor();
    }
}
