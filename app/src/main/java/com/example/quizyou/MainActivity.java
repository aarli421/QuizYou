package com.example.quizyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizyou.User.ViewGradedTestActivity;

public class MainActivity extends AppCompatActivity {
    private Button mgradedTest;
    // TODO Dropdown select if you are a student or a teacher
    // TODO SignUp as student or teacher
    // TODO LogIn as student or teacher
    // TODO Store in Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgradedTest = findViewById(R.id.gradedTests);

        mgradedTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewGradedTestActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });
    }


}
