package com.github.motassemja.moauth.interceptors;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2018 MotassemJa@GitHub
 * All rights reserved.
 * Name: ContentTypeInterceptor
 * Autor: Motassem Jalal
 * Datum: 01.11.2018
 */
public class ContentTypeInterceptor implements Interceptor {

    /**
     * Content Type
     */
    private String contentType;

    public ContentTypeInterceptor(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request contentTypeReq = chain.request()
                .newBuilder()
                .addHeader("Content-Type", contentType)
                .build();

        return chain.proceed(contentTypeReq);
    }
}
