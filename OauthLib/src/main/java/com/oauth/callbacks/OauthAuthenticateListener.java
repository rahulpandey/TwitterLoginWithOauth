package com.oauth.callbacks;

import com.oauth.exception.OauthAuthenticationException;

/**
 * Created by Rahul on 10/15/2015.
 */
public interface OauthAuthenticateListener {
    void onOauthAuthenticationDone(String token, String tokenSecret);
    void onOauthAuthenticationError(OauthAuthenticationException ex);
}
