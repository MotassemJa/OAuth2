package com.github.motassemja.moauth;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import com.github.motassemja.moauth.credentials.InMemoryCredentialsStore;
import com.github.motassemja.moauth.credentials.MoAuthCredentials;
import com.github.motassemja.moauth.credentials.MoAuthCredentialsStore;
import com.github.motassemja.moauth.credentials.MoAuthROPCCredentials;
import com.github.motassemja.moauth.credentials.MoAuthRefreshCredentials;
import com.github.motassemja.moauth.exceptions.MoAuthException;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthClient {


    public interface MoAuthCallback {
        void onComplete(MoAuthTokenResult tokenResult, Exception e);
    }

    private MoAuthConfig oAuthConfig;

    private MoAuthCredentialsStore mCredentialsStore;
    private Context mContext;

    public MoAuthClient(MoAuthConfig oAuthConfig, Context context) {
        this.oAuthConfig = oAuthConfig;
        this.mContext = context;
        if (oAuthConfig.getCredentialsStore() != null) {
            mCredentialsStore = oAuthConfig.getCredentialsStore();
        } else {
            mCredentialsStore = new InMemoryCredentialsStore();
        }
    }

    public MoAuthCredentialsStore getCredentialsStore() {
        return mCredentialsStore;
    }

    public void setCredentialsStore(MoAuthCredentialsStore mCredentialsStore) {
        this.mCredentialsStore = mCredentialsStore;
    }

    public void requestOAuthTokenWithCredentials(MoAuthCredentials credentials, MoAuthCallback callback) {
        if (credentials instanceof MoAuthRefreshCredentials) {
            requestOAuthTokenWithRefreshToken((MoAuthRefreshCredentials) credentials, callback);
        } else if (credentials instanceof MoAuthROPCCredentials) {
            requestOAuthTokenWithUsername((MoAuthROPCCredentials) credentials, callback);
        } else {
            requestOAuthToken(credentials, callback);
        }
    }

    /**
     * Request OAuthToken with grant_type = password
     *
     * @param credentials
     * @param callback
     */
    public void requestOAuthTokenWithUsername(MoAuthROPCCredentials credentials, MoAuthCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("grant_type", "password");
        formBuilder.add("username", credentials.getUsername());
        formBuilder.add("password", credentials.getPassword());
        requestOAuthTokenWithBody(credentials.getClientID(), credentials.getClientSecret(), formBuilder, callback);
    }

    /**
     * Request OAuthToken with gran_type = client_credentials
     *
     * @param credentials
     * @param callback
     */
    public void requestOAuthToken(MoAuthCredentials credentials, MoAuthCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("grant_type", "client_credentials");
        requestOAuthTokenWithBody(credentials.getClientID(), credentials.getClientSecret(), formBuilder, callback);
    }

    /**
     * Request OAuthToken with grant_type = refresh_token
     *
     * @param credentials
     * @param callback
     */
    public void requestOAuthTokenWithRefreshToken(MoAuthRefreshCredentials credentials, MoAuthCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("grant_type", "refresh_token");
        formBuilder.add("refresh_token", credentials.getRefreshToken());
        requestOAuthTokenWithBody(credentials.getClientID(), credentials.getClientSecret(), formBuilder, callback);
    }

    private void requestOAuthTokenWithBody(final String clientID, final String clientSecret, FormBody.Builder body, final MoAuthCallback callback) {

        try {
            parseScopes(body);

            Request.Builder requestBuilder = new Request.Builder();
            String authHeader = Credentials.basic(clientID, clientSecret);
            requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", authHeader);

            RequestBody requestBody = body.build();
            Request request = requestBuilder.url(oAuthConfig.getTokenUri().toURL())
                    .post(requestBody)
                    .build();

            new RequestTask(request, new RequestTask.OnTaskFinishedListener() {
                @Override
                public void onTaskSuccess(String body) {
                    try {
                        MoAuthTokenResult token = parseResponseData(clientID, clientSecret, body);
                        if (token != null) callback.onComplete(token, null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onTaskFailed(MoAuthException ex) {

                }
            }).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseScopes(FormBody.Builder body) {
        List<String> scopes = oAuthConfig.getScopes();
        if (!scopes.isEmpty()) {
            String scopeBody = "";
            for (String s : scopes) {
                scopeBody += s + ":";
            }
            body.add("scope", scopeBody);
        }
    }

    /**
     * Parse the response which represents the token
     *
     *
     * @param clientID
     * @param clientSecret
     *@param data String containing the json object  @return OAuthTokenResult
     * @throws JSONException
     */
    private MoAuthTokenResult parseResponseData(String clientID, String clientSecret, String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String accessToken = jsonObject.getString("access_token");
        String refreshToken = jsonObject.isNull("refresh_token") ? "" : jsonObject.getString("refresh_token");
        int expiresIn = jsonObject.getInt("expires_in");
        if (!refreshToken.isEmpty()) {
            mCredentialsStore.storeCredentials(new MoAuthRefreshCredentials(clientID, clientSecret, refreshToken), mContext);
        } else {
            mCredentialsStore.storeCredentials(new MoAuthCredentials(clientID, clientSecret), mContext);
        }

        return new MoAuthTokenResult(accessToken, refreshToken, expiresIn);
    }

    /**
     * Prepare the authorization header to add it to request body
     *
     * @return String representing the authorization header value
     */
    private String createAuthorizationHeader() {
        return okhttp3.Credentials.basic(oAuthConfig.getClientID(), oAuthConfig.getClientSecret());
    }
}
