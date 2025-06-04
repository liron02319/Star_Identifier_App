package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.star_identifier_app.R;

public class MainActivity extends AppCompatActivity {



    VideoView videoMenuScreenPage;
    MediaPlayer mMediaPlayer;
    private Button signIn, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        videoMenuScreenPage=findViewById(R.id.videoMenuScreenPage);
        signIn=findViewById(R.id.buttonMainLogIn);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a loading spinner here, or move to another screen
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();

                // Example: open another activity
                Intent intent = new Intent(MainActivity.this, Home2.class);
                startActivity(intent);

                // Or you can run a login check here
            }
        });



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