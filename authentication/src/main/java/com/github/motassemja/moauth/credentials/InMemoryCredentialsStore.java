package com.github.motassemja.moauth.credentials;

import android.content.Context;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

/**
 * Created by moja on 12.06.2017.
 */

public class InMemoryCredentialsStore implements MoAuthCredentialsStore {

    @Override
    public void storeCredentials(MoAuthCredentials credentials, Context context) {
        SecurePreferences.setValue(credentials.getClientID(), credentials.getClientSecret(), context);
    }

    @Override
    public MoAuthCredentials loadCredentials(String clientId, Context context) {
        String clientSecret = SecurePreferences.getStringValue(clientId, context, "");
        assert clientSecret != null;
        if (!clientSecret.isEmpty()) {
            return new MoAuthCredentials(clientId, clientSecret);
        }
        return null;
    }
}
