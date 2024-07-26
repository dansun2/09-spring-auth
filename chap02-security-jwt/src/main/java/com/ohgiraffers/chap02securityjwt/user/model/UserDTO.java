package com.ohgiraffers.chap02securityjwt.user.model;

import com.ohgiraffers.chap02securityjwt.user.model.entity.OhUser;
import com.ohgiraffers.chap02securityjwt.user.model.entity.OhgiraffersRole;

public class UserDTO {
    private String userId;
    private String userPass;
    private String userName;
    private OhgiraffersRole role;

    public UserDTO() {
    }

    public UserDTO(String userId, String userPass, String userName, OhgiraffersRole role) {
        this.userId = userId;
        this.userPass = userPass;
        this.userName = userName;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public OhgiraffersRole getRole() {
        return role;
    }

    public void setRole(OhgiraffersRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                '}';
    }
}
