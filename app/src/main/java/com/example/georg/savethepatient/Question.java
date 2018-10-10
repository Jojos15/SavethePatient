package com.example.georg.savethepatient;

public class Question {

    private String quest;
    private String[] answers;
    private int[] type;

    public Question(String quest, String[] answers, int[] type){
        this.quest = quest;
        this.answers = answers;
        this.type = type;
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

    public int[] getType(){
        return type;
    }

    public int getType(int index){
        return type[index];
    }

    public void setType(int[] type) {
        this.type = type;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }
}
