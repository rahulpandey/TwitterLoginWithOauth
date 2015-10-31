package com.oauth.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.oauth.R;


public class AuthenticationDialogFragment extends DialogFragment {
    public static final String KEY_URL = "com.oauth.KEY_URL";
    public static final String KEY_CALLBACK = "com.oauth.KEY_CALLBACK";
    public static final String KEY_RECEIVER = "com.oauth.KEY_RECEIVER";
    public static final int RESULT_AUTHORISE = 1;
    private ResultReceiver mResultReceiver;
    private WebView webView;
    private ProgressBar mProgressBar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResultReceiver = getArguments().getParcelable(KEY_RECEIVER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication_dialog, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.backgroundIndicator);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyAppWebViewClient());
        webView.loadUrl(getArguments().getString(KEY_URL));
        return view;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    private void showProgressBar(boolean show) {
        webView.setVisibility(show ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public class MyAppWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgressBar(true);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!Uri.parse(url).getScheme().equals(Uri.parse(getArguments().getString(KEY_CALLBACK)).getScheme())) {
                return false;
            } else {
                AuthenticationDialogFragment.this.getDialog().cancel();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_URL, url);
                mResultReceiver.send(RESULT_AUTHORISE, bundle);
                return true;
            }

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            showProgressBar(false);
        }
    }
}
