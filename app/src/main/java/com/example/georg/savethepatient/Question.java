package com.example.georg.savethepatient;

public class Question {

    private String quest;
    private String[] answers = new String[4];

    public Question(String quest, String[] answers){
        this.quest = quest;
        this.answers = answers;
    }

    public String getQuest() {
        return quest;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getAnswers(int index) {
        return answers[index];
    }
}
