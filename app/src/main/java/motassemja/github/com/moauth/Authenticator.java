package motassemja.github.com.moauth;

import java.util.Calendar;

import motassemja.github.com.moauth.credentials.MoAuthCredentials;
import motassemja.github.com.moauth.exceptions.MoAuthExceptionManager;
import motassemja.github.com.moauth.exceptions.MoAuthExceptionReason;

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

    public Authenticator(MoAuthConfig config) {
        this.mConfig = config;
        mClient = new MoAuthClient(mConfig);
    }

    public void authenticateWithCredentials(final AuthenticationCallback callback) {
        mClient.requestOAuthToken(new MoAuthClient.MoAuthCallback() {
            @Override
            public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                if (e == null) {
                    callback.onAuthenticationCompleted(true, null);
                } else {
                    callback.onAuthenticationCompleted(false, e);
                }
            }
        });
    }

    public void authenticateWithUsername(String username, String password, final AuthenticationCallback callback) {
        mClient.requestOAuthTokenWithUsername(username, password, new MoAuthClient.MoAuthCallback() {
            @Override
            public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    callback.onAuthenticationCompleted(false, e);
                }
                else {
                    mTokenResult = tokenResult;
                    callback.onAuthenticationCompleted(true, e);
                }
            }
        });
    }

    public void invalidateAuthToken() {

    }

    public void signOut() {

    }

    public void retrieveAccessToken(final AccessTokenCallback callback) {
        MoAuthTokenResult validAccessToken = null;
        if (isAccessTokenValid()) {
            validAccessToken = mTokenResult;
            if (!validAccessToken.getAccessToken().isEmpty()) {
                callback.onAccessTokenReceived(validAccessToken.getAccessToken(), null);
            }
            else {
                MoAuthCredentials credentials = mClient.getCredentialsStore().loadCredentials();
                if (credentials != null) {
                    mClient.requestOAuthTokenWithRefreshToken(credentials.getRefreshToken(), new MoAuthClient.MoAuthCallback() {
                        @Override
                        public void onComplete(MoAuthTokenResult tokenResult, Exception e) {
                            if (e != null) {
                                callback.onAccessTokenReceived(null, e);
                            }
                            else {
                                callback.onAccessTokenReceived(tokenResult.getAccessToken(), null);
                            }
                        }
                    });
                }
                else {
                    callback.onAccessTokenReceived(null, new MoAuthExceptionManager("No credentials available",
                            MoAuthExceptionReason.REASON_UNAUTHORIZED_CLIENT));
                }
            }
        }
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
