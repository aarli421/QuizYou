package com.example.quizyou.Test;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;

import java.util.ArrayList;

public class GradeTestActivity extends AppCompatActivity {

    // TODO Display wrong errors

    Spinner mSpinner;
    Button mSubmit = null;
    TestResult result = null;

    private ArrayList<EditText> editTexts = new ArrayList<>();
    private final static String TAG = "GradeTestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_test);

        mSpinner = findViewById(R.id.spinnerTests);

        //mSubmit = findViewById(R.id.submitGradedTest);

        ArrayList<String> list = new ArrayList<>();

//        final Teacher teacher1 = new Teacher("Teacher Trump", "teachertrump@gmail.com", "teachertrump");
//        ArrayList<Question> questions = new ArrayList<>();
//        questions.add(new Question("Why do you like to eat chicken nuggets?", "Cuz I do", 5));
//        questions.add(new Question("Why do you like to nuggets?", "Bruhhh", 7));
//        Test t1 = new Test(5, questions, "Random name");
//        ArrayList<String> ans = new ArrayList<>();
//        ans.add("69");
//        ans.add("You suck dick");
//        ans.add("you suck dick fuck gay ");
//        teacher1.addTestResults(new TestResult(t1, ans, new Student("fsadghfhkj", "sdfgs", "sdfgsdf")));
//
//        ans.add("69");
//        ans.add("You dic dick dick diiiiiiiiiiiiccccccccccckkkkkkkkkk");
//        ans.add("you ");
//        teacher1.addTestResults(new TestResult(t1, ans, new Student("Brandon's dick", "sdfgs", "sdfgsdf")));

        for (TestResult t : ((Teacher) MainActivity.u).getResults()) {
            list.add(t.getAnswerer().getName() + "'s " + t.getTest().getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout layout = findViewById(R.id.linearLayout);
                layout.removeAllViews();

                String name = parent.getItemAtPosition(position).toString();

                for (TestResult t : ((Teacher) MainActivity.u).getResults()) {
                    if ((t.getAnswerer().getName() + "'s " + t.getTest().getName()).equals(name)) {
                        result = t;
                    }
                }

                int totalScore = 0;
                if (result != null) {
                    for (int i = 0; i < result.getQuestions().size(); i++) {

                        TextView text = new TextView(getApplicationContext());
                        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        Typeface face = Typeface.create("sans-serif-light", Typeface.NORMAL);
                        text.setTypeface(face);
                        text.setTextSize(36);
                        text.setText(result.getQuestions().get(i).getPrompt() + "\nYour Answer: " + result.getQuestions().get(i).getAnswer() +
                                "\nStudent's Answer: " + result.getAnswers().get(i));
                        layout.addView(text);

                        EditText editText = new EditText(getApplicationContext());
                        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        editText.setHint("Enter points out of total points " + result.getQuestions().get(i).getPoints());

                        totalScore += result.getQuestions().get(i).getPoints();

                        editTexts.add(editText);
                        layout.addView(editText);
                    }

                    final EditText notes = new EditText(getApplicationContext());
                    notes.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    notes.setHint("Enter some notes...");
                    layout.addView(notes);

                    mSubmit = new Button(getApplicationContext());
                    mSubmit.setText("Submit");
                    layout.addView(mSubmit);

                    final int finalTotalScore = totalScore;
                    mSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (result != null) {
                                int score = 0;
                                for (int i = 0; i < editTexts.size(); i++) {
                                    score += Integer.parseInt(editTexts.get(i).getText().toString());
                                }

                                final String note = notes.getText().toString();

                                ((Teacher) MainActivity.u).addGradedTests(new GradedTest(result.getTest(), score, finalTotalScore, note));
                                result.getAnswerer().addReport(new GradedTest(result.getTest(), score, finalTotalScore, note));
                                Log.d(TAG, "Score: " + score + " Final Score: " + finalTotalScore + " Note: " + note);

                                startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                                finish();
                                return;
                            }
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
