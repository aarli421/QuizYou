package com.example.quizyou.Test.Question;

public class ShortAnswerQuestion extends Question {
    public ShortAnswerQuestion(String prompt, String answer, int points) {
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
