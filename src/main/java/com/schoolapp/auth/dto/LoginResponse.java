package com.schoolapp.auth.dto;

import java.util.List;

public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private List<String> roles;

    public LoginResponse(String accessToken, Long userId, String username, List<String> roles) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}