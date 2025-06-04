package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
/*
public class MoreInformation extends AppCompatActivity {


    ImageView imStarMore;
    RecyclerView rvStarsMore;
    List<Star> stars = new ArrayList<>();
    StarsAdapter starsAdapter;
    Context context = this;
    List<Star> starListAll = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);
//        ArrayList<Star> starList = getIntent().getParcelableExtra("starList");
        System.out.println("0000000");
//        System.out.println(starList);
        imStarMore = findViewById(R.id.imStarMore);
        rvStarsMore = findViewById(R.id.rvStarsMore);

//        Bitmap bitmap = (Bitmap)  getIntent().getParcelableExtra("BitmapImage");
//        imStarMore.setImageBitmap(bitmap);
// Retrieve the Bitmap object from the Intent
        Bitmap bitmap = getIntent().getParcelableExtra("imageBitmap");
        if (bitmap == null){
            bitmap = Home.bitmap;
            Home.bitmap = null;
        }



        String hour = "23:59:59";
        String date = "2023-06-09";
        String latitude =  getIntent().getStringExtra("latitude");
        if (latitude == null){
            latitude = Home.latitude;
            Home.latitude = null;
        }
        String longitude =  getIntent().getStringExtra("longitude");
        if (longitude == null){
            longitude = Home.longitude;
            Home.longitude = null;
        }
        System.out.println(latitude);
        System.out.println(longitude);
//                String latitude = "-60.834";
//                String longitude = "14.66";
        annotatePictureWithStarNames( latitude,longitude, hour, date);

        System.out.println(bitmap);
// Display the image in an ImageView or perform any desired operations
        imStarMore.setImageBitmap(bitmap);
//        stars.add(new Star("name1","clarity1","more1"));
//        stars.add(new Star("name2","clarity2","more2"));
//        stars.add(new Star("name3","clarity3","more3"));
//        stars.add(new Star("name4","clarity4","more4"));




    }

    private void annotatePictureWithStarNames( String latitude,String longitude, String hour, String date) {


        CoordinatesConverter converter = new CoordinatesConverter(latitude,longitude);
        String dec = converter.coordinatesDec();
        String ra = converter.coordinatesRa();
        SimbadRequestTask task = new SimbadRequestTask(ra, dec, new SimbadRequestTask.OnTaskCompleteListener() {
            @Override
            public void onTaskComplete(List<Star> starList) {
                System.out.println(starList);
                starListAll = starList;
                rvStarsMore.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                starsAdapter = new StarsAdapter(context,starListAll);
                rvStarsMore.setAdapter(starsAdapter);
                for (Star starName1 :starList) {
                    if (starName1.getName().contains("* alf")) {
                        SimbadNameStar task = new SimbadNameStar(starName1.getName(), new SimbadNameStar.OnTaskCompleteListener() {
                            @Override
                            public void onTaskComplete(String nameR) {
                                System.out.println(nameR);
                                if (!nameR.equals("")){
                                    starName1.setName(nameR);
                                    System.out.println("starList "+starList);
                                }

                            }
                        });

                        task.execute();
                    }
                }

            }
        });

        task.execute();
    }


}

 */