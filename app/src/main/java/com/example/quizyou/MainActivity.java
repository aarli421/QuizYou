package com.example.quizyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.quizyou.Test.GradedTest;
import com.example.quizyou.Test.Question.Question;
import com.example.quizyou.Test.Test;
import com.example.quizyou.Test.TestResult;
import com.example.quizyou.User.Student;
import com.example.quizyou.User.StudentActivity;
import com.example.quizyou.User.Teacher;
import com.example.quizyou.User.TeacherActivity;
import com.example.quizyou.User.User;
import com.example.quizyou.signUpSpinner.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // TODO Dropdown select if you are a student or a teacher
    // TODO SignUp as student or teacher
    // TODO LogIn as student or teacher
    // TODO Store in Firebase
    // TODO Adding read from Firebase

    // Sign Up Stuff

    private EditText mSignUpName, mSignUpEmail, mSignUpPassword;
    private Button mSignUp;
    private Spinner mSpinner;


    private EditText mLoginEmail, mLoginPassword;

    private Button mLogin;
    private TextView mGoToSignUp;

    private ImageView quizView, youView;

    private boolean oneFinished = false;

    public static String email, password;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private final static String TAG = "MainActivity";

    private Animation animation;

    public static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_xd);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_out);

        quizView = findViewById(R.id.quizText);
        youView = findViewById(R.id.youText);

        quizView.startAnimation(animation);
        handler.postDelayed(runnable, 500);

        mDb.collection("Students")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    int index = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        index++;
                        StudentActivity.students.put(document.getId(), turnHashMapToStudent((HashMap<String, Object>) document.getData()));
                    }

                    Student.setStaticID(index);

                    Log.d(TAG, StudentActivity.students.toString());

                    if (oneFinished) {
                        //handler.post(periodicUpdate);
                        //userIsLoggedIn();
                    } else {
                        oneFinished = true;
                    }
                }
            });

        mDb.collection("Teachers")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            index++;
                            TeacherActivity.teachers.put(document.getId(), turnHashMapToTeacher((HashMap<String, Object>) document.getData()));
                        }

                        Teacher.setStaticID(index);

                        Log.d(TAG, TeacherActivity.teachers.toString());

                        if (oneFinished) {
                            //handler.post(periodicUpdate);
                            //userIsLoggedIn();
                        } else {
                            oneFinished = true;
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

        FirebaseApp.initializeApp(this);

        load();

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);

        mLogin = findViewById(R.id.login);
        mGoToSignUp = findViewById(R.id.goToSignUp);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInWithEmailAuthCredential();
            }
        });

        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });
    }

    public static Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            youView.startAnimation(animation);
        }
    };

    private void logInWithEmailAuthCredential() {
        if (mLoginEmail.getText().toString() == null || mLoginPassword.getText().toString() == null) return;

        mAuth.signInWithEmailAndPassword(mLoginEmail.getText().toString(), mLoginPassword.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        email = mLoginEmail.getText().toString();
                        password = mLoginPassword.getText().toString();

                        userIsLoggedIn();
                    } else {
                        mLogin.setText("Login Failed");
                    }
                }
            });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            if (email == null) {
                email = user.getEmail();
            }

            for (Object value : TeacherActivity.teachers.values()) {
                if (((Teacher) value).equals(email)) {
                    u = ((Teacher) value);
                    break;
                }
            }

            for (Object value : StudentActivity.students.values()) {
                if (((Student) value).equals(email)) {
                    u = ((Student) value);
                    break;
                }
            }

            if (u != null) {
                try {
                    Student s = ((Student) u);
                    startActivity(new Intent(getApplicationContext(), StudentActivity.class));
                    Log.d(TAG, "User is student");
                } catch (ClassCastException e) {
                    Teacher t = ((Teacher) u);
                    startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
                    Log.d(TAG, "User is teacher");
                }
            }

            finish();
            return;
        }
    }

    public static void load() {
        mDb.collection("Students")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        StudentActivity.students.clear();

                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            index++;
                            StudentActivity.students.put(document.getId(), turnHashMapToStudent((HashMap<String, Object>) document.getData()));
                        }

                        Student.setStaticID(index);

                        if (u != null) {
                            try {
                                Student s = ((Student) u);
                                u = (Student) StudentActivity.students.get(Long.toString(s.getID()));
                                Log.d(TAG, "Student " + ((Student) u).getName() + " was updated");
                            } catch (ClassCastException e) {
                                Log.d(TAG, "Could not cast because of " + e);
                            }
                        }

                        Log.d(TAG, StudentActivity.students.toString());
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });

        mDb.collection("Teachers")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    TeacherActivity.teachers.clear();

                    if (task.isSuccessful()) {
                        int index = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            index++;
                            TeacherActivity.teachers.put(document.getId(), turnHashMapToTeacher((HashMap<String, Object>) document.getData()));
                        }

                        Teacher.setStaticID(index);

                        if (u != null) {
                            try {
                                Teacher t = ((Teacher) u);
                                u = (Teacher) TeacherActivity.teachers.get(Long.toString(t.getID()));
                                Log.d(TAG, "Teacher " + ((Teacher) u).getName() + " was updated");
                            } catch (ClassCastException e) {
                                Log.d(TAG, "Could not cast because of " + e);
                            }
                        }

                        Log.d(TAG, TeacherActivity.teachers.toString());
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
    }

    //public static Handler handler = new Handler();
    public static void save() {
        //handler.postDelayed(periodicUpdate, 5*1000);

        if (u != null) {
            try {
                Student s = ((Student) u);
                mDb.collection("Students")
                        .document(Long.toString(s.getID()))
                        .set(s, SetOptions.merge());

                Log.d(TAG, "Saving " + s.getName() + "...");
            } catch (ClassCastException e) {
                Teacher t = ((Teacher) u);
                mDb.collection("Teachers")
                        .document(Long.toString(t.getID()))
                        .set(t, SetOptions.merge());

                Log.d(TAG, "Saving " + t.getName() + "...");
            }
        }
    }

    public static void save(Student s) {
        mDb.collection("Students")
                .document(Long.toString(s.getID()))
                .set(s);

        Log.d(TAG, "Saving " + s.getName() + "...");
    }

    public static void save(Teacher t) {
        mDb.collection("Teachers")
                .document(Long.toString(t.getID()))
                .set(t);

        Log.d(TAG, "Saving " + t.getName() + "...");
    }

    private static Student turnHashMapToStudent(HashMap<String, Object> map) {
        ArrayList<Test> taken = new ArrayList<>();
        ArrayList<Object> takenList = (ArrayList<Object>) map.get("taken");
        for (Object obj : takenList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            taken.add(turnHashMapToTest(testMap));
        }

        ArrayList<Test> pending = new ArrayList<>();
        ArrayList<Object> pendingList = (ArrayList<Object>) map.get("pending");
        for (Object obj : pendingList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            pending.add(turnHashMapToTest(testMap));
        }

        ArrayList<GradedTest> reports = new ArrayList<>();
        ArrayList<Object> reportsList = (ArrayList<Object>) map.get("reports");
        for (Object obj : reportsList) {
            HashMap<String, Object> gradedTestMap = (HashMap<String, Object>) obj;
            reports.add(turnHashMapToGradedTest(gradedTestMap));
        }

        Student s = new Student((String) map.get("name"), (String) map.get("email"), (String) map.get("password"),
                taken, pending, reports, (Long) map.get("id"));

        return s;
    }

    private static Teacher turnHashMapToTeacher(HashMap<String, Object> map) {
        ArrayList<String> studentIDs = new ArrayList<>();
        ArrayList<Object> studentIDsList = (ArrayList<Object>) map.get("studentIDs");
        for (Object obj : studentIDsList) {
            studentIDs.add((String) obj);
        }

        ArrayList<Test> madeTests = new ArrayList<>();
        ArrayList<Object> madeTestsList = (ArrayList<Object>) map.get("madeTests");
        for (Object obj : madeTestsList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            madeTests.add(turnHashMapToTest(testMap));
        }

        ArrayList<Test> assignedTests = new ArrayList<>();
        ArrayList<Object> assignedTestsList = (ArrayList<Object>) map.get("assignedTests");
        for (Object obj : assignedTestsList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            assignedTests.add(turnHashMapToTest(testMap));
        }

        ArrayList<TestResult> results = new ArrayList<>();
        ArrayList<Object> testResultList = (ArrayList<Object>) map.get("results");
        for (Object obj : testResultList) {
            HashMap<String, Object> testMap = (HashMap<String, Object>) obj;
            results.add(turnHashMapToTestResult(testMap));
        }

        ArrayList<GradedTest> gradedTests = new ArrayList<>();
        ArrayList<Object> gradedTestsList = (ArrayList<Object>) map.get("gradedTests");
        for (Object obj : gradedTestsList) {
            HashMap<String, Object> gradedTestMap = (HashMap<String, Object>) obj;
            gradedTests.add(turnHashMapToGradedTest(gradedTestMap));
        }

        return new Teacher((String) map.get("name"), (String) map.get("email"), (String) map.get("password"),
                studentIDs, madeTests, assignedTests, results, gradedTests, (Long) map.get("id"));
    }

    private static TestResult turnHashMapToTestResult(HashMap<String, Object> map) {
        long studentID = (long) map.get("studentID");
        Test test = turnHashMapToTest((HashMap<String, Object>) map.get("test"));
        ArrayList<String> answers = (ArrayList<String>) map.get("answers");
        int exitedApp = (int) (long) map.get("exitedApp");
        String start = (String) map.get("start");
        String end = (String) map.get("end");
        return new TestResult(test, answers, studentID, exitedApp, start, end);
    }

    private static GradedTest turnHashMapToGradedTest(HashMap<String, Object> map) {
        String notes = (String) map.get("notes");
        int points = (int) (long) map.get("points");
        int totalPoints = (int) (long) map.get("totalPoints");
        long id = (long) map.get("studentId");
        HashMap<String, Object> testMap = (HashMap<String, Object>) map.get("test");

        return new GradedTest(turnHashMapToTest(testMap), points, totalPoints, notes, id);
    }

    private static Test turnHashMapToTest(HashMap<String, Object> map) {
        String name = (String) map.get("name");
        long timeLimit = (Long) map.get("timeLimit");
        ArrayList<Object> questionsList = (ArrayList<Object>) map.get("questions");
        ArrayList<Question> questions = turnObjListToQuestionsList(questionsList);

        return new Test(timeLimit, questions, name);
    }

    private static ArrayList<Question> turnObjListToQuestionsList(ArrayList<Object> questionsList) {
        ArrayList<Question> questions = new ArrayList<>();
        for (Object obj : questionsList) {
            HashMap<String, Object> question = (HashMap<String, Object>) obj;
            Question q = new Question((String) question.get("prompt"), (String) question.get("answer"), (int) (long) question.get("points"));
            questions.add(q);
        }

        return questions;
    }
}
