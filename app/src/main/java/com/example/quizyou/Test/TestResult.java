package com.example.quizyou.Test;

import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;

import java.util.ArrayList;

public class TestResult {
    private ArrayList<Question> questions;
    private ArrayList<String> answers;
    private Test test;
    private long studentID;
    private int exitedApp;
    private String start, end;

    public TestResult(Test test, ArrayList<String> answers, long studentID, int exitedApp, String start, String end) {
        this.test = test;
        questions = test.getQuestions();
        this.answers = answers;
        this.studentID = studentID;
        this.exitedApp = exitedApp;
        this.start = start;
        this.end = end;
    }

    public long getStudentID() {
        return studentID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public int getExitedApp() {
        return exitedApp;
    }

    public Test getTest() {
        return test;
    }
}
