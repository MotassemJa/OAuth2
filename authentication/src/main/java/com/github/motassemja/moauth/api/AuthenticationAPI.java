package com.github.motassemja.moauth.api;


import com.github.motassemja.moauth.model.MoAuthTokenResult;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Copyright (c) 2018 MotassemJa@GitHub
 * All rights reserved.
 * <p>
 * Name: AuthenticationAPI
 * Autor: Motassem Jalal
 * Datum: 01.11.2018
 */
public interface AuthenticationAPI {

    @FormUrlEncoded
    @POST("oauth/token")
    Observable<MoAuthTokenResult> authenticate(@Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("oauth/token")
    Call<MoAuthTokenResult> authenticateOldSchool(@Field("grant_type") String grantType);
}
