package com.oauth;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v4.app.DialogFragment;

import com.oauth.callbacks.OauthAuthenticateListener;
import com.oauth.config.OauthConfig;
import com.oauth.exception.OauthAuthenticationException;
import com.oauth.fragment.AuthenticationDialogFragment;

import java.util.concurrent.ExecutionException;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthException;

public class OauthAuthenticator {
    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_SECRET = "KEY_SECRET";

    private String mCallback;

    private OAuthConsumer consumer;
    private OAuthProvider provider;
    private AuthenticationThread mLoginThread;
    private OauthAuthenticateListener mOauthAuthenticateListener;

    private android.support.v4.app.FragmentManager mFragmentManager;
    private LoginResultReceiver resultReceiver = new LoginResultReceiver(new Handler());

    private static final int URL_RETRIEVE = 1;
    private static final int ACCESS_TOKEN_RETRIEVE = 2;
    private static final int REPORT_ERROR = 3;

    public OauthAuthenticator(OauthConfig oauthConfig) {
        mCallback = oauthConfig.getCallBackUrl();
        consumer = new DefaultOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET);
        provider = new DefaultOAuthProvider(oauthConfig.getRequestTokenUrl(), oauthConfig.getAccessTokenUrl(), oauthConfig.getOauthAuthorizeUrl());
        mOauthAuthenticateListener = oauthConfig.getOauthAuthenticateListener();
        mFragmentManager = oauthConfig.getFragmentManager();
    }


    private android.os.Handler handler = new android.os.Handler(new android.os.Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case URL_RETRIEVE:
                    showLoginDialog((String) message.obj);
                    break;
                case ACCESS_TOKEN_RETRIEVE:
                    Bundle bundle = (Bundle) message.obj;
                    mOauthAuthenticateListener.onOauthAuthenticationDone(bundle.getString(KEY_TOKEN), bundle.getString(KEY_SECRET));
                    break;
                case REPORT_ERROR:
                    Exception ex = (Exception) message.obj;
                    mOauthAuthenticateListener.onOauthAuthenticationError(new OauthAuthenticationException(ex));
                    break;
            }
            return true;
        }
    });

    private void showLoginDialog(String url) {
        DialogFragment dialogFragment = new AuthenticationDialogFragment();
        Bundle args = new Bundle();
        args.putString(AuthenticationDialogFragment.KEY_URL, url);
        args.putString(AuthenticationDialogFragment.KEY_CALLBACK, mCallback);
        args.putParcelable(AuthenticationDialogFragment.KEY_RECEIVER, resultReceiver);
        dialogFragment.setArguments(args);
        dialogFragment.show(mFragmentManager, "oauth_login_dialog");

    }

    @SuppressLint("ParcelCreator")
    public class LoginResultReceiver extends ResultReceiver {
        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler {@link Handler}
         */
        public LoginResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == AuthenticationDialogFragment.RESULT_AUTHORISE) {
                mLoginThread = new AuthenticationThread(Uri.parse(resultData.getString(AuthenticationDialogFragment.KEY_URL)), false);
                mLoginThread.startRunning();
            }
        }
    }

    public void startAuthentication() {
        mLoginThread = new AuthenticationThread(null, true);
        mLoginThread.startRunning();

    }

    private class AuthenticationThread extends Thread implements Runnable {
        private boolean isRetrieveAuthUrl;
        private Uri uri;
        private volatile boolean isLoginRunning;

        public AuthenticationThread(Uri uri, boolean isRetrieveAuthUrl) {
            this.isRetrieveAuthUrl = isRetrieveAuthUrl;
            this.uri = uri;
        }

        @Override
        public void run() {
            while (isLoginRunning) {
                try {
                    if (isRetrieveAuthUrl) {
                        String mAuthUrl = provider.retrieveRequestToken(consumer, mCallback);
                        Message.obtain(handler, URL_RETRIEVE, mAuthUrl).sendToTarget();
                    } else {
                        String oauth_verifier = uri.getQueryParameter("oauth_verifier");
                        //    String oauth_token = uri.getQueryParameter("oauth_token");
                        provider.retrieveAccessToken(consumer, oauth_verifier);
                        parseOauthToken();
                    }
                    stopRunning();
                } catch (OAuthException | InterruptedException | ExecutionException e) {
                    Message.obtain(handler, REPORT_ERROR, e).sendToTarget();
                    stopRunning();

                }
            }

        }

        public synchronized void stopRunning() {
            isLoginRunning = false;
            mLoginThread = null;
        }

        public synchronized void startRunning() {
            isLoginRunning = true;
            start();
        }
    }

    private void parseOauthToken() throws InterruptedException, ExecutionException {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TOKEN, consumer.getToken());
        bundle.putString(KEY_SECRET, consumer.getTokenSecret());
        Message.obtain(handler, ACCESS_TOKEN_RETRIEVE, bundle).sendToTarget();
    }


}