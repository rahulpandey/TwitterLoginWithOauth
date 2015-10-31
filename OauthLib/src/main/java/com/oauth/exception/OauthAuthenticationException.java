package com.oauth.exception;


public final class OauthAuthenticationException extends Exception {

    public OauthAuthenticationException(String detailMessage) {
        super(detailMessage);
    }
    public OauthAuthenticationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public OauthAuthenticationException(Throwable throwable) {
        super(throwable);
    }
}
