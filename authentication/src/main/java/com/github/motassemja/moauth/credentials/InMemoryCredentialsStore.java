package com.github.motassemja.moauth.credentials;

import android.content.Context;

import de.adorsys.android.securestoragelibrary.SecurePreferences;

/**
 * Created by moja on 12.06.2017.
 */

public class InMemoryCredentialsStore implements MoAuthCredentialsStore {

    @Override
    public void storeCredentials(MoAuthCredentials credentials, Context context) {
        SecurePreferences.setValue(credentials.getAlias(), credentials.getRefreshToken(), context);
    }

    @Override
    public MoAuthCredentials loadCredentials(String alias, Context context) {
        String rToken = SecurePreferences.getStringValue(alias, context, "");
        assert rToken != null;
        if (!rToken.isEmpty()) {
            return new MoAuthCredentials(rToken, alias);
        }
        return null;
    }
}
