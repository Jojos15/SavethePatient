package com.example.georg.savethepatient;

public class Question {

    private String quest;
    private String[] answers;
    private int[] type;
    private boolean hasAnswers = true;
    private String textToDisplay = null;
    private Question implication = null;

    public Question(String quest, String[] answers, int[] type){
        this.quest = quest;
        this.answers = answers;
        this.type = type;
    }

    public Question(String quest, String[] answers, int[] type, Question implication){
        this.quest = quest;
        this.answers = answers;
        this.type = type;
        this.implication = implication;
    }

    public Question(String quest, String textToDisplay){
        this.hasAnswers = false;
    }

    public String getQuest() {
        return quest;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getAnswers(int index) {
        if(answers==null){
            return null;
        }
        return answers[index];
    }

    public int[] getType(){
        return type;
    }

    public int getType(int index){
        return type[index];
    }

    public String getRightAnswer(){
        String temp = "";
        for(int i =0; i<type.length; i++){
            if(type[i] == QuestionLoader.RIGHT){
                temp = answers[i];
            }
        }
        return temp;
    }

    public boolean isHasAnswers() {
        return hasAnswers;
    }

    public void setType(int[] type) {
        this.type = type;
    }

    public void setQuest(String quest) {
        this.quest = quest;
    }

    public String getTextToDisplay() {
        return textToDisplay;
    }

    public void setTextToDisplay(String textToDisplay) {
        this.textToDisplay = textToDisplay;
    }

    public Question getImplication() {
        return implication;
    }

    public void setImplication(Question implication) {
        this.implication = implication;
    }
}
