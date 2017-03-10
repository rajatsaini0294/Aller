package com.rajat.android.aller.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rajat on 3/10/2017.
 */

public class LocationPOJO implements Parcelable {
    protected LocationPOJO(Parcel in) {
    }

    public static final Creator<LocationPOJO> CREATOR = new Creator<LocationPOJO>() {
        @Override
        public LocationPOJO createFromParcel(Parcel in) {
            return new LocationPOJO(in);
        }

        @Override
        public LocationPOJO[] newArray(int size) {
            return new LocationPOJO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
