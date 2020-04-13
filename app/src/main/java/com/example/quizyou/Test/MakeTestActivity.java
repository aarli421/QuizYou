package com.example.quizyou.Test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;

import java.util.ArrayList;

public class MakeTestActivity extends AppCompatActivity {
    private long timeLimit;
    private Question question;

    // TODO Creating a question must have prompt, points, and answer
    // TODO Have a add question button
    // TODO Add time limit

    private ArrayList<QuestionTexts> questionTexts = new ArrayList<>();
    private int index = 0;
    private boolean goBack = false;

    Button mNext, mBack;
    public static EditText mTestName, mTimeLimit, mPrompt, mAnswer, mPoints;
    public static TextView mProblem, mDelete, mSubmit;

    private static final String TAG = "MakeTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_test);

        mTestName = findViewById(R.id.testName);
        mTimeLimit = findViewById(R.id.timeLimit);

        mPrompt = findViewById(R.id.prompt);
        mAnswer = findViewById(R.id.answer);
        mPoints = findViewById(R.id.points);

        mProblem = findViewById(R.id.problem);

        mNext = findViewById(R.id.nextButton);
        mBack = findViewById(R.id.backButton);
        mDelete = findViewById(R.id.deleteButton);
        mSubmit = findViewById(R.id.submitButton);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrompt.getText().toString().length() == 0 || mPoints.getText().toString().length() == 0 || mAnswer.getText().toString().length() == 0) {
                    return;
                }

                if (goBack) {
                    int num = index + 2;
                    mProblem.setText("Question " + num);

                    if (index + 1 == questionTexts.size()) {
                        mPrompt.getText().clear();
                        mPoints.getText().clear();
                        mAnswer.getText().clear();
                        index++;
                        goBack = false;
                    } else {
                        index++;
                        //Log.d(TAG, "On index " + index + " which corresponds with " + questionTexts.get(index));
                        questionTexts.get(index).display();
                    }
                } else {
                    int num = index + 1;
                    mProblem.setText("Question " + num);

                    if (index == questionTexts.size()) {
                        questionTexts.add(new QuestionTexts(mPrompt.getText().toString(), mPoints.getText().toString(), mAnswer.getText().toString()));
                        mPrompt.getText().clear();
                        mPoints.getText().clear();
                        mAnswer.getText().clear();
                        index++;
                    } else {
                        index++;
                        questionTexts.get(index).display();
                    }
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d(TAG, questionTexts.toString());

                if (mPrompt.getText().toString().length() == 0 || mPoints.getText().toString().length() == 0 || mAnswer.getText().toString().length() == 0) {
                    return;
                }

                if (index > 0) {
                    if (!goBack) {
                        goBack = true;
                    }

                    if (index == questionTexts.size()) {
                        questionTexts.add(new QuestionTexts(mPrompt.getText().toString(), mPoints.getText().toString(), mAnswer.getText().toString()));
                    }

                    index--;
                    questionTexts.get(index).display();

                    int num = index + 1;
                    mProblem.setText("Question " + num);
                } else {
                    // TODO Display cannot go back further
                }
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0) {
                    questionTexts.remove(index);
                    questionTexts.get(index).display();
                } else {
                    questionTexts.remove(index);
                    index--;
                    questionTexts.get(index).display();
                }
                return;
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Question> questions = new ArrayList<>();
                for (int i = 0; i < questionTexts.size(); i++) {
                    questions.add(new Question(questionTexts.get(i).getPrompt(), questionTexts.get(i).getAnswer(), Integer.parseInt(questionTexts.get(i).getPoints())));
                }
                ((Teacher) MainActivity.u).addMadeTest(new Test(1000 * 60 * Long.parseLong(mTimeLimit.getText().toString()), questions, mTestName.getText().toString()));
                startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                finish();
                return;
            }
        });
    }
}

class QuestionTexts {
    private String prompt, points, answer;

    public QuestionTexts(String prompt, String points, String answer) {
        this.prompt = prompt;
        this.points = points;
        this.answer = answer;
    }

    public void display() {
        MakeTestActivity.mPrompt.setText(prompt);
        MakeTestActivity.mPoints.setText(points);
        MakeTestActivity.mAnswer.setText(answer);
    }

    public String getPrompt() {
        return prompt;
    }

    public String getPoints() {
        return points;
    }

    public String getAnswer() {
        return answer;
    }

    public String toString() {
        return "Prompt: " + prompt + " Answer: " + answer + " Points: " + points;
    }
}
