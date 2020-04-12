package com.example.quizyou.Test;

public class GradedTest {
    private int points, totalPoints;
    private String notes;

    public GradedTest(int points, int totalPoints, String notes) {
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
}
