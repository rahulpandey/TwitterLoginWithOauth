package com.oauthexample;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.model.TwitterProfile;
import com.util.Constant;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView mProfileImageView = (ImageView) findViewById(R.id.view);
        TextView mTxtUserName = (TextView) findViewById(R.id.txtUserName);
        TextView mTxtScreenName = (TextView) findViewById(R.id.txtScreenName);
        TwitterProfile mTwitterProfile = getIntent().getParcelableExtra(Constant.KEY_TWITTER_PROFILE);
        ImageView coverPic = (ImageView) findViewById(R.id.user_cover_image);
        mTxtUserName.setText(mTwitterProfile.getName());
        mTxtScreenName.setText(mTwitterProfile.getScreenName());
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#" + mTwitterProfile.getProfileBackgroundColor()));
        }*/

        RequestBuilder<Bitmap> bitmapRequestBuilder = Glide.with(this)
                .asBitmap()
                .apply(RequestOptions
                        .diskCacheStrategyOf(DiskCacheStrategy.DATA)
                        .centerCrop(this)
                        .placeholder(R.drawable.bg));
        bitmapRequestBuilder.load(mTwitterProfile.getProfileImageUrlHttps()).into(mProfileImageView);
        bitmapRequestBuilder.load(mTwitterProfile.getProfileBackgroundImageUrlHttps()).into(coverPic);

    }
}
