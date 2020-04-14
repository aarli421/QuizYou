package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;

import java.util.ArrayList;

public class Test {
    private long timeLimit;
    private ArrayList<Question> questions;
    private String name;

    public Test(long timeLimit, ArrayList<Question> questions, String name) {
        this.timeLimit = (long) 1000 * 60 * timeLimit;
        this.questions = questions;
        this.name = name;
    }

    public long getTimeLimit() {
        return timeLimit;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void startTest() {

    }
}
