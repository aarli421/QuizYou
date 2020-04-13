package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.R;
import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.GradedTestAdapter;

import java.util.ArrayList;

//TODO implement test objects in array

public class ViewGradedTestActivity extends AppCompatActivity {

    private Button mBack;
    private ArrayList<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graded_test);

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

        ListView mListView = (ListView) findViewById(R.id.list_view1);

        Question question = new Question( "prompt", "answer", 5) ;
        Question question1 = new Question( "prompt", "answer", 5) ;
        Question question2 = new Question( "prompt", "answer", 5) ;
        Question question3 = new Question( "prompt", "answer", 5) ;
        Question question4 = new Question( "prompt", "answer", 5) ;
        Question question5 = new Question( "prompt", "answer", 5) ;
        Question question6 = new Question( "prompt", "answer", 5) ;
        Question question7 = new Question( "prompt", "answer", 5) ;
        Question question8 = new Question( "prompt", "answer", 5) ;



        questionList.add(question);
        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        questionList.add(question4);
        questionList.add(question5);
        questionList.add(question6);
        questionList.add(question7);
        questionList.add(question8);


        Test test1 = new Test(60, questionList,  "Test 1" );
        Test test2 = new Test(60, questionList,  "Test 2" );
        Test test3 = new Test(60, questionList,  "Test 3" );
        Test test4 = new Test(60, questionList,  "Test 4" );
        Test test5 = new Test(60, questionList,  "Test 5" );

        GradedTest graded1 = new GradedTest(test1, 40, 45,"good job");
        GradedTest graded2 = new GradedTest(test2, 41, 45,"good job");
        GradedTest graded3 = new GradedTest(test3, 40, 45,"good job");
        GradedTest graded4 = new GradedTest(test4, 20, 45,"bad job");
        GradedTest graded5 = new GradedTest(test5, 40, 45,"good job");

        ArrayList<GradedTest> gradedList = new ArrayList<>();
        gradedList.add(graded1);
        gradedList.add(graded2);
        gradedList.add(graded3);
        gradedList.add(graded4);
        gradedList.add(graded5);



        GradedTestAdapter adapter = new GradedTestAdapter(this, R.layout.adapter_view_layout, gradedList);
        mListView.setAdapter(adapter);
    }

}
