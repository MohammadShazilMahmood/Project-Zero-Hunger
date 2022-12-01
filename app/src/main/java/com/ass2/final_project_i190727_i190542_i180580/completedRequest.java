package com.ass2.final_project_i190727_i190542_i180580;

import android.os.Parcel;
import android.os.Parcelable;

public class completedRequest implements Parcelable {
    acceptedRequest accepted_request;
    String completion_time;


    public completedRequest() {
    }

    public completedRequest(acceptedRequest accepted_request, String completion_time) {
        this.accepted_request = accepted_request;
        this.completion_time = completion_time;
    }

    protected completedRequest(Parcel in) {
        accepted_request = in.readParcelable(acceptedRequest.class.getClassLoader());
        completion_time = in.readString();
    }

    public static final Creator<completedRequest> CREATOR = new Creator<completedRequest>() {
        @Override
        public completedRequest createFromParcel(Parcel in) {
            return new completedRequest(in);
        }

        @Override
        public completedRequest[] newArray(int size) {
            return new completedRequest[size];
        }
    };

    public acceptedRequest getAccepted_request() {
        return accepted_request;
    }

    public void setAccepted_request(acceptedRequest accepted_request) {
        this.accepted_request = accepted_request;
    }

    public String getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(String completion_time) {
        this.completion_time = completion_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(accepted_request, i);
        parcel.writeString(completion_time);
    }
}
