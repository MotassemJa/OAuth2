package motassemja.github.com.moauth.credentials;

/**
 * Created by moja on 12.06.2017.
 */

public interface MoAuthCredentialsStore {
    void storeCredentials(MoAuthCredentials credentials);

    MoAuthCredentials loadCredentials();
}
