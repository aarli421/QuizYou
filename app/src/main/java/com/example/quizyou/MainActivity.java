package com.example.quizyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Question.MultipleChoiceQuestion;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Question.ShortAnswerQuestion;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestResult;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // TODO Dropdown select if you are a student or a teacher
    // TODO SignUp as student or teacher
    // TODO LogIn as student or teacher
    // TODO Store in Firebase

    private EditText mLoginEmail, mLoginPassword, mSignUpName, mSignUpEmail, mSignUpPassword;
    private Button mLogin, mSignUp;
    private Spinner mSpinner;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getId().equals("Student")) {
                                    StudentActivity.students = document.getData();
                                    Log.d(TAG, document.getId() + " => " + StudentActivity.students);
                                } else if (document.getId().equals("Teacher")) {
                                    TeacherActivity.teachers = document.getData();
                                    Log.d(TAG, document.getId() + " => " + TeacherActivity.teachers);
                                }
                            }

                            handler.post(periodicUpdate);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        userIsLoggedIn();

//        Log.d(TAG, "Loading...");
//        mDb.collection("users").document("Student").set(StudentActivity.students);
//        System.out.println(StudentActivity.students);
//        mDb.collection("users").document("Teacher").set(TeacherActivity.teachers);
//        System.out.println(TeacherActivity.teachers);

        FirebaseApp.initializeApp(this);

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mSignUpName = findViewById(R.id.signUpName);
        mSignUpEmail = findViewById(R.id.signUpEmail);
        mSignUpPassword = findViewById(R.id.signUpPassword);

        mLogin = findViewById(R.id.login);
        mSignUp = findViewById(R.id.signUp);

        mSpinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

//        HashMap<Object, Teacher> students = new HashMap<>();
//        Teacher teacher1 = new Teacher("sdfgsdf", "sdfgsdf", "asdf");
//        ArrayList<Question> questions = new ArrayList<>();
//        questions.add(new ShortAnswerQuestion("Why do you like to eat chicken nuggets?", "Cuz I do", 5));
//        questions.add(new ShortAnswerQuestion("Why do you like to nuggets?", "Bruhhh", 7));
//        Test t1 = new Test(5, questions);
//        questions.add(new MultipleChoiceQuestion("You suck diiiiiick", "bruhh", 10));
//        Test t2 = new Test(6, questions);
//        teacher1.addMadeTest(t1);
//        teacher1.addAssignedTest(t1);
//
//        ArrayList<String> ans = new ArrayList<>();
//        ans.add("69");
//        ans.add("You suck dick");
//        ans.add("you suck dick fuck gay ");
//        teacher1.addTestResults(new TestResult(t1, ans, new Student("fsadghfhkj", "sdfgs", "sdfgsdf")));
//        teacher1.addGradedTests(new GradedTest(t1, 5, 18, "notes"));
//        students.put(Integer.toString(1), teacher1);
//        students.put(Integer.toString(2), new Teacher("yeah", "aarondfgsdf", "asasdf"));
//        students.put(Integer.toString(3), new Teacher("ysjadhjfkhkh", "aarondfgsdf", "aem"));

//        mDb.collection("users")
//                .document("students")
//                .set(students);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = mSpinner.getSelectedItem().toString();

                if (selection.equals("Select One")) {
                    // TODO Tell user to select one
                } else {
                    logInWithEmailAuthCredential();
                }
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = mSpinner.getSelectedItem().toString();

                if (selection.equals("Select One")) {
                    // TODO Tell user to select one
                } else {
                    signUpWithEmailAuthCredential();
                }
            }
        });
    }

    private void logInWithEmailAuthCredential() {
        if (mLoginEmail.getText().toString() == null || mLoginPassword.getText().toString() == null) return;

        mAuth.signInWithEmailAndPassword(mLoginEmail.getText().toString(), mLoginPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userIsLoggedIn();
                        } else {
                            mLogin.setText("Login Failed");
                        }
                    }
                });
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
                                StudentActivity.students.put(Integer.toString(s.getID()), s);
                            } else if (mSpinner.getSelectedItem().toString().equals("Teacher")) {
                                Teacher t = new Teacher(mSignUpName.getText().toString(), mSignUpEmail.getText().toString(), mSignUpPassword.getText().toString());
                                TeacherActivity.teachers.put(Integer.toString(t.getID()), t);
                            }

                            userIsLoggedIn();
                        }
                    }
                });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getApplicationContext(), StudentActivity.class));
            finish();
            return;
        }
    }

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 5*1000);

            Log.d(TAG, "Saving...");

            mDb.collection("users")
                    .document("Student")
                    .set(StudentActivity.students);

            mDb.collection("users")
                    .document("Teacher")
                    .set(TeacherActivity.teachers);
        }
    };

}
