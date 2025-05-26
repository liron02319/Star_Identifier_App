package com.example.star_identifier_app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {



    VideoView videoMenuScreenPage;
    MediaPlayer mMediaPlayer;
    private Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        videoMenuScreenPage=findViewById(R.id.videoMenuScreenPage);

        String path="android.resource://" + getPackageName() + "/" + R.raw.video_main_screen;
        Uri uri=Uri.parse(path);
        videoMenuScreenPage.setVideoURI(uri);
/*
        videoMenu.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                String errorMsg = "שגיאה בהפעלת הווידאו: what=" + what + ", extra=" + extra;
                Log.e("VideoView", errorMsg);

                Toast.makeText(Main.this, errorMsg, Toast.LENGTH_LONG).show();

                return true; // Important! Return true to suppress system dialog.
            }
        });*/
        videoMenuScreenPage.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer=mp;
                mMediaPlayer.start();
                mMediaPlayer.setLooping(true);


            }

        });





    }



}