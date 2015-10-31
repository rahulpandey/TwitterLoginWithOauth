package com.oauth.config;


import android.support.v4.app.FragmentManager;

import com.oauth.callbacks.OauthAuthenticateListener;
import com.oauth.OauthAuthenticator;

public class OauthConfig {
    private String requestTokenUrl;
    private String accessTokenUrl;
    private String oauthAuthorizeUrl;
    private FragmentManager fragmentManager;
    private OauthAuthenticateListener mOauthAuthenticateListener;
    private String callBackUrl;

    private OauthConfig(Builder builder) {
        this.requestTokenUrl = builder.requestTokenUrl;
        this.accessTokenUrl = builder.accessTokenUrl;
        this.oauthAuthorizeUrl = builder.oauthAuthorizeUrl;
        this.fragmentManager = builder.fragmentManager;
        this.mOauthAuthenticateListener = builder.mOauthAuthenticateListener;
        this.callBackUrl = builder.callBackUrl;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }



    public String getRequestTokenUrl() {
        return requestTokenUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public String getOauthAuthorizeUrl() {
        return oauthAuthorizeUrl;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public OauthAuthenticateListener getOauthAuthenticateListener() {
        return mOauthAuthenticateListener;
    }

    public void authenticate() {
        OauthAuthenticator oauthAuthenticator = new OauthAuthenticator(this);
        oauthAuthenticator.startAuthentication();
    }

    public static class Builder {
        private OauthAuthenticateListener mOauthAuthenticateListener;
        private FragmentManager fragmentManager;
        private String requestTokenUrl;
        private String accessTokenUrl;
        private String oauthAuthorizeUrl;
        private String callBackUrl;


        public Builder addFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public Builder addCallBackUrl(String callBackUrl) {
            this.callBackUrl = callBackUrl;
            return this;
        }

        public Builder addOauthAuthenticateListener(OauthAuthenticateListener mOauthAuthenticateListener) {
            this.mOauthAuthenticateListener = mOauthAuthenticateListener;
            return this;
        }


        public Builder addRequestTokenUrl(String requestTokenUrl) {
            this.requestTokenUrl = requestTokenUrl;
            return this;
        }

        public Builder addAccessTokenUrl(String accessTokenUrl) {
            this.accessTokenUrl = accessTokenUrl;
            return this;
        }

        public Builder addOauthAuthorizeUrl(String oauthAuthorizeUrl) {
            this.oauthAuthorizeUrl = oauthAuthorizeUrl;
            return this;
        }

        public OauthConfig build() {
            return new OauthConfig(this);
        }
    }
}
