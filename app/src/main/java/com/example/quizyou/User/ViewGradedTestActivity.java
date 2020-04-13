package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.R;
import com.example.quizyou.Test.GradedTest;

import java.util.ArrayList;

public class ViewGradedTestActivity extends AppCompatActivity {
    private TextView mtestName;
    private TextView mtestScore;
    private Button mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graded_test);

        mtestName = findViewById(R.id.testName);
        mtestScore = findViewById(R.id.testScore);
        mBack = findViewById(R.id.viewGradeTestBack);

        Student obj = new Student("Larry Zhi", "larryzhi@gmail.com", "larryzhi");

        ArrayList<GradedTest> tests = obj.getReports();
        String str = "";
        for(int i = 0; i < tests.size(); i++){
            str += tests.get(i);

        }
        mtestName.setText(str);
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
