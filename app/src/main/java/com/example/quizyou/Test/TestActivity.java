package com.example.quizyou.Test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.R;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.TeacherActivity;

public class TestActivity extends AppCompatActivity {

    private Button mBack;

    // TODO Create a new test
    // TODO Have scrollable UI
    // TODO List all questions and answer boxes
    // TODO Submit button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mBack = findViewById (R.id.back_button);

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
