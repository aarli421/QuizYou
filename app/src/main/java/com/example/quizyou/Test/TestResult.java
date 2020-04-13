package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;

import java.util.ArrayList;

public class TestResult {
    private ArrayList<Question> questions;
    private Student answerer;

    public TestResult(Test test, Student student) {
        questions = test.getQuestions();
        answerer = student;
    }

    public TestResult(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Student getAnswerer() {
        return answerer;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
