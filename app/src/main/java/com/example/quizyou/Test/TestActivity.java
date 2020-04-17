package com.example.quizyou.Test;

import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private TextView mName, mTimeRemaining, mPrompt, mQuestionNumber;
    private EditText mAnswer;

    private static final String TAG = "TestActivity";
    private ImageButton mSubmit, mNext, mBack;

    private String[] answersArr;

    private Test test;

    private int exitedApp = 0, index = 0;

    // TODO Create timer UI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        test = StudentActivity.selectedTest;
        if (test == null) return;

        answersArr = new String[test.getQuestions().size()];

        mAnswer = findViewById(R.id.answer);

        mName = findViewById(R.id.takeTestName);
        mTimeRemaining = findViewById(R.id.timeRemaining);
        mPrompt = findViewById(R.id.takeTestPrompt);
        mQuestionNumber = findViewById(R.id.takeTestQuestionNumber);

        mSubmit = findViewById(R.id.submitTest);
        mNext = findViewById(R.id.nextQuestion);
        mBack = findViewById(R.id.previousQuestion);

        mName.setText(test.getName());
        mTimeRemaining.setText(Long.toString(test.getTimeLimit()));

        display();

        for (int i = 0; i < answersArr.length; i++) answersArr[i] = "";

        final CountDownTimer countDownTimer = new CountDownTimer(test.getTimeLimit(), 1000) {

            public void onTick(long millisUntilFinished) {
                long hours = millisUntilFinished / 3600000;
                long minutes = (millisUntilFinished - hours * 3600000) / 60000;
                long seconds = (millisUntilFinished - hours * 3600000 - minutes * 60000) / 1000;

                String hoursStr = "", minutesStr = "", secondsStr = "";

                if (hours <= 9) {
                    hoursStr = "0";
                }

                if (minutes <= 9) {
                    minutesStr = "0";
                }

                if (seconds <= 9) {
                    secondsStr = "0";
                }

                mTimeRemaining.setText(hoursStr + hours + ":" + minutesStr + minutes + ":" + secondsStr + seconds);
            }

            public void onFinish() {
                finishTest(test);
            }
        }.start();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answersArr[index] = mAnswer.getText().toString();
                countDownTimer.cancel();
                finishTest(test);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < test.getQuestions().size() - 1) {
                    answersArr[index] = mAnswer.getText().toString();

                    index++;
                    display();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    answersArr[index] = mAnswer.getText().toString();

                    index--;
                    display();
                }
            }
        });

        //handler.postDelayed(periodicUpdate, test.getTimeLimit());
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

    public void display() {
        mAnswer.setText(answersArr[index]);
        mPrompt.setText(test.getQuestions().get(index).getPrompt());
        int questionNum = index + 1;
        mQuestionNumber.setText("Question " + questionNum + "/" + test.getQuestions().size());
    }

    public void finishTest(Test test) {
        ArrayList<String> answers = new ArrayList<>();
        for (String ans : answersArr) {
            if (ans == null) ans = "";
            answers.add(ans);
        }

        MainActivity.load();

        for (Map.Entry<String, Object> m : TeacherActivity.teachers.entrySet()) {
            if (((Teacher) m.getValue()).getStudentIDs().contains(Long.toString(((Student) MainActivity.u).getID()))) {// && ((Teacher) m.getValue()).getAssignedTests().contains(test)) {
                ((Teacher) m.getValue()).addTestResults(new TestResult(test, answers, (Student) MainActivity.u, exitedApp));
                ((Student) MainActivity.u).addTaken(test);

                MainActivity.mDb.collection("Students")
                        .document(Long.toString(((Student) MainActivity.u).getID()))
                        .update("taken", ((Student) MainActivity.u).getTaken(),
                                "pending", ((Student) MainActivity.u).getPending());

                MainActivity.mDb.collection("Teachers")
                        .document(Long.toString(((Teacher) m.getValue()).getID()))
                        .update("results", ((Teacher) m.getValue()).getResults());

                handler.removeCallbacksAndMessages(null);

                Log.d(TAG, "Entered");
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