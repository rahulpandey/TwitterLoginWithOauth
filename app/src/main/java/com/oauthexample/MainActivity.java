package com.oauthexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.TwitterApplication;
import com.model.TwitterProfile;
import com.oauth.callbacks.OauthAuthenticateListener;
import com.oauth.config.OauthConfig;
import com.oauth.exception.OauthAuthenticationException;
import com.service.TwitterRestAdapter;
import com.util.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        TwitterApplication.getInstance().saveTokens(token, tokenSecret);
        Call<TwitterProfile> profile = TwitterRestAdapter.getInstance().getProfile();
        profile.enqueue(new Callback<TwitterProfile>() {
            @Override
            public void onResponse(Call<TwitterProfile> call, Response<TwitterProfile> response) {
                showProgressBar(false);
                if (response.isSuccessful()) {
                    TwitterProfile twitterProfile = response.body();
                    startProfileActivity(twitterProfile);
                }

            }

            @Override
            public void onFailure(Call<TwitterProfile> call, Throwable t) {
                showProgressBar(false);
            }
        });

    }

    private void startProfileActivity(TwitterProfile mTwitterProfile) {
        Intent mIntent = new Intent(this, ProfileActivity.class);
        mIntent.putExtra(Constant.KEY_TWITTER_PROFILE, mTwitterProfile);
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
