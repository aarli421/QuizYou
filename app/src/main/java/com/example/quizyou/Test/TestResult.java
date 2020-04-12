package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;

import java.util.ArrayList;

public class TestResult {
    private ArrayList<Question> questions;

    public TestResult(Test test) {
        questions = test.getQuestions();
    }

    public TestResult(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
