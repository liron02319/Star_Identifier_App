package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.star_identifier_app.R;

import java.util.ArrayList;
import java.util.List;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    Button btnCamera, btnGallery;
    ImageView imStar;
    static Bitmap bitmap;
    List<StarCamera> starListAll = new ArrayList<>();
    static String latitude;
    static String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        imStar = findViewById(R.id.imStar);

        btnCamera.setOnClickListener(l ->{
            Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(iCamera, 100);

        });

        btnGallery.setOnClickListener(l ->{
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, 1000);

        });

        imStar.setOnClickListener(l ->{
            try {
                Intent intentMore = new Intent(this, MoreInformation.class);

                Bitmap bitmap = ((BitmapDrawable) imStar.getDrawable()).getBitmap();
                System.out.println("latitude "+latitude);
                System.out.println("longitude "+longitude);
                intentMore.putExtra("imageBitmap", bitmap);
                intentMore.putExtra("latitude", latitude);
                intentMore.putExtra("longitude", longitude);

                startActivity(intentMore);
            } catch (RuntimeException e) {
                Intent intentMore = new Intent(this, MoreInformation.class);

                bitmap = ((BitmapDrawable) imStar.getDrawable()).getBitmap();
                startActivity(intentMore);

            }





        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            System.out.println(requestCode);

            if (requestCode == 1000){
//                gallery
                imStar.setImageURI(data.getData());
                showCustomAlertDialog(Home.this);

            }

            if (requestCode == 100){
//                camera
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imStar.setImageBitmap(bitmap);

                getLocation1();
            }
        }

    }

    private void getLocation1() {
        LocationManager locationManager;
        LocationListener locationListener;
        // Initialize the LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Define the LocationListener
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Called when the location is updated
                double latitude1 = location.getLatitude();
                double longitude1 = location.getLongitude();

                // Use the latitude and longitude values
                // ...
                String hour = "23:59:59";
                String date = "2023-06-09";
                latitude = String.valueOf(latitude1);
                longitude =  String.valueOf(longitude1);
                System.out.println("longitudelongitude "+longitude);;
                System.out.println("latitudelatitude "+latitude);
//                String latitude = "-60.834";
//                String longitude = "14.66";
                annotatePictureWithStarNames(imStar, latitude,longitude, hour, date);

                // Stop listening for location updates
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            // Request the necessary permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void showCustomAlertDialog(Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Custom Dialog");

        // Inflate the custom layout for the dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        alertDialogBuilder.setView(dialogView);

        final EditText editText1 = dialogView.findViewById(R.id.editText1);
        final EditText editText2 = dialogView.findViewById(R.id.editText2);

        // Set the positive button click listener
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the values from the EditText views
                String value1 = editText1.getText().toString();
                String value2 = editText2.getText().toString();
                dialog.dismiss();

                String hour = "23:59:59";
                String date = "2023-06-09";
                latitude = value1;
                longitude = value2;
//                 latitude = "-60.834";
//                 longitude = "14.66";
//                String latitude = "-60.834";
//                String longitude = "14.66";
                annotatePictureWithStarNames(imStar, latitude,longitude, hour, date);

                // Do something with the values
                // For example, display a Toast message with the entered values
//                Toast.makeText(MainActivity.this, "Value 1: " + value1 + "\nValue 2: " + value2, Toast.LENGTH_SHORT).show();
            }
        });

        // Set the negative button click listener
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void annotatePictureWithStarNames(ImageView imageView, String latitude,String longitude, String hour, String date) {


        CoordinatesConverter converter = new CoordinatesConverter(latitude,longitude);
        String dec = converter.coordinatesDec();
        String ra = converter.coordinatesRa();
        SimbadRequestTask task = new SimbadRequestTask(ra, dec, new SimbadRequestTask.OnTaskCompleteListener() {
            @Override
            public void onTaskComplete(List<StarCamera> starList) {
                System.out.println(starList);
                circleStarsOnImage(imageView,starList);
                starListAll = starList;
                for (StarCamera starName1 :starList) {
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


    private float calculateMaxRA(List<StarCamera> starList) {
        float maxRA = Float.MIN_VALUE;

        for (StarCamera star : starList) {
            String ra = star.getRa();

            if (ra.split(" ").length == 3) {


                float raDegrees = convertToDecimalDegrees(ra);

                if (raDegrees > maxRA) {
                    maxRA = raDegrees;
                }
            }
        }

        return maxRA;
    }

    private float calculateMaxDEC(List<StarCamera> starList) {
        float maxDEC = Float.MIN_VALUE;

        for (StarCamera star : starList) {
            String dec = star.getDec();
            if (dec.split(" ").length == 3){
                float decDegrees = convertToDecimalDegrees(dec);

                if (decDegrees > maxDEC) {
                    maxDEC = decDegrees;
                }
            }

        }

        return maxDEC;
    }

    private float convertToDecimalDegrees(String coordinate) {
        String[] parts = coordinate.split(" ");
        int degrees = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        float seconds = Float.parseFloat(parts[2]);
        if (degrees < 0) {
            degrees *= -1;
        }
        float decimalDegrees = degrees + (minutes / 60.0f) + (seconds / 3600.0f);
        return decimalDegrees;
    }
    public void circleStarsOnImage(ImageView imageView, List<StarCamera> starList) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        System.out.println(bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(3);
        float minRA = Float.MAX_VALUE;
        float minDEC = Float.MAX_VALUE;
        for (StarCamera star : starList) {
            String ra = star.getRa();
            String dec = star.getDec();
            if (ra.split(" ").length == 3 && dec.split(" ").length == 3) {
                float raDegrees = convertToDecimalDegrees(ra);
                float decDegrees = convertToDecimalDegrees(dec);
                if (raDegrees < minRA) {
                    minRA = raDegrees;
                }
                if (decDegrees < minDEC) {
                    minDEC = decDegrees;
                }
            }

        }
//        int imageViewWidth = 850;
//        int imageViewHeight = 800;
        int imageViewWidth = imageView.getWidth()-160;
        int imageViewHeight = imageView.getHeight()-380;
        System.out.println(imageViewWidth);
        System.out.println("imageViewHeight "+imageViewHeight);;
        float maxRA = calculateMaxRA(starList);
        float maxDEC = calculateMaxDEC(starList);
        float raRange = maxRA - minRA;
        float decRange = maxDEC - minDEC;
        for (StarCamera star : starList) {
            String starRA = star.getRa();
            String starDEC = star.getDec();
            if (starRA.split(" ").length == 3 && starDEC.split(" ").length == 3) {
                float RADegrees = convertToDecimalDegrees(starRA);
                float DecDegrees = convertToDecimalDegrees(starDEC);
                float normalizedX, normalizedY;
                normalizedX = ((RADegrees - minRA) / raRange);
                normalizedY = ((DecDegrees - minDEC) / decRange);
//                float pixelX = (90+((1 - normalizedX) * imageViewWidth);
//                float pixelY =365+( normalizedY * imageViewHeight);
                float pixelX = 60+((1 - normalizedX) * imageViewWidth);
                float pixelY =200+( normalizedY * imageViewHeight);
                canvas.drawCircle(pixelX, pixelY, 10, circlePaint);
                Paint textPaint = new Paint();
                textPaint.setColor(Color.WHITE);
                textPaint.setTextSize(20);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                canvas.drawText(star.getName(),pixelX-80, pixelY-30 , textPaint);

            }

        }
        imageView.setImageBitmap(mutableBitmap);
        imageView.setDrawingCacheEnabled(false);
    }




}


