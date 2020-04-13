package com.example.quizyou.Test.Question;

public class Question {
    public String prompt, answer;
    public int points;

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
}
