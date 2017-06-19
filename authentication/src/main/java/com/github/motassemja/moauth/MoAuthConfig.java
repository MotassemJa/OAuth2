package com.github.motassemja.moauth;

import java.net.URI;
import java.util.List;

import com.github.motassemja.moauth.credentials.MoAuthCredentialsStore;

/**
 * Created by moja on 12.06.2017.
 */

public class MoAuthConfig {
    private List<String> scopes;
    private URI tokenUri;
    private Integer timeout;
    private String clientID;
    private String clientSecret;
    private MoAuthCredentialsStore credentialsStore;

    public MoAuthConfig(List<String> scopes, URI tokenUri, Integer timeout, String clientID, String clientSecret) {
        this.scopes = scopes;
        this.tokenUri = tokenUri;
        this.timeout = timeout;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public URI getTokenUri() {
        return tokenUri;
    }

    public void setTokenUri(URI tokenUri) {
        this.tokenUri = tokenUri;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
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

    public MoAuthCredentialsStore getCredentialsStore() {
        return credentialsStore;
    }

    public void setCredentialsStore(MoAuthCredentialsStore credentialsStore) {
        this.credentialsStore = credentialsStore;
    }
}
