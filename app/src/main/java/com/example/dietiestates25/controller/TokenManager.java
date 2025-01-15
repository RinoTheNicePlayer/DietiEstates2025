package com.example.dietiestates25.controller;

public class TokenManager {
    private static TokenManager instance;
    private String idToken;

    private String role;

    private TokenManager() {}

    public static synchronized TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String token) {
        this.idToken = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
