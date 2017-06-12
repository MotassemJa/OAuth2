package motassemja.github.com.moauth.credentials;

/**
 * Created by moja on 12.06.2017.
 */

public class InMemoryCredentialsStore implements MoAuthCredentialsStore {
    private MoAuthCredentials credentials;

    @Override
    public void storeCredentials(MoAuthCredentials credentials) {

    }

    @Override
    public MoAuthCredentials loadCredentials() {
        return null;
    }
}
