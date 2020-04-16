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
    Button mSubmit, mBack, mGradeSelected;
    TestResult result = null;

    private ArrayList<EditText> editTexts = new ArrayList<>();
    private final static String TAG = "GradeTestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_test);

        mSpinner = findViewById(R.id.spinnerTests);
        mBack = findViewById(R.id.back_button);
        mGradeSelected = findViewById(R.id.gradeSelectedButton);

        Log.d(TAG, ((Teacher) MainActivity.u).getResults().toString());

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        ArrayList<String> list = new ArrayList<>();
        Log.d(TAG, ((Teacher) MainActivity.u).getResults().toString());
        for (TestResult t : ((Teacher) MainActivity.u).getResults()) {
            list.add(t.getAnswerer().getName() + "'s " + t.getTest().getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        mSpinner.setAdapter(adapter);
    }
}
