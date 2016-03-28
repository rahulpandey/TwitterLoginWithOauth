package com.service;

import com.TwitterApplication;
import com.model.TwitterProfile;
import com.oauth.BuildConfig;
import com.util.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;


public class TwitterRestAdapter {


    private static TwitterService sInstance;

    public static synchronized void initialize() {
        String[] tokens = TwitterApplication.getInstance().getTokens();
        OkHttpOAuthConsumer okHttpOAuthConsumer = new OkHttpOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET);
        okHttpOAuthConsumer.setTokenWithSecret(tokens[0], tokens[1]);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        SigningInterceptor signingInterceptor = new SigningInterceptor(okHttpOAuthConsumer);
        OkHttpClient build = new OkHttpClient.Builder().addInterceptor(signingInterceptor)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL).client(build)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sInstance = retrofit.create(TwitterService.class);
    }

    public static synchronized TwitterService getInstance() {
        if (sInstance == null) {
            initialize();
        }
        return sInstance;
    }

    public interface TwitterService {
        @GET("account/verify_credentials.json")
        Call<TwitterProfile> getProfile();
    }

}
