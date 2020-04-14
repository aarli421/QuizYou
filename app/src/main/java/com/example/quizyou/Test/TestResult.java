package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;

import java.util.ArrayList;

public class TestResult {
    private ArrayList<Question> questions;
    private ArrayList<String> answers;
    private Test test;
    private Student answerer;
    private int exitedApp;

    public TestResult(Test test, ArrayList<String> answers, Student student, int exitedApp) {
        this.test = test;
        questions = test.getQuestions();
        this.answers = answers;
        answerer = student;
        this.exitedApp = exitedApp;
    }

    public Student getAnswerer() {
        return answerer;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getExitedApp() {
        return exitedApp;
    }

    public Test getTest() {
        return test;
    }
}
