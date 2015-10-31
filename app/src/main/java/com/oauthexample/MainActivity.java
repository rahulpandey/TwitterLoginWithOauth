package com.oauthexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.model.TwitterProfile;
import com.oauth.callbacks.OauthAuthenticateListener;
import com.oauth.config.OauthConfig;
import com.oauth.exception.OauthAuthenticationException;
import com.util.Constant;
import com.volleynetwork.GsonRequest;
import com.volleynetwork.VolleySingleton;


public class MainActivity extends AppCompatActivity implements OauthAuthenticateListener {

    private View buttonView;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonView = findViewById(R.id.button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void onLoginClick(View view) {
        OauthConfig oauthConfig = new OauthConfig.Builder()
                .addRequestTokenUrl(Constant.REQUEST_TOKEN)
                .addOauthAuthorizeUrl(Constant.AUTHORIZE)
                .addAccessTokenUrl(Constant.ACCESS_TOKEN)
                .addCallBackUrl(Constant.OAUTH_CALLBACK_URL)
                .addFragmentManager(getSupportFragmentManager())
                .addOauthAuthenticateListener(this)
                .build();
        showProgressBar(true);
        oauthConfig.authenticate();


    }


    @Override
    public void onOauthAuthenticationDone(final String token, final String tokenSecret) {
        Log.d(TAG, "token=>" + token);
        Log.d(TAG, "Token Secret=>" + tokenSecret);
        GsonRequest<TwitterProfile> mRequest = new GsonRequest<>(
                Constant.BASE_URL + "account/verify_credentials.json",
                TwitterProfile.class,
                new com.android.volley.Response.Listener<TwitterProfile>() {
                    @Override
                    public void onResponse(TwitterProfile mTwitterProfile) {
                        showProgressBar(false);
                        startProfileActivity(mTwitterProfile, token, tokenSecret);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        showProgressBar(false);
                    }
                });
        VolleySingleton
                .getInstance(getApplicationContext(), token, tokenSecret)
                .addToRequestQueue(mRequest);
    }

    private void startProfileActivity(TwitterProfile mTwitterProfile, String token, String tokenSecret) {
        Intent mIntent = new Intent(this, ProfileActivity.class);
        mIntent.putExtra(Constant.KEY_TWITTER_PROFILE, mTwitterProfile);
        mIntent.putExtra(Constant.KEY_TOKEN, token);
        mIntent.putExtra(Constant.KEY_TOKEN_SECRET, tokenSecret);
        startActivity(mIntent);
    }

    @Override
    public void onOauthAuthenticationError(OauthAuthenticationException ex) {
        showProgressBar(false);
        ex.printStackTrace();
    }

    public void showProgressBar(boolean show) {
        buttonView.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
