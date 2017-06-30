package com.github.motassemja.moauth;

import android.content.Context;

import com.github.motassemja.moauth.credentials.MoAuthCredentials;
import com.github.motassemja.moauth.credentials.MoAuthROPCCredentials;
import com.github.motassemja.moauth.exceptions.MoAuthExceptionManager;
import com.github.motassemja.moauth.exceptions.MoAuthExceptionReason;

import java.util.Calendar;

/**
 * Created by moja on 12.06.2017.
 */

public class Authenticator {
    public interface AuthenticationCallback {
        void onAuthenticationCompleted(boolean flag, Exception e);
    }

    public interface AccessTokenCallback {
        void onAccessTokenReceived(String s, Exception e);
    }

    private MoAuthConfig mConfig;
    private MoAuthClient mClient;
    private static MoAuthTokenResult mTokenResult;
    private Context mContext;
    private String mTenantName;

    public Authenticator(MoAuthConfig config, Context context, String tenantName) {
        this.mConfig = config;
        mClient = new MoAuthClient(mConfig, context);
        this.mContext = context;
        this.mTenantName = tenantName;
    }

    public void authenticateWithCredentials(final AuthenticationCallback callback) {
        mClient.requestOAuthTokenWithCredentials(new MoAuthCredentials(mConfig.getClientID(), mConfig.getClientSecret()), new MoAuthClient.MoAuthCallback() {
            @Override
            public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    callback.onAuthenticationCompleted(false, e);
                }
                else {
                    mTokenResult = tokenResult;
                    callback.onAuthenticationCompleted(true, null);
                }
            }
        });
    }

    public void authenticateWithUsername(final String username, final String password, final AuthenticationCallback callback) {
        MoAuthROPCCredentials credentials = new MoAuthROPCCredentials(mConfig.getClientID(), mConfig.getClientSecret(), username, password);
        mClient.requestOAuthTokenWithUsername(credentials, new MoAuthClient.MoAuthCallback() {
            @Override
            public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    callback.onAuthenticationCompleted(false, e);
                } else {
                    mTokenResult = tokenResult;
                    callback.onAuthenticationCompleted(true, null);
                }
            }
        });
    }

    public void invalidateAuthToken() {

    }

    public void signOut() {

    }

    /**
     * Get the access token by refresh token.
     *
     * @param callback
     */
    public void retrieveAccessToken(final AccessTokenCallback callback) {
        MoAuthTokenResult validAccessToken = null;
        if (isAccessTokenValid()) {
            validAccessToken = mTokenResult;
            if (!validAccessToken.getAccessToken().isEmpty()) { // Token is valid and still available, just return it.
                callback.onAccessTokenReceived(validAccessToken.getAccessToken(), null);
            } else {
                MoAuthCredentials credentials = mClient.getCredentialsStore().loadCredentials(mConfig.getClientID(), mContext);
                if (credentials != null) {
                    mClient.requestOAuthTokenWithCredentials(credentials, new MoAuthClient.MoAuthCallback() {
                        @Override
                        public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                            if (e != null) callback.onAccessTokenReceived(null, e);
                            else callback.onAccessTokenReceived(tokenResult.getAccessToken(), null);
                        }
                    });
                } else {
                    callback.onAccessTokenReceived(null, new MoAuthExceptionManager("No credentials available",
                            MoAuthExceptionReason.REASON_UNAUTHORIZED_CLIENT));
                }
            }
        }
        callback.onAccessTokenReceived(null, new MoAuthExceptionManager("Access token invalid"
                , MoAuthExceptionReason.REASON_NOT_AUTHENTICATED));
    }

    private boolean isAccessTokenValid() {
        if (mTokenResult != null) {
            if (!mTokenResult.getAccessToken().isEmpty()) {
                Calendar c = Calendar.getInstance();
                int currentSeconds = c.get(Calendar.SECOND);
                int time = (int) (System.currentTimeMillis() / 1000);
                if (time - mTokenResult.getTimestamp() < mTokenResult.getExpiresIn()) {
                    return true;
                }
            }
        }
        return false;
    }
}
