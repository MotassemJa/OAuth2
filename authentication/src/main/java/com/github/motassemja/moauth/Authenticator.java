package com.github.motassemja.moauth;

import android.text.TextUtils;

import com.github.motassemja.moauth.credentials.MoAuthCredentials;
import com.github.motassemja.moauth.model.MoAuthTokenResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by moja on 12.06.2017.
 */

public class Authenticator {

    /**
     * Authentication Client
     */
    private MoAuthClient mClient;

    /**
     * Current token
     */
    private MoAuthTokenResult mTokenResult;

    /**
     * Constructor
     * @param config - Contains required information
     */
    public Authenticator(MoAuthConfig config) {
        mClient = MoAuthClient.getInstance(config.getTokenUrl(), new MoAuthCredentials(config.getClientID(), config.getClientSecret()));
    }

    /**
     * Fetch new token or get old one if still valid
     * @return Observable containing a valid token
     */
    public Observable<MoAuthTokenResult> authenticate() {
        if (!isAccessTokenValid()) {
            return mClient.authenticateWithClientCredentials()
                    .doOnNext(moAuthTokenResult -> mTokenResult = moAuthTokenResult);
        }
        return Observable.just(mTokenResult);
    }

    /**
     * Validates current token
     * @return - True if token still valid, false otherwise
     */
    private boolean isAccessTokenValid() {
        if (mTokenResult != null) {
            if (!TextUtils.isEmpty(mTokenResult.getAccessToken())) {
                int time = (int) (System.currentTimeMillis() / 1000);
                return time - mTokenResult.getTimestamp() < mTokenResult.getExpiresIn();
            }
        }
        return false;
    }
}
