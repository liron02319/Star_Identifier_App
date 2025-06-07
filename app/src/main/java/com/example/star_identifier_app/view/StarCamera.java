package com.example.star_identifier_app.view;

import android.os.Parcel;
import android.os.Parcelable;

public class StarCamera implements Parcelable {
    String name;
    String ra;
    String dec;

    public StarCamera(String name, String ra, String dec) {
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

    protected StarCamera(Parcel in) {
        name = in.readString();
        ra = in.readString();
        dec = in.readString();
    }
    public static final Parcelable.Creator<StarCamera> CREATOR = new Parcelable.Creator<StarCamera>() {
        @Override
        public StarCamera createFromParcel(Parcel in) {
            return new StarCamera(in);
        }

        @Override
        public StarCamera[] newArray(int size) {
            return new StarCamera[size];
        }
    };

}
