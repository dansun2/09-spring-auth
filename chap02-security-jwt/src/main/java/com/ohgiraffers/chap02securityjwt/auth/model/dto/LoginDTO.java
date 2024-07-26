package com.ohgiraffers.chap02securityjwt.auth.model.dto;

// 클라이언트가 전달할때의 데이터
public class LoginDTO {
    // 기본생성자는 자동으로 추가됨 (objectMapper가 자동으로 추가해줌)

    private String id;
    private String pass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "id='" + id + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
