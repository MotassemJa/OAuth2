package com.github.motassemja.moauth.interceptors;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018 MotassemJa@GitHub
 * All rights reserved.
 * Name: AuthorizationInterceptor
 * Autor: Motassem Jalal
 * Datum: 01.11.2018
 */
public class AuthorizationInterceptor implements Interceptor {
    /**
     * Basic authentication string
     */
    private String authorization;

    public AuthorizationInterceptor(String auth) {
        this.authorization = auth;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request authReq = chain.request()
                .newBuilder()
                .addHeader("Authorization", authorization)
                .build();

        return chain.proceed(authReq);
    }
}
