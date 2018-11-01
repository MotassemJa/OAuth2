/*
 * Copyright (c) MotassemJa@GitHub
 * All rights reserved.
 *
 * Name: OAuthClient
 * Author: Motassem Jalal
 * Date: 01.11.2018
 */
package com.github.motassemja.moauth;


import android.os.NetworkOnMainThreadException;

import com.github.motassemja.moauth.api.AuthenticationAPI;
import com.github.motassemja.moauth.credentials.MoAuthCredentials;
import com.github.motassemja.moauth.interceptors.AuthorizationInterceptor;
import com.github.motassemja.moauth.interceptors.ContentTypeInterceptor;
import com.github.motassemja.moauth.model.MoAuthTokenResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoAuthClient {

    /**
     * Authentication Server URL
     */
    private String baseUrl;

    /**
     * Singleton instance
     */
    private static MoAuthClient INSTANCE = null;

    /**
     * Service
     */
    private AuthenticationAPI authApi;

    /**
     * To be authenticated
     */
    private MoAuthCredentials clientCredentials;

    /**
     * Private Constructor
     *
     * @param url         - Base URL
     * @param credentials - ID & Secret
     */
    private MoAuthClient(String url, MoAuthCredentials credentials) {
        // Avoid Bad URL Exception
        if (!url.endsWith("/")) url += "/";
        this.baseUrl = url;

        this.clientCredentials = credentials;

        final Gson gson = new GsonBuilder().create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ContentTypeInterceptor("application/x-www-form-urlencoded"))
                .addInterceptor(new AuthorizationInterceptor(
                        Credentials.basic(clientCredentials.getClientID(), clientCredentials.getClientSecret()))
                )
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        authApi = retrofit.create(AuthenticationAPI.class);
    }

    /**
     * Singleton
     *
     * @param url         - Token URL
     * @param credentials - ID & Secret
     * @return - Instance of Client
     */
    public static MoAuthClient getInstance(String url, MoAuthCredentials credentials) {
        if (INSTANCE == null || !INSTANCE.baseUrl.equals(url) || !INSTANCE.clientCredentials.equals(credentials)) {
            INSTANCE = new MoAuthClient(url, credentials);
        }
        return INSTANCE;
    }

    /**
     * Authenticate with Client Credentials grant type
     * Will run on a separate thread from Thread Pool
     *
     * @return Observable containing {@link MoAuthTokenResult}
     */
    public Observable<MoAuthTokenResult> authenticateWithClientCredentials() {
        return authApi.authenticate("client_credentials")
                .subscribeOn(Schedulers.io());
    }

    /**
     * Fetch an access token
     *
     * @return Response with Access Token
     * @throws IOException
     * @throws NetworkOnMainThreadException This method will cause app to crash in case it was used in main thread.
     */
    public retrofit2.Response<MoAuthTokenResult> authenticateOldSchool() throws IOException, NetworkOnMainThreadException {
        return authApi.authenticateOldSchool("client_credentials").execute();
    }
}
