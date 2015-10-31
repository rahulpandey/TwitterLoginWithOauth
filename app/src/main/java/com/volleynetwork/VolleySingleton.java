package com.volleynetwork;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.oauth.BuildConfig;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.util.BitmapLruCache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

/**
 * Created by Rahul Pandey on 08-08-2015 at 20:16
 */
public class VolleySingleton {
    private static VolleySingleton mInstance;
    private final ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private String token;
    private String tokenSecret;

    private VolleySingleton(Context context, String token, String tokenSecret) {
        this.token = token;
        this.tokenSecret = tokenSecret;
        mCtx = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapLruCache());

    }

    public static synchronized VolleySingleton getInstance(Context context, String token, String tokenSecret) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context, token, tokenSecret);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new SignpostUrlStack(token, tokenSecret));
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private static class SignpostUrlStack extends HurlStack {
        private final OkUrlFactory okUrlFactory;
        private OAuthConsumer consumer;

        public SignpostUrlStack(String token, String tokenSecret) {
            consumer = new DefaultOAuthConsumer(BuildConfig.API_KEY, BuildConfig.API_SECRET);
            consumer.setTokenWithSecret(token, tokenSecret);
            this.okUrlFactory = new OkUrlFactory(new OkHttpClient());
        }

        @Override
        protected HttpURLConnection createConnection(URL url) throws IOException {
            HttpURLConnection connection = okUrlFactory.open(url);
            try {
                consumer.sign(connection);
            } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
