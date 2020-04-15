package com.example.quizyou.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.MakeTestActivity;
import com.example.quizyou.Test.Test;
import com.example.quizyou.fragments.assign_text;
import com.example.quizyou.fragments.grade_test;
import com.example.quizyou.fragments.logout;
import com.example.quizyou.fragments.view_report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.quizyou.R.string.navigation_drawer_open;

public class TeacherActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
        //implements OnNavigationItemSelectedListener {

    private Button mLogout, mGradeTests, mStudentReports, mAssignTest;

    private Spinner mSpinner;

    private static final String TAG = "TeacherActivity";

    public static Map<String, Object> teachers = new HashMap<>();

    // TODO Menu to view Email/Password/ID/Name/Code/Logout
    // TODO View list of students, list of test made, list of tests results, list of test assigned
    // TODO Make test button
    // TODO Grade test button
    // TODO ArrayList of teachers

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.layout);
        NavigationView navigationView = findViewById(R.id.nav_viewer);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new view_report()).commit();
//        navigationView.setCheckedItem(R.id.student_reports);

        View header = navigationView.getHeaderView(0);

        TextView email = header.findViewById(R.id.email);
        TextView name = header.findViewById(R.id.name);

        mSpinner = findViewById(R.id.madeTestSpinner);
        mAssignTest = findViewById(R.id.assign);

        email.setText(((Teacher) MainActivity.u).getEmail());
        name.setText(((Teacher) MainActivity.u).getName());

        ArrayList<String> assignedTestNames = new ArrayList<>();
        for (Test t : ((Teacher) MainActivity.u).getMadeTests()) {
            assignedTestNames.add(t.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, assignedTestNames);
        mSpinner.setAdapter(adapter);

        mAssignTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            for (Test t : ((Teacher) MainActivity.u).getMadeTests()) {
                if (t.getName().equals(mSpinner.getSelectedItem().toString())) {
                    ((Teacher) MainActivity.u).addAssignedTest(t);

                    MainActivity.mDb.collection("users")
                        .document("Teacher")
                        .update(
                                Long.toString(((Teacher) MainActivity.u).getID()), ((Teacher) MainActivity.u)
                        );

                    break;
                }
            }
            }
        });
    }

    //@Override
    public boolean onNavigationItemSelected (@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.assign_test:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new assign_text()).commit();
                break;

            case R.id.student_reports:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new view_report()).commit();
                break;

            case R.id.grade_test:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new grade_test()).commit();
                break;

            case R.id.logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new logout()).commit();
                break;
        }

        finish();




        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

    // I will finish this by wednesday

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_teacher);
//
//
//        mLogout = findViewById(R.id.logout_button);
//        mGradeTests = findViewById(R.id.grade_test_button);
//        mStudentReports = findViewById(R.id.student_reports_button);
//        mAssignTest = findViewById(R.id.assign_test_button);
//
//        mLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//                return;
//            }
//        });
//
//        mGradeTests.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), GradeTestActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//                return;
//            }
//        });
//
//        mStudentReports.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//                return;
//            }
//        });
//
//        mAssignTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MakeTestActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//                return;
//            }
//        });
//    }
//}
