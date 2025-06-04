package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Star {
    private final String name;
    private final float x;
    private final float y;

    public Star(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Star copy(String name, float x, float y) {
        return new Star(name, x, y);
    }

    public Star copy() {
        return new Star(this.name, this.x, this.y);
    }

    // Component functions for destructuring (Kotlin data class feature)
    public String component1() {
        return name;
    }

    public float component2() {
        return x;
    }

    public float component3() {
        return y;
    }

    @Override
    public String toString() {
        return "Star(name=" + name + ", x=" + x + ", y=" + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Star star = (Star) obj;
        return Float.compare(star.x, x) == 0 &&
                Float.compare(star.y, y) == 0 &&
                Objects.equals(name, star.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y);
    }
}





