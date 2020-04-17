package com.example.quizyou.Test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakeTestActivity extends AppCompatActivity {
    private long timeLimit;
    private Question question;

    // TODO Creating a question must have prompt, points, and answer
    // TODO Have a add question button
    // TODO Add time limit

    private ArrayList<QuestionTexts> questionTexts = new ArrayList<>();
    private int index = 0;
    private boolean goBack = false;

    ImageButton mNext, mBack, mBackToHome, mDelete, mSubmit;
    public static EditText mTestName, mTimeLimit, mPrompt, mAnswer, mPoints;
    public static TextView mProblem;

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
        mBackToHome = findViewById(R.id.home_button);

        mBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrompt.getText().toString().length() == 0 || mPoints.getText().toString().length() == 0 || mAnswer.getText().toString().length() == 0) {
                    // TODO Tell user to fill out something
                    return;
                }

                try {
                    Log.d(TAG, "On index " + index + " which corresponds with " + questionTexts.get(index));
                } catch (Exception e) {
                    Log.d(TAG, "Could not print because of " + e);
                }

                if (goBack) {
                    int num = index + 2;
                    int totalNum = questionTexts.size();
                    mProblem.setText("On Question: " + num + "\nQuestions Made: " + totalNum);

                    if (index + 1 == questionTexts.size()) {
                        mPrompt.getText().clear();
                        mPoints.getText().clear();
                        mAnswer.getText().clear();
                        index++;
                        goBack = false;
                    } else {
                        index++;

                        try {
                            Log.d(TAG, "On index " + index + " which corresponds with " + questionTexts.get(index));
                        } catch (Exception e) {
                            Log.d(TAG, "Could not print because of " + e);
                        }


                        questionTexts.get(index).display();
                    }
                } else {
                    int num = index + 2;
                    int totalNum = questionTexts.size() + 1;
                    mProblem.setText("On Question: " + num + "\nQuestions Made: " + totalNum);

                    if (index == questionTexts.size()) {
                        questionTexts.add(new QuestionTexts(mPrompt.getText().toString(), mPoints.getText().toString(), mAnswer.getText().toString()));
                        mPrompt.getText().clear();
                        mPoints.getText().clear();
                        mAnswer.getText().clear();
                        index++;
                    } else {
                        index++;

                        try {
                            Log.d(TAG, "On index " + index + " which corresponds with " + questionTexts.get(index));
                        } catch (Exception e) {
                            Log.d(TAG, "Could not print because of " + e);
                        }

                        questionTexts.get(index).display();
                    }
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                    try {
                        Log.d(TAG, "On index " + index + " which corresponds with " + questionTexts.get(index));
                    } catch (Exception e) {
                        Log.d(TAG, "Could not print because of " + e);
                    }

                    questionTexts.get(index).display();

                    int num = index + 1;
                    int totalNum = questionTexts.size();
                    mProblem.setText("On Question: " + num + "\nQuestions Made: " + totalNum);
                } else {
                    // TODO Display cannot go back further
                }
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < questionTexts.size()) {
                    if (index == 0) {
                        questionTexts.remove(index);
                        questionTexts.get(index).display();
                    } else {
                        questionTexts.remove(index);
                        index--;
                        questionTexts.get(index).display();
                    }
                }

                int num = index + 1;
                int totalNum = questionTexts.size();
                mProblem.setText("On Question: " + num + "\nQuestions Made: " + totalNum);
                return;
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!goBack && mPrompt.getText().toString().length() != 0 && mAnswer.getText().toString().length() != 0 && mPoints.getText().toString().length() != 0) {
                    questionTexts.add(new QuestionTexts(mPrompt.getText().toString(), mPoints.getText().toString(), mAnswer.getText().toString()));
                }

                else{
                    Toast.makeText(MakeTestActivity.this , "Please fill out all sections", Toast.LENGTH_LONG).show();
                    return;
                }

                ArrayList<Question> questions = new ArrayList<>();
                for (int i = 0; i < questionTexts.size(); i++) {
                    questions.add(new Question(questionTexts.get(i).getPrompt(), questionTexts.get(i).getAnswer(), Integer.parseInt(questionTexts.get(i).getPoints())));
                }

                MainActivity.load();

                ((Teacher) MainActivity.u).addMadeTest(new Test(60 * 1000 * Long.parseLong(mTimeLimit.getText().toString()), questions, mTestName.getText().toString()));

                MainActivity.mDb.collection("Teachers")
                        .document(Long.toString(((Teacher) MainActivity.u).getID()))
                        .update("madeTests", ((Teacher) MainActivity.u).getMadeTests());

                //MainActivity.save();

                Log.d(TAG, Long.toString(((Teacher) MainActivity.u).getID()));

                Toast.makeText(MakeTestActivity.this,"Test " + mTestName.getText() + " Successfully Made", Toast.LENGTH_LONG).show();

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
