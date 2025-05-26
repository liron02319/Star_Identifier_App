package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import android.os.Parcel;
import android.os.Parcelable;

public class Star implements Parcelable {
    String name;
    String ra;
    String dec;

    public Star(String name, String ra, String dec) {
        this.name = name;
        this.ra = ra;
        this.dec = dec;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    @Override
    public String toString() {
        return "Star{" +
                "name='" + name + '\'' +
                ", ra='" + ra + '\'' +
                ", dec='" + dec + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(ra);
        parcel.writeString(dec);
    }

    protected Star(Parcel in) {
        name = in.readString();
        ra = in.readString();
        dec = in.readString();
    }
    public static final Parcelable.Creator<Star> CREATOR = new Parcelable.Creator<Star>() {
        @Override
        public Star createFromParcel(Parcel in) {
            return new Star(in);
        }

        @Override
        public Star[] newArray(int size) {
            return new Star[size];
        }
    };

}
