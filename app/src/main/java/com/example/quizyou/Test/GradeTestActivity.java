package com.example.quizyou.Test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;

import java.util.ArrayList;

public class GradeTestActivity extends AppCompatActivity {

    // TODO Display wrong errors

//    Spinner mSpinner;
    Button mSubmit, mBack, mGradeSelected;

    private ArrayList<EditText> editTexts = new ArrayList<>();
    private final static String TAG = "GradeTestActivity";
    private ListView mListView;

    public static TestResult selectedTestResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_test);

//        mSpinner = findViewById(R.id.spinnerTests);
        mBack = findViewById(R.id.back_button);
        mGradeSelected = findViewById(R.id.gradeSelectedButton);
        mListView = findViewById(R.id.grade_list_view);

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

        ArrayList<TestResult> testResults = new ArrayList<>();
        for(int i = 0; i<((Teacher)MainActivity.u).getResults().size(); i++){
            testResults.add(((Teacher)MainActivity.u).getResults().get(i));
        }

        TestResultAdapter adapter1 = new TestResultAdapter(this, R.layout.adapter_grade_view_layout, testResults);
        mListView.setAdapter(adapter1);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestResult result = (TestResult) parent.getItemAtPosition(position);
                selectedTestResult = result;

                startActivity(new Intent(getApplicationContext(), GradeStudentTestActivity.class));
                finish();
            }
        });
    }
}
