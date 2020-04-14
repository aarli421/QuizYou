package com.example.quizyou.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.MakeTestActivity;
import com.example.quizyou.fragments.assign_text;
import com.example.quizyou.fragments.grade_test;
import com.example.quizyou.fragments.logout;
import com.example.quizyou.fragments.see_results;
import com.example.quizyou.fragments.student_home;
import com.example.quizyou.fragments.take_test;
import com.example.quizyou.fragments.view_report;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

import static com.example.quizyou.R.string.navigation_drawer_open;
public class StudentHomeActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    //implements OnNavigationItemSelectedListener {

    private Button mLogout, mGradeTests, mStudentReports, mAssignTest;


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
        setContentView(R.layout.student_navview);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.layout1);
        NavigationView navigationView = findViewById(R.id.nav_viewer1);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

       //getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new student_home()).commit();
       //navigationView.setCheckedItem(R.id.see_home);



    }

    //@Override
    public boolean onNavigationItemSelected (@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.take_test:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new take_test()).commit();
                break;

            case R.id.see_results:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new see_results()).commit();
                break;

            case R.id.see_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.der_fragment, new student_home()).commit();
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
