package com.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 10/31/2015.
 */
public class TwitterProfile implements Parcelable {



        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("id_str")
        @Expose
        private String idStr;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("screen_name")
        @Expose
        private String screenName;
        @SerializedName("profile_background_color")
        @Expose
        private String profileBackgroundColor;
        @SerializedName("profile_background_image_url")
        @Expose
        private String profileBackgroundImageUrl;
        @SerializedName("profile_background_image_url_https")
        @Expose
        private String profileBackgroundImageUrlHttps;
        @SerializedName("profile_image_url")
        @Expose
        private String profileImageUrl;
        @SerializedName("profile_image_url_https")
        @Expose
        private String profileImageUrlHttps;

        /**
         *
         * @return
         * The id
         */
        public Integer getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The idStr
         */
        public String getIdStr() {
            return idStr;
        }

        /**
         *
         * @param idStr
         * The id_str
         */
        public void setIdStr(String idStr) {
            this.idStr = idStr;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The screenName
         */
        public String getScreenName() {
            return screenName;
        }

        /**
         *
         * @param screenName
         * The screen_name
         */
        public void setScreenName(String screenName) {
            this.screenName = screenName;
        }

        /**
         *
         * @return
         * The profileBackgroundColor
         */
        public String getProfileBackgroundColor() {
            return profileBackgroundColor;
        }

        /**
         *
         * @param profileBackgroundColor
         * The profile_background_color
         */
        public void setProfileBackgroundColor(String profileBackgroundColor) {
            this.profileBackgroundColor = profileBackgroundColor;
        }

        /**
         *
         * @return
         * The profileBackgroundImageUrl
         */
        public String getProfileBackgroundImageUrl() {
            return profileBackgroundImageUrl;
        }

        /**
         *
         * @param profileBackgroundImageUrl
         * The profile_background_image_url
         */
        public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
            this.profileBackgroundImageUrl = profileBackgroundImageUrl;
        }

        /**
         *
         * @return
         * The profileBackgroundImageUrlHttps
         */
        public String getProfileBackgroundImageUrlHttps() {
            return profileBackgroundImageUrlHttps;
        }

        /**
         *
         * @param profileBackgroundImageUrlHttps
         * The profile_background_image_url_https
         */
        public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
            this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
        }

        /**
         *
         * @return
         * The profileImageUrl
         */
        public String getProfileImageUrl() {
            return profileImageUrl;
        }

        /**
         *
         * @param profileImageUrl
         * The profile_image_url
         */
        public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
        }

        /**
         *
         * @return
         * The profileImageUrlHttps
         */
        public String getProfileImageUrlHttps() {
            return profileImageUrlHttps;
        }

        /**
         *
         * @param profileImageUrlHttps
         * The profile_image_url_https
         */
        public void setProfileImageUrlHttps(String profileImageUrlHttps) {
            this.profileImageUrlHttps = profileImageUrlHttps;
        }


        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeValue(this.id);
                dest.writeString(this.idStr);
                dest.writeString(this.name);
                dest.writeString(this.screenName);
                dest.writeString(this.profileBackgroundColor);
                dest.writeString(this.profileBackgroundImageUrl);
                dest.writeString(this.profileBackgroundImageUrlHttps);
                dest.writeString(this.profileImageUrl);
                dest.writeString(this.profileImageUrlHttps);
        }

        public TwitterProfile() {
        }

        protected TwitterProfile(Parcel in) {
                this.id = (Integer) in.readValue(Integer.class.getClassLoader());
                this.idStr = in.readString();
                this.name = in.readString();
                this.screenName = in.readString();
                this.profileBackgroundColor = in.readString();
                this.profileBackgroundImageUrl = in.readString();
                this.profileBackgroundImageUrlHttps = in.readString();
                this.profileImageUrl = in.readString();
                this.profileImageUrlHttps = in.readString();
        }

        public static final Parcelable.Creator<TwitterProfile> CREATOR = new Parcelable.Creator<TwitterProfile>() {
                public TwitterProfile createFromParcel(Parcel source) {
                        return new TwitterProfile(source);
                }

                public TwitterProfile[] newArray(int size) {
                        return new TwitterProfile[size];
                }
        };
}
