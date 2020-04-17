package com.example.quizyou.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.GradedTestAdapter;
import com.example.quizyou.Test.Question.Question;

import java.util.ArrayList;

public class ViewStudentReportActivity extends AppCompatActivity {

    private ImageButton mBack;
    private TextView mNoTests;
    private ListView mListView;

    private SwipeRefreshLayout mPullRefresh;

    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_report);

        mBack = findViewById(R.id.back_button);
        mNoTests = findViewById(R.id.textView6);
        mListView = findViewById(R.id.student_list_view);

        mPullRefresh = findViewById(R.id.pullToRefreshStudentReport);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        if (ReportActivity.selectedStudent.getReports().size() == 0){
            mNoTests.setText("No graded tests right now");
        }  else{
            mNoTests.setText("");
        }
        GradedTestAdapter adapter = new GradedTestAdapter(this, R.layout.adapter_view_layout, ReportActivity.selectedStudent.getReports());
        mListView.setAdapter(adapter);

        mPullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.load();
                Intent intent = new Intent(getApplicationContext(), ViewStudentReportActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                mPullRefresh.setRefreshing(false);
            }
        });
    }
}