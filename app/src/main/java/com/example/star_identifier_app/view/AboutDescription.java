package com.example.star_identifier_app.view;

import android.widget.Button;

public class AboutDescription {

    protected String title;
    protected String description;
    protected boolean isVisible;

    public AboutDescription(String title, String description, boolean isVisible) {
        this.title = title;
        this.description = description;
        this.isVisible = isVisible;
    }
}