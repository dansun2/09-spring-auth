package com.ohgiraffers.chap02securityjwt.auth.model;

import com.ohgiraffers.chap02securityjwt.user.model.entity.OhUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// UserDetails 상속받는 이유는 DB에서 가져온 유저정보의 타입을 강제화시키기 위하여?
public class DetailsUser implements UserDetails {
    private OhUser user;

    public DetailsUser() {
    }

    public DetailsUser(OhUser user) {
        this.user = user;
    }

    public OhUser getUser() {
        return user;
    }

    public void setUser(OhUser user) {
        this.user = user;
    }

    @Override
    public boolean isAccountNonExpired() { // 계정만료 여부를 확인
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠겨있는 계정 확인
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 탈퇴계정 여부(false면 해당계정 못씀)
        return true;
    }

    @Override
    public boolean isEnabled() { // 계정 비활성화 여부(false면 해당계정 못씀)
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(role -> authorities.add(()->role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPass();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }
}
