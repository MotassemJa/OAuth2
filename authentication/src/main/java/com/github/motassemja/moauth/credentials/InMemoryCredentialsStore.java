package com.github.motassemja.moauth.credentials;

import android.content.Context;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

/**
 * Created by moja on 12.06.2017.
 */

public class InMemoryCredentialsStore implements MoAuthCredentialsStore {

    @Override
    public void storeCredentials(MoAuthCredentials credentials) {
        SecurePreferences.setValue(credentials.getClientID(), credentials.getClientSecret());
    }

    @Override
    public MoAuthCredentials loadCredentials(String clientId) {
        String clientSecret = SecurePreferences.getStringValue(clientId, "");
        assert clientSecret != null;
        if (!clientSecret.isEmpty()) {
            return new MoAuthCredentials(clientId, clientSecret);
        }
        return null;
    }
}
