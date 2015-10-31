package com.util;

/**
 * Created by Rahul on 10/28/2015.
 */
public final class Constant {
    private static final String SCHEMA = "x-oauth-application";
    private static final String HOST = "callback";
    public static final String OAUTH_CALLBACK_URL = SCHEMA + "://" + HOST;
    public static final String REQUEST_TOKEN = "https://api.twitter.com/oauth/request_token";
    public static final String ACCESS_TOKEN = "https://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE = "https://api.twitter.com/oauth/authorize";
    public static final String BASE_URL = "https://api.twitter.com/1.1/";

    public static final String KEY_TWITTER_PROFILE = "KEY_TWITTER_PROFILE";
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_TOKEN_SECRET = "KEY_TOKEN_SECRET";
}
