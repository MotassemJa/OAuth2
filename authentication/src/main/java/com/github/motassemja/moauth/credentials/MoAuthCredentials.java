package com.github.motassemja.moauth.credentials;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthCredentials {
    private String refreshToken;
    private String alias;

    public MoAuthCredentials(String refreshToken, String alias) {
        this.refreshToken = refreshToken;
        this.alias = alias;
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
}
