package com.example.quizyou.Test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;

public class MakeTestActivity extends AppCompatActivity {
    private long timeLimit;
    private Question question;

    // TODO Creating a question must have prompt, points, and answer
    // TODO Have a add question button
    // TODO Add time limit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_test);
    }
}
