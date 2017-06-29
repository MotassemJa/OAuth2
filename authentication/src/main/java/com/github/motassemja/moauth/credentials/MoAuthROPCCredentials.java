package com.github.motassemja.moauth.credentials;

/**
 * Copyright (c) 2017 T-Systems Multimedia Solutions GmbH
 * Riesaer Str. 5, D-01129 Dresden, Germany
 * All rights reserved.
 * Name: MoAuthROPCCredentials
 * Autor: moja
 * Datum: 29.06.2017
 */

public class MoAuthROPCCredentials extends MoAuthCredentials {
    private String username;
    private String password;

    public MoAuthROPCCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public MoAuthROPCCredentials(String clientId, String clientSecret, String username, String password) {
        super(clientId, clientSecret);
        this.username = username;
        this.password = password;
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
