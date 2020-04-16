package com.example.quizyou.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;

import java.util.ArrayList;

public class ViewStudentReportActivity extends AppCompatActivity {

    private ImageButton mBack;
    private TextView mNoTests;


    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_student_reports);

        mBack = findViewById(R.id.back_button);
        mNoTests = findViewById(R.id.textView6);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });


    }
}