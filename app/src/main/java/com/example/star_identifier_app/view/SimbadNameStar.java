package com.example.star_identifier_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.star_identifier_app.R;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimbadNameStar extends AsyncTask<String, Void, String> {
    String nameStar;
    private OnTaskCompleteListener listener;
    String nameR;
    public SimbadNameStar(String name, OnTaskCompleteListener onTaskCompleteListener) {
        this.nameStar = name;
        this.listener = onTaskCompleteListener;
    }

    @Override
    protected String doInBackground(String... params) {
        List<String> names = new ArrayList<>();
        try {
            String url = "http://simbad.u-strasbg.fr/simbad/sim-id?output.format=ASCII&Ident="+nameStar+"&submit=submit+id";
            Document doc = Jsoup.connect(url).get();
            String bodyText = doc.body().text();
            int nameStartIndex = bodyText.indexOf("Identifiers");
            int nameEndIndex = bodyText.indexOf("Bibcodes");
            if (nameStartIndex != -1 && nameEndIndex != -1) {
                String nameSection = bodyText.substring(nameStartIndex, nameEndIndex);
                String[] lines = nameSection.split("\n");
                boolean test = false;
                for (String line : lines) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        if (line.contains("NAME")){
                            String[] nameWords = line.split(" ");
                            for (String word : nameWords) {
                                if (test){
                                    names.add(word);
                                    test = false;
                                }
                                if (word.equals("NAME")){
                                    test = true;
                                }
                            }
                        }

                    }
                }
                Set<String> duplicateNames = new HashSet<>();
                Set<String> uniqueNames = new HashSet<>();
                for (String name : names) {
                    if (!uniqueNames.add(name)) {
                        duplicateNames.add(name);
                    }
                }
                if (duplicateNames.size() > 0){
                    names.clear();
                    names.addAll(duplicateNames);
                }else {
                }

            } else {
                names.add("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (names.size() > 0){
            nameR = names.get(0);

        }else {
            nameR = "";
        }
        return nameR;
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onTaskComplete(nameR);
        }
    }

    public interface OnTaskCompleteListener {
        void onTaskComplete(String name);
    }
}
