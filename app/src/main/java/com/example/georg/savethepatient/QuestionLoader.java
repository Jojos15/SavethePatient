package com.example.georg.savethepatient;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class QuestionLoader {

    Context context;

    public QuestionLoader(Context context){
        this.context = context;
        load();
    }

    public void load(){
        InputStream is = context.getResources().openRawResource(R.raw.level1);
        String line;
        String cvsSplitBy = ",";


        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                for(int i = 0;i<country.length;i++){
                    Log.d("fileRead", country[i]);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
