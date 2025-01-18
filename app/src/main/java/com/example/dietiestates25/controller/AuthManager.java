package com.example.dietiestates25.controller;

public class AuthManager {
    private static AuthManager instance;
    private String idToken;

    private String role;

    private AuthManager() {}

    public static synchronized AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
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
