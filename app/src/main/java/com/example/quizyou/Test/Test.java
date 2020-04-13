package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;

import java.util.ArrayList;

public class Test {
    private int timeLimit;
    private ArrayList<Question> questions;

    public Test(int timeLimit, ArrayList<Question> questions) {
        this.timeLimit = timeLimit;
        this.questions = questions;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void startTest() {

    }
}
