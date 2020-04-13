package com.example.quizyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Question.ShortAnswerQuestion;
import com.example.quizyou.Test.Test;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // TODO Dropdown select if you are a student or a teacher
    // TODO SignUp as student or teacher
    // TODO LogIn as student or teacher
    // TODO Store in Firebase

    private EditText mLoginEmail, mLoginPassword, mSignUpName, mSignUpEmail, mSignUpPassword;
    private Button mLogin, mSignUp;
    private Spinner mSpinner;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        //userIsLoggedIn();

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
//        teacher1.addAssignedTest(new Test(5, questions));
//        teacher1.add
//        students.put(Integer.toString(1), );
//        students.put(Integer.toString(2), new Teacher("yeah", "aarondfgsdf", "asasdf"));
//        students.put(Integer.toString(3), new Teacher("ysjadhjfkhkh", "aarondfgsdf", "aem"));

//        mDb.collection("users").document("students")
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
}
