package com.github.motassemja.moauth;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthTokenResult {
    private String accessToken;
    private String refreshToken;
    private double expiresIn;
    private int timestamp;

    public MoAuthTokenResult(String accessToken, String refreshToken, int expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        timestamp = (int) (System.currentTimeMillis() / 1000);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public double getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(double expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
