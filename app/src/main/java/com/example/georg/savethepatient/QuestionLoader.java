package com.example.georg.savethepatient;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class QuestionLoader {

    public static int WRONG = 0;
    public static int RIGHT = 1;
    public static int IMPLICATION = 2;

    private Context context;
    private ArrayList<Question> level1 = new ArrayList<>();
    private ArrayList<Question> level2 = new ArrayList<>();
    private ArrayList<Question> level3 = new ArrayList<>();

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

                level1.add(new Question(questions[0], new String[]{questions[1], questions[2], questions[3], questions[4]}, new int[]{RIGHT, WRONG, WRONG, WRONG}));
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
                level2.add(new Question(questions[0], new String[]{questions[1], questions[2], questions[3], questions[4]}, new int[]{RIGHT, WRONG, WRONG, WRONG}));
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

                level3.add(new Question(questions[0], new String[]{questions[1], questions[2], questions[3], questions[4]}, new int[]{RIGHT, IMPLICATION, WRONG, WRONG}));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Question> getQuestionsList(int level){

        return shuffle(level);
    }

    private ArrayList<Question> shuffle(int level){

        switch (level){
            case 1: return shuffleQuestions(10, level1);
            case 2: return shuffleQuestions(8, level2);
            case 3: return shuffleQuestions(5, level3);
            default: return null;

        }
    }

    private ArrayList<Question> shuffleQuestions(int count, ArrayList<Question> questions){
        Random random = new Random();
        ArrayList<Question> temp = new ArrayList<>();
        ArrayList<Integer> positions = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < questions.size(); i++) {
            positions.add(i);
        }
        while (positions.size() >=0) {
            int pos = random.nextInt(positions.size());
            temp.add(shuffleAnswers(questions.get(positions.get(pos))));
            positions.remove(pos);
            if(counter==count){
                break;
            }
            else counter++;
        }
        return temp;
    }

    private Question shuffleAnswers(Question question){
        Random rnd = ThreadLocalRandom.current();
        String[] ar = question.getAnswers();
        int[] type = question.getType();
        for (int i = question.getAnswers().length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;

            int temp = type[index];
            type[index] = type[i];
            type[i] = temp;

        }

        return new Question(question.getQuest(), ar, type);
    }
}