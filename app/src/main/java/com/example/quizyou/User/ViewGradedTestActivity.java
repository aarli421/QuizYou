
package com.example.quizyou.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.GradedTestAdapter;

import java.util.ArrayList;

//TODO implement test objects in array

public class ViewGradedTestActivity extends AppCompatActivity {

    private SwipeRefreshLayout mPullRefresh;

    private ImageButton mBack;
    private TextView mNoTests;
    private ArrayList<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_graded_test);

        mPullRefresh = findViewById(R.id.pullToRefreshGradedTests);

        mBack = findViewById(R.id.back_button);
        mNoTests = findViewById(R.id.textView6);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        ListView mListView = (ListView) findViewById(R.id.list_view1);

        if (((Student) MainActivity.u).getReports().size() == 0){
            mNoTests.setText("No graded tests right now");
        } else {
            mNoTests.setText("");
        }
        GradedTestAdapter adapter = new GradedTestAdapter(this, R.layout.adapter_view_layout, ((Student) MainActivity.u).getReports());
        mListView.setAdapter(adapter);

        mPullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.load();
                Intent intent = new Intent(getApplicationContext(), ViewGradedTestActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                mPullRefresh.setRefreshing(false);
            }
        });
    }

}