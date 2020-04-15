package com.example.quizyou;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.example.quizyou.signUpSpinner.UserAdapter;
import com.example.quizyou.signUpSpinner.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private EditText mSignUpName, mSignUpEmail, mSignUpPassword;

    private ArrayList<UserItem> mUserList;
    private UserAdapter mAdapter;

    private Button mSignUp;

    private Spinner mSpinner;

    private String selection;

    private static final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        FirebaseApp.initializeApp(this);

        MainActivity.mDb.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            MainActivity.readData(task);
                            MainActivity.handler.post(MainActivity.periodicUpdate);
                            userIsLoggedIn();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        mSpinner = findViewById(R.id.dropdown);

        initList();

        mAdapter = new UserAdapter(this, mUserList);
        mSpinner.setAdapter(mAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSignUpName = findViewById(R.id.signUpName);
        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserItem clickedItem = (UserItem) parent.getItemAtPosition(position);
                selection = clickedItem.getUserName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selection = mSpinner.getSelectedItem().toString();

                if (selection.equals("Select One")) {
                    // TODO Tell user to select one
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(new ContextThemeWrapper(SignUpActivity.this, R.style.dialog));
                    View mView = getLayoutInflater().inflate(R.layout.activity_select_one, null);
                    Button mClose = (Button) mView.findViewById(R.id.close);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    mClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    signUpWithEmailAuthCredential();
                }
            }
        });
    }

    private void initList(){
        mUserList = new ArrayList<>();
        mUserList.add(new UserItem("Teacher", R.drawable.teacher));
        mUserList.add(new UserItem("Student", R.drawable.student));
    }

    private void signUpWithEmailAuthCredential() {
        if (mSignUpName.getText().toString() == null || mSignUpEmail.getText().toString() == null || mSignUpPassword.getText().toString() == null) return;

        mAuth.createUserWithEmailAndPassword(mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mSpinner.getSelectedItem().toString().equals("Student")) {
                                Student s = new Student(mSignUpName.getText().toString(), mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString());
                                StudentActivity.students.put(Long.toString(s.getID()), s);
                            } else if (mSpinner.getSelectedItem().toString().equals("Teacher")) {
                                Teacher t = new Teacher(mSignUpName.getText().toString(), mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString());
                                TeacherActivity.teachers.put(Long.toString(t.getID()), t);
                            }

                            MainActivity.email = mSignUpEmail.getText().toString();
                            MainActivity.password = mSignUpPassword.getText().toString();

                            userIsLoggedIn();
                        }
                    }
                });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (MainActivity.email == null) {
                MainActivity.email = user.getEmail();
            }

            for (Object value : TeacherActivity.teachers.values()) {
                if (((Teacher) value).equals(MainActivity.email)) {
                    MainActivity.u = ((Teacher) value);
                    break;
                }
            }

            for (Object value : StudentActivity.students.values()) {
                if (((Student) value).equals(MainActivity.email)) {
                    MainActivity.u = ((Student) value);
                    break;
                }
            }

            if (MainActivity.u != null) {
                try {
                    Student s = ((Student) MainActivity.u);
                    startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                    Log.d(TAG, "User is student");
                } catch (ClassCastException e) {
                    Teacher t = ((Teacher) MainActivity.u);
                    startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                    Log.d(TAG, "User is teacher");
                }
            }

            finish();
            return;
        }
    }
}
