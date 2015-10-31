package com.oauthexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.model.TwitterProfile;
import com.util.Constant;
import com.volleynetwork.VolleySingleton;
import com.widget.SquareImageView;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SquareImageView mProfileImageView = (SquareImageView) findViewById(R.id.view);
        TextView mTxtUserName = (TextView) findViewById(R.id.txtUserName);
        TextView mTxtScreenName = (TextView) findViewById(R.id.txtScreenName);
        TwitterProfile mTwitterProfile = getIntent().getParcelableExtra(Constant.KEY_TWITTER_PROFILE);
        mTxtUserName.setText(mTwitterProfile.getName());
        mTxtScreenName.setText(mTwitterProfile.getScreenName());
        String token = getIntent().getStringExtra(Constant.KEY_TOKEN);
        String tokenSecret = getIntent().getStringExtra(Constant.KEY_TOKEN_SECRET);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#" + mTwitterProfile.getProfileBackgroundColor()));
        }*/

        mProfileImageView.setDefaultImageResId(R.drawable.bg);
        mProfileImageView.setErrorImageResId(R.drawable.bg);
        mProfileImageView.setImageUrl(mTwitterProfile.getProfileImageUrl(), VolleySingleton.getInstance(getApplicationContext(), token, tokenSecret).getImageLoader());
    }
}
