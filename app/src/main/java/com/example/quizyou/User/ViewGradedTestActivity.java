package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Test;

import java.util.ArrayList;

public class ViewGradedTestActivity extends AppCompatActivity {
    private TextView mtestName;
    private TextView mtestScore;
    private Button mBack;

    @Override
    //TODO add the name of the test
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graded_test);

        mtestName = findViewById(R.id.testName);
        mtestScore = findViewById(R.id.testScore);
        mBack = findViewById(R.id.viewGradeTestBack);
        Student obj = new Student("Larry Zhi", "larryzhi@gmail.com", "larryzhi");

        ArrayList<Question> tempQ = new ArrayList<Question>();
        Question newQ = new Question("Are you gay?", "yes", 5);
        tempQ.add(newQ);
        Test obj1 = new Test(5000, tempQ,"Aaron is Gay");
        GradedTest obj2 = new GradedTest(obj1, 1, 5, "Aaron is gay");
        obj.addReport(obj2);

        ArrayList<Question> tempQ1 = new ArrayList<Question>();
        Question newR = new Question("What is 1 + 1?", "2", 3);
        tempQ1.add(newR);
        Test obj3 = new Test (5000, tempQ1, "Unit 3 Quiz");
        GradedTest obj4 = new GradedTest(obj3, 2, 3, "aaron is too retarded to do 1 + 1");
        obj.addReport(obj4);
        String str = "";
        String name = "";

        //((Student) MainActivity.u)
        for(GradedTest test : obj.getReports()){
            str += test.getPoints() + "/" + test.getTotalPoints() + "\n" + "\n";
            name += test.getTest().getName() + "\n" + "\n";
        }

        mtestScore.setText(str);
        mtestName.setText(name);
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
