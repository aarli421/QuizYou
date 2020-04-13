package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.MakeTestActivity;
import com.example.quizyou.Test.TestActivity;

public class StudentActivity extends AppCompatActivity {

    private Button mLogout, mJoinClass, mSeeReports, mTakeTest;

    // TODO Menu to view Email/Password/ID/Name/Code/Logout
    // TODO View list of students, list of test made, list of tests results, list of test assigned
    // TODO Make test button
    // TODO Grade test button
    // TODO ArrayList of teachers

    // I will finish this by wednesday

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

    }
}
