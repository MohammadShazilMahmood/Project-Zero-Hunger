package com.ass2.final_project_i190727_i190542_i180580;

import android.os.Parcel;
import android.os.Parcelable;

public class notifications implements Parcelable {
    String playerID, loggedIN, notificationSettings;


    public notifications() {
    }

    public notifications(String playerID, String loggedIN, String notificationSettings) {
        this.playerID = playerID;
        this.loggedIN = loggedIN;
        this.notificationSettings = notificationSettings;
    }

    protected notifications(Parcel in) {
        playerID = in.readString();
        loggedIN = in.readString();
        notificationSettings = in.readString();
    }

    public static final Creator<notifications> CREATOR = new Creator<notifications>() {
        @Override
        public notifications createFromParcel(Parcel in) {
            return new notifications(in);
        }

        @Override
        public notifications[] newArray(int size) {
            return new notifications[size];
        }
    };

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getLoggedIN() {
        return loggedIN;
    }

    public void setLoggedIN(String loggedIN) {
        this.loggedIN = loggedIN;
    }

    public String getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(String notificationSettings) {
        this.notificationSettings = notificationSettings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(playerID);
        parcel.writeString(loggedIN);
        parcel.writeString(notificationSettings);
    }
}
