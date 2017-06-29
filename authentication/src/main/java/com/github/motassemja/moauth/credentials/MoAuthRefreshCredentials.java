package com.github.motassemja.moauth.credentials;

/**
 * Copyright (c) 2017 T-Systems Multimedia Solutions GmbH
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 * Name: MoAuthRefreshCredentials
 * Autor: moja
 * Datum: 29.06.2017
 */

public class MoAuthRefreshCredentials extends MoAuthCredentials {
    private String refreshToken;

    public MoAuthRefreshCredentials(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public MoAuthRefreshCredentials(String clientId, String clientSecret, String refreshToken) {
        super(clientId, clientSecret);
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
