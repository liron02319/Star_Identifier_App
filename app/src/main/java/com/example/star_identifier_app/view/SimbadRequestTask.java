package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import android.annotation.SuppressLint;
import android.os.AsyncTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
public class SimbadRequestTask extends AsyncTask<Void, Void, String> {
    private String latitude;
    private String longitude;
    List<Star> starList = new ArrayList<>();
    private OnTaskCompleteListener listener;
    public SimbadRequestTask(String latitude, String longitude, OnTaskCompleteListener listener) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.longitude = longitude.replace(" ", "+");
        this.latitude = latitude.replace(" ", "+");
        this.listener = listener;
    }

    @SuppressLint("WrongThread")
    @Override
    protected String doInBackground(Void... voids) {
        try {
            String encodedLatitude = URLEncoder.encode(latitude, "UTF-8");
            String encodedLongitude = URLEncoder.encode(longitude, "UTF-8");
            String apiUrl = "https://simbad.u-strasbg.fr/simbad/sim-coo?Coord=+"+latitude+"+"+longitude+"+&Radius=20&Radius.unit=arcmin&submit=submit+query";
            System.out.println(apiUrl);
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            String re = "";
            boolean test = false;
            boolean testLon = false;
            boolean testLat = false;
            boolean testStar = false;
            boolean test11= false;
            String valueName = "";
            String valueLat = "";
            String valueLon = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("TBODY")){
                    test = true;
                }
                if (test){
                    System.out.println(line);
                    if (testLat){
                        if ( testStar){
                            valueLat = extractValueFromLon(line);
                            valueLon = valueLon.trim();
                            valueLat = valueLat.trim();
                            starList.add(new Star(valueName,valueLon,valueLat));

                        }
                        testLat = false;
                        testStar = false;
                        test11 = false;

                    }

                    if (testLon){
                        valueLon = extractValueFromLon(line);
                        System.out.println(valueLon);
                        testLon = false;
                    }
                    if (line.contains("class=\"lon\"")){
                        testLon = true;
                        System.out.println(testLon);
                    }
                    if (line.contains("class=\"lat\"")){
                        testLat = true;

                    }
                    if (test11){
                        if (line.contains("*")) {
                            testStar = true;
                        }
                    }
                    if (line.contains("submit")) {
                        valueName = extractValueFromLine(line);
                        test11 = true;
                    }
                }

            }
            System.out.println("starList "+starList);
            reader.close();

            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }
    private static String extractValueFromLine(String line) {
        Pattern pattern = Pattern.compile("<A.*?>(.*?)</A>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String extractValueFromLat(String line) {
        Pattern pattern = Pattern.compile("<TD.*?class=\"lat\">(.*?)</TD>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    private static String extractValueFromLon(String line) {
        Pattern pattern = Pattern.compile("(.*?)</TD>");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onTaskComplete(starList);
        }
    }

    public interface OnTaskCompleteListener {
        void onTaskComplete(List<Star> starList);
    }
}
*/