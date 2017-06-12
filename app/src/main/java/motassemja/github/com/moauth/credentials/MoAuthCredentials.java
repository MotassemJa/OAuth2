package motassemja.github.com.moauth.credentials;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthCredentials {
    private String refreshToken;

    public MoAuthCredentials(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
