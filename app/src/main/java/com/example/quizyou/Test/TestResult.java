package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;

import java.util.ArrayList;

public class TestResult {
    private ArrayList<Question> questions;
    private ArrayList<String> answers;
    private Student answerer;

    public TestResult(Test test, ArrayList<String> answers, Student student) {
        questions = test.getQuestions();
        this.answers = answers;
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
