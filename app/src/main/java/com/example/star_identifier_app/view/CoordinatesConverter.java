package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import java.util.Formatter;

public class CoordinatesConverter {
    String latitude;
    String longitude;

    public CoordinatesConverter(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String coordinatesRa(){
        double lon = Double.parseDouble(longitude);
        double ra = convertToRA(lon);
        String formattedRA = formatRA(ra);
        return formattedRA;
    }
    public String coordinatesDec(){
        double lat = Double.parseDouble(latitude);
        double dec = convertToDec(lat);
        String formattedDec = formatDec(dec);
        return formattedDec;

    }

    private static double convertToRA(double longitude) {
        double ra = longitude / 15.0; // Convert longitude to RA
        return ra;
    }

    private static double convertToDec(double latitude) {
        double dec = latitude; // Latitude is already in Dec format
        return dec;
    }

    private static String formatRA(double ra) {
        Formatter formatter = new Formatter();
        formatter.format("%02d %02d %06.3f", (int) ra, (int) ((ra % 1) * 60), ((ra * 60) % 1) * 60);
        String formattedRA = formatter.toString();
        formatter.close();
        return formattedRA;
    }

    private static String formatDec(double dec) {
        Formatter formatter = new Formatter();
        formatter.format("%+03d %02d %06.4f", (int) dec, (int) (Math.abs(dec) % 1 * 60), (Math.abs(dec) * 60 % 1) * 60);
        String formattedDec = formatter.toString();
        formatter.close();
        return formattedDec;
    }
}
