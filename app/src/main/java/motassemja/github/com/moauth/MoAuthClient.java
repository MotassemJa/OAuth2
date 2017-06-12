package motassemja.github.com.moauth;

import java.io.IOException;
import java.util.List;

import motassemja.github.com.moauth.credentials.InMemoryCredentialsStore;
import motassemja.github.com.moauth.credentials.MoAuthCredentialsStore;
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

    public MoAuthClient(MoAuthConfig oAuthConfig) {
        this.oAuthConfig = oAuthConfig;
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

    /**
     * Request OAuthToken with grant_type = password
     *
     * @param username
     * @param password
     * @param callback
     */
    public void requestOAuthTokenWithUsername(String username, String password, MoAuthCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("grant_type", "password");
        formBuilder.add("username", username);
        formBuilder.add("password", password);
        requestOAuthTokenWithBody(formBuilder, callback);
    }

    /**
     * Request OAuthToken with gran_type = client_credentials
     *
     * @param callback
     */
    public void requestOAuthToken(MoAuthCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("grant_type", "client_credentials");
        requestOAuthTokenWithBody(formBuilder, callback);
    }

    /**
     * Request OAuthToken with grant_type = refresh_token
     *
     * @param refreshToken
     * @param callback
     */
    public void requestOAuthTokenWithRefreshToken(String refreshToken, MoAuthCallback callback) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("grant_type", "refresh_token");
        formBuilder.add("refresh_token", refreshToken);
        requestOAuthTokenWithBody(formBuilder, callback);
    }

    private void requestOAuthTokenWithBody(FormBody.Builder body, final MoAuthCallback callback) {

        try {
            parseScopes(body);

            Request.Builder requestBuilder = new Request.Builder();
            String authHeader = createAuthorizationHeader();
            requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", authHeader);

            RequestBody requestBody = body.build();
            Request request = requestBuilder.url(oAuthConfig.getTokenUri().toURL()).post(requestBody).build();

            new RequestTask(request, new MoAuthCallback() {
                @Override
                public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                    if (e != null) {
                        callback.onComplete(null, e);
                    } else {
                        callback.onComplete(tokenResult, null);
                    }
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
     * Prepare the authorization header to add it to request body
     *
     * @return String representing the authorization header value
     */
    private String createAuthorizationHeader() {
        return okhttp3.Credentials.basic(oAuthConfig.getClientID(), oAuthConfig.getClientSecret());
    }
}
