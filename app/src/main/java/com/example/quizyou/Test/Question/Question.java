package com.example.quizyou.Test.Question;

public class Question {
    private String prompt, answer;
    private int points;

    public Question() {
        prompt = "";
        answer = "";
        points = 0;
    }

    public Question(String prompt, String answer, int points) {
        this.prompt = prompt;
        this.answer = answer;
        this.points = points;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPoints() {
        return points;
    }
}
