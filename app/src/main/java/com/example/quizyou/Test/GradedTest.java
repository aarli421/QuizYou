package com.example.quizyou.Test;

public class GradedTest {
    private int points, totalPoints;
    private String notes, score, testName;
    private Test test;

    public GradedTest(Test test, int points, int totalPoints, String notes) {
        this.test = test;
        this.points = points;
        this.totalPoints = totalPoints;
        this.notes = notes;
    }

    public GradedTest(String testName, String score, String notes) {
        this.testName = testName;
        this.score = points + " / " + totalPoints;
        this.notes = notes;
    }



    public int getPoints() {
        return points;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public String getNotes() {
        return notes;
    }

    public String getTestName(){return test.getName();}

    public Test getTest() {
        return test;
    }
}
