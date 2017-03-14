package com.rajat.android.aller.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rajat on 3/10/2017.
 */

public class LocationPOJO implements Parcelable {

    public String _id;
    public String place_id;
    public String place_name;
    public String place_address;
    public String place_phone;
    public String place_website;
    public String place_latitude;
    public String place_longitude;
    public String place_rating;

    public LocationPOJO() {
    }

    public String getPlace_rating() {
        return place_rating;
    }

    public String getPlace_longitude() {
        return place_longitude;
    }

    public String getPlace_latitude() {
        return place_latitude;
    }

    public String getPlace_website() {
        return place_website;
    }

    public String getPlace_phone() {
        return place_phone;
    }

    public String getPlace_address() {
        return place_address;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public void setPlace_address(String place_address) {
        this.place_address = place_address;
    }

    public void setPlace_phone(String place_phone) {
        this.place_phone = place_phone;
    }

    public void setPlace_website(String place_website) {
        this.place_website = place_website;
    }

    public void setPlace_latitude(String place_latitude) {
        this.place_latitude = place_latitude;
    }

    public void setPlace_longitude(String place_longitude) {
        this.place_longitude = place_longitude;
    }

    public void setPlace_rating(String place_rating) {
        this.place_rating = place_rating;
    }

    protected LocationPOJO(Parcel in) {
        _id = in.readString();
        place_id = in.readString();
        place_name = in.readString();
        place_address = in.readString();
        place_phone = in.readString();
        place_website = in.readString();
        place_latitude = in.readString();
        place_longitude = in.readString();
        place_rating = in.readString();

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
        parcel.writeString(_id);
        parcel.writeString(place_id);
        parcel.writeString(place_name);
        parcel.writeString(place_address);
        parcel.writeString(place_phone);
        parcel.writeString(place_website);
        parcel.writeString(place_latitude);
        parcel.writeString(place_longitude);
        parcel.writeString(place_rating);
    }
}
