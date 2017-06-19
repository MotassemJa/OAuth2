package com.github.motassemja.moauth.credentials;

import android.content.Context;

/**
 * Created by moja on 12.06.2017.
 */

public interface MoAuthCredentialsStore {
    void storeCredentials(MoAuthCredentials credentials, Context context);

    MoAuthCredentials loadCredentials(String alias, Context context);
}
