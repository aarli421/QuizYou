package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizyou.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private Button mViewStudent;
    private Button mBack;
    private ArrayList<Student> students = new ArrayList<>();



    // TODO List all of the teacher's students and grades for each test in a dropdown menu
    // TODO Back button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mBack = findViewById (R.id.back_button);

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

        //Array list of students here

        Student student1 = new Student("student1", "s@.com", "begone");
        Student student2 = new Student("student2", "s@.com", "begone");
        Student student3 = new Student("student3", "s@.com", "begone");
        Student student4 = new Student("student4", "s@.com", "begone");
        Student student5 = new Student("student5", "s@.com", "begone");
        Student student6 = new Student("student6", "s@.com", "begone");
        Student student7 = new Student("student7", "s@.com", "begone");
        Student student8 = new Student("student8", "s@.com", "begone");
        Student student9 = new Student("student9", "s@.com", "begone");
        Student student10 = new Student("student10", "s@.com", "begone");
        Student student11 = new Student("student11", "s@.com", "begone");
        Student student12 = new Student("student12", "s@.com", "begone");

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        students.add(student6);
        students.add(student7);
        students.add(student8);
        students.add(student9);
        students.add(student10);
        students.add(student11);
        students.add(student12);

        studentList adapter = new studentList(this, R.layout.adapt_view_layout, students);
        mListView.setAdapter(adapter);





    }
}
