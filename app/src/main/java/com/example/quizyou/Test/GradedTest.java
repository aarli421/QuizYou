package com.example.quizyou.Test;

public class GradedTest {
    private int points, totalPoints;
    private String notes;
    private Test test;

    public GradedTest(Test test, int points, int totalPoints, String notes) {
        this.test = test;
        this.points = points;
        this.totalPoints = totalPoints;
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

    public Test getTest() {
        return test;
    }
}
