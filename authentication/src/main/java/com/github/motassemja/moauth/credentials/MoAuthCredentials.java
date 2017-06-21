package com.github.motassemja.moauth.credentials;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthCredentials {
    private String refreshToken;
    private String clientID;
    private String clientSecret;
    private String username;
    private String password;
    private String alias;

    public MoAuthCredentials(String refreshToken, String alias) {
        this.refreshToken = refreshToken;
        this.alias = alias;
    }

    public MoAuthCredentials() {

    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
