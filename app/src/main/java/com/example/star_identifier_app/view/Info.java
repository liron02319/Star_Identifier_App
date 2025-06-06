package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.star_identifier_app.R;

import java.util.ArrayList;

public class Info extends AppCompatActivity {


    private ArrayList<AboutDescription> arrayList;
    private RecyclerView recyclerView;

    private AboutAdapter adapter;

    VideoView videoViewStarGifInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        videoViewStarGifInfo = findViewById(R.id.videoGifScreenLogoStarInfo);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_loading_logo);
        videoViewStarGifInfo.setVideoURI(video);

        // Loop the video by restarting it on completion
        videoViewStarGifInfo.setOnCompletionListener(mp -> {
            videoViewStarGifInfo.start(); // Loop
        });

        videoViewStarGifInfo.start(); // Start playback


        recyclerView=findViewById(R.id.recycler_View);
        arrayList=new ArrayList<>();

        arrayList.add(new AboutDescription("About The App?",getString(R.string.AboutTheApp),false));
        arrayList.add(new AboutDescription("How To Detect Stars",getString(R.string.HowToDetectStars),false));
        arrayList.add(new AboutDescription("More Information",getString(R.string.MoreInformation),false));




        adapter=new AboutAdapter(arrayList,Info.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();





    }












}