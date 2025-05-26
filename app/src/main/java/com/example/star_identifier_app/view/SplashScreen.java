package com.example.star_identifier_app.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.star_identifier_app.R;

public class SplashScreen extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        videoView = findViewById(R.id.videoSplashScreenLogo);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_loading_logo);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(mp -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        });

        videoView.start();
    }
}