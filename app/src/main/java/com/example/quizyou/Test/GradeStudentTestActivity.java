package com.example.quizyou.Test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;

public class GradeStudentTestActivity extends AppCompatActivity {

    private TextView mStudentName, mExits, mQuestionNumber, mStudentAnswer, mAnswerKey, mPrompt;
    private EditText mPoints, mNotes;
    private ImageButton mNext, mBack, mSubmit,mBackback;
    private int[] points;

    private static final String TAG = "GradeStudentActivity";

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_student_test);

        MainActivity.load();
        mBackback = findViewById(R.id.back_button);
        mStudentName = findViewById(R.id.gradeTestStudentName);
        mExits = findViewById(R.id.gradeTestExits);
        mQuestionNumber = findViewById(R.id.gradeTestQuestionNumber);
        mStudentAnswer = findViewById(R.id.gradeTestStudentAnswer);
        mAnswerKey = findViewById(R.id.gradeTestAnswerKey);
        mPrompt = findViewById(R.id.gradeTestPrompt);

        mPoints = findViewById(R.id.gradeTestPoints);
        mNotes = findViewById(R.id.gradeTestNotes);

        mNext = findViewById(R.id.gradeTestNext);
        mBack = findViewById(R.id.gradeTestBack);
        mSubmit = findViewById(R.id.gradeTestSubmit);

        mStudentName.setText(GradeTestActivity.selectedTestResult.getAnswerer().getName());
        mExits.setText(Integer.toString(GradeTestActivity.selectedTestResult.getExitedApp()));

        points = new int[GradeTestActivity.selectedTestResult.getQuestions().size()];

        for (int i = 0; i < points.length; i++) {
            points[i] = -10000000;
        }

        display();

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < GradeTestActivity.selectedTestResult.getQuestions().size() - 1) {
                    if (mPoints.getText().toString().length() == 0) {
                        // TODO Add put number of points dialog
                        return;
                    }

                    points[index] = Integer.parseInt(mPoints.getText().toString());

                    index++;
                    display();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    if (mPoints.getText().toString().length() == 0) {
                        // TODO Add put number of points dialog
                        return;
                    }

                    points[index] = Integer.parseInt(mPoints.getText().toString());

                    index--;
                    display();
                }
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    points[index] = Integer.parseInt(mPoints.getText().toString());
                } catch (Exception e) {
                    Log.d(TAG, "Could not convert because of " + e);
                }

                for (int i = 0; i < points.length; i++) {
                    if (points[i] == -10000000) {
                        // TODO Say must fill out all points EditTexts
                        return;
                    }
                }

                finishTest();
            }
        });

        mBackback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GradeTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });
    }

    private void display() {
        mPoints.setText("");
        int questionNumber = index + 1;
        mQuestionNumber.setText("Question " + questionNumber);
        mStudentAnswer.setText(GradeTestActivity.selectedTestResult.getAnswers().get(index));
        mAnswerKey.setText(GradeTestActivity.selectedTestResult.getQuestions().get(index).getAnswer());
        mPrompt.setText(GradeTestActivity.selectedTestResult.getQuestions().get(index).getPrompt());

        if (points[index] != -10000000) {
            mPoints.setText(Integer.toString(points[index]));
        } else {
            mPoints.setHint("Points out of total " + GradeTestActivity.selectedTestResult.getQuestions().get(index).getPoints() + " points.");
        }
    }

    private void finishTest() {
        int score = 0, finalTotalScore = 0;
        for (int i = 0; i < points.length; i++) {
            score += points[i];
            finalTotalScore += GradeTestActivity.selectedTestResult.getQuestions().get(i).getPoints();
        }

        String note;
        if (mNotes.getText().toString().length() == 0) {
            note = "";
        } else {
            note = mNotes.getText().toString();
        }

        ((Teacher) MainActivity.u).removeTestResults(GradeTestActivity.selectedTestResult);
        ((Teacher) MainActivity.u).addGradedTests(new GradedTest(GradeTestActivity.selectedTestResult.getTest(), score, finalTotalScore, note, GradeTestActivity.selectedTestResult.getAnswerer().getID()));
        GradeTestActivity.selectedTestResult.getAnswerer().addReport(new GradedTest(GradeTestActivity.selectedTestResult.getTest(), score, finalTotalScore, note, GradeTestActivity.selectedTestResult.getAnswerer().getID()));
        Log.d(TAG, "Score: " + score + " Final Score: " + finalTotalScore + " Note: " + note);

        MainActivity.mDb.collection("Students")
                .document(Long.toString(GradeTestActivity.selectedTestResult.getAnswerer().getID()))
                .update("reports", GradeTestActivity.selectedTestResult.getAnswerer().getReports());

        MainActivity.mDb.collection("Teachers")
                .document(Long.toString(((Teacher) MainActivity.u).getID()))
                .update("results", ((Teacher) MainActivity.u).getResults(),
                        "gradedTests", ((Teacher) MainActivity.u).getGradedTests());

        startActivity(new Intent(getApplicationContext(), GradeTestActivity.class));
        finish();
    }
}
