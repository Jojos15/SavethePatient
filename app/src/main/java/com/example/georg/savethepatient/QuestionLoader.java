package com.example.georg.savethepatient;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class QuestionLoader {

    Context context;
    ArrayList<Question> level1 = new ArrayList<>();
    ArrayList<Question> level2 = new ArrayList<>();
    ArrayList<Question> level3 = new ArrayList<>();

    public QuestionLoader(Context context){
        this.context = context;
        load();
    }

    public void load(){
        String line;
        String cvsSplitBy = ",";

        // LEVEL 1
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.level1), Charset.forName("UTF-8")))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] questions = line.split(cvsSplitBy);

                level1.add(new Question(questions[0], new String[]{questions[1], questions[2], questions[3], questions[4]}));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //LEVEL 2
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.level2), Charset.forName("UTF-8")))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] questions = line.split(cvsSplitBy);
                level2.add(new Question(questions[0], new String[]{questions[1], questions[2], questions[3], questions[4]}));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //LEVEL 3
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.level3), Charset.forName("UTF-8")))) {

            br.readLine();

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] questions = line.split(cvsSplitBy);

                level3.add(new Question(questions[0], new String[]{questions[1], questions[2], questions[3], questions[4]}));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> getQuestionsList(int level){

        return shuffle(level);
    }

    private ArrayList<Question> shuffle(int level){

        ArrayList<Question> temp = new ArrayList<>();
        if(level==1) {
            Random random = new Random();
            ArrayList<Integer> positions = new ArrayList<>();
            int count = 1;
            for (int i = 0; i < level1.size(); i++) {
                positions.add(i);
            }
            while (positions.size() >= 0) {
                Log.d("position size", level1.size() +"");
                int pos = random.nextInt(positions.size());
                temp.add(level1.get(positions.get(pos)));
                positions.remove(pos);
                if(count==10){
                    break;
                }
                else count++;
            }
        }
        else if(level==2){
            Random random = new Random();
            ArrayList<Integer> positions = new ArrayList<>();
            int count = 1;
            for (int i = 0; i < level2.size(); i++) {
                positions.add(i);
            }
            while (positions.size() >= 0) {
                int pos = random.nextInt(positions.size());
                temp.add(level2.get(positions.get(pos)));
                positions.remove(pos);
                if(count==8){
                    break;
                }
                else count++;
            }
        }
        else{
            Random random = new Random();
            ArrayList<Integer> positions = new ArrayList<>();
            int count = 1;
            for (int i = 0; i < level3.size(); i++) {
                positions.add(i);
            }
            while (positions.size() >=0) {
                int pos = random.nextInt(positions.size());
                temp.add(level3.get(positions.get(pos)));
                positions.remove(pos);
                if(count==5){
                    break;
                }
                else count++;
            }
        }

        return temp;
    }
}
