package com.example.quizyou.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.MakeTestActivity;
import com.example.quizyou.Test.TestActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends Activity {

    private Button mLogout, mJoinClass, mMyReports, mTakeTest;

    public static Map<String, Object> students = new HashMap<>();


    // I will finish this by wednesday

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        mLogout = findViewById(R.id.logout_button);
        mJoinClass = findViewById(R.id.join_class_button);
        mMyReports = findViewById(R.id.my_reports_button);
        mTakeTest = findViewById(R.id.take_test_button);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        mJoinClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(new ContextThemeWrapper(StudentActivity.this, R.style.dialog));
                View mView = getLayoutInflater().inflate(R.layout.activity_join_class, null);

                EditText mResponse = (EditText)  mView.findViewById(R.id.editText3);
                Button mSubmit = (Button) mView.findViewById(R.id.submit);
                Button mbackDialog = (Button) mView.findViewById(R.id.backDialog);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mbackDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

        });


        mMyReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewGradedTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        mTakeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });


    }


}
