package com.example.quizyou.Test;

import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;

import java.util.ArrayList;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private TextView title;
    private LinearLayout scrollViewLinearLayout;

    private static final String TAG = "TestActivity";

    private ArrayList<EditText> editTexts = new ArrayList<>();

    private Button mBack;

    private Test test;

    private int exitedApp = 0;

    // TODO Create timer UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mBack = findViewById(R.id.back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });
//        ArrayList<Question> questions = new ArrayList<>();
//        questions.add(new Question("Why do you like to eat chicken nuggets?", "Cuz I do", 5));
//        questions.add(new Question("Why do you like to nuggets?", "Bruhhh", 7));
//        test = new Test(5, questions, "Random name");

        test = StudentActivity.selectedTest;

        if (test == null) return;

        title = findViewById(R.id.testTitle);
        scrollViewLinearLayout = findViewById(R.id.testLinearLayout);

        title.setText(test.getName());
        for (Question q : test.getQuestions()) {
            TextView text = new TextView(getApplicationContext());
            text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            Typeface face = Typeface.create("sans-serif-light", Typeface.NORMAL);
            text.setTypeface(face);
            text.setTextSize(24);
            text.setText(q.getPrompt());
            scrollViewLinearLayout.addView(text);

            EditText editText = new EditText(getApplicationContext());
            editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setHint("Answer");
            editTexts.add(editText);
            scrollViewLinearLayout.addView(editText);
        }

        Button mSubmit = new Button(getApplicationContext());
        mSubmit.setText("Submit");
        scrollViewLinearLayout.addView(mSubmit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTest(test);
            }
        });

        handler.postDelayed(periodicUpdate, test.getTimeLimit());

//        mBack = findViewById (R.id.back_button);
//
//        mBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//                return;
//
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Resumed the app");
    }

    @Override
    protected void onPause() {
        super.onPause();
        exitedApp++;
        Log.d(TAG, "Exited the app");
    }

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            finishTest(test);
            Log.d(TAG, "Finished handler");
        }
    };

    public void finishTest(Test test) {
        ArrayList<String> answers = new ArrayList<>();
        for (EditText editText : editTexts) {
            answers.add(editText.getText().toString());
        }

        for (Map.Entry<String, Object> m : TeacherActivity.teachers.entrySet()) {
            Log.d(TAG, "Student: " + ((Student) MainActivity.u) + " Teacher: " + ((Teacher) m.getValue()));

            if (((Teacher) m.getValue()).getStudentIDs().contains(Long.toString(((Student) MainActivity.u).getID())) && ((Teacher) m.getValue()).getAssignedTests().contains(test)) {
                ((Teacher) m.getValue()).addTestResults(new TestResult(test, answers, (Student) MainActivity.u, exitedApp));
                ((Student) MainActivity.u).addTaken(test);
                break;
            }
        }

        ((Student) MainActivity.u).addTaken(test);

        Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}