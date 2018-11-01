package com.github.motassemja.moauth.credentials;

import androidx.annotation.Nullable;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthCredentials {
    private String clientID;
    private String clientSecret;


    public MoAuthCredentials(String clientID, String clientSecret) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public MoAuthCredentials() {

    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof MoAuthCredentials
                && ((MoAuthCredentials) obj).clientID.equals(this.clientID)
                && ((MoAuthCredentials) obj).clientSecret.equals(this.clientSecret);
    }
}
