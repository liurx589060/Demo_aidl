package com.lrx.plugin.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/12/31.
 */

public class SampleInfo implements Parcelable{
    private String userName;
    private String userId;
    private int userAge;
    private String userSex;
    private String jsonString;

    protected SampleInfo(Parcel in) {
        userName = in.readString();
        userId = in.readString();
        userAge = in.readInt();
        userSex = in.readString();
        jsonString = in.readString();
    }

    public SampleInfo() {}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userId);
        dest.writeInt(userAge);
        dest.writeString(userSex);
        dest.writeString(jsonString);
    }

    public void readFromParcel(Parcel source) {
        userName = source.readString();
        userId = source.readString();
        userAge = source.readInt();
        userSex = source.readString();
        jsonString = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SampleInfo> CREATOR = new Creator<SampleInfo>() {
        @Override
        public SampleInfo createFromParcel(Parcel in) {
            return new SampleInfo(in);
        }

        @Override
        public SampleInfo[] newArray(int size) {
            return new SampleInfo[size];
        }
    };

    public String getUserName() {
        return userName==null?"":userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex==null?"":userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserId() {
        return userId==null?"":userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJsonString() {
        return jsonString==null?"":jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("userName:  ")
                .append(getUserName())
                .append("\n")
                .append("userId: ")
                .append(getUserId())
                .append("\n")
                .append("userAge: ")
                .append(getUserAge())
                .append("\n")
                .append("userSex: ")
                .append(getUserSex())
                .append("\n")
                .append("jsonString: ")
                .append(getJsonString());
        return builder.toString();
    }
}
