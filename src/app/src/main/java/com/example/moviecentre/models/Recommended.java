package com.example.moviecentre.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Recommended implements Parcelable {

    private String title;


    public Recommended() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Recommended(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    protected Recommended(Parcel in) {
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recommended> CREATOR = new Parcelable.Creator<Recommended>() {
        @Override
        public Recommended createFromParcel(Parcel in) {
            return new Recommended(in);
        }

        @Override
        public Recommended[] newArray(int size) {
            return new Recommended[size];
        }
    };

}