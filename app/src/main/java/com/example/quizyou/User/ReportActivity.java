package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private Button mViewStudent;
    private Button mBack;
    private ArrayList<Student> students = new ArrayList<>();
    private TextView mNoTests;



    // TODO List all of the teacher's students and grades for each test in a dropdown menu
    // TODO Back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mBack = findViewById (R.id.back_button);
        mNoTests = findViewById(R.id.textView69);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        ListView mListView = (ListView) findViewById(R.id.list_view1);
        ArrayList<Student> studentsReportsList = new ArrayList<>();

        for (String s : ((Teacher) MainActivity.u).getStudentIDs()) {
            for (Object student : StudentActivity.students.values()) {
                if (s.equals(Long.toString(((Student) student).getID()))) {
                    studentsReportsList.add((Student) student);
                    break;
                }
            }
        }

        if (studentsReportsList.size() == 0) {
            mNoTests.setText("No students right now");
        } else {
            mNoTests.setText("");
        }

        studentList adapter = new studentList(this, R.layout.adapt_view_layout, studentsReportsList);
        mListView.setAdapter(adapter);
    }
}