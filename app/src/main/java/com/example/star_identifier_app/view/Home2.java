package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.star_identifier_app.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import android.widget.VideoView;


public class Home2 extends AppCompatActivity {



     ImageView imageView;
     ProgressBar progressBar;
     Button btnCamera;
     Button btnGallery;

    VideoView videoViewStarGif;

    private Uri cameraPhotoUri = null;

    // Replace with your actual server IP
    private final String SERVER_UPLOAD_URL = "http://10.0.0.6:5000/upload";

    private final ActivityResultLauncher<Uri> takePhotoLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), success -> {
                if (success && cameraPhotoUri != null) {
                    Log.d(TAG, "Camera captured URI: " + cameraPhotoUri);
                    handleImageUri(cameraPhotoUri);
                } else {
                    Toast.makeText(this, "Camera capture canceled or failed.", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<String> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    Log.d(TAG, "Gallery selected URI: " + uri);
                    handleImageUri(uri);
                } else {
                    Toast.makeText(this, "No image selected.", Toast.LENGTH_SHORT).show();
                }
            });

    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        videoViewStarGif = findViewById(R.id.videoGifScreenLogoStar);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_loading_logo);
        videoViewStarGif.setVideoURI(video);

        // Loop the video by restarting it on completion
        videoViewStarGif.setOnCompletionListener(mp -> {
            videoViewStarGif.start(); // Loop
        });

        videoViewStarGif.start(); // Start playback



        /////

        imageView = findViewById(R.id.imageViewd);
        progressBar = findViewById(R.id.progressBar);
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        btnCamera.setOnClickListener(v -> openCamera());

        btnGallery.setOnClickListener(v -> {
            // Stop and hide the video view when Gallery button is clicked
            if (videoViewStarGif.isPlaying()) {
                videoViewStarGif.stopPlayback();
            }
            videoViewStarGif.setVisibility(View.GONE);

            openGalleryPicker();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private void openCamera() {
        File photoFile;
        try {
            photoFile = File.createTempFile("photo_" + System.currentTimeMillis(), ".jpg", getCacheDir());
        } catch (Exception e) {
            Toast.makeText(this, "Failed to create file for photo.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "createTempImageFile failed", e);
            return;
        }

        cameraPhotoUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                photoFile
        );
        Log.d(TAG, "Opening camera with URI: " + cameraPhotoUri);
        takePhotoLauncher.launch(cameraPhotoUri);
    }

    private void openGalleryPicker() {
        pickImageLauncher.launch("image/*");
    }

    private void handleImageUri(Uri imageUri) {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        btnCamera.setEnabled(false);
        btnGallery.setEnabled(false);

        executorService.execute(() -> {
            try {
                // STEP 1: Convert URI to File
                File imageFile;
                try {
                    imageFile = uriToFile(imageUri);
                    Log.d(TAG, "STEP 1: Copied URI to file: " + imageFile.getAbsolutePath());
                } catch (Exception e) {
                    throw new Exception("Failed to convert URI to File", e);
                }

                // STEP 2 & 3: Upload to server AND parse JSON
                String jsonResponse;
                try {
                    jsonResponse = uploadImageFile(imageFile);
                    Log.d(TAG, "STEP 2: Received JSON: " + jsonResponse);
                } catch (Exception e) {
                    throw new Exception("Upload/Server error", e);
                }

                List<Star> stars;
                try {
                    stars = parseStarData(jsonResponse);
                    Log.d(TAG, "STEP 3: Parsed " + stars.size() + " stars");
                } catch (Exception e) {
                    throw new Exception("JSON parsing failure", e);
                }

                // STEP 4: Draw annotations on the image
                Bitmap annotatedBitmap;
                try {
                    annotatedBitmap = drawStarsOnImage(imageFile, stars);
                } catch (Exception e) {
                    throw new Exception("Failed drawing annotations", e);
                }

                // STEP 5: Display annotated image (back on Main thread)
                mainHandler.post(() -> {
                    imageView.setImageBitmap(annotatedBitmap);
                    imageView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    btnCamera.setEnabled(true);
                    btnGallery.setEnabled(true);
                });

            } catch (Exception e) {
                e.printStackTrace();
                String rootClass = e.getClass().getSimpleName();
                String rootMsg = e.getMessage() != null ? e.getMessage() : "no message";
                String causeClass = e.getCause() != null ? e.getCause().getClass().getSimpleName() : "none";
                String causeMsg = e.getCause() != null && e.getCause().getMessage() != null ? e.getCause().getMessage() : "none";

                mainHandler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnCamera.setEnabled(true);
                    btnGallery.setEnabled(true);
                    Toast.makeText(
                            Home2.this,
                            "Error [" + rootClass + "]: " + rootMsg + "\nCause [" + causeClass + "]: " + causeMsg,
                            Toast.LENGTH_LONG
                    ).show();
                });
            }
        });
    }

    private File uriToFile(Uri uri) throws Exception {
        if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath());
        } else {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) {
                throw new Exception("Unable to open InputStream from URI");
            }

            File tempFile = File.createTempFile("selected_image_", ".jpg", getCacheDir());
            try (FileOutputStream output = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } finally {
                inputStream.close();
            }
            return tempFile;
        }
    }

    private String uploadImageFile(File imageFile) throws Exception {
        // 1) Build a client with a 5-minute read timeout
        OkHttpClient client = new OkHttpClient.Builder()
                // How long to wait for the server to accept the connection
                .connectTimeout(60, TimeUnit.SECONDS)
                // How long to wait for reads (i.e. response body). We set it to 600 seconds (5 min).
                .readTimeout(600, TimeUnit.SECONDS)
                // How long to wait for writes (i.e. uploading the file). You can adjust as needed.
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        // 2) Prepare multipart‐form request body (field name must match Flask's "image")
        RequestBody requestBody = RequestBody.create(imageFile, MediaType.parse("image/jpeg"));
        MultipartBody multipart = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getName(), requestBody)
                .build();

        // 3) Build and execute the HTTP request
        Request request = new Request.Builder()
                .url(SERVER_UPLOAD_URL)
                .post(multipart)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // Log response code/body for debugging
            Log.d(TAG, "Upload HTTP code: " + response.code());
            ResponseBody responseBody = response.body();
            String bodyString = responseBody != null ? responseBody.string() : null;
            Log.d(TAG, "Upload response body: " + bodyString);

            if (!response.isSuccessful()) {
                throw new Exception("Server returned HTTP " + response.code());
            }
            if (bodyString == null) {
                throw new Exception("Empty response from server");
            }
            return bodyString;
        }
    }

    private List<Star> parseStarData(String jsonResponse) throws Exception {
        List<Star> resultList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(jsonResponse);
            JSONArray starsArray = jsonObj.getJSONArray("stars");
            for (int i = 0; i < starsArray.length(); i++) {
                JSONObject starObj = starsArray.getJSONObject(i);
                String name = starObj.getString("name");
                float x = (float) starObj.getDouble("x");
                float y = (float) starObj.getDouble("y");
                resultList.add(new Star(name, x, y));
            }
        } catch (JSONException e) {
            throw new Exception("JSON parsing error", e);
        }
        return resultList;
    }

    private Bitmap drawStarsOnImage(File imageFile, List<Star> stars) throws Exception {
        Bitmap originalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        if (originalBitmap == null) {
            throw new Exception("Failed to decode image.");
        }

        Bitmap annotatedBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        originalBitmap.recycle();

        Canvas canvas = new Canvas(annotatedBitmap);

        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(5f);

        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25f);
        textPaint.setShadowLayer(5f, 2f, 2f, Color.BLACK);

        for (Star star : stars) {
            float x = star.getX();
            float y = star.getY();
            canvas.drawCircle(x, y, 20f, circlePaint);
            canvas.drawText(star.getName(), x + 25f, y - 25f, textPaint);
        }
        return annotatedBitmap;
    }

    private static final String TAG = "MainActivity";
}

