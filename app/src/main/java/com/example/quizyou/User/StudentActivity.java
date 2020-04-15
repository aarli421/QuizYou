package com.example.quizyou.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizyou.MainActivity;
import com.example.quizyou.R;
import com.example.quizyou.Test.GradeTestActivity;
import com.example.quizyou.Test.MakeTestActivity;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestActivity;
import com.example.quizyou.fragments.logout;
import com.example.quizyou.fragments.see_results;
import com.example.quizyou.fragments.student_home;
import com.example.quizyou.fragments.take_test;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.quizyou.R.string.navigation_drawer_open;

public class StudentActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    private Button mLogout, mJoinClass, mReports, mTakeTest, mAssignTest;

    private Spinner mSpinner;

    public static Map<String, Object> students = new HashMap<>();

    public static Test selectedTest = null;

    private static final String TAG = "StudentActivity";

    // I will finish this by wednesday

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_navview);

        drawer = findViewById(R.id.layout1);
        NavigationView navigationView = findViewById(R.id.nav_viewer1);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView email = header.findViewById(R.id.email);
        TextView name = header.findViewById(R.id.name);

        mSpinner = findViewById(R.id.pendingTestsSpinner);

        email.setText(((Student) MainActivity.u).getEmail());
        name.setText(((Student) MainActivity.u).getName());

        ArrayList<String> PendingTestNames = new ArrayList<>();
        for (Test t : ((Student) MainActivity.u).getPending()) {
            PendingTestNames.add(t.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, PendingTestNames);
        mSpinner.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mLogout = findViewById(R.id.logout_button);
        mJoinClass = findViewById(R.id.join_class_button);
        mReports = findViewById(R.id.my_reports_button);
        mTakeTest = findViewById(R.id.take_test);

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

                final EditText mResponse = (EditText)  mView.findViewById(R.id.code);
                Button mSubmit = (Button) mView.findViewById(R.id.submit);
                Button mBackDialog = (Button) mView.findViewById(R.id.backDialog);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mBackDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                mSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Teacher t = (Teacher) TeacherActivity.teachers.get(mResponse.getText().toString());

                        if (!(t.getStudentIDs().contains(Long.toString(((Student) MainActivity.u).getID())))) {
                            t.addStudents(Long.toString(((Student) MainActivity.u).getID()));

                            MainActivity.mDb.collection("users")
                                    .document("Teacher")
                                    .update(
                                            Long.toString(t.getID()), t
                                    );

                            Log.d(TAG, t.getStudentIDs().toString());
                        } else {
                            // TODO Already joined class dialog
                            Toast.makeText(StudentActivity.this,"Already joined " +  ((Teacher) TeacherActivity.teachers.get(mResponse.getText().toString())).getName() +"'s class", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                });
            }

        });


        mReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewGradedTestActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        mTakeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Test t : ((Student) MainActivity.u).getPending()) {
                    if (t.getName().equals(mSpinner.getSelectedItem().toString())) {
                        selectedTest = t;
                        break;
                    }
                }

                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

    }

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